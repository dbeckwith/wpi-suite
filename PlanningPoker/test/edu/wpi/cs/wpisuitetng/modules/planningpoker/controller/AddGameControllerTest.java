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

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockRequest;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 * @author Andrew
 * 
 */
public class AddGameControllerTest {
    
    static AddGameController instance;
    
    @BeforeClass
    static public void setUpBeforeClass() {
        AddGameControllerTest.instance = AddGameController.getInstance();
    }
    
    @Test
    public void testGetInstance() {
        AddGameControllerTest.instance = AddGameController.getInstance();
        Assert.assertEquals(
                "A new instance is not the same as the previous instance",
                AddGameControllerTest.instance, AddGameController.getInstance());
    }
    
    @Test
    public void testAddGame() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        AddGameController agc = AddGameController.getInstance();
        agc.addGame(new GameModel(1, "Test", "Test Description",
                new ArrayList<GameRequirementModel>(), new Date(),
                GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.COMPLETE));
        MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
}
