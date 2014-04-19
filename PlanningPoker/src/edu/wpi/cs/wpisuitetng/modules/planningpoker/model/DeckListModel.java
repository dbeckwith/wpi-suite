/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 *
 */
public class DeckListModel extends AbstractListModel<DeckModel> {
	private DeckModel defaultDeck;
	private static DeckListModel instance;
	private ArrayList<SimpleListObserver> observers = new ArrayList<SimpleListObserver>();
	private static final long serialVersionUID = 3193186441179194894L;
	private ArrayList<DeckModel> decks = new ArrayList<DeckModel>();

	/**
	 * Returns the instance of this DeckListModel or creates a new one
	 * 
	 * @return The instance of this DeckListModel
	 */
	public static DeckListModel getInstance() {
		if (DeckListModel.instance == null) {
			DeckListModel.instance = new DeckListModel();
		}

		return DeckListModel.instance;
	}

	/**
	 * Empties the list of decks
	 */
	public void emptyModel() {
		decks.clear();
	}

	/**
	 * Adds default deck
	 */
	public void setDefaultDeck(DeckModel defaultDeck) {
		this.defaultDeck = defaultDeck;
	}

	/**
	 * Gets default deck
	 */
	public DeckModel getDefaultDeck() {
		return defaultDeck;
	}

	/**
	 * Adds a new deck to the list
	 * 
	 * @param deck
	 */
	public void addDeck(DeckModel deck) {
		decks.add(deck);

		for (int i = 0; i < observers.size(); i++){
			observers.get(i).listUpdated();
		}
	}
	
	/**
	 * Remove all observers for the model
	 */
	public void removeObservers(){
        observers.clear();
    }

	/**
	 * Adds an observer to this model
	 * 
	 * @param o
	 */
	public void addObserver(SimpleListObserver o) {
		observers.add(o);
	}

	/**
	 * Gets element at index
	 * 
	 * @param index
	 */
	@Override
	public DeckModel getElementAt(int index) {
		return decks.get(index);
	}

	/**
	 * Returns all decks[]
	 * @return The list of decks
	 */
	public ArrayList<DeckModel> getDecks() {
		return decks;
	}

	/**
	 * Gets length of decks
	 * @return The size of the list of decks
	 */
	@Override
	public int getSize() {
		return decks.size();
	}
}
