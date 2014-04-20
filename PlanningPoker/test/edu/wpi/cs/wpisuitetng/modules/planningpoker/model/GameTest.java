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
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * @author Lukas, Dan
 * 
 */
public class GameTest {
    
    static Session defaultSession;
    static GameEntityManager manager;
    static User existingUser;
    static Project testProject;
    static String mockSsid;
    static MockData db;

    @BeforeClass
    static public void setUpBeforeClass() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        mockSsid = "abc123";
        testProject = new Project("test", "1");
        existingUser = new User("joe", "joe", "1234", 2);
        defaultSession = new Session(existingUser, testProject, mockSsid);
        db = new MockData(new HashSet<Object>());
        db.save(existingUser);
        manager = new GameEntityManager(db);
    }
    
	@Test
	public void TestRequirementEndsAfterDeadline() {
		GameModel testgame = new GameModel("Test Game", "something", null,
				DeckListModel.getInstance().getDefaultDeck(), new Date(
						System.currentTimeMillis() + 1000),
				GameType.DISTRIBUTED, GameStatus.NEW);
		GameModel created = new GameModel();
		try {
            created = manager
                    .makeEntity(defaultSession, testgame.toJSON());
            final List<Model> oldGameModels = db.retrieve(GameModel.class, "id", created.getID());
            System.out.println(oldGameModels.size());
            System.out.println("Hi" + oldGameModels.get(0));
            created.startGame();
            created = manager
                    .update(defaultSession, created.toJSON());
        }
        catch (WPISuiteException e1) {
        }
		try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
        }
		Assert.assertTrue(created.isEnded());
	}

	@Test
	public void TestRequirementNotCompleteBeforeDeadline() {
		GameModel testgame = new GameModel("Test Game", "something", null,
				DeckListModel.getInstance().getDefaultDeck(), new Date(
						System.currentTimeMillis() + 100000000),
				GameType.DISTRIBUTED, GameStatus.PENDING);
		Assert.assertFalse(testgame.isEnded());
	}

}
