/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request
 * to the server to get decks.
 * @author Team 9
 * @version 1.0
 */
public class GetDecksRequestObserver implements RequestObserver {
    GetDecksController controller;
    
    /**
     * Creates a new get decks request observer
     * 
     * @param controller
     *        GetDecksRequestObserver to be created
     */
    public GetDecksRequestObserver(GetDecksController controller) {
        this.controller = controller;
    }
    
    /**
     * Parse the deck when a response is received with a success (2xx) status
     * code.
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        final DeckModel[] decks = DeckModel.fromJSONArray(iReq.getResponse()
                .getBody());
        GetDecksController.receivedDecks(decks);
    }
    
    /**
     * Prints an error when a response is received with an client error (4xx) or
     * server error (5xx) status code.
     */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println("The request to add a deck failed.");
    }
    
    /**
     * print an error if an attempt to make a request fails.
     */
    @Override
    public void fail(IRequest iReq, Exception e) {
        System.err.println("The request to add a deck failed.");
    }
    
    /**
     * Gets add deck controller
     * 
     * @return controller to be get
     */
    public GetDecksController getController() {
        return controller;
    }
    
    /**
     * Sets add deck controller
     * 
     * @param c
     *        controller to be set
     */
    public void setController(GetDecksController c) {
        controller = c;
    }
}
