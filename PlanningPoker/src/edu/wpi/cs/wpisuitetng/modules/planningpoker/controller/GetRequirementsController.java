package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds by sending request to get requirements from remote
 * server.
 * @author Team 9
 * @version 1.0
 */
public class GetRequirementsController {
    
    private final GetRequirementsRequestObserver observer;
    private static GetRequirementsController instance = null;
    
    /**
     * Constructs the controller given a RequirementModel
     */
    private GetRequirementsController() {
        
        observer = new GetRequirementsRequestObserver(this);
    }
    
    /**
     * Get the instance of the GetRequirementsController or creates one if it
     * does not exist.
     * 
     * @return the instance of the GetRequirementController
     */
    public static GetRequirementsController getInstance() {
        if (instance == null) {
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
     * Add the given requirements to the local model (they were received from
     * the core).
     * This method is called by the GetRequirementsRequestObserver
     * 
     * @param requirements
     *        array of requirements received from the server
     */
    public void receivedRequirements(GameRequirementModel[] requirements) {
        // Make sure the response was not null
        if (requirements != null) {
            
            // set the requirements to the local model
            RequirementsListModel.getInstance().setRequirements(requirements);
        }
    }
    
}
