/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the GameListModel class
 * 
 * @author Team 9
 * @version 1.0
 */
public class GameListModelTest {
    static GameListModel instance;
    
    /**
     * Initializes the mock network and the instance of the GameListModel
     */
    @BeforeClass
    static public void prepare() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        
        GameListModelTest.instance = GameListModel.getInstance();
        GameListModelTest.instance.removeObservers();
        GameListModelTest.instance.removeStatusObservers();
    }
    
    /**
     * Tests that the instance returned by getInstance is the same as a previous
     * instance
     */
    @Test
    public void testGetInstance() {
        Assert.assertEquals("A new instance is not the same as the previous instance",
                GameListModelTest.instance, GameListModel.getInstance());
    }
    
    /**
     * Tests that adding a list listener works correctly
     */
    @Test
    public void testAddListListener() {
        final SimpleListObserver slo = new SimpleListObserver() {
            @Override
            public void listUpdated() {
            }
        };
        GameListModelTest.instance.addListListener(slo);
        Assert.assertTrue(GameListModelTest.instance.getObservers().contains(slo));
        GameListModelTest.instance.addListListener(slo);
        Assert.assertTrue(GameListModelTest.instance.getObservers().contains(slo));
    }
    
    /**
     * Tests that adding and removing games works correctly
     */
    @Test
    public void testGameManipulation() {
        final GameModel game1 = new GameModel("Test Game", "Test Game Description", null,
                DeckModel.DEFAULT_DECK, new Date(), null);
        final GameModel game2 = new GameModel("Test Game 2", "Test Game Description 2", null,
                DeckModel.DEFAULT_DECK, new Date(), null);
        GameListModelTest.instance.addGame(game1);
        GameListModelTest.instance.addGame(game2);
        Assert.assertTrue(GameListModelTest.instance.getGames().contains(game1));
        Assert.assertTrue(GameListModelTest.instance.getGames().contains(game2));
        GameListModelTest.instance.removeGame(game1);
        Assert.assertTrue(GameListModelTest.instance.getGames().contains(game2));
        Assert.assertFalse(GameListModelTest.instance.getGames().contains(game1));
        GameListModelTest.instance.addGame(game1);
        GameListModelTest.instance.emptyModel();
        Assert.assertFalse(GameListModelTest.instance.getGames().contains(game1));
        Assert.assertFalse(GameListModelTest.instance.getGames().contains(game2));
    }
}
