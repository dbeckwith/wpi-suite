/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockRequest;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the CurrentUserController class
 * 
 * @author Team 9
 * @version 1.0
 */
public class CurrentUserControllerTest {
    
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    PrintStream oldOut = System.out;
    PrintStream oldErr = System.err;
    
    /**
     * Sets the streams to catch standard out and error
     */
    @Before
    public void prepareStreams() {
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
    }
    
    /**
     * Set users to null to avoid problems with stuff from previous tests
     */
    @Before
    public void resetController() {
        CurrentUserController.getInstance().setUsers(null);
    }
    
    /**
     * Restores standard out and error
     */
    @After
    public void cleanUpStreams() {
        System.setOut(oldOut);
        System.setErr(oldErr);
    }
    
    /**
     * Tests to ensure requestUsers makes a network request
     */
    @Test
    public void testRequest() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        final CurrentUserController cuc = CurrentUserController.getInstance();
        cuc.requestUsers();
        final MockRequest request = ((MockNetwork) Network.getInstance()).getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    /**
     * Tests to see if the receivedUsers method works correctly for a null list
     */
    @Test
    public void testReceivedNullUsers() {
        // Setup
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        final CurrentUserController cuc = CurrentUserController.getInstance();
        
        cuc.receivedUsers(null);
        Assert.assertEquals(cuc.getUser(), null);
        Assert.assertNull(cuc.getUsers());
        try {
            cuc.findUser("Username");
            // iterates through a null list, should throw nullpointer
            Assert.fail();
        }
        catch (NullPointerException e) {
            Assert.assertTrue(true);
        }
    }
    
    /**
     * Tests if receivedUsers method works correctly for a non-null list of
     * users
     */
    @Test
    public void testReceivedNonNullUsers() {
        // Setup
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        final CurrentUserController cuc = CurrentUserController.getInstance();
        
        final User expected = new User("User", "Username", "Password", 1);
        final User[] received = { expected };
        
        cuc.receivedUsers(received);
        Assert.assertEquals(cuc.getUser(), null);
        Assert.assertEquals(expected, cuc.findUser("Username"));
        
    }
    
    /**
     * Since we're not connected to a server, the request will time out
     * and not retrieve a user
     */
    @Test
    public void testRequestThreadTimeout() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        final CurrentUserController cuc = CurrentUserController.getInstance();
        cuc.requestUsers();
        
        Assert.assertTrue(cuc.requestTimedOut());
    }
    
}
