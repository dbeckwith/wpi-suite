package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
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
    private ArrayList<GameStatusObserver> status_observers;
    private GameStatusObserver game_observer;
    
    /**
     * Constructor that initializes list of games, list of observers, a
     * controller to service DB retrieval requests, and a timer to periodically
     * refresh the list of games.
     */
    public GameListModel() {
        games = new ArrayList<>();
        observers = new ArrayList<>();
        status_observers = new ArrayList<>();
        game_observer = new GameStatusObserver() {
            
            @Override
            public void statusChanged(GameModel game) {
                for (GameStatusObserver gso : status_observers) {
                    gso.statusChanged(game);
                }
            }
        };
    }
    
    /**
     * Add a SimpleListObserver that is notified when the list of games is
     * changed
     * 
     * @param slo
     *            The SimpleListObserver to add
     */
    public void addListListener(SimpleListObserver slo) {
        if (!observers.contains(slo)) {
            observers.add(slo);
        }
    }
    
    public void addStatusListener(GameStatusObserver gso) {
        if (!status_observers.contains(gso)) {
            status_observers.add(gso);
        }
    }
    
    /**
     * Add a game to the list
     * 
     * @param g
     *            the game to add
     */
    public void addGame(GameModel g) {
        games.add(g);
        g.addStatusListener(game_observer);
        updated();
    }
    
    /**
     * Add multiple games to the list. The purpose of this method is that it
     * will add multiple games at once and fire only one update on the list.
     * 
     * @param gs
     *            the list of games to add
     */
    public void addGames(GameModel[] gs) {
        for (GameModel g : gs) {
            games.add(g);
            g.addStatusListener(game_observer);
        }
        updated();
    }
    
    /**
     * Removes a game from the list. Doesn't do anything if the game is not in
     * the list
     * 
     * @param g
     *            The game to remove
     */
    public void removeGame(GameModel g) {
        if (games.contains(g)) {
            games.remove(g);
            g.removeStatusListener(game_observer);
            updated();
        }
    }
    
    /**
     * Empties the list of games.
     */
    public void emptyModel() {
        for (GameModel g : games) {
            g.removeStatusListener(game_observer);
        }
        games.clear();
        updated();
    }
    
    /**
     * Sets the list of games to only contain the given games. The purpose of
     * this method is to be able to clear the model and add multiple games to it
     * and fire only one list updated event.
     * 
     * @param gs
     *            the games to be included in the list model
     */
    public void setGames(GameModel[] gs) {
        games.clear();
        for (GameModel g : gs) {
            games.add(g);
            g.addStatusListener(game_observer);
        }
        updated();
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
    
    /**
     * 
     * @return the simplelistobservers for the list of games
     */
    public ArrayList<SimpleListObserver> getObservers() {
        return observers;
    }
    
}
