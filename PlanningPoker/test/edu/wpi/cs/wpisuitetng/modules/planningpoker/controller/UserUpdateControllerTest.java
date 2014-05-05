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

import edu.wpi.cs.wpisuitetng.modules.core.models.Carrier;
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
        User steve = new User("steve", "steve", "Pass", 1);
        
        Assert.assertFalse(steve.isNotifyByEmail());
        steve.setNotifyByEmail(true);
        steve = updateAndRetrieve(steve);
        
        Assert.assertTrue(steve.isNotifyByEmail());
        
        steve.setNotifyByEmail(false);
        steve = updateAndRetrieve(steve);
        Assert.assertFalse(steve.isNotifyByEmail());
        
        Assert.assertFalse(steve.isNotifyBySMS());
        steve.setNotifyBySMS(true);
        steve = updateAndRetrieve(steve);
        
        Assert.assertTrue(steve.isNotifyBySMS());
        
        steve.setNotifyBySMS(false);
        steve = updateAndRetrieve(steve);
        Assert.assertFalse(steve.isNotifyBySMS());
        
        Assert.assertNull(steve.getEmail());
        steve.setEmail("steve@example.com");
        steve = updateAndRetrieve(steve);
        Assert.assertEquals("steve@example.com", steve.getEmail());
        
        Assert.assertEquals(Carrier.UNKNOWN, steve.getCarrier());
        steve.setCarrier(Carrier.VERIZON);
        steve = updateAndRetrieve(steve);
        Assert.assertEquals(Carrier.VERIZON, steve.getCarrier());
        
        Assert.assertNull(steve.getPhoneNumber());
        steve.setPhoneNumber("0123456789");
        steve = updateAndRetrieve(steve);
        Assert.assertEquals("0123456789", steve.getPhoneNumber());
    }
    
    private User updateAndRetrieve(final User user) {
        // assume that the user is sent and retrieved
        return user;
    }
    
}
