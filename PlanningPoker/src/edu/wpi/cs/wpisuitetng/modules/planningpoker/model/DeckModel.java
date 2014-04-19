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
import java.util.Collections;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * This class is meant to represent a deck of cards that the user can pick from
 * for making estimates to a game.
 */
public class DeckModel extends AbstractModel {
	private String name;
	private ArrayList<Double> cards;
	private boolean allowsMultipleSelection;
	private boolean isNone;
    
    /**
     * Creates a new deck.
     * 
     * @param name
     *            the name of the deck
     * @param cards
     *            list of cards for the deck
     * @param allowsMultipleSelection
     *            whether or not this deck should allow multiple selection
     */
	public DeckModel(String name, ArrayList<Double> cards,
			boolean allowsMultipleSelection) {
		this.name = name;
		this.cards = cards;
		this.allowsMultipleSelection = allowsMultipleSelection;
		this.isNone = false;
	}

    /**
     * Creates a new deck without any cards that does not allow multiple
     * selection.
     * 
     * @param name
     *            the name of the deck
     */
	public DeckModel(String name) {
		this.name = name;
		this.cards = null;
		this.allowsMultipleSelection = false;
        this.isNone = false;
	}
    
    /**
     * Creates a new prototype empty deck without a name. This constructor
     * should generally not be used except in databse queries.
     */
    public DeckModel() {
        this.name = null;
        this.cards = null;
        this.allowsMultipleSelection = false;
        this.isNone = false;
    }

	/**
	 * Gets the name of this deck.
	 * 
	 * @return the name of the deck
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the list of cards in this deck.
	 * 
	 * @return The cards in the deck
	 */
	public ArrayList<Double> getCards() {
		return this.cards;
	}
    
    /**
     * Adds a card to this deck. Duplicates are not allowed, so if the given
     * value is already in this deck, this method does nothing
     * 
     * @param newCard
     *            the value of the card to add
     */
	public void addCard(Double newCard) {
		if (cards.contains(newCard)) {
			return;
		}
		cards.add(newCard);
	}

	/**
	 * Removes a card from this deck.
	 * 
	 * @param card the value to remove, if it is in the deck
	 */
	public void removeCard(Double card) {
		if (!cards.contains(card)) {
			return;
		}
		cards.remove(card);
	}

	/**
	 * Sorts the cards in the deck in ascending order.
	 */
	public void sort() {
		Collections.sort(cards);
	}

	@Override
	public void delete() {
	}

	@Override
	public Boolean identify(Object arg0) {
		return null;
	}

	@Override
	public void save() {
	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this, DeckModel.class);
	}

	/**
	 * Creates a deck from a JSON object.
	 * 
	 * @param json
	 *            The JSON string
	 * @return The DeckModel from the JSON string
	 */
	public static DeckModel fromJSON(String json) {
		final Gson parser = new Gson();
		DeckModel deck = parser.fromJson(json, DeckModel.class);
		return deck;
	}

	/**
	 * Creates an array of deck models from a JSONArray.
	 * 
	 * @param json
	 * @return Array of DeckModels from the JSON array
	 */
	public static DeckModel[] fromJSONArray(String json) {
		final Gson parser = new Gson();
		DeckModel[] decks = parser.fromJson(json, DeckModel[].class);
		return decks;
	}
	
	@Override
	public String toString() {
		return name;
	}

    /**
     * Gets whether this deck is a None deck or not. A None deck has no cards in
     * it, but should instead allow the user to enter whatever estimate they
     * want.
     * 
     * @return true if this deck is a None deck, false otherwise
     */
	public boolean getIsNone() {
		return isNone;
	}

    /**
     * Gets whether multiple card selection is allowed for this deck. This means
     * that the user can select mutiple cards from the deck and sum their values
     * to make their estimate.
     * 
     * @return true if this deck allows multiple selection, false otherwise
     */
	public boolean getAllowsMultipleSelection() {
		return this.allowsMultipleSelection;
	}
}
