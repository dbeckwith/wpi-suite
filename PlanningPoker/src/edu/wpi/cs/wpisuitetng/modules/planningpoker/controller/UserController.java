package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UserController{
	
	 private UserRequestObserver observer;
	 private User[] users;
	 
	 public UserController(){
		 observer = new UserRequestObserver(this);
		 requestUsers();
	 }
	 /**
	  * Requests query of all users related to the project
	  */
	 private void requestUsers() {
	     final Request request = Network.getInstance().makeRequest("core/user",
	             HttpMethod.GET);
	     request.addObserver(observer); // add an observer to process the response
	     request.send(); // send the request
	 }
	    
	 /**
	  * Sets the users list to the users received by the network
	  * 
	  * @param users
	  *        The list of users received by UserRequestController
	  */
	 public void receivedUsers(User[] users) {
	     if (users != null) {
	         this.users = users;
	         //parseEmails();
	     }
	     else {
	         // TODO: error handling
	     }
	 }
	 
	 public User[] getUsers(){
		 return users;
	 }
}