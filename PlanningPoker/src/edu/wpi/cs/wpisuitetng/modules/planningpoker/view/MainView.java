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

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.AllGamesViewPanel;

/**
 * This is the main panel of the planning poker GUI
 * 
 * @author llhunker, blammeson, nfbrown, dbeckwith
 * @version 1.0
 * 
 */
public class MainView extends JTabbedPane {
    /**
     * 
     */
    private static final long serialVersionUID = 7802378837976895569L;
    private final AllGamesViewPanel mainPanel;
    private boolean setExitOnClose = false;
    
    public MainView() {
        mainPanel = new AllGamesViewPanel();
        addTab("Games", null, mainPanel, null);
        addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent arg0) {
                PlanningPoker.getViewController()
                        .tabChanged(getSelectedIndex());
                
            }
            
        });
        
        addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
			    // called when this tab is switched to

                CurrentUserController.getInstance(); // initialize CurrentUserController early so it gets the current user
                GetDecksController.getInstance().retrieveDecks();
                GetRequirementsController.getInstance().retrieveRequirements();
                GetGamesController.getInstance().retrieveGames();
                
				if (!setExitOnClose){
				    JFrame window = null;
				    
			        Component parent = MainView.this;
			        while(!((parent = parent.getParent()) instanceof JFrame)){
			        	System.out.println(parent);
			        }
			        window = (JFrame)parent;
			        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			        setExitOnClose = true;
				}
			}
			
			@Override
			public void ancestorRemoved(AncestorEvent event) {}
			
			
			@Override
			public void ancestorMoved(AncestorEvent event) {}
		
        });
  
    }
    
    /**
     * @return the mainPanel
     */
    public AllGamesViewPanel getMainPanel() {
        return mainPanel;
    }
}
