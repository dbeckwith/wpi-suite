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
           DeckModel.DEFAULT_DECK, new Date(
                    System.currentTimeMillis() - 100000), GameModel.GameStatus.PENDING);
    static long existingGameID = existingGame.getID();
    static Project testProject = new Project("test", "1");
    static User admin = new User("admin", "admin", "1234", 27);
    static String mockSsid = "abc123";
    static Session defaultSession = new Session(existingUser, testProject, mockSsid);
    static GameEntityManager manager = new GameEntityManager(db);
    static GameModel newGame = new GameModel("New Game", "A new game", null,
           DeckModel.DEFAULT_DECK, new Date(
                    System.currentTimeMillis() - 100000), GameModel.GameStatus.PENDING);
    static long newGameID = newGame.getID();
    static GameModel goodUpdatedGame = new GameModel();;
    static Session adminSession = new Session(admin, testProject, mockSsid);
    static Project otherProject = new Project("other", "2");
    GameModel otherGame;
    static Session otherSession = new Session(admin, otherProject, mockSsid);
    
    /**
     * Initializes the mock network and prepares other variables
     */
    @BeforeClass
    static public void prepareFirst() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));

        admin.setRole(Role.ADMIN);
        final GameModel gameUpdates = new GameModel("Updated Game", "Some updates",
                null, DeckModel.DEFAULT_DECK, new Date(
                        System.currentTimeMillis() - 100000), GameModel.GameStatus.PENDING);
        goodUpdatedGame.copyFrom(existingGame);
        goodUpdatedGame.editCopyFrom(gameUpdates);
        
        db.save(existingUser);
        db.save(admin);
    }
    
    /**
     * Clears the database then repopulates it to create the test environment
     */
    @Before
    public void prepare() {
        try {
            manager.deleteAll(adminSession);
            manager.deleteAll(otherSession);
        }
        catch (WPISuiteException e) {
            Assert.fail();
        }
        otherGame = new GameModel("Other Game", "something", null,
                DeckModel.DEFAULT_DECK, new Date(
                         System.currentTimeMillis() - 100000), GameModel.GameStatus.PENDING);
        db.save(existingGame, testProject);
        db.save(otherGame, otherProject);
    }
    
    /**
     * Tests that make entity properly stores an object in the database
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testMakeEntity() throws WPISuiteException {
        final GameModel created = manager
                .makeEntity(defaultSession, newGame.toJSON());
        Assert.assertEquals(newGameID, created.getID()); // IDs are unique across
        // projects
        Assert.assertEquals("New Game", created.getName());
        Assert.assertSame(db.retrieve(GameModel.class, "id", newGameID).get(0), created);
    }
    
    /**
     * Tests that database retrieval works correctly
     * 
     * @throws NotFoundException
     */
    @Test
    public void testGetEntity() throws NotFoundException {
        db.save(existingGame, testProject);
        final GameModel[] games = manager.getEntity(defaultSession, "" + existingGameID);
        Assert.assertSame(existingGame, games[0]);
    }
    
    /**
     * Tests that trying to retrieve an entity with a bad ID returns the appropriate exception
     * 
     * @throws NotFoundException
     */
    @Test(expected = NotFoundException.class)
    public void testGetBadId() throws NotFoundException {
        manager.getEntity(defaultSession, "-1");
    }
    
    /**
     * Tests that trying to retrieve an entity with an ID that doesn't exist throws
     * the appropriate exception
     * 
     * @throws NotFoundException
     */
    @Test(expected = NotFoundException.class)
    public void testGetMissingEntity() throws NotFoundException {
        manager.getEntity(defaultSession, "2");
    }
    
    /**
     * Tests retrieval of all items from the database
     */
    @Test
    public void testGetAll() {
        final GameModel[] received = manager.getAll(defaultSession);
        Assert.assertEquals(1, received.length);
        Assert.assertSame(existingGame, received[0]);
    }
    
    /**
     * Tests that save properly saves to the database.
     */
    @Test
    public void testSave() {
        final GameModel game = new GameModel("Save Test", "something", null,
               DeckModel.DEFAULT_DECK, new Date(
                        System.currentTimeMillis() - 100000), GameModel.GameStatus.PENDING);
        final long saveTestGameID = game.getID();
        manager.save(defaultSession, game);
        Assert.assertSame(game, db.retrieve(GameModel.class, "id", saveTestGameID).get(0));
        Assert.assertSame(testProject, game.getProject());
    }
    
    /**
     * Tests that delete works correctly with the database
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testDelete() throws WPISuiteException {
        Assert.assertSame(existingGame, db.retrieve(GameModel.class, "id", existingGameID)
                .get(0));
        Assert.assertTrue(manager.deleteEntity(adminSession, existingGameID + ""));
        Assert.assertEquals(0, db.retrieve(GameModel.class, "id", existingGameID).size());
    }
    
    /**
     * Tests that trying to delete an entity that doesn't exist throws the appropriate exception.
     * 
     * @throws WPISuiteException
     */
    @Test(expected = NotFoundException.class)
    public void testDeleteMissing() throws WPISuiteException {
        manager.deleteEntity(adminSession, "4534");
    }
    
    /**
     * Tests to ensure that you can't delete an entity from another project
     * 
     * @throws WPISuiteException
     */
    @Test(expected = NotFoundException.class)
    public void testDeleteFromOtherProject() throws WPISuiteException  {
        manager.deleteEntity(adminSession, Long.toString(otherGame.getID()));
    }
    
    /**
     * Tests to ensure you can't delete without privileges
     * @throws WPISuiteException
     */
    @Test(expected = UnauthorizedException.class)
    public void testDeleteNotAllowed() throws WPISuiteException {
        manager.deleteEntity(defaultSession,
                Long.toString(existingGame.getID()));
    }
    
    /**
     * Tests that delete all the entities from the database works correctly
     * @throws WPISuiteException
     */
    @Test
    public void testDeleteAll() throws WPISuiteException {
        final GameModel anotherGame = new GameModel("a title", "a description",
                null, DeckModel.DEFAULT_DECK, new Date(
                        System.currentTimeMillis() - 100000), GameModel.GameStatus.PENDING);
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
    
    /**
     * Ensures you can't delete all without the proper privileges
     * 
     * @throws WPISuiteException
     */
    @Test(expected = UnauthorizedException.class)
    public void testDeleteAllNotAllowed() throws WPISuiteException {
        manager.deleteAll(defaultSession);
    }
    
    /**
     * Ensures delete all works even when there are no objects
     * 
     * @throws WPISuiteException
     */
    @Test
    public void testDeleteAllWhenEmpty() throws WPISuiteException {
        manager.deleteAll(adminSession);
        manager.deleteAll(adminSession);
        // no exceptions
    }
    
    /**
     * Tests that count method works correctly
     */
    @Test
    public void testCount() {
        Assert.assertEquals(2, manager.Count());
    }
    
    /**
     * Tests that the update method works correctly
     * 
     * @throws WPISuiteException
     */
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
    
    /**
     * Tests that the unimplemented advancedGet throws the appropriate exception
     * 
     * @throws NotImplementedException
     */
    @Test(expected = NotImplementedException.class)
    public void testAdvancedGet() throws NotImplementedException {
        manager.advancedGet(defaultSession, new String[0]);
    }
    
    /**
     * Tests that the unimplemented advancedPost throws the appropriate exception
     * 
     * @throws NotImplementedException
     */
    @Test(expected = NotImplementedException.class)
    public void testAdvancedPost() throws NotImplementedException {
        manager.advancedPost(defaultSession, "", "");
    }
    
    /**
     * Tests that the unimplemented advancedPut throws the appropriate exception
     * 
     * @throws NotImplementedException
     */
    @Test(expected = NotImplementedException.class)
    public void testAdvancedPut() throws NotImplementedException {
        manager.advancedPut(defaultSession, new String[0], "");
    }
    
}
