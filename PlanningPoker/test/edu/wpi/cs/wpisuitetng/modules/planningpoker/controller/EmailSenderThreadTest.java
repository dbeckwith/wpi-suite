/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Tests the EmailSenderThread class
 * 
 * @author Team 9
 * @version 1.0
 */
public class EmailSenderThreadTest {
    
    /**
     * Tests email sending functionality by sending an e-mail to team9wpi@gmail.com
     */
    @Test
    public void testSendEmail() {
        final User person = new User("Team9", "User", "team9wpi@gmail.com",
                "Pass", 0);
        person.setNotifyByEmail(true);
        Assert.assertTrue(person.isNotifyByEmail());
        EmailController.getInstance().setUsers(new User[] { person });
        final EmailSenderThread est = new EmailSenderThread(
                "EmailSenderThreadTest",
                "This came from EmailSenderThreadTest.java");
        est.start();
    }
    
}
