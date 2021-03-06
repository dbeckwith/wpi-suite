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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;

/**
 * Used to notify all clients at once that a game has ended. This runs on the
 * server to prevent clients in different timezones or out-of-sync system clocks
 * from prematurely ending a game - therefore, <b>do not use this in client-side
 * code</b>.
 * 
 * @author Sam Carlberg
 * @version 1.0
 */
public class GameTimeoutObserver extends Thread {
    
    private static final ArrayList<GameTimeoutObserver> OBSERVERS = new ArrayList<>();
    
    /**
     * The game to end once the deadline has passed.
     */
    private final GameModel game;
    
    /**
     * The session we're in.
     */
    private final Session session;
    
    /**
     * How long to sleep before checking again if the game is over.
     */
    private final long sleepTime = 1000;
    
    /**
     * Signifies the time when a game ends on the server.
     */
    private Date endDate;
    
    private final SimpleDateFormat dateFormatGMT = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
    private final SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
    
    /**
     * Creates a new {@link GameTimeoutObserver}. Called when the
     * GameEntityManager creates a new GameModel on the server.
     * 
     * @see GameEntityManager#makeEntity(Session, String)
     * @param session
     * @param game
     */
    public GameTimeoutObserver(Session session, GameModel game) {
        super("GameTimeoutObserver-" + game.getID());
        dateFormatGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        this.session = session;
        this.game = game;
        GameTimeoutObserver.OBSERVERS.add(this);
        setDaemon(true);
    }
    
    @Override
    public void run() {
        endDate = toGMT(game.getEndTime());
        if (game.getStatus().equals(GameStatus.PENDING) && game.hasDeadline()) {
            while (!finished()) {
                try {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e) {
                    Logger.getGlobal().warning("GameTimeoutObserver interrupted");
                }
            }
            try {
                Logger.getGlobal().info("Deadline reached, ending game: " + game.toString());
                // clone the game so we don't change it in the database
                final GameModel clone = new GameModel();
                clone.copyFrom(game);
                clone.setEnded(true);
                GameEntityManager.getInstance().update(session, clone.toJSON());
            }
            catch (WPISuiteException e) {
                Logger.getGlobal().severe("Could not save ended game to database");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Check to see if the game has finished.
     */
    private boolean finished() { // $codepro.audit.disable booleanMethodNamingConvention
        return toGMT(new Date()).after(endDate);
    }
    
    
    /**
     * Gets the observer associated with the given game.
     * 
     * @param game
     * @return GameTimeoutObserver
     */
    public static GameTimeoutObserver getObserver(GameModel game) {
        boolean alreadyReturned = false;
        GameTimeoutObserver toReturn = null;
        for (GameTimeoutObserver o : GameTimeoutObserver.OBSERVERS) {
            if (o.game.equals(game)) {
                toReturn = o;
                alreadyReturned = true;
                Logger.getGlobal().info("Got observer for game " + game.getName());
                break;
            }
        }
        if (!alreadyReturned) {
            toReturn = null;
            Logger.getGlobal().warning("Could not get observer for game " + game.getName());
        }
        return toReturn;
    }
    
    /**
     * Converts a date to GMT.
     * 
     * @param date
     * @return
     */
    private Date toGMT(Date date) {
        Date toReturn = null;
        try {
            toReturn = dateFormatLocal.parse(dateFormatGMT.format(date));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    
}
