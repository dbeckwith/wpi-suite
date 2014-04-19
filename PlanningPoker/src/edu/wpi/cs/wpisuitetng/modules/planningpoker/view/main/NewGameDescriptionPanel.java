/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * 
 * @author Lukas
 */
public class NewGameDescriptionPanel extends javax.swing.JPanel implements SimpleListObserver {
    private static final DecimalFormat cardFormat = new DecimalFormat("0.#");
    private static final long serialVersionUID = 4601624442206350512L;
    
    /**
     * Creates new form GameDescription
     */
    public NewGameDescriptionPanel() {
        setBackground(Color.WHITE);
        initComponents();
        
        final Calendar now = new GregorianCalendar();
        final int gameCount = GameListModel.getInstance().getGames().size();
        nameField.setText(String.format("Game %d - %d/%d/%d %d:%d", gameCount + 1,
                now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.YEAR),
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)));
        isNameValid = true;
        
        setErrorBorder(descriptionField, false);
        
        DeckListModel.getInstance().addObserver(this);
        
        final ArrayList<Double> cards = new ArrayList<Double>();
        cards.add(0.0);
        cards.add(1.0);
        cards.add(1.0);
        cards.add(2.0);
        cards.add(3.0);
        cards.add(5.0);
        cards.add(8.0);
        cards.add(13.0);
        
        defaultDeck = new DeckModel("Default", cards, true);
        DeckListModel.getInstance().setDefaultDeck(defaultDeck);
        deckComboBox.addItem(defaultDeck);
        noDeck = new DeckModel("No deck", new ArrayList<Double>(), false);
        deckComboBox.addItem(noDeck);
        generatedDeck = new DeckModel("Generated deck", new ArrayList<Double>(), false);
        deckComboBox.addItem(generatedDeck);
        
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
                isNameValid = (nameField.getText() != null && !nameField.getText().isEmpty());
                setErrorBorder(nameField, isNameValid);
                parentPanel.check();
            }
        });
        
        descriptionField.getDocument().addDocumentListener(new DocumentListener() {
            
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
                
                isDescriptionValid = (descriptionField.getText() != null && !descriptionField
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
                canValidateDeadline();
                parentPanel.check();
            }
        });
        
        newDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.showPanel("newdeckpanel");
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
        this.game = game;
        nameField.setText(game.getName());
        descriptionField.setText(game.getDescription());
        distributed.setSelected(game.getType() == GameType.DISTRIBUTED);
        live.setSelected(game.getType() == GameType.LIVE);
        
        nameField.setEditable(false);
        nameField.setEnabled(false);
        descriptionField.setEditable(false);
        descriptionField.setEnabled(false);
        
        distributed.setEnabled(false);
        live.setEnabled(false);
        
        final DefaultComboBoxModel<DeckModel> decks = (DefaultComboBoxModel<DeckModel>) deckComboBox
                .getModel();
        
        for (int i = 0; i < decks.getSize(); i++) {
            DeckModel deck = decks.getElementAt(i);
            if (deck.getName().equals(game.getDeck())) {
                deckComboBox.setSelectedItem(deck);
            }
        }
        
        if (game.getEndTime() != null) {
            selectDeadline.setSelected(true);
            datePicker.setDate(game.getEndTime());
            timeSpinner.setValue(game.getEndTime());
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        gameType = new javax.swing.ButtonGroup();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        nameField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        descriptionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setBorder(null);
        jScrollPane1.setViewportBorder(null);
        descriptionField = new javax.swing.JTextPane();
        descriptionField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        selectDeadline = new javax.swing.JCheckBox();
        selectDeadline.setBackground(Color.WHITE);
        distributed = new javax.swing.JRadioButton();
        distributed.setBackground(Color.WHITE);
        live = new javax.swing.JRadioButton();
        live.setBackground(Color.WHITE);
        
        nameLabel.setText("Game Name: *");
        
        descriptionLabel.setText("Game Description: *");
        
        jScrollPane1.setViewportView(descriptionField);
        
        selectDeadline.setText("Deadline");
        
        gameType.add(distributed);
        distributed.setSelected(true);
        distributed.setText("Distributed Game");
        
        gameType.add(live);
        live.setText("Live Game");
        
        datePicker = new JXDatePicker(getDefaultDate());
        final JButton eDate = (JButton) datePicker.getComponent(1);
        final JButton dateBtn = (JButton) datePicker.getComponent(1);
        dateBtn.remove(eDate);
        
        dateBtn.setIcon(ImageLoader.getIcon("calendar.png"));
        dateBtn.setFocusPainted(false);
        dateBtn.setMargin(new Insets(0, 0, 0, 0));
        dateBtn.setContentAreaFilled(false);
        dateBtn.setBorderPainted(false);
        dateBtn.setOpaque(false);
        
        final JLabel lblDate = new JLabel("Date:");
        
        timeSpinner = new JSpinner();
        timeSpinner.setModel(new SpinnerDateModel());
        final JSpinner.DateEditor dEdit = new JSpinner.DateEditor(timeSpinner, "h:mm a");
        timeSpinner.setEditor(dEdit);
        timeSpinner.setValue(getDefaultDate());
        
        selectDeadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePicker.setEnabled(selectDeadline.isSelected());
                timeSpinner.setEnabled(selectDeadline.isSelected());
            }
        });
        
        final JLabel deckLabel = new JLabel("Deck:");
        
        deckComboBox = new JComboBox<DeckModel>();
        deckComboBox.setRenderer(new DeckComboBoxRenderer());
        
        deckComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((DeckModel) deckComboBox.getSelectedItem()).toString()
                        .equals("Generated deck")) {
                    maximumValue.setVisible(true);
                    maxValueLabel.setVisible(true);
                    newDeckButton.setEnabled(false);
                    getErrors();
                    if (parentPanel != null) {
                        parentPanel.check();
                    }
                }
                else {
                    maximumValue.setVisible(false);
                    maxValueLabel.setVisible(false);
                    newDeckButton.setEnabled(true);
                    getErrors();
                    if (parentPanel != null) {
                        parentPanel.check();
                    }
                }
            }
        });
        newDeckButton = new JButton("New Deck");
        
        maximumValue = new JTextField();
        maximumValue.setDocument(new JTextFieldLimit(2));
        maximumValue.setColumns(10);
        
        maximumValue.getDocument().addDocumentListener(new DocumentListener() {
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
                getErrors();
                parentPanel.check();
            }
        });
        
        maxValueLabel = new JLabel("Max value (< 99)");
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(Alignment.LEADING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.LEADING)
                                                                                .addComponent(
                                                                                        jScrollPane1,
                                                                                        GroupLayout.DEFAULT_SIZE,
                                                                                        418,
                                                                                        Short.MAX_VALUE)
                                                                                .addComponent(
                                                                                        nameLabel)
                                                                                .addComponent(
                                                                                        descriptionLabel)
                                                                                .addComponent(
                                                                                        nameField,
                                                                                        GroupLayout.DEFAULT_SIZE,
                                                                                        418,
                                                                                        Short.MAX_VALUE)
                                                                                .addGroup(
                                                                                        layout.createSequentialGroup()
                                                                                                .addGroup(
                                                                                                        layout.createParallelGroup(
                                                                                                                Alignment.LEADING)
                                                                                                                .addGroup(
                                                                                                                        layout.createSequentialGroup()
                                                                                                                                .addComponent(
                                                                                                                                        deckLabel)
                                                                                                                                .addPreferredGap(
                                                                                                                                        ComponentPlacement.RELATED)
                                                                                                                                .addComponent(
                                                                                                                                        deckComboBox,
                                                                                                                                        GroupLayout.PREFERRED_SIZE,
                                                                                                                                        175,
                                                                                                                                        GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(
                                                                                                                        live))
                                                                                                .addPreferredGap(
                                                                                                        ComponentPlacement.RELATED)
                                                                                                .addGroup(
                                                                                                        layout.createParallelGroup(
                                                                                                                Alignment.LEADING)
                                                                                                                .addGroup(
                                                                                                                        layout.createSequentialGroup()
                                                                                                                                .addComponent(
                                                                                                                                        maximumValue,
                                                                                                                                        GroupLayout.DEFAULT_SIZE,
                                                                                                                                        42,
                                                                                                                                        Short.MAX_VALUE)
                                                                                                                                .addPreferredGap(
                                                                                                                                        ComponentPlacement.RELATED)
                                                                                                                                .addComponent(
                                                                                                                                        newDeckButton)
                                                                                                                                .addGap(12))
                                                                                                                .addComponent(
                                                                                                                        maxValueLabel)))
                                                                                .addComponent(
                                                                                        selectDeadline)
                                                                                .addComponent(
                                                                                        distributed))
                                                                .addGap(13))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(lblDate)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(datePicker,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(timeSpinner,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap()))));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nameLabel)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(descriptionLabel)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                        .addComponent(distributed)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addGroup(
                                layout.createParallelGroup(Alignment.BASELINE).addComponent(live)
                                        .addComponent(maxValueLabel))
                        .addGap(5)
                        .addGroup(
                                layout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(deckLabel)
                                        .addComponent(deckComboBox, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newDeckButton)
                                        .addComponent(maximumValue, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                        .addGap(18)
                        .addComponent(selectDeadline)
                        .addGap(8)
                        .addGroup(
                                layout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblDate)
                                        .addComponent(datePicker, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)).addGap(6)));
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private static Date getDefaultDate() {
        final Date currentDate = new Date();
        // default date is one hour from now
        return new Date(currentDate.getTime() + 1000 * 60 * 60);
    }
    
    /**
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
            ret =  null;
        }
        return ret;
    }
    
    /**
     * @return deck for this game
     */
    public DeckModel getDeck() {
        return (DeckModel) deckComboBox.getSelectedItem();
    }
    
    /**
     * Validates the user-entered deadline. If entered deadline is before the
     * current date and time return false and show error.
     * 
     * @return valid
     */
    public boolean canValidateDeadline() {
        datePicker.setEnabled(selectDeadline.isSelected());
        timeSpinner.setEnabled(selectDeadline.isSelected());
        
        if (!selectDeadline.isSelected()) {
            return true;
        }
        
        final Date currentDate = new Date();
        final Date enteredDate = getDate();
        
        final boolean valid = enteredDate.after(currentDate);
        return valid;
    }
    
    /**
     * Makes sure maximum value is a positive integer
     * 
     * @return valid@return valid
     */
    public boolean canValidateMaximum() {
        return !deckComboBox.getSelectedItem().toString().equals("Generated deck")
                || Pattern.matches("[1-9][0-9]*", maximumValue.getText());
    }
    
    /**
     * Return maximum value
     */
    public int getMaxCardValue() {
        return Integer.parseInt(maximumValue.getText());
    }
    
    /**
     * Check whether the name, description, and date have valid data
     * 
     * @return valid
     */
    public boolean canValidateForm() {
        final boolean valid = isNameValid && isDescriptionValid && canValidateDeadline();
        return valid;
    }
    
    /**
     * Populates deck combo box with new decks
     * 
     * @param decks
     */
    @Override
    public void listUpdated() {
        final ArrayList<DeckModel> decks = DeckListModel.getInstance().getDecks();
        final DefaultComboBoxModel<DeckModel> newModel = new DefaultComboBoxModel<DeckModel>();
        newModel.addElement(defaultDeck);
        newModel.addElement(noDeck);
        newModel.addElement(generatedDeck);
        for (DeckModel deck : decks) {
            newModel.addElement(deck);
        }
        deckComboBox.setModel(newModel);
        
        if (game != null) {
            for (int i = 0; i < newModel.getSize(); i++) {
                DeckModel deck = newModel.getElementAt(i);
                System.out.println(i + " deck  " + deck.getName());
                if (deck != null && deck.getName().equals(game.getDeck().getName())) {
                    deckComboBox.setSelectedItem(deck);
                }
            }
        }
    }
    
    public class JTextFieldLimit extends PlainDocument {
        /**
         * 
         */
        private static final long serialVersionUID = 1859032164698771516L;
        private final int limit;
        
        JTextFieldLimit(int limit) {
            this.limit = limit;
        }
        
        @Override
        public void insertString(int offset, String str, AttributeSet attr)
                throws BadLocationException {
            if (str == null) {
                return;
            }
            
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
    
    /**
     * Renders tooltips for deck selection combobox
     */
    class DeckComboBoxRenderer extends BasicComboBoxRenderer {
        private static final long serialVersionUID = -6654798255103649031L;
        
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                
                if (-1 < index && (index == 0 || index > 2)) {
                    String toolTip = "";
                    for (Double card : ((DeckModel) value).getCards()) {
                        toolTip += NewGameDescriptionPanel.cardFormat.format(card) + ", ";
                    }
                    toolTip = toolTip.replaceAll(", $", "");
                    list.setToolTipText(toolTip);
                }
                else if (index == 1) {
                    list.setToolTipText("User-entered values, no limit");
                }
                else if (index == 2) {
                    list.setToolTipText("Create a deck with cards from 1 to specified value");
                }
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            
            setFont(list.getFont());
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    /**
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
        if (!canValidateMaximum()) {
            errors.add("Maximum value is invalid");
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
    
    public void setEditGamePanel(NewGamePanel p) {
        parentPanel = p;
    }
    
    /**
     * @return the descriptionField
     */
    public javax.swing.JTextPane getDescriptionField() {
        return descriptionField;
    }
    
    /**
     * @return the distributed
     */
    public javax.swing.JRadioButton getDistributed() {
        return distributed;
    }
    
    /**
     * @return the nameField
     */
    public javax.swing.JTextField getNameField() {
        return nameField;
    }
    
    private GameModel game;
    
    private boolean isNameValid = false;
    private boolean isDescriptionValid = false;
    
    private JSpinner timeSpinner;
    private JXDatePicker datePicker;
    private NewGamePanel parentPanel;
    private javax.swing.JTextPane descriptionField;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JRadioButton distributed;
    private javax.swing.ButtonGroup gameType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton live;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JCheckBox selectDeadline;
    private JComboBox<DeckModel> deckComboBox;
    private final DeckModel defaultDeck;
    private final DeckModel generatedDeck;
    private final DeckModel noDeck;
    private JButton newDeckButton;
    private JTextField maximumValue;
    private JLabel maxValueLabel;
}
