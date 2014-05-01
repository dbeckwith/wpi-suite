/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar;

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
 * Buttons for new game and delete game
 * 
 * @author Team 9
 * @version 1.0
 * 
 */
public class AdminButtonGroup extends ToolbarGroupView {

    private static final long serialVersionUID = 312905811728893535L;
    private final JButton endGameButton;
    private final JButton closeGameButton;
    private final JButton editGameButton;
    private final JButton startGameButton;
    
    private ActionListener startGameCallback;
    
    private final JPanel contentPanel = new JPanel();
    
    /**
     * 
     * Creates a new AdminButtonGroup
     *
     */
    public AdminButtonGroup() {
        super("");
        
        endGameButton = new JButton("<html>End<br/>Estimation</html>");
        endGameButton.setToolTipText("End the currently selected game so that users can no longer vote on it. A final estimation can still be made after estimation is ended.");
        endGameButton.setIcon(ImageLoader.getIcon("EndEstimation.png"));
        
        closeGameButton = new JButton("<html>Close<br/>Game</html>");
        closeGameButton.setToolTipText("Completely close the currently selected game, archiving it.");
        closeGameButton.setIcon(ImageLoader.getIcon("CloseGame.png"));
        
        editGameButton = new JButton("<html>Edit<br/>Game</html>");
        editGameButton.setToolTipText("Edit the details of the currently selected games.");
        editGameButton.setIcon(ImageLoader.getIcon("edit.png"));
        
        startGameButton = new JButton("<html>Start<br/>Game</html>");
        startGameButton.setToolTipText("Start estimation on the currently selected game, enabling users to vote on its requirements.");
        startGameButton.setIcon(ImageLoader.getIcon("StartEstimation.png"));
        
        startGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(startGameCallback != null){
					ActionListener call = startGameCallback;
					startGameCallback = null;
					call.actionPerformed(e);
				}
			}
		});
        
        
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        setPreferredWidth(350);
        
        endGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlanningPoker.getViewController().endEstimation();
            }
        });
        
        closeGameButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                PlanningPoker.getViewController().closeGame();
                
            }
            
        });
        
        editGameButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                PlanningPoker.getViewController().editGame();
                
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                PlanningPoker.getViewController().startGame();
            }
        });
        
        endGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        contentPanel.add(endGameButton);
        contentPanel.add(closeGameButton);
        contentPanel.add(startGameButton);
        contentPanel.add(editGameButton);
        contentPanel.setOpaque(false);
        
        this.add(contentPanel);
        
    }
    
    /**
     * sets whether the endGame button is Enabled
     * 
     * @param b
     *        whether the button should be enabled
     */
    public void setEndGameEnabled(boolean b) {
        endGameButton.setEnabled(b);
    }
    
    /**
     * Shows the Start Game and Edit Game buttons when passed true
     *  else shows the End Game and Close Game buttons when passed false
     * @param b
     *      The boolean to flip the buttons
     */
    public void showNewGameButtons(boolean b) {
        endGameButton.setVisible(!b);
        closeGameButton.setVisible(!b);
        startGameButton.setVisible(b);
        editGameButton.setVisible(b);
    }
    public JButton getCloseGameButton() {
        return closeGameButton;
    }
    public JButton getEditGameButton() {
        return editGameButton;
    }
    public JButton getEndGameButton() {
        return endGameButton;
    }
    public JButton getStartGameButton() {
        return startGameButton;
    }
    
    public void setStartGameCallback(ActionListener a){
    	startGameCallback = a;
    }
}
