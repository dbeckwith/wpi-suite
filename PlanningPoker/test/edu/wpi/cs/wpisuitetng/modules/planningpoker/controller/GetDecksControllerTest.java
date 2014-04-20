package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.junit.Assert;
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
        DeckModel deck1 = new DeckModel("Deck 1");
        DeckModel deck2 = new DeckModel("Deck 2");
        DeckListModel.getInstance().removeObservers();
        GetDecksController.receivedDecks(new DeckModel[] { deck1, deck2 });
        Assert.assertTrue(DeckListModel.getInstance().getDecks()
                .contains(deck1));
        Assert.assertTrue(DeckListModel.getInstance().getDecks()
                .contains(deck2));
    }
    
}
