package com.techverse.service;

import java.security.SecureRandom;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {
	  private static final String DIGITS = "0123456789";
	    private static final SecureRandom random = new SecureRandom();
	 /*
	  
	 private String senderEmail="laxmipatil070295@gmail.com";
	
 
     private String senderPassword= "mfaxitwotgaxhkst";
	
	 */
	
	
	
	
	private String senderEmail="laxmi.patil@techverse.world";
	
	 
    private String senderPassword= "9vYXuAf9ptj7";
	
     private String host= "smtp.zoho.in";
	
	 
     int port=587;
     
     @Autowired
     private JavaMailSender emailSender;

     
	
	public boolean sendEmail(String recipientEmail,String emailSubject, String emailBody)
	{
		Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a mail session with the specified properties
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a message
        	
        	
        	
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(emailSubject);
            message.setContent(emailBody, "text/html");
            // message.setContent();

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully.");
            return true;

        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return false;
        }
    }
	 
	
	
	

	public boolean sendEmailWithAttachment(String recipientEmail, String emailSubject, String emailBody, MultipartFile birthCertificate ,
           MultipartFile lastResult,
             MultipartFile parentAadhar,
             MultipartFile studentAadhar,
            MultipartFile bankDoc,
             MultipartFile cast,
             MultipartFile transferCertificate,
              MultipartFile profile,
            MultipartFile sssmid) {
	    Properties props = new Properties();
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", port);
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");

	    // Create a mail session with the specified properties
	    Session session = Session.getInstance(props, new Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(senderEmail, senderPassword);
	        }
	    });

	    try {
	        // Create a message
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(senderEmail));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
	        message.setSubject(emailSubject);

	        // Create the message body part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(emailBody, "text/html");

	        
	     // Create the multipart message
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	        
	       
	        
	        
	         multipart.addBodyPart(createAttachment(birthCertificate));
	         multipart.addBodyPart(createAttachment( lastResult));
	         multipart.addBodyPart(createAttachment(parentAadhar));
	         multipart.addBodyPart(createAttachment(studentAadhar));
	         multipart.addBodyPart(createAttachment(bankDoc));
	         multipart.addBodyPart(createAttachment( cast));
	         multipart.addBodyPart(createAttachment(transferCertificate));
	         multipart.addBodyPart(createAttachment(profile));
	         multipart.addBodyPart(createAttachment(sssmid));
	         
	        // Set the multipart message to the email message
	        message.setContent(multipart);

	        // Send the message
	        Transport.send(message);

	        System.out.println("Email with attachment sent successfully.");
	        return true;

	    } catch (Exception e) {
	        System.out.println("Error sending email with attachment: " + e.getMessage());
	        return false;
	    }
	}


	 public MimeBodyPart createAttachment(MultipartFile file) throws Exception {
	        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
	        DataSource source = new ByteArrayDataSource(file.getBytes(), file.getContentType());
	        attachmentBodyPart.setDataHandler(new DataHandler(source));
	        attachmentBodyPart.setFileName(file.getOriginalFilename());
	        return attachmentBodyPart;
	    }
	 
	 
	 
	 public  String generateOTP(int length) {
	        StringBuilder otp = new StringBuilder(length);
	        for (int i = 0; i < length; i++) {
	            int index = random.nextInt(DIGITS.length());
	            otp.append(DIGITS.charAt(index));
	        }
	        return otp.toString();
	    }
}