/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGamesController;

/**
 * This class represents a planning poker game
 * 
 * @author Team 9
 * @version 1.0
 * 
 *          Represents a planning poker game
 */
public class GameModel extends AbstractModel implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1255801057004044696L;
    
    /**
     * An enumeration representing the different states of a game.
     */
    public static enum GameStatus {
        NEW("New"), PENDING("Pending"), COMPLETE("Complete"), CLOSED("Closed");
        
        private final String name;
        
        /**
         * Creates a new GameStatus
         * 
         * @param stat
         *            the name of the status
         */
        GameStatus(String stat) {
            name = stat;
        }
        
        /**
         * 
         * Gets the name of this status
         * 
         * @return the name
         */
        public String getName() {
            return name;
        }
    };
    
    private transient ArrayList<GameStatusObserver> status_observers;
    
    private long id;
    private String name;
    private String description;
    private List<GameRequirementModel> requirements;
    private Date endDate;
    private GameStatus status;
    private String owner;
    private DeckModel deck;
    private static int nextId = 0;
    
    /**
     * Default constructor creates instance with invalid id and null fields.
     * This constructor should generally not be used except for database
     * queries.
     */
    public GameModel() {
        this(null, // name
                null, // description
                null, // requirements
                null, // deck
                null, // end date
                null, // status
                null); // owner
    }
    
    /**
     * Creates a new planning poker game.
     * 
     * @param name
     *            the game's name
     * @param description
     *            the description of the game
     * @param requirements
     *            a list of the requirements to be estimated for this game
     * @param deck
     *            the deck of cards users can use to estimate requirements for
     *            this game
     * @param end
     *            the deadline for this game
     * @param status
     *            what the current status of this game should be
     */
    public GameModel(String name, String description,
            List<GameRequirementModel> requirements, DeckModel deck, Date end,
            GameStatus status) {
        this(name, description, requirements, deck, end, status, ConfigManager
                .getConfig().getUserName());
    }
    
    /**
     * Creates a new planning poker game.
     * 
     * @param name
     *            the game's name
     * @param description
     *            the description of the game
     * @param requirements
     *            a list of the requirements to be estimated for this game
     * @param deck
     *            the deck of cards users can use to estimate requirements for
     *            this game
     * @param endDate
     *            the deadline for this game
     * @param status
     *            what the current status of this game should be
     * @param owner
     *            the name of the user who created this game
     */
    public GameModel(String name, String description,
            List<GameRequirementModel> requirements, DeckModel deck,
            Date endDate, GameStatus status, String owner) {
        id = System.currentTimeMillis();
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.deck = deck;
        this.endDate = endDate;
        this.status = status;
        this.owner = owner;
        status_observers = new ArrayList<>();
    }
    
    /**
     * Gets the name of this game.
     * 
     * @return the name of this game
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the unique ID number of this game.
     * 
     * @param id
     *            the new ID number
     */
    public void setID(long id) {
        this.id = id;
    }
    
    /**
     * Gets the description of this game.
     * 
     * @return the description of this game
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the name of the user who created this game.
     * 
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }
    
    /**
     * Adds a GameStatusObserver to the list of status observers
     * 
     * @param gso
     *            the GameStatusObserver to add
     */
    public void addStatusListener(GameStatusObserver gso) {
        if (!status_observers.contains(gso)) {
            status_observers.add(gso);
        }
    }
    
    /**
     * Removes a GameStatusObserver from the list of status observers
     * 
     * @param gso
     *            the GameStatusObserver to remove
     */
    public void removeStatusListener(GameStatusObserver gso) {
        if (status_observers.contains(gso)) {
            status_observers.remove(gso);
        }
    }
    
    /**
     * Returns the list of estimates for a given requirement
     * 
     * @param reqIndex
     *            the index of the requirement in the list of requirements
     * @return an array containing all of the estimates
     */
    public List<Estimate> getEstimates(int reqIndex) {
        return requirements.get(reqIndex).getEstimates();
    }
    
    /**
     * Gets the list of requirements for this game.
     * 
     * @return the requirements for this game
     */
    public List<GameRequirementModel> getRequirements() {
        return requirements;
    }
    
    /**
     * Sets the game's status to pending if it is new
     */
    public void startGame() {
        if (status == GameStatus.NEW) {
            status = GameStatus.PENDING;
            for (GameStatusObserver gso : status_observers) {
                gso.statusChanged(this);
            }
        }
    }
    
    /**
     * Gets the deck that users can use to estimate requirements of this game.
     * 
     * @return the deck for this game
     */
    public DeckModel getDeck() {
        return deck;
    }
    
    /**
     * Gets the deadline time for this game.
     * 
     * @return the end time for this game
     */
    public Date getEndTime() {
        return endDate;
    }
    
    /**
     * Checks if the deadline for this game has passed
     * 
     * @return if the deadline for this game has passed
     */
    public boolean deadlinePassed() { // $codepro.audit.disable booleanMethodNamingConvention
        final Date currDate = new Date();
        return ((endDate != null) && endDate.before(currDate));
    }
    
    /**
     * Manually set the game to ended
     * 
     * @param fin
     *            whether or not the game should be ended
     */
    public void setEnded(boolean fin) {
        final GameStatus new_status = fin ? GameStatus.COMPLETE
                : GameStatus.PENDING;
        if (status != new_status && status == GameStatus.PENDING) {
            status = new_status;
            endDate = new Date();
            for (int i = 0; i < status_observers.size(); i++) {
                status_observers.get(i).statusChanged(this);
            }
            UpdateGamesController.getInstance().updateGame(this);
        }
    }
    
    /**
     * Checks if all users have voted on all requirements
     * 
     * @return whether all users have voted on all requirements
     */
    public boolean checkVoted() { // $codepro.audit.disable booleanMethodNamingConvention
        boolean toReturn = true;
        int returnFlag = 1;

        final ArrayList<User> users;
        if(CurrentUserController.getInstance().getUsers() != null){
            users = new ArrayList<User>(Arrays.asList(CurrentUserController
                    .getInstance().getUsers()));
        }
        else {
            users = new ArrayList<User>();
        }
        
        if (requirements == null) {
            
            toReturn = false;
            returnFlag = 2;
        }
        if (returnFlag == 1) {
            for (GameRequirementModel r : requirements) {
                if (!r.allVoted()) {
                    
                    toReturn = false;
                    returnFlag = 3;
                    break;
                }
            }
        }
        if (returnFlag == 1) {
            CurrentUserController.getInstance().requestUsers();
            try {
                Thread.sleep(250);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            final ArrayList<User> nowUsers;
            if(CurrentUserController.getInstance().getUsers() != null) {
                nowUsers = new ArrayList<User>(Arrays.asList(CurrentUserController.getInstance().getUsers()));
            }
            else {
                nowUsers = new ArrayList<User>();
            }
            
            if(users.containsAll(nowUsers)){
                toReturn = true;
            }
            else{
                toReturn = false;
            }
        }
        return toReturn;
    }
    
    /**
     * If the current time is past the end date of the game or all requirements
     * have been voted on by all users, set the game to ended.
     * 
     * @return whether the game has ended
     */
    public boolean isEnded() {
        if(status != GameStatus.COMPLETE && status != GameStatus.CLOSED){
            if (checkVoted()) {
                setEnded(true);
            }
        }
        return (status == GameStatus.COMPLETE || status == GameStatus.CLOSED);
    }
    
    /**
     * Returns whether the game is closed
     * 
     * @return whether the game has been closed
     */
    public boolean isClosed() {
        return (status == GameStatus.CLOSED);
    }
    
    /**
     * Sets the game status to closed so that no more edits can be made.
     */
    public void closeGame() {
        if (status != GameStatus.COMPLETE) {
            endDate = new Date();
        }
        status = GameStatus.CLOSED;
        // notify status listeners of the change
        for (int i = 0; i < status_observers.size(); i++) {
            status_observers.get(i).statusChanged(this);
        }
    }
    
    /**
     * determines if the game has been started
     * 
     * @return true if the game's status is not new
     */
    public boolean isStarted() {
        return !(status == GameStatus.NEW);
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
    
    /**
     * Creates a GameModel from a JSON string
     * 
     * @param json
     * @return GameModel object from JSON string
     */
    public static GameModel fromJSON(String json) {
        final Gson parser = new Gson();
        final GameModel gm = parser.fromJson(json, GameModel.class);
        gm.status_observers = new ArrayList<>();
        return gm;
    }
    
    /**
     * Creates an array of GameModels from a JSON array
     * 
     * @param json
     * @return Array of GameModels from the JSON array
     */
    public static GameModel[] fromJSONArray(String json) {
        final Gson parser = new Gson();
        final GameModel[] gms = parser.fromJson(json, GameModel[].class);
        for (GameModel gm : gms) {
            gm.status_observers = new ArrayList<>();
        }
        return gms;
    }
    
    public long getID() {
        return id;
    }
    
    public GameStatus getStatus() {
        return status;
    }
    
    /**
     * Copies the information from the given GameModel into this GameModel
     * 
     * @param g
     */
    public void copyFrom(GameModel g) {
        id = g.id;
        name = g.name;
        description = g.description;
        requirements = g.requirements;
        endDate = g.endDate;
        status = g.status;
        status_observers = g.status_observers;
        owner = g.owner;
        deck = g.deck;
    }
    
    /**
     * Copies the information from the given GameModel into this GameModel This
     * version is for editing a game, so it does not copy status observers or
     * the id
     * 
     * @param g
     */
    public void editCopyFrom(GameModel g) {
        name = g.name;
        description = g.description;
        requirements = g.requirements;
        endDate = g.endDate;
        status = g.status;
        owner = g.owner;
        deck = g.deck;
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
        return name;
    }
    
    /**
     * Returns whether the input GameModel is equal to this one
     * 
     * @param other
     * @return True if this GameModel is equal to the input GameModel
     */
    public boolean equals(GameModel other) { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.obeyEqualsContract.obeyGeneralContractOfEquals, lineLength
        return other.id == id && other.name.equals(name);
    }
    
    @Override
    public boolean equals(Object other) {
        boolean ret;
        if (this == other) {
            ret = true;
        } else if (other instanceof GameModel) {
            ret = this.equals((GameModel) other);
        } else {
            ret = super.equals(other);
        }
        return ret;
    }
    
    @Override
    public int hashCode() {
        return (int) id;
    }
    
    /**
     * Checks to see if this game has a deadline.
     * 
     * @return true if game has deadline
     */
    public boolean hasDeadline() {
        return endDate != null;
    }
}
