package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * 
 * @author Andrew, Lukas
 * 
 */
public class EstimateTest {
    
    @Test
    public void testCompareMethods() {
        Estimate est1 = new Estimate(new User("joe", "joe", "password", 1), 20);
        Estimate est2 = new Estimate(new User("joe", "joe", "password", 2), 15);
        Estimate est3 = new Estimate(new User("joe", "joe", "password", 3), 20);
        Assert.assertTrue(est1.compareTo(est2) > 0);
        Assert.assertTrue(est1.compareTo(est3) == 0);
        Assert.assertTrue(est2.compareTo(est1) < 0);
    }
    
    @Test
    public void testJSON() {
        Estimate est = new Estimate(new User("joe", "joe", "password", 1), 20);
        Estimate estAfter = est.fromJSON(est.toJSON());
        Assert.assertEquals(est.getIdNum(), estAfter.getIdNum());
        Assert.assertEquals(est.getName(), estAfter.getName());
        Assert.assertEquals(est.getUsername(), estAfter.getUsername());
        Assert.assertEquals(est.getEstimate(), estAfter.getEstimate(), 3);
    }
    
}
