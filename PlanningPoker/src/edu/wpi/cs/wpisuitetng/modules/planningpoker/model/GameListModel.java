/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * A model for keeping track of all the games in the database as a list of
 * GameModels.
 * 
 * @author Team 9
 * @version 1.0
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

	private final List<GameModel> games;
	private final List<SimpleListObserver> observers;
	private final List<GameStatusObserver> status_observers;
	private final GameStatusObserver game_observer;

	private GameListModel() {
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
	 * changed.
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
	 * Adds a GameStatusObserver to the list of status observers which will be
	 * notified when any of the games in the list of games changes its status.
	 * 
	 * @see GameModel#addStatusListener(GameStatusObserver)
	 * @param gso
	 *            the status observer to add
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
	 * the list.
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
	}

	/**
	 * Gets a list of all the games currently in the database.
	 * 
	 * @return the list of games
	 */
	public List<GameModel> getGames() {
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
	public int getSize() {
		return games.size();
	}

	@Override
	public GameModel getElementAt(int index) {
		return games.get(index);
	}

	/**
	 * Gets all the list observers currently registered with the list model.
	 * 
	 * @return the simplelistobservers for the list of games
	 */
	public List<SimpleListObserver> getObservers() {
		return observers;
	}

	/**
	 * Remove all observers from the model.
	 */
	public void removeObservers() {
		observers.clear();
	}

	/**
	 * Remove all game status observers from the model.
	 */
	public void removeStatusObservers() {
		status_observers.clear();
	}

}
