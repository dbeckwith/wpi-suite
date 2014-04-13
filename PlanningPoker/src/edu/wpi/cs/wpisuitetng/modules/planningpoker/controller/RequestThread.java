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

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * A class for requesting users from the server.
 * 
 * @author Sam Carlberg
 */
public class RequestThread extends Thread {
    
    private final AbstractUserController controller;
    
    private final RequestObserver observer = new RequestObserver() {
        @Override
        public void responseSuccess(IRequest iReq) {
            System.out.println("Got response success, waking controller"); //TODO remove
            notifyController();
        }
        
        @Override
        public void responseError(IRequest iReq) {
            System.out.println("Got response error, waking controller"); //TODO remove
            notifyController();
        }
        
        @Override
        public void fail(IRequest iReq, Exception exception) {
            System.out.println("Got response failure, "
                    + "waking controller to avoid deadlocks"); //TODO remove
            notifyController();
        }
        
        private void notifyController() {
            synchronized (controller) {
                controller.notifyAll();
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
        try {
            requestUsers();
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Sends the request. This blocks the controller's thread until a response
     * is received.
     */
    private void requestUsers() throws InterruptedException {
        synchronized (controller) {
            final Request request = Network.getInstance().makeRequest(
                    "core/user", HttpMethod.GET);
            request.addObserver(observer);
            request.addObserver(controller.observer);
            System.out.println("Sending request for user..."); //TODO remove
            request.send();
        }
    }
    
}
