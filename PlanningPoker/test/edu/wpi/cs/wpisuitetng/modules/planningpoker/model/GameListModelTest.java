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
        GameModel nullGame = new GameModel();
        GameModel trueGame = new GameModel(1, "Test Game", "Test Game Description", null, new Date(), null, GameStatus.COMPLETE);
        instance.addGame(nullGame);
        instance.addGame(trueGame);
        assertTrue(instance.getGames().contains(nullGame));
        assertTrue(instance.getGames().contains(trueGame));
        instance.removeGame(nullGame);
        assertTrue(instance.getGames().contains(trueGame));
        assertFalse(instance.getGames().contains(nullGame));
        instance.addGame(nullGame);
        instance.emptyModel();
        assertFalse(instance.getGames().contains(trueGame));
        assertFalse(instance.getGames().contains(nullGame));
    }
}
