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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

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
     * @param tree
     *        the tree of games
     */
    public CompletedRequirementPanel(JTree tree) {
        setBackground(Color.WHITE);
        // setup tablemodel (using autogenerted netbeans code)
        initComponents();
        this.tree = tree;
        tableScrollPane.getViewport().setBackground(Color.WHITE);
        addAncestorListener(new AncestorListener() {
            
            @Override
            public void ancestorAdded(AncestorEvent event) {
                finalEstimateField.requestFocusInWindow();
                finalEstimateField.selectAll();
            }
            
            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }
            
            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
            
        });
        
        final KeyListener saveOnEnter = new KeyListener() {
            
            private final int enterKey = KeyEvent.VK_ENTER;
            private final int shiftKey = KeyEvent.VK_SHIFT;
            private boolean enterPressed = false;
            private boolean shiftPressed = false;
            
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == enterKey) {
                    enterPressed = true;
                }
                
                if (e.getKeyCode() == shiftKey) {
                    shiftPressed = true;
                }
                
                if (enterPressed) {
                    if (shiftPressed) {
                        saveAndContinue();
                    }
                    else {
                        saveFinalEstimate();
                    }
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == enterKey) {
                    enterPressed = false;
                }
                
                if (e.getKeyCode() == shiftKey) {
                    shiftPressed = false;
                }
            }
            
        };
        finalEstimateField.addKeyListener(saveOnEnter);
        notePane.addKeyListener(saveOnEnter);
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
        if (parent_game.getOwner().equals(CurrentUserController.USER_NAME)
                && req.getFinalEstimate() == 0) {
            finalEstimateField
            .setText((int) (req.getEstimateMean() + 0.5) + "");
        }
        else {
            finalEstimateField.setText(req.getFinalEstimate() + "");
        }
        finalEstimateField.requestFocusInWindow();
        finalEstimateField.selectAll();
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
        voteResultTable.getColumnModel().getColumn(0).setPreferredWidth(253);
        voteResultTable.getColumnModel().getColumn(1).setResizable(false);
        voteResultTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        
        votedUsersValueLabel
        .setText(Integer.toString(tableModel.getRowCount()));
        notePane.setText(req.getEstimateNote());
    }
    
    private void displayFinalEstimateFields(boolean b) {
        finalEstimateField.setEditable(b);
        saveFinalEstimateButton.setVisible(b);
        notePane.setEditable(b);
        btnUpdateRequirementManager.setVisible(b);
        btnSaveAndContinue.setVisible(b);
    }
    
    /**
     * Checks whether the final estimate field should be set, and changes them
     * based on it.
     */
    private void checkDisplayFinal() {
        displayFinalEstimateFields(CurrentUserController.USER_NAME
                .equals(parentModel.getOwner()) && !parentModel.isClosed());
    }
    
    private void initComponents() {
        final Font temp_Font;
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {7, 105, 60, 97, 90, 32, 0, 36, 0};
        gridBagLayout.rowHeights = new int[]{60, 27, 20, 10, 6, 16, 16, 16, 0, 7};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        setLayout(gridBagLayout);
        tableScrollPane = new javax.swing.JScrollPane();
        tableScrollPane.setToolTipText("A list of all the users who must vote on this requirement.");
        voteResultTable = new javax.swing.JTable();
        voteResultTable.setToolTipText("A list of all the users who must vote on this requirement.");
        voteResultTable.setBackground(Color.WHITE);
        
        tableScrollPane.setBackground(Color.WHITE);
        
        voteResultTable.setModel(new DefaultTableModel(new Object[][] { { null,
            null }, }, new String[] { "User", "Estimate" }) {
            /**
             * 
             */
            private static final long serialVersionUID = -5144539907705808611L;
            private final boolean[] columnEditables = new boolean[] { false,
                    false };
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        temp_Font = voteResultTable.getTableHeader().getFont();
        voteResultTable.getTableHeader().setFont(
                temp_Font.deriveFont(Font.BOLD));
        voteResultTable.getColumnModel().getColumn(0).setPreferredWidth(253);
        voteResultTable.getColumnModel().getColumn(1).setResizable(false);
        voteResultTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        tableScrollPane.setViewportView(voteResultTable);
        final GridBagConstraints gbc_tableScrollPane = new GridBagConstraints();
        gbc_tableScrollPane.fill = GridBagConstraints.BOTH;
        gbc_tableScrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_tableScrollPane.gridwidth = 10;
        gbc_tableScrollPane.gridx = 0;
        gbc_tableScrollPane.gridy = 0;
        add(tableScrollPane, gbc_tableScrollPane);
        
        finalEstimateField = new JTextField();
        finalEstimateField.setToolTipText("The final estimate of this requirement; the final value is it to be given, which may or may not actually be based on users' estimates.");
        finalEstimateField.setColumns(10);
        finalEstimateField.setBackground(Color.WHITE);
        finalEstimateField.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
                null, null));
        
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
        
        final JLabel lblGameStatistics = new JLabel("Game Statistics:");
        lblGameStatistics.setFont(new Font("Dialog", Font.BOLD, 12));
        GridBagConstraints gbc_lblGameStatistics = new GridBagConstraints();
        gbc_lblGameStatistics.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblGameStatistics.insets = new Insets(0, 0, 5, 5);
        gbc_lblGameStatistics.gridx = 1;
        gbc_lblGameStatistics.gridy = 2;
        add(lblGameStatistics, gbc_lblGameStatistics);
        
        lblFinalEstimate = new JLabel("Final Estimate:");
        lblFinalEstimate.setToolTipText("The final estimate of this requirement; the final value is it to be given, which may or may not actually be based on users' estimates.");
        final GridBagConstraints gbc_lblFinalEstimate = new GridBagConstraints();
        gbc_lblFinalEstimate.anchor = GridBagConstraints.EAST;
        gbc_lblFinalEstimate.insets = new Insets(0, 0, 5, 5);
        gbc_lblFinalEstimate.gridx = 4;
        gbc_lblFinalEstimate.gridy = 2;
        add(lblFinalEstimate, gbc_lblFinalEstimate);
        final GridBagConstraints gbc_finalEstimateField = new GridBagConstraints();
        gbc_finalEstimateField.fill = GridBagConstraints.HORIZONTAL;
        gbc_finalEstimateField.anchor = GridBagConstraints.NORTH;
        gbc_finalEstimateField.insets = new Insets(0, 0, 5, 5);
        gbc_finalEstimateField.gridwidth = 4;
        gbc_finalEstimateField.gridx = 5;
        gbc_finalEstimateField.gridy = 2;
        add(finalEstimateField, gbc_finalEstimateField);
        
        final JLabel votedUsersLabel = new JLabel("Users Voted:");
        GridBagConstraints gbc_votedUsersLabel = new GridBagConstraints();
        gbc_votedUsersLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_votedUsersLabel.anchor = GridBagConstraints.NORTH;
        gbc_votedUsersLabel.insets = new Insets(0, 0, 5, 5);
        gbc_votedUsersLabel.gridx = 1;
        gbc_votedUsersLabel.gridy = 3;
        add(votedUsersLabel, gbc_votedUsersLabel);
        
        votedUsersValueLabel = new JLabel("123");
        GridBagConstraints gbc_votedUsersValueLabel = new GridBagConstraints();
        gbc_votedUsersValueLabel.anchor = GridBagConstraints.SOUTHWEST;
        gbc_votedUsersValueLabel.insets = new Insets(0, 0, 5, 5);
        gbc_votedUsersValueLabel.gridx = 2;
        gbc_votedUsersValueLabel.gridy = 3;
        add(votedUsersValueLabel, gbc_votedUsersValueLabel);
        
        final JLabel lblNote = new JLabel("Note:");
        lblNote.setToolTipText("A note to describe reasons behind the final estimate. Optional only on the first final estimate given to this requirement. If the final estimate is being changed, the note is required.");
        final GridBagConstraints gbc_lblNote = new GridBagConstraints();
        gbc_lblNote.anchor = GridBagConstraints.EAST;
        gbc_lblNote.insets = new Insets(0, 0, 5, 5);
        gbc_lblNote.gridx = 4;
        gbc_lblNote.gridy = 3;
        add(lblNote, gbc_lblNote);
        
        notePane = new JTextPane();
        notePane.setToolTipText("A note to describe reasons behind the final estimate. Optional only on the first final estimate given to this requirement. If the final estimate is being changed, the note is required.");
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
        final GridBagConstraints gbc_notePane = new GridBagConstraints();
        gbc_notePane.gridheight = 4;
        gbc_notePane.gridwidth = 4;
        gbc_notePane.fill = GridBagConstraints.BOTH;
        gbc_notePane.insets = new Insets(0, 0, 5, 5);
        gbc_notePane.gridx = 5;
        gbc_notePane.gridy = 3;
        add(notePane, gbc_notePane);

        meanLabel = new javax.swing.JLabel();
        
        meanLabel.setText("Mean:");
        final GridBagConstraints gbc_meanLabel = new GridBagConstraints();
        gbc_meanLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_meanLabel.anchor = GridBagConstraints.NORTH;
        gbc_meanLabel.insets = new Insets(0, 0, 5, 5);
        gbc_meanLabel.gridx = 1;
        gbc_meanLabel.gridy = 4;
        add(meanLabel, gbc_meanLabel);
        meanValueLabel = new javax.swing.JLabel();
        
        meanValueLabel.setText("XYZ");
        final GridBagConstraints gbc_meanValueLabel = new GridBagConstraints();
        gbc_meanValueLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_meanValueLabel.insets = new Insets(0, 0, 5, 5);
        gbc_meanValueLabel.gridx = 2;
        gbc_meanValueLabel.gridy = 4;
        add(meanValueLabel, gbc_meanValueLabel);
        medianLabel = new javax.swing.JLabel();
        
        medianLabel.setText("Median:");
        final GridBagConstraints gbc_medianLabel = new GridBagConstraints();
        gbc_medianLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_medianLabel.anchor = GridBagConstraints.NORTH;
        gbc_medianLabel.insets = new Insets(0, 0, 5, 5);
        gbc_medianLabel.gridx = 1;
        gbc_medianLabel.gridy = 5;
        add(medianLabel, gbc_medianLabel);
        
        lblError = new JLabel("* Positive Integers Only!");
        lblError.setVisible(false);
        medianValueLabel = new javax.swing.JLabel();
        
        medianValueLabel.setText("ABC");
        final GridBagConstraints gbc_medianValueLabel = new GridBagConstraints();
        gbc_medianValueLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_medianValueLabel.insets = new Insets(0, 0, 5, 5);
        gbc_medianValueLabel.gridx = 2;
        gbc_medianValueLabel.gridy = 5;
        add(medianValueLabel, gbc_medianValueLabel);
        lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblError.setForeground(Color.RED);
        final GridBagConstraints gbc_lblError = new GridBagConstraints();
        gbc_lblError.gridwidth = 2;
        gbc_lblError.anchor = GridBagConstraints.EAST;
        gbc_lblError.insets = new Insets(0, 0, 5, 5);
        gbc_lblError.gridx = 4;
        gbc_lblError.gridy = 8;
        add(lblError, gbc_lblError);
        saveFinalEstimateButton = new JButton("Save");
        saveFinalEstimateButton.setEnabled(false);
        saveFinalEstimateButton.setIcon(ImageLoader.getIcon("Save.png"));
        
        saveFinalEstimateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                saveFinalEstimate();
            }
        });

        final GridBagConstraints gbc_saveFinalEstimateButton = new GridBagConstraints();
        gbc_saveFinalEstimateButton.insets = new Insets(0, 0, 5, 5);
        gbc_saveFinalEstimateButton.anchor = GridBagConstraints.WEST;
        gbc_saveFinalEstimateButton.gridx = 7;
        gbc_saveFinalEstimateButton.gridy = 8;
        add(saveFinalEstimateButton, gbc_saveFinalEstimateButton);
        
        btnSaveAndContinue = new JButton("Save and Continue");
        btnSaveAndContinue.setEnabled(false);
        btnSaveAndContinue.setIcon(ImageLoader.getIcon("SaveAndContinue.png"));
        
        btnSaveAndContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                saveAndContinue();
            }
        });
        
        GridBagConstraints gbc_btnSaveAndContinue = new GridBagConstraints();
        gbc_btnSaveAndContinue.insets = new Insets(0, 0, 5, 5);
        gbc_btnSaveAndContinue.gridx = 6;
        gbc_btnSaveAndContinue.gridy = 8;
        add(btnSaveAndContinue, gbc_btnSaveAndContinue);
        
        btnUpdateRequirementManager = new JButton("Update Requirement Manager");
        final GridBagConstraints gbc_btnUpdateRequirementManager = new GridBagConstraints();
        gbc_btnUpdateRequirementManager.insets = new Insets(0, 0, 5, 5);
        gbc_btnUpdateRequirementManager.gridx = 8;
        gbc_btnUpdateRequirementManager.gridy = 8;
        add(btnUpdateRequirementManager, gbc_btnUpdateRequirementManager);
        btnUpdateRequirementManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                req.updateRequirementManager();
                UpdateGamesController.getInstance().updateGame(parentModel);
                final ArrayList<GameStatusObserver> gsos = parentModel
                        .getStatusObservers();
                for (int i = 0; i < gsos.size(); i++) {
                    gsos.get(i).statusChanged(parentModel);
                }
            }
        });
    }
    
    /**
     * Validates the user inputs so the GUI can react accordingly
     */
    private void validatePanel() {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                final String pattern = "^[\\s]*$";
                try {
                    final int finalEstimate = Integer
                            .parseInt(finalEstimateField.getText());
                    if (finalEstimate == req.getFinalEstimate()) {
                        lblError.setVisible(false);
                        saveFinalEstimateButton.setEnabled(false);
                        btnSaveAndContinue.setEnabled(false);
                    }
                    else if (finalEstimate <= 0) {
                        //set error label
                        lblError.setText("* Positive Integers Only!");
                        lblError.setVisible(true);
                        saveFinalEstimateButton.setEnabled(false);
                        btnSaveAndContinue.setEnabled(false);
                    }
                    else if (Pattern.matches(pattern, notePane.getText())
                            && req.getFinalEstimate() != 0) {
                        lblError.setText("* You Must Add a Note!");
                        lblError.setVisible(true);
                        saveFinalEstimateButton.setEnabled(false);
                        btnSaveAndContinue.setEnabled(false);
                    }
                    else {
                        lblError.setVisible(false);
                        saveFinalEstimateButton.setEnabled(true);
                        if (parentModel.getRequirements().indexOf(req) < parentModel
                                .getRequirements().size() - 1) {
                            btnSaveAndContinue.setEnabled(true);
                        }
                        else {
                            btnSaveAndContinue.setEnabled(false);
                        }
                    }
                }
                catch (NumberFormatException e) {
                    //set error label
                    lblError.setText("* Positive Integers Only!");
                    lblError.setVisible(true);
                    saveFinalEstimateButton.setEnabled(false);
                    btnSaveAndContinue.setEnabled(false);
                }
                
                if (req.isFromRequirementManager() && (req.getFinalEstimate() != req.getParentEstimate())) {
                    btnUpdateRequirementManager.setEnabled(true);
                } else {
                    btnUpdateRequirementManager.setEnabled(false);
                }
            }
        });
    }
    
    /**
     * Saves the final estimate
     */
    private void saveFinalEstimate() {
        if (saveFinalEstimateButton.isEnabled()) {
            lblError.setVisible(false);
            if (req.getFinalEstimate() != 0
                    && !req.getEstimateNote().startsWith("Manual change: \n")) {
                req.setEstimateNote("Manual change: \n" + notePane.getText());
            }
            else {
                req.setEstimateNote(notePane.getText());
            }
            req.setFinalEstimate(Integer.parseInt(finalEstimateField.getText()));
            UpdateGamesController.getInstance().updateGame(parentModel);
            final ArrayList<GameStatusObserver> gsos = parentModel
                    .getStatusObservers();
            for (int i = 0; i < gsos.size(); i++) {
                gsos.get(i).statusChanged(parentModel);
            }
        }
    }
    
    /**
     * Saves the final estimate and moves to the next game
     */
    private void saveAndContinue() {
        saveFinalEstimate();
        if (parentModel.getRequirements().indexOf(req) < parentModel
                .getRequirements().size() - 1) {
            tree.setSelectionRow(tree.getSelectionRows()[0] + 1);
        }
    }
    
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
    private final JTree tree;
    private JButton btnUpdateRequirementManager;
    private JButton btnSaveAndContinue;
}
