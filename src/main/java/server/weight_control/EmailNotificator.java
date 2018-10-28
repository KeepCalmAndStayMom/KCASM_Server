package server.weight_control;


import server.database2.LoginDB;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailNotificator {

    private final static String EMAIL_KCASM = "kandstaymom@gmail.com";
    private final static String PASSWORD_KCASM = "KCASM_96";
    private final static String SUBJECT = "KCASM - Controllo Peso";

    static void sendEmail(String msg, String reciverEmail) {

        if(reciverEmail!=null)
        {

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(EMAIL_KCASM, PASSWORD_KCASM);
                        }
                    });

            try {
                MimeMessage message = new MimeMessage(session);
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciverEmail));
                message.setSubject(SUBJECT);
                message.setText(msg);

                Transport.send(message);
                System.out.println("Mail inviata correttamente");
            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Errore nell'invio della mail");
            }
        }
        else
            System.out.println("Email non trovata");
    }
}
