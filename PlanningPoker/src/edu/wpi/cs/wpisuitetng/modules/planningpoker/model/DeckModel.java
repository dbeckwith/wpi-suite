/**
 * Holds the model for a planning poker deck
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
 *
 */
public class DeckModel extends AbstractModel {
    private String name;
    private ArrayList<Integer> cards;
    
    public DeckModel(String name, ArrayList<Integer> cards) {
        this.name = name;
        this.cards = cards;
    }
    
    public DeckModel(String name) {
        this.name = name;
        this.cards = null;
    }

    public DeckModel() {
        this.name = null;
        this.cards = null;
    }
    
    public void addCard(Integer newCard) {
        if (cards.contains(newCard)) { return; }
        cards.add(newCard);
    }
    
    public void removeCard(Integer card) {
        if (!cards.contains(card)) { return; }
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

    @Override
    public String toJSON() {
        return new Gson().toJson(this, DeckModel.class);
    }
    
    public static DeckModel fromJSON(String json) {
        final Gson parser = new Gson();
        DeckModel deck = parser.fromJson(json,  DeckModel.class);
        return deck;
    }

    public static DeckModel[] fromJSONArray(String json) {
        final Gson parser = new Gson();
        DeckModel[] decks = parser.fromJson(json,  DeckModel[].class);
        return decks;
    }
}
