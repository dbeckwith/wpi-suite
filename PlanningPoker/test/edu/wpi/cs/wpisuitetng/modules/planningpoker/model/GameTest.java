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
import java.util.HashSet;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus; // $codepro.audit.disable
                                                                                // unnecessaryImport
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests games and their deadlines
 * 
 * @author Team 9
 * @version 1.0
 */
public class GameTest {
    
    static User existingUser = new User("joe", "joe", "1234", 2);
    static Project testProject = new Project("test", "1");
    static String mockSsid = "abc123";
    static MockData db = new MockData(new HashSet<Object>());
    static GameEntityManager manager = new GameEntityManager(GameTest.db);
    static Session defaultSession = new Session(GameTest.existingUser, GameTest.testProject,
            GameTest.mockSsid);
    
    /**
     * Initializes mock network and saves a user to the database
     */
    @BeforeClass
    static public void prepare() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        GameTest.db.save(GameTest.existingUser);
    }
    
    /**
     * Tests to ensure a game ends after its deadline by sleeping until the
     * deadline is up
     */
    @Test
    public void TestGameEndsAfterDeadline() {
        final GameModel testgame = new GameModel("Test Game", "something", null,
                DeckModel.DEFAULT_DECK, new Date(System.currentTimeMillis() + 1000), GameStatus.NEW);
        GameModel created = new GameModel();
        try {
            created = GameTest.manager.makeEntity(GameTest.defaultSession, testgame.toJSON());
            //final List<Model> oldGameModels = db.retrieve(GameModel.class, "id", created.getID());
            created.startGame();
            created = GameTest.manager.update(GameTest.defaultSession, created.toJSON());
        }
        catch (WPISuiteException e1) {
            System.out.print("");
        }
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            System.out.print("");
        }
        Assert.assertTrue(created.isEnded());
    }
    
    /**
     * Tests that a game that hasn't reached its deadline and hasn't been ended
     * in any other way
     * is not yet ended.
     */
    @Test
    public void TestRequirementNotCompleteBeforeDeadline() {
        
        final GameModel testgame = new GameModel("Test Game", "something", null,
                DeckModel.DEFAULT_DECK, new Date(System.currentTimeMillis() + 100000000),
                GameStatus.PENDING);
        Assert.assertFalse(testgame.isEnded());
    }
    
}
