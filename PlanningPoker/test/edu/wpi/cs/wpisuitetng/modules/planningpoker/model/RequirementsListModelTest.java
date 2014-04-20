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
 * 
 * @author Andrew
 * 
 */
public class RequirementsListModelTest {
    
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream oldOut = System.out;
    
    @Before
    public void prepareStreams() {
        System.setOut(new PrintStream(outStream));
    }
    
    @After
    public void cleanUpStreams() {
        System.setOut(oldOut);
    }
    
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
