package ar.com.ultrafibra.webultrafibra.service.util;

import java.io.UnsupportedEncodingException;
import lombok.extern.slf4j.Slf4j;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.internet.AddressException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    private Properties prop;
    private String remitente;
    private String password;
    private MimeMessage mCorreo;
    private Session mSession;

    public EmailService() {
        prop = new Properties();

        this.remitente = "catchall@ultrafibra.com.ar";
        this.password = "I5/wE*a6wA";

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

    public void enviarMail(String email, String asunto, String urlConfirm) throws UnsupportedEncodingException {
        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress("catchall@ultrafibra.com.ar", "UltraFibra"));
            mCorreo.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mCorreo.setSubject(asunto);
            mCorreo.setContent(getBodyMessage(urlConfirm), "text/html; charset=utf-8");

            Transport.send(mCorreo);

            System.out.println("Correo Enviado = " + email);

        } catch (AddressException ex) {
            log.error("Error en la dirección de correo: {}", ex.getMessage(), ex);
        } catch (MessagingException ex) {
            log.error("Error al enviar el correo: {}", ex.getMessage(), ex);
        }
    }

    private String getBodyMessage(String urlConfirm) {
        return "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Confirmación de Email</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            background-color: #050f1c; /* Azul oscuro */\n"
                + "            color: #ffffff;\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            text-align: center;\n"
                + "            padding: 20px;\n"
                + "        }\n"
                + "        .container {\n"
                + "            max-width: 800px;\n" /* Ajuste del ancho del contenedor */
                + "            margin: 0 auto;\n"
                + "            background-color: #ffffff;\n"
                + "            color: #000000;\n"
                + "            border-radius: 10px;\n"
                + "            overflow: hidden;\n"
                + "            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);\n" /* Sombra para darle un efecto más pulido */
                + "        }\n"
                + "        .header {\n"
                + "            background-color: #050f1c;\n"
                + "            padding: 20px;\n" /* Reducción del padding */
                + "        }\n"
                + "        .header img {\n"
                + "            width: 150px;\n" /* Reducción del tamaño del logo */
                + "            height: auto;\n"
                + "        }\n"
                + "        .content {\n"
                + "            padding: 20px;\n"
                + "        }\n"
                + "        .button {\n"
                + "            display: inline-block;\n"
                + "            margin: 20px auto;\n"
                + "            padding: 10px 20px;\n"
                + "            background-color: #050f1c;\n" /* Corrección del doble símbolo # */
                + "            color: #ffffff;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            font-size: 16px;\n"
                + "            font-weight: bold;\n" /* Negrita para el texto del botón */
                + "        }\n"
                + "        .button:hover {\n"
                + "            background-color: #0a1b33;\n" /* Color más claro para el hover */
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "            <img src=\"https://drive.google.com/uc?export=view&id=1Odeuxlly5jGTmaEdTP4csUvvGXvymq7-\" alt=\"Logo de la Empresa\">\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <h1>Confirma tu Email</h1>\n"
                + "            <p>Gracias por registrarte en nuestra plataforma. Por favor, confirma tu email haciendo clic en el botón de abajo.</p>\n"
                + "            <a href=\"" + urlConfirm + "\" class=\"button\">Confirmar Email</a>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
    }

    public void recoveryPasswordEmail(String email, String asunto, String password) throws UnsupportedEncodingException {

        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress("catchall@ultrafibra.com.ar", "UltraFibra"));
            mCorreo.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mCorreo.setSubject(asunto);
            mCorreo.setContent(getBodyMessageRecoveryPassword(password), "text/html; charset=utf-8");

            Transport.send(mCorreo);

            System.out.println("Correo Enviado = " + email);

        } catch (AddressException ex) {
            log.error("Error en la dirección de correo: {}", ex.getMessage(), ex);
        } catch (MessagingException ex) {
            log.error("Error al enviar el correo: {}", ex.getMessage(), ex);
        }
    }

    private String getBodyMessageRecoveryPassword(String password) {
        return "<html lang=\"en\">\n"
                + "<head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    <title>Recuperar Contraseña</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            background-color: #050f1c; /* Azul oscuro */\n"
                + "            color: #ffffff;\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            text-align: center;\n"
                + "            padding: 20px;\n"
                + "        }\n"
                + "        .container {\n"
                + "            max-width: 800px;\n" /* Ajuste del ancho del contenedor */
                + "            margin: 0 auto;\n"
                + "            background-color: #ffffff;\n"
                + "            color: #000000;\n"
                + "            border-radius: 10px;\n"
                + "            overflow: hidden;\n"
                + "            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);\n" /* Sombra para darle un efecto más pulido */
                + "        }\n"
                + "        .header {\n"
                + "            background-color: #050f1c;\n"
                + "            padding: 20px;\n" /* Reducción del padding */
                + "        }\n"
                + "        .header img {\n"
                + "            width: 150px;\n" /* Reducción del tamaño del logo */
                + "            height: auto;\n"
                + "        }\n"
                + "        .content {\n"
                + "            padding: 20px;\n"
                + "        }\n"
                + "        .button {\n"
                + "            display: inline-block;\n"
                + "            margin: 20px auto;\n"
                + "            padding: 10px 20px;\n"
                + "            background-color: #050f1c;\n" /* Corrección del doble símbolo # */
                + "            color: #ffffff;\n"
                + "            text-decoration: none;\n"
                + "            border-radius: 5px;\n"
                + "            font-size: 16px;\n"
                + "            font-weight: bold;\n" /* Negrita para el texto del botón */
                + "        }\n"
                + "        .button:hover {\n"
                + "            background-color: #0a1b33;\n" /* Color más claro para el hover */
                + "        }\n"
                + "    </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <div class=\"container\">\n"
                + "        <div class=\"header\">\n"
                + "           <img src=\"https://drive.google.com/uc?export=view&id=1Odeuxlly5jGTmaEdTP4csUvvGXvymq7-\" alt=\"Logo de la Empresa\">\n"
                + "        </div>\n"
                + "        <div class=\"content\">\n"
                + "            <h1>Recuperacion de Contraseña</h1>\n"
                + "            <p>Recibimos una Solicitud para recuperar tu Contraseña.</p>\n"
                + "            <p>Si usted no solicito esto por favor Desestimelo y cambie su contraseña.</p>\n"
                + "            <b>Contrase Actual: <b> <p> " + password + "</p>\n"
                + "        </div>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
    }
}
