package com.ForMonk2.helpers;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Message;
import com.ForMonk2.model.*;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.ForMonk2.model.BlogModels.SendEmailRequest;

public class BlogHelper {

	public static ApiResponseModel<String> sendEmail(SendEmailRequest sendEmailRequest) throws Exception {
		
		ApiResponseModel<String> response = new ApiResponseModel<>();

		String emailPort = "587";		
		Properties properties = System.getProperties();
		properties.put("mail.smtp.port", emailPort);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(properties);
		
		try {
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("InfluenceMonk"));
			
			for(String to : sendEmailRequest.to) {
				if(emailIsValid(to))
					message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
				else {
					response.setError(true);
					if(response.getMessage() == null ) {
						response.setMessage(to +" is an invalid email address . \n");
					}else {
						response.setMessage(response.getMessage() + to +"is an invalid address \n");
					}
				}
				
			}
			
			if(response.getError())
				return response;

			message.setSubject(sendEmailRequest.subject);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(sendEmailRequest.body);
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			message.setContent(multipart);
			
			String emailHost = "smtp.gmail.com";
			String fromUser = "majumdartanmay68";
			String fromUserEmailPassword = "Vugo&$7745";
			
			Transport transport = session.getTransport("smtp");
			
			transport.connect(emailHost, fromUser, fromUserEmailPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			
			response.setMessage("Email has been sent");
			return response;
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	private static boolean emailIsValid(String email) {
		
		Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        
        return matcher.find();
	}
	
}
 