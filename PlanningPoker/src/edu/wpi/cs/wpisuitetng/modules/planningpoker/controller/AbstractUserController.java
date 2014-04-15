/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Sam Carlberg
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.concurrent.CountDownLatch;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * An abstract class for classes requesting users from the server.
 * 
 * @author Sam Carlberg
 * 
 */
public abstract class AbstractUserController {
    
    public final CountDownLatch latch = new CountDownLatch(1);
    
    final Object lock = new Object();
    
    /**
     * This controller's observer.
     */
    final UserRequestObserver observer;
    
    /**
     * The array of users in the project logged onto.
     */
    private User[] users = null;
    
    protected AbstractUserController() {
        observer = new UserRequestObserver(this);
    }
    
    /**
     * Called when the UserRequestObserver for this controller receives users
     * from the server.
     * 
     * @param users
     *        an array of users on the server. May be null.
     */
    public abstract void receivedUsers(User[] users);
    
    /**
     * Requests query of all users related to the project.
     * 
     * @see AbstractUserController#receivedUsers(User[])
     */
    public void requestUsers() {
        synchronized (this) {
            new RequestThread(this).start();
            try {
                System.out.println("Waiting for response");//TODO remove
                wait();
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Set the array of users in the current project.
     */
    public void setUsers(User[] users) {
        this.users = users;
    }
    
    /**
     * Gets the array of users in the current project.
     */
    public User[] getUsers() {
        return users;
    }
    
}
