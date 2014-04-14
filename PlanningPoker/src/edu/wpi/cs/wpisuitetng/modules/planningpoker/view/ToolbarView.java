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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar.AdminButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar.CommonButtonGroup;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar.HelpButtons;

/**
 * This is the main class for the planning poker toolbar
 * 
 * @author llhunker, blammeson
 * 
 */
public class ToolbarView extends DefaultToolbarView {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1142258027071142978L;
    private final CommonButtonGroup commonButtonGroup = new CommonButtonGroup();
    private final AdminButtonGroup adminButtonGroup = new AdminButtonGroup();
    private HelpButtons helpButtons = new HelpButtons();
    
    public ToolbarView() {
        addGroup(commonButtonGroup);
        addGroup(adminButtonGroup);
        adminButtonGroup.setVisible(false);
        addGroup(helpButtons);
    }
    
    /**
     * sets visibility of admin buttons
     * 
     * @param b
     *        whether admin buttons should be visible
     */
    public void setAdminVisibility(boolean b) {
        adminButtonGroup.setVisible(b);
    }
    
    /**
     * sets whether the endGame button is shown
     * 
     * @param b
     *        whether to enable the end estimation button
     */
    public void setEndGame(boolean b) {
        adminButtonGroup.setEndGameEnabled(b);
    }
}
