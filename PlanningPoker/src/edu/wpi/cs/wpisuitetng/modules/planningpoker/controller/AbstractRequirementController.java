/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * nfbrown
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Abstract requirement controller to be extended by other classes that need to
 * receive requirements
 * 
 * @author Team 9
 * @version Apr 18, 2014
 */
public abstract class AbstractRequirementController {
    
    /**
     * For testing only.
     */
    private boolean timedOut = true;
    
    private long timeout = 1500;
    
    private static Requirement[] reqs = null;
    
    final GetRequirementsRequestObserver observer;
    
    protected AbstractRequirementController() {
        observer = new GetRequirementsRequestObserver(this);
        retrieveRequirements();
    }
    
    /**
     * This method is called by a RequirementRequestThread when it received a
     * response from the server
     * 
     * @param reqs
     *        The received requirements
     */
    public abstract void receivedRequirements(Requirement[] reqs);
    
    /**
     * Requests query of all requirements related to the project.
     * 
     */
    public void retrieveRequirements() {
        synchronized (this) {
            new RequirementRequestThread(this).start();
            try {
                wait(timeout);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Set the array of requirements in the current project.
     * 
     * @param reqs
     *        an array of requirements in the current project.
     * 
     */
    public static void setRequirements(Requirement[] reqs) {
        AbstractRequirementController.reqs = reqs;
    }
    
    /**
     * Gets the array of requirements in the current project.
     * 
     * @return the array of requirements in the current project.
     */
    public static Requirement[] getRequirements() {
        return AbstractRequirementController.reqs;
    }
    
    /**
     * For testing only.
     * 
     * @return timeOut value
     */
    protected boolean requestTimedOut() { // $codepro.audit.disable booleanMethodNamingConvention
        return timedOut;
    }
    
    /**
     * For testing only.
     */
    protected void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }
    
    
    /**
     * Set timeout value for user request
     * 
     * @param timeout
     *        a variable to hold timeout value
     */
    public void setTimeout(long timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("Timeout must be >= 0");
        }
        else {
            this.timeout = timeout;
        }
    }
    
    /**
     * @return timeout the request timeout
     */
    public long getTimeout() {
        return timeout;
    }
    
    
}
