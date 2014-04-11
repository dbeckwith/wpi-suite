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

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;


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
    private GameModel currentSelectionGame;
    
    /**
     * Creates new form GameViewPanel
     */
    public AllGamesViewPanel() {
        initComponents();
        final JTree tree = gameTree.getTree();
        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setRightComponent(splitPane);
        
        gameDescriptionPanel = new GameDescriptionPanel();
        splitPane.setLeftComponent(gameDescriptionPanel);
        
        requirementPanel = new JPanel();
        splitPane.setRightComponent(requirementPanel);
        requirementPanel.setLayout(new CardLayout(0, 0));
        
        requirementDescriptionPanel = new RequirementDescriptionPanel();
        requirementPanel.add(requirementDescriptionPanel, "requirement");
        
        JPanel noRequirementPanel = new JPanel();
        requirementPanel.add(noRequirementPanel, "no requirement");
        noRequirementPanel.setLayout(new BorderLayout(0, 0));
        
        JLabel lblSelectARequirement = new JLabel("Select a requirement");
        lblSelectARequirement.setHorizontalAlignment(SwingConstants.CENTER);
        noRequirementPanel.add(lblSelectARequirement, BorderLayout.CENTER);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
                
                if (node == null) {
                    return;
                }
                
                currentSelectionGame = null; // reset selected game
                
                Object nodeInfo = node.getUserObject();
                if (nodeInfo instanceof GameModel) {
                    ((CardLayout) getRequirementPanel().getLayout()).show(
                            getRequirementPanel(), "no requirement");
                    GameModel game = (GameModel) nodeInfo;
                    currentSelectionGame = game;
                    getGameDescriptionPanel().setGame(game);
                } else if (nodeInfo instanceof GameRequirementModel) {
                    ((CardLayout) getRequirementPanel().getLayout()).show(
                            getRequirementPanel(), "requirement");
                    GameRequirementModel req = (GameRequirementModel) nodeInfo;
                    GameModel parent_game = (GameModel) ((DefaultMutableTreeNode) (node
                            .getParent())).getUserObject();
                    getRequirementDescriptionPanel().setData(
                            CurrentUserController.getInstance().getUser(),
                            parent_game, req);
                    
                    GameModel game = (GameModel) ((DefaultMutableTreeNode) node
                            .getParent()).getUserObject();
                    getGameDescriptionPanel().setGame(game);
                    currentSelectionGame = game;
                }
                if (currentSelectionGame != null) {
                    PlanningPoker.getViewController().displayAdmin(
                            currentSelectionGame);
                }
            }
        });
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
    }// </editor-fold>//GEN-END:initComponents
    
    private javax.swing.JSplitPane jSplitPane3;
    private GamesListPanel gameTree;
    private JPanel requirementPanel;
    private RequirementDescriptionPanel requirementDescriptionPanel;
    private GameDescriptionPanel gameDescriptionPanel;
    
    protected JPanel getRequirementPanel() {
        return requirementPanel;
    }
    
    protected RequirementDescriptionPanel getRequirementDescriptionPanel() {
        return requirementDescriptionPanel;
    }
    
    protected GameDescriptionPanel getGameDescriptionPanel() {
        return gameDescriptionPanel;
    }
    
    /**
     * gets currently selected game in the tree, either the selected game, or
     * parent game of the selected requirement
     * 
     * @return the currently selected game parent in the tree, null if currently
     *         selected is note a game or requirement
     */
    public GameModel getSelectedGame() {
        
        return currentSelectionGame;
    }
}
