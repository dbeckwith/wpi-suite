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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 * @author Andrew
 * 
 */
public class GameListModelTest {
	static GameListModel instance;

	@BeforeClass
	static public void setUpBeforeClass() {
	    Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
		GameListModelTest.instance = GameListModel.getInstance();
		instance.removeObservers();
		instance.removeStatusObservers();
	}

	@Test
	public void testGetInstance() {
		Assert.assertEquals(
				"A new instance is not the same as the previous instance",
				GameListModelTest.instance, GameListModel.getInstance());
	}

	@Test
	public void testAddListListener() {
		SimpleListObserver slo = new SimpleListObserver() {
			@Override
			public void listUpdated() {
			}
		};
		GameListModelTest.instance.addListListener(slo);
		Assert.assertTrue(GameListModelTest.instance.getObservers().contains(
				slo));
		GameListModelTest.instance.addListListener(slo);
		Assert.assertTrue(GameListModelTest.instance.getObservers().contains(
				slo));
	}

	@Test
	public void testGameManipulation() {
		GameModel game1 = new GameModel("Test Game",
				"Test Game Description", null, DeckModel.DEFAULT_DECK, new Date(), null, GameStatus.PENDING);
		GameModel game2 = new GameModel("Test Game 2",
				"Test Game Description 2", null, DeckModel.DEFAULT_DECK, new Date(), null,
				GameStatus.COMPLETE);
		GameListModelTest.instance.addGame(game1);
		GameListModelTest.instance.addGame(game2);
		Assert.assertTrue(GameListModelTest.instance.getGames().contains(game1));
		Assert.assertTrue(GameListModelTest.instance.getGames().contains(game2));
		GameListModelTest.instance.removeGame(game1);
		Assert.assertTrue(GameListModelTest.instance.getGames().contains(game2));
		Assert.assertFalse(GameListModelTest.instance.getGames()
				.contains(game1));
		GameListModelTest.instance.addGame(game1);
		GameListModelTest.instance.emptyModel();
		Assert.assertFalse(GameListModelTest.instance.getGames()
				.contains(game1));
		Assert.assertFalse(GameListModelTest.instance.getGames()
				.contains(game2));
	}
}
