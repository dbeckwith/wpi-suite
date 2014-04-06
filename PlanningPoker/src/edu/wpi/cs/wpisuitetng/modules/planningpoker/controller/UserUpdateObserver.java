package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class UserUpdateObserver implements RequestObserver {

private final UserPrefsController controller;
    
    /**
     * Constructs the observer given an AddRequirementController
     * @param controller the controller used to add requirements
     */
    public UserUpdateObserver(UserPrefsController controller) {
        this.controller = controller;
    }
    
    /**
     * Parse the user that was received from the server then pass them to
     * the controller.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Get the response to the given request
        String response = iReq.getResponse().getBody();
        
        final Gson parser = new Gson();
        
        // Parse the user out of the response body
        final User user = User.fromJSON(response);     
        controller.updateReceived(user);
    }
    
    /**
     * Takes an action if the response results in an error.
     * Specifically, outputs that the request failed.
     * @param iReq IRequest
    
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(IRequest) */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println(iReq.getResponse().getStatusMessage());
        System.err.println("The request to update a user failed.");
    }

    /**
     * Takes an action if the response fails.
     * Specifically, outputs that the request failed.
     * @param iReq IRequest
     * @param exception Exception
    
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest, Exception) */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to update a user failed.");
    }
    
}
