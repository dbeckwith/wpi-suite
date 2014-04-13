/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 *
 */
public class GetDecksRequestObserver implements RequestObserver {
    GetDecksController controller;
    
    /**
     * Creates a new get decks request observer
     * @param controller
     */
    public GetDecksRequestObserver(GetDecksController controller) {
        this.controller = controller;
    }
    
    /**
     * Parse the deck that was received from the server
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        DeckModel decks[] = DeckModel.fromJSONArray(iReq.getResponse().getBody());
        controller.receivedDecks(decks);
    }
    
    /**
     * Prints an error if addition unsuccessful
     */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println("The request to add a deck failed.");
    }
    
    /**
     * If addition fails
     */
    @Override
    public void fail(IRequest iReq, Exception e) {
        System.err.println("The request to add a deck failed.");
    }
    
    /**
     * Gets add deck controller
     * 
     * @return
     */
    public GetDecksController getController() {
        return controller;
    }
    
    /**
     * Sets add deck controller
     * 
     * @param c
     */
    public void setController(GetDecksController c) {
        controller = c;
    }
}
