package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbstractUserControllerTest {
    
    @Test
    public void testSetTimeOut() {
        EmailController ec = EmailController.getInstance();
        ec.setTimeout(1);
        assertEquals(1, ec.getTimeout());
    }
    
}
