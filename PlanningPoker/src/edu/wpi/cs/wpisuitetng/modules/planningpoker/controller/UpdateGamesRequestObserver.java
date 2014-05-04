/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;


/**
 * This observer is called when a response is received from a request
 * to the server to update games.
 * @author Team 9
 * @version 1.0
 */
public class UpdateGamesRequestObserver implements RequestObserver {
    
    
    
    /**
     * Parse the game that was received from the server then pass them to the
     * controller when a response is received with a success (2xx) status code.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess
     * (edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
    }
    
    /**
     * Reports an error when a response is received with an client error (4xx)
     * or server error (5xx) status code.
     * 
     * @param iReq
     *        IRequest
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        Logger.getGlobal().severe("The request to update a game failed: " + 
                                    iReq.getResponse().getStatusMessage());
    }
    
    /**
     * Report an error if an attempt to make a request fails.
     * 
     * @param iReq
     *        IRequest
     * @param exception
     *        Exception
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest,
     *      Exception)
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        Logger.getGlobal().severe("The request to update a game failed");
    }
}
