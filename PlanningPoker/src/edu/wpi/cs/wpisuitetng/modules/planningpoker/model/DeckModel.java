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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 *
 */
public class DeckModel extends AbstractModel {
	private String name;
	private ArrayList<Double> cards;
	private boolean allowsMultipleSelection;

	/**
	 * Creates a new deck
	 * 
	 * @param name
	 * @param cards
	 */
	public DeckModel(String name, ArrayList<Double> cards,
			boolean allowsMultipleSelection) {
		this.name = name;
		this.cards = cards;
		this.allowsMultipleSelection = allowsMultipleSelection;
	}

	/**
	 * Also creates a new deck
	 * 
	 * @param name
	 */
	public DeckModel(String name) {
		this.name = name;
		this.cards = null;
		this.allowsMultipleSelection = false;
	}

	/**
	 * Still creating a new deck, but THIS ONE is empty
	 */
	public DeckModel() {
		this.name = null;
		this.cards = null;
	}

	/**
	 * Adds a card to this deck
	 * 
	 * @param newCard
	 */
	public void addCard(Double newCard) {
		if (cards.contains(newCard)) {
			return;
		}
		cards.add(newCard);
	}

	/**
	 * Removes a card from this deck
	 * 
	 * @param card
	 */
	public void removeCard(Double card) {
		if (!cards.contains(card)) {
			return;
		}
		cards.remove(card);
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

	/**
	 * Turns this into a JSON object
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, DeckModel.class);
	}

	/**
	 * Creates a deck from a JSON object
	 * 
	 * @param json
	 * @return
	 */
	public static DeckModel fromJSON(String json) {
		final Gson parser = new Gson();
		DeckModel deck = parser.fromJson(json, DeckModel.class);
		return deck;
	}

	/**
	 * Creates an array of deck models
	 * 
	 * @param json
	 * @return
	 */
	public static DeckModel[] fromJSONArray(String json) {
		final Gson parser = new Gson();
		DeckModel[] decks = parser.fromJson(json, DeckModel[].class);
		return decks;
	}

	/**
	 * Returns the name of the deck
	 */
	@Override
	public String toString() {
		return name;
	}
}
