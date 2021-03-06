/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


/**
 * Controller for updating games
 * 
 * @author Team 9
 * @version 1.0
 */
public class UpdateGamesController {
    
    private static UpdateGamesController instance = null;
    private final UpdateGamesRequestObserver observer;
    
    /**
     * Construct an UpdateGamesController for the given model, view pair
     */
    private UpdateGamesController() {
        observer = new UpdateGamesRequestObserver();
    }
    
    /**
     * Get the instance of the UpdateGamesController or creates one if it
     * does not exist.
     * 
     * @return the instance of the UpdateGamesController
     */
    public static UpdateGamesController getInstance() {
        if (UpdateGamesController.instance == null) {
            UpdateGamesController.instance = new UpdateGamesController();
        }
        
        return UpdateGamesController.instance;
    }
    
    /**
     * This method updates a game on the server.
     * 
     * @param newGame
     *        is the game to be updated on the server.
     */
    public void updateGame(GameModel newGame) {
        final Request request = Network.getInstance().makeRequest("planningpoker/game",
                HttpMethod.POST);
        request.setBody(newGame.toJSON()); // put the new game in the body of
        // the request
        request.addObserver(observer); // add an observer to process the
                                       // response
        request.send();
    }
}
