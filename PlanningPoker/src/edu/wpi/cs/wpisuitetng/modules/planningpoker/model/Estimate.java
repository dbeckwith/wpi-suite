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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class Estimate extends AbstractModel implements Comparable<Estimate> {
    
    private final User user;
    private final float estimate;
    
    public Estimate(User user, float estimate) {
        this.user = user;
        this.estimate = estimate;
    }
    
    public User getUser() {
        return user;
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
