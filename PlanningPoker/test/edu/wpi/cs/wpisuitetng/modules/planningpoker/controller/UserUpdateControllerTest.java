package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 *
 */
public class UserUpdateControllerTest {
    
    @Test
    public void testSetNotifications() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        UserUpdateController uuc = UserUpdateController.getInstance();
        User steve = new User("Steve", "steve", "Pass", 1);
        uuc.setUser(steve);
        
        assertFalse(steve.isNotifyByEmail());
        assertFalse(uuc.canNotifyByEmail());
        uuc.setNotifyByEmail(true);
        assertTrue(steve.isNotifyByEmail());
        assertTrue(uuc.canNotifyByEmail());
        uuc.setNotifyByEmail(false);
        assertFalse(steve.isNotifyByEmail());
        assertFalse(uuc.canNotifyByEmail());
        
        
        assertFalse(steve.isNotifyByIM());
        assertFalse(uuc.canNotifyByIM());
        uuc.setNotifyByIM(true);
        assertTrue(steve.isNotifyByIM());
        assertTrue(uuc.canNotifyByIM());
        uuc.setNotifyByIM(false);
        assertFalse(steve.isNotifyByIM());
        assertFalse(uuc.canNotifyByIM());
        
    }
    
}
