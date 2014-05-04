/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Brett Ammeson
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds when the user clicks the Create Game button by
 * storing new game to remote server's database.
 * 
 * @author team9
 * @version 1.0
 */
public class AddGameController {
    private static AddGameController instance = null;
    private AddGameRequestObserver observer;
    
    /**
     * Construct an AddGameController for the given model, view pair
     */
    public AddGameController() {
        setObserver(new AddGameRequestObserver(this));
    }
    
    /**
     * Gets instance of the AddGameController or creates one if it does not
     * exist.
     * 
     * @return the instance of the AddGameController
     */
    public static AddGameController getInstance() {
        if (AddGameController.instance == null) {
            AddGameController.instance = new AddGameController();
        }
        
        return AddGameController.instance;
    }
    
    /**
     * This method adds a game to the server.
     * 
     * @param newGame
     *        game to be added.
     */
    public void addGame(GameModel newGame) {
        final Request request = Network.getInstance().makeRequest("planningpoker/game",
                HttpMethod.PUT); // PUT ==
                                 // create
        request.setBody(newGame.toJSON()); // put the new game in the body of
                                           // the request
        request.addObserver(observer); // add an observer to process the
                                       // response
        request.send();
    }
    
    /**
     * Get the observer for the controller
     * 
     * @return observer for the controller
     */
    public AddGameRequestObserver getObserver() {
        return observer;
    }
    
    
    /**
     * Set the observer for the controller
     * 
     * @param observer
     *        observer to be set
     */
    public void setObserver(AddGameRequestObserver observer) {
        this.observer = observer;
    }
}
