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

import java.util.Arrays;
import java.util.LinkedList;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

/**
 * This controller responds when a game is started or ended by sending email
 * notifications
 * 
 * @author team9
 * @version 1.0
 */
public class EmailController {
    
    /**
     * Subject line for a new game email.
     */
    private static final String NEW_GAME_SUBJECT = "A new Planning Poker game has begun!"; //$NON-NLS-1$
    
    /**
     * Subject line for an ended game email.
     */
    private static final String END_GAME_SUBJECT = "A Planning Poker game has ended."; //$NON-NLS-1$
    
    /**
     * All users in the current project.
     */
    private User[] users = new User[] {};
    
    /**
     * Creates a new EmailController class. Private to avoid instansiation.
     */
    private EmailController() { // $codepro.audit.disable emptyMethod    
    }
    
    /**
     * The instance of the controller.
     */
    private static EmailController Instance;
    
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
     * Sends an email with the given subject and body to all users who have
     * chosen to receive email notifications.
     * 
     * @param subject
     *        the subject line of the email
     * @param body
     *        the body of the email
     */
    public void sendEmails(String subject, String body) {
        final EmailSenderThread sender = new EmailSenderThread(subject, body);
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
        sendEmails(END_GAME_SUBJECT, endGameMessageBody(game));
    }
    
    /**
     * Sends an email notification to all users who have chosen to receive
     * email notifications that a game has started.
     * 
     * @param game
     *        the game has started
     */
    public void sendGameStartNotifications(GameModel game) {
        sendEmails(NEW_GAME_SUBJECT, startGameMessageBody(game));
    }
    
    /**
     * Sets the users in the current project.
     */
    public void setUsers(User[] users) {
        System.out.println("Setting users to " + Arrays.asList(users));
        this.users = users;
    }
    
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
    private String startGameMessageBody(GameModel game) {
        String body = "\n";
        
        if (game.getOwner() == null) {
            body += "An unknown user has created a new Planning Poker game called ";
        }
        else {
            body += game.getOwner().getName()
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
    
    
    /**
     * Generates a email message body for an ended game notification.
     * 
     * @param game
     *        the ended game to notify people about
     * @return a String containing the message body
     */
    private String endGameMessageBody(GameModel game) {
        String body = "\n";
        body += "The Planning Poker game " + game.getName()
                + " has ended and is no longer open for estimation.";
        return body;
    }
}
