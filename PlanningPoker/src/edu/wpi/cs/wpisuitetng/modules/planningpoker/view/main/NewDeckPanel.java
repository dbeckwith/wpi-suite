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
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
public class NewDeckPanel extends JPanel implements ActionListener {
    
    private static final long serialVersionUID = 4631372194324496204L;
    
    /**
     * 
     * Creates a new NewDeckPanel
     *
     */
    public NewDeckPanel() {
        setBackground(Color.WHITE);
        GetDecksController.getInstance().retrieveDecks();
        tutorial = false;
        
        cards = new ArrayList<SpinnerCard>();
        
        final JLabel deckLabel = new JLabel("Deck Name: *");
        deckLabel.setToolTipText("The name of the new deck.");
        
        newDeckName = new JTextField();
        newDeckName.setToolTipText("The name of the new deck.");
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
                boolean alreadyReturned = false;
                for (DeckModel deck : DeckListModel.getInstance().getDecks()) {
                    
                    if (deck.toString().equals(newDeckName.getText().trim())
                            || newDeckName.getText().equals("Default")
                            || newDeckName.getText().equals("No deck")
                            || newDeckName.getText().equals("Generated deck")) {
                        isNameValid = false;
                        nameInUse = true;
                        setErrorBorder(newDeckName, isNameValid);
                        checkNewDeck();
                        alreadyReturned = true;
                        break;
                        
                    }
                }
                if (!alreadyReturned){
                    nameInUse = false;
                    
                    isNameValid = newDeckName.getText() != null
                            && !newDeckName.getText().trim().isEmpty();
                    
                    setErrorBorder(newDeckName, isNameValid);
                    
                    checkNewDeck();
                }
       
                
            }
        });
        
        final JLabel cardLabel = new JLabel("Cards: *");
        cardLabel.setToolTipText("The list of cards the user will be able to select in order to make their estimate."); // $codepro.audit.disable lineLength

        createDeckButton = new JButton("Create Deck");
        createDeckButton.setToolTipText("Create and save this deck.");
        
        createDeckButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(newDeckCallback != null){
					ActionListener call = newDeckCallback;
					newDeckCallback = null;
					call.actionPerformed(new ActionEvent(this, 0, ""));
				}
			}
		});
        
        createDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	final ArrayList<Double> newCards = new ArrayList<Double>();
            	
            	for(SpinnerCard card:cards){
            		newCards.add((double)card.getEstimateValue());
            	}
            	
                final DeckModel newDeck = new DeckModel(newDeckName.getText().trim(), newCards,
                        multipleSelect.isSelected());
                newDeck.sort();
                DeckListModel.getInstance().addDeck(newDeck);
                parentPanel.setNewDeck();
                AddDeckController.getInstance().addDeck(newDeck);
                parentPanel.showPanel("reqlistpanel");

            }
        });
        
        cancelCreationButton = new JButton("Cancel Creation");
        cancelCreationButton.setToolTipText("Cancel making this deck and don't save it.");
        cancelCreationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                parentPanel.showPanel("reqlistpanel");
            }
        });
        
        final ButtonGroup selectionGroup = new ButtonGroup();
        
        final JRadioButton singleSelect = new JRadioButton("Single");
        singleSelect.setToolTipText("Single selection mode only allows the user to select one card from the deck."); // $codepro.audit.disable lineLength
        singleSelect.setBackground(Color.WHITE);
        singleSelect.setSelected(true);
        
        multipleSelect = new JRadioButton("Multiple");
        multipleSelect.setToolTipText("Multiple selection mode allows the user to select any number of cards from the deck and add their values to make their estimate."); // $codepro.audit.disable lineLength
        multipleSelect.setBackground(Color.WHITE);
        
        selectionGroup.add(singleSelect);
        selectionGroup.add(multipleSelect);
        
        final JLabel selectionLabel = new JLabel("Selection Mode");
        selectionLabel.setToolTipText("The selection mode for this deck.");
        
        errorLabel = new JLabel("<errors>");
        errorLabel.setForeground(Color.RED);

        newDeckName.setText(makeNewDeckName());
        
        checkNewDeck();
        
        setErrorBorder(newDeckName, true);
        
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        addCard = new JButton("Add Card");
        addCard.setToolTipText("Add a new card to the list of cards.");
        addCard.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		addCard();
 
        	}
        });
        addCard.setIcon(ImageLoader.getIcon("newReq.png"));
        
        final GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 601, Short
        							.MAX_VALUE)
        					.addContainerGap())
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(groupLayout.createSequentialGroup()
        							.addComponent(singleSelect)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(multipleSelect))
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        								.addComponent(deckLabel)
        								.addComponent(newDeckName, GroupLayout.DEFAULT_SIZE, 
        										605, Short.MAX_VALUE)
        								.addComponent(cardLabel)
        								.addGroup(groupLayout.createSequentialGroup()
        									.addComponent(createDeckButton)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(errorLabel)
        									.addPreferredGap(ComponentPlacement.RELATED, 263, Short
        											.MAX_VALUE)
        									.addComponent(cancelCreationButton)))
        							.addPreferredGap(ComponentPlacement.RELATED))
        						.addComponent(selectionLabel))
        					.addGap(8))
        				.addComponent(addCard, Alignment.LEADING)))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(deckLabel)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(newDeckName, GroupLayout.PREFERRED_SIZE, GroupLayout
        					.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(cardLabel)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(addCard)
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
        
        cardPanel = new JPanel(){
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void paintComponent(Graphics g) {
                final BufferedImage texture = ImageLoader.getImage("felt.png");
                for (int x = 0; x < getWidth(); x += texture.getWidth()) {
                    for (int y = 0; y < getHeight(); y += texture.getHeight()) {
                        g.drawImage(texture, x, y, null);
                    }
                }
            }
        };
        cardPanel.setBackground(Color.WHITE);
        scrollPane.setViewportView(cardPanel);
        final GridBagLayout gbl_cardPanel = new GridBagLayout();
        cardPanel.setLayout(gbl_cardPanel);
        setLayout(groupLayout);
        
        addCard();
    }
    
    /**
     * 
     * Resets the fields in the form to their default values.
     *
     */
    public void resetFields() {
        newDeckName.setText(makeNewDeckName());
    }
    
    /**
     * makes the current input valid if it is not already
     */
    public void validateInput(){
        if (!isNameValid) {
            newDeckName.setText(makeNewDeckName());
        }
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

        else {
            errorLabel.setText("");
        }
        createDeckButton.setEnabled(isNameValid && !tutorial);
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
    
    private void addCard(){
    	final SpinnerCard newCard = new SpinnerCard(cards.size(), DeckModel.NO_LIMIT);
		newCard.setDeleteListener(NewDeckPanel.this);
		newCard.setPreferredSize(new Dimension(80, 120));
		
		if(cards.size() == 1){
			cards.get(0).setDeleteListener(this);
		} else if(cards.size() == 0){
			newCard.setDeleteListener(null);
		}
		
		cards.add(newCard);
				
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 10, 0, 10);
		cardPanel.add(newCard, gbc);
		
		scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar()
				.getMaximum());
		
		cardPanel.repaint();
		cardPanel.revalidate();
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		final Card deleteCard = (Card)e.getSource();
		cardPanel.remove(deleteCard);
		cards.remove(deleteCard);
		
		if(cards.size() == 1){
			cards.get(0).setDeleteListener(null);
		}
		
		cardPanel.repaint();
		cardPanel.revalidate();
		
		
	}
	
	public void setSaveDeckCallback(ActionListener a){
		newDeckCallback = a;
	}
	
	/**
     * @return the createDeckButton
     */
    public JButton getCreateDeckButton() {
        return createDeckButton;
    }

    /**
     * @return the cancelCreationButton
     */
    public JButton getCancelDeckButton() {
        return cancelCreationButton;
    }
    
    /**
     * @param tutorial the tutorial to set
     */
    public void setTutorial(boolean tutorial) {
        this.tutorial = tutorial;
    }



    private ActionListener newDeckCallback;
	
	private final JScrollPane scrollPane;
    private final ArrayList<SpinnerCard> cards;
    private final JPanel cardPanel;
    private NewGamePanel parentPanel;
    private final JButton createDeckButton;
    private final JTextField newDeckName;
    private final JButton cancelCreationButton;
    private boolean isNameValid = false;
    private boolean nameInUse = false;
    private final JRadioButton multipleSelect;
    private final JLabel errorLabel;
    private JButton addCard;
    private boolean tutorial;   //a flag to show if the tutorial is running
	
}
