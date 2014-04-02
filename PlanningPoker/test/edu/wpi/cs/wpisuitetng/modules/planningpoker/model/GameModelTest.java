package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;

/**
 * TODO: Add tests for requirement manipulation
 * 
 * @author Andrew
 *
 */
public class GameModelTest {
    
    GameModel nullGame;
    GameModel game1;
    GameModel game2;
    GameModel game3;
    GameModel game4;
    
    @Before
    public void setUp(){
        nullGame = new GameModel();
        game1 = new GameModel(1, "Test Game 1", "Live Game that just ended", null, new Date(), GameType.LIVE, GameStatus.COMPLETE, null);
        game2 = new GameModel(2, "Test Game 2", "Distributed Game that will end in 5 seconds", null, new Date(System.currentTimeMillis()+5000), GameType.DISTRIBUTED, GameStatus.PENDING, null);
        game3 = new GameModel(3, "Test Game 3", "Live Game with end time in 10 seconds, but already manually ended", null, new Date(System.currentTimeMillis()+10000), GameType.LIVE, GameStatus.COMPLETE, null);
        game4 = new GameModel(4, "Test Game 4", "Distributed Game that has end time 10 seconds ago but hasn't been updated to be complete yet", null, new Date(System.currentTimeMillis()-10000), GameType.DISTRIBUTED, GameStatus.PENDING, null);
    }
    
    @Test
    public void testAddListListener() {
        SimpleListObserver slo = new SimpleListObserver() {  
            @Override
            public void listUpdated() {}
        };
        game1.addListListener(slo);
        assertTrue(game1.getObservers().contains(slo));
        game1.addListListener(slo);
        assertTrue(game1.getObservers().contains(slo));
    }
    
    @Test
    public void testSetEnded(){
        game1.setEnded(true);
        assertEquals(GameStatus.COMPLETE, game1.getStatus());
        game2.setEnded(true);
        assertEquals(GameStatus.COMPLETE, game2.getStatus());
        game3.setEnded(false);
        assertEquals(GameStatus.PENDING, game3.getStatus());
    }
    
    @Test
    public void testIsEnded(){
        assertTrue(game1.isEnded());
        assertFalse(game2.isEnded());
        assertTrue(game3.isEnded());
        assertTrue(game4.isEnded());
    }
}
