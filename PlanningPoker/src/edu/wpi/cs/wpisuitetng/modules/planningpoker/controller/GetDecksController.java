/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


/**
 * This controller is used to get decks from server
 * 
 * @author Team 9
 * @version 1.0
 */
public class GetDecksController {
    private final GetDecksRequestObserver observer;
    private static GetDecksController instance = null;
    
    /**
     * Returns a controller for getting decks
     */
    private GetDecksController() {
        observer = new GetDecksRequestObserver(this);
    }
    
    /**
     * Returns the GetDecksController instance
     * 
     * @return The instance of this GetDecksController
     */
    public static GetDecksController getInstance() {
        if (GetDecksController.instance == null) {
            GetDecksController.instance = new GetDecksController();
        }
        return GetDecksController.instance;
    }
    
    /**
     * Retrieves all decks
     */
    public void retrieveDecks() {
        // GET reads existing objects
        final Request request = Network.getInstance().makeRequest("planningpoker/deck",
                HttpMethod.GET);
        request.addObserver(observer);
        request.send();
    }
    
    /**
     * Adds received decks to the DeskListModel. This is called from the
     * GetDecksRequestObserver.
     * 
     * @param decks
     *        decks received
     */
    public static void receivedDecks(DeckModel[] decks) {
        DeckListModel.getInstance().emptyModel();
        if (decks != null) {
            DeckListModel.getInstance().addAllDecks(decks);
        }
    }
}
