/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

import java.awt.Color;
import java.awt.Font;

/**
 * 
 * This panel shows information about a completed game.
 *
 * @author Team 9
 * @version 1.0
 */
public class CompletedGamePanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = -7702704328142908459L;
    
    private GameStatusObserver gameStatusListener = null;
    
    private GameModel selectedGame;
    
    /**
     * Creates a new CompletedGamePanel
     *
     */
    public CompletedGamePanel() {
        setBackground(Color.WHITE);
        initComponents();
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        gameStatusListener = new GameStatusObserver() {
            
            @Override
            public void statusChanged(GameModel game) {
                updateGame();
            }
            
        };
        selectedGame = null;
    }
    
    /**
     * Set the game that this panel should show a summary of.
     * 
     * @param game
     *            the game to show
     */
    public void setGame(GameModel game) {
        if (selectedGame != null) {
            selectedGame.removeStatusListener(gameStatusListener);
        }
        selectedGame = game;
        selectedGame.addStatusListener(gameStatusListener);
        updateGame();
    }
    
    /**
     * Reload information about the game and rebuild the table to show this information.
     */
    private void updateGame() {
        if (selectedGame != null) {
            setNumRequirements(selectedGame.getRequirements().size() + "");
            final DefaultTableModel model = (DefaultTableModel) voteResultTable
                    .getModel();
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            for (GameRequirementModel req : selectedGame.getRequirements()) {
                model.addRow(new Object[] { req.getName(),
                        req.getEstimateMean(), req.getEstimateMedian(),
                        req.getFinalEstimate() });
            }
        }
    }
    
    private void initComponents() {
        
        jSeparator1 = new javax.swing.JSeparator();
        tableScrollPane = new javax.swing.JScrollPane();
        voteResultTable = new javax.swing.JTable();
        
        tableScrollPane.setBackground(new java.awt.Color(153, 0, 102));
        
        voteResultTable.setModel(new DefaultTableModel(new Object[][] { { null,
                null, null, null }, }, new String[] { "Requirement", "Mean",
                "Median", "Final Estimate" }) {
            /**
                     * 
                     */
            private static final long serialVersionUID = -7421202548175051005L;
            private final boolean[] columnEditables = new boolean[] { false, false, false,
                    false };
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        final Font temp_Font;
        temp_Font = voteResultTable.getTableHeader().getFont();
        voteResultTable.getTableHeader().setFont(temp_Font.deriveFont(Font.BOLD));
        tableScrollPane.setViewportView(voteResultTable);
        
        final JLabel lblNumberOfRequirements = new JLabel("Number of Requirements:");
        
        numRequirements = new JLabel("<num reqs>");
        
        final JLabel lblRequirements = new JLabel("Requirements:");
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblNumberOfRequirements)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(numRequirements)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 
                    		GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(257, Short.MAX_VALUE))
                .addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblRequirements)
                    .addContainerGap(370, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblRequirements)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, 
                        		GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblNumberOfRequirements)
                            .addComponent(numRequirements)))
                    .addContainerGap())
        );
        setLayout(layout);
    }
    
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JTable voteResultTable;
    private JLabel numRequirements;
    
    /**
     * Sets the number of requirements
     * @param text
     */
    protected void setNumRequirements(String text) {
        numRequirements.setText(text);
    }
}
