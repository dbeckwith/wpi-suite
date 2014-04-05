package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.CardLayout;

import javax.swing.JPanel;

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
    
    public void setData(GameModel parent_game, GameRequirementModel req) {
        
        if (parent_game.isEnded()) {
            completedPanel.setRequirement(parent_game, req);
            ((CardLayout) getLayout()).show(this, "complete");
        } else {
            votePanel.setRequirement(parent_game, req);
            ((CardLayout) getLayout()).show(this, "vote");
        }
    }
    
}
