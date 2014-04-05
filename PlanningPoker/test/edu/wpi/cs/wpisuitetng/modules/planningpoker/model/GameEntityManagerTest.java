package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

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
        ArrayList<User> users = new ArrayList<User>();
        existingUser = new User("joe", "joe", "1234", 2); 
        users.add(existingUser);
        existingGame = new GameModel(1, "Existing Game", "something", null, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING, users);       
        defaultSession = new Session(existingUser, testProject, mockSsid);
        newGame = new GameModel(-1, "New Game", "A new game", null, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING, users);  
        otherGame = new GameModel(3, "Other Game", "something", null, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING, users); 
        goodUpdatedGame = new GameModel(1, "Updated Game", "Some updates", null, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING, users);  
        
        db = new MockData(new HashSet<Object>());
        db.save(existingGame, testProject);
        db.save(otherGame, otherProject);
        db.save(existingUser);
        db.save(admin);
        manager = new GameEntityManager(db);
    }
    
    @Test
    public void testMakeEntity() throws WPISuiteException {
        GameModel created = manager.makeEntity(defaultSession, newGame.toJSON());
        assertEquals(2, created.getID()); // IDs are unique across projects
        assertEquals("New Game", created.getName());
        assertSame(db.retrieve(GameModel.class, "id", 2).get(0), created);
    }
    
    @Test
    public void testGetEntity() throws NotFoundException {
        GameModel[] games = manager.getEntity(defaultSession, "1");
        assertSame(existingGame, games[0]);
    }
    
    @Test(expected=NotFoundException.class)
    public void testGetBadId() throws NotFoundException {
        manager.getEntity(defaultSession, "-1");
    }
    
    @Test(expected=NotFoundException.class)
    public void testGetMissingEntity() throws NotFoundException {
        manager.getEntity(defaultSession, "2");
    }
    
    @Test
    public void testGetAll() throws WPISuiteException {
        GameModel[] received = manager.getAll(defaultSession);
        assertEquals(1, received.length);
        assertSame(existingGame, received[0]);
    }
    
    @Test
    public void testSave() throws WPISuiteException {
    	ArrayList<User> users = new ArrayList<User>();
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	users.add(kevin);
        GameModel game = new GameModel(4, "Save Test", "something", null, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING, users);  
        manager.save(defaultSession, game);
        assertSame(game, db.retrieve(GameModel.class, "id", 4).get(0));
        assertSame(testProject, game.getProject());
    }
    
    @Test
    public void testDelete() throws WPISuiteException {
        assertSame(existingGame, db.retrieve(GameModel.class, "id", 1).get(0));
        assertTrue(manager.deleteEntity(adminSession, "1"));
        assertEquals(0, db.retrieve(GameModel.class, "id", 1).size());
    }
    
    @Test(expected=NotFoundException.class)
    public void testDeleteMissing() throws WPISuiteException {
        manager.deleteEntity(adminSession, "4534");
    }
    
    @Test(expected=NotFoundException.class)
    public void testDeleteFromOtherProject() throws WPISuiteException {
        manager.deleteEntity(adminSession, Integer.toString(otherGame.getID()));
    }
    
    @Test(expected=UnauthorizedException.class)
    public void testDeleteNotAllowed() throws WPISuiteException {
        manager.deleteEntity(defaultSession, Integer.toString(existingGame.getID()));
    }
   
    @Test
    public void testDeleteAll() throws WPISuiteException {
    	ArrayList<User> users = new ArrayList<User>();
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	users.add(kevin);
        GameModel anotherGame = new GameModel(-1, "a title", "a description", null, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
        manager.makeEntity(defaultSession, anotherGame.toJSON());
        assertEquals(2, db.retrieveAll(new GameModel(), testProject).size());
        manager.deleteAll(adminSession);
        assertEquals(0, db.retrieveAll(new GameModel(), testProject).size());
        // otherGame should still be around
        assertEquals(1, db.retrieveAll(new GameModel(), otherProject).size());
    }
    
    @Test(expected=UnauthorizedException.class)
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
        assertEquals(2, manager.Count());
    }
    
    @Test
    public void testUpdate() throws WPISuiteException {
        GameModel updated = manager.update(defaultSession, goodUpdatedGame.toJSON());
        assertSame(existingGame, updated);
        assertEquals(goodUpdatedGame.getName(), updated.getName()); // make sure ModelMapper is used
    }
    
    @Test(expected=NotImplementedException.class)
    public void testAdvancedGet() throws WPISuiteException {
        manager.advancedGet(defaultSession, new String[0]);
    }
    
    @Test(expected=NotImplementedException.class)
    public void testAdvancedPost() throws WPISuiteException {
        manager.advancedPost(defaultSession, "", "");
    }
    
    @Test(expected=NotImplementedException.class)
    public void testAdvancedPut() throws WPISuiteException {
        manager.advancedPut(defaultSession, new String[0], "");
    }
    
    
}
