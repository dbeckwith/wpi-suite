/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker.controller;

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
    
    private AbstractUserController controller;
    
    /**
     * Creates a new UserRequestController
     * @param controller The EmailController that will be used to call receivedUsers()
     */
    public UserRequestObserver(AbstractUserController controller) {
        this.controller = controller;
    }

    @Override
    public void responseSuccess(IRequest iReq) {
        Gson gson;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(User.class, new UserDeserializer());
        gson = builder.create();
        
        String response = iReq.getResponse().getBody();
        User[] users = gson.fromJson(response, User[].class);
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
