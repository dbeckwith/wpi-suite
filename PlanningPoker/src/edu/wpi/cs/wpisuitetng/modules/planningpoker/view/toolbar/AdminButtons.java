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
 * @author Dan
 * 
 */
public class AdminButtons extends ToolbarGroupView {
    /**
     * 
     */
    private static final long serialVersionUID = 312905811728893535L;
    private final JButton endGameButton;
    private final JButton closeGameButton;
    
    private final JPanel contentPanel = new JPanel();
    
    public AdminButtons() {
        super("");
        
        endGameButton = new JButton("<html>End<br/>Estimation</html>");
        endGameButton.setIcon(ImageLoader.getIcon("EndGame.png"));
        
        closeGameButton = new JButton("<html>Close<br/>Game</html>");
        closeGameButton.setIcon(ImageLoader.getIcon("CloseGame.png"));
        
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
        
        endGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        contentPanel.add(endGameButton);
        contentPanel.add(closeGameButton);
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
}
