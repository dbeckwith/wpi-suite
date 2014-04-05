package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UserPrefsController extends AbsUserController {

	/** The current user */
	private User user;

	private UserPrefsController() {
		super();
		requestUsers();
	}

	private static UserPrefsController instance;

	public static UserPrefsController getInstance() {
		if (instance == null) {
			instance = new UserPrefsController();
		}
		return instance;
	}

	@Override
	public void receivedUsers(User[] users) {
		if (users == null) {
			this.users = null;
			this.user = null;
			System.err.println("No users received");
		} else {
			this.users = users;
			this.user = findUser(ConfigManager.getConfig().getUserName());
			System.out.println("Current user = " + user.toJSON());// TODO remove
		}
	}

	/**
	 * Finds the user with the given username in an array of users.
	 * 
	 * @param name
	 *            the username to search for
	 * @param users
	 *            the array of users
	 * @return the user in the array with the given user name, or null if none
	 *         exists
	 */
	private User findUser(String name) {
		for (User u : users) {
			if (u.getName().equals(name)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Get the user currently logged in.
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	public boolean getNotifyByEmail() {
		return user.isNotifyByEmail();
	}

	public boolean getNotifyByIM() {
		return user.isNotifyByIM();
	}

	public void setNotifyByEmail(boolean notify) {
		sendPutRequest("email", notify);
	}

	public void setNotifyByIM(boolean notify) {
		sendPutRequest("im", notify);
	}

	private void sendPutRequest(String notificationType, boolean notify) {
		switch (notificationType) {
		case "email":
			user.setNotifyByEmail(notify);
			break;
		case "im":
			user.setNotifyByIM(notify);
			break;
		default:
			System.err.println("Invalid notification type " + notificationType);
			return;
		}
		final Request request = Network.getInstance().makeRequest("core/user",
				HttpMethod.PUT);
		request.setBody(user.toJSON());
		request.send();
	}

}
