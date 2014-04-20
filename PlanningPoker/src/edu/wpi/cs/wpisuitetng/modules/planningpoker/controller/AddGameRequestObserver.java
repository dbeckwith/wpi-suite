/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request
 * to the server to add a game.
 * 
 * @author Team 9
 * @version 1.0
 */
public class AddGameRequestObserver implements RequestObserver {
    private AddGameController controller;
    
    /**
     * Constructs the observer for an AddRequirementController
     * 
     * @param controller
     *        the controller used to add requirements
     */
    public AddGameRequestObserver(AddGameController controller) {
        setController(controller);
    }
    
    /**
     * Parse the requirement when a response is received with a success (2xx)
     * status code. Then pass them to
     * the controller.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
    }
    
    /**
     * Report error when a response is received with an client error (4xx) or
     * server error (5xx) status code.
     * 
     * @param iReq
     *        IRequest
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println("The request to add a game failed.");
    }
    
    /**
     * Report error Takes an action if an attempt to make a request fails.
     * 
     * @param iReq
     *        IRequest
     * @param exception
     *        Exception
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest,
     *      Exception)
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to add a game failed.");
    }
    
    /**
     * Gets AddGameController
     * 
     * @return the controller served by this observer
     */
    public AddGameController getController() {
        return controller;
    }
    
    /**
     * Sets AddGameController
     * 
     * @param controller
     *        the controller to be set
     */
    public void setController(AddGameController controller) {
        this.controller = controller;
    }
    
}
