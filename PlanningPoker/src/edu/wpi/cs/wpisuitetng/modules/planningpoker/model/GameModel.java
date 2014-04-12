/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * TODO: Contributors' names
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;

/**
 * Represents a planning poker game
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
    
    private transient ArrayList<GameStatusObserver> status_observers;
    
    private int id;
    private String name;
    private String description;
    private ArrayList<GameRequirementModel> requirements;
    private Date endDate;
    private GameType type;
    private GameStatus status;
    private String owner;
    
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
        status_observers = null;
        owner = null;
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
        status_observers = new ArrayList<>();
        owner = ConfigManager.getConfig().getUserName();
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
        status_observers = new ArrayList<>();
        owner = ConfigManager.getConfig().getUserName();
    }
    
    /**
     * @param id
     * @param name
     * @param description
     * @param requirements
     * @param endDate
     * @param type
     * @param status
     * @param owner
     */
    public GameModel(int id, String name, String description,
            ArrayList<GameRequirementModel> requirements, Date endDate,
            GameType type, GameStatus status, String owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.endDate = endDate;
        this.type = type;
        this.status = status;
        this.owner = owner;
        status_observers = new ArrayList<>();
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
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    public void addStatusListener(GameStatusObserver gso) {
        if (!status_observers.contains(gso)) {
            status_observers.add(gso);
        }
    }
    
    public void removeStatusListener(GameStatusObserver gso) {
        if (status_observers.contains(gso)) {
            status_observers.remove(gso);
        }
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
     *            whether or not the game should be ended
     */
    public void setEnded(boolean fin) {
        GameStatus new_status = fin ? GameStatus.COMPLETE : GameStatus.PENDING;
        if (status != new_status) {
            status = new_status;
            for (int i = 0; i < status_observers.size(); i++) {
                status_observers.get(i).statusChanged(this);
            }
        }
    }
    
    /**
     * If the current time is past the end date of the game, set the game as
     * ended.
     * 
     * @return whether the game has ended
     */
    public boolean isEnded() {
        if (endDate != null
                && (endDate.before(new Date(System.currentTimeMillis())))) {
            setEnded(true);
        }
        return (status == GameStatus.COMPLETE);
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
        GameModel gm = parser.fromJson(json, GameModel.class);
        gm.status_observers = new ArrayList<>();
        return gm;
    }
    
    public static GameModel[] fromJSONArray(String json) {
        final Gson parser = new Gson();
        GameModel[] gms = parser.fromJson(json, GameModel[].class);
        for (GameModel gm : gms) {
            gm.status_observers = new ArrayList<>();
        }
        return gms;
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
        status_observers = g.status_observers;
    }
    
    /**
     * 
     * @return the simplelistobservers for the list of games
     */
    public ArrayList<GameStatusObserver> getStatusObservers() {
        return status_observers;
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
    public boolean equals(GameModel other) {
        return other.id == id && other.name.equals(other.name);
    }
    
    public boolean equals(Object other) {
        if (this == other)
            return true;
        else if (other instanceof GameModel)
            return this.equals((GameModel) other);
        else
            return super.equals(other);
    }
    
}
