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
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 0), 25, null));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 1), 20, null));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 30, null));
        Assert.assertEquals("testing mean failed", 25, grm.getEstimateMean(), 3);
    }
    
    @Test
    public void testMedian() {
        final GameRequirementModel grm = new GameRequirementModel(-1,
                "A requirement", "A Description", "A type");
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 0), 25, null));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 1), 20, null));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 30, null));
        Assert.assertEquals("testing median failed", 25,
                grm.getEstimateMedian(), 3);
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 25, null));
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
    
    @Test
    public void testEqualsMethod() {
        final GameRequirementModel grm = new GameRequirementModel(-1,
                "A requirement", "A description", "A type",
                new ArrayList<Estimate>());
        final GameRequirementModel grmDouble = new GameRequirementModel(-1,
                "A requirement", "A description", "A type",
                new ArrayList<Estimate>());
        final GameRequirementModel grmDifferent1 = new GameRequirementModel(-1,
                "A requirement", "B description", "A type");
        final GameRequirementModel grmDifferent2 = new GameRequirementModel(5,
                "B requirement", "A description", "B type");
        final GameRequirementModel grmDifferent3 = new GameRequirementModel(6,
                "C requirement", "D description", "Z type");
        final GameRequirementModel blank = new GameRequirementModel();
        int anInteger = 5;
        String aString = "A String";
        
        Assert.assertTrue(grm.equals(grm));
        Assert.assertFalse(grm.equals(blank));
        Assert.assertFalse(blank.equals(grm));
        Assert.assertFalse(grm.equals(anInteger));
        Assert.assertFalse(grm.equals(aString));
        Assert.assertTrue(grm.equals(grmDouble));
        Assert.assertTrue(grmDouble.equals((Object) grm));
        Assert.assertFalse(grm.equals((Object) grmDifferent1));
        Assert.assertFalse(grm.equals( grmDifferent1));
        Assert.assertFalse(grm.equals((Object) grmDifferent2));
        Assert.assertFalse(grm.equals(grmDifferent2));
        Assert.assertFalse(grm.equals((Object) grmDifferent3));
        Assert.assertFalse(grm.equals(grmDifferent3));
        
    }
    
}
