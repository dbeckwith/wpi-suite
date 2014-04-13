/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;


import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

/**
 * 
 * @author Sonaxaton
 */
public class GamesListPanel extends javax.swing.JPanel {
    
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
                updateTree();
            }
        });
        
        GameListModel.getInstance().addStatusListener(new GameStatusObserver() {
            
            @Override
            public void statusChanged(GameModel game) {
                updateTree();
            }
        });
        
        gameTree.addTreeSelectionListener(new TreeSelectionListener() {
            
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                gameTree.expandPath(e.getPath());
            }
        });
    }
    
    private void updateTree() {
        
        // save an array of games whose nodes were open before the update
        final ArrayList<GameModel> expandedGames = new ArrayList<>();
        
        for (int i = 0; i < gameTree.getRowCount(); i++) {
            // loop through all the visible nodes
            TreePath path = gameTree.getPathForRow(i);
            Object userObject = ((DefaultMutableTreeNode) path
                    .getLastPathComponent()).getUserObject();
            if (userObject instanceof GameModel && gameTree.isExpanded(path)) {
                // if the user object is a GameModel and the node is expanded,
                // add it to the list
                expandedGames.add((GameModel) userObject);
            }
        }
        
        // save the selected node
        Object selectedNodeUserObject = null;
        try {
            selectedNodeUserObject = ((DefaultMutableTreeNode) gameTree
                    .getSelectionPath().getLastPathComponent()).getUserObject();
        } catch (NullPointerException e) {
        }
        
        
        // rebuild the tree
        final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode() {
            private static final long serialVersionUID = 8933074607488306596L;
            
            {
                final DefaultMutableTreeNode pendingFolder = new DefaultMutableTreeNode(
                        "Pending Games");
                final DefaultMutableTreeNode completeFolder = new DefaultMutableTreeNode(
                        "Complete Games");
                add(pendingFolder);
                add(completeFolder);
                for (GameModel gm : GameListModel.getInstance().getGames()) {
                    DefaultMutableTreeNode gameNode = new DefaultMutableTreeNode();
                    gameNode.setUserObject(gm);
                    
                    if (gm.isEnded()) {
                        completeFolder.add(gameNode);
                    } else {
                        pendingFolder.add(gameNode);
                    }
                    if (gm.getRequirements() != null) {
                        for (GameRequirementModel r : gm.getRequirements()) {
                            DefaultMutableTreeNode reqNode = new DefaultMutableTreeNode();
                            reqNode.setUserObject(r);
                            gameNode.add(reqNode);
                        }
                    }
                }
            }
        };
        gameTree.setModel(new DefaultTreeModel(rootNode));
        
        
        // go through all the new nodes and find ones with a game in the
        // expandedGames list
        Enumeration treeEnum = rootNode.depthFirstEnumeration();
        DefaultMutableTreeNode node;
        while (treeEnum.hasMoreElements()) {
            node = (DefaultMutableTreeNode) treeEnum.nextElement();
            if (node.getUserObject() != null
                    && ((node.getUserObject() instanceof GameModel && expandedGames
                            .contains(node.getUserObject()))
                            || node.getUserObject().equals("Pending Games") || node
                            .getUserObject().equals("Complete Games"))) {
                // if the node's game was in the list,
                // or the node is a folder of games, expand it
                gameTree.expandPath(new TreePath(node.getPath()));
            }
        }
        
        
        // go through all the new node and find the one with user object
        // equal to the one that was selected
        treeEnum = rootNode.depthFirstEnumeration();
        while (treeEnum.hasMoreElements()) {
            node = (DefaultMutableTreeNode) treeEnum.nextElement();
            if (node.getUserObject() != null
                    && node.getUserObject().equals(selectedNodeUserObject)) {
                gameTree.setSelectionPath(new TreePath(node.getPath()));
            }
        }
        
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
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
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
