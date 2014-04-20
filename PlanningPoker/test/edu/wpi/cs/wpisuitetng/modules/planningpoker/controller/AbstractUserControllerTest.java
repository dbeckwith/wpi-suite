package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.junit.Assert;
import org.junit.Test;

public class AbstractUserControllerTest {
    
    @Test
    public void testSetTimeOut() {
        EmailController ec = EmailController.getInstance();
        ec.setTimeout(1);
        Assert.assertEquals(1, ec.getTimeout());
    }
    
}
