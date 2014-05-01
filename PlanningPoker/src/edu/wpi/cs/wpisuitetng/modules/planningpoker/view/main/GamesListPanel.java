/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

/**
 * 
 * This panel shows a list of the currently available games, allowing access to
 * them and showing simple status information about them.
 * 
 * @author Team 9
 * @version 1.0
 */
public class GamesListPanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = 4257983013648294131L;
    
    /**
     * Creates a new GamesListPanel
     */
    public GamesListPanel() {
        initComponents();
        gameTree.setCellRenderer(new GamesListTreeCellRenderer());
        gameTree.setLargeModel(true);
        
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
        Object selectedNodeParent = null;
        Object selectedNodeUserObject = null;
        boolean requirement = false;
        boolean game = false;
        if (gameTree.getSelectionCount() != 0) {
            final DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) gameTree.getSelectionPath()
                    .getLastPathComponent();
            selectedNodeUserObject = treeNode.getUserObject();
            if (selectedNodeUserObject instanceof GameRequirementModel) {
                requirement = true;
                selectedNodeParent = ((DefaultMutableTreeNode) treeNode.getParent())
                		.getUserObject();
            }
            else if (selectedNodeUserObject instanceof GameModel) {
            	System.out.println("saved selected game");
                game = true;
            }
        }
        
        
        final DefaultMutableTreeNode pendingFolder = new DefaultMutableTreeNode(
                "Games in Progress (0)");
        final DefaultMutableTreeNode completeFolder = new DefaultMutableTreeNode(
                "Completed Games (0)");
        final DefaultMutableTreeNode closedFolder = new DefaultMutableTreeNode(
                "Closed Games (0)");
        // rebuild the tree
        final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        
        List<GameModel> games = GameListModel.getInstance().getGames();
        Collections.sort(games, new Comparator<GameModel>() {
            @Override
            public int compare(GameModel gm1, GameModel gm2) {
                GameStatus gs1 = gm1.getStatus();
                GameStatus gs2 = gm2.getStatus();
                int compareGameStatus = gs1.compareTo(gs2);
                if (compareGameStatus != 0) {
                    return compareGameStatus;
                }
                else if (gs1 == GameStatus.COMPLETE || gs1 == GameStatus.CLOSED) {
                    return -(gm1.getEndTime().compareTo(gm2.getEndTime()));
                }
                else if (gm1.getID() > gm2.getID()) {
                    return -1;
                }
                else if (gm1.getID() < gm2.getID()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });
        
        rootNode.add(pendingFolder);
        rootNode.add(completeFolder);
        rootNode.add(closedFolder);
        for (int i = 0; i < GameListModel.getInstance().getGames().size(); i++) {
            DefaultMutableTreeNode gameNode = new DefaultMutableTreeNode();
            gameNode.setUserObject(GameListModel.getInstance().getGames()
                    .get(i));
            
            if (GameListModel.getInstance().getGames().get(i).isClosed()) {
                closedFolder.add(gameNode);
            }
            else if (GameListModel.getInstance().getGames().get(i).isEnded()) {
                completeFolder.add(gameNode);
            }
            else {
                if (GameListModel.getInstance().getGames().get(i).isStarted()
                        || GameListModel.getInstance().getGames().get(i)
                                .getOwner()
                                .equals(CurrentUserController.USER_NAME)) {
                    pendingFolder.add(gameNode);
                }
            }
            if (GameListModel.getInstance().getGames().get(i).getRequirements() != null) {
                for (GameRequirementModel r : GameListModel.getInstance()
                        .getGames().get(i).getRequirements()) {
                    DefaultMutableTreeNode reqNode = new DefaultMutableTreeNode();
                    reqNode.setUserObject(r);
                    gameNode.add(reqNode);
                }
            }
            pendingFolder
                    .setUserObject("Games in Progress (" + pendingFolder.getChildCount() + ")");
            completeFolder.setUserObject("Completed Games (" + completeFolder.getChildCount() 
            		+ ")");
            closedFolder.setUserObject("Closed Games (" + closedFolder.getChildCount() + ")");
        }
        if (completeFolder.getChildCount() == 0) {
            completeFolder.add(new DefaultMutableTreeNode(
                    "<No completed games>"));
        }
        if (pendingFolder.getChildCount() == 0) {
            pendingFolder.add(new DefaultMutableTreeNode(
                    "<No games in progress>"));
        }
        if (closedFolder.getChildCount() == 0) {
            closedFolder.add(new DefaultMutableTreeNode("<No closed games>"));
        }
        
        gameTree.setModel(new DefaultTreeModel(rootNode));
        
        
        // go through all the new nodes and find ones with a game in the
        // expandedGames list
        @SuppressWarnings("rawtypes")
        Enumeration treeEnum = rootNode.depthFirstEnumeration();
        DefaultMutableTreeNode node;
        while (treeEnum.hasMoreElements()) {
            node = (DefaultMutableTreeNode) treeEnum.nextElement();
            if (node.getUserObject() != null
                    && ((node.getUserObject() instanceof GameModel && expandedGames
                            .contains(node.getUserObject()))
                            || node == pendingFolder || node == completeFolder || node == closedFolder)) {
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
                    && node.getUserObject() instanceof GameRequirementModel) {
                if (requirement
                        && ((GameRequirementModel) node.getUserObject()).equals(
                        		(GameRequirementModel) selectedNodeUserObject)
                        && ((GameModel) ((DefaultMutableTreeNode) node.getParent()).
                        		getUserObject()).equals((GameModel) selectedNodeParent)){
                    gameTree.setSelectionPath(new TreePath(node.getPath()));
                }
            }
            else if (node.getUserObject() != null
                    && node.getUserObject() instanceof GameModel) {
                if (game
                        && ((GameModel) node.getUserObject()).getID() == ((GameModel) 
                        		selectedNodeUserObject)
                                .getID()){
                    gameTree.setSelectionPath(new TreePath(node.getPath()));
                }
            }
        }
        
    }
    
    private void initComponents() {
        
        jScrollPane2 = new javax.swing.JScrollPane();
        gameTree = new javax.swing.JTree();
        gameTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
        gameTree.setRootVisible(false);
        gameTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        
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
    }
    
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree gameTree;
    
    /**
     * Gets a reference to this panel's tree
     * 
     * @return the tree
     */
    public JTree getTree() {
        return gameTree;
    }
}
