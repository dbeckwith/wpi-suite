package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.ArrayList;

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
public class UserPrefsController extends CurrentUserController {
    
    private final ArrayList observers = new ArrayList<>();
    
    /**
     * Default constructor.
     * 
     * @see CurrentUserController#CurrentUserController()
     */
    public UserPrefsController() {
        super();
    }
    
    /**
     * Gets the user's current email notification setting.
     * @return
     */
    public boolean getNotifyByEmail() {
        return getUser().isNotifyByEmail();
    }
    
    /**
     * Gets the user's current IM notification setting.
     */
    public boolean getNotifyByIM() {
        return getUser().isNotifyByIM();
    }
    
    /**
     * Sets the user to receive or stop receiving email notifications.
     */
    public void setNotifyByEmail(boolean doNotify) {
        sendPostRequest("email", doNotify);
    }
    
    /**
     * Sets the user to receive or stop receiving IM notifications.
     */
    public void setNotifyByIM(boolean doNotify) {
        sendPostRequest("im", doNotify);
    }
    
    /**
     * A helper method for setting notification preference in the user's
     * preferences.
     */
    private void sendPostRequest(String notificationType, boolean doNotify) {
        switch (notificationType) {
            case "email":
                getUser().setNotifyByEmail(doNotify);
                break;
            case "im":
                getUser().setNotifyByIM(doNotify);
                break;
            default:
                System.err.println("Invalid notification type "
                        + notificationType);
                return;
        }
        final Request request = Network.getInstance().makeRequest("core/user",
                HttpMethod.POST);
        request.setBody(getUser().toJSON());
        request.send();
        System.out.println("Updated: " + getUser());// TODO remove
    }
    
    
}
