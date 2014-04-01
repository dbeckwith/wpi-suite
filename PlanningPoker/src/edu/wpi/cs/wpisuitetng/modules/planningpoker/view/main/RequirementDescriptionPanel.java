package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.CardLayout;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

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
    
    public void showPanel(Requirement req) {
        switch (req.getStatus()) {
            case COMPLETE:
                ((CardLayout) getLayout()).show(this, "complete");
                break;
            case INPROGRESS:
                ((CardLayout) getLayout()).show(this, "vote");
                break;
            default:
                break;
        }
    }
    
    public VotePanel getVotePanel() {
        return votePanel;
    }
    public CompletedRequirementPanel getCompletedPanel() {
        return completedPanel;
    }
}
