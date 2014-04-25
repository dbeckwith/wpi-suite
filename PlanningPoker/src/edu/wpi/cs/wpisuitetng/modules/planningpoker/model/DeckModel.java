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
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * This class is meant to represent a deck of cards that the user can pick from
 * for making estimates to a game.
 * 
 * @author Team 9
 * @version 1.0
 */
public class DeckModel extends AbstractModel {

	public static final DeckModel DEFAULT_DECK;

	public static final int NO_LIMIT = 0;

	static {
		final ArrayList<Double> defaultCards = new ArrayList<Double>();

		defaultCards.add(0d);
		defaultCards.add(1d);
		defaultCards.add(1d);
		defaultCards.add(2d);
		defaultCards.add(3d);
		defaultCards.add(5d);
		defaultCards.add(8d);
		defaultCards.add(13d);

		DEFAULT_DECK = new DeckModel("Default Deck", defaultCards, true);
	}

	private final String name;
	private final List<Double> cards;
	private final boolean allowsMultipleSelection;
	private final boolean isNone;
	private final int maxEstimate;

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
	public DeckModel(String name, List<Double> cards,
			boolean allowsMultipleSelection) {
		this.name = name;
		this.cards = cards;
		isNone = (cards == null || cards.size() == 0);

		this.allowsMultipleSelection = allowsMultipleSelection;
		maxEstimate = NO_LIMIT;
	}

	/**
	 * Creates a new deck without any cards that does not allow multiple
	 * selection.
	 * 
	 * 
	 * @param max
	 *            int
	 */
	public DeckModel(int max) {
		name = "None";
		cards = null;
		allowsMultipleSelection = false;
		isNone = true;
		maxEstimate = Math.max(max, NO_LIMIT);
	}

	/**
	 * Creates a new prototype empty deck without a name. This constructor
	 * should generally not be used except in databse queries.
	 */
	public DeckModel() {
		name = null;
		cards = null;
		allowsMultipleSelection = false;
		isNone = false;
		maxEstimate = NO_LIMIT;
	}

	/**
	 * Gets the name of this deck.
	 * 
	 * @return the name of the deck
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the list of cards in this deck.
	 * 
	 * @return The cards in the deck
	 */
	public List<Double> getCards() {
		return cards;
	}

	/**
	 * Gets whether this deck is a None deck or not. A None deck has no cards in
	 * it, but should instead allow the user to enter whatever estimate they
	 * want.
	 * 
	 * @return true if this deck is a None deck, false otherwise
	 */
	public boolean isNone() {
		return isNone;
	}

	/**
	 * Gets the maximum custom estimate that can be entered for this deck
	 * 
	 * @return maximum estimate , NO_LIMIT if there isnt one
	 */
	public int getMaxEstimate() {
		return maxEstimate;
	}

	/**
	 * Adds a card to this deck. Duplicates are not allowed, so if the given
	 * value is already in this deck, this method does nothing
	 * 
	 * @param newCard
	 *            the value of the card to add
	 */
	public void addCard(Double newCard) {
		if (!cards.contains(newCard)) {
			cards.add(newCard);
		}
	}

	/**
	 * Removes a card from this deck.
	 * 
	 * @param card
	 *            the value to remove, if it is in the deck
	 */
	public void removeCard(Double card) {
		if (cards.contains(card)) {
			cards.remove(card);
		}
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
	 * 
	 * @return The DeckModel from the JSON string
	 */
	public static DeckModel fromJSON(String json) {
		final Gson parser = new Gson();
		final DeckModel deck = parser.fromJson(json, DeckModel.class);
		return deck;
	}

	/**
	 * Creates an array of deck models from a JSONArray.
	 * 
	 * @param json
	 * 
	 * @return Array of DeckModels from the JSON array
	 */
	public static DeckModel[] fromJSONArray(String json) {
		final Gson parser = new Gson();
		final DeckModel[] decks = parser.fromJson(json, DeckModel[].class);
		return decks;
	}

	@Override
	public String toString() {
		return "" + name;
	}

	/**
	 * Gets whether multiple card selection is allowed for this deck. This means
	 * that the user can select mutiple cards from the deck and sum their values
	 * to make their estimate.
	 * 
	 * 
	 * @return true if this deck allows multiple selection, false otherwise
	 */
	public boolean canAllowsMultipleSelection() {
		return allowsMultipleSelection;
	}
}
