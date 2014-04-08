package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * 
 * @author Lukas, Andrew
 * 
 */
public class GameRequirementModelTest {
    
    @Test
    public void testMean() {
        final GameRequirementModel grm = new GameRequirementModel(-1,
                "A requirement", "A Description", "A type");
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 0), 25));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 1), 20));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 30));
        Assert.assertEquals("testing mean failed", 25, grm.getEstimateMean(), 3);
    }
    
    @Test
    public void testMedian() {
        final GameRequirementModel grm = new GameRequirementModel(-1,
                "A requirement", "A Description", "A type");
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 0), 25));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 1), 20));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 30));
        Assert.assertEquals("testing median failed", 25,
                grm.getEstimateMedian(), 3);
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 25));
        Assert.assertEquals("testing median failed", 25,
                grm.getEstimateMedian(), 3);
        
    }
    
    @Test
    public void testEmptyModel() {
        final GameRequirementModel grm = new GameRequirementModel(-1,
                "A requirement", "A description", "A type",
                new ArrayList<Estimate>());
        Assert.assertEquals(0, grm.getEstimateMean(), 3);
        Assert.assertEquals(0, grm.getEstimateMedian(), 3);
    }
    
    @Test
    public void testOtherConstructors() {
        final GameRequirementModel blank = new GameRequirementModel();
        Assert.assertEquals(-1, blank.getParentId());
        blank.setParentId(1);
        Assert.assertEquals(1, blank.getParentId());
        Assert.assertSame("", blank.getType());
        Assert.assertSame("", blank.getName());
        final GameRequirementModel fromReq = new GameRequirementModel(
                new Requirement(2, "Test Req", "Test Desc"));
        Assert.assertSame("Test Req", fromReq.getName());
        Assert.assertSame("Test Desc", fromReq.getDescription());
        Assert.assertEquals(0, fromReq.getEstimates().size());
        
    }
    
}
