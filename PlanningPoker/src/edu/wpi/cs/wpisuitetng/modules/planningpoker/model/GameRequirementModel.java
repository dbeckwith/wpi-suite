/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * A simplified requirement model for a planning poker game
 * 
 * @author Lukas
 * 
 */
public class GameRequirementModel extends AbstractModel {
    /** the id of the parent requirement in requirements manager */
    private int parentId;
    
    /** the requirement name */
    private String name;
    
    /** the requirement description */
    private String description;
    
    /** the type of requirement */
    private String type;
    
    /** the estimates for the requirement */
    private ArrayList<Estimate> estimates;
    
    private int finalEstimate;
    
    private boolean isFromRequirementManager;
    
    private String estimateNote;
    
    /**
     * A unique id for this instance of the requirement
     */
    private int id;
    
    private static int nextId = 0;
    
    
    /**
     * Creates a new GameRequirementModel
     * 
     * @param parentId
     * @param name
     * @param description
     * @param type
     * @param estimates
     */
    public GameRequirementModel(int parentId, String name, String description,
            String type, ArrayList<Estimate> estimates) {
        this.parentId = parentId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.estimates = estimates;
        
        Collections.sort(this.estimates);
        finalEstimate = -1;
        id = nextId;
        nextId++;
        isFromRequirementManager = false;
    }
    
    /**
     * @param parentId
     * @param name
     * @param description
     * @param type
     */
    public GameRequirementModel(int parentId, String name, String description,
            String type) {
        this.parentId = parentId;
        this.name = name;
        this.description = description;
        this.type = type;
        estimates = new ArrayList<Estimate>();
        finalEstimate = -1;
        id = nextId;
        nextId++;
        isFromRequirementManager = false;
    }
    
    public GameRequirementModel() {
        this(-1, "", "", "", new ArrayList<Estimate>());
    }
    
    /**
     * Creates a new GameRequirementModel from a requirement from
     * RequirementManager
     * 
     * @param r
     */
    public GameRequirementModel(Requirement r) {
        this(r.getId(), r.getName(), r.getDescription(),
                r.getType().toString(), new ArrayList<Estimate>());
        isFromRequirementManager = true;
    }
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    
    /**
     * @return the parentId
     */
    public int getParentId() {
        return parentId;
    }
    
    /**
     * @param parentId
     *        the parentId to set
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @return the estimates
     */
    public ArrayList<Estimate> getEstimates() {
        return estimates;
    }
    
    /**
     * @return true if this requirement came from the Requirement Manager, false
     *         otherwise
     */
    public boolean isFromRequirementManager() {
        return isFromRequirementManager;
    }
    
    /**
     * Adds an estimate to the list of estimates
     * 
     * @param e
     */
    public void addEstimate(Estimate e) {
        estimates.add(e);
        Collections.sort(estimates);
    }
    
    /**
     * Updates an estimate with a new one
     * 
     * @param old
     *        the old estimate
     * @param updated
     *        the new estimate
     */
    public void UpdateEstimate(Estimate old, Estimate updated) {
        estimates.remove(old);
        estimates.add(updated);
        Collections.sort(estimates);
    }
    
    /**
     * Computes the median of all of the estimates
     * 
     * @return the median
     */
    public float getEstimateMedian() {
        float toReturn;
        if (estimates.isEmpty()) {
            toReturn = 0;
        }
        else {
            final ArrayList<Estimate> estimates_copy = new ArrayList<>(
                    estimates);
            Collections.sort(estimates_copy);
            final int count = estimates_copy.size();
            if (estimates_copy.size() % 2 == 1) {
                toReturn = estimates_copy.get(count / 2).getEstimate();
            }
            else {
                toReturn = (estimates_copy.get(count / 2).getEstimate() + estimates_copy
                        .get(count / 2 - 1).getEstimate()) / 2;
            }
        }
        return toReturn;
    }
    
    /**
     * Computes the numerical average of all of the estimates
     * 
     * @return the mean (average)
     */
    public float getEstimateMean() {
        float toReturn = 0;
        if (!estimates.isEmpty()) {
            for (Estimate e : estimates) {
                toReturn += e.getEstimate() / (estimates.size());
            }
        }
        return toReturn;
    }
    
    /**
     * Determines if all users have voted on a requirement
     * 
     * @return whether all users have voted on a requirement
     */
    public boolean allVoted() {
        ArrayList<User> estimateUsers = new ArrayList<User>();
        User[] users = CurrentUserController.getInstance().getUsers();
        
        // checks to see if an all users have voted
        for (Estimate e : estimates) {
            for (User u : users) {
                if (e.getIdNum() == u.getIdNum()) {
                    estimateUsers.add(u);
                }
            }
        }
        
        for (User u : users) {
            if (!estimateUsers.contains(u)) { return false; }
        }
        return true;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
     */
    @Override
    public void delete() {
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
     */
    @Override
    public Boolean identify(Object arg0) {
        return null;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
     */
    @Override
    public void save() {
        
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
     */
    @Override
    public String toJSON() {
        return new Gson().toJson(this, Requirement.class);
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
    public int getFinalEstimate() {
        return finalEstimate;
    }
    
    public void setFinalEstimate(int finalEstimate) {
        this.finalEstimate = finalEstimate;
    }
    
    public boolean equals(GameRequirementModel other) {
        return (name.equals(other.name))
                && (description.equals(other.description));
    }
    
    @Override
    public boolean equals(Object other) {
        boolean toReturn = false;
        if (this == other) {
            toReturn = true;
        }
        else if (other instanceof GameRequirementModel) {
            toReturn = this.equals((GameRequirementModel) other);
        }
        else {
            toReturn = super.equals(other);
        }
        return toReturn;
    }
    
    /**
     * Updates the estimate for the requirement in the Requirement Manager with
     * the final estimate from Planning Poker
     */
    public void updateParentEstimate() {
        // TODO: Drew, implement this
    }

    public String getEstimateNote() {
        return estimateNote;
    }
    
    public void setEstimateNote(String estimateNote) {
        this.estimateNote = estimateNote;
    }
    
    
}
