/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Tests the GameRequirementModel class
 * 
 * @author Team 9
 * @version 1.0
 */
public class GameRequirementModelTest {
    
    /**
     * Tests that mean calculation of estimates works correctly
     */
    @Test
    public void testMean() {
        final GameRequirementModel grm = new GameRequirementModel(-1,
                "A requirement", "A Description", "A type");
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 0), 25,
                null));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 1), 20,
                null));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 30,
                null));
        Assert.assertEquals("testing mean failed", 25, grm.getEstimateMean(), 3);
    }
    
    /**
     * Tests that median calculation of estimates works correctly
     */
    @Test
    public void testMedian() {
        final GameRequirementModel grm = new GameRequirementModel(-1,
                "A requirement", "A Description", "A type");
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 0), 25,
                null));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 1), 20,
                null));
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 30,
                null));
        Assert.assertEquals("testing median failed", 25,
                grm.getEstimateMedian(), 3);
        grm.addEstimate(new Estimate(new User("Bob", "Bob", "password", 2), 25,
                null));
        Assert.assertEquals("testing median failed", 25,
                grm.getEstimateMedian(), 3);
        
    }
    
    /**
     * Tests that mean and median return 0 for an empty model
     */
    @Test
    public void testEmptyModel() {
        final GameRequirementModel grm = new GameRequirementModel(-1,
                "A requirement", "A description", "A type",
                new ArrayList<Estimate>());
        Assert.assertEquals(0, grm.getEstimateMean(), 3);
        Assert.assertEquals(0, grm.getEstimateMedian(), 3);
    }
    
    /**
     * Tests that other constructors for GameRequirementModel work correctly
     */
    @Test
    public void testOtherConstructors() {
        final GameRequirementModel blank = new GameRequirementModel();
        Assert.assertEquals(0, blank.getParentId());
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
    
    /**
     * Tests that the equals method for GameRequirementModels works correctly
     */
    @Test
    public void testEqualsMethod() {
        final Object grm = new GameRequirementModel(-1, "A requirement",
                "A description", "A type", new ArrayList<Estimate>());
        final Object grmDouble = new GameRequirementModel(-1, "A requirement",
                "A description", "A type", new ArrayList<Estimate>());
        final Object grmDifferent1 = new GameRequirementModel(-1,
                "A requirement", "B description", "A type");
        final Object grmDifferent2 = new GameRequirementModel(5,
                "B requirement", "A description", "B type");
        final Object grmDifferent3 = new GameRequirementModel(6,
                "C requirement", "D description", "Z type");
        final Object blank = new GameRequirementModel();
        final int anInteger = 5;
        final String aString = "A String";
        
        Assert.assertTrue(grm.equals(grm));
        Assert.assertFalse(grm.equals(blank));
        Assert.assertFalse(blank.equals(grm));
        Assert.assertFalse(grm.equals(anInteger));
        Assert.assertFalse(grm.equals(aString));
        Assert.assertTrue(grm.equals(grmDouble));
        Assert.assertTrue(grmDouble.equals(grm));
        Assert.assertFalse(grm.equals(grmDifferent1));
        Assert.assertFalse(grm.equals(grmDifferent2));
        Assert.assertFalse(grm.equals(grmDifferent3));
        
    }
    
}
