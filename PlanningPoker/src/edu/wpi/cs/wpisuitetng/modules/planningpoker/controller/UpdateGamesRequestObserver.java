/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * TODO: Contributors' names
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;
/**
 * This handles updating of games
 * TODO @author Brett Ammeson
 *
 */
public class UpdateGamesRequestObserver implements RequestObserver {
    
    private final UpdateGamesController controller;
    
    /**
     * Constructs the observer given an UpdateGamesController
     * 
     * @param controller
     *            the controller used to add games
     */
    public UpdateGamesRequestObserver(UpdateGamesController controller) {
        this.controller = controller;
    }
    
    /**
     * Parse the game that was received from the server then pass them to the
     * controller.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Get the response to the given request
        final ResponseModel response = iReq.getResponse();
        
        // Parse the game out of the response body
        // final GameModel game = GameModel.fromJson(response.getBody());
    }
    
    /**
     * Takes an action if the response results in an error. Specifically,
     * outputs that the request failed.
     * 
     * @param iReq
     *            IRequest
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        System.err.println(iReq.getResponse().getStatusMessage());
        System.err.println("The request to update a game failed.");
    }
    
    /**
     * Takes an action if the response fails. Specifically, outputs that the
     * request failed.
     * 
     * @param iReq
     *            IRequest
     * @param exception
     *            Exception
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest,
     *      Exception)
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        System.err.println("The request to update a game failed.");
    }
}
