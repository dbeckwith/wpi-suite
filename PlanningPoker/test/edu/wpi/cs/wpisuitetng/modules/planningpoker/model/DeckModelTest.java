/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the DeckModel class
 * 
 * @author Team 9
 * @version 1.0
 */
public class DeckModelTest {
    ArrayList<Double> cards;
    DeckModel deck;
    
    @Before
    public void prepare(){
        cards = new ArrayList<Double>();
        cards.add(1.0);
        cards.add(2.0);
        deck = new DeckModel("Test Deck", cards, true);
    }
    
    @Test
    public void testCardManipulation() {
        deck.addCard(3.0);
        assertTrue(deck.getCards().contains(3.0));
        deck.removeCard(2.0);
        assertFalse(deck.getCards().contains(2.0));
        deck.addCard(2.0);
        deck.addCard(2.0);
        assertEquals(3, deck.getCards().size());
        deck.removeCard(4.0);
        assertEquals(3, deck.getCards().size());
        deck.sort();
        assertEquals(2.0, deck.getCards().get(1), 3);
        
    }
    
    @Test
    public void testJSON() {
        Assert.assertEquals(deck.getName(), DeckModel.fromJSON(deck.toJSON())
                .getName());
        Assert.assertEquals(deck.getName(), DeckModel.fromJSONArray("["
                + deck.toJSON() + "]")[0].getName());
    }
}
