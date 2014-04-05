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
public abstract class AbsUserController {

	UserRequestObserver observer;
	User[] users;

	public AbsUserController() {
		observer = new UserRequestObserver(this);
	}

	/**
	 * Called when the UserRequestObserver for this controller recieves users
	 * from the server.
	 * 
	 * @param users
	 *            an array of users on the server. May be null.
	 */
	public abstract void receivedUsers(User[] users);

	/**
	 * Requests query of all users related to the project
	 */
	protected void requestUsers() {
		final Request request = Network.getInstance().makeRequest("core/user",
				HttpMethod.GET);
		request.addObserver(observer); // add an observer to process the response
		request.send(); // send the request
	}

}
