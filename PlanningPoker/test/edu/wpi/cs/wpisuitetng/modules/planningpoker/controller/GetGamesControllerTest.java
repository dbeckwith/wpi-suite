/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * TODO: Contributors' names
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockRequest;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the GetGamesController class
 * 
 * @author Team 9
 * @version 1.0
 */
public class GetGamesControllerTest {
    GetGamesController instance = GetGamesController.getInstance();
    GameModel nullGame = new GameModel();
    GameModel game1 = new GameModel("Test Game 1", "Live Game that just ended",
            null, DeckListModel.getInstance().getDefaultDeck(), new Date(),
            GameType.LIVE, GameStatus.COMPLETE);
    GameModel game2 = new GameModel("Test Game 2",
            "Distributed Game that will end in 5 seconds", null, DeckListModel
                    .getInstance().getDefaultDeck(), new Date(
                    System.currentTimeMillis() + 5000), GameType.DISTRIBUTED,
            GameStatus.PENDING);
    GameModel game3 = new GameModel(
            "Test Game 3",
            "Live Game with end time in 10 seconds, but already manually ended",
            null, DeckListModel.getInstance().getDefaultDeck(), new Date(System
                    .currentTimeMillis() + 10000), GameType.LIVE,
            GameStatus.COMPLETE);
    GameModel game4 = new GameModel(
            "Test Game 4",
            "Distributed Game that has end time 10 seconds ago but hasn't been updated to be complete yet",
            null, DeckListModel.getInstance().getDefaultDeck(), new Date(System
                    .currentTimeMillis() - 10000), GameType.DISTRIBUTED,
            GameStatus.PENDING);
    GameListModel list = GameListModel.getInstance();
    GameModel[] gamesToAdd = new GameModel[] { game1, game2, game3, game4 };
    
    /**
     * Initiates the mock network and removes status observers from the game
     * list
     */
    @BeforeClass
    static public void prepareFirst() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://localhost"));
        
    }
    
    /**
     * Initializes variables
     */
    @Before
    public void prepare() {
        instance = GetGamesController.getInstance();
        nullGame = new GameModel();
        game1 = new GameModel("Test Game 1", "Live Game that just ended", null,
                DeckListModel.getInstance().getDefaultDeck(), new Date(),
                GameType.LIVE, GameStatus.COMPLETE);
        game2 = new GameModel("Test Game 2",
                "Distributed Game that will end in 5 seconds", null,
                DeckListModel.getInstance().getDefaultDeck(), new Date(
                        System.currentTimeMillis() + 5000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        game3 = new GameModel(
                "Test Game 3",
                "Live Game with end time in 10 seconds, but already manually ended",
                null, DeckListModel.getInstance().getDefaultDeck(), new Date(
                        System.currentTimeMillis() + 10000), GameType.LIVE,
                GameStatus.COMPLETE);
        game4 = new GameModel(
                "Test Game 4",
                "Distributed Game that has end time 10 seconds ago but hasn't been updated to be complete yet",
                null, DeckListModel.getInstance().getDefaultDeck(), new Date(
                        System.currentTimeMillis() - 10000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        list = GameListModel.getInstance();
        gamesToAdd = new GameModel[] { game1, game2, game3, game4 };
        list.removeObservers();
        list.removeStatusObservers();
    }
    
    /**
     * Tests that getInstance returns the same instance
     */
    @Test
    public void testGetInstance() {
        Assert.assertEquals(
                "A new instance is not the same as the previous instance",
                instance, GetGamesController.getInstance());
    }
    
    /**
     * Tests that GameListModel is correctly populated after receiving games
     */
    @Test
    public void testReceivedGames() {
        instance.receivedGames(new GameModel[] { game1, game2, game3, game4 });
        Assert.assertTrue(GameListModel.getInstance().getGames()
                .contains(game1));
        Assert.assertTrue(GameListModel.getInstance().getGames()
                .contains(game2));
        Assert.assertTrue(GameListModel.getInstance().getGames()
                .contains(game3));
        Assert.assertTrue(GameListModel.getInstance().getGames()
                .contains(game4));
    }
    
    /**
     * Tests that retrieveGames makes a network request
     */
    @Test
    public void testRetrieveGames() {
        GetGamesController.getInstance().retrieveGames();
        final MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
}
