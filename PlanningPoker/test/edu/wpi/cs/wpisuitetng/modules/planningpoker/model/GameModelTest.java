/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the GameModel class
 * 
 * @author Team 9
 * @version 1.0
 */
public class GameModelTest {
    GameModel nullGame;
    GameModel game1;
    GameModel game2;
    GameModel game3;
    List<GameRequirementModel> reqs;
    
    /**
     * Initializes the mock network and other variables
     */
    @Before
    public void prepare() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        reqs = new ArrayList<GameRequirementModel>();
        final GameRequirementModel aReq = new GameRequirementModel(1,
                "Req name", "Req desc", "User Story", new ArrayList<Estimate>());
        reqs.add(aReq);
        nullGame = new GameModel();

        game1 = new GameModel("Test Game 1", "Game that just ended", reqs,
                DeckModel.DEFAULT_DECK, new Date(),
                GameModel.GameStatus.COMPLETE);
        game2 = new GameModel("Test Game 2",
                "Game that will end in 5 seconds", reqs,
                DeckModel.DEFAULT_DECK, new Date(
                        System.currentTimeMillis() + 5000),
                GameModel.GameStatus.PENDING);
        game3 = new GameModel(
                "Test Game 3",
                "Game with end time in 10 seconds, but already manually ended",

                reqs, DeckModel.DEFAULT_DECK, new Date(
                        System.currentTimeMillis() + 10000),
                GameModel.GameStatus.COMPLETE);
    }
    
    /**
     * Tests that setting the ended status of a game works correctly
     */
    @Test
    public void testSetEnded() {
        game1.setEnded(true);
        Assert.assertEquals(GameModel.GameStatus.COMPLETE, game1.getStatus());
        game2.setEnded(true);
        Assert.assertEquals(GameModel.GameStatus.COMPLETE, game2.getStatus());
        game3.setEnded(false);
        Assert.assertEquals(GameModel.GameStatus.COMPLETE, game3.getStatus());
    }
    
    /**
     * Tests that the isEnded method returns the appropriate boolean
     */
    @Test
    public void testIsEnded() {
        CurrentUserController.getInstance().receivedUsers(
                new User[] { new User("Name", "Username", "Pass", 1) });
        Assert.assertTrue(game1.isEnded());
        Assert.assertFalse(game2.isEnded());
        Assert.assertTrue(game3.isEnded());
    }
    
    /**
     * Tests that an object is the same after being transformed to and from JSON
     */
    @Test
    public void testJSON() {
        Assert.assertEquals(game1.getName(), GameModel.fromJSON(game1.toJSON())
                .getName());
        Assert.assertEquals(game2.getName(), GameModel.fromJSONArray("["
                + game2.toJSON() + "]")[0].getName());
    }
    
    /**
     * Tests the status of a games after closing or not closing them
     */
    @Test
    public void testClosedGame() {
        CurrentUserController.getInstance().receivedUsers(
                new User[] { new User("Name", "Username", "Pass", 1) });
        game1.closeGame();
        game2.closeGame();
        Assert.assertTrue(game1.isEnded());
        Assert.assertTrue(game2.isEnded());
        Assert.assertTrue(game3.isEnded());
        Assert.assertTrue(game1.isClosed());
        Assert.assertTrue(game2.isClosed());
        Assert.assertFalse(game3.isClosed());
    }
}
