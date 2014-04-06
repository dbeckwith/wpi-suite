package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class EstimateTest {
    
    @Test
    public void testCompareMethods() {
        Estimate est1 = new Estimate(new User ("joe", "joe", "password", 1), 20);
        Estimate est2 = new Estimate(new User ("joe", "joe", "password", 2), 15);
        Estimate est3 = new Estimate(new User ("joe", "joe", "password", 3), 20);
        assertTrue(est1.compareTo(est2) > 0);
        assertTrue(est1.compareTo(est3) == 0);
        assertTrue(est2.compareTo(est1) < 0);
    }
    
    @Test
    public void testJSON() {
        Estimate est = new Estimate(new User("joe", "joe", "password", 1), 20);
        Estimate estAfter = est.fromJSON(est.toJSON());
        assertEquals(est.getUser().getIdNum(), estAfter.getUser().getIdNum());
        assertEquals(est.getUser().getName(), estAfter.getUser().getName());
        assertEquals(est.getUser().getUsername(), estAfter.getUser().getUsername());
        assertEquals(est.getEstimate(), estAfter.getEstimate(), 3);
    }
    
}
