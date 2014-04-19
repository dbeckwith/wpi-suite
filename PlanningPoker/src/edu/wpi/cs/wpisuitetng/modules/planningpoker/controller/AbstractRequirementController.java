package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Abstract requirement controller to be extended by other classes that need to
 * receive requirements
 * 
 * @author nfbrown
 * @version Apr 18, 2014
 */
public abstract class AbstractRequirementController {
    
    final GetRequirementsRequestObserver observer;
    
    private Requirement[] reqs = null;
    
    protected AbstractRequirementController() {
        observer = new GetRequirementsRequestObserver(this);
    }
    
    public void retrieveRequirements() {
        final Request request = Network.getInstance().makeRequest(
                "requirementmanager/requirement", HttpMethod.GET); // GET == read
        request.addObserver(observer); // add an observer to process the response
        request.send(); // send the request
    }
    
    public abstract void receivedRequirements(Requirement[] reqs);
    
}
