package be.leerstad.EindwerkChezJava.model;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * MultiPartExample Model Object.
 * 
 * <P>
 * Various attributes of MultipartExample, and related behaviour used to send an email.
 * 
 * @author Aaron Westerlinck
 * @version 1.0
 */
public class MultipartChezJava {
	
	private final Logger logger = Logger.getLogger(MultipartChezJava.class);
	final String username = "cvoleerstadB1@gmail.com";
	final String password = "programmeren3";

	/** Creates new MultipartExample */
	public MultipartChezJava() {

	}

/// Contenttype is "text/html; charset=utf-8"
	public BodyPart addSimpleBodyPart(String tekst, String contentType)
			throws MessagingException {
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(tekst, contentType);
		return messageBodyPart;
	}

	public BodyPart addAttachment(String stringFile) throws MessagingException {
		File file = new File(stringFile);
		MimeBodyPart attachmentBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);
		DataHandler handler = new DataHandler(source);
		attachmentBodyPart.setDataHandler(handler);
		attachmentBodyPart.setFileName(file.getName());
		attachmentBodyPart.setDisposition(BodyPart.ATTACHMENT);
		return attachmentBodyPart;
	}
	/**
	 * Sent mail from cvoleerstadB1@gmail.com to the reciever
	 * @param multipart
	 * @throws MessagingException 
	 */
	public void sendMail(Multipart multipart, String reciever, String subject ) throws MessagingException {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("cvoleerstadB1@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(reciever));
			message.setSubject(subject);
			message.setSentDate(new Date(System.currentTimeMillis()));
			message.setContent(multipart);
			Transport.send(message);

			logger.info("mail is succesfully sent!");

		} catch (MessagingException e ) {
			//logger.error(e);
			throw e ;//new RuntimeException(e);		
		}

	}

}