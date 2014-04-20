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







import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * @author Lukas, Dan
 * 
 */
public class GameTest {
    
    @BeforeClass
    static public void prepare() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
    }
    
    @Test
    public void TestRequirementEndsAfterDeadline() {
        final GameModel testgame = new GameModel("Test Game", "something", null,
                DeckListModel.getInstance().getDefaultDeck(), new Date(
                        System.currentTimeMillis() - 100000),
                        GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.PENDING);
        Assert.assertTrue(testgame.isEnded());
    }
    
    @Test
    public void TestRequirementNotCompleteBeforeDeadline() {
        final GameModel testgame = new GameModel("Test Game", "something", null,
                DeckListModel.getInstance().getDefaultDeck(), new Date(
                        System.currentTimeMillis() + 100000000),
                        GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.PENDING);
        Assert.assertFalse(testgame.isEnded());
    }
    
}
