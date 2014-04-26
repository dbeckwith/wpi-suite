/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.CardLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;

/**
 * 
 * This panel shows information about a single game, showing its requirements
 * and other information about the game.
 * 
 * @author Team 9
 * @version 1.0
 */
public class GameDescriptionPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 7579915917240962935L;
	private final CompletedGamePanel completed;
	private final UncompletedGameDescriptionPanel uncompleted;

	/**
	 * 
	 * Creates a new GameDescriptionPanel
	 * 
	 */
	public GameDescriptionPanel() {
		setLayout(new CardLayout(0, 0));

		uncompleted = new UncompletedGameDescriptionPanel();
		add(uncompleted, "uncompleted");

		completed = new CompletedGamePanel();
		add(completed, "completed");
	}

	/**
	 * Sets the game that this panel will show information about
	 * 
	 * @param game
	 *            the game to show
	 */
	public void setGame(GameModel game) {
		if (game.isEnded()) {
			((CardLayout) getLayout()).show(this, "completed");
			completed.setGame(game);
		} else {
			((CardLayout) getLayout()).show(this, "uncompleted");
			uncompleted.setGame(game);
		}
	}
}