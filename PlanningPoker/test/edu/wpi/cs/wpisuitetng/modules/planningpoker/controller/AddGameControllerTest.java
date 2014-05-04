/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockRequest;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the AddGameController class
 * 
 * @author Team 9
 * @version 1.0
 */
public class AddGameControllerTest {

	static AddGameController instance = AddGameController.getInstance();

	/**
	 * Tests that getInstance returns the same instance
	 */
	@Test
	public void testGetInstance() {
		AddGameControllerTest.instance = AddGameController.getInstance();
		Assert.assertEquals(
				"A new instance is not the same as the previous instance",
				AddGameControllerTest.instance, AddGameController.getInstance());
	}

	/**
	 * Tests that addGame sends a network request
	 */
	@Test
	public void testAddGame() {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		final AddGameController agc = AddGameController.getInstance();
		agc.addGame(new GameModel( "Test", "Test Description",
				new ArrayList<GameRequirementModel>(), DeckModel.DEFAULT_DECK, new Date(),
				GameModel.GameStatus.COMPLETE));
		final MockRequest request = ((MockNetwork) Network.getInstance())
				.getLastRequestMade();
		if (request == null) {
			Assert.fail("request not sent");
		}
		Assert.assertTrue(request.isSent());
	}

}
