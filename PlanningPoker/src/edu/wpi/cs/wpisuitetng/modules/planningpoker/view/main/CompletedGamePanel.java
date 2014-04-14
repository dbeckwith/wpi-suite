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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import java.awt.Color;

/**
 * 
 * @author Sam Carlberg <slcarlberg@wpi.edu>
 */
public class CompletedGamePanel extends javax.swing.JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7702704328142908459L;
    
    private GameStatusObserver gameStatusListener = null;
    
    private GameModel selectedGame;
    
    /**
     * Creates new form DetailPanel
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
    
    public void setGame(GameModel game) {
        if (selectedGame != null) {
            selectedGame.removeStatusListener(gameStatusListener);
        }
        selectedGame = game;
        selectedGame.addStatusListener(gameStatusListener);
        updateGame();
    }
    
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
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
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
            private boolean[] columnEditables = new boolean[] { false, false, false,
                    false };
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        tableScrollPane.setViewportView(voteResultTable);
        
        final JLabel lblNumberOfRequirements = new JLabel("Number of Requirements:");
        
        numRequirements = new JLabel("");
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                    .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblNumberOfRequirements)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(numRequirements)
                    .addContainerGap(307, Short.MAX_VALUE))
                .addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblNumberOfRequirements)
                            .addComponent(numRequirements)))
                    .addContainerGap())
        );
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JTable voteResultTable;
    private JLabel numRequirements;
    
    protected String getNumRequirements() {
        return numRequirements.getText();
    }
    
    protected void setNumRequirements(String text) {
        numRequirements.setText(text);
    }
}
