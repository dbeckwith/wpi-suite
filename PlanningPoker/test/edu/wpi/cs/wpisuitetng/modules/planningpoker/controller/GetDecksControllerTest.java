package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;

/**
 * 
 * @author Andrew
 *
 */
public class GetDecksControllerTest {
    
    @Test
    public void test() {
        GetDecksController gdc = GetDecksController.getInstance();
        DeckModel deck1 = new DeckModel("Deck 1");
        DeckModel deck2 = new DeckModel("Deck 2");
        DeckListModel.getInstance().removeObservers();
        gdc.receivedDecks(new DeckModel[] {deck1, deck2});
        assertTrue(DeckListModel.getInstance().getDecks().contains(deck1));
        assertTrue(DeckListModel.getInstance().getDecks().contains(deck2));
    }
    
}
