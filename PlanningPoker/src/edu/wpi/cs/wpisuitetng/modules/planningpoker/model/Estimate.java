package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class Estimate extends AbstractModel implements Comparable<Estimate> {
    
    private String name;
    private String username;
    private int idNum;
    private float estimate;
    
    public Estimate(User user, float estimate) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.idNum = user.getIdNum();
        this.estimate = estimate;
    }
    
    public String getName() {
        return name;
    }
    
    public String getUsername() {
        return username;
    }
    
    public int getIdNum() {
        return idNum;
    }
    
    public float getEstimate() {
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
    
    public Estimate fromJSON(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, Estimate.class);
    }
    
    @Override
    public int compareTo(Estimate arg0) {
        if (estimate > arg0.getEstimate()) {
            return 1;
        }
        else if (estimate == arg0.getEstimate()) {
            return 0;
        }
        else {
            return -1;
        }
    }
    
}
