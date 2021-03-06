/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 * @version 1.0
 */
public class GetRequirementsControllerTest {
    /**
     * test ReceivedRequirements
     */
    @Test
    public void testReceivedRequirements() {
        final GetRequirementsController grc = GetRequirementsController.getInstance();
        final Requirement req1 = new Requirement(1, "Requirement 1", "Requirement 1 Description");
        final Requirement req2 = new Requirement(2, "Requirement 2", "Requirement 2 Description");
        final Requirement req3 = new Requirement(3, "Requirement 3", "Requirement 3 Description");
        final Requirement req4 = new Requirement(4, "Requirement 4", "Requirement 4 Description");
        final Requirement[] list = new Requirement[] { req1, req2, req3, req4 };
        grc.receivedRequirementsTesting(list);
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
