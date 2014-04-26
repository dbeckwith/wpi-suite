/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

/**
 * 
 * This class is a custom renderer for the GamesListPanel's tree. It adds the
 * ability to show icons next to games and requirements based on their statuses.
 * 
 * @author Team 9
 * @version 1.0
 */
public class GamesListTreeCellRenderer extends DefaultTreeCellRenderer {
    
    private static final long serialVersionUID = -2728918517590604079L;
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);
        
        final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        ImageIcon icon = null;
        
        setFont(getFont().deriveFont(Font.PLAIN));
        
        if (node.getUserObject() instanceof GameModel) {
            final GameModel game = (GameModel) node.getUserObject();
            
            if (game.isClosed()) {
                icon = ImageLoader.getIcon("archiveTree.png");
            }
            else if (game.isEnded()) {
                icon = ImageLoader.getIcon("GameCompleted.png");
            }
            else if (!game.isStarted()) {
                icon = ImageLoader.getIcon("new_req_small.png");
            }
            else {
                icon = ImageLoader.getIcon("GameInProgress.png");
                if (game.getRequirements() != null) {
                    boolean hasUnvotedReqs = false;
                    req_loop:
                    for (GameRequirementModel req : game
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
                            if (!voted){
                                hasUnvotedReqs = true;
                            }
                        }
                    }
                    if (hasUnvotedReqs) {
                        setFont(getFont().deriveFont(Font.BOLD));
                    }
                }
            }
        } else if (node.getUserObject() instanceof GameRequirementModel) {
            final GameRequirementModel req = (GameRequirementModel) node
                    .getUserObject();

            icon = ImageLoader.getIcon("GameInProgress.png");
            
            for (Estimate e : req.getEstimates()) {
                if (e.getUsername() != null
                        && e.getUsername().equals(
                                CurrentUserController.getInstance().getUser()
                                        .getUsername())) {
                    icon = ImageLoader.getIcon("GameCompleted.png");
                    break;
                }
            }
        }
        
        if (node.getUserObject() != null
                && node.getUserObject() instanceof String) {
            if (((String) node.getUserObject())
                    .matches("Games in Progress \\(\\d+\\)")) {
                icon = ImageLoader.getIcon("GameInProgressFolder.png");
            } else if (((String) node.getUserObject())
                    .matches("Complete Games \\(\\d+\\)")) {
                icon = ImageLoader.getIcon("GameCompletedFolder.png");
            } else if (node.getUserObject().equals("<No complete games>")
                    || node.getUserObject().equals("<No games in progress>")) {
                icon = ImageLoader.getIcon("noGames.png");
            }
        }
        
        if (icon != null) {
            setIcon(icon);
        }
        
        return this;
    }
}
