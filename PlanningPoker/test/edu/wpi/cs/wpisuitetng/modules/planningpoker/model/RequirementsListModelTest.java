package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * 
 * @author Andrew
 * 
 */
public class RequirementsListModelTest {
    
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream oldOut = System.out;
    
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outStream));
    }
    
    @After
    public void cleanUpStreams() {
        System.setOut(oldOut);
    }
    
    @Test
    public void testObserverManipulation() {
        RequirementsListModel rlm = RequirementsListModel.getInstance();
        SimpleListObserver mySLO = new SimpleListObserver() {
            
            @Override
            public void listUpdated() {
                System.out.print("Observers updated");
            }
        };
        rlm.addListListener(mySLO);
        Assert.assertTrue(rlm.getObservers().contains(mySLO));
        rlm.addRequirement(new GameRequirementModel());
        Assert.assertTrue("Observers updated",
                outStream.toString().contains("Observers updated"));
        rlm.removeListListener(mySLO);
        Assert.assertFalse(rlm.getObservers().contains(mySLO));
        
    }
    
}
