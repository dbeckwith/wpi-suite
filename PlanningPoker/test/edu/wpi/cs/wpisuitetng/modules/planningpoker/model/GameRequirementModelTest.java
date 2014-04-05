package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

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
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 25));
        Assert.assertEquals("testing median failed", 25, grm.getEstimateMean(),
                3);
    }
    
}
