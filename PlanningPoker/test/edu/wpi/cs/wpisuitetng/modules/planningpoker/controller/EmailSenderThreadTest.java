package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class EmailSenderThreadTest {
    
    @Test
    public void testSendEmail() {
        User person = new User("Team9", "User", "team9wpi@gmail.com", "Pass", 0);
        person.setNotifyByEmail(true);
        EmailController.getInstance().receivedUsers(new User[] {person});
        EmailSenderThread est = new EmailSenderThread("EmailSenderThreadTest", "This came from EmailSenderThreadTest.java");
        est.run();
    }
    
}
