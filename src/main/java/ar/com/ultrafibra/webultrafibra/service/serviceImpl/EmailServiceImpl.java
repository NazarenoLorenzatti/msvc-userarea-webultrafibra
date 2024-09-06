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

    private Properties prop;
    private String remitente;
    private String password;
    private MimeMessage mCorreo;
    private Session mSession;
    private String email;

    public EmailServiceImpl() {
        prop = new Properties();

        this.remitente = "catchall@ultrafibra.com.ar";
        this.password = "I5/wE*a6wA";
        this.email = "contacto@ultrafibra.com.ar";
        prop.put("mail.smtp.host", "vps-2838525-x.dattaweb.com");
        prop.put("mail.smtp.ssl.trust", "vps-2838525-x.dattaweb.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");

        this.mSession = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });
    }

    public int enviarMail(String asunto, String mensaje) {
        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress("contacto@ultrafibra.com.ar"));
            mCorreo.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mCorreo.setSubject(asunto);
            mCorreo.setText(mensaje, "ISO-8859-1", "html");

            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(this.remitente, this.password);
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
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(this.remitente));
            mCorreo.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
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
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(this.remitente, this.password);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();

            return 200;

        } catch (MessagingException | IOException ex) {
            ex.printStackTrace();
            return 500;
        }
    }
}
