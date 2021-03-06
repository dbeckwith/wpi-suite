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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;


/**
 * A panel to editing deck settings
 * 
 * @author Team 9
 * @version 1.0
 * 
 */
public class DeckOptionsPanel extends JPanel implements SimpleListObserver, ActionListener,
        ChangeListener {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3228337947577586565L;
    
    private NewGameDescriptionPanel parentPanel;
    
    private JCheckBox useDeck;
    private JComboBox<DeckModel> savedDecks;
    private JButton newDeckButton;
    private JSpinner maxSpinner;
    
    private ActionListener newDeckCallback;
    
    /**
     * Create the panel.
     */
    public DeckOptionsPanel() {
        initComponents();
        
        DeckListModel.getInstance().addObserver(this);
    }
    
    /**
     * initilize components in this panel
     */
    public void initComponents() {
        setBackground(Color.WHITE);
        setBorder(new TitledBorder(null, "Deck Options", TitledBorder.LEADING, TitledBorder.TOP,
                null, null));
        
        useDeck = new JCheckBox("Deck:");
        useDeck.setToolTipText("Uncheck this box to not have a deck for this game and instead allow users to input estimates manually."); // $codepro.audit.disable lineLength
        useDeck.setSelected(true);
        useDeck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkUseDeck();
            }
        });
        
        useDeck.addActionListener(this);
        
        
        useDeck.setBackground(Color.WHITE);
        
        savedDecks = new JComboBox<DeckModel>();
        savedDecks
                .setToolTipText("The currently selected deck for estimating requirements of this game."); // $codepro.audit.disable lineLength
        
        savedDecks.addActionListener(this);
        
        newDeckButton = new JButton("New Deck...");
        
        newDeckButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newDeckCallback != null) {
                    ActionListener call = newDeckCallback;
                    newDeckCallback = null;
                    call.actionPerformed(new ActionEvent(DeckOptionsPanel.this, 0, "newDeckButton"));
                }
                
            }
        });
        newDeckButton.setToolTipText("Create a new deck for estimating this game.");
        
        final JLabel lblMaximumEstimate = new JLabel("Maximum Estimate:");
        lblMaximumEstimate
                .setToolTipText("The maximum allowed estimate for this game if it has no deck. Enter a value of 0 to indicate no limit."); // $codepro.audit.disable lineLength
        
        final JLabel lblForNo = new JLabel("(0 for no limit)");
        lblForNo.setToolTipText("The maximum allowed estimate for this game if it has no deck. Enter a value of 0 to indicate no limit."); // $codepro.audit.disable lineLength
        lblForNo.setFont(new Font("Tahoma", Font.ITALIC, 11));
        
        maxSpinner = new JSpinner();
        maxSpinner
                .setToolTipText("The maximum allowed estimate for this game if it has no deck. Enter a value of 0 to indicate no limit."); // $codepro.audit.disable lineLength
        maxSpinner.addChangeListener(this);
        maxSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null,
                new Integer(1)));
        final JFormattedTextField txt = ((JSpinner.NumberEditor) maxSpinner.getEditor())
                .getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
        
        final GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(
                        groupLayout
                                .createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        groupLayout
                                                .createParallelGroup(Alignment.LEADING)
                                                .addGroup(
                                                        groupLayout
                                                                .createSequentialGroup()
                                                                .addComponent(lblMaximumEstimate)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(lblForNo)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(maxSpinner,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        143, Short.MAX_VALUE))
                                                .addGroup(
                                                        groupLayout
                                                                .createSequentialGroup()
                                                                .addComponent(useDeck)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(savedDecks, 0, 160,
                                                                        Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(newDeckButton)))
                                .addGap(14)));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
                groupLayout
                        .createSequentialGroup()
                        .addContainerGap()
                        .addGroup(
                                groupLayout
                                        .createParallelGroup(Alignment.BASELINE)
                                        .addComponent(useDeck)
                                        .addComponent(savedDecks, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newDeckButton))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(
                                groupLayout
                                        .createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblMaximumEstimate)
                                        .addComponent(lblForNo)
                                        .addComponent(maxSpinner, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(74, Short.MAX_VALUE)));
        setLayout(groupLayout);
        
        //enable the correct controls
        checkUseDeck();
        
    }
    
    public void setParent(NewGameDescriptionPanel p) {
        parentPanel = p;
    }
    
    /**
     * Set the game for the deck options
     * 
     * @param game
     */
    public void setGame(GameModel game) {
        listUpdated();
        
        if (game.getDeck() != null) {
            useDeck.setSelected(!game.getDeck().isNone());
            
            if (game.getDeck().isNone()) {
                maxSpinner.getModel().setValue(game.getDeck().getMaxEstimate());
            }
            else {
                for (int i = 0; i < savedDecks.getModel().getSize(); i++) {
                    DeckModel deck = savedDecks.getModel().getElementAt(i);
                    if (deck != null && deck.getName().equals(game.getDeck().getName())) {
                        savedDecks.setSelectedItem(deck);
                        break;
                    }
                }
            }
            checkUseDeck();
            repaint();
        }
    }
    
    
    /**
     * @return the deck selected for this panel
     */
    public DeckModel getDeck() {
        DeckModel deck;
        
        if (useDeck.isSelected()) {
            DeckModel selectedDeck = (DeckModel) savedDecks.getSelectedItem();
            if (selectedDeck == null) {
                selectedDeck = DeckModel.DEFAULT_DECK;
            }
            deck = new DeckModel(selectedDeck.getName(), selectedDeck.getCards(),
                    selectedDeck.canAllowsMultipleSelection());
        }
        else {
            deck = new DeckModel((Integer) maxSpinner.getModel().getValue());
        }
        return deck;
    }
    
    /**
     * Populates deck combo box with new decks
     */
    @Override
    public void listUpdated() {
        final ArrayList<DeckModel> decks = DeckListModel.getInstance().getDecks();
        final DefaultComboBoxModel<DeckModel> newModel = new DefaultComboBoxModel<DeckModel>();
        newModel.addElement(DeckModel.DEFAULT_DECK);
        for (DeckModel deck : decks) {
            newModel.addElement(deck);
        }
        
        
        DeckModel selected = (DeckModel) savedDecks.getSelectedItem();
        if (selected == null) {
            selected = DeckModel.DEFAULT_DECK;
        }
        
        savedDecks.setModel(newModel);
        
        for (int i = 0; i < newModel.getSize(); i++) {
            DeckModel deck = newModel.getElementAt(i);
            if (deck != null && deck.getName() != null && deck.getName().equals(selected.getName())) {
                savedDecks.setSelectedItem(deck);
                break;
            }
        }
        
        repaint();
        
    }
    
    /**
     * add NewDeckButtonListener
     * 
     * @param a
     *        the NewDeckButtonListener to be added
     */
    public void addNewDeckButtonListener(ActionListener a) {
        if (a != null) {
            newDeckButton.addActionListener(a);
        }
    }
    
    public void setNewDeckButtonCallback(ActionListener a) {
        newDeckCallback = a;
    }
    
    public JButton getNewDeckButton() {
        return newDeckButton;
    }
    
    private void checkUseDeck() {
        boolean deckSelected = useDeck.isSelected();
        
        maxSpinner.setEnabled(!deckSelected);
        savedDecks.setEnabled(deckSelected);
        newDeckButton.setEnabled(deckSelected);
        
    }
    
    /**
     * Set the selected deck in the savedDecks combo box to the newest deck
     */
    public void setNewDeck() { // $codepro.audit.disable accessorMethodNamingConvention
        savedDecks.setSelectedIndex(savedDecks.getItemCount() - 1);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (parentPanel != null) {
            parentPanel.checkParent();
        }
        
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
        if (parentPanel != null) {
            parentPanel.checkParent();
        }
    }
}
