/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * nfbrown
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This is a thread that handles making a request for requirements. It blocks
 * the main thread until a response is received or a timeout occurs in the main
 * thread. If a response is received before the timeout, it notifies the main
 * thread to continue executing.
 * 
 * @author nfbrown
 * @version Apr 21, 2014
 */
public class RequirementRequestThread extends Thread {
    
    /**
     * The controller sending a request for a user.
     */
    private final AbstractRequirementController controller;
    
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
                controller.notifyAll();
                controller.setTimedOut(false);
            }
        }
    };
    
    /**
     * Creates a new thread that notifies the given controller.
     * 
     * @param controller
     *        the controller to notify
     */
    public RequirementRequestThread(AbstractRequirementController controller) {
        super("Requirement Request Thread");
        this.controller = controller;
    }
    
    @Override
    public void run() {
        requestRequirements();
    }
    
    /**
     * Sends the request. This blocks the controller's thread until a response
     * is received.
     */
    private void requestRequirements() {
        final Request request = Network.getInstance().makeRequest("requirementmanager/requirement",
                HttpMethod.GET);
        request.addObserver(observer);
        request.addObserver(controller.observer);
        Logger.getGlobal().info("Sent request for requirements");
        request.send();
    }
    
}
