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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
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
    private JButton endGameButton;
    
    private final JPanel contentPanel = new JPanel();
    
    public AdminButtons() {
        super("");
        
        endGameButton = new JButton("<html>End<br/>Estimation</html>");
        endGameButton.setIcon(ImageLoader.getIcon("EndGame.png"));
        endGameButton.setEnabled(false);        //disables button because it hasn't been implemented
        
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        setPreferredWidth(175);
        
        endGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        contentPanel.add(endGameButton);
        contentPanel.setOpaque(false);
        
        this.add(contentPanel);
        
    }
}
