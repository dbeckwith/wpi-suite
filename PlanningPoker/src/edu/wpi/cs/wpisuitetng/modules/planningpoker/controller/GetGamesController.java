/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.NotificationClient;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This controller responds by sending request to get games from remote server.
 * 
 * @author Team9
 * @version 1.0
 */
public class GetGamesController implements ActionListener {
    private final GetGamesRequestObserver observer;
    private static GetGamesController instance = null;
    
    /**
     * Constructs the controller given a GameModel
     */
    private GetGamesController() {
        
        observer = new GetGamesRequestObserver(this);
        NotificationClient.getInstance().start();
    }
    
    /**
     * get the instance of the GetGameControlleror creates one if it does
     * not exist.
     * 
     * @return the instance of the GetGameController
     */
    public static GetGamesController getInstance() {
        if (GetGamesController.instance == null) {
            GetGamesController.instance = new GetGamesController();
        }
        
        return GetGamesController.instance;
    }
    
    /**
     * Sends an HTTP request to store a game when the update button is pressed
     * 
     * @param e
     *        ActionEvent
     * 
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        retrieveGames();
    }
    
    /**
     * Sends an HTTP request to retrieve all games
     */
    public void retrieveGames() {
        final Request request = Network.getInstance().makeRequest("planningpoker/game",
                HttpMethod.GET);
        request.addObserver(observer); // add an observer to process the
                                       // response
        request.send(); // send the request
    }
    
    /**
     * Add the given games to the local model (they were received from the
     * core). This method is called by the GetGamesRequestObserver
     * 
     * @param games
     *        array of games received from the server
     */
    public void receivedGames(GameModel[] games) { // $codepro.audit.disable methodShouldBeStatic
        // Make sure the response was not null
        if (games != null) {
            // add the games to the local model
            GameListModel.getInstance().setGames(games);
        }
    }
}
