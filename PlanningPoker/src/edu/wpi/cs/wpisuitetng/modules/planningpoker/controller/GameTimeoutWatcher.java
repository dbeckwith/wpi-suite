/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;

/**
 * The purpose of this class is to schedule tasks in the future for checking
 * games to see if they have passed their end date.
 * @author Team 9
 * @version 1.0
 */
public class GameTimeoutWatcher {
    
    private static GameTimeoutWatcher instance = null;
    
    /**
     * Returns the instance of this GameTimeoutWatcher or creates a new one
     * 
     * @return The instance of this GameTimeoutWatcher
     */
    public static GameTimeoutWatcher getInstance() {
        if (GameTimeoutWatcher.instance == null) {
            GameTimeoutWatcher.instance = new GameTimeoutWatcher();
        }
        
        return GameTimeoutWatcher.instance;
    }
    
    private final Timer timer;
    private final ArrayList<GameTimeoutTask> activeTasks;
    
    private GameTimeoutWatcher() {
        timer = new Timer("GameTimeoutWatcher timer");
        activeTasks = new ArrayList<GameTimeoutTask>();
    }
    
    /**
     * Adds a game to be watched for completion at its end date.
     * 
     * @param game
     *        the game to be watched
     */
    public void watchGame(GameModel game) {
        if (game.getEndTime() != null) {
            final GameTimeoutTask task = new GameTimeoutTask(game);
            // schedule with timer to run 500 ms after end time
            timer.schedule(task, new Date(game.getEndTime().getTime() + 500));
            // add to list of active tasks
            activeTasks.add(task);
        }
    }
    
    /**
     * Stop watching a game and forget about it.
     * 
     * @param game
     *        the game to be stopped watching
     */
    public void stopWatchingGame(GameModel game) {
        // look for the task that is watching the given game
        GameTimeoutTask associatedTask = null;
        for (GameTimeoutTask task : activeTasks) {
            if (task.getGame().equals(game)) {
                associatedTask = task;
                break;
            }
        }
        if (associatedTask != null) {
            associatedTask.cancel(); // stop the task
            activeTasks.remove(associatedTask); // forget about it
            timer.purge(); // tell the timer to forget about it
        }
    }
    
}
