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
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Create Deck button by
 * storing new deck to remote server's database.
 * 
 * @author Team 9
 * @version 1.0
 */
public class AddDeckController {
    private static AddDeckController instance = null;
    private AddDeckRequestObserver observer;
    
    /**
     * Constructs a controller to add new decks
     */
    public AddDeckController() {
        setObserver(new AddDeckRequestObserver(this));
    }
    
    /**
     * Return the add deck controller or creates a new one
     * 
     * @return The instance of this AddDeckController
     */
    public static AddDeckController getInstance() {
        if (AddDeckController.instance == null) {
            AddDeckController.instance = new AddDeckController();
        }
        
        return AddDeckController.instance;
    }
    
    /**
     * Adds a new deck to the server
     * 
     * @param deck
     *        deck to be added
     */
    public void addDeck(DeckModel deck) {
        // PUT creates a new object
        final Request request = Network.getInstance().makeRequest("planningpoker/deck",
                HttpMethod.PUT);
        request.setBody(deck.toJSON());
        request.addObserver(observer);
        request.send();
    }
    
    /**
     * Returns observer for AddDeckController
     * 
     * @return observer of AddDeckController
     */
    public AddDeckRequestObserver getObserver() {
        return observer;
    }
    
    /**
     * Sets observer for AddDeckController
     * 
     * @param observer
     *        observer to be set for AddDeckController
     */
    public void setObserver(AddDeckRequestObserver observer) {
        this.observer = observer;
    }
}
