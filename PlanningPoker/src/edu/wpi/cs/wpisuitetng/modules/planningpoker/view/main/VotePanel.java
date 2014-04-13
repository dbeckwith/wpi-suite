/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;


import java.awt.Dimension;
import java.awt.FlowLayout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


/**
 * 
 * @author nfbrown
 */
public class VotePanel extends javax.swing.JPanel {
    
    /**
     *
     */
    
    private static final long serialVersionUID = 6053116033835102214L;
    
    /**
     * the user's previous estimate
     */
    private Estimate old;
    
    /**
     * Creates new form VotePanel
     */
    public VotePanel() {
        initComponents();
        parentGame = null;
        req = null;
        selectMultiple = true;
        cards = new ArrayList<CardButton>();
        old = null;
    }
    
    public void setRequirement(User currentUser, GameModel parentGame,
            GameRequirementModel req) {
        this.currentUser = currentUser;
        this.parentGame = parentGame;
        this.req = req;
        
        reqDescriptionTextArea.setText(req.getDescription());
        setRequirementName(req.getName());
        setRequirementType(req.getType());
        // setRequirementProgress();
        
        old = null; //ensure it is erased
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
        deck.add("0.5");
        deck.add("1");
        deck.add("2");
        deck.add("3");
        deck.add("5");
        deck.add("10");
        
        ArrayList<Integer> selected = new ArrayList<Integer>();
        if(old != null){
            selected = old.getCardsSelected();
        }
        
        cards.clear();
        estimateCardsPanel.removeAll();
        for (final String estimate : deck) {
            final CardButton estimateCard = new CardButton(estimate);
            cards.add(estimateCard);
            
            estimateCard.setText(estimate);
            estimateCard.setPreferredSize(new Dimension(80, 120));
            estimateCard.setCardSelected(selected.contains(new Integer(deck.indexOf(estimate))));
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
                    VotePanel.this.repaint();
                }
            });
            
            estimateCardsPanel.add(estimateCard);
        }
        
        validate();
        repaint();
    }
    
    
    private void selectEstimateCard() {
        
        boolean alreadyVoted = false;
        new Thread() {
            @Override
            public void run() {
                UpdateGamesController.getInstance().updateGame(parentGame);
            }
        }.start();
        
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
        
        if (alreadyVoted) {
            req.UpdateEstimate(old, est);
        }
        else {
            req.addEstimate(est);
        }
        old = est;
    }
    
    protected void setRequirementProgress(int numCompleted, int total) {
        setCompletedVotesText(numCompleted + "/" + total);
        setVotesProgressBarValue((int) (100f * numCompleted / total));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        estimateLabel = new javax.swing.JLabel();
        voteField = new javax.swing.JLabel();
        completedVotesField = new javax.swing.JLabel();
        votesProgressBar = new javax.swing.JProgressBar();
        
        estimateLabel.setText("Estimate:");
        
        voteField.setText("Votes:");
        
        completedVotesField.setText("0/0");
        
        final JLabel lblRequirement = new JLabel("Requirement:");
        
        final JScrollPane scrollPane = new JScrollPane();
        
        final JScrollPane scrollPane_1 = new JScrollPane();
        
        requirementNameLabel = new JLabel("");
        
        final JLabel lblDescription = new JLabel("Description:");
        
        final JLabel lblType = new JLabel("Type:");
        
        requirementType = new JLabel("");
        
        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectEstimateCard();
            }
        });
        btnSubmit.setEnabled(false);
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addComponent(
                                                        scrollPane_1,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        430, Short.MAX_VALUE)
                                                .addComponent(scrollPane)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        lblRequirement)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.LEADING)
                                                                                .addGroup(
                                                                                        layout.createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                        ComponentPlacement.RELATED)
                                                                                                .addComponent(
                                                                                                        requirementNameLabel))
                                                                                .addGroup(
                                                                                        layout.createSequentialGroup()
                                                                                                .addGap(249)
                                                                                                .addComponent(
                                                                                                        lblType)
                                                                                                .addPreferredGap(
                                                                                                        ComponentPlacement.RELATED)
                                                                                                .addComponent(
                                                                                                        requirementType))))
                                                .addComponent(lblDescription)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        estimateLabel)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED,
                                                                        170,
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
                                                .addComponent(btnSubmit,
                                                        Alignment.TRAILING))
                                .addContainerGap()));
        layout.setVerticalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(lblRequirement)
                                                .addComponent(
                                                        requirementNameLabel)
                                                .addComponent(lblType)
                                                .addComponent(requirementType))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblDescription)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPane,
                                        GroupLayout.PREFERRED_SIZE, 83,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addComponent(estimateLabel)
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
                                .addComponent(scrollPane_1,
                                        GroupLayout.DEFAULT_SIZE, 111,
                                        Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnSubmit).addContainerGap()));
        
        estimateCardsPanel = new JPanel();
        scrollPane_1.setViewportView(estimateCardsPanel);
        estimateCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        reqDescriptionTextArea = new JTextArea();
        reqDescriptionTextArea.setEditable(false);
        reqDescriptionTextArea.setLineWrap(true);
        scrollPane.setViewportView(reqDescriptionTextArea);
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    protected void setRequirementName(String text) {
        requirementNameLabel.setText(text);
    }
    
    protected void setRequirementType(String text_1) {
        requirementType.setText(text_1);
    }
    
    protected String getCompletedVotesText() {
        return completedVotesField.getText();
    }
    
    protected void setCompletedVotesText(String text_2) {
        completedVotesField.setText(text_2);
    }
    
    protected int getVotesProgressBarValue() {
        return votesProgressBar.getValue();
    }
    
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
        boolean btnSubmitFlag = false;
        for (CardButton c : cards) {
            
            System.out.println(c.getEstimateValue() + " " + c.isCardSelected());
            if (c.isCardSelected()) {
                btnSubmitFlag = true;
                break;
            }
        }
        btnSubmit.setEnabled(btnSubmitFlag);
        return;
    }
    
    public void setAllowMultipleCards(boolean allow) {
        selectMultiple = allow;
    }
    
    private User currentUser;
    private GameModel parentGame;
    private GameRequirementModel req;
    
    private boolean selectMultiple;
    
    private JButton btnSubmit;
    
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
    
    
}
