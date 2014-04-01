package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class Estimate extends AbstractModel implements Comparable<Estimate>{
	
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

    @Override
    public int compareTo(Estimate arg0) {
        if(estimate > arg0.getEstimate()){
            return 1;
        }
        else if (estimate == arg0.getEstimate()){
            return 0;
        }
        else{
            return -1;
        }
    }

}
