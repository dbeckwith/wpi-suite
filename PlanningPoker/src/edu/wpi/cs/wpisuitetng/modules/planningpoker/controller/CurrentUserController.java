/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Sam Carlberg
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * A class for getting the User object associated with the user currently logged
 * into Janeway.
 * 
 * @author Sam Carlberg
 * 
 */
public class CurrentUserController extends AbstractUserController {
    
    /**
     * The username of the user currently logged in.
     */
    public static final String USER_NAME = ConfigManager.getConfig()
            .getUserName();
    private User user = null;
    
    private static CurrentUserController instance = null;
    
    public static CurrentUserController getInstance() {
        if (CurrentUserController.instance == null) {
            CurrentUserController.instance = new CurrentUserController();
        }
        
        return CurrentUserController.instance;
    }
    
    /**
     * Creates a new controller and requests users from the database.
     */
    protected CurrentUserController() {
        requestUsers();
    }
    
    @Override
    public void receivedUsers(User[] users) {
        if (users == null) {
            System.err.println("No users received");
        } else {
            setUsers(users);
            user = findUser(CurrentUserController.USER_NAME);
            System.out.println("Set user to " + user); // TODO remove
        }
    }
    
    /**
     * Finds the user with the given username.
     * 
     * @param name
     *            the username to search for
     * @return the user in the array with the given user name, or null if none
     *         exists
     */
    public User findUser(String name) {
        User toReturn = null;
        for (User u : getUsers()) {
            if (u.getUsername().equals(name)) {
                toReturn = u;
                break;
            }
        }
        return toReturn;
    }
    
    /**
     * Gets the currently logged in user.
     * 
     * @return
     */
    public User getUser() {
        return user;
    }
    
}
