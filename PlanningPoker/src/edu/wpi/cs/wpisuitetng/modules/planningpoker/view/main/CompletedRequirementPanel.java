/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GameStatusObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * 
 * This panel shows information about a requirement that is no longer accepting
 * votes. It shows a general summary of the votes that were made, as well as the
 * ability to set the final estimate of the requirement.
 * 
 * @author Team 9
 * @version 1.0
 */
public class CompletedRequirementPanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = -7702704328142908459L;
    
    private DefaultTableModel tableModel;
    
    private GameRequirementModel req;
    private GameModel parentModel;
    
    /**
     * Creates a new CompletedRequirementPanel
     *
     */
    public CompletedRequirementPanel() {
        setBackground(Color.WHITE);
        // setup tablemodel (using autogenerted netbeans code)
        initComponents();
        tableScrollPane.getViewport().setBackground(Color.WHITE);
    }
    
    /**
     * Sets the requirement that this panel is showing information about
     * 
     * @param parent_game
     *        the game that the requirement belongs to
     * @param req
     *        the requirement to show
     */
    public void setRequirement(GameModel parent_game, GameRequirementModel req) {
        this.req = req;
        parentModel = parent_game;
        parentModel.addStatusListener(new GameStatusObserver() {
            
            @Override
            public void statusChanged(GameModel game) {
                checkDisplayFinal();
            }
            
        });
        
        checkDisplayFinal();
        
        meanValueLabel.setText(String.format("%1.1f", req.getEstimateMean()));
        medianValueLabel
                .setText(String.format("%1.1f", req.getEstimateMedian()));
        if (parent_game.getOwner().equals(CurrentUserController.getInstance().getUser())
                && req.getFinalEstimate() == 0) {
            finalEstimateField
                    .setText((int) (req.getEstimateMean() + 0.5) + "");
        }
        else {
            finalEstimateField.setText(req.getFinalEstimate() + "");
        }
        
        tableModel = new javax.swing.table.DefaultTableModel() {
            
            /**
             * 
             */
            private static final long serialVersionUID = 766575328559324615L;
            
            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        
        tableModel.addColumn("User");
        tableModel.addColumn("Estimate");
        for (Estimate e : req.getEstimates()) {
            String[] row = new String[2];
            row[0] = e.getName() == null ? "???" : e.getName();
            row[1] = String.format("%.1f", e.getEstimate());
            tableModel.addRow(row);
        }
        voteResultTable.setModel(tableModel);
        
        votedUsersValueLabel
                .setText(Integer.toString(tableModel.getRowCount()));
        notePane.setText(req.getEstimateNote());
    }
    
    private void displayFinalEstimateFields(boolean b) {
        finalEstimateField.setEditable(b);
        lblError.setVisible(b);   //TODO maybe change this
        saveFinalEstimateButton.setVisible(b);
        notePane.setEditable(b);
    }
    
    /**
     * Checks whether the final estimate field should be set, and changes them
     * based on it.
     */
    private void checkDisplayFinal() {
        displayFinalEstimateFields(CurrentUserController.getInstance().getUser()
                .equals(parentModel.getOwner())
                && !parentModel.isClosed());
    }
    
    private void initComponents() {
        
        jSeparator1 = new javax.swing.JSeparator();
        meanLabel = new javax.swing.JLabel();
        medianLabel = new javax.swing.JLabel();
        tableScrollPane = new javax.swing.JScrollPane();
        voteResultTable = new javax.swing.JTable();
        voteResultTable.setBackground(Color.WHITE);
        meanValueLabel = new javax.swing.JLabel();
        medianValueLabel = new javax.swing.JLabel();
        
        meanLabel.setText("Mean:");
        
        medianLabel.setText("Median:");
        
        tableScrollPane.setBackground(Color.WHITE);
        
        voteResultTable.setModel(new DefaultTableModel(new Object[][] { { null,
                null }, }, new String[] { "User", "Estimate" }) {
            /**
             * 
             */
            private static final long serialVersionUID = -5144539907705808611L;
            private final boolean[] columnEditables = new boolean[] { false, false };
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        final Font temp_Font;
        temp_Font = voteResultTable.getTableHeader().getFont();
        voteResultTable.getTableHeader().setFont(
                temp_Font.deriveFont(Font.BOLD));
        voteResultTable.getColumnModel().getColumn(0).setPreferredWidth(253);
        voteResultTable.getColumnModel().getColumn(1).setResizable(false);
        voteResultTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        tableScrollPane.setViewportView(voteResultTable);
        
        meanValueLabel.setText("XYZ");
        
        medianValueLabel.setText("ABC");
        
        lblFinalEstimate = new JLabel("Final Estimate:");
        
        finalEstimateField = new JTextField();
        finalEstimateField.setColumns(10);
        saveFinalEstimateButton = new JButton("Save");
        saveFinalEstimateButton.setEnabled(false);
        saveFinalEstimateButton.setIcon(ImageLoader.getIcon("Save.png"));
        
        finalEstimateField.getDocument().addDocumentListener(
                new DocumentListener() {
                    
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        validate();
                        
                    }
                    
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        validate();
                        
                    }
                    
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        validate();
                        
                    }
                    
                    private void validate() {
                        validatePanel();
                    }
                    
                });
        
        
        saveFinalEstimateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                lblError.setVisible(false);
                if (req.getFinalEstimate() != 0) {
                    req.setEstimateNote("Manual change: \n" + notePane.getText());
                } else {
                    req.setEstimateNote(notePane.getText());
                }
                req.setFinalEstimate(Integer.parseInt(finalEstimateField
                        .getText()));
                UpdateGamesController.getInstance().updateGame(parentModel);
                final ArrayList<GameStatusObserver> gsos = parentModel
                        .getStatusObservers();
                for (int i = 0; i < gsos.size(); i++) {
                    gsos.get(i).statusChanged(parentModel);
                }
            }
        });
        
        lblError = new JLabel("* Positive Integers Only!");
        lblError.setVisible(false);
        lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblError.setForeground(Color.RED);
        
        final JLabel lblGameStatistics = new JLabel("Game Statistics:");
        lblGameStatistics.setFont(new Font("Dialog", Font.BOLD, 12));
        
        final JLabel votedUsersLabel = new JLabel("Users Voted:");
        
        votedUsersValueLabel = new JLabel("123");
        
        final JLabel lblNote = new JLabel("Note: ");
        
        notePane = new JTextPane();
        notePane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        
        notePane.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void removeUpdate(DocumentEvent arg0) {
                validate();
            }
            
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                validate();
            }
            
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                validate();
            }
            
            private void validate() {
                validatePanel();
            }
        });
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 879,
                        Short.MAX_VALUE)
                .addGroup(
                        layout.createSequentialGroup().addContainerGap()
                                .addComponent(meanLabel)
                                .addContainerGap(839, Short.MAX_VALUE))
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.LEADING)
                                                                                .addComponent(
                                                                                        lblGameStatistics)
                                                                                .addComponent(
                                                                                        medianLabel))
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.LEADING)
                                                                                .addGroup(
                                                                                        layout.createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                        ComponentPlacement.RELATED)
                                                                                                .addComponent(
                                                                                                        jSeparator1,
                                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                                        GroupLayout.DEFAULT_SIZE,
                                                                                                        GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(
                                                                                        layout.createSequentialGroup()
                                                                                                .addGap(51)
                                                                                                .addGroup(
                                                                                                        layout.createParallelGroup(
                                                                                                                Alignment.TRAILING)
                                                                                                                .addComponent(
                                                                                                                        meanValueLabel)
                                                                                                                .addComponent(
                                                                                                                        medianValueLabel)
                                                                                                                .addComponent(
                                                                                                                        votedUsersValueLabel)))))
                                                .addComponent(votedUsersLabel))
                                .addGap(224)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.TRAILING)
                                                .addComponent(lblFinalEstimate)
                                                .addComponent(lblNote))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addGroup(
                                                        Alignment.TRAILING,
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        notePane,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        209,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED,
                                                                        36,
                                                                        Short.MAX_VALUE)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.TRAILING)
                                                                                .addComponent(
                                                                                        saveFinalEstimateButton)
                                                                                .addComponent(
                                                                                        lblError)))
                                                .addComponent(
                                                        finalEstimateField,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        106,
                                                        GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()));
        layout.setVerticalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addComponent(tableScrollPane,
                                        GroupLayout.DEFAULT_SIZE, 186,
                                        Short.MAX_VALUE)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED,
                                                                        45,
                                                                        Short.MAX_VALUE)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.TRAILING)
                                                                                .addGroup(
                                                                                        layout.createSequentialGroup()
                                                                                                .addGroup(
                                                                                                        layout.createParallelGroup(
                                                                                                                Alignment.LEADING)
                                                                                                                .addComponent(
                                                                                                                        jSeparator1,
                                                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                                                        10,
                                                                                                                        GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(
                                                                                                                        lblGameStatistics))
                                                                                                .addPreferredGap(
                                                                                                        ComponentPlacement.RELATED)
                                                                                                .addGroup(
                                                                                                        layout.createParallelGroup(
                                                                                                                Alignment.BASELINE)
                                                                                                                .addComponent(
                                                                                                                        votedUsersLabel)
                                                                                                                .addComponent(
                                                                                                                        votedUsersValueLabel))
                                                                                                .addPreferredGap(
                                                                                                        ComponentPlacement.RELATED)
                                                                                                .addGroup(
                                                                                                        layout.createParallelGroup(
                                                                                                                Alignment.BASELINE)
                                                                                                                .addComponent(
                                                                                                                        meanLabel)
                                                                                                                .addComponent(
                                                                                                                        meanValueLabel)))
                                                                                .addComponent(
                                                                                        lblError))
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.LEADING)
                                                                                .addGroup(
                                                                                        layout.createParallelGroup(
                                                                                                Alignment.BASELINE)
                                                                                                .addComponent(
                                                                                                        medianLabel)
                                                                                                .addComponent(
                                                                                                        medianValueLabel))
                                                                                .addComponent(
                                                                                        saveFinalEstimateButton))
                                                                .addContainerGap())
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGap(18)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.BASELINE)
                                                                                .addComponent(
                                                                                        finalEstimateField,
                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                        GroupLayout.DEFAULT_SIZE,
                                                                                        GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(
                                                                                        lblFinalEstimate))
                                                                .addPreferredGap(
                                                                        ComponentPlacement.UNRELATED)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.LEADING)
                                                                                .addComponent(
                                                                                        notePane,
                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                        62,
                                                                                        GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(
                                                                                        lblNote))
                                                                .addGap(32)))));
        setLayout(layout);
    }
    
    /**
     * Validates the user inputs so the GUI can react accordingly
     */
    private void validatePanel() {
        final String pattern = "^[\\s]*$";
        try {
            final int finalEstimate = Integer.parseInt(finalEstimateField
                    .getText());
            if (finalEstimate == req.getFinalEstimate()) {
                lblError.setVisible(false);
                saveFinalEstimateButton.setEnabled(false);
            } else if (finalEstimate <= 0) {
                //set error label
                lblError.setText("* Positive Integers Only!");
                lblError.setVisible(true);
                saveFinalEstimateButton.setEnabled(false);
            } else if (Pattern.matches(pattern, notePane.getText()) 
                    && req.getFinalEstimate() != 0) {
                lblError.setText("* You Must Add a Note!");
                lblError.setVisible(true);
                saveFinalEstimateButton.setEnabled(false);
            } else {
                lblError.setVisible(false);
                saveFinalEstimateButton.setEnabled(true);
            }
        }
        catch (NumberFormatException e) {
            //set error label
            lblError.setText("* Positive Integers Only!");
            lblError.setVisible(true);
            saveFinalEstimateButton.setEnabled(false);
        }
    }
    
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel meanLabel;
    private javax.swing.JLabel meanValueLabel;
    private javax.swing.JLabel medianLabel;
    private javax.swing.JLabel medianValueLabel;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JTable voteResultTable;
    private JLabel lblFinalEstimate;
    private JTextField finalEstimateField;
    private JLabel lblError;
    private JButton saveFinalEstimateButton;
    private JLabel votedUsersValueLabel;
    private JTextPane notePane;
}
