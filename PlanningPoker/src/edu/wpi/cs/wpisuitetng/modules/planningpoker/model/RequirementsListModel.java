/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * A list model for keeping track of all of the requirements in the database.
 */
public class RequirementsListModel extends
        AbstractListModel<GameRequirementModel> {
    
    private static final long serialVersionUID = -972873964063134055L;
    
    private static RequirementsListModel instance = null;
    
    private final ArrayList<GameRequirementModel> requirements;
    private final ArrayList<SimpleListObserver> observers;
    
    private RequirementsListModel() {
        requirements = new ArrayList<GameRequirementModel>();
        observers = new ArrayList<SimpleListObserver>();
    }
    
    /**
     * Returns the instance of this RequirementsListModel or creates a new one
     * 
     * @return The instance of this RequirementsListModel
     */
    public static RequirementsListModel getInstance() {
        if (instance == null) {
            instance = new RequirementsListModel();
        }
        
        return instance;
    }
    
    /**
     * Add a SimpleListObserver that is notified when the list of games is
     * changed
     * 
     * @param slo
     *        The SimpleListObserver to add
     */
    public void addListListener(SimpleListObserver slo) {
        if (!observers.contains(slo)) {
            observers.add(slo);
        }
    }
    
    /**
     * Removes a SimpleListObserver from the list of SimpleListObservers
     * 
     * @param slo
     *            The SimpleListObserver to be removed
     */
    public void removeListListener(SimpleListObserver slo) {
        if (observers.contains(slo)) {
            observers.remove(slo);
        }
    }
    
    /**
     * Returns the list of SimpleListObservers
     * 
     * @return ArrayList of SimpleListObservers
     */
    public ArrayList<SimpleListObserver> getObservers() {
        return observers;
    }
    
    /**
     * Notifies all of the list observers that the list has changed.
     */
    private void updated() {
        for (SimpleListObserver observer : observers) {
            observer.listUpdated();
        }
    }
    
    /**
     * Sets the list of requirements to the given list. The purpose of this
     * method is to be able to clear the list and add a number of requirements
     * while only triggering one list updated event.
     * 
     * @param reqs
     *            The array of GameRequirementModels
     */
    public void setRequirements(GameRequirementModel[] reqs) {
        requirements.clear();
        for (GameRequirementModel req : reqs) {
            requirements.add(req);
        }
        updated();
    }
    
    /**
     * Adds a requirement to the list of requirements.
     * 
     * @param req
     *            The GameRequirementModel to add
     */
    public void addRequirement(GameRequirementModel req) {
        requirements.add(req);
        updated();
    }
    
    /**
     * Adds multiple requirements to the list of requirements. The purpose of
     * this method is to be able to add a number of requirements to the list
     * model and only trigger one list changed event.
     * 
     * @param reqs
     *            The array of GameRequirementModels to add
     */
    public void addMultipleRequirements(GameRequirementModel[] reqs) {
        for (GameRequirementModel req : reqs) {
            requirements.add(req);
        }
        updated();
    }
    
    /**
     * Removes a requirement from the list of requirements
     * 
     * @param req
     *            The GameRequirementModel to remove
     */
    public void removeRequirement(GameRequirementModel req) {
        requirements.remove(req);
        updated();
    }
    
    /**
     * Empties the list of requirements
     */
    public void emptyModel() {
        requirements.clear();
        updated();
    }
    
    /**
     * Gets the full list of current requirements.
     * @return The list of requirements
     */
    public ArrayList<GameRequirementModel> getAll() {
        return requirements;
    }
    
    @Override
    public int getSize() {
        return requirements.size();
    }
    
    @Override
    public GameRequirementModel getElementAt(int index) {
        return requirements.get(index);
    }
    
}
