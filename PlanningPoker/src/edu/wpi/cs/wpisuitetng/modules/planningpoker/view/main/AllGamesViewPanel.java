/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

import java.awt.Color;

/**
 * This is the main planning poker view. It is intended to be a way of viewing
 * summaries of all the games at once and for voting on games.
 * 
 * @author Team 9
 * @version 1.0
 */
public class AllGamesViewPanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = -6990619499118841478L;
    private GameModel currentSelectionGame;
    
    /**
     * Creates a new AllGamesViewPanel.
     */
    public AllGamesViewPanel() {
        initComponents();
        final JTree tree = gameTree.getTree();
        
        descriptionCard = new JPanel();
        descriptionCard.setLayout(new CardLayout(0, 0));
        jSplitPane3.setRightComponent(descriptionCard);
        
        emptyDescriptionPanel = new JPanel();
        emptyDescriptionPanel.setBackground(Color.WHITE);
        descriptionCard.add(emptyDescriptionPanel, "empty");
        emptyDescriptionPanel.setLayout(new BorderLayout(0, 0));
        
        emptyDescriptionLabel = new JLabel("Select a Game");
        emptyDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emptyDescriptionLabel.setIcon(ImageLoader.getIcon("leftArrow.png"));
        emptyDescriptionPanel.add(emptyDescriptionLabel, BorderLayout.CENTER);
        
        final JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        descriptionCard.add(splitPane, "description");
        
        gameDescriptionPanel = new GameDescriptionPanel();
        splitPane.setLeftComponent(gameDescriptionPanel);
        
        requirementPanel = new JPanel();
        splitPane.setRightComponent(requirementPanel);
        requirementPanel.setLayout(new CardLayout(0, 0));
        
        requirementDescriptionPanel = new RequirementDescriptionPanel();
        requirementPanel.add(requirementDescriptionPanel, "requirement");
        
        final JPanel noRequirementPanel = new JPanel();
        noRequirementPanel.setBackground(Color.WHITE);
        requirementPanel.add(noRequirementPanel, "no requirement");
        noRequirementPanel.setLayout(new BorderLayout(0, 0));
        
        final JLabel lblSelectARequirement = new JLabel("Select a Requirement");
        lblSelectARequirement.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectARequirement.setIcon(ImageLoader.getIcon("leftArrow.png"));
        noRequirementPanel.add(lblSelectARequirement, BorderLayout.CENTER);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                final DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
                
                if (node != null) {
                    
                    currentSelectionGame = null; // reset selected game
                    
                    final Object nodeInfo = node.getUserObject();
                    
                    if (nodeInfo instanceof GameModel) {
                        ((CardLayout) getRequirementPanel().getLayout()).show(
                                getRequirementPanel(), "no requirement");
                        final GameModel game = (GameModel) nodeInfo;
                        currentSelectionGame = game;
                        getGameDescriptionPanel().setGame(game);
                        ((CardLayout)getDescriptionCard().getLayout()).show(getDescriptionCard(), "description");
                    
                    } else if (nodeInfo instanceof GameRequirementModel) {
                    	
                        ((CardLayout) getRequirementPanel().getLayout()).show(
                                getRequirementPanel(), "requirement");
                        final GameRequirementModel req = (GameRequirementModel) nodeInfo;
                        final GameModel parent_game = (GameModel) ((DefaultMutableTreeNode) (node
                                .getParent())).getUserObject();
                        getRequirementDescriptionPanel().setData(
                                CurrentUserController.getInstance().getUser(),
                                parent_game, req);
                        
                        final GameModel game = (GameModel) ((DefaultMutableTreeNode) node
                                .getParent()).getUserObject();
                        getGameDescriptionPanel().setGame(game);
                        currentSelectionGame = game;
                        ((CardLayout)getDescriptionCard().getLayout()).show(getDescriptionCard(), "description");
	                } else {
	                    ((CardLayout)getDescriptionCard().getLayout()).show(getDescriptionCard(), "empty");
	                }
               
                    PlanningPoker.getViewController().displayAdmin(currentSelectionGame);
                }
            }
        });
    }
    
    private void initComponents() {
        
        jSplitPane3 = new javax.swing.JSplitPane();
        
        jSplitPane3.setDividerLocation(190);
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
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
    }
    
    private javax.swing.JSplitPane jSplitPane3;
    private GamesListPanel gameTree;
    private final JPanel requirementPanel;
    private final RequirementDescriptionPanel requirementDescriptionPanel;
    private final GameDescriptionPanel gameDescriptionPanel;
    private JPanel emptyDescriptionPanel;
    private JLabel emptyDescriptionLabel;
    private JPanel descriptionCard;
    
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
     * Gets currently selected game in the tree; either the selected game, or
     * the parent game of the selected requirement
     * 
     * @return the currently selected game parent in the tree, null if currently
     *         selected is not a game or requirement
     */
    public GameModel getSelectedGame() {
        
        return currentSelectionGame;
    }
    
    /**
     * sets the currently selected game in the tree for testing purposes
     * 
     * @param game the game to set
     */
    public void setSelectedGame(GameModel game) {
        this.currentSelectionGame = game;
    }
    
    protected JPanel getDescriptionCard() {
        return descriptionCard;
    }
}
