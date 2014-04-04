package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * An interface for classes wishing to recieve users from the server.
 * 
 * @author Sam Carlberg
 * 
 */
public interface IUserController {

	/**
	 * Called when the UserRequestObserver for this controller recieves users
	 * from the server.
	 * 
	 * @param users
	 *            an array of users on the server. May be null.
	 */
	public void receivedUsers(User[] users);

}
