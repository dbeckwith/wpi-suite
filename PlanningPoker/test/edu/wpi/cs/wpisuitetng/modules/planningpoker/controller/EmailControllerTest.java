package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class EmailControllerTest {
    
    EmailController ec;
    MockData db;
    MockNetwork network;
    User u1;
    User u2;
    User u3;
    User u4;
    Project testProject;
    String mockSsid;
    Session defaultSession;
    
    @Before
    public void setup() {
        ec = new EmailController();
        u1 = new User("James Bond", "jbond", "team9wpi@gmail.com", null, 7);
        u2 = new User("Money Penny", "mpenny", "team9wpi@gmail.com", null, 2);
        u3 = new User("Q", "q", "shanzhou321@gmail.com", "secret", 1);
        u4 = new User("M", "m", "nickb1694@gmail.com", null, 0);
        testProject = new Project("test", "1");
        mockSsid = "abc123";
        defaultSession = new Session(u1, testProject, mockSsid);
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        db = new MockData(new HashSet<Object>());
        db.save(u1, testProject);
        db.save(u2, testProject);
        db.save(u3, testProject);
        db.save(u4, testProject);
    }
    
    @Test
    public void testTest() {
        ec.sendNotifications();
    }
    
}
