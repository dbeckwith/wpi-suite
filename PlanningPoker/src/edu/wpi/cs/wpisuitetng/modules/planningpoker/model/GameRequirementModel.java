/**
 * 
 */
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

	public GameRequirementModel(int parentId, String name, String description,
			String type, ArrayList<Estimate> estimates) {
		this.parentId = parentId;
		this.name = name;
		this.description = description;
		this.type = type;
		this.estimates = estimates;

		Collections.sort(this.estimates);
	}

	/**
	 * @param parentId
	 * @param name
	 * @param description
	 */
	public GameRequirementModel(int parentId, String name, String description,
			String type) {
		this.parentId = parentId;
		this.name = name;
		this.description = description;
		this.type = type;
		estimates = new ArrayList<Estimate>();
	}

	public GameRequirementModel() {
		this(-1, "", "", "", new ArrayList<Estimate>());
	}

	public GameRequirementModel(Requirement r) {
		this(r.getId(), r.getName(), r.getDescription(),
				r.getType().toString(), new ArrayList<Estimate>());
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
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

	public void addEstimate(Estimate e) {
		estimates.add(e);
		Collections.sort(estimates);
	}

	/**
	 * Computes the median of all of the estimates
	 * 
	 * @return the median
	 */
	public float getEstimateMedian() {
		if (estimates.isEmpty()) {
			return 0;
		}
		ArrayList<Estimate> estimates_copy = new ArrayList<>(estimates);
		Collections.sort(estimates_copy);
		int count = estimates_copy.size();
		if (estimates_copy.size() % 2 == 1) {
			return estimates_copy.get(count / 2).getEstimate();
		} else {
			return (estimates_copy.get(count / 2).getEstimate() + estimates_copy
					.get(count / 2 - 1).getEstimate()) / 2;
		}
	}

	/**
	 * Computes the numerical average of all of the estimates
	 * 
	 * @return the mean (average)
	 */
	public float getEstimateMean() {
		if (estimates.isEmpty()) {
			return 0;
		}
		float mean = 0;
		for (Estimate e : estimates) {
			mean += e.getEstimate() / (estimates.size());
		}
		return mean;
	}

	/**
	 * Determines if all users have voted on a requirement
	 * 
	 * @return boolean
	 */
	public boolean allVoted(GameModel g) {
		ArrayList<User> estimateUsers = new ArrayList<User>();
		User[] users = CurrentUserController.getInstance().getUsers();
		
		while (users == null) {
			users = CurrentUserController.getInstance().getUsers();
		}
		
		System.out.println(estimates.size() + " " + users.length);
		for (Estimate e : estimates) {
			estimateUsers.add(e.getUser());
		}

		for (User u : users) {
			if (!estimateUsers.contains(u)) {
				System.out.println("User not found" + u);
				return false;
			}
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

}
