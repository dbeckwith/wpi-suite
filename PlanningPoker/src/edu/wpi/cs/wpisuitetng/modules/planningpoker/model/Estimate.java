/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This class represents an estimate made by a user for a requirement of how
 * much value they think a requirement should be worth.
 * 
 * @author Team 9
 * @version 1.0
 */
public class Estimate extends AbstractModel implements Comparable<Estimate> {
    
    private final String name;
    private final String username;
    private final int idNum;
    private final float theEstimate;
    private final List<Integer> cardsSelected;
    
    /**
     * Creates a new Estimate.
     * 
     * @param user
     *            the user who made the estimate
     * @param estimate
     *            the user's estimate value
     * @param cards
     *            the cards that the user selected to make their estimate
     */
    public Estimate(User user, float estimate, List<Integer> cards) {
        name = user.getName();
        username = user.getUsername();
        idNum = user.getIdNum();
        theEstimate = estimate;
        cardsSelected = cards;
    }
    
    /**
     * Gets the list of cards that the user selected in order to make this
     * estimate.
     * 
     * @return the list of card values
     */
    public List<Integer> getCardsSelected() {
        return cardsSelected;
    }
    
    /**
     * Gets the name of the user that made this estimate.
     * 
     * @see User
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the username of the user that made this estimate.
     *
     * @see User
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the ID number of the user that made this estimate.
     *
     * @see User
     * @return the user's ID number
     */
    public int getIdNum() {
        return idNum;
    }
    
    /**
     * Gets the estimate value that was made by the user.
     * 
     * @return the estimate value
     */
    public float getEstimate() {
        return theEstimate;
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
    
    /**
     * Creates an Estimate object from a JSON string
     *
     * @param json
     * @return Estimate from the JSON string
     */
    public static Estimate fromJSON(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, Estimate.class);
    }
    
    @Override
    public int compareTo(Estimate arg0) {
        int toReturn;
        if (theEstimate > arg0.getEstimate()) {
            toReturn = 1;
        }
        else if (Math.abs(theEstimate - arg0.getEstimate()) < 0.0001) {
            toReturn = 0;
        }
        else {
            toReturn = -1;
        }
        return toReturn;
    }
    
}
