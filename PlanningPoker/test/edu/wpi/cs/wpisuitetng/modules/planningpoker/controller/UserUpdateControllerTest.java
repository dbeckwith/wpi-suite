/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the UserUpdateController class
 * 
 * @author Team 9
 * @version 1.0
 */
public class UserUpdateControllerTest {
    
    /**
     * Tests the behavior of the notification setting methods
     */
    @Test
    public void testSetNotifications() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        final UserUpdateController uuc = UserUpdateController.getInstance();
        final User steve = new User("Steve", "steve", "Pass", 1);
        uuc.setUser(steve);
        
        assertFalse(steve.isNotifyByEmail());
        uuc.setNotifyByEmail(true);
        assertTrue(steve.isNotifyByEmail());
        uuc.setNotifyByEmail(false);
        assertFalse(steve.isNotifyByEmail());
        
        assertFalse(steve.isNotifyByIM());
        uuc.setNotifyByIM(true);
        assertTrue(steve.isNotifyByIM());
        uuc.setNotifyByIM(false);
        assertFalse(steve.isNotifyByIM());
        
    }
    
}
