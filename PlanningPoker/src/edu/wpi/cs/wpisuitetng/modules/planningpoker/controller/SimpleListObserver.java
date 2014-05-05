/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

/**
 * Notified when a list changes
 * 
 * @author Team 9
 * @version 1.0
 */
public interface SimpleListObserver {
    
    /**
     * Gets notified when a list is updated
     */
    void listUpdated();
    
}
