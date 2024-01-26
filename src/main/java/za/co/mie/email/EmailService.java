package za.co.mie.email;


import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;



public class EmailService {

    public static void sendReservationConfirmation(String recipientEmail,String msg) {
        final String senderEmail = "tebogomarvin8@gmail.com";
        final String senderPassword = "yigp xhck brfs ekbk";

        // Email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            message.setSubject("Flight Reservation Confirmation");

            message.setText(msg);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error: " + e.getMessage());
        }
    }
    
}
