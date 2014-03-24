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

import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.DiscussionPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.DistributedGame;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.ListOfCompleteGames;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.ListOfOngoingGames;

/**
 * This is the main panel of the planning poker GUI
 * @author llhunker, blammeson, nfbrown
 *
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {
	
	ListOfOngoingGames userQueue = new ListOfOngoingGames();
	ListOfOngoingGames listOfOngoingGames = new ListOfOngoingGames();
	ListOfCompleteGames listOfCompleteGames = new ListOfCompleteGames();
	DiscussionPanel discussion = new DiscussionPanel();
	DistributedGame dGame1 = new DistributedGame();	//only for testing purposes
	
	
	public MainView(){
		
		addTab("Queue", null, userQueue, null);
		addTab("Current", null, listOfOngoingGames, null);
		listOfOngoingGames.flipPanel();		//for demonstration purposes
		addTab("Complete", null, listOfCompleteGames, null);
		listOfCompleteGames.flipPanel();	//for demonstration purposes
		addTab("Requirement x", null, dGame1, null);
		
	
		
	}
}
