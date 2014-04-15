/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.PlainDocument;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JTextField;

/**
 * 
 * @author Lukas
 */
public class NewGameDescriptionPanel extends javax.swing.JPanel implements
        SimpleListObserver {
    private static final DecimalFormat cardFormat = new DecimalFormat("0.#");
    private static final long serialVersionUID = 4601624442206350512L;
    
    /**
     * Creates new form GameDescription
     */
    public NewGameDescriptionPanel() {
        setBackground(Color.WHITE);
        initComponents();
        
        DeckListModel.getInstance().addObserver(this);
        
        ArrayList<Double> cards = new ArrayList<Double>();
        cards.add(0.0);
        cards.add(1.0);
        cards.add(1.0);
        cards.add(2.0);
        cards.add(3.0);
        cards.add(5.0);
        cards.add(8.0);
        cards.add(13.0);
        
        defaultDeck = new DeckModel("Default", cards, false);
        DeckListModel.getInstance().setDefaultDeck(defaultDeck);
        deckComboBox.addItem(defaultDeck);
        noDeck = new DeckModel("No deck", new ArrayList<Double>(), false);
        deckComboBox.addItem(noDeck);
        generatedDeck = new DeckModel("Generated deck",
                new ArrayList<Double>(), false);
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
                isNameValid = (nameField.getText() != null && !nameField
                        .getText().isEmpty());
                parent.check();
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
                        
                        isDescriptionValid = (descriptionField.getText() != null && !descriptionField
                                .getText().isEmpty());
                        parent.check();
                    }
                });
        
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateDeadline();
                parent.check();
            }
        });
        
        timeSpinner.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                validateDeadline();
                parent.check();
            }
        });
        
        selectDeadline.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                validateDeadline();
                parent.check();
            }
        });
        
        newDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showPanel("newdeckpanel");
            }
        });
        
        validateDeadline();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        gameType = new javax.swing.ButtonGroup();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new javax.swing.JTextPane();
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
        JButton eDate = (JButton) datePicker.getComponent(1);
        JButton dateBtn = (JButton) datePicker.getComponent(1);
        dateBtn.remove(eDate);
        
        dateBtn.setIcon(ImageLoader.getIcon("calendar.png"));
        dateBtn.setFocusPainted(false);
        dateBtn.setMargin(new Insets(0, 0, 0, 0));
        dateBtn.setContentAreaFilled(false);
        dateBtn.setBorderPainted(false);
        dateBtn.setOpaque(false);
        
        JLabel lblDate = new JLabel("Date:");
        
        timeSpinner = new JSpinner();
        timeSpinner.setModel(new SpinnerDateModel());
        JSpinner.DateEditor dEdit = new JSpinner.DateEditor(timeSpinner,
                "h:mm a");
        timeSpinner.setEditor(dEdit);
        timeSpinner.setValue(getDefaultDate());
        
        selectDeadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePicker.setEnabled(selectDeadline.isSelected());
                timeSpinner.setEnabled(selectDeadline.isSelected());
            }
        });
        
        JLabel deckLabel = new JLabel("Deck:");
        
        deckComboBox = new JComboBox<DeckModel>();
        deckComboBox.setRenderer(new DeckComboBoxRenderer());
        
        deckComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((DeckModel) deckComboBox.getSelectedItem()).toString()
                        .equals("Generated deck")) {
                    maximumValue.setEnabled(true);
                    maximumValue.setEditable(true);
                    maxValueLabel.setEnabled(true);
                    getErrors();
                }
                else {
                    maximumValue.setEnabled(false);
                    maximumValue.setEditable(false);
                    maxValueLabel.setEnabled(false);
                    getErrors();
                }
            }
        });
        newDeckButton = new JButton("Create Deck");
        
        maximumValue = new JTextField();
        maximumValue.setDocument(new JTextFieldLimit(2));
        maximumValue.setEditable(false);
        maximumValue.setEnabled(false);
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
            
            public void validate() {
                getErrors();
            }
        });
        
        maxValueLabel = new JLabel("Max value *");
        maxValueLabel.setEnabled(false);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                                .addComponent(nameLabel)
                                .addComponent(descriptionLabel)
                                .addComponent(nameField, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(deckLabel)
                                            .addPreferredGap(ComponentPlacement.RELATED)
                                            .addComponent(deckComboBox, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(selectDeadline)
                                        .addComponent(distributed)
                                        .addComponent(live))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(maximumValue, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                                            .addPreferredGap(ComponentPlacement.RELATED)
                                            .addComponent(newDeckButton)
                                            .addGap(12))
                                        .addComponent(maxValueLabel))))
                            .addGap(13))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblDate)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(nameLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(descriptionLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(distributed)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(live))
                        .addComponent(maxValueLabel))
                    .addGap(5)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(deckLabel)
                        .addComponent(deckComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(maximumValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(newDeckButton))
                    .addGap(18)
                    .addComponent(selectDeadline)
                    .addGap(8)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblDate)
                        .addComponent(datePicker, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(timeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(6))
        );
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private Date getDefaultDate() {
        Date currentDate = new Date();
        // default date is one hour from now
        return new Date(currentDate.getTime() + 1000 * 60 * 60);
    }
    
    /**
     * @return selected date and time
     */
    public Date getDate() {
        // We need to go through all of these incantations because almost every
        // relevant method in Date is deprecated...
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(datePicker.getDate());
        
        GregorianCalendar time = new GregorianCalendar();
        time.setTime(((SpinnerDateModel) timeSpinner.getModel()).getDate());
        
        date.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        
        if (selectDeadline.isSelected()) {
            return date.getTime();
        }
        else {
            return null;
        }
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
    public boolean validateDeadline() {
        datePicker.setEnabled(selectDeadline.isSelected());
        timeSpinner.setEnabled(selectDeadline.isSelected());
        
        if (!selectDeadline.isSelected()) { return true; }
        
        Date currentDate = new Date();
        Date enteredDate = getDate();
        
        boolean valid = enteredDate.after(currentDate);
        return valid;
    }
    
    /**
     * Makes sure maximum value is a positive integer
     * 
     * @return valid@return valid
     */
    public boolean validateMaximum() {
        return Pattern.matches(maximumValue.getText(), "[1-9][0-9]?");
    }
    
    /**
     * Check whether the name, description, and date have valid data
     * 
     * @return valid
     */
    public boolean validateForm() {
        boolean valid = isNameValid && isDescriptionValid && validateDeadline();
        return valid;
    }
    
    /**
     * Populates deck combo box with new decks
     * 
     * @param decks
     */
    @Override
    public void listUpdated() {
        ArrayList<DeckModel> decks = DeckListModel.getInstance().getDecks();
        DefaultComboBoxModel<DeckModel> newModel = new DefaultComboBoxModel<DeckModel>();
        newModel.addElement(defaultDeck);
        newModel.addElement(noDeck);
        newModel.addElement(generatedDeck);
        for (DeckModel deck : decks) {
            newModel.addElement(deck);
        }
        deckComboBox.setModel(newModel);
    }
    
    public class JTextFieldLimit extends PlainDocument {
        private int limit;
        
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }
        
        public void insertString(int offset, String str, AttributeSet attr)
                throws BadLocationException {
            if (str == null)
                return;
            
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
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                
                if (-1 < index && (index == 0 || index > 2)) {
                    String toolTip = "";
                    for (Double card : ((DeckModel) value).getCards()) {
                        toolTip += NewGameDescriptionPanel.cardFormat
                                .format(card) + ", ";
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
    
    public ArrayList<String> getErrors() {
        ArrayList<String> errors = new ArrayList<>();
        if (!isNameValid) {
            errors.add("Name field is required");
        }
        if (!isDescriptionValid) {
            errors.add("Description field is required");
        }
        if (!validateDeadline()) {
            errors.add("Deadline is invalid");
        }
        if (!validateMaximum()) {
            errors.add("Maximum value must be a positive integer");
        }
        return errors;
    }
    
    public void setEditGamePanel(NewGamePanel p) {
        parent = p;
    }
    
    private boolean isNameValid = false;
    private boolean isDescriptionValid = false;
    
    private JSpinner timeSpinner;
    private JXDatePicker datePicker;
    private NewGamePanel parent;
    public javax.swing.JTextPane descriptionField;
    private javax.swing.JLabel descriptionLabel;
    public javax.swing.JRadioButton distributed;
    public javax.swing.ButtonGroup gameType;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JRadioButton live;
    public javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    public javax.swing.JCheckBox selectDeadline;
    private JComboBox<DeckModel> deckComboBox;
    private DeckModel defaultDeck;
    private DeckModel generatedDeck;
    private DeckModel noDeck;
    private JButton newDeckButton;
    private JTextField maximumValue;
    private JLabel maxValueLabel;
}
