package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This class waits for response from the server based on the
 * request sent by its EmailController
 * @author nfbrown, szhou, dcwethern
 *
 */
public class UserRequestObserver implements RequestObserver {
    
    private EmailController controller;
    private UserController userController;
    
    /**
     * Creates a new UserRequestController
     * @param controller The EmailController that will be used to call receivedUsers()
     */
    public UserRequestObserver(EmailController controller) {
        this.controller = controller;
    }
    
    public UserRequestObserver(UserController controller){
    	this.userController = controller;
    }

    @Override
    public void responseSuccess(IRequest iReq) {
        String response = iReq.getResponse().getBody();
        final Gson parser = new Gson();
        User[] users = parser.fromJson(response, User[].class);
        controller.receivedUsers(users); 
        userController.receivedUsers(users);
    }

    @Override
    public void responseError(IRequest iReq) {
        fail(iReq, null);
    }

    @Override
    public void fail(IRequest iReq, Exception exception) {
        controller.receivedUsers(null);
    }
    
}
