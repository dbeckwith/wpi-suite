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

/**
 * 
 * @author Team 9
 * @version 1.0
 */
public final class TutorialComponents {
    
    private final ArrayList<TutorialPath> TUTORIALS = new ArrayList<>();
    
    private TutorialComponents() {
    }
    
    private static TutorialComponents instance = null;
    
    /**
     * Gets the singleton instance.
     */
    public static TutorialComponents getInstance() {
        if (instance == null) {
            instance = new TutorialComponents();
            instance.initTutorialPaths();
        }
        return instance;
    }
    
    /**
     * Gets the tutorial path with the given name, or null if no tutorial with
     * that name exists.
     * 
     * @param tutorialName
     *        the name of the tutorial to retrieve
     */
    public TutorialPath getTutorial(String tutorialName) {
        for (TutorialPath t : TUTORIALS) {
            if (t.getName().equals(tutorialName)) {
                return t;
            }
        }
        return null;
    }
    
    /**
     * Adds the given tutorial to the list of tutorials.
     * 
     * @param tutorial
     *        the tutorial to add
     * @return true if the tutorial was added to the list, otherwise returns
     *         false
     */
    public boolean addTutorial(TutorialPath tutorial) {
        if (!TUTORIALS.contains(tutorial)) {
            System.out.println("Added tutorial " + tutorial.getName());
            return TUTORIALS.add(tutorial);
        }
        System.out.println("Failed to add tutorial " + tutorial.getName()
                + " (already stored)");
        return false;
    }
    
    public ArrayList<TutorialPath> getTutorials() {
        return TUTORIALS;
    }
    
    /**
     * Create all tutorial paths here.
     */
    private void initTutorialPaths() {
        
    }
    
}
