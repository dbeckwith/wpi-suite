/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Sam Carlberg
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.Date;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications.NotificationServer;

/**
 * Used to notify all clients at once that a game has ended. This runs on the
 * server to prevent clients in different timezones or out-of-sync system clocks
 * from prematurely ending a game - therefore, <b>do not use this in client-side
 * code</b>.
 * 
 * @author Sam Carlberg
 * 
 */
public class GameTimeoutObserver extends Thread {
    
    private final GameModel game;
    private final Session session;
    
    /**
     * Signifies the time when a game ends on the server.
     */
    private final Date endDate;
    
    /**
     * Creates a new {@link GameTimeoutObserver}. Called when the
     * GameEntityManager creates a new GameModel on the server.
     * 
     * @see GameEntityManager#makeEntity(Session, String)
     */
    public GameTimeoutObserver(Session session, GameModel game) {
        this.session = session;
        this.game = game;
        endDate = new Date();
        endDate.setTime(endDate.getTime() + game.timeAlive());
        System.out.println("Game " + game + "\n\tends at: " + endDate);
        System.out.println("\tTime alive = " + game.timeAlive() + "ms");
    }
    
    @Override
    public void run() {
        if (game.timeAlive() > 0) {
            while (!finished()) {
                try {
                    sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                System.out.println("Deadline reached, ending game: "
                        + game.toString());
                game.setEnded(true);
                GameEntityManager.getInstance().update(session, game.toJSON());
                NotificationServer.getInstance().sendUpdateNotification();
            }
            catch (WPISuiteException e) {
                System.err.println("Could not save ended game to database");
                e.printStackTrace();
            }
        }
        else {
            System.out
                    .println("Game has no deadline, not running timeout observer.");
        }
    }
    
    /**
     * Check to see if the game has finished.
     */
    private boolean finished() {
        return new Date().after(endDate);
    }
    
}
