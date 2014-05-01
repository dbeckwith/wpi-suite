/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * nfbrown, szhou, dcwethern
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

/**
 * This controller responds when a game is started or ended by sending email
 * notifications
 * 
 * @author Team 9
 * @version 1.0
 */
public class EmailController {
    
    
    
    /**
     * Subject line for a new game email.
     */
    private static final String NEW_GAME_SUBJECT = "A new Planning Poker game has begun!";
    
    /**
     * Subject line for an ended game email.
     */
    private static final String END_GAME_SUBJECT = "A Planning Poker game has ended.";
    
    /**
     * All users in the current project.
     */
    private User[] users = new User[] {};
    
    /**
     * Creates a new EmailController class. Private to avoid instantiation.
     */
    private EmailController() { // $codepro.audit.disable emptyMethod    
    }
    
    /**
     * The instance of the controller.
     */
    private static EmailController Instance = null;
    
    /**
     * The owner of the game that emails are being sent for.
     */
    private static User gameOwner = null;
    
    /**
     * Gets the instance of the EmailController.
     * 
     * @return the instance of the EmailController.
     */
    public static EmailController getInstance() {
        if (Instance == null) {
            Instance = new EmailController();
        }
        return Instance;
    }
    
    /**
     * Sets the owner of the game for which notifications are being sent.
     */
    public static void setOwner(User owner) {
    	gameOwner = owner;
    }
    
    /**
     * Sends an email with the given subject and body to all users who have
     * chosen to receive email notifications.
     * 
     * @param subject
     *        the subject line of the email
     * @param body
     *        the body of the email
     * @param SMSbody
     */
    public void sendEmails(String subject, String body, String SMSbody) { // $codepro.audit.disable methodShouldBeStatic, lineLength
        final EmailSenderThread sender = new EmailSenderThread(subject, body, SMSbody);
        sender.start();
    }
    
    /**
     * Sends an email notification to all users who have chosen to receive
     * email notifications that a game has ended.
     * 
     * @param game
     *        the game has ended
     */
    public void sendGameEndNotifications(GameModel game) {
        final String endGameMessageBody = endGameMessageBody(game);
        sendEmails(END_GAME_SUBJECT, endGameMessageBody, endGameMessageBody);
    }
    
    /**
     * Sends an email notification to all users who have chosen to receive
     * email notifications that a game has started.
     * 
     * @param game
     *        the game has started
     */
    public void sendGameStartNotifications(GameModel game) {
        sendEmails(NEW_GAME_SUBJECT, startGameMessageBody(game), startGameMessageBodySMS(game));
    }
    
    /**
     * Sets the users in the current project.
     */
    public void setUsers(User[] users) {
        this.users = users;
    }
    
    /**
     * Gets the users in the current project.
     */
    public User[] getUsers() {
        return users;
    }
    
    /**
     * Generates a email message body for a new game notification.
     * 
     * @param game
     *        the new game to notify people about
     * @return a String containing the message body
     */
    private static String startGameMessageBody(GameModel game) {
        String body = "\n";
        
        if (game.getOwner() == null) {
            body += "An unknown user has created a new Planning Poker game called ";
        }
        else {
            body += gameOwner.getName()
                    + " has created a new Planning Poker game called ";
        }
        body += game.getName() + ".\n\n";
        
        body += "The requirements for this game are:\n";
        for (GameRequirementModel req : game.getRequirements()) {
            body += "\t" + req.toString() + "\n";
        }
        body += "\n";
        
        body += game.getEndTime() == null ? ""
                : "The deadline for submitting estimations is "
                        + game.getEndTime();
        return body;
    }
    
    private static String startGameMessageBodySMS(GameModel game) {
        String body = "\n";
        
        if (game.getOwner() == null) {
            body += "An unknown user has created a new Planning Poker game called ";
        }
        else {
            body += gameOwner.getName()
                    + " has created a new Planning Poker game called ";
        }
        body += game.getName() + ".\n\n";
        
        body += game.getEndTime() == null ? ""
                : "Deadline: "
                        + game.getEndTime();
        return body;
    }  
    
    
    /**
     * Generates a email message body for an ended game notification.
     * 
     * @param game
     *        the ended game to notify people about
     * @return a String containing the message body
     */
    private static String endGameMessageBody(GameModel game) {
        String body = "\n";
        body += "The Planning Poker game " + game.getName()
                + " has ended and is no longer open for estimation.";
        return body;
    }
}
