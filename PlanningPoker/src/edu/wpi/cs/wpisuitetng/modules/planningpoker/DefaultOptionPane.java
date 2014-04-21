/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * Default implementation of OptionPane that just refers to JOptionPane
 * 
 * @author Team 9
 * @version 1.0
 */
public class DefaultOptionPane implements OptionPane {
    
    @Override
    public int showConfirmDialog(Component parentComponent, Object message,
            String title, int optionType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title,
                optionType);
    }
    
}
