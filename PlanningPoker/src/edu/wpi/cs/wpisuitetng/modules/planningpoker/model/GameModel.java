package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * Represents a planning poker game
 * 
 * @author Akshay
 * 
 */
public class GameModel extends AbstractModel {
    
    public static enum GameStatus {
        PENDING("Pending"), COMPLETE("Complete");
        
        public String name;
        
        GameStatus(String stat) {
            name = stat;
        }
    };
    
    
    public static enum GameType {
        LIVE, DISTRIBUTED
    };
    
    private ArrayList<SimpleListObserver> observers;
    
    private int id;
    private String name;
    private String description;
    private ArrayList<GameRequirementModel> requirements;
    private Date endDate;
    private GameType type;
    private GameStatus status;
    
    /**
     * Default constructor creates instance with invalid id and null fields
     */
    public GameModel() {
        id = -1;
        name = null;
        description = null;
        requirements = null;
        endDate = null;
        type = null;
        status = null;
        observers = null;
    }
    
    /**
     * Constructor
     * 
     * @param id
     * @param name
     * @param description
     * @param requirements
     * @param end
     * @param type
     * @param status
     */
    public GameModel(int id, String name, String description,
            ArrayList<GameRequirementModel> requirements, Date end,
            GameType type, GameStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        endDate = end;
        this.type = type;
        this.status = status;
        observers = new ArrayList<SimpleListObserver>();
    }
    
    /**
     * Constructor
     * 
     * @param name
     * @param description
     * @param requirements
     * @param end
     * @param type
     * @param status
     */
    public GameModel(String name, String description,
            ArrayList<GameRequirementModel> requirements, Date end,
            GameType type, GameStatus status) {
        id = -1;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        endDate = end;
        this.type = type;
        this.status = status;
        observers = new ArrayList<SimpleListObserver>();
    }
    
    /**
     * @return the name of this game
     */
    public String getName() {
        return name;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @return the name of this game
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Add a SimpleListObserver that is notified when the list of estimates is
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
     * Add a user's estimate to the list
     * 
     * @param user
     * @param estimate
     */
    public void addEstimate(Estimate e, int reqIndex) {
        requirements.get(reqIndex).addEstimate(e);
        updated();
    }
    
    /**
     * @return an array containing all of the estimates
     */
    public ArrayList<Estimate> getEstimates(int reqIndex) {
        return requirements.get(reqIndex).getEstimates();
    }
    
    /**
     * @return The Requirements for this game
     */
    public ArrayList<GameRequirementModel> getRequirements() {
        return requirements;
    }
    
    /**
     * @return The end time for this game
     */
    public Date getEndTime() {
        return endDate;
    }
    
    /**
     * Returns which type of game this is
     * 
     * @return Either TYPE_LIVE or TYPE_DISTRIBUTED
     */
    public GameType getType() {
        return type;
    }
    
    /**
     * Manually set the game to ended
     * 
     * @param fin
     *        whether or not the game should be ended
     */
    public void setEnded(boolean fin) {
        status = fin ? GameStatus.COMPLETE : GameStatus.PENDING;
    }
    
    /**
     * If the current time is past the end date of the game, set the game as ended.
     * 
     * @return whether the game has ended
     */
    public boolean isEnded() {
        if (endDate != null && (endDate.before(new Date(System.currentTimeMillis())))) {
            setEnded(true);
        }
        return (status == GameStatus.COMPLETE);
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
    public void save() {
        
    }
    
    @Override
    public void delete() {
        
    }
    
    @Override
    public String toJSON() {
        return new Gson().toJson(this, GameModel.class);
    }
    
    @Override
    public Boolean identify(Object o) {
        return null;
    }
    
    
    public static GameModel fromJSON(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, GameModel.class);
    }
    
    public static GameModel[] fromJSONArray(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, GameModel[].class);
    }
    
    public int getID() {
        return id;
    }
    
    public GameStatus getStatus() {
        return status;
    }
    
    public void copyFrom(GameModel g) {
        id = g.id;
        name = g.name;
        description = g.description;
        requirements = g.requirements;
        endDate = g.endDate;
        type = g.type;
        status = g.status;
        observers = g.observers;
    }
    
    /**
     * 
     * @return the simplelistobservers for the list of games
     */
    public ArrayList<SimpleListObserver> getObservers(){
        return observers;
    }
        
    @Override
    public String toString() {
        return getName();
    }
    
}
