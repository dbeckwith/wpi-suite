package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class controls the sending of email notifications The code for sending
 * emails was based on code at
 * http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
 * 
 * @author nfbrown, szhou, dcwethern
 * 
 */
public class EmailController implements IUserController {
	private UserRequestObserver observer;
	private User[] users;

	private String from = "team9wpi@gmail.com"; // GMail user name
	private String password = "team9ftw"; // GMail password
	private String subject = "A planning poker game is about to begin";
	private String body;

	/**
	 * Creates a new EmailController class
	 */
	public EmailController() {
		super();
	}

	public void sendNotifications() {
		requestUsers();
	}

	private void setBody() {
		String username = ConfigManager.getConfig().getUserName();
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				this.body = u.getName()
						+ " has created a new Planning Poker game. Please make your estimates!";
				break;
			}
		}
	}

	/**
	 * Sends an email to all users that are not the creator of the game
	 */
	private void sendEmails() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, password);
					}
				});

		setBody();

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(this.from));
			message.setSubject(this.subject);

			for (User u : users) {
				if (u.getEmail() != null) {
					if (!u.getUsername().equals(
							ConfigManager.getConfig().getUserName())) {
						message.setRecipient(Message.RecipientType.TO,
								new InternetAddress(u.getEmail()));
						message.setText("Dear " + u.getName() + ",\n" + body);
						Transport.send(message);
					}
				}
			}
		} catch (MessagingException e) {
			System.err.println("Email notifications failed to send");
			e.printStackTrace();
		}
	}

	/**
	 * Requests query of all users related to the project
	 */
	private void requestUsers() {
		final Request request = Network.getInstance().makeRequest("core/user",
				HttpMethod.GET);
		request.addObserver(observer); // add an observer to process the
										// response
		request.send(); // send the request
	}

	/**
	 * Sets the users list to the users received by the network
	 * 
	 * @param users
	 *            The list of users received by UserRequestController
	 */
	public void receivedUsers(User[] users) {
		if (users != null) {
			this.users = users;
			sendEmails();
		} else {
			System.err.println("Users not received properly");
		}
	}

}
