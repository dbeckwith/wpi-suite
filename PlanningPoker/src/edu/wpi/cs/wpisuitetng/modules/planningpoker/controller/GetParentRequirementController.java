package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class GetParentRequirementController extends
        AbstractRequirementController {
    
    private static GetParentRequirementController instance = null;
    private Requirement[] requirements;
    
    /**
     * Constructs the controller given a RequirementModel
     */
    private GetParentRequirementController() {
        super();
        retrieveRequirements();
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
        this.requirements = requirements;
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
        if (requirements == null) {
            System.out.println("Requirement list is null");
            return null;
        }
        else {
            for (Requirement r : requirements) {
                if (r.getId() == id) {
                    System.out.println("Found requirement " + r);
                    return r; 
                }
            }
        }
        System.out.println("Did not find requirement");
        return null;
    }
    
}
