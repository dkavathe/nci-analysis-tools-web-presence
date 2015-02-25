/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nih.nci.queue.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author wangy21
 */
public class MailUtil {

    public boolean mailTo(String destEmail, String _title, String _message) {
        // Recipient's email ID needs to be mentioned.
        String to = destEmail;

        // Sender's email ID needs to be mentioned
        String user = System.getenv("USER");
        String hostname = System.getenv("HOSTNAME");
        String from = "ncianalysis@ncias-d1373-v.nci.nih.gov";
        if(user!= null && user.length() > 0 && hostname != null && hostname.length() > 0) {
            from = user + "@" + hostname;
        }

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(_title);

            // Send the actual HTML message, as big as you like
            // message.setContent(_message, "text/html");
            message.setText(_message);

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {            
            return false;
        }
        return true;
    }
}