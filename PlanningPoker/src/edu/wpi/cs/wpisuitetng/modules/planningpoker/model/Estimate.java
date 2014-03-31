package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class Estimate extends AbstractModel {
	
	private User user;
	private float estimate;
	
	public Estimate(User user, float estimate){
		this.user = user;
		this.estimate = estimate;
	}
	
	public User getUser(){
		return user;
	}
	
	public float getEstimate(){
		return estimate;
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
		return new Gson().toJson(this, Estimate.class);
	}

}
