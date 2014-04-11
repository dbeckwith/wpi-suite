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

import java.awt.CardLayout;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

public class RequirementDescriptionPanel extends JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6869910614623975734L;
    private VotePanel votePanel;
    private CompletedRequirementPanel completedPanel;
    
    /**
     * Create the panel.
     */
    public RequirementDescriptionPanel() {
        setLayout(new CardLayout(0, 0));
        
        votePanel = new VotePanel();
        add(votePanel, "vote");
        
        completedPanel = new CompletedRequirementPanel();
        add(completedPanel, "complete");
        
    }
    
    public void setData(User current_user, GameModel parent_game, GameRequirementModel req) {
        if (parent_game.isEnded()) {
            completedPanel.setRequirement(parent_game, req);
            ((CardLayout) getLayout()).show(this, "complete");
        } else {
            votePanel.setRequirement(current_user, parent_game, req);
            ((CardLayout) getLayout()).show(this, "vote");
        }
    }
    
}
