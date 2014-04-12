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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


public class GetRequirementsController {

    private GetRequirementsRequestObserver observer;
    private static GetRequirementsController instance;

    /**
     * Constructs the controller given a RequirementModel
     */
    private GetRequirementsController() {
        
        observer = new GetRequirementsRequestObserver(this);
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
     * Sends an HTTP request to retrieve all requirements
     */
    public void retrieveRequirements() {
        final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET); // GET == read
        request.addObserver(observer); // add an observer to process the response
        request.send(); // send the request
    }

    /**
     * Add the given requirements to the local model (they were received from the core).
     * This method is called by the GetRequirementsRequestObserver
     * 
     * @param requirements array of requirements received from the server
     */
    public void receivedRequirements(GameRequirementModel[] requirements) {
        // Empty the local model to eliminate duplications
        RequirementsListModel.getInstance().emptyModel();
        
        // Make sure the response was not null
        if (requirements != null) {
            
            // add the requirements to the local model
            RequirementsListModel.getInstance().addRequirements(requirements);
        }
    }
    
}
