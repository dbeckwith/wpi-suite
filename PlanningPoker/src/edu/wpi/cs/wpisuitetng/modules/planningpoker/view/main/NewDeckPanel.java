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
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;

/**
 * 
 * This panel gives an interface for creating a new planning poker deck.
 *
 * @author Team 9
 * @version 1.0
 */
public class NewDeckPanel extends JPanel {
    
    private static final long serialVersionUID = 4631372194324496204L;
    
    /**
     * 
     * Creates a new NewDeckPanel
     *
     */
    public NewDeckPanel() {
        setBackground(Color.WHITE);
        GetDecksController.getInstance().retrieveDecks();
        
        final SpringLayout springLayout = new SpringLayout();
        setLayout(springLayout);
        
        final JLabel deckLabel = new JLabel("Deck Name: *");
        springLayout.putConstraint(SpringLayout.NORTH, deckLabel, 10,
                SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, deckLabel, 10,
                SpringLayout.WEST, this);
        add(deckLabel);
        
        newDeckName = new JTextField();
        springLayout.putConstraint(SpringLayout.NORTH, newDeckName, 6,
                SpringLayout.SOUTH, deckLabel);
        springLayout.putConstraint(SpringLayout.WEST, newDeckName, 10,
                SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, newDeckName, -10,
                SpringLayout.EAST, this);
        add(newDeckName);
        newDeckName.setColumns(10);
        
        newDeckName.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
                
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
                
            }
            
