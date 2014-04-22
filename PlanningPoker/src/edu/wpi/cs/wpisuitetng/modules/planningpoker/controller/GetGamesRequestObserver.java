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
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This observer is called when a response is received from a request
 * to the server to get games.
 * @author Team 9
 * @version 1.0
 */
public class GetGamesRequestObserver implements RequestObserver {
    
    private final GetGamesController controller;
    
    /**
     * Constructs the observer given a GetGamesController
     * 
     * @param controller
     *        the controller used to retrieve games
     */
    public GetGamesRequestObserver(GetGamesController controller) {
        this.controller = controller;
    }
    
    /**
     * Parse the games out of the response body and pass them to the controller
     * when a response is received with a success (2xx) status code.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Convert the JSON array of games to a Games object array
        final GameModel[] games = GameModel.fromJSONArray(iReq.getResponse()
                .getBody());
        
        // Pass these Games to the controller
        controller.receivedGames(games);
    }
    
    /**
     * Reports an error when a response is received with an client error (4xx)
     * or
     * server error (5xx) status code.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        fail(iReq, null);
    }
    
    /**
     * Put an error games in the PostBoardPanel if an attempt to make a request
     * fails.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest,
     *      java.lang.Exception)
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        // TODO: Needs to be updated with a GameModel indicating an error
        final GameModel[] errorGames = { new GameModel() };
        controller.receivedGames(errorGames);
    }
}
