package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Component;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;

public class GamesListTreeCellRenderer extends DefaultTreeCellRenderer {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2728918517590604079L;
    
    public GamesListTreeCellRenderer() {
    }
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        
        if (node.getUserObject() instanceof GameModel) {
            ImageIcon icon = null;
            
            // TODO: set game status icon
            
            GameModel game = (GameModel)node.getUserObject();
            
            switch (game.getStatus()) {
                case COMPLETE:
                    //icon = new ImageIcon(ImageLoader.load("tick_octagon.png"));
                    break;
                case PENDING:
                    //icon = new ImageIcon(ImageLoader.load("in-progress.png"));
                    break;
            }
            
            if (icon != null) {
                setIcon(icon);
            }
        }
        
        return this;
    }
    
}
