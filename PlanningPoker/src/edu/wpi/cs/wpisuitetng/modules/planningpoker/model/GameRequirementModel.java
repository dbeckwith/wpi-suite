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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetParentRequirementController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * A simplified requirement model for a planning poker game
 * 
 * @author Team 9
 * @version 1.0
 */
public class GameRequirementModel extends AbstractModel {
    /**
     * the id of the parent requirement in requirements manager
     */
    private int parentId;
    private int parentEstimate = 0;
    
	private String name;
	private String description;
	private String type;
	private ArrayList<Estimate> estimates;
	private int finalEstimate;
	
	private String estimateNote;
	private boolean fromRequirementManager = false;
	/**
	 * A unique id for this instance of the requirement
	 */
	private int id;
	
	private static int nextId = 0;
	

    /**
     * Creates a new GameRequirementModel.<br>
     * The list of estimates passed in is sorted.
     * 
     * @param parentId
     *            the ID number of this requirement's parent requirement in
     *            requirements manager
     * @param name
     *            the name of the requirement
     * @param description
     *            the requirement's description
     * @param type
     *            the type of requirement
     * @param estimates
     *            the list of estimates that have been made so far for this
     *            requirement
     */
	public GameRequirementModel(int parentId, String name, String description,
			String type, ArrayList<Estimate> estimates) {
		this.parentId = parentId;
		this.name = name;
		this.description = description;
		this.type = type;
		this.estimates = estimates;

		Collections.sort(this.estimates);
		finalEstimate = 0;
		id = nextId;
		nextId++;
		estimateNote = "";
	}

    /**
     * Creates a new GameRequirementModel with no estimates made so far.
     * 
     * @param parentId
     *            the ID number of this requirement's parent requirement in
     *            requirements manager
     * @param name
     *            the name of the requirement
     * @param description
     *            the requirement's description
     * @param type
     *            the type of requirement
     */
	public GameRequirementModel(int parentId, String name, String description,
			String type) {
	    this(parentId, name, description, type, new ArrayList<Estimate>());
	}

    /**
     * Creates a new GameRequirementModel with all blank fields. This
     * constructor should not generally be used except in database queries.
     */
	public GameRequirementModel() {
		this(0, "", "", "", new ArrayList<Estimate>());
	}

    /**
     * Creates a new GameRequirementModel from a requirement from
     * RequirementManager.
     * 
     * @param r
     *            the Requirement to model this GameRequirementModel off of
     */
	public GameRequirementModel(Requirement r) {
		this(r.getId(), r.getName(), r.getDescription(),
				r.getType().toString(), new ArrayList<Estimate>());
		fromRequirementManager = true;
		parentEstimate = r.getEstimate();
	}

	/**
	 * Gets the unique ID of this game requirement.
     * @return the estimateNote
     */
    public String getEstimateNote() {
        return estimateNote;
    }

    /**
     * @param estimateNote the estimateNote to set
     */
    public void setEstimateNote(String estimateNote) {
        this.estimateNote = estimateNote;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the unique ID of this requirement's parent requirement.
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent ID of this requirement.
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the type of requirement this is.
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the name of this requirement.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description of this requirement.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the list of estimates made so far for this requirement.
	 * @return the estimates
	 */
	public ArrayList<Estimate> getEstimates() {
		return estimates;
	}
    
    /**
     * Adds an estimate to the list of estimates for this requirement.<br>
     * This method also sorts the list of estimates after adding an estimate, so
     * the list is always kept sorted.
     * 
     * @param e
     *            the estimate to add
     */
	public void addEstimate(Estimate e) {
		estimates.add(e);
		Collections.sort(estimates);
	}

    /**
     * Updates an estimate with a new one by removing the old estimate and
     * adding the new one.
     * 
     * @param old
     *            the old estimate
     * @param updated
     *            the new estimate
     */
    public void UpdateEstimate(Estimate old, Estimate updated) {
		estimates.remove(old);
		estimates.add(updated);
		Collections.sort(estimates);
	}

    /**
     * Computes the median value of all of the estimates.
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
     * Computes the numerical average of all of the estimates.
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
     * Determines if all users have voted on a requirement.
     * 
     * @return true if all users have voted on a requirement, false otherwise
     */
    public boolean allVoted() {
		final ArrayList<User> estimateUsers = new ArrayList<User>();
		final User[] users = CurrentUserController.getInstance().getUsers();

		// checks to see if an all users have voted
		for (Estimate e : estimates) {
			for (User u : users) {
				if (e.getIdNum() == u.getIdNum()) {
					estimateUsers.add(u);
				}
			}
		}

		for (int i = 0; i < users.length; i++) {
		    if (!estimateUsers.contains(users[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void delete() {
	}

	@Override
	public Boolean identify(Object arg0) {
		return null;
	}

	@Override
	public void save() {

	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this, Requirement.class);
	}

	@Override
	public String toString() {
		return name;
	}

    /**
     * Gets the final estimate made for this requirement.<br>
     * The final estimate is meant to be creater of the game's final decision as
     * to what the value of the requirement should be.
     * 
     * @return the final estimate
     */
	public int getFinalEstimate() {
		return finalEstimate;
	}
    
    /**
     * Sets the final estimate of this requirement.
     * 
     * @param finalEstimate
     *            the final estimate
     */
	public void setFinalEstimate(int finalEstimate) {
		this.finalEstimate = finalEstimate;
    }
    
    /**
     * Tests to see if this requirement is equivalent to another. Two
     * requirements are equivalent if they have the same name and description.
     * 
     * @param other
     *            the other requirement to compare to
     * @return true if the two requirements are considered equal, false
     *         otherwise
     */    
    @Override
    public boolean equals(Object other) {
        boolean toReturn = false;
        if (this == other) {
            toReturn = true;
        }
        else if (other instanceof GameRequirementModel) {
            toReturn = (name.equals(((GameRequirementModel)other).name))
                    && (description.equals(((GameRequirementModel)other).description));
        }
        else {
            toReturn = super.equals(other);
        }
        return toReturn;
    }
    
    /**
     * 
     */
    public void updateRequirementManager() {
        GetRequirementsController.getInstance().retrieveRequirements();
        Requirement parent = GetParentRequirementController.getInstance().getParentRequirement(parentId);
        parent.setEstimate(finalEstimate);
        UpdateRequirementController.getInstance().updateRequirement(parent);
        RequirementModel.getInstance().getRequirement(parentId).setEstimate(finalEstimate);
        ViewEventController.getInstance().refreshTable();
        parentEstimate = finalEstimate;
    }
    
    /**
     * @return True if this requirement came from the requirement manager
     */
    public boolean isFromRequirementManager() {
        return fromRequirementManager;
    }
    
    /**
     * @return The estimate for the parent requirement
     */
    public int getParentEstimate() {
        return parentEstimate;
    }
    
}
