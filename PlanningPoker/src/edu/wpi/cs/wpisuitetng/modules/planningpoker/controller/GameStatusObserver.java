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

/**
 * This observer is called when a response is received from a request
 * to the server to check game status.
 * @author Team 9
 * @version 1.0
 */
public interface GameStatusObserver {
    
    /**
     * Gets called when a game's status changes
     * 
     * @param game
     *        the game whose status changes
     */
    void statusChanged(GameModel game);
    
}
