package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.core.models.UserDeserializer;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This class waits for response from the server based on the
 * request sent by its UserController
 * @author nfbrown, szhou, dcwethern
 *
 */
public class UserRequestObserver implements RequestObserver {
    
    private AbsUserController controller;
    
    /**
     * Creates a new UserRequestController
     * @param controller The EmailController that will be used to call receivedUsers()
     */
    public UserRequestObserver(AbsUserController controller) {
        this.controller = controller;
    }

    @Override
    public void responseSuccess(IRequest iReq) {
        Gson gson;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(User.class, new UserDeserializer());
        gson = builder.create();
        
        String response = iReq.getResponse().getBody();
        System.out.println("Server: " + response);
        User[] users = gson.fromJson(response, User[].class);
        System.out.println("Parsed: " + Arrays.toString(users));
        controller.receivedUsers(users);  
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
