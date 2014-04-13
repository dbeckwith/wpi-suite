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

import java.util.LinkedList;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This class controls the sending of email notifications
 * 
 * @author nfbrown, szhou, dcwethern
 * 
 */
public class EmailController extends AbstractUserController {
    
    /**
     * Subject line for a new game email.
     */
    private static final String NEW_GAME_SUBJECT = "A new Planning Poker game has begun!"; //$NON-NLS-1$
    
    /**
     * Subject line for an ended game email.
     */
    private static final String END_GAME_SUBJECT = "A Planning Poker game has ended."; //$NON-NLS-1$
    
    /**
     * A list of all threads used to send email notifications. Threads are added
     * to the list when {@link #sendEmails(String, String)} is called and are
     * run when the controller recieves users from the server to avoid sending
     * to users who have chosen not to recieve emails or not sending to users
     * who have.
     */
    private final LinkedList<EmailSenderThread> senderThreads = new LinkedList<>();
    
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
     * Gets the instance of the controller.
     * 
     * @return the instance of the controller.
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
        senderThreads.add(sender);
        requestUsers();
    }
    
    /**
     * Sets the users list to the users received by the network
     * 
     * @param users
     *        The list of users received by UserRequestController
     */
    @Override
    public void receivedUsers(User[] users) {
        if (users != null) {
            setUsers(users);
            while (!senderThreads.isEmpty()) { // $codepro.audit.disable methodInvocationInLoopCondition
                senderThreads.pop().start();
            }
        }
    }
    
    /**
     * Sends an email notification to all users who have chosen to receive
     * email notifications that a game has ended.
     */
    public void sendGameEndNotifications() {
        final String body = "Please check the Planning Poker module for updates on recently ended games."; //$NON-NLS-1$
        sendEmails(END_GAME_SUBJECT, body);
    }
    
    /**
     * Sends an email notification to all users who have chosen to receive
     * email notifications that a game has started.
     */
    public void sendGameStartNotifications() {
        final String body = CurrentUserController.getInstance().getUser() // $codepro.audit.disable disallowStringConcatenation
                .getName()
                + " has created a new Planning Poker game. Please make your estimates!"; //$NON-NLS-1$
        sendEmails(NEW_GAME_SUBJECT, body);
    }
    
}
