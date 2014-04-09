package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.util.TimerTask;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;

/**
 * The purpose of this class is to define a TimerTask that updates the completed status of games once they have passed their end date.
 * @author dbeckwith
 *
 */
public class GameTimeoutTask extends TimerTask {
    
    private GameModel game;
    
    /**
     * Constructs a new task that will update the given game.
     * @param game
     */
    public GameTimeoutTask(GameModel game) {
        this.game = game;
    }
    
    @Override
    public void run() {
        System.out.println("Game \"" + game + "\" is expiring!");
        game.isEnded();
        GameTimeoutWatcher.getInstance().stopWatchingGame(game);
    }
    
    /**
     * Get the game that this task is associated with.
     * @return
     */
    public GameModel getGame() {
        return game;
    }
    
}