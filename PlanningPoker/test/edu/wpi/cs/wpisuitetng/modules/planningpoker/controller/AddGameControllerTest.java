package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Andrew
 * 
 */
public class AddGameControllerTest {
    
    static AddGameController instance;
    
    @BeforeClass
    static public void setUpBeforeClass() {
        AddGameControllerTest.instance = AddGameController.getInstance();
    }
    
    @Test
    public void testGetInstance() {
        Assert.assertEquals(
                "A new instance is not the same as the previous instance",
                AddGameControllerTest.instance, AddGameController.getInstance());
    }
    
}
