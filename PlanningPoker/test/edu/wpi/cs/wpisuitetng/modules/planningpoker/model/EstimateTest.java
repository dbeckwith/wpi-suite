/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * TODO: Contributors' names
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Tests the Estimate class
 * 
 * @author Team 9
 * @version 1.0
 */
public class EstimateTest {
    
    /**
     * Tests that the compare methods work correctly
     */
    @Test
    public void testCompareMethods() {
        final Estimate est1 = new Estimate(new User("joe", "joe", "password", 1), 20, null);
        final Estimate est2 = new Estimate(new User("joe", "joe", "password", 2), 15, null);
        final Estimate est3 = new Estimate(new User("joe", "joe", "password", 3), 20, null);
        Assert.assertTrue(est1.compareTo(est2) > 0);
        Assert.assertTrue(est1.compareTo(est3) == 0);
        Assert.assertTrue(est2.compareTo(est1) < 0);
    }
    
    /**
     * Tests that the Estimate is the same after being transformed to JSON and back
     */
    @Test
    public void testJSON() {
        final Estimate est = new Estimate(new User("joe", "joe", "password", 1), 20, null);
        final Estimate estAfter = Estimate.fromJSON(est.toJSON());
        Assert.assertEquals(est.getIdNum(), estAfter.getIdNum());
        Assert.assertEquals(est.getName(), estAfter.getName());
        Assert.assertEquals(est.getUsername(), estAfter.getUsername());
        Assert.assertEquals(est.getEstimate(), estAfter.getEstimate(), 3);
    }
    
}
