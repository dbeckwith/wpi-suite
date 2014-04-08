/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

/**
 *
 */
public class DeckListModel extends AbstractListModel<DeckModel> {
    private static DeckListModel instance;
    /**
     * 
     */
    private static final long serialVersionUID = 3193186441179194894L;
    private ArrayList<DeckModel> decks;
    
    public static DeckListModel getInstance() {
        if (DeckListModel.instance == null) {
            DeckListModel.instance = new DeckListModel();
        }
        
        return DeckListModel.instance;
    }
    
    public void emptyModel() {
        decks.clear();
    }
    
    /**
     * Adds a new deck to the list
     * 
     * @param deck
     */
    public void addDeck(DeckModel deck) {
        decks.add(deck);
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
     * Gets length of decks
     */
    @Override
    public int getSize() {
        return decks.size();
    }
}
