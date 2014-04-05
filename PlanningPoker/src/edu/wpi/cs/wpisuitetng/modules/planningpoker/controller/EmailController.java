package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import javax.mail.internet.AddressException;

import org.apache.commons.mail.*;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This class controls the sending of email notifications
 * 
 * @author nfbrown, szhou, dcwethern
 * 
 */
public class EmailController {
    private UserRequestObserver observer;
    private User[] users;
    
    private String username = "team9wpi";
    private String from = "team9wpi@gmail.com";     // GMail user name
    private String password = "team9ftw";           // GMail password
    private String subject = "A new Planning Poker game has begun!";
    private String body;
    
    /**
     * Creates a new EmailController class
     */
    public EmailController() {
        this.observer = new UserRequestObserver(this);
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
     * 
     * @throws EmailException
     * @throws AddressException
     */
    private void sendEmails() {
        setBody();
        System.setProperty("java.net.preferIPv4Stack", "true");
        try {
            for (User u : users) {
                if (u.getEmail() != null) {
                    Email email = new SimpleEmail();
                    email.setHostName("smtp.gmail.com");
                    email.setSmtpPort(587);
                    email.setAuthenticator(new DefaultAuthenticator(username, password));
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
     * Requests query of all users related to the project
     */
    private void requestUsers() {
        final Request request = Network.getInstance().makeRequest("core/user",
                HttpMethod.GET);
        request.addObserver(observer); // add an observer to process the response
        request.send(); // send the request
    }
    
    /**
     * Sets the users list to the users received by the network
     * 
     * @param users
     *        The list of users received by UserRequestController
     */
    public void receivedUsers(User[] users) {
        if (users != null) {
            this.users = users;
            sendEmails();
        }
        else {
            System.err.println("Users not received properly");
        }
    }
    
    
}
