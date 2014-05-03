/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Sam Carlberg, Ted Armstrong
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A controller for allowing the current user to change personal preferences.
 * Currently only allows for notification preferences, but can be expanded in
 * the future.
 * 
 * @author Team 9
 * @version 1.0
 * @see UserPreferencesPanel
 */
public class UserUpdateController {
    
    /**
     * Default constructor.
     */
    private UserUpdateController() {
    }
    
    /**
     * The instance of the controller.
     */
    private static UserUpdateController Instance = null;
    
    /**
     * Returns the instance of this UserUpdateController or creates a new one
     * 
     * @return The instance of this UserUpdateController
     */
    public static UserUpdateController getInstance() {
        if (Instance == null) {
            Instance = new UserUpdateController();
        }
        return Instance;
    }
    
    /**
     * Updates the given user object on the server.
     * 
     * @param user
     *        the user to update on the server
     */
    public void updateUser(User user) { // $codepro.audit.disable methodShouldBeStatic -->
        System.out.println("Sending update to user " + user.getName());
        final Request request = Network.getInstance().makeRequest("core/user", //$NON-NLS-1$
                HttpMethod.POST);
        request.setBody(user.toJSON());
        request.send();
    }
}
