// $codepro.audit.disable
// com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString
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
    
    /**
     * The current user.
     */
    private User user = null;
    
    /**
     * The instance of the controller.
     */
    private static CurrentUserController Instance = null;
    
    /**
     * Gets the instance of the controller.
     * 
     * @return the instance of the controller.
     */
    public static CurrentUserController getInstance() {
        if (Instance == null) {
            Instance = new CurrentUserController();
        }
        
        return Instance;
    }
    
    /**
     * Creates a new controller and requests users from the database.
     */
    private CurrentUserController() {
        requestUsers();
    }
    
    @Override
    public void receivedUsers(User[] users) {
        if (users != null) {
            setUsers(users);
            user = findUser(USER_NAME);
        }
        else {
            System.err.println("No users received!");//TODO remove
        }
        System.out.println("User = " + user);//TODO remove
    }
    
    /**
     * Finds the user with the given username.
     * 
     * @param name
     *        the username to search for
     * @return the user in the array with the given user name, or null if none
     *         exists
     */
    public User findUser(String name) {
        for (User u : getUsers()) {
            if (u.getUsername().equals(name))
                return u;
        }
        return null;
    }
    
    /**
     * Gets the currently logged in user.
     */
    public User getUser() {
        return user;
    }
    
}
