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
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import javax.swing.border.EtchedBorder;

public class NewDeckPanel extends JPanel {
	/**
     * 
     */
    private static final long serialVersionUID = 4631372194324496204L;
    
    public NewDeckPanel() {
        setBackground(Color.WHITE);
		GetDecksController.getInstance().retrieveDecks();
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel deckLabel = new JLabel("Deck Name: *");
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

			public void validate() {
				for (DeckModel deck : DeckListModel.getInstance().getDecks()) {
					if (deck.toString().equals(newDeckName.getText()) || newDeckName.getText().equals("Default")) {
						isNameValid = false;
						nameInUse = true;
						checkNewDeck();
						return;
					}
				}
				nameInUse = false;
				
				isNameValid = newDeckName.getText() != null
						&& !newDeckName.getText().isEmpty();
				checkNewDeck();
			}
		});

		JLabel cardLabel = new JLabel("Cards: *");
		springLayout.putConstraint(SpringLayout.NORTH, cardLabel, 6,
				SpringLayout.SOUTH, newDeckName);
		springLayout.putConstraint(SpringLayout.WEST, cardLabel, 0,
				SpringLayout.WEST, deckLabel);
		add(cardLabel);

		newDeckCards = new JTextPane();
		newDeckCards.setForeground(SystemColor.desktop);
		newDeckCards.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		springLayout.putConstraint(SpringLayout.NORTH, newDeckCards, 2,
				SpringLayout.SOUTH, cardLabel);
		springLayout.putConstraint(SpringLayout.WEST, newDeckCards, 10,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, newDeckCards, -137,
				SpringLayout.SOUTH, this);
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

			public void validate() {
				String pattern = "( *\\.?[0-9][0-9.]*,? ?)*\\.?[0-9][0-9.]*";
				areCardsValid = newDeckCards.getText() != null
						&& !newDeckCards.getText().isEmpty()
						&& Pattern.matches(pattern, newDeckCards.getText());
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
				String newCards = newDeckCards.getText();
				String newCardArray[] = newCards.split("[ ,]+");
				ArrayList<Double> cards = new ArrayList<Double>();
				for (String newCard : newCardArray) {
					if (!newCard.isEmpty() && !newCard.equals(null)) {
						cards.add(Double.parseDouble(newCard));
					}
				}
				DeckModel newDeck = new DeckModel(newDeckName.getText(), cards,
						multipleSelect.isSelected());
				newDeck.sort();
				AddDeckController.getInstance().addDeck(newDeck);
				parent.showPanel("reqlistpanel");
				newDeckName.setText("");
				newDeckCards.setText("");
			}
		});

		cancelCreationButton = new JButton("Cancel Creation");
		cancelCreationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				parent.showPanel("reqlistpanel");
			}
		});

		springLayout.putConstraint(SpringLayout.SOUTH, cancelCreationButton, 0,
				SpringLayout.SOUTH, createDeckButton);
		springLayout.putConstraint(SpringLayout.EAST, cancelCreationButton,
				-10, SpringLayout.EAST, this);
		add(cancelCreationButton);

		ButtonGroup selectionGroup = new ButtonGroup();

		JRadioButton singleSelect = new JRadioButton("Single");
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

		JLabel selectionLabel = new JLabel("Selection Mode");
		springLayout.putConstraint(SpringLayout.WEST, selectionLabel, 0,
				SpringLayout.WEST, deckLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, selectionLabel, -1,
				SpringLayout.NORTH, singleSelect);
		add(selectionLabel);

		cardHelpLabel = new JLabel(
				"Please enter a list of numbers separated by commas");
		springLayout.putConstraint(SpringLayout.NORTH, cardHelpLabel, 2,
				SpringLayout.SOUTH, newDeckCards);
		springLayout.putConstraint(SpringLayout.WEST, cardHelpLabel, 0,
				SpringLayout.WEST, deckLabel);
		add(cardHelpLabel);
		
		errorLabel = new JLabel("<errors>");
		errorLabel.setForeground(Color.RED);
		springLayout.putConstraint(SpringLayout.NORTH, errorLabel, 5, SpringLayout.NORTH, createDeckButton);
		springLayout.putConstraint(SpringLayout.WEST, errorLabel, 6, SpringLayout.EAST, createDeckButton);
		add(errorLabel);
		checkNewDeck();
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
	 * Set parent panel
	 * 
	 * @param p
	 */
	public void setEditGamePanel(NewGamePanel p) {
		this.parent = p;
	}

	private NewGamePanel parent;
	private JButton createDeckButton;
	private JTextField newDeckName;
	private JButton cancelCreationButton;
	private boolean isNameValid = false;
	private boolean areCardsValid = false;
	private boolean nameInUse = false;
	private JTextPane newDeckCards;
	private JRadioButton multipleSelect;
	private JLabel cardHelpLabel;
	private JLabel errorLabel;
}
