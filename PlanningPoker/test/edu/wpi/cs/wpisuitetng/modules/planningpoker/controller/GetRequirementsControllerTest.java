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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * 
 * @author Andrew
 * 
 */
public class GetRequirementsControllerTest {
    
    @Test
    public void testReceivedRequirements() {
        GetRequirementsController grc = GetRequirementsController.getInstance();
        Requirement req1 = new Requirement(1,
                "Requirement 1", "Requirement 1 Description");
        Requirement req2 = new Requirement(2,
                "Requirement 2", "Requirement 2 Description");
        Requirement req3 = new Requirement(3,
                "Requirement 3", "Requirement 3 Description");
        Requirement req4 = new Requirement(4,
                "Requirement 4", "Requirement 4 Description");
        Requirement[] list = new Requirement[] { req1, req2,
                req3, req4 };
        grc.receivedRequirements(list);
        Assert.assertTrue(RequirementsListModel.getInstance().getAll()
                .contains(new GameRequirementModel(req1)));
        Assert.assertTrue(RequirementsListModel.getInstance().getAll()
                .contains(new GameRequirementModel(req2)));
        Assert.assertTrue(RequirementsListModel.getInstance().getAll()
                .contains(new GameRequirementModel(req3)));
        Assert.assertTrue(RequirementsListModel.getInstance().getAll()
                .contains(new GameRequirementModel(req4)));
    }
    
}
