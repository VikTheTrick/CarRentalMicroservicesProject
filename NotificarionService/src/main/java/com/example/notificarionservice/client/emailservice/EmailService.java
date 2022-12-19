package com.example.notificarionservice.client.emailservice;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    public EmailService() {

    }

    public void send(String recipient, String text, String subject)
    {
        final String username = "marebrzina@zohomail.eu";
        final String password = "bakaprase69";

        String host = "127.0.0.1";

        // Getting system properties
        Properties properties = System.getProperties();

        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);

        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress("viktor@baka.com"));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("This is Subject");

            // set body of the email.
            message.setText("This is a test mail");

            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }
}
