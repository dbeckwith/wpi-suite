/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;

/**
 * Tests the GetDecksController class
 * 
 * @author Team 9
 * @version 1.0
 */
public class GetDecksControllerTest {
    
    /**
     * Tests that DeckListModel correctly contains decks after they've been received
     */
    @Test
    public void testReceivedDecks() {
        final DeckModel deck1 = new DeckModel("Deck 1");
        final DeckModel deck2 = new DeckModel("Deck 2");
        DeckListModel.getInstance().removeObservers();
        GetDecksController.receivedDecks(new DeckModel[] { deck1, deck2 });
        Assert.assertTrue(DeckListModel.getInstance().getDecks()
                .contains(deck1));
        Assert.assertTrue(DeckListModel.getInstance().getDecks()
                .contains(deck2));
    }
    
}
