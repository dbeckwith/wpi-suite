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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

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
        
        cards = new ArrayList<CardButton>();
        
        final JLabel deckLabel = new JLabel("Deck Name: *");
        
        newDeckName = new JTextField();
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
        
        createDeckButton = new JButton("Create Deck");
        
        createDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	String newCards = null; //###################################################################################### #FIXIT
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
            }
        });
        
        cancelCreationButton = new JButton("Cancel Creation");
        cancelCreationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                parentPanel.showPanel("reqlistpanel");
            }
        });
        
        final ButtonGroup selectionGroup = new ButtonGroup();
        
        final JRadioButton singleSelect = new JRadioButton("Single");
        singleSelect.setBackground(Color.WHITE);
        singleSelect.setSelected(true);
        
        multipleSelect = new JRadioButton("Multiple");
        multipleSelect.setBackground(Color.WHITE);
        
        selectionGroup.add(singleSelect);
        selectionGroup.add(multipleSelect);
        
        final JLabel selectionLabel = new JLabel("Selection Mode");
        
        errorLabel = new JLabel("<errors>");
        errorLabel.setForeground(Color.RED);

        newDeckName.setText(makeNewDeckName());
        
        checkNewDeck();
        
        setErrorBorder(newDeckName, true);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        JButton addCard = new JButton("Add Card");
        addCard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("adding card");
        		CardButton newCard = new CardButton();
        		newCard.setPreferredSize(new Dimension(80, 120));
        		cards.add(newCard);
        		newCard.setValue(cards.size());
        		cardPanel.add(newCard);
        		
        		cardPanel.repaint();
        		cardPanel.revalidate();
        	}
        });
        addCard.setIcon(ImageLoader.getIcon("newReq.png"));
        
        JButton deleteCard = new JButton("Delete Card");
        deleteCard.setIcon(ImageLoader.getIcon("Delete.png"));
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
        					.addContainerGap())
        				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(groupLayout.createSequentialGroup()
        							.addComponent(singleSelect)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(multipleSelect))
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        								.addComponent(deckLabel)
        								.addComponent(newDeckName, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
        								.addComponent(cardLabel)
        								.addGroup(groupLayout.createSequentialGroup()
        									.addComponent(createDeckButton)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(errorLabel)
        									.addPreferredGap(ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
        									.addComponent(cancelCreationButton)))
        							.addPreferredGap(ComponentPlacement.RELATED))
        						.addComponent(selectionLabel))
        					.addGap(8))
        				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
        					.addComponent(addCard)
        					.addPreferredGap(ComponentPlacement.RELATED, 243, Short.MAX_VALUE)
        					.addComponent(deleteCard)
        					.addContainerGap())))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(deckLabel)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(newDeckName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(cardLabel)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(addCard)
        				.addComponent(deleteCard))
        			.addGap(18)
        			.addComponent(selectionLabel)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(singleSelect)
        						.addComponent(multipleSelect))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(createDeckButton)
        						.addComponent(cancelCreationButton)))
        				.addComponent(errorLabel))
        			.addContainerGap())
        );
        
        cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        scrollPane.setViewportView(cardPanel);
        cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setLayout(groupLayout);
    }
    
    /**
     * 
     * Resets the fields in the form to their default values.
     *
     */
    public void resetFields() {
        newDeckName.setText(makeNewDeckName());
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
    
    private ArrayList<CardButton> cards;
    private JPanel cardPanel;
    private NewGamePanel parentPanel;
    private final JButton createDeckButton;
    private final JTextField newDeckName;
    private final JButton cancelCreationButton;
    private boolean isNameValid = false;
    private boolean areCardsValid = false;
    private boolean nameInUse = false;
    private final JRadioButton multipleSelect;
    private final JLabel errorLabel;
}
