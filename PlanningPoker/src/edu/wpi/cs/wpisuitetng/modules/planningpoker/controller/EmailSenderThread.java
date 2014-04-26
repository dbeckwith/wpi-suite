/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * A class for handling email sending to users.
 * @author Team 9
 * @version 1.0
 */
public class EmailSenderThread extends Thread { // $codepro.audit.disable declareDefaultConstructors

    /**
     * Gmail username.
     */
    private static final String USERNAME = "team9wpi"; //$NON-NLS-1$
    
    /**
     * Gmail address.
     */
    private static final String EMAIL_ADDRESS = "team9wpi@gmail.com";  //$NON-NLS-1$
    
    /**
     * Gmail password.
     */
    private static final String PASSWORD = "team9ftw";  //$NON-NLS-1$
    
    /**
     * The SMTP port to connect to on the googlemail server.
     */
    private static final int SMTP_PORT = 587;
    
    /**
     * Subject and body of the email.
     */
    private final String subject, body;
    
    /**
     * Creates a new sender thread.
     * 
     * @param subject
     *        the subject line of the email to be sent
     * @param body
     *        the body of the email to be sent
     */
    public EmailSenderThread(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }
    
    @Override
    public void run() {
        sendEmails();
    }
    
    /**
     * Sends the email to all users who have chosen to recieve email
     * notifications.
     */
    private void sendEmails() {
        System.setProperty("java.net.preferIPv4Stack", "true"); //$NON-NLS-2$ //$NON-NLS-1$
        try {
            for (User u : EmailController.getInstance().getUsers()) {
                if (u != null && u.getEmail() != null && u.isNotifyByEmail()) {
                    Email email = new SimpleEmail();
                    email.setHostName("smtp.gmail.com"); //$NON-NLS-1$
                    email.setSmtpPort(SMTP_PORT);
                    email.setAuthenticator(new DefaultAuthenticator(USERNAME,
                            PASSWORD));
                    email.setSSLOnConnect(true);
                    email.addTo(u.getEmail());
                    email.setSubject(subject);
                    email.setFrom(EMAIL_ADDRESS);
                    email.setMsg("Dear " + u.getName() + "," //$NON-NLS-1$ // $codepro.audit.disable disallowStringConcatenation
                            + System.getProperty("line.separator") + body); //$NON-NLS-1$
                    email.send();
                    System.out.println("Sent email to " + u.getName());
                }
            }
        }
        catch (EmailException e) {
            // failed to send email
            e.printStackTrace();
            return;
        }
    }
    
}
