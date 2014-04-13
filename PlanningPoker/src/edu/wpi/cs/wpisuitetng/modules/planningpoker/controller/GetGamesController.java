/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Brett Ammeson, Andrew Han
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
 * This handles requests for games
 * 
 * @author Brett Ammeson, Andrew Han
 */
public class GetGamesController implements ActionListener {
    private GetGamesRequestObserver observer;
    private static GetGamesController instance;
    
    /**
     * Constructs the controller given a GameModel
     */
    private GetGamesController() {
        
        observer = new GetGamesRequestObserver(this);
        NotificationClient.getInstance().start();
    }
    
    /**
     * @return the instance of the GetGameController or creates one if it does
     *         not exist.
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
     *            ActionEvent
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
        final Request request = Network.getInstance().makeRequest(
                "planningpoker/game", HttpMethod.GET); // GET == read
        request.addObserver(observer); // add an observer to process the
                                       // response
        request.send(); // send the request
    }
    
    /**
     * Add the given games to the local model (they were received from the
     * core). This method is called by the GetGamesRequestObserver
     * 
     * @param games
     *            array of games received from the server
     */
    public void receivedGames(GameModel[] games) {
        // Make sure the response was not null
        if (games != null) {
            // if (games[0].getID() != -1) {
            // add the games to the local model
            GameListModel.getInstance().setGames(games);
            // }
        }
    }
}
