/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.Component;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.OptionPane;

/**
 * OptionPane implementation that automatically returns no
 * 
 * @author Andrew
 *
 */
public class NoMockOptionPane implements OptionPane {
    
    @Override
    public int showConfirmDialog(Component parentComponent, Object message,
            String title, int optionType) {
        return JOptionPane.NO_OPTION;
    }
    
}
