/**
 * Holds the model for a planning poker deck
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

/**
 *
 */
public class DeckModel {
    private String name;
    private ArrayList<Integer> cards;
    
    public DeckModel(String name, ArrayList<Integer> cards) {
        this.name = name;
        this.cards = cards;
    }
    
    public DeckModel(String name) {
        this.name = name;
    }
    
    public void addCard(Integer newCard) {
        if (cards.contains(newCard)) { return; }
        cards.add(newCard);
    }
    
    public void removeCard(Integer card) {
        if (!cards.contains(card)) { return; }
        cards.remove(card);
    }
}
