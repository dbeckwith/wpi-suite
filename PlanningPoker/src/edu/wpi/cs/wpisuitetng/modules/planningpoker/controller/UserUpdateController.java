/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
    
    private User user;
    
    /**
     * Default constructor.
     * 
     * @see CurrentUserController#CurrentUserController()
     */
    private UserUpdateController() {
        user = CurrentUserController.getInstance().getUser();
    }
    
    private enum FieldName {
        IM_NOTIFY, EMAIL_NOTIFY, EMAIL_UPDATE
    }
    
    /**
     * The instance of the controller.
     */
    private static UserUpdateController Instance;
    
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
     * Gets the user's current email notification setting.
     * 
     * @return The User class email notification setting
     */
    public boolean canNotifyByEmail() {
        return user.isNotifyByEmail();
    }
    
    /**
     * Gets the user's current IM notification setting.
     * 
     * @return The User class IM notification setting
     */
    public boolean canNotifyByIM() {
        return user.isNotifyByIM();
    }
    
    /**
     * Sets the user to receive or stop receiving email notifications.
     * 
     * @param doNotify
     *        Boolean representing whether or not a User would like to receive
     *        email notifications
     */
    public void setNotifyByEmail(boolean doNotify) {
        sendPostRequest(FieldName.EMAIL_NOTIFY, doNotify);
    }
    
    /**
     * Sets the user to receive or stop receiving IM notifications.
     * 
     * @param doNotify
     *        Boolean representing whether or not a User would like to receive
     *        IM notifications
     */
    public void setNotifyByIM(boolean doNotify) {
        sendPostRequest(FieldName.IM_NOTIFY, doNotify);
    }
    
    /**
     * update email
     * @param e
     */
    public void updateEmail(String e) {
        sendPostRequest(FieldName.EMAIL_UPDATE, e);
    }
    
    /**
     * A helper method for setting notification preference in the user's
     * preferences.
     * 
     * @param fieldToUpdate
     *        Enum representing the different fields that a user can update from
     *        the preferences panel
     * @param newValue
     *        The new value of the field being updated
     */
    private <T> void sendPostRequest(FieldName fieldToUpdate, T newValue) {
        boolean flag = true;
        switch (fieldToUpdate) {
            case EMAIL_NOTIFY:
                user.setNotifyByEmail((Boolean) newValue);
                break;
            case IM_NOTIFY:
                user.setNotifyByIM((Boolean) newValue);
                break;
            case EMAIL_UPDATE:
                user.setEmail((String) newValue);
                break;
            default:
                System.err.println("Invalid notification type " + fieldToUpdate);
                flag = false;
                break;
        }
        if(flag) {
        final Request request = Network.getInstance().makeRequest("core/user", //$NON-NLS-1$
                HttpMethod.POST);
        request.setBody(user.toJSON());
        request.send();
        }
    }
    
    /**
     * A method that sets the current user for testing purposes..
     * 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
