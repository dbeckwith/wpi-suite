package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
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
    ArrayList<GameRequirementModel> reqs;
    
    @Before
    public void setUp() {
        reqs = new ArrayList<GameRequirementModel>();
        GameRequirementModel aReq = new GameRequirementModel(1, "Req name",
                "Req desc", "User Story", new ArrayList<Estimate>());
        reqs.add(aReq);
        nullGame = new GameModel();
        game1 = new GameModel(1, "Test Game 1", "Live Game that just ended",
                reqs, new Date(), GameType.LIVE, GameStatus.COMPLETE);
        game2 = new GameModel(2, "Test Game 2",
                "Distributed Game that will end in 5 seconds", reqs, new Date(
                        System.currentTimeMillis() + 5000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        game3 = new GameModel(
                3,
                "Test Game 3",
                "Live Game with end time in 10 seconds, but already manually ended",
                reqs, new Date(System.currentTimeMillis() + 10000),
                GameType.LIVE, GameStatus.COMPLETE);
        game4 = new GameModel(
                4,
                "Test Game 4",
                "Distributed Game that has end time 10 seconds ago but hasn't been updated to be complete yet",
                reqs, new Date(System.currentTimeMillis() - 10000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
    }
    
    @Test
    public void testSetEnded() {
        game1.setEnded(true);
        Assert.assertEquals(GameStatus.COMPLETE, game1.getStatus());
        game2.setEnded(true);
        Assert.assertEquals(GameStatus.COMPLETE, game2.getStatus());
        game3.setEnded(false);
        Assert.assertEquals(GameStatus.PENDING, game3.getStatus());
    }
    
    @Test
    public void testIsEnded() {
        Assert.assertTrue(game1.isEnded());
        Assert.assertFalse(game2.isEnded());
        Assert.assertTrue(game3.isEnded());
        Assert.assertTrue(game4.isEnded());
    }
    
    @Test
    public void testJSON() {
        assertEquals(game1.getName(), GameModel.fromJSON(game1.toJSON()).getName());
        assertEquals(game2.getName(), GameModel.fromJSONArray("[" + game2.toJSON() + "]")[0].getName());
    }
}