            private void validate() {
                for (DeckModel deck : DeckListModel.getInstance().getDecks()) {
                    if (deck.toString().equals(newDeckName.getText().trim())
                            || newDeckName.getText().equals("Default")
                            || newDeckName.getText().equals("No deck")
                            || newDeckName.getText().equals("Generated deck")) {
                        isNameValid = false;
                        nameInUse = true;
                        setErrorBorder(newDeckName, isNameValid);
                        checkNewDeck();
                        return;
                    }
                }
                nameInUse = false;
                
                isNameValid = newDeckName.getText() != null
                        && !newDeckName.getText().trim().isEmpty();
                
                setErrorBorder(newDeckName, isNameValid);
                
                checkNewDeck();
            }
        });
        
        final JLabel cardLabel = new JLabel("Cards: *");
        springLayout.putConstraint(SpringLayout.NORTH, cardLabel, 13,
                SpringLayout.SOUTH, newDeckName);
        springLayout.putConstraint(SpringLayout.WEST, cardLabel, 0,
                SpringLayout.WEST, deckLabel);
        add(cardLabel);
        
        newDeckCards = new JTextPane();
        springLayout.putConstraint(SpringLayout.NORTH, newDeckCards, 6,
                SpringLayout.SOUTH, cardLabel);
        newDeckCards.setForeground(SystemColor.desktop);
        newDeckCards.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
                null));
        springLayout.putConstraint(SpringLayout.WEST, newDeckCards, 10,
                SpringLayout.WEST, this);
        springLayout.putConstraint(SpringLayout.EAST, newDeckCards, -10,
                SpringLayout.EAST, this);
        add(newDeckCards);
        
        newDeckCards.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
                
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
                
            }
            
            private void validate() {
                
                final String pattern = " *([0-9]{1,3} *, *)*[0-9]{1,3} *";
                areCardsValid = newDeckCards.getText() != null
                        && !newDeckCards.getText().isEmpty()
                        && Pattern.matches(pattern, newDeckCards.getText());
                
                setErrorBorder(newDeckCards, areCardsValid);
                
                checkNewDeck();
            }
        });
        
        createDeckButton = new JButton("Create Deck");
        springLayout.putConstraint(SpringLayout.WEST, createDeckButton, 0,
                SpringLayout.WEST, deckLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, createDeckButton, -10,
                SpringLayout.SOUTH, this);
        add(createDeckButton);
        
        createDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                final String newCards = newDeckCards.getText();
                final String[] newCardArray = newCards.split("[ ,]+");
                final ArrayList<Double> cards = new ArrayList<Double>();
                for (String newCard : newCardArray) {
                    if (!newCard.isEmpty() && !newCard.equals(null)) {
                        cards.add(Double.parseDouble(newCard));
                    }
                }
                final DeckModel newDeck = new DeckModel(newDeckName.getText().trim(), cards,
                        multipleSelect.isSelected());
                newDeck.sort();
                AddDeckController.getInstance().addDeck(newDeck);
                parentPanel.showPanel("reqlistpanel");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        parentPanel.setNewDeck();
                    }
                }.run();
            }
        });
        
        cancelCreationButton = new JButton("Cancel Creation");
        cancelCreationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                parentPanel.showPanel("reqlistpanel");
            }
        });
        
        springLayout.putConstraint(SpringLayout.SOUTH, cancelCreationButton, 0,
                SpringLayout.SOUTH, createDeckButton);
        springLayout.putConstraint(SpringLayout.EAST, cancelCreationButton,
                -10, SpringLayout.EAST, this);
        add(cancelCreationButton);
        
        final ButtonGroup selectionGroup = new ButtonGroup();
        
        final JRadioButton singleSelect = new JRadioButton("Single");
        singleSelect.setBackground(Color.WHITE);
        singleSelect.setSelected(true);
        springLayout.putConstraint(SpringLayout.WEST, singleSelect, 0,
                SpringLayout.WEST, deckLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, singleSelect, -19,
                SpringLayout.NORTH, createDeckButton);
        add(singleSelect);
        
        multipleSelect = new JRadioButton("Multiple");
        multipleSelect.setBackground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, multipleSelect, 0,
                SpringLayout.NORTH, singleSelect);
        springLayout.putConstraint(SpringLayout.WEST, multipleSelect, 4,
                SpringLayout.EAST, singleSelect);
        add(multipleSelect);
        
        selectionGroup.add(singleSelect);
        selectionGroup.add(multipleSelect);
        
        final JLabel selectionLabel = new JLabel("Selection Mode");
        springLayout.putConstraint(SpringLayout.WEST, selectionLabel, 0,
                SpringLayout.WEST, deckLabel);
        springLayout.putConstraint(SpringLayout.SOUTH, selectionLabel, -1,
                SpringLayout.NORTH, singleSelect);
        add(selectionLabel);
        
        cardHelpLabel = new JLabel(
                "Please enter a list of integers (up to 3 digits) separated by commas");
        springLayout.putConstraint(SpringLayout.NORTH, cardHelpLabel, 2,
                SpringLayout.SOUTH, newDeckCards);
        springLayout.putConstraint(SpringLayout.WEST, cardHelpLabel, 0,
                SpringLayout.WEST, deckLabel);
        add(cardHelpLabel);
        
        errorLabel = new JLabel("<errors>");
        errorLabel.setForeground(Color.RED);
        springLayout.putConstraint(SpringLayout.NORTH, errorLabel, 5,
                SpringLayout.NORTH, createDeckButton);
        springLayout.putConstraint(SpringLayout.WEST, errorLabel, 6,
                SpringLayout.EAST, createDeckButton);
        add(errorLabel);
        
        newDeckName.setText(makeNewDeckName());
        
        checkNewDeck();
        
        setErrorBorder(newDeckName, true);
        setErrorBorder(newDeckCards, false);
    }
    
    /**
     * 
     * Resets the fields in the form to their default values.
     *
     */
    public void resetFields() {
        newDeckName.setText(makeNewDeckName());
        newDeckCards.setText("");
    }
    
    private void checkNewDeck() {
        if (!isNameValid) {
            if (nameInUse) {
                errorLabel.setText("Name already in use");
            }
            else {
                errorLabel.setText("Name is required");
            }
        }
        else if (!areCardsValid) {
            errorLabel.setText("Invalid cards list");
        }
        else {
            errorLabel.setText("");
        }
        createDeckButton.setEnabled(isNameValid && areCardsValid);
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
    
    private static String makeNewDeckName() {
        // deck name prefix
        final String defaultName = "New Deck";
        // the number that will go after the prefix
        int deckNum = 0;
        
        // go through all the current decks and find the largest number
        // already in the names
        for (DeckModel deck : DeckListModel.getInstance().getDecks()) {
            String name = deck.getName();
            // if the name looks like the prefix with a number after it
            if (name != null && name.matches(defaultName + "( \\d+)?")) {
                int newNum;
                if (name.matches(defaultName + " \\d+")) {
                    // name has a number after it
                    newNum = Integer.parseInt(name.substring(defaultName
                            .length() + 1)) + 1;
                }
                else {
                    // name has no number after it, so the number was 0
                    newNum = 1;
                }
                // if the number in the name was bigger than the current number,
                // use this one
                if (newNum > deckNum) {
                    deckNum = newNum;
                }
            }
        }
        
        // only show the number if it wasn't 0
        return defaultName + (deckNum == 0 ? "" : " " + deckNum);
    }
    
    /**
     * Set parent panel
     * 
     * @param p
     */
    public void setEditGamePanel(NewGamePanel p) {
        parentPanel = p;
    }
    
    private NewGamePanel parentPanel;
    private final JButton createDeckButton;
    private final JTextField newDeckName;
    private final JButton cancelCreationButton;
    private boolean isNameValid = false;
    private boolean areCardsValid = false;
    private boolean nameInUse = false;
    private final JTextPane newDeckCards;
    private final JRadioButton multipleSelect;
    private final JLabel cardHelpLabel;
    private final JLabel errorLabel;
}
