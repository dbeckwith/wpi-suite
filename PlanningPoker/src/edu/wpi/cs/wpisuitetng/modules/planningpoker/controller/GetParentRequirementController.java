/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * nfbrown
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * This controller is used to get the parent requirement for a
 * GameRequirementModel
 * 
 * @author nfbrown
 * @version Apr 21, 2014
 */
public class GetParentRequirementController extends
        AbstractRequirementController {
    
    private static GetParentRequirementController instance = null;
    
    /**
     * Constructs the controller given a RequirementModel
     */
    private GetParentRequirementController() {
        super();
        requestRequirements();
    }
    
    /**
     * 
     * @return the instance of the GetRequirementController or creates one if it
     *         does not
     *         exist.
     */
    public static GetParentRequirementController getInstance() {
        if (instance == null) {
            instance = new GetParentRequirementController();
        }
        
        return instance;
    }
    
    /**
     * Add the given requirements to the local model (they were received from
     * the core).
     * This method is called by the GetRequirementsRequestObserver
     * 
     * @param requirements
     *        array of requirements received from the server
     */
    public void receivedRequirements(Requirement[] requirements) {
        if (requirements != null) {
            setRequirements(requirements);
        }
        else {
            System.out.println("Requirements list is null");
        }
    }
    
    /**
     * Given the ID of a GameRequirementModel, finds the parent ID from the
     * requirement manager
     * 
     * @param id
     *        GameRequirementModel ID
     * @return the requirement from the requirement manager or null if the
     *         requirement does not exist in the requirement manager
     */
    public Requirement getParentRequirement(int id) {
        requestRequirements();
        if (getRequirements() == null) {
            return null;
        }
        else {
            for (Requirement r : getRequirements()) {
                if (r.getId() == id) { return r; }
            }
            return null;
        }
    }
    
}
