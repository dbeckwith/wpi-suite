package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class UserController extends AbstractUserController {
	
	public UserController(){
		
	}
	@Override
	public void receivedUsers(User[] users) {
		 if (users != null) {
			 setUsers(users);
	     }
	     else {
	         System.err.println("Users not received properly");
	     }
	}
}