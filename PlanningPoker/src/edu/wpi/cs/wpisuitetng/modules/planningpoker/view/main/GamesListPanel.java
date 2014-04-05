/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

/**
 * 
 * @author Sonaxaton
 */
public class GamesListPanel extends javax.swing.JPanel {
    
    // TODO: organize games into completed and uncompleted folders
    
    /**
     *
     */
    private static final long serialVersionUID = 4257983013648294131L;
    
    /**
     * Creates new form GamesListPanel
     */
    public GamesListPanel() {
        initComponents();
        gameTree.setCellRenderer(new GamesListTreeCellRenderer());
        
        GameListModel.getInstance().addListListener(new SimpleListObserver() {
            
            @Override
            public void listUpdated() {
                DefaultMutableTreeNode root_node = new DefaultMutableTreeNode() {
                    private static final long serialVersionUID = 8933074607488306596L;
                    
                    {
                        DefaultMutableTreeNode pending_folder = new DefaultMutableTreeNode(
                                "Pending Games");
                        DefaultMutableTreeNode complete_folder = new DefaultMutableTreeNode(
                                "Complete Games");
                        for (GameModel gm : GameListModel.getInstance()
                                .getGames()) {
                            DefaultMutableTreeNode game_node = new DefaultMutableTreeNode();
                            game_node.setUserObject(gm);
                            
                            if (gm.getRequirements() != null) {
                                for (GameRequirementModel r : gm
                                        .getRequirements()) {
                                    DefaultMutableTreeNode req_node = new DefaultMutableTreeNode();
                                    req_node.setUserObject(r);
                                    
                                    game_node.add(req_node);
                                }
                            }
                            if (gm.isEnded()) {
                                complete_folder.add(game_node);
                            } else {
                                pending_folder.add(game_node);
                            }
                        }
                        add(pending_folder);
                        add(complete_folder);
                    }
                };
                gameTree.setModel(new DefaultTreeModel(root_node));
                
                
                DefaultMutableTreeNode currentNode = root_node.getNextNode();
                do {
                    if (currentNode.getLevel() == 1) {
                        gameTree.expandPath(new TreePath(currentNode.getPath()));
                    }
                    currentNode = currentNode.getNextNode();
                } while (currentNode != null);
            }
        });
        
        gameTree.addTreeSelectionListener(new TreeSelectionListener() {
            
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                gameTree.expandPath(e.getPath());
            }
        });
        
        
        // TODO: estimates are not possible cards!!! change to deck values once
        // the deck is added
        
        
        ArrayList<GameRequirementModel> reqs = new ArrayList<>();
        reqs.add(new GameRequirementModel(367432, "Requirement 1",
                "THis is required!", "its type"));
        GameListModel.getInstance().addGame(
                new GameModel(23, "Test Game", "This game is a test", reqs,
                        new Date(), GameType.LIVE, GameStatus.PENDING));
        reqs = new ArrayList<>();
        reqs.add(new GameRequirementModel(15, "Requirement A",
                "THis is required!", "user story"));
        reqs.add(new GameRequirementModel(51, "Requirement B",
                "THis is definitely required!", "doofus story"));
        GameListModel.getInstance().addGame(
                new GameModel(25, "Test Game 2", "This game is also a test",
                        reqs, new Date(System.currentTimeMillis() + 24 * 60
                                * 60 * 1000), GameType.DISTRIBUTED,
                        GameStatus.PENDING));
    }
    
    public JTree getTree() {
        return gameTree;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        jScrollPane2 = new javax.swing.JScrollPane();
        gameTree = new javax.swing.JTree();
        gameTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
        gameTree.setRootVisible(false);
        
        jScrollPane2.setViewportView(gameTree);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 465,
                Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 320,
                Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree gameTree;
    // End of variables declaration//GEN-END:variables
}
