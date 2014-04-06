package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

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
     * Sets the user to recieve or stop recieving email notifications.
     */
    public void setNotifyByEmail(boolean doNotify) {
        sendPutRequest("email", doNotify);
    }
    
    /**
     * Sets the user to recieve or stop recieving IM notifications.
     */
    public void setNotifyByIM(boolean doNotify) {
        sendPutRequest("im", doNotify);
    }
    
    /**
     * A helper method for setting notification preference in the user's
     * preferences.
     */
    private void sendPutRequest(String notificationType, boolean doNotify) {
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
