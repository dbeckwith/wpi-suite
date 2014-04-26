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

/**
 * OptionPane interface allows testing to simulate yes or no selections on
 * confirmation dialogs.
 * 
 * @author Team 9
 * @version 1.0
 */
public interface OptionPane {
	/**
	 * @see JOptionPane#showConfirmDialog(Component, Object, String, int);
	 */
	int showConfirmDialog(Component parentComponent, Object message,
			String title, int optionType);
}
