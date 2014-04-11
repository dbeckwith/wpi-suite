/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * TODO: Contributors' names
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * An abstract class for classes requesting users from the server.
 * 
 * @author Sam Carlberg
 * 
 */
public abstract class AbstractUserController {
    
    private final UserRequestObserver observer;
    private User[] users = null;
    
    public AbstractUserController() {
        observer = new UserRequestObserver(this);
    }
    
    /**
     * Called when the UserRequestObserver for this controller recieves users
     * from the server.
     * 
     * @param users
     *        an array of users on the server. May be null.
     */
    public abstract void receivedUsers(User[] users);
    
    /**
     * Requests query of all users related to the project.
     * 
     * @see AbstractUserController#receivedUsers(User[])
     */
    protected void requestUsers() {
        final Request request = Network.getInstance().makeRequest("core/user",
                HttpMethod.GET);
        request.addObserver(observer); // add an observer to process the
                                       // response
        request.send(); // send the request
    }
    
    /**
     * Set the array of users in the current project.
     */
    public void setUsers(User[] users) {
        this.users = users;
    }
    
    /**
     * Gets the array of users in the current project.
     */
    public User[] getUsers() {
        return users;
    }
    
}
