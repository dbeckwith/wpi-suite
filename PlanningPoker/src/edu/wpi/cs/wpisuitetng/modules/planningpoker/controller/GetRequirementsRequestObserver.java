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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class GetRequirementsRequestObserver implements RequestObserver {
    
    private GetRequirementsController controller;
    
    /**
     * Constructs the observer given a GetRequirementsController
     * 
     * @param controller
     *            the controller used to retrieve requirements
     */
    public GetRequirementsRequestObserver(GetRequirementsController controller) {
        this.controller = controller;
    }
    
    /**
     * Parse the requirements out of the response body and pass them to the
     * controller
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Convert the JSON array of requirements to a Requirement object array
        Requirement[] requirements = Requirement.fromJsonArray(iReq
                .getResponse().getBody());
        
        GameRequirementModel[] game_reqs = new GameRequirementModel[requirements.length];
        
        for (int i = 0; i < requirements.length; i++) {
            game_reqs[i] = new GameRequirementModel(requirements[i]);
        }
        
        // Pass these Requirements to the controller
        controller.receivedRequirements(game_reqs);
    }
    
    /**
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        fail(iReq, null);
    }
    
    /**
     * Put an error requirement in the PostBoardPanel if the request fails.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest,
     *      java.lang.Exception)
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        GameRequirementModel[] errorRequirement = { new GameRequirementModel(
                new Requirement(6, "Error", "error desc")) };
        controller.receivedRequirements(errorRequirement);
    }
    
}
