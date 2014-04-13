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

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;

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
                nameError.setVisible(!isNameValid);
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
                        descriptionError.setVisible(!isDescriptionValid);
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
        selectDeadline.setSelected(true);
        distributed = new javax.swing.JRadioButton();
        live = new javax.swing.JRadioButton();
        nameError = new javax.swing.JLabel();
        descriptionError = new javax.swing.JLabel();
        
        nameLabel.setText("Name:");
        
        descriptionLabel.setText("Description:");
        
        jScrollPane1.setViewportView(descriptionField);
        
        selectDeadline.setText("Deadline");
        
        gameType.add(distributed);
        distributed.setSelected(true);
        distributed.setText("Distributed Game");
        
        gameType.add(live);
        live.setText("Live Game");
        
        nameError.setForeground(new java.awt.Color(255, 0, 0));
        nameError.setText("* Required field!");
        
        descriptionError.setForeground(new java.awt.Color(255, 0, 0));
        descriptionError.setText("* Required field!");
        descriptionError.setFocusable(false);
        
        datePicker = new JXDatePicker(new Date());
        
        JLabel lblDate = new JLabel("Date:");
        
        timeSpinner = new JSpinner();
        timeSpinner.setModel(new SpinnerDateModel());
        JSpinner.DateEditor dEdit = new JSpinner.DateEditor(timeSpinner,
                "h:mm a");
        timeSpinner.setEditor(dEdit);
        timeSpinner.setValue(new Date());
        
        selectDeadline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePicker.setEnabled(selectDeadline.isSelected());
                timeSpinner.setEnabled(selectDeadline.isSelected());
            }
        });
        
        deadlineError = new JLabel("Invalid date!");
        deadlineError.setForeground(new java.awt.Color(255, 0, 0));
        
        JLabel deckLabel = new JLabel("Deck:");
        
        deckComboBox = new JComboBox<DeckModel>();
        deckComboBox.setRenderer(new DeckComboBoxRenderer());
        
        newDeckButton = new JButton("Create Deck");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addGroup(
                                                        layout.createParallelGroup(
                                                                Alignment.LEADING,
                                                                false)
                                                                .addComponent(
                                                                        jScrollPane1,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        408,
                                                                        Short.MAX_VALUE)
                                                                .addGroup(
                                                                        layout.createSequentialGroup()
                                                                                .addGroup(
                                                                                        layout.createParallelGroup(
                                                                                                Alignment.LEADING)
                                                                                                .addGroup(
                                                                                                        layout.createSequentialGroup()
                                                                                                                .addComponent(
                                                                                                                        selectDeadline)
                                                                                                                .addPreferredGap(
                                                                                                                        ComponentPlacement.UNRELATED)
                                                                                                                .addComponent(
                                                                                                                        deadlineError))
                                                                                                .addComponent(
                                                                                                        distributed)
                                                                                                .addComponent(
                                                                                                        live))
                                                                                .addGap(50))
                                                                .addGroup(
                                                                        layout.createSequentialGroup()
                                                                                .addComponent(
                                                                                        nameLabel)
                                                                                .addPreferredGap(
                                                                                        ComponentPlacement.RELATED)
                                                                                .addComponent(
                                                                                        nameError))
                                                                .addGroup(
                                                                        layout.createSequentialGroup()
                                                                                .addComponent(
                                                                                        descriptionLabel)
                                                                                .addPreferredGap(
                                                                                        ComponentPlacement.RELATED)
                                                                                .addComponent(
                                                                                        descriptionError))
                                                                .addComponent(
                                                                        nameField))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        lblDate)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        datePicker,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        timeSpinner,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        GroupLayout.PREFERRED_SIZE))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        deckLabel)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        deckComboBox,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        128,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        newDeckButton)))
                                .addContainerGap(32, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(nameLabel)
                                                .addComponent(nameError))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(nameField,
                                        GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(descriptionLabel)
                                                .addComponent(descriptionError))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1,
                                        GroupLayout.DEFAULT_SIZE, 61,
                                        Short.MAX_VALUE)
                                .addComponent(distributed)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(live)
                                .addGap(5)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(deckLabel)
                                                .addComponent(
                                                        deckComboBox,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addComponent(newDeckButton))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(selectDeadline)
                                                .addComponent(deadlineError))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(
                                                        datePicker,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblDate)
                                                .addComponent(
                                                        timeSpinner,
                                                        GroupLayout.PREFERRED_SIZE,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        GroupLayout.PREFERRED_SIZE))
                                .addGap(24)));
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
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
        if (!selectDeadline.isSelected()) {
            deadlineError.setVisible(false);
            return true;
        }
        
        Date currentDate = new Date();
        Date enteredDate = getDate();
        
        boolean valid = enteredDate.after(currentDate);
        
        deadlineError.setVisible(!valid);
        return valid;
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
        for (DeckModel deck : decks) {
            newModel.addElement(deck);
        }
        deckComboBox.setModel(newModel);
    }
    
    public void setEditGamePanel(NewGamePanel p) {
        parent = p;
    }
    
    class DeckComboBoxRenderer extends BasicComboBoxRenderer {
        /**
         * 
         */
        private static final long serialVersionUID = -6654798255103649031L;
        
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                
                if (-1 < index) {
                    String toolTip = "";
                    for (Double card : ((DeckModel) value).getCards()) {
                        toolTip += NewGameDescriptionPanel.cardFormat
                                .format(card) + ", ";
                    }
                    toolTip = toolTip.replaceAll(", $", "");
                    list.setToolTipText(toolTip);
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
    
    private boolean isNameValid = false;
    private boolean isDescriptionValid = false;
    
    private JSpinner timeSpinner;
    private JXDatePicker datePicker;
    private NewGamePanel parent;
    private javax.swing.JLabel descriptionError;
    public javax.swing.JTextPane descriptionField;
    private javax.swing.JLabel descriptionLabel;
    public javax.swing.JRadioButton distributed;
    public javax.swing.ButtonGroup gameType;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JRadioButton live;
    private javax.swing.JLabel nameError;
    public javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    public javax.swing.JCheckBox selectDeadline;
    private JLabel deadlineError;
    private JComboBox<DeckModel> deckComboBox;
    private DeckModel defaultDeck;
    private JButton newDeckButton;
}
