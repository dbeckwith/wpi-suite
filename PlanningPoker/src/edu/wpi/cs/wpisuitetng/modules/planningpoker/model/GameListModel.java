package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * Stores a list of games and their statuses
 * 
 * @author Akshay, Andrew
 * 
 */
public class GameListModel extends AbstractListModel<GameModel> {
    
    private static GameListModel instance;
    
    /**
     * Retrieves the singleton GameListModel
     * 
     * @return Only GameListModel instance
     */
    public static GameListModel getInstance() {
        if (GameListModel.instance == null) {
            GameListModel.instance = new GameListModel();
        }
        return GameListModel.instance;
    }
    
    private static final long serialVersionUID = -4216338772150454616L;
    
    private ArrayList<GameModel> games;
    private ArrayList<SimpleListObserver> observers;
    private GetGamesController gameRetrievalController;
    private Timer autoRefresh;
    
    /**
     * Constructor that initializes list of games, list of observers, a
     * controller to service DB retrieval requests,
     * and a timer to periodically refresh the list of games.
     */
    public GameListModel() {
        games = new ArrayList<>();
        observers = new ArrayList<SimpleListObserver>();
        gameRetrievalController = GetGamesController.getInstance();
        final int timerDelay = 15000; //milliseconds
        ActionListener checkDB = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gameRetrievalController.retrieveGames();
                    autoRefresh.setDelay(timerDelay);
                }
                catch (NullPointerException err) {
                    autoRefresh.setDelay(autoRefresh.getDelay() + 15000);
                };
                updated();
            }
        };
        autoRefresh = new Timer(timerDelay, checkDB);
        autoRefresh.start();
    }
    
    /**
     * Add a SimpleListObserver that is notified when the list of games is
     * changed
     * 
     * @param slo
     *        The SimpleListObserver to add
     */
    public void addListListener(SimpleListObserver slo) {
        if (!observers.contains(slo)) {
            observers.add(slo);
        }
    }
    
    /**
     * Add a game to the list
     * 
     * @param req
     *        The game to add
     * @param status
     *        The game's status
     */
    public void addGame(GameModel g) {
        games.add(g);
        updated();
    }
    
    /**
     * Removes a game from the list. Doesn't do anything if the game is not in
     * the list
     * 
     * @param req
     *        The game to remove
     */
    public void removeGame(GameModel g) {
        if (games.contains(g)) {
            games.remove(g);
            updated();
        }
    }
    
    /**
     * Empties the list of games.
     */
    public void emptyModel() {
        games.clear();
    }
    
    /**
     * @return the list of games
     */
    public ArrayList<GameModel> getGames() {
        return games;
    }
    
    /**
     * Notifies all observers when that the list has changed
     */
    private void updated() {
        for (SimpleListObserver observer : observers) {
            observer.listUpdated();
        }
    }
    
    @Override
    /**
     * @return the size of the list of games
     */
    public int getSize() {
        return games.size();
    }
    
    @Override
    /**
     * @param index
     *      the index of the element to retrieve in the list of games
     * @return the game in the list at index
     */
    public GameModel getElementAt(int index) {
        return games.get(index);
    }
    
}
