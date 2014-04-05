package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

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
        ImageIcon icon = null;
        
        if (node.getUserObject() instanceof GameModel) {
            GameModel game = (GameModel) node.getUserObject();
            
            if (game.isEnded()) {
                icon = new ImageIcon(ImageLoader.getImage("GameCompleted.png"));
            } else {
                icon = new ImageIcon(ImageLoader.getImage("GameInProgress.png"));
            }
        } else if (node.getUserObject() != null) {
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
