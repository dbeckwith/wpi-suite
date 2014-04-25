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
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * A model for storing all the decks currently in the database.
 * 
 * @author Team 9
 * @version 1.0
 */
public class DeckListModel extends AbstractListModel<DeckModel> {
	private static DeckListModel instance = null;
	private final List<SimpleListObserver> observers = new ArrayList<SimpleListObserver>();
	private static final long serialVersionUID = 3193186441179194894L;
	private final List<DeckModel> decks = new ArrayList<DeckModel>();

    /**
     * Returns the instance of this DeckListModel or creates a new one
     * 
     * @return the instance of this DeckListModel
     */
	public static DeckListModel getInstance() {
		if (DeckListModel.instance == null) {
			DeckListModel.instance = new DeckListModel();
		}

		return DeckListModel.instance;
	}

	/**
	 * Empties the list of decks.
	 */
	public void emptyModel() {
		decks.clear();
	}


    
    /**
     * Adds a new deck to the list.
     * 
     * @param deck
     *            the deck to add
     */
	public void addDeck(DeckModel deck) {
		decks.add(deck);

		for (int i = 0; i < observers.size(); i++){
			observers.get(i).listUpdated();
		}
	}
	
	public void addAllDecks(DeckModel[] newDecks){
		for(int i = 0; i < newDecks.length; i++){
			decks.add(newDecks[i]);
		}
		
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
     * Adds an observer to this model which will be notified whenever a deck is
     * added or removed.
     * 
     * @param o
     *            the observer to add
     */
	public void addObserver(SimpleListObserver o) {
		observers.add(o);
	}

	@Override
	public DeckModel getElementAt(int index) {
		return decks.get(index);
	}

	/**
	 * Gets the list of all decks currently being stored.
	 * @return the list of decks
	 */
	public List<DeckModel> getDecks() {
		return decks;
	}

	@Override
	public int getSize() {
		return decks.size();
	}
}
