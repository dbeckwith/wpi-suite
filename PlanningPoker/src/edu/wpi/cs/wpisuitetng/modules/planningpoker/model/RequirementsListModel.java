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

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

public class RequirementsListModel extends
        AbstractListModel<GameRequirementModel> {
    
    /**
     * 
     */
    private static final long serialVersionUID = -972873964063134055L;

    private static RequirementsListModel instance = null;
    
    private final ArrayList<GameRequirementModel> requirements;
    private final ArrayList<SimpleListObserver> observers;
    
    private RequirementsListModel() {
        requirements = new ArrayList<GameRequirementModel>();
        observers = new ArrayList<SimpleListObserver>();
    }
    
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
     *            The SimpleListObserver to add
     */
    public void addListListener(SimpleListObserver slo) {
        if (!observers.contains(slo)) {
            observers.add(slo);
        }
    }
    
    public void removeListListener(SimpleListObserver slo) {
        if (observers.contains(slo)) {
            observers.remove(slo);
        }
    }
    
    public ArrayList<SimpleListObserver> getObservers() {
        return observers;
    }
    
    private void updated() {
        for (SimpleListObserver observer : observers) {
            observer.listUpdated();
        }
    }
    
    public void setRequirements(GameRequirementModel[] reqs) {
        requirements.clear();
        for (GameRequirementModel req : reqs) {
            requirements.add(req);
        }
        updated();
    }
    
    public void addRequirement(GameRequirementModel req) {
        requirements.add(req);
        updated();
    }
    
    public void addMultipleRequirements(GameRequirementModel[] reqs) {
        for (GameRequirementModel req : reqs) {
            requirements.add(req);
        }
        updated();
    }
    
    public void removeRequirement(GameRequirementModel req) {
        requirements.remove(req);
        updated();
    }
    
    public void emptyModel() {
        requirements.clear();
        updated();
    }
    
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
