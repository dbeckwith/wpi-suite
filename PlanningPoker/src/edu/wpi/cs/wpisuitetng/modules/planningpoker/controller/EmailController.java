package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import javax.mail.internet.AddressException;

import org.apache.commons.mail.*;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This class controls the sending of email notifications
 * 
 * @author nfbrown, szhou, dcwethern
 * 
 */
public class EmailController extends AbstractUserController {

	private String username = "team9wpi";
	private String from = "team9wpi@gmail.com"; // GMail user name
	private String password = "team9ftw"; // GMail password
	private String subject = "A new Planning Poker game has begun!";
	private String body;

	/**
	 * Creates a new EmailController class
	 */
	public EmailController() {
		super();
	}

	/**
	 * Gets all users, then sends email notifications to all users with email
	 * addresses on file
	 */
	public void sendNotifications() {
		requestUsers();
	}

	/**
	 * Sets the body of the email
	 */
	private void setBody() {
		String username = ConfigManager.getConfig().getUserName();
		for (User u : getUsers()) {
			if (u.getUsername().equals(username)) {
				this.body = u.getName()
						+ " has created a new Planning Poker game. Please make your estimates!";
				break;
			}
		}
	}

	/**
	 * Sends an email to all users
	 * 
	 * @throws EmailException
	 * @throws AddressException
	 */
	private void sendEmails() {
		setBody();
		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			for (User u : getUsers()) {
				if (u.getEmail() != null) {
					Email email = new SimpleEmail();
					email.setHostName("smtp.gmail.com");
					email.setSmtpPort(587);
					email.setAuthenticator(new DefaultAuthenticator(username,
							password));
					email.setSSLOnConnect(true);
					email.addTo(u.getEmail());
					email.setSubject(subject);
					email.setFrom(from);
					email.setMsg("Dear " + u.getName() + ",\n" + body);
					email.send();
				}
			}
		} catch (EmailException e) {
			System.err.println("Email failed to send");
			e.printStackTrace();
		}
	}

	/**
	 * Sets the users list to the users received by the network
	 * 
	 * @param users
	 *            The list of users received by UserRequestController
	 */
	@Override
	public void receivedUsers(User[] users) {
		if (users != null) {
			setUsers(users);
			sendEmails();
		} else {
			System.err.println("Users not received properly");
		}
	}

}
