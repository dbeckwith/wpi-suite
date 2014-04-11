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
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;

/**
 * @author Lukas, Dan
 * 
 */
public class GameTest {
    
    @Test
    public void TestRequirementEndsAfterDeadline() {
        final GameModel testgame = new GameModel(1, "Test Game", "something", null,
                new Date(System.currentTimeMillis() - 100000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        Assert.assertTrue(testgame.isEnded());
    }
    
    @Test
    public void TestRequirementNotCompleteBeforeDeadline() {
        final GameModel testgame = new GameModel(2, "Test Game", "something", null,
                new Date(System.currentTimeMillis() + 100000000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        Assert.assertFalse(testgame.isEnded());
    }
    
}
