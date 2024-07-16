package ar.com.ultrafibra.webultrafibra.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.AddressException;
import javax.mail.util.ByteArrayDataSource;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Data
public class EmailServiceImpl {

    private Properties PROP;
    private String SENDER;
    private String PASSWORD;
    private MimeMessage mCorreo;
    private Session M_SESSION;

    public EmailServiceImpl() {
        PROP = new Properties();

        this.SENDER = "alertas.ups.ultrafibra@gmail.com";
        this.PASSWORD = "spgsodpzqyxfqjxh";

        PROP.put("mail.smtp.host", "smtp.gmail.com");
        PROP.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        PROP.setProperty("mail.smtp.starttls.enable", "true");
        PROP.setProperty("mail.smtp.port", "587");
        PROP.setProperty("mail.smtp.user", this.SENDER);
        PROP.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        PROP.setProperty("mail.smtp.auth", "true");

        this.M_SESSION = Session.getDefaultInstance(PROP);
    }

    public int enviarMail(String email, String asunto, String mensaje) {

        try {
            mCorreo = new MimeMessage(M_SESSION);
            mCorreo.setFrom(new InternetAddress("nl.loragro@gmail.com"));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mCorreo.setSubject(asunto);
            mCorreo.setText(mensaje, "ISO-8859-1", "html");

            Transport mTransport = M_SESSION.getTransport("smtp");
            mTransport.connect(this.SENDER, this.PASSWORD);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();

            return 200;

        } catch (AddressException ex) {
            return 500;
        } catch (MessagingException ex) {
            return 500;
        }

    }

    public int enviarMail(String email, String asunto, String mensaje, MultipartFile multipartFile) {
        try {
            // Crear el mensaje de correo
            mCorreo = new MimeMessage(M_SESSION);
            mCorreo.setFrom(new InternetAddress(this.SENDER));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mCorreo.setSubject(asunto);

            // Crear el cuerpo del mensaje
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mensaje, "text/html; charset=ISO-8859-1");

            // Crear un multipart para agregar el cuerpo y los adjuntos
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new ByteArrayDataSource(multipartFile.getBytes(), multipartFile.getContentType());
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName(multipartFile.getOriginalFilename());
            multipart.addBodyPart(attachmentBodyPart);

            // Configurar el mensaje con el contenido del multipart
            mCorreo.setContent(multipart);

            // Enviar el mensaje
            Transport mTransport = M_SESSION.getTransport("smtp");
            mTransport.connect(this.SENDER, this.PASSWORD);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();

            return 200;

        } catch (MessagingException | IOException ex) {
            ex.printStackTrace();
            return 500;
        }
    }
}
