package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class EmailSenderThreadTest {
    
    @Test
    public void testSendEmail() {
        User person = new User("Team9", "User", "dummyemail@awefjf.com", "Pass", 0);
        person.setNotifyByEmail(true);
        EmailController.getInstance().receivedUsers(new User[] {person});
        EmailSenderThread est = new EmailSenderThread("Hi", "This came from EmailSenderThreadTest.java");
        est.run();
    }
    
}
