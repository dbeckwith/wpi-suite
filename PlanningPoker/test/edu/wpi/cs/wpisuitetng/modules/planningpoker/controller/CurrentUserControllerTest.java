package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.assertEquals;

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

public class CurrentUserControllerTest {
    
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    PrintStream oldOut = System.out;
    PrintStream oldErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(oldOut);
        System.setErr(oldErr);
    }
    
    @Test
    public void testRequest() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        CurrentUserController cuc = CurrentUserController.getInstance();
        cuc.requestUsers();
        MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    @Test
    public void testReceivedUsers(){
        CurrentUserController cuc = CurrentUserController.getInstance();
        cuc.receivedUsers(null);
        assertEquals("No users received\r\n", errStream.toString());
        cuc.receivedUsers(new User[] {new User("User", "Username", "Password", 1)});
        assertEquals("Set user to null\r\n", outStream.toString());
    }
    
}
