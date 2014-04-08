package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;

/**
 * 
 * @author Andrew
 *
 */
public class GameListModelTest {
    static GameListModel instance;
    
    @BeforeClass
    static public void setUpBeforeClass(){
        instance = GameListModel.getInstance();
    }
    
    @Test
    public void testGetInstance() {
        assertEquals("A new instance is not the same as the previous instance", instance, GameListModel.getInstance());
    }
    
    @Test
    public void testAddListListener(){
        SimpleListObserver slo = new SimpleListObserver() {  
            @Override
            public void listUpdated() {}
        };
        instance.addListListener(slo);
        assertTrue(instance.getObservers().contains(slo));
        instance.addListListener(slo);
        assertTrue(instance.getObservers().contains(slo));
    }
    
    @Test
    public void testGameManipulation(){
        GameModel game1 = new GameModel(1, "Test Game", "Test Game Description", null, new Date(), null, GameStatus.PENDING);
        GameModel game2 = new GameModel(2, "Test Game 2", "Test Game Description 2", null, new Date(), null, GameStatus.COMPLETE);
        instance.addGame(game1);
        instance.addGame(game2);
        assertTrue(instance.getGames().contains(game1));
        assertTrue(instance.getGames().contains(game2));
        instance.removeGame(game1);
        assertTrue(instance.getGames().contains(game2));
        assertFalse(instance.getGames().contains(game1));
        instance.addGame(game1);
        instance.emptyModel();
        assertFalse(instance.getGames().contains(game1));
        assertFalse(instance.getGames().contains(game2));
    }
}
