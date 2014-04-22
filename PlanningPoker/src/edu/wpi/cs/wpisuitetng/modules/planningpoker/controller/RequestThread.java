/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * A class for requesting users from the server.
 * @author Team 9
 * @version 1.0
 */
public class RequestThread extends Thread {
    
    /**
     * The controller sending a request for a user.
     */
    private final AbstractUserController controller;
    
    /**
     * Responsible for waking the controller when the server sends a response,
     * otherwise the main thread could get deadlocked.
     */
    private final RequestObserver observer = new RequestObserver() {
        @Override
        public void responseSuccess(IRequest iReq) {
            notifyController();
        }
        
        @Override
        public void responseError(IRequest iReq) {
            notifyController();
        }
        
        @Override
        public void fail(IRequest iReq, Exception exception) {
            notifyController();
        }
        
        /**
         * Wakes the controller.
         */
        private void notifyController() {
            synchronized (controller) {
                System.out.println("Got response");
                controller.notifyAll();
                controller.setTimedOut(false);
            }
        }
    };
    
    /**
     * Creates a new thread that notifies the given controller.
     * 
     * @param controller
     *        the controller to nofity
     */
    public RequestThread(AbstractUserController controller) {
        this.controller = controller;
    }
    
    @Override
    public void run() {
        requestUsers();
    }
    
    /**
     * Sends the request. This blocks the controller's thread until a response
     * is received.
     */
    private void requestUsers() {
        final Request request = Network.getInstance().makeRequest("core/user",
                HttpMethod.GET);
        request.addObserver(observer);
        request.addObserver(controller.observer);
        System.out.println("Sending request for user..."); //TODO remove
        request.send();
    }
    
}
