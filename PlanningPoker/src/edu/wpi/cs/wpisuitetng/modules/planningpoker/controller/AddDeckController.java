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
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class AddDeckController {
    private static AddDeckController instance;
    private AddDeckRequestObserver observer;
    
    /**
     * Constructs a controller to add new decks
     */
    public AddDeckController() {
        setObserver(new AddDeckRequestObserver(this));
    }
    
    /**
     * Return the add deck controller or creates a new one
     */
    public static AddDeckController getInstance() {
        if (AddDeckController.instance == null) {
            AddDeckController.instance = new AddDeckController();
        }
        
        return AddDeckController.instance;
    }
    
    /**
     * Adds a new deck
     * 
     * @param deck
     */
    public void addDeck(DeckModel deck) {
        // PUT creates a new object
        final Request request = Network.getInstance().makeRequest(
                "planningpoker/deck", HttpMethod.PUT);
        request.setBody(deck.toJSON());
        request.addObserver(observer);
        request.send();
    }
    
    /**
     * Returns observer for AddDeckController
     * 
     * @return
     */
    public AddDeckRequestObserver getObserver() {
        return observer;
    }
    
    public void setObserver(AddDeckRequestObserver observer) {
        this.observer = observer;
    }
}
