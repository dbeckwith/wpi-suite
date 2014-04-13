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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class Estimate extends AbstractModel implements Comparable<Estimate> {
    
    private final String name;
    private final String username;
    private final int idNum;
    private final float estimate;
    private final ArrayList<Integer> cardsSelected;
    
    public Estimate(User user, float estimate, ArrayList<Integer> cards) {
        name = user.getName();
        username = user.getUsername();
        idNum = user.getIdNum();
        this.estimate = estimate;
        cardsSelected = cards;
    }
    
    /**
     * @return the cardsSelected
     */
    public ArrayList<Integer> getCardsSelected() {
        return cardsSelected;
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
