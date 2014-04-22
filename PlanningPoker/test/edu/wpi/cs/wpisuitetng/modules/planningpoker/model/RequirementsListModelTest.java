/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * Tests the RequirementsListModel class
 * 
 * @author Team 9
 * @version 1.0
 */
public class RequirementsListModelTest {
    
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream oldOut = System.out;
    
    /**
     * Sets the stream for standard out
     */
    @Before
    public void prepareStreams() {
        System.setOut(new PrintStream(outStream));
    }
    
    /**
     * Restores the normal standard out stream
     */
    @After
    public void cleanUpStreams() {
        System.setOut(oldOut);
    }
    
    /**
     * Tests that adding and removing observers works correctly
     */
    @Test
    public void testObserverManipulation() {
        final RequirementsListModel rlm = RequirementsListModel.getInstance();
        final SimpleListObserver mySLO = new SimpleListObserver() {
            
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
