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
 * @see UserPreferencesPanel
 * 
 * @author Sam Carlberg
 * 
 */
public class UserUpdateController {
    
    /**
     * The current user.
     */
    private final User user;
    
    /**
     * Default constructor.
     * 
     * @see CurrentUserController#CurrentUserController()
     */
    private UserUpdateController() {
        user = CurrentUserController.getInstance().getUser();
    }
    
    private enum FieldName {
        IM_NOTIFY, EMAIL_NOTIFY
    }
    
    /**
     * The instance of the controller.
     */
    private static UserUpdateController Instance;
    
    /**
     * Gets the instance of the controller.
     * 
     * @return the instance of the controller.
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
     * @return
     */
    public boolean getNotifyByEmail() {
        return user.isNotifyByEmail();
    }
    
    /**
     * Gets the user's current IM notification setting.
     */
    public boolean getNotifyByIM() {
        return user.isNotifyByIM();
    }
    
    /**
     * Sets the user to receive or stop receiving email notifications.
     * 
     * @param doNotify
     *        whether or not to recieve notifications.
     */
    public void setNotifyByEmail(boolean doNotify) {
        sendPostRequest(FieldName.EMAIL_NOTIFY, doNotify);
    }
    
    /**
     * Sets the user to receive or stop receiving IM notifications.
     * 
     * @param doNotify
     *        whether or not to recieve notifications.
     */
    public void setNotifyByIM(boolean doNotify) {
        sendPostRequest(FieldName.IM_NOTIFY, doNotify);
    }
    
    /**
     * A helper method for setting notification preference in the user's
     * preferences.
     */
    private <T> void sendPostRequest(FieldName fieldToUpdate, T newValue) {
        switch (fieldToUpdate) {
            case EMAIL_NOTIFY:
                user.setNotifyByEmail((Boolean) newValue);
                break;
            case IM_NOTIFY:
                user.setNotifyByIM((Boolean) newValue);
                break;
            default:
                return;
        }
        final Request request = Network.getInstance().makeRequest("core/user", //$NON-NLS-1$
                HttpMethod.POST);
        request.setBody(user.toJSON());
        request.send();
    }
    
    
}
