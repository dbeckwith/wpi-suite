package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class GetParentRequirementController extends AbstractRequirementController {
    
    private static GetParentRequirementController instance = null;

    /**
     * Constructs the controller given a RequirementModel
     */
    private GetParentRequirementController() {
        super();
    }
    
    /**
    
     * @return the instance of the GetRequirementController or creates one if it does not
     * exist. */
    public static GetParentRequirementController getInstance()
    {
        if(instance == null)
        {
            instance = new GetParentRequirementController();
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
        
    }
    
}
