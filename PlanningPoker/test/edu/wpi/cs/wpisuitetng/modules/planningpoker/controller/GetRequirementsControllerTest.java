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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;

/**
 * 
 * @author Andrew
 * 
 */
public class GetRequirementsControllerTest {
    
    @Test
    public void testReceivedRequirements() {
        GameRequirementModel req1 = new GameRequirementModel(1,
                "Requirement 1", "Requirement 1 Description", "User Story");
        GameRequirementModel req2 = new GameRequirementModel(2,
                "Requirement 2", "Requirement 2 Description", "User Story");
        GameRequirementModel req3 = new GameRequirementModel(3,
                "Requirement 3", "Requirement 3 Description", "User Story");
        GameRequirementModel req4 = new GameRequirementModel(4,
                "Requirement 4", "Requirement 4 Description", "User Story");
        GameRequirementModel[] list = new GameRequirementModel[] { req1, req2,
                req3, req4 };
        GetRequirementsController.receivedRequirements(list);
        Assert.assertTrue(RequirementsListModel.getInstance().getAll()
                .contains(req1));
        Assert.assertTrue(RequirementsListModel.getInstance().getAll()
                .contains(req2));
        Assert.assertTrue(RequirementsListModel.getInstance().getAll()
                .contains(req3));
        Assert.assertTrue(RequirementsListModel.getInstance().getAll()
                .contains(req4));
    }
    
}
