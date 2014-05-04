/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request
 * to the server to add a deck.
 * 
 * @author Team 9
 * @version 1.0
 */
public class AddDeckRequestObserver implements RequestObserver {
    private AddDeckController controller;
    
    /**
     * Construct an observer for an AddDeckRequestController
     * 
     * @param controller
     *        the controller that the observer serves
     */
    public AddDeckRequestObserver(AddDeckController controller) {
        setController(controller);
    }
    
    
    /**
     * Parse the deck when a response is received with a success (2xx) status
     * code.
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        GetDecksController.getInstance().retrieveDecks();
    }
    
    /**
     * Prints an error when a response is received with an client error (4xx) or
     * server error (5xx) status code.
     */
    @Override
    public void responseError(IRequest iReq) {
        Logger.getGlobal().severe("The request to add a deck failed");
    }
    
    /**
     * Prints an error if an attempt to make a request fails.
     */
    @Override
    public void fail(IRequest iReq, Exception e) {
        Logger.getGlobal().severe("The request to add a deck failed");
    }
    
    /**
     * Gets AddDeckController
     * 
     * @return AddDeckController served by this observer
     */
    public AddDeckController getController() {
        return controller;
    }
    
    /**
     * Sets AddDeckController
     * 
     * @param c
     *        AddDeckController to be set
     */
    public void setController(AddDeckController c) {
        controller = c;
    }
}
