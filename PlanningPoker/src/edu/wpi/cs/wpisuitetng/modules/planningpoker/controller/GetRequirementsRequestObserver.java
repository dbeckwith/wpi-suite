package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request
 * to the server to get requirements.
 * @author Team 9
 * @version 1.0
 */
public class GetRequirementsRequestObserver implements RequestObserver {
    
    private final GetRequirementsController controller;
    
    /**
     * Constructs the observer given a GetRequirementsController
     * 
     * @param controller
     *        the controller used to retrieve requirements
     */
    public GetRequirementsRequestObserver(GetRequirementsController controller) {
        this.controller = controller;
    }
    
    /**
     * Parse the requirements out of the response body and pass them to the
     * controller when a response is received with a success (2xx) status code.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Convert the JSON array of requirements to a Requirement object array
        final Requirement[] requirements = Requirement.fromJsonArray(iReq
                .getResponse().getBody());
        
        final ArrayList<GameRequirementModel> game_reqs = new ArrayList<>();
        
        for (Requirement req : requirements) {
            if (req.getIteration().equals("Backlog")) {
                game_reqs.add(new GameRequirementModel(req));
            }
        }
        
        // Pass these Requirements to the controller
        controller.receivedRequirements(game_reqs
                .toArray(new GameRequirementModel[0]));
    }
    
    /**
     * Reports an error when a response is received with an client error (4xx)
     * or server error (5xx) status code.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        fail(iReq, null);
    }
    
    /**
     * Put an error requirement in the PostBoardPanel if an attempt to make a
     * request fails.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest,
     *      java.lang.Exception)
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        final GameRequirementModel[] errorRequirement = { new GameRequirementModel(
                new Requirement(6, "Error", "error desc")) };
        controller.receivedRequirements(errorRequirement);
    }
    
}
