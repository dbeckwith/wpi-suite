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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameTimeoutWatcher;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * Stores a list of games and their statuses
 * 
 * @author Akshay, Andrew
 * 
 */
public class GameListModel extends AbstractListModel<GameModel> {
    
    private static GameListModel instance;
    
    /**
     * Retrieves the singleton GameListModel
     * 
     * @return Only GameListModel instance
     */
    public static GameListModel getInstance() {
        if (GameListModel.instance == null) {
            GameListModel.instance = new GameListModel();
        }
        return GameListModel.instance;
    }
    
    private static final long serialVersionUID = -4216338772150454616L;
    
    private final ArrayList<GameModel> games;
    private final ArrayList<SimpleListObserver> observers;
    private final ArrayList<GameStatusObserver> status_observers;
    private final GameStatusObserver game_observer;
    
    /**
     * Constructor that initializes list of games, list of observers, a
     * controller to service DB retrieval requests, and a timer to periodically
     * refresh the list of games.
     */
    public GameListModel() {
        games = new ArrayList<>();
        observers = new ArrayList<>();
        status_observers = new ArrayList<>();
        game_observer = new GameStatusObserver() {
            
            @Override
            public void statusChanged(GameModel game) {
                for (GameStatusObserver gso : status_observers) {
                    gso.statusChanged(game);
                }
            }
        };
    }
    
    /**
     * Add a SimpleListObserver that is notified when the list of games is
     * changed
     * 
     * @param slo
     *            The SimpleListObserver to add
     */
    public void addListListener(SimpleListObserver slo) {
        if (!observers.contains(slo)) {
            observers.add(slo);
        }
    }
    
    /**
     * Adds a GameStatusObserver to the list of status observers
     *
     * @param gso
     */
    public void addStatusListener(GameStatusObserver gso) {
        if (!status_observers.contains(gso)) {
            status_observers.add(gso);
        }
    }
    
    /**
     * Add a game to the list
     * 
     * @param g
     *            the game to add
     */
    public void addGame(GameModel g) {
        addAndRegisterGame(g);
        updated();
    }
    
    /**
     * Add multiple games to the list. The purpose of this method is that it
     * will add multiple games at once and fire only one update on the list.
     * 
     * @param gs
     *            the list of games to add
     */
    public void addMultipleGames(GameModel[] gs) {
        for (GameModel g : gs) {
            addAndRegisterGame(g);
        }
        updated();
    }
    
    /**
     * Removes a game from the list. Doesn't do anything if the game is not in
     * the list
     * 
     * @param g
     *            The game to remove
     */
    public void removeGame(GameModel g) {
        if (games.contains(g)) {
            removeAndUnregisterGame(g);
            updated();
        }
    }
    
    /**
     * Empties the list of games.
     */
    public void emptyModel() {
        final int numGames = games.size();
        for (int i = 0; i < numGames; i++) {
            removeAndUnregisterGame(games.get(0));
        }
        updated();
    }
    
    /**
     * Sets the list of games to only contain the given games. The purpose of
     * this method is to be able to clear the model and add multiple games to it
     * and fire only one list updated event.
     * 
     * @param gs
     *            the games to be included in the list model
     */
    public void setGames(GameModel[] gs) {
        final int numGames = games.size();
        for (int i = 0; i < numGames; i++) {
            removeAndUnregisterGame(games.get(0));
        }
        for (GameModel g : gs) {
            addAndRegisterGame(g);
        }
        updated();
    }
    
    /**
     * Adds the given game to the list and registers it with any listeners,
     * watchers, etc. that are required.
     * 
     * @param g
     */
    private void addAndRegisterGame(GameModel g) {
        games.add(g);
        g.addStatusListener(game_observer);
        if (!g.isEnded()) {
            // if the game is still going
            // watch for when it ends
            GameTimeoutWatcher.getInstance().watchGame(g);
        }
    }
    
    /**
     * Removes the given game from the list and unregisters it from an
     * listeners, watchers, etc.
     * 
     * @param g
     */
    private void removeAndUnregisterGame(GameModel g) {
        games.remove(g);
        g.removeStatusListener(game_observer);
        GameTimeoutWatcher.getInstance().stopWatchingGame(g);
    }
    
    /**
     * @return the list of games
     */
    public ArrayList<GameModel> getGames() {
        return games;
    }
    
    /**
     * Notifies all observers when that the list has changed
     */
    private void updated() {
        for (SimpleListObserver observer : observers) {
            observer.listUpdated();
        }
    }
    
    @Override
    /**
     * @return the size of the list of games
     */
    public int getSize() {
        return games.size();
    }
    
    @Override
    /**
     * @param index
     *      the index of the element to retrieve in the list of games
     * @return the game in the list at index
     */
    public GameModel getElementAt(int index) {
        return games.get(index);
    }
    
    /**
     * 
     * @return the simplelistobservers for the list of games
     */
    public ArrayList<SimpleListObserver> getObservers() {
        return observers;
    }
    
    /**
     * Remove all observers for the model
     */
    public void removeObservers(){
        observers.clear();
    }
    
    /**
     * Remove all game status observers for the model
     */
    public void removeStatusObservers(){
        status_observers.clear();
    }
    
}
