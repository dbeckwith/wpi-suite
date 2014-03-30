package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * 
 * @author dbeckwith
 */
public class AllGamesViewPanel extends javax.swing.JPanel {
    
    /**
     *
     */
    private static final long serialVersionUID = -6990619499118841478L;
    
    /**
     * Creates new form GameViewPanel
     */
    public AllGamesViewPanel() {
        initComponents();
        final JTree tree = gameTree.getTree();
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
                
                if (node == null) {
                    return;
                }
                
                Object nodeInfo = node.getUserObject();
                if (nodeInfo instanceof GameModel) {
                    setGameOrReqView(true);
                } else if (nodeInfo instanceof Requirement) {
                    setGameOrReqView(false);
                }
            }
        });
    }
    
    private void setGameOrReqView(boolean is_game) {
        if (is_game) {
            ((CardLayout) selectedItemPanel.getLayout()).show(selectedItemPanel, "game");
        } else {
            ((CardLayout) selectedItemPanel.getLayout()).show(selectedItemPanel, "req");
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        jSplitPane3 = new javax.swing.JSplitPane();
        
        jSplitPane3.setDividerLocation(190);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSplitPane3,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 658,
                                Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSplitPane3,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 394,
                                Short.MAX_VALUE).addContainerGap()));
        
        gameTree = new GamesListPanel();
        jSplitPane3.setLeftComponent(gameTree);
        
        selectedItemPanel = new JPanel();
        jSplitPane3.setRightComponent(selectedItemPanel);
        selectedItemPanel.setLayout(new CardLayout(0, 0));
        
        gamePanel = new GameDescriptionPanel();
        selectedItemPanel.add(gamePanel, "game");
        
        requirementPanel = new RequirementDescriptionPanel();
        selectedItemPanel.add(requirementPanel, "req");
    }// </editor-fold>//GEN-END:initComponents
    
    private javax.swing.JSplitPane jSplitPane3;
    private GamesListPanel gameTree;
    private JPanel selectedItemPanel;
    private GameDescriptionPanel gamePanel;
    private RequirementDescriptionPanel requirementPanel;
}
