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
        ArrayList<Integer> selectedCards = null;
        final ArrayList<Estimate> estimates = req.getEstimates();
        for (Estimate e : estimates) {
            if (e.getUsername().equals(currentUser.getUsername())) {
                selectedCards = e.getCardsSelected();
                voted = true;
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
        
        // Add card values from the game's deck
        for (Double cardVal : parentGame.getDeck().getCards()) {
            deck.add(cardVal.toString());
        }
        
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
        estimateCardsPanel.removeAll();
        for (final String estimate : deck) {
            final CardButton estimateCard = new CardButton(estimate);
            cards.add(estimateCard);
            
            estimateCard.setText(estimate);
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
        
        if (voted) {
            for (Integer i : selectedCards) {
                cards.get(i).setCardSelected(true);
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
     * Sets the progress for voting on this requirement
     * 
     * @param numCompleted
     *        the number of the completed requirements
     * @param total
     *        the total number of the completed requirements
     */
    protected void setRequirementProgress(int numCompleted, int total) {
        setCompletedVotesText(numCompleted + "/" + total);
        setVotesProgressBarValue((int) (100f * numCompleted / total));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        
        estimateLabel = new javax.swing.JLabel();
        voteField = new javax.swing.JLabel();
        completedVotesField = new javax.swing.JLabel();
        votesProgressBar = new javax.swing.JProgressBar();
        
        estimateLabel.setText("Please select your Estimate:");
        
        voteField.setText("Votes:");
        
        completedVotesField.setText("0/0");
        
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
        
        prevVoteLabel = new JLabel("<previous vote>");
        
        final JLabel lblSelectedTotal = new JLabel("Selected Total:");
        
        lblTotal = new JLabel("<total>");
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.TRAILING)
                .addGroup(
                        Alignment.LEADING,
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addComponent(
                                                        scrollPane,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        430, Short.MAX_VALUE)
                                                .addComponent(
                                                        estimateScrollPane,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        430, Short.MAX_VALUE)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        lblRequirement)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        requirementNameLabel)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED,
                                                                        70,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(
                                                                        voteField)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        votesProgressBar,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        160,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        completedVotesField))
                                                .addComponent(estimateLabel)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        btnSubmit)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        lblSelectedTotal)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        lblTotal)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED,
                                                                        101,
                                                                        Short.MAX_VALUE)
                                                                .addComponent(
                                                                        lblYouVoted)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        prevVoteLabel))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        lblType)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        requirementType)))
                                .addContainerGap()));
        layout.setVerticalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.BASELINE)
                                                                                .addComponent(
                                                                                        lblRequirement)
                                                                                .addComponent(
                                                                                        requirementNameLabel))
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.BASELINE)
                                                                                .addComponent(
                                                                                        lblType)
                                                                                .addComponent(
                                                                                        requirementType)))
                                                .addGroup(
                                                        layout.createParallelGroup(
                                                                Alignment.TRAILING)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.BASELINE)
                                                                                .addComponent(
                                                                                        voteField)
                                                                                .addComponent(
                                                                                        completedVotesField))
                                                                .addComponent(
                                                                        votesProgressBar,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPane,
                                        GroupLayout.PREFERRED_SIZE, 50,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(estimateLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(estimateScrollPane,
                                        GroupLayout.DEFAULT_SIZE, 133,
                                        Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(btnSubmit)
                                                .addComponent(lblYouVoted)
                                                .addComponent(prevVoteLabel)
                                                .addComponent(lblSelectedTotal)
                                                .addComponent(lblTotal))
                                .addContainerGap()));
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
    
    /**
     * get the completed votes text
     * 
     * @return the completed votes text
     */
    protected String getCompletedVotesText() {
        return completedVotesField.getText();
    }
    
    /**
     * Sets the completed votes text
     * 
     * @param text
     *        the completed votes text
     */
    protected void setCompletedVotesText(String text) {
        completedVotesField.setText(text);
    }
    
    /**
     * get the value of vote progress bar
     * 
     * @return the value of vote progress bar
     */
    protected int getVotesProgressBarValue() {
        return votesProgressBar.getValue();
    }
    
    /**
     * Sets the progress bar value
     * 
     * @param value
     *        the progress bar value
     */
    protected void setVotesProgressBarValue(int value) {
        votesProgressBar.setValue(value);
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
        for (CardButton c : cards) {
            
            System.out.println(c.getEstimateValue() + " " + c.isCardSelected());
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
    private void updateTotal() {
        float total = 0;
        for (CardButton card : cards) {
            if (card.isCardSelected()) {
                total += card.getEstimateValue();
            }
        }
        lblTotal.setText(CardButton.cardFormat.format(total));
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel completedVotesField;
    private javax.swing.JLabel estimateLabel;
    private javax.swing.JLabel voteField;
    private javax.swing.JProgressBar votesProgressBar;
    private JTextArea reqDescriptionTextArea;
    private JPanel estimateCardsPanel;
    private JLabel requirementNameLabel;
    private JLabel requirementType;
    
    private JLabel prevVoteLabel;
    private JLabel lblYouVoted;
}
