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
import edu.wpi.cs.wpisuitetng.modules.core.models.Carrier;

/**
 * A class for handling email sending to users.
 * 
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
    private final String subject, body, SMSbody;
    
    /**
     * Creates a new sender thread.
     * 
     * @param subject
     *        the subject line of the email to be sent
     * @param body
     *        the body of the email to be sent
     * @param SMSbody
     */
    public EmailSenderThread(String subject, String body, String SMSbody) {
        this.subject = subject;
        this.body = body;
        this.SMSbody = SMSbody;
    }
    
    @Override
    public void run() {
        sendSMSNotifications();
        sendEmailNotifications();
    }
    
    private void sendSMSNotifications() {
        for (User u : EmailController.getInstance().getUsers()) {
            if (u != null && u.getPhoneNumber() != null
                    && u.getCarrier() != Carrier.UNKNOWN && u.isNotifyBySMS()) {
                sendEmail(u,
                        u.getPhoneNumber() + "@" + u.getCarrier().getURL(),
                        null, SMSbody);
                System.out.println("Sent SMS to " + u.getName());
            }
        }
    }
    
    private void sendEmailNotifications() {
        for (User u : EmailController.getInstance().getUsers()) {
            if (u != null && u.getEmail() != null && u.isNotifyByEmail()) {
                sendEmail(u, u.getEmail(), subject, body);
                System.out.println("Sent email to " + u.getName());
            }
        }
    }
    
    /**
     * Sends the email to all users who have chosen to receive email
     * notifications.
     */
    private void sendEmail(User u, String emailAddress, String theSubject, String theBody) {
        System.setProperty("java.net.preferIPv4Stack", "true"); //$NON-NLS-2$ //$NON-NLS-1$
        try {
            final Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com"); //$NON-NLS-1$
            email.setSmtpPort(SMTP_PORT);
            email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
            email.setSSLOnConnect(true);
            email.addTo(emailAddress);
            email.setSubject(theSubject);
            email.setFrom(EMAIL_ADDRESS);
            email.setMsg("Dear " + u.getName() + "," //$NON-NLS-1$ // $codepro.audit.disable disallowStringConcatenation
                    + System.getProperty("line.separator") + theBody); //$NON-NLS-1$
            email.send();
        }
        catch (EmailException e) {
            // failed to send email
            e.printStackTrace();
        }
    }
    
}
