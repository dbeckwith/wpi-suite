package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.JRadioButton;

public class NewDeckPanel extends JPanel {
	public NewDeckPanel() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel deckLabel = new JLabel("Deck Name:");
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
				isNameValid = newDeckName.getText() != null
						&& !newDeckName.getText().isEmpty();
				deckNameErrorLabel.setVisible(!isNameValid);
				checkNewDeck();
			}
		});

		JLabel cardLabel = new JLabel("Cards:");
		springLayout.putConstraint(SpringLayout.NORTH, cardLabel, 6,
				SpringLayout.SOUTH, newDeckName);
		springLayout.putConstraint(SpringLayout.WEST, cardLabel, 0,
				SpringLayout.WEST, deckLabel);
		add(cardLabel);

		newDeckCards = new JTextArea();
		newDeckCards.setLineWrap(true);
		springLayout.putConstraint(SpringLayout.NORTH, newDeckCards, 2,
				SpringLayout.SOUTH, cardLabel);
		springLayout.putConstraint(SpringLayout.WEST, newDeckCards, 10,
				SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, newDeckCards, -137,
				SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, newDeckCards, -10,
				SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.EAST, newDeckName, 0,
				SpringLayout.EAST, newDeckCards);
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
				String pattern = "(\\.?[0-9][0-9.]*,? ?)*\\.?[0-9][0-9.]*";
				areCardsValid = newDeckCards.getText() != null
						&& !newDeckCards.getText().isEmpty()
						&& Pattern.matches(pattern, newDeckCards.getText());
				cardsErrorLabel.setVisible(!areCardsValid);
				checkNewDeck();
			}
		});

		createDeckButton = new JButton("Create Deck");
		springLayout.putConstraint(SpringLayout.WEST, createDeckButton, 0,
				SpringLayout.WEST, deckLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, createDeckButton, -10,
				SpringLayout.SOUTH, this);
		add(createDeckButton);

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

		deckNameErrorLabel = new JLabel("*Required Field");
		springLayout.putConstraint(SpringLayout.NORTH, deckNameErrorLabel, 0,
				SpringLayout.NORTH, deckLabel);
		springLayout.putConstraint(SpringLayout.WEST, deckNameErrorLabel, 6,
				SpringLayout.EAST, deckLabel);
		deckNameErrorLabel.setForeground(Color.RED);
		add(deckNameErrorLabel);

		cardsErrorLabel = new JLabel("*Invalid value!");
		cardsErrorLabel.setForeground(Color.RED);
		springLayout.putConstraint(SpringLayout.NORTH, cardsErrorLabel, 6,
				SpringLayout.SOUTH, newDeckName);
		springLayout.putConstraint(SpringLayout.WEST, cardsErrorLabel, 6,
				SpringLayout.EAST, cardLabel);
		add(cardsErrorLabel);
		
		ButtonGroup selectionGroup = new ButtonGroup();
		
		JRadioButton singleSelect = new JRadioButton("Single");
		singleSelect.setSelected(true);
		springLayout.putConstraint(SpringLayout.WEST, singleSelect, 0, SpringLayout.WEST, deckLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, singleSelect, -19, SpringLayout.NORTH, createDeckButton);
		add(singleSelect);
		
		multipleSelect = new JRadioButton("Multiple");
		springLayout.putConstraint(SpringLayout.NORTH, multipleSelect, 0, SpringLayout.NORTH, singleSelect);
		springLayout.putConstraint(SpringLayout.WEST, multipleSelect, 4, SpringLayout.EAST, singleSelect);
		add(multipleSelect);
		
		selectionGroup.add(singleSelect);
		selectionGroup.add(multipleSelect);
		
		JLabel selectionLabel = new JLabel("Selection Mode");
		springLayout.putConstraint(SpringLayout.WEST, selectionLabel, 0, SpringLayout.WEST, deckLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, selectionLabel, -1, SpringLayout.NORTH, singleSelect);
		add(selectionLabel);
		
		cardHelpLabel = new JLabel("Please enter a list of numbers separated by commas");
		springLayout.putConstraint(SpringLayout.NORTH, cardHelpLabel, 6, SpringLayout.SOUTH, newDeckCards);
		springLayout.putConstraint(SpringLayout.WEST, cardHelpLabel, 0, SpringLayout.WEST, deckLabel);
		add(cardHelpLabel);
		checkNewDeck();
	}

	private void checkNewDeck() {
		createDeckButton.setEnabled(isNameValid && areCardsValid);
	}

	/**
	 * Ensures everything filled out properly
	 * 
	 * @return
	 */
	private boolean validateForm() {
		return isNameValid && areCardsValid;
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
	private JLabel deckNameErrorLabel;
	private JLabel cardsErrorLabel;
	private JTextArea newDeckCards;
	private JRadioButton multipleSelect;
	private JLabel cardHelpLabel;
}
