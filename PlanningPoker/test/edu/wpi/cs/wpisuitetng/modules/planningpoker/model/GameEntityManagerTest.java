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
            DeckModel.DEFAULT_DECK, new Date(System.currentTimeMillis() - 100000),
            GameModel.GameStatus.PENDING);
    static long existingGameID = GameEntityManagerTest.existingGame.getID();
    static Project testProject = new Project("test", "1");
    static User admin = new User("admin", "admin", "1234", 27);
    static String mockSsid = "abc123";
    static Session defaultSession = new Session(GameEntityManagerTest.existingUser,
            GameEntityManagerTest.testProject, GameEntityManagerTest.mockSsid);
    static GameEntityManager manager = new GameEntityManager(GameEntityManagerTest.db);
    static GameModel newGame = new GameModel("New Game", "A new game", null,
            DeckModel.DEFAULT_DECK, new Date(System.currentTimeMillis() - 100000),
            GameModel.GameStatus.PENDING);
    static long newGameID = GameEntityManagerTest.newGame.getID();
    static GameModel goodUpdatedGame = new GameModel();;
    static Session adminSession = new Session(GameEntityManagerTest.admin,
            GameEntityManagerTest.testProject, GameEntityManagerTest.mockSsid);
    static Project otherProject = new Project("other", "2");
    GameModel otherGame;
    static Session otherSession = new Session(GameEntityManagerTest.admin,
            GameEntityManagerTest.otherProject, GameEntityManagerTest.mockSsid);
    
    /**
     * Initializes the mock network and prepares other variables
     */
    @BeforeClass
    static public void prepareFirst() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        
        GameEntityManagerTest.admin.setRole(Role.ADMIN);
        final GameModel gameUpdates = new GameModel("Updated Game", "Some updates", null,
                DeckModel.DEFAULT_DECK, new Date(System.currentTimeMillis() - 100000),
                GameModel.GameStatus.PENDING);
        GameEntityManagerTest.goodUpdatedGame.copyFrom(GameEntityManagerTest.existingGame);
        GameEntityManagerTest.goodUpdatedGame.editCopyFrom(gameUpdates);
        
        GameEntityManagerTest.db.save(GameEntityManagerTest.existingUser);
        GameEntityManagerTest.db.save(GameEntityManagerTest.admin);
    }
    
    /**
     * Clears the database then repopulates it to create the test environment
     */
    @Before
    public void prepare() {
        try {
            GameEntityManagerTest.manager.deleteAll(GameEntityManagerTest.adminSession);
            GameEntityManagerTest.manager.deleteAll(GameEntityManagerTest.otherSession);
        }
        catch (WPISuiteException e) {
            Assert.fail();
        }
        otherGame = new GameModel("Other Game", "something", null, DeckModel.DEFAULT_DECK,
                new Date(System.currentTimeMillis() - 100000), GameModel.GameStatus.PENDING);
        GameEntityManagerTest.db.save(GameEntityManagerTest.existingGame,
                GameEntityManagerTest.testProject);
        GameEntityManagerTest.db.save(otherGame, GameEntityManagerTest.otherProject);
    }
    
    /**
     * Tests that make entity properly stores an object in the database
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testMakeEntity() throws WPISuiteException {
        final GameModel created = GameEntityManagerTest.manager.makeEntity(
                GameEntityManagerTest.defaultSession, GameEntityManagerTest.newGame.toJSON());
        Assert.assertEquals(GameEntityManagerTest.newGameID, created.getID()); // IDs are unique across
        // projects
        Assert.assertEquals("New Game", created.getName());
        Assert.assertSame(
                GameEntityManagerTest.db.retrieve(GameModel.class, "id",
                        GameEntityManagerTest.newGameID).get(0), created);
    }
    
    /**
     * Tests that database retrieval works correctly
     * 
     * @throws NotFoundException
     */
    @Test
    public void testGetEntity() throws NotFoundException {
        GameEntityManagerTest.db.save(GameEntityManagerTest.existingGame,
                GameEntityManagerTest.testProject);
        final GameModel[] games = GameEntityManagerTest.manager.getEntity(
                GameEntityManagerTest.defaultSession, "" + GameEntityManagerTest.existingGameID);
        Assert.assertSame(GameEntityManagerTest.existingGame, games[0]);
    }
    
    /**
     * Tests that trying to retrieve an entity with a bad ID returns the
     * appropriate exception
     * 
     * @throws NotFoundException
     */
    @Test(expected = NotFoundException.class)
    public void testGetBadId() throws NotFoundException {
        GameEntityManagerTest.manager.getEntity(GameEntityManagerTest.defaultSession, "-1");
    }
    
    /**
     * Tests that trying to retrieve an entity with an ID that doesn't exist
     * throws
     * the appropriate exception
     * 
     * @throws NotFoundException
     */
    @Test(expected = NotFoundException.class)
    public void testGetMissingEntity() throws NotFoundException {
        GameEntityManagerTest.manager.getEntity(GameEntityManagerTest.defaultSession, "2");
    }
    
    /**
     * Tests retrieval of all items from the database
     */
    @Test
    public void testGetAll() {
        final GameModel[] received = GameEntityManagerTest.manager
                .getAll(GameEntityManagerTest.defaultSession);
        Assert.assertEquals(1, received.length);
        Assert.assertSame(GameEntityManagerTest.existingGame, received[0]);
    }
    
    /**
     * Tests that save properly saves to the database.
     */
    @Test
    public void testSave() {
        final GameModel game = new GameModel("Save Test", "something", null,
                DeckModel.DEFAULT_DECK, new Date(System.currentTimeMillis() - 100000),
                GameModel.GameStatus.PENDING);
        final long saveTestGameID = game.getID();
        GameEntityManagerTest.manager.save(GameEntityManagerTest.defaultSession, game);
        Assert.assertSame(game,
                GameEntityManagerTest.db.retrieve(GameModel.class, "id", saveTestGameID).get(0));
        Assert.assertSame(GameEntityManagerTest.testProject, game.getProject());
    }
    
    /**
     * Tests that delete works correctly with the database
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testDelete() throws WPISuiteException {
        Assert.assertSame(
                GameEntityManagerTest.existingGame,
                GameEntityManagerTest.db.retrieve(GameModel.class, "id",
                        GameEntityManagerTest.existingGameID).get(0));
        Assert.assertTrue(GameEntityManagerTest.manager.deleteEntity(
                GameEntityManagerTest.adminSession, GameEntityManagerTest.existingGameID + ""));
        Assert.assertEquals(
                0,
                GameEntityManagerTest.db.retrieve(GameModel.class, "id",
                        GameEntityManagerTest.existingGameID).size());
    }
    
    /**
     * Tests that trying to delete an entity that doesn't exist throws the
     * appropriate exception.
     * 
     * @throws WPISuiteException
     */
    @Test(expected = NotFoundException.class)
    public void testDeleteMissing() throws WPISuiteException {
        GameEntityManagerTest.manager.deleteEntity(GameEntityManagerTest.adminSession, "4534");
    }
    
    /**
     * Tests to ensure that you can't delete an entity from another project
     * 
     * @throws WPISuiteException
     */
    @Test(expected = NotFoundException.class)
    public void testDeleteFromOtherProject() throws WPISuiteException {
        GameEntityManagerTest.manager.deleteEntity(GameEntityManagerTest.adminSession,
                Long.toString(otherGame.getID()));
    }
    
    /**
     * Tests to ensure you can't delete without privileges
     * 
     * @throws WPISuiteException
     */
    @Test(expected = UnauthorizedException.class)
    public void testDeleteNotAllowed() throws WPISuiteException {
        GameEntityManagerTest.manager.deleteEntity(GameEntityManagerTest.defaultSession,
                Long.toString(GameEntityManagerTest.existingGame.getID()));
    }
    
    /**
     * Tests that delete all the entities from the database works correctly
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testDeleteAll() throws WPISuiteException {
        final GameModel anotherGame = new GameModel("a title", "a description", null,
                DeckModel.DEFAULT_DECK, new Date(System.currentTimeMillis() - 100000),
                GameModel.GameStatus.PENDING);
        GameEntityManagerTest.manager.makeEntity(GameEntityManagerTest.defaultSession,
                anotherGame.toJSON());
        Assert.assertEquals(
                2,
                GameEntityManagerTest.db.retrieveAll(new GameModel(),
                        GameEntityManagerTest.testProject).size());
        GameEntityManagerTest.manager.deleteAll(GameEntityManagerTest.adminSession);
        Assert.assertEquals(
                0,
                GameEntityManagerTest.db.retrieveAll(new GameModel(),
                        GameEntityManagerTest.testProject).size());
        // otherGame should still be around
        Assert.assertEquals(
                1,
                GameEntityManagerTest.db.retrieveAll(new GameModel(),
                        GameEntityManagerTest.otherProject).size());
    }
    
    /**
     * Ensures you can't delete all without the proper privileges
     * 
     * @throws WPISuiteException
     */
    @Test(expected = UnauthorizedException.class)
    public void testDeleteAllNotAllowed() throws WPISuiteException {
        GameEntityManagerTest.manager.deleteAll(GameEntityManagerTest.defaultSession);
    }
    
    /**
     * Ensures delete all works even when there are no objects
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testDeleteAllWhenEmpty() throws WPISuiteException {
        GameEntityManagerTest.manager.deleteAll(GameEntityManagerTest.adminSession);
        GameEntityManagerTest.manager.deleteAll(GameEntityManagerTest.adminSession);
        // no exceptions
    }
    
    /**
     * Tests that count method works correctly
     */
    @Test
    public void testCount() {
        Assert.assertEquals(2, GameEntityManagerTest.manager.Count());
    }
    
    /**
     * Tests that the update method works correctly
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testUpdate() throws WPISuiteException {
        final GameModel updated = GameEntityManagerTest.manager.update(
                GameEntityManagerTest.defaultSession,
                GameEntityManagerTest.goodUpdatedGame.toJSON());
        Assert.assertSame(GameEntityManagerTest.existingGame, updated);
        Assert.assertEquals(GameEntityManagerTest.goodUpdatedGame.getName(), updated.getName()); // make
        // sure
        // ModelMapper
        // is
        // used
    }
    
    /**
     * Tests that the unimplemented advancedGet throws the appropriate exception
     * 
     * @throws NotImplementedException
     */
    @Test(expected = NotImplementedException.class)
    public void testAdvancedGet() throws NotImplementedException {
        GameEntityManagerTest.manager.advancedGet(GameEntityManagerTest.defaultSession,
                new String[0]);
    }
    
    /**
     * Tests that the unimplemented advancedPost throws the appropriate
     * exception
     * 
     * @throws NotImplementedException
     */
    @Test(expected = NotImplementedException.class)
    public void testAdvancedPost() throws NotImplementedException {
        GameEntityManagerTest.manager.advancedPost(GameEntityManagerTest.defaultSession, "", "");
    }
    
    /**
     * Tests that the unimplemented advancedPut throws the appropriate exception
     * 
     * @throws NotImplementedException
     */
    @Test(expected = NotImplementedException.class)
    public void testAdvancedPut() throws NotImplementedException {
        GameEntityManagerTest.manager.advancedPut(GameEntityManagerTest.defaultSession,
                new String[0], "");
    }
    
}
