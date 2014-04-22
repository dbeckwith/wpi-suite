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

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * This handles requests for game requirements
 * TODO: @author 
 *
 */
public class GetRequirementsController extends AbstractRequirementController {

    private static GetRequirementsController instance = null;

    /**
     * Constructs the controller given a RequirementModel
     */
    private GetRequirementsController() {
        super();
    }
    
    /**
    
     * @return the instance of the GetRequirementController or creates one if it does not
     * exist. */
    public static GetRequirementsController getInstance()
    {
        if(instance == null)
        {
            instance = new GetRequirementsController();
        }
        
        return instance;
    }
    

    /**
     * Add the given requirements to the local model (they were received from the core).
     * This method is called by the GetRequirementsRequestObserver
     * 
     * @param requirements array of requirements received from the server
     */
    public void receivedRequirements(Requirement[] requirements) {
     // Make sure the response was not null
        if (requirements != null) {
            ArrayList<GameRequirementModel> gameReqs = new ArrayList<GameRequirementModel>();
            for (int i = 0; i < requirements.length; i++) {
                if (requirements[i].getIteration().equals("Backlog")) {
                    gameReqs.add(new GameRequirementModel(requirements[i]));
                }
            }
            GameRequirementModel[] toSet = new GameRequirementModel[requirements.length];
            // set the requirements to the local model
            RequirementsListModel.getInstance().setRequirements(gameReqs.toArray(toSet));
        }
    }
    
}
