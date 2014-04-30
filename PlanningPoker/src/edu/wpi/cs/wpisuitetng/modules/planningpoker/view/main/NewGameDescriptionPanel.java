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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * The panel to show the form when the user click create game button. The user
 * can fill out this form to set the name of the game, the description for the
 * game, end date of the game, and type of the game
 * 
 * @author Team 9
 * @version 1.0
 */
public class NewGameDescriptionPanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = 4601624442206350512L;
    
    /**
     * Creates new form GameDescription
     */
    public NewGameDescriptionPanel() {
        setBackground(Color.WHITE);
        initComponents();
        
        //set default game name
        final Calendar now = new GregorianCalendar();
        final int gameCount = GameListModel.getInstance().getGames().size();
        final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        final String formatted = formatter.format(now.getTime());
        nameField.setText(String.format("Game %d - ", gameCount + 1)
                + formatted);
        isNameValid = true;
        
        setErrorBorder(descriptionField, false);
        
        GetDecksController.getInstance().retrieveDecks();
        
        nameField.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
            }
            
            private void validate() {
                parentPanel.setHasChanged(true);
                isNameValid = (nameField.getText() != null && !nameField
                        .getText().isEmpty());
                setErrorBorder(nameField, isNameValid);
                parentPanel.check();
            }
        });
        
        descriptionField.getDocument().addDocumentListener(
                new DocumentListener() {
                    
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        validate();
                    }
                    
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        validate();
                    }
                    
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        validate();
                    }
                    
                    private void validate() {
                        parentPanel.setHasChanged(true);
                        isDescriptionValid = (descriptionField.getText() != null && 
                        		!descriptionField
                                .getText().isEmpty());
                        setErrorBorder(descriptionField, isDescriptionValid);
                        parentPanel.check();
                    }
                });
        
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canValidateDeadline();
                parentPanel.check();
            }
        });
        
        timeSpinner.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                canValidateDeadline();
                parentPanel.check();
            }
        });
        
        selectDeadline.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.setHasChanged(true);
                canValidateDeadline();
                parentPanel.check();
            }
        });
        
        canValidateDeadline();
    }
    
    /**
     * Set the panel's contents from the given game for editing
     * 
     * @param game
     *        the game to load information from
     */
    public void setGame(GameModel game) {
        nameField.setText(game.getName());
        descriptionField.setText(game.getDescription());
        
        nameField.setBackground(Color.WHITE);
        nameField.setEditable(false);
        nameField.setEnabled(false);
        descriptionField.setEditable(false);
        descriptionField.setEnabled(false);
        
        
        if (game.getEndTime() != null) {
            selectDeadline.setSelected(true);
            datePicker.setDate(game.getEndTime());
            timeSpinner.setValue(game.getEndTime());
        }
        else {
            selectDeadline.setSelected(false);
            datePicker.setDate(getDefaultDate());
            timeSpinner.setValue(getDefaultDate());
        }
        
        deckOptions.setGame(game);
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        
        nameLabel = new javax.swing.JLabel();
        nameLabel.setToolTipText("The name of the new game.");
        nameField = new javax.swing.JTextField();
        nameField.setToolTipText("The name of the new game.");
        nameField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        descriptionLabel = new javax.swing.JLabel();
        descriptionLabel.setToolTipText("The new game's description.");
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionScrollPane.setBorder(null);
        descriptionScrollPane.setViewportBorder(null);
        descriptionField = new javax.swing.JTextPane();
        descriptionField.setToolTipText("The new game's description.");
        descriptionField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
                null));
        
        nameLabel.setText("Game Name: *");
        
        descriptionLabel.setText("Game Description: *");
        
        descriptionScrollPane.setViewportView(descriptionField);
        
        deckOptions = new DeckOptionsPanel();
        deckOptions.setParent(this);
        
        final JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "Deadline",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBackground(Color.WHITE);
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(descriptionScrollPane, Alignment.LEADING, GroupLayout
                        		.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                        .addComponent(nameField, Alignment.LEADING, GroupLayout
                        		.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                        .addComponent(nameLabel, Alignment.LEADING)
                        .addComponent(descriptionLabel, Alignment.LEADING)
                        .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                        .addComponent(deckOptions, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
                        		529, Short.MAX_VALUE))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(12)
                    .addComponent(nameLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout
                    		.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(descriptionLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(descriptionScrollPane, GroupLayout.DEFAULT_SIZE, 
                    		106, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(deckOptions, GroupLayout.PREFERRED_SIZE, 99, 
                    		GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 94, 
                    		GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        selectDeadline = new javax.swing.JCheckBox();
        selectDeadline.setToolTipText("If checked, the game will end itself automatically at the specified date.");
        selectDeadline.setBackground(Color.WHITE);
        
        selectDeadline.setText("Deadline");
        
        selectDeadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePicker.setEnabled(selectDeadline.isSelected());
                timeSpinner.setEnabled(selectDeadline.isSelected());
            }
        });
        
        final JLabel lblDate = new JLabel("Date:");
        lblDate.setToolTipText("The date that the game will end itself automatically.");
        
        datePicker = new JXDatePicker(getDefaultDate());
        datePicker.setToolTipText("The date that the game will end itself automatically.");
        datePicker.getEditor().setToolTipText("The date that the game will end itself automatically.");
        final JButton eDate = (JButton) datePicker.getComponent(1);
        final JButton dateBtn = (JButton) datePicker.getComponent(1);
        dateBtn.remove(eDate);
        
        dateBtn.setIcon(ImageLoader.getIcon("calendar.png"));
        dateBtn.setFocusPainted(false);
        dateBtn.setMargin(new Insets(0, 0, 0, 0));
        dateBtn.setContentAreaFilled(false);
        dateBtn.setBorderPainted(false);
        dateBtn.setOpaque(false);
        
        timeSpinner = new JSpinner();
        timeSpinner.setToolTipText("The date that the game will end itself automatically.");
        timeSpinner.setModel(new SpinnerDateModel());
        final JSpinner.DateEditor dEdit = new JSpinner.DateEditor(timeSpinner,
                "h:mm a");
        timeSpinner.setEditor(dEdit);
        timeSpinner.setValue(getDefaultDate());
        final GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1
                .setHorizontalGroup(gl_panel_1
                        .createParallelGroup(Alignment.LEADING)
                        .addGroup(
                                gl_panel_1
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                gl_panel_1
                                                        .createParallelGroup(
                                                                Alignment.LEADING)
                                                        .addComponent(
                                                                selectDeadline)
                                                        .addGroup(
                                                                gl_panel_1
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                lblDate)
                                                                        .addGap(12)
                                                                        .addComponent(
                                                                                datePicker,
                                                                                GroupLayout
                                                                                .PREFERRED_SIZE,
                                                                                GroupLayout
                                                                                .DEFAULT_SIZE,
                                                                                GroupLayout
                                                                                .PREFERRED_SIZE)
                                                                        .addGap(6)
                                                                        .addComponent(
                                                                                timeSpinner,
                                                                                GroupLayout
                                                                                .PREFERRED_SIZE,
                                                                                GroupLayout
                                                                                .DEFAULT_SIZE,
                                                                                GroupLayout
                                                                                .PREFERRED_SIZE)))
                                        .addContainerGap(165, Short.MAX_VALUE)));
        gl_panel_1
                .setVerticalGroup(gl_panel_1
                        .createParallelGroup(Alignment.LEADING)
                        .addGroup(
                                gl_panel_1
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(selectDeadline)
                                        .addPreferredGap(
                                                ComponentPlacement.RELATED)
                                        .addGroup(
                                                gl_panel_1
                                                        .createParallelGroup(
                                                                Alignment.LEADING)
                                                        .addGroup(
                                                                gl_panel_1
                                                                        .createSequentialGroup()
                                                                        .addGap(4)
                                                                        .addComponent(
                                                                                lblDate))
                                                        .addComponent(
                                                                datePicker,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(
                                                                gl_panel_1
                                                                        .createSequentialGroup()
                                                                        .addGap(2)
                                                                        .addComponent(
                                                                                timeSpinner,
                                                                                GroupLayout
                                                                                .PREFERRED_SIZE,
                                                                                GroupLayout
                                                                                .DEFAULT_SIZE,
                                                                                GroupLayout
                                                                                .PREFERRED_SIZE)))
                                        .addContainerGap(35, Short.MAX_VALUE)));
        panel_1.setLayout(gl_panel_1);
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private static Date getDefaultDate() {
        final Date currentDate = new Date();
        // default date is one hour from now
        return new Date(currentDate.getTime() + 1000 * 60 * 60);
    }
    
    /**
     * get the date and time set by the user
     * 
     * @return selected date and time
     */
    public Date getDate() {
        // We need to go through all of these incantations because almost every
        // relevant method in Date is deprecated...
        final GregorianCalendar date = new GregorianCalendar();
        date.setTime(datePicker.getDate());
        
        final GregorianCalendar time = new GregorianCalendar();
        time.setTime(((SpinnerDateModel) timeSpinner.getModel()).getDate());
        
        date.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        
        Date ret;
        if (selectDeadline.isSelected()) {
            ret = date.getTime();
        }
        else {
            ret = null;
        }
        return ret;
    }
    
    /**
     * get the deck for this game
     * 
     * @return deck for this game
     */
    public DeckModel getDeck() {
        return deckOptions.getDeck();
    }
    
    /**
     * Validates the user-entered deadline. If entered deadline is before the
     * current date and time return false and show error.
     * 
     * @return valid the boolean value to indicate if the game is still valid
     *         (whether or not pass the deadline)
     */
    public boolean canValidateDeadline() {
    	final boolean valid;
    	
        datePicker.setEnabled(selectDeadline.isSelected());
        timeSpinner.setEnabled(selectDeadline.isSelected());
        
        if (!selectDeadline.isSelected()) { 
        	return true;
        }
        
        final Date currentDate = new Date();
        final Date enteredDate = getDate();
        
        valid = enteredDate.after(currentDate);
        return valid;
    }
    
    /**
     * Check whether the name, description, and date have valid data
     * 
     * @return valid the boolean value to determine if the form is properly
     *         filled.
     */
    public boolean canValidateForm() {
        final boolean valid = isNameValid && isDescriptionValid
                && canValidateDeadline();
        return valid;
    }
    
    /**
     * get a list of errors when creating a new game
     * 
     * @return the list of errors when creating a new game
     */
    public ArrayList<String> getErrors() {
        final ArrayList<String> errors = new ArrayList<>();
        if (!isNameValid) {
            errors.add("Name field is required");
        }
        if (!isDescriptionValid) {
            errors.add("Description field is required");
        }
        if (!canValidateDeadline()) {
            errors.add("Deadline is invalid");
        }
        return errors;
    }
    
    /**
     * Set a components border color to indicate and error
     * 
     * @param c
     *        the component
     * @param valid
     *        whether or not it is valid
     */
    private static void setErrorBorder(JComponent c, boolean valid) {
        if (!valid) {
            c.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        else {
            c.setBorder(BorderFactory.createEtchedBorder());
        }
    }
    
    /**
     * set the parent panel for this subpanel
     * 
     * @param p
     *        the parent panel for this subpanel
     */
    public void setEditGamePanel(NewGamePanel p) {
        parentPanel = p;
        
        deckOptions.addNewDeckButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.showPanel("newdeckpanel");
            }
        });
        
    }
    
    /**
     * check if Parent panel is valid
     */
    public void checkParent() {
        if (parentPanel != null) {
            parentPanel.check();
        }
    }
    
    /**
     * @return the descriptionField
     */
    public JTextPane getDescriptionField() {
        return descriptionField;
        
    }
    
    /**
     * @return the nameField
     */
    public JTextField getNameField() {
        return nameField;
    }
    
    /**
     * Sets the selected deck in the combo box of saved decks to the newest deck
     */
    public void setNewDeck(){
        deckOptions.setNewDeck();
    }

    private boolean isNameValid = false;
    private boolean isDescriptionValid = false;
    
    private JSpinner timeSpinner;
    private JXDatePicker datePicker;
    private NewGamePanel parentPanel;
    private JTextPane descriptionField;
    private JLabel descriptionLabel;
    private JScrollPane descriptionScrollPane;
    private JTextField nameField;
    private JLabel nameLabel;
    private JCheckBox selectDeadline;
    private DeckOptionsPanel deckOptions;
}
