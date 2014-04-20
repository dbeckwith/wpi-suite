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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests for GameEntityManager
 * 
 * @author Team 9
 * @version 1.0
 */
public class GameEntityManagerTest {
    
    static MockData db = new MockData(new HashSet<Object>());
    static User existingUser = new User("joe", "joe", "1234", 2);
    static GameModel existingGame = new GameModel("Existing Game", "something", null,
            DeckListModel.getInstance().getDefaultDeck(), new Date(
                    System.currentTimeMillis() - 100000),
                    GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.PENDING);
    static int existingGameID = existingGame.getID();
    static Project testProject = new Project("test", "1");
    static User admin = new User("admin", "admin", "1234", 27);
    static String mockSsid = "abc123";
    static Session defaultSession = new Session(existingUser, testProject, mockSsid);
    static GameEntityManager manager = new GameEntityManager(db);
    static GameModel newGame = new GameModel("New Game", "A new game", null,
            DeckListModel.getInstance().getDefaultDeck(), new Date(
                    System.currentTimeMillis() - 100000),
                    GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.PENDING);
    static int newGameID = newGame.getID();
    static GameModel goodUpdatedGame = new GameModel();;
    static Session adminSession = new Session(admin, testProject, mockSsid);
    static Project otherProject = new Project("other", "2");
    static GameModel otherGame = new GameModel("Other Game", "something", null,
            DeckListModel.getInstance().getDefaultDeck(), new Date(
                    System.currentTimeMillis() - 100000),
                    GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.PENDING);
    
    @BeforeClass
    static public void prepareFirst() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        admin.setRole(Role.ADMIN);
        final GameModel gameUpdates = new GameModel("Updated Game", "Some updates",
                null, DeckListModel.getInstance().getDefaultDeck(), new Date(
                        System.currentTimeMillis() - 100000),
                        GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.PENDING);
        goodUpdatedGame.copyFrom(existingGame);
        goodUpdatedGame.editCopyFrom(gameUpdates);
        
        db.save(existingGame, testProject);
        db.save(otherGame, otherProject);
        db.save(existingUser);
        db.save(admin);
    }
    
    @Before
    public void prepare() {
        try {
            manager.deleteAll(adminSession);
        }
        catch (WPISuiteException e) {
            Assert.fail();
        }
        db.save(existingGame, testProject);
        db.save(otherGame, otherProject);
        db.save(existingUser);
        db.save(admin);
    }
    
    @Test
    public void testMakeEntity() throws WPISuiteException {
        final GameModel created = manager
                .makeEntity(defaultSession, newGame.toJSON());
        Assert.assertEquals(newGameID, created.getID()); // IDs are unique across
        // projects
        Assert.assertEquals("New Game", created.getName());
        Assert.assertSame(db.retrieve(GameModel.class, "id", newGameID).get(0), created);
    }
    
    @Test
    public void testGetEntity() throws NotFoundException {
        db.save(existingGame, testProject);
        final GameModel[] games = manager.getEntity(defaultSession, "" + existingGameID);
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
    public void testGetAll() {
        final GameModel[] received = manager.getAll(defaultSession);
        Assert.assertEquals(1, received.length);
        Assert.assertSame(existingGame, received[0]);
    }
    
    @Test
    public void testSave() {
        final GameModel game = new GameModel("Save Test", "something", null,
                DeckListModel.getInstance().getDefaultDeck(), new Date(
                        System.currentTimeMillis() - 100000),
                        GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.PENDING);
        final int saveTestGameID = game.getID();
        manager.save(defaultSession, game);
        Assert.assertSame(game, db.retrieve(GameModel.class, "id", saveTestGameID).get(0));
        Assert.assertSame(testProject, game.getProject());
    }
    
    @Test
    public void testDelete() throws WPISuiteException {
        Assert.assertSame(existingGame, db.retrieve(GameModel.class, "id", existingGameID)
                .get(0));
        Assert.assertTrue(manager.deleteEntity(adminSession, existingGameID + ""));
        Assert.assertEquals(0, db.retrieve(GameModel.class, "id", existingGameID).size());
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
        final GameModel anotherGame = new GameModel("a title", "a description",
                null, DeckListModel.getInstance().getDefaultDeck(), new Date(
                        System.currentTimeMillis() - 100000),
                        GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.PENDING);
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
    public void testCount() {
        Assert.assertEquals(2, manager.Count());
    }
    
    @Test
    public void testUpdate() throws WPISuiteException {
        final GameModel updated = manager.update(defaultSession,
                goodUpdatedGame.toJSON());
        Assert.assertSame(existingGame, updated);
        Assert.assertEquals(goodUpdatedGame.getName(), updated.getName()); // make
        // sure
        // ModelMapper
        // is
        // used
    }
    
    @Test(expected = NotImplementedException.class)
    public void testAdvancedGet() throws NotImplementedException {
        manager.advancedGet(defaultSession, new String[0]);
    }
    
    @Test(expected = NotImplementedException.class)
    public void testAdvancedPost() throws NotImplementedException {
        manager.advancedPost(defaultSession, "", "");
    }
    
    @Test(expected = NotImplementedException.class)
    public void testAdvancedPut() throws NotImplementedException {
        manager.advancedPut(defaultSession, new String[0], "");
    }
    
}
