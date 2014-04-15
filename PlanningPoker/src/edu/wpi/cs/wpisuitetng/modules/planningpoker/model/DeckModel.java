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
import java.util.Comparator;

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
     *        The name of the deck
     * @param cards
     *        List of cards for the deck
     * @param allowsMultipleSelection
     *        Whether or not this deck should allow multiple selection
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
     * @return The cards in the deck
     */
    public ArrayList<Double> getCards() {
        return this.cards;
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
        if (cards.contains(newCard)) { return; }
        cards.add(newCard);
    }
    
    /**
     * Removes a card from this deck
     * 
     * @param card
     */
    public void removeCard(Double card) {
        if (!cards.contains(card)) { return; }
        cards.remove(card);
    }
    
    /**
     * Sorts the cards in the deck in ascending order
     */
    public void sort() {
        Collections.sort(cards, new Comparator<Double>() {
            public int compare(Double a, Double b) {
                if (a == b) {
                    return 0;
                }
                else if (a > b) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
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
     * 
     * @return The JSON string for this object
     */
    @Override
    public String toJSON() {
        return new Gson().toJson(this, DeckModel.class);
    }
    
    /**
     * Creates a deck from a JSON object
     * 
     * @param json
     *        The JSON string
     * @return The DeckModel from the JSON string
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
     * @return Array of DeckModels from the JSON array
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
    
    /**
     * Returns whether multiple card selection is allowed
     */
    public boolean getAllowsMultipleSelection() {
        return this.allowsMultipleSelection;
    }
}
