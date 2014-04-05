package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;

public class GetGamesControllerTest {
    
    static GetGamesController instance;
    static GameModel nullGame;
    static GameModel game1;
    static GameModel game2;
    static GameModel game3;
    static GameModel game4;
    static GameListModel list;
    static GameModel[] gamesToAdd;
    
    @BeforeClass
    static public void setUpBeforeClass() {
        GetGamesControllerTest.instance = GetGamesController.getInstance();
        GetGamesControllerTest.nullGame = new GameModel();
        GetGamesControllerTest.game1 = new GameModel(1, "Test Game 1",
                "Live Game that just ended", null, new Date(), GameType.LIVE,
                GameStatus.COMPLETE);
        GetGamesControllerTest.game2 = new GameModel(2, "Test Game 2",
                "Distributed Game that will end in 5 seconds", null, new Date(
                        System.currentTimeMillis() + 5000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        GetGamesControllerTest.game3 = new GameModel(
                3,
                "Test Game 3",
                "Live Game with end time in 10 seconds, but already manually ended",
                null, new Date(System.currentTimeMillis() + 10000),
                GameType.LIVE, GameStatus.COMPLETE);
        GetGamesControllerTest.game4 = new GameModel(
                4,
                "Test Game 4",
                "Distributed Game that has end time 10 seconds ago but hasn't been updated to be complete yet",
                null, new Date(System.currentTimeMillis() - 10000),
                GameType.DISTRIBUTED, GameStatus.PENDING);
        GetGamesControllerTest.list = GameListModel.getInstance();
        GetGamesControllerTest.gamesToAdd = new GameModel[] {
                GetGamesControllerTest.game1, GetGamesControllerTest.game2,
                GetGamesControllerTest.game3, GetGamesControllerTest.game4 };
    }
    
    @Test
    public void testGetInstance() {
        Assert.assertEquals(
                "A new instance is not the same as the previous instance",
                GetGamesControllerTest.instance,
                GetGamesController.getInstance());
    }
    
    @Test
    public void testReceivedGames() {
        GetGamesControllerTest.instance.receivedGames(new GameModel[] {
                GetGamesControllerTest.game1, GetGamesControllerTest.game2,
                GetGamesControllerTest.game3, GetGamesControllerTest.game4 });
        Assert.assertTrue(GameListModel.getInstance().getGames()
                .contains(GetGamesControllerTest.game1));
        Assert.assertTrue(GameListModel.getInstance().getGames()
                .contains(GetGamesControllerTest.game2));
        Assert.assertTrue(GameListModel.getInstance().getGames()
                .contains(GetGamesControllerTest.game3));
        Assert.assertTrue(GameListModel.getInstance().getGames()
                .contains(GetGamesControllerTest.game4));
    }
    
}
