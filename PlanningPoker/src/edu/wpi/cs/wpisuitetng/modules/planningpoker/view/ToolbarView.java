/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    TODO: Contributors' names
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar.AdminButtons;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar.CommonButtons;

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
    private CommonButtons commonButtons = new CommonButtons();
    private AdminButtons adminButtons = new AdminButtons();
    
    public ToolbarView() {
        addGroup(commonButtons);
    }
    
    /**
     * shows the admin buttons in the toolbar
     */
    public void showAdmin(){
        remove(adminButtons);    //ensures admin buttons are not already in the toolbar
        addGroup(adminButtons);
    }
    
    /**
     * hides the admin buttons
     */
    public void hideAdmin(){
        remove(adminButtons);
    }
}
