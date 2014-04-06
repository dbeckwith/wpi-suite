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

import javax.swing.JTabbedPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.AllGamesViewPanel;

/**
 * This is the main panel of the planning poker GUI
 * 
 * @author llhunker, blammeson, nfbrown, dbeckwith
 * 
 */
public class MainView extends JTabbedPane {
    /**
     * 
     */
    private static final long serialVersionUID = 7802378837976895569L;
    
    
    public MainView() {
        AllGamesViewPanel mainPanel = new AllGamesViewPanel();
        
        addTab("Games", null, mainPanel, null);
        
        addAncestorListener(new AncestorListener() {
            
            @Override
            public void ancestorAdded(AncestorEvent event) {
                GetGamesController.getInstance().retrieveGames();
                
                CurrentUserController.getInstance(); // initialize CurrentUserController early so it gets the current user
            }
            
            @Override
            public void ancestorRemoved(AncestorEvent event) {
                // Do Nothing
                
            }
            
            @Override
            public void ancestorMoved(AncestorEvent event) {
                // Do Nothing
                
            }
            
        });
        
    }
}
