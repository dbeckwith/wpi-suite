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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

public class GamesListTreeCellRenderer extends DefaultTreeCellRenderer {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2728918517590604079L;
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        ImageIcon icon = null;
        
        setFont(getFont().deriveFont(Font.PLAIN));
        
        if (node.getUserObject() instanceof GameModel) {
            GameModel game = (GameModel) node.getUserObject();
            
            if (game.isEnded()) {
                icon = new ImageIcon(ImageLoader.getImage("GameCompleted.png"));
            } else {
                icon = new ImageIcon(ImageLoader.getImage("GameInProgress.png"));
                
                if (game.getRequirements() != null) {
                    boolean hasUnvotedReqs = false;
                    req_loop: for (GameRequirementModel req : game
                            .getRequirements()) {
                        if (req.getEstimates() != null) {
                            boolean voted = false;
                            for (Estimate e : req.getEstimates()) {
                                if (e != null
                                        && e.getUsername() != null
                                        && e.getUsername().equals(
                                                CurrentUserController
                                                        .getInstance()
                                                        .getUser()
                                                        .getUsername())) {
                                    voted = true;
                                    break req_loop;
                                }
                            }
                            if (!voted)
                                hasUnvotedReqs = true;
                        }
                    }
                    if (hasUnvotedReqs) {
                        setFont(getFont().deriveFont(Font.BOLD));
                    }
                }
            }
        } else if (node.getUserObject() instanceof GameRequirementModel) {
            GameRequirementModel req = (GameRequirementModel) node
                    .getUserObject();
            
            for (Estimate e : req.getEstimates()) {
                if (e.getUsername() != null
                        && e.getUsername().equals(
                                CurrentUserController.getInstance().getUser()
                                        .getUsername())) {
                    icon = new ImageIcon(
                            ImageLoader.getImage("GameCompleted.png"));
                    break;
                }
            }
        }
        
        if (node.getUserObject() != null) {
            if (node.getUserObject().equals("Pending Games")) {
                icon = new ImageIcon(ImageLoader.getImage("GameInProgress.png"));
            } else if (node.getUserObject().equals("Complete Games")) {
                icon = new ImageIcon(ImageLoader.getImage("GameCompleted.png"));
            }
        }
        
        if (icon != null) {
            setIcon(icon);
        }
        
        return this;
    }
    
}
