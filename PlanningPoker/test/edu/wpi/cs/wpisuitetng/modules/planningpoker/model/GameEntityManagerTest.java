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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;

/**
 * Tests for GameEntityManager
 * 
 * @author nfbrown
 */
public class GameEntityManagerTest {
    
    MockData db;
    User existingUser;
    GameModel existingGame;
    Session defaultSession;
    String mockSsid;
    GameEntityManager manager;
    GameModel newGame;
    GameModel goodUpdatedGame;
    User bob;
    Session adminSession;
    Project testProject;
    Project otherProject;
    GameModel otherGame;
    
    @Before
    public void setUp() {
        User admin = new User("admin", "admin", "1234", 27);
        admin.setRole(Role.ADMIN);
        testProject = new Project("test", "1");
        otherProject = new Project("other", "2");
        mockSsid = "abc123";
        adminSession = new Session(admin, testProject, mockSsid);
        existingUser = new User("joe", "joe", "1234", 2);
        existingGame = new GameModel(1, "Existing Game", "something", null,
                new Date(System.currentTimeMillis() - 100000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        defaultSession = new Session(existingUser, testProject, mockSsid);
        newGame = new GameModel(-1, "New Game", "A new game", null, new Date(
                System.currentTimeMillis() - 100000), GameType.DISTRIBUTED,
                GameStatus.PENDING);
        otherGame = new GameModel(3, "Other Game", "something", null, new Date(
                System.currentTimeMillis() - 100000), GameType.DISTRIBUTED,
                GameStatus.PENDING);
        goodUpdatedGame = new GameModel(1, "Updated Game", "Some updates",
                null, new Date(System.currentTimeMillis() - 100000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        
        db = new MockData(new HashSet<Object>());
        db.save(existingGame, testProject);
        db.save(otherGame, otherProject);
        db.save(existingUser);
        db.save(admin);
        manager = new GameEntityManager(db);
    }
    
    @Test
    public void testMakeEntity() throws WPISuiteException {
        GameModel created = manager
                .makeEntity(defaultSession, newGame.toJSON());
        Assert.assertEquals(2, created.getID()); // IDs are unique across projects
        Assert.assertEquals("New Game", created.getName());
        Assert.assertSame(db.retrieve(GameModel.class, "id", 2).get(0), created);
    }
    
    @Test
    public void testGetEntity() throws NotFoundException {
        GameModel[] games = manager.getEntity(defaultSession, "1");
        Assert.assertSame(existingGame, games[0]);
    }
    
    @Test(expected = NotFoundException.class)
    public void testGetBadId() throws NotFoundException {
        manager.getEntity(defaultSession, "-1");
    }
    
    @Test(expected = NotFoundException.class)
    public void testGetMissingEntity() throws NotFoundException {
        manager.getEntity(defaultSession, "2");
    }
    
    @Test
    public void testGetAll() throws WPISuiteException {
        GameModel[] received = manager.getAll(defaultSession);
        Assert.assertEquals(1, received.length);
        Assert.assertSame(existingGame, received[0]);
    }
    
    @Test
    public void testSave() throws WPISuiteException {
        GameModel game = new GameModel(4, "Save Test", "something", null,
                new Date(System.currentTimeMillis() - 100000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        manager.save(defaultSession, game);
        Assert.assertSame(game, db.retrieve(GameModel.class, "id", 4).get(0));
        Assert.assertSame(testProject, game.getProject());
    }
    
    @Test
    public void testDelete() throws WPISuiteException {
        Assert.assertSame(existingGame, db.retrieve(GameModel.class, "id", 1)
                .get(0));
        Assert.assertTrue(manager.deleteEntity(adminSession, "1"));
        Assert.assertEquals(0, db.retrieve(GameModel.class, "id", 1).size());
    }
    
    @Test(expected = NotFoundException.class)
    public void testDeleteMissing() throws WPISuiteException {
        manager.deleteEntity(adminSession, "4534");
    }
    
    @Test(expected = NotFoundException.class)
    public void testDeleteFromOtherProject() throws WPISuiteException {
        manager.deleteEntity(adminSession, Integer.toString(otherGame.getID()));
    }
    
    @Test(expected = UnauthorizedException.class)
    public void testDeleteNotAllowed() throws WPISuiteException {
        manager.deleteEntity(defaultSession,
                Integer.toString(existingGame.getID()));
    }
    
    @Test
    public void testDeleteAll() throws WPISuiteException {
        GameModel anotherGame = new GameModel(-1, "a title", "a description",
                null, new Date(System.currentTimeMillis() - 100000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        manager.makeEntity(defaultSession, anotherGame.toJSON());
        Assert.assertEquals(2, db.retrieveAll(new GameModel(), testProject)
                .size());
        manager.deleteAll(adminSession);
        Assert.assertEquals(0, db.retrieveAll(new GameModel(), testProject)
                .size());
        // otherGame should still be around
        Assert.assertEquals(1, db.retrieveAll(new GameModel(), otherProject)
                .size());
    }
    
    @Test(expected = UnauthorizedException.class)
    public void testDeleteAllNotAllowed() throws WPISuiteException {
        manager.deleteAll(defaultSession);
    }
    
    @Test
    public void testDeleteAllWhenEmpty() throws WPISuiteException {
        manager.deleteAll(adminSession);
        manager.deleteAll(adminSession);
        // no exceptions
    }
    
    @Test
    public void testCount() throws WPISuiteException {
        Assert.assertEquals(2, manager.Count());
    }
    
    @Test
    public void testUpdate() throws WPISuiteException {
        GameModel updated = manager.update(defaultSession,
                goodUpdatedGame.toJSON());
        Assert.assertSame(existingGame, updated);
        Assert.assertEquals(goodUpdatedGame.getName(), updated.getName()); // make sure ModelMapper is used
    }
    
    @Test(expected = NotImplementedException.class)
    public void testAdvancedGet() throws WPISuiteException {
        manager.advancedGet(defaultSession, new String[0]);
    }
    
    @Test(expected = NotImplementedException.class)
    public void testAdvancedPost() throws WPISuiteException {
        manager.advancedPost(defaultSession, "", "");
    }
    
    @Test(expected = NotImplementedException.class)
    public void testAdvancedPut() throws WPISuiteException {
        manager.advancedPut(defaultSession, new String[0], "");
    }
    
    
}
