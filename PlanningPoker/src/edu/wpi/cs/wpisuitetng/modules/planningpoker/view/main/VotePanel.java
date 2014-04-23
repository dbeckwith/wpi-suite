/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;
import java.awt.Font;

/**
 * the panel to show vote GUI
 * 
 * @author Team 9
 * @version 1.0
 */
public class VotePanel extends javax.swing.JPanel {
    
    private static final DecimalFormat NUM_FORMAT = new DecimalFormat("#.#");
    
    private static final long serialVersionUID = 6053116033835102214L;
    
    /**
     * the user's previous estimate
     */
    private Estimate old;
    
    /**
     * Creates new form VotePanel
     */
    public VotePanel() {
        setBackground(Color.WHITE);
        initComponents();
        parentGame = null;
        req = null;
        selectMultiple = true;
        cards = new ArrayList<CardButton>();
        old = null;
    }
    
    /**
     * Sets the requirement for voting
     * 
     * @param currentUser
     *        the current user
     * @param parentGame
     *        the parent game
     * @param req
     *        the requirement
     */
    public void setRequirement(User currentUser, GameModel parentGame,
            GameRequirementModel req) {
        this.currentUser = currentUser;
        this.parentGame = parentGame;
        this.req = req;
        
        boolean voted = false;
        old = null;
        ArrayList<Integer> selectedCards = null;
        final ArrayList<Estimate> estimates = req.getEstimates();
        for (Estimate e : estimates) {
            if (e.getUsername().equals(currentUser.getUsername())) {
                selectedCards = e.getCardsSelected();
                voted = true;
                old = e;
            }
        }
        
        reqDescriptionTextArea.setText(req.getDescription());
        setRequirementName(req.getName());
        setRequirementType(req.getType());
        

        setAllowMultipleCards(parentGame.getDeck().canAllowsMultipleSelection());
        System.out.println("multiple selection : "
                + parentGame.getDeck().canAllowsMultipleSelection());
        
        
        old = null; // ensure it is erased
        for (Estimate e : req.getEstimates()) {
            System.out.println(e.getEstimate() + " from " + e.getUsername());
            if (e.getUsername() != null && currentUser != null
                    && e.getIdNum() == currentUser.getIdNum()
                    && e.getName().equals(currentUser.getName())
                    && e.getUsername().equals(currentUser.getUsername())) {
                old = e;
                break;
            }
        }
        
        final ArrayList<String> deck = new ArrayList<>();
        
        
        
        ArrayList<Integer> selected = new ArrayList<Integer>();
        if (old != null) {
            selected = old.getCardsSelected();
            lblYouVoted.setVisible(true);
            prevVoteLabel.setText(NUM_FORMAT.format(old.getEstimate()));
        }
        else {
            lblYouVoted.setVisible(false);
            prevVoteLabel.setText("");
        }
        
        cards.clear();
        if(parentGame.getDeck().isNone()){
        	estimateCardsPanel.removeAll();
        	CardButton estimateInput = new CardButton();
        	estimateInput.setLimit(parentGame.getDeck().getMaxEstimate());
            estimateInput.addActionListener(new ActionListener() {					
				@Override
				public void actionPerformed(ActionEvent e) {
					updateTotal();	
					validateCards();
				}
			});
            cards.add(estimateInput);
            updateTotal();
        	validateCards();
        	estimateInput.setPreferredSize(new Dimension(120, 160));
        	estimateCardsPanel.add(estimateInput);
        	
        	if(voted && old != null){
        		cards.get(0).setValue(old.getEstimate());
        	}
        } else {
        	            
            // Add card values from the game's deck
            for (Double cardVal : parentGame.getDeck().getCards()) {
                deck.add(cardVal.toString());
            }
            
        	
	        estimateCardsPanel.removeAll();
	        for (final String estimate : deck) {
	            final CardButton estimateCard = new CardButton(estimate);
	            
	            cards.add(estimateCard);
	            
	            estimateCard.setPreferredSize(new Dimension(80, 120));
	            estimateCard.setCardSelected(selected.contains(new Integer(deck
	                    .indexOf(estimate))));
	            estimateCard.addActionListener(new ActionListener() {
	                
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    if (estimateCard.isCardEnabled()) {
	                        estimateCard.setCardSelected(!estimateCard
	                                .isCardSelected());
	                        if (!selectMultiple) {
	                            deselectOtherCards(estimateCard);
	                        }
	                        validateCards();
	                    }
	                    updateTotal();
	                    VotePanel.this.repaint();
	                }
	            });
	            
	            estimateCardsPanel.add(estimateCard, BorderLayout.CENTER);
	        }
	        
	        if(voted){
	        		for(Integer i:selectedCards){
	        		cards.get(i).setCardSelected(true);
	        	}        	
	        	
        	}
	        
        }
        
        updateTotal();
        
