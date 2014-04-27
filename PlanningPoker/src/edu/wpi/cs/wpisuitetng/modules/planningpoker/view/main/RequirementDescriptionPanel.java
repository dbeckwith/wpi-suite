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

import javax.swing.JPanel;
import javax.swing.JTree;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

/**
 * Depending on the requirement and game status, this is the panel to show
 * requirement description of the requirement or vote GUI or result of estimates
 * 
 * @author Team 9
 * @version 1.0
 * 
 */
public class RequirementDescriptionPanel extends JPanel {
    
    private static final long serialVersionUID = 6869910614623975734L;
    private final VotePanel votePanel;
    private final CompletedRequirementPanel completedPanel;
    private final RequirementDescriptionOnlyPanel newReq;
    
    /**
     * Create the panel.
     * 
     * @param tree
     *        the tree of games
     */
    public RequirementDescriptionPanel(JTree tree) {
        setLayout(new CardLayout(0, 0));
        
        votePanel = new VotePanel();
        add(votePanel, "vote");
        
        completedPanel = new CompletedRequirementPanel(tree);
        add(completedPanel, "complete");
        
        newReq = new RequirementDescriptionOnlyPanel();
        add(newReq, "new");
        
    }
    
    /**
     * Sets the data for the RequirementDescriptionPanel
     * 
     * @param current_user
     *        the current user
     * @param parent_game
     *        the parent game
     * @param req
     *        the requirement
     */
    public void setData(User current_user, GameModel parent_game,
            GameRequirementModel req) {
        if (parent_game.isEnded()) {
            completedPanel.setRequirement(parent_game, req);
            ((CardLayout) getLayout()).show(this, "complete");
        }
        else if (parent_game.isStarted()) {
            votePanel.setRequirement(current_user, parent_game, req);
            ((CardLayout) getLayout()).show(this, "vote");
        }
        else {
            newReq.setRequirement(parent_game, req);
            ((CardLayout) getLayout()).show(this, "new");
        }
    }
    
}
