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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.toolbar;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

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
		try {
			ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getResource("EndGame.png")));
			endGameButton.setIcon(icon);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        setPreferredWidth(350);
        
        endGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        contentPanel.add(endGameButton);
        contentPanel.setOpaque(false);
        
        this.add(contentPanel);
        
    }
}
