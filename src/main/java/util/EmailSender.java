package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailSender {
    private static final String email = "kavijaedu@gmail.com";
    private static final String password = "KavijaEdu@2006";

    public void sendReciept(String reciever, byte[] reportBytes) {
        String pdf = "src/main/resources/reports/pdf/orderSummery.pdf";
        final String username = "kavijaedu@gmail.com";
        final String password = "KavijaEdu@2006";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.3"); // Example for TLSv1.2

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kavijakumuditha12@gmail.com", "inha wydj scgr pubr");
            }
        });

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("thisu2006@gmail.com"));
            message.setSubject("Report Email");

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setFileName("report.pdf");
            attachmentPart.setContent(reportBytes, "application/pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
