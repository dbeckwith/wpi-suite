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
 * 
 * @author Andrew, Lukas
 * 
 */
public class EstimateTest {
    
    @Test
    public void testCompareMethods() {
        Estimate est1 = new Estimate(new User("joe", "joe", "password", 1), 20, null);
        Estimate est2 = new Estimate(new User("joe", "joe", "password", 2), 15, null);
        Estimate est3 = new Estimate(new User("joe", "joe", "password", 3), 20, null);
        Assert.assertTrue(est1.compareTo(est2) > 0);
        Assert.assertTrue(est1.compareTo(est3) == 0);
        Assert.assertTrue(est2.compareTo(est1) < 0);
    }
    
    @Test
    public void testJSON() {
        Estimate est = new Estimate(new User("joe", "joe", "password", 1), 20, null);
        Estimate estAfter = Estimate.fromJSON(est.toJSON());
        Assert.assertEquals(est.getIdNum(), estAfter.getIdNum());
        Assert.assertEquals(est.getName(), estAfter.getName());
        Assert.assertEquals(est.getUsername(), estAfter.getUsername());
        Assert.assertEquals(est.getEstimate(), estAfter.getEstimate(), 3);
    }
    
}
