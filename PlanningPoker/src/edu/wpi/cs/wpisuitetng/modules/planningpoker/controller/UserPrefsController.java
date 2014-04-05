package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class UserPrefsController extends AbsUserController {

	/** The current user */
	private User user;

	public UserPrefsController() {
		super();
		requestUsers();
	}

	@Override
	public void receivedUsers(User[] users) {
		if (users == null) {
			this.users = new User[0];
			this.user = null;
			System.err.println("No users received");
		} else {
			this.users = users;
			this.user = findUser(ConfigManager.getConfig().getUserName(), users);
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
	private User findUser(String name, User[] users) {
		System.out.println("Finding user with name " + name); //TODO remove
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
		user.setNotifyByEmail(notify);
	}

	public void setNotifyByIM(boolean notify) {
		user.setNotifyByIM(notify);
	}

}
