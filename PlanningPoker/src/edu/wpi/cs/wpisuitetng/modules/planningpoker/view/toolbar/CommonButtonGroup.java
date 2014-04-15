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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * this is an example of buttons
 * 
 * @author llhunker, blammeson, nfbrown
 * 
 */
public class CommonButtonGroup extends ToolbarGroupView {
	/**
     * 
     */
	private static final long serialVersionUID = -2589339467561118867L;

	private final JButton newGameButton;
	private final JButton userPrefsButton;

	private final JPanel contentPanel = new JPanel();

	public CommonButtonGroup() {
		super(""); // not sure if this is needed

		newGameButton = new JButton("<html>Create<br/>Game</html>");
		newGameButton.setIcon(ImageLoader.getIcon("NewGame.png"));

		userPrefsButton = new JButton("<html>Preferences</html>");

		userPrefsButton.setIcon(ImageLoader.getIcon("prefs.png"));

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		setPreferredWidth(350);
		// Adding functionality to new game button, not sure if this is where
		// this should go --nfbrown
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlanningPoker.getViewController().addNewGameTab();
			}
		});

		userPrefsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PlanningPoker.getViewController().addUserPrefsTab();
			}
		});

		newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		newGameButton.setPreferredSize(new Dimension(200, 200));
		userPrefsButton.setHorizontalAlignment(SwingConstants.CENTER);
		userPrefsButton.setPreferredSize(new Dimension(150, 200));
		contentPanel.add(newGameButton);
		contentPanel.add(userPrefsButton);
		contentPanel.setOpaque(false);

		this.add(contentPanel);
	}

}
