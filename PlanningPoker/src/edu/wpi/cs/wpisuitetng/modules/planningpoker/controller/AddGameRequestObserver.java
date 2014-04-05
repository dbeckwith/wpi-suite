/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This handles all game creation requests
 * 
 * @author Brett Ammeson
 * 
 */
public class AddGameRequestObserver implements RequestObserver {
    private AddGameController controller;
    
    /**
     * Constructs the observer given an AddRequirementController
     * 
     * @param controller
     *            the controller used to add requirements
     */
    public AddGameRequestObserver(AddGameController controller) {
        setController(controller);
    }
    
    /**
     * Parse the requirement that was received from the server then pass them to
     * the controller.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Get the response to the given request
        final ResponseModel response = iReq.getResponse();

		GetGamesController.getInstance().retrieveGames();
        
        // Parse the requirement out of the response body
        // final GameModel game = GameModel.fromJson(response.getBody());
    }
    
    /**
     * Takes an action if the response results in an error. Specifically,
     * outputs that the request failed.
     * 
     * @param iReq
     *            IRequest
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println("The request to add a game failed.");
    }
    
    /**
     * Takes an action if the response fails. Specifically, outputs that the
     * request failed.
     * 
     * @param iReq
     *            IRequest
     * @param exception
     *            Exception
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest,
     *      Exception)
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to add a game failed.");
    }
    
    /**
     * @return the controller
     */
    public AddGameController getController() {
        return controller;
    }
    
    /**
     * @param controller
     *            the controller to set
     */
    public void setController(AddGameController controller) {
        this.controller = controller;
    }
    
}
