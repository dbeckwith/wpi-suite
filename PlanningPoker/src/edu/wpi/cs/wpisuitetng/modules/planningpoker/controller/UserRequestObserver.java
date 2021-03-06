/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.core.models.UserDeserializer;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This class waits for response from the server based on the
 * request sent by its UserController
 * 
 * @author Team 9
 * @version 1.0
 */
public class UserRequestObserver implements RequestObserver {
    
    /**
     * The controller that will be used to call receivedUsers().
     */
    private final AbstractUserController controller;
    
    /**
     * Creates a new UserRequestController
     * 
     * @param controller
     *        The EmailController that will be used to call receivedUsers()
     */
    public UserRequestObserver(AbstractUserController controller) {
        this.controller = controller;
    }
    
    @Override
    public void responseSuccess(IRequest iReq) {
        final Gson gson;
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(User.class, new UserDeserializer());
        gson = builder.create();
        
        final String response = iReq.getResponse().getBody();
        final User[] users = gson.fromJson(response, User[].class);
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
