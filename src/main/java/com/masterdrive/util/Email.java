package com.masterdrive.util;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.masterdrive.util.Status.Code;

@Configuration
@EnableTransactionManagement
public class Email {

	@Autowired
	private static Environment env;
	
	public static boolean sendMail(String recepient,String message,String subject)
    {
        String host = env.getProperty("email.host");
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable",env.getProperty("email.starttls.enable"));
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", env.getProperty("email.user"));
        props.put("mail.smtp.user", env.getProperty("email.user.password"));
        props.put("mail.smtp.port", env.getProperty("email.port"));
        props.put("mail.smtp.auth", env.getProperty("email.host.auth"));
        Session session = Session.getDefaultInstance(props,null);
        MimeMessage mimeMessage = new MimeMessage(session);
        try{
           mimeMessage.setFrom(new InternetAddress(env.getProperty("email.user")));
           InternetAddress address = new InternetAddress(recepient);
           mimeMessage.setRecipient(Message.RecipientType.TO, address);
           mimeMessage.setSubject(subject);
           mimeMessage.setText(message);
           Transport transport = session.getTransport(env.getProperty("email.transport"));
           transport.connect(host,env.getProperty("email.user"),env.getProperty("email.user.password"));
           transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
           transport.close();
           return true;
        }
        catch(MessagingException e)
        {
            return false;
        }       
    }
}