        validate();
        repaint();
    }
    
    private void selectEstimateCard() {
        
        boolean alreadyVoted = false;
        final ArrayList<Estimate> estimates = req.getEstimates();
        for (Estimate e : estimates) {
            if (e.getUsername().equals(currentUser.getUsername())) {
                alreadyVoted = true;
            }
        }
        
        final ArrayList<Integer> selected = new ArrayList<Integer>();
        float estimate = 0;
        for (CardButton c : cards) {
            if (c.isCardSelected()) {
                estimate += c.getEstimateValue();
                selected.add(new Integer(cards.indexOf(c)));
            }
        }
        
        System.out.println("Estimate = " + estimate);
        final Estimate est = new Estimate(currentUser, estimate, selected);
        
        lblYouVoted.setVisible(true);
        prevVoteLabel.setText(NUM_FORMAT.format(est.getEstimate()));
        
        if (alreadyVoted) {
            req.UpdateEstimate(old, est);
        }
        else {
            req.addEstimate(est);
        }
        old = est;
        
        UpdateGamesController.getInstance().updateGame(parentGame);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        
        estimateLabel = new javax.swing.JLabel();
        
        estimateLabel.setText("Please select your Estimate:");
        
        final JLabel lblRequirement = new JLabel("Requirement:");
        
        final JScrollPane scrollPane = new JScrollPane();
        final JScrollPane estimateScrollPane = new JScrollPane();
        
        //JScrollPane scrollPane_1 = new JScrollPane();
        
        requirementNameLabel = new JLabel("<requirement>");
        
        final JLabel lblType = new JLabel("Type:");
        
        requirementType = new JLabel("<type>");
        
        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectEstimateCard();
            }
        });
        btnSubmit.setEnabled(false);
        
        estimateCardsPanel = new JPanel() {
            /**
             * 
             */
            private static final long serialVersionUID = 2543832513784794212L;

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
        estimateCardsPanel.setBackground(Color.WHITE);
        
        estimateCardsPanel.setMaximumSize(new Dimension(0, 0));
        
        lblYouVoted = new JLabel("You voted: ");
        lblYouVoted.setFont(new Font("Dialog", Font.BOLD, 18));
        
        prevVoteLabel = new JLabel("<previous vote>");
        prevVoteLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        
        final JLabel lblSelectedTotal = new JLabel("Selected Total:");
        
        lblTotal = new JLabel("<total>");
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane)
                        .addComponent(estimateScrollPane, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblRequirement)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(requirementNameLabel))
                        .addComponent(estimateLabel)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnSubmit)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblSelectedTotal)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblTotal)
                            .addPreferredGap(ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                            .addComponent(lblYouVoted)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(prevVoteLabel))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblType)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(requirementType)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblRequirement)
                        .addComponent(requirementNameLabel))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblType)
                        .addComponent(requirementType))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(estimateLabel)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(estimateScrollPane, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnSubmit)
                        .addComponent(lblYouVoted)
                        .addComponent(prevVoteLabel)
                        .addComponent(lblSelectedTotal)
                        .addComponent(lblTotal))
                    .addContainerGap())
        );
        final FlowLayout fl_estimateCardsPanel = new FlowLayout(FlowLayout.CENTER, 5,
                5);
        estimateCardsPanel.setLayout(fl_estimateCardsPanel);
        
        estimateScrollPane.setViewportView(estimateCardsPanel);
        
        reqDescriptionTextArea = new JTextArea();
        reqDescriptionTextArea.setEditable(false);
        reqDescriptionTextArea.setLineWrap(true);
        scrollPane.setViewportView(reqDescriptionTextArea);
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Sets the requirement name
     * 
     * @param text
     *        the requirement name
     */
    protected void setRequirementName(String text) {
        requirementNameLabel.setText(text);
    }
    
    /**
     * Sets the requirement type
     * 
     * @param text
     *        the requirement type
     */
    protected void setRequirementType(String text) {
        requirementType.setText(text);
    }
    
    
    private void deselectOtherCards(CardButton card) {
        for (CardButton c : cards) {
            if (c != card) {
                c.setCardSelected(false);
            }
        }
        System.out.println("the card " + card.isCardSelected());
    }
    
    private void validateCards() {
    	
    	if(parentGame.getDeck().isNone()){
    		btnSubmit.setEnabled(true);
    		return;
    	}
    	
        for (CardButton c : cards) {
            if (c.isCardSelected()) {
                btnSubmit.setEnabled(true);
                return;
            }
        }
        btnSubmit.setEnabled(false);
    }
    
    /**
     * Updates the selected total displayed next to the submit button
     */
    private void updateTotal(){
    	float total = 0;
    	for(CardButton card:cards){
    		if(card.isCardSelected() || parentGame.getDeck().isNone()){
    			total += card.getEstimateValue();
    		}
    	}
    	lblTotal.setText(CardButton.cardFormat.format(total));
    	
    	if(btnSubmit != null && old != null){
    		btnSubmit.setEnabled(total != old.getEstimate());
    	}
    	

    }
    
    /**
     * set the game to allow for choosing multiple cards
     * 
     * @param allow
     *        the boolean value to indicate that multiple cards selection is
     *        allowed
     */
    public void setAllowMultipleCards(boolean allow) {
        selectMultiple = allow;
    }
    
    private User currentUser;
    private GameModel parentGame;
    private GameRequirementModel req;
    
    private boolean selectMultiple;
    
    private JButton btnSubmit;
    private JLabel lblTotal;
    private final ArrayList<CardButton> cards;
    private javax.swing.JLabel estimateLabel;
    private JTextArea reqDescriptionTextArea;
    private JPanel estimateCardsPanel;
    private JLabel requirementNameLabel;
    private JLabel requirementType;
    
    private JLabel prevVoteLabel;
    private JLabel lblYouVoted;
}
