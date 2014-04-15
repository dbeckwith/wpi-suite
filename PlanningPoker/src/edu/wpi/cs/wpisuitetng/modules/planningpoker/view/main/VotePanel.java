/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
import java.awt.BorderLayout;

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
     * @param currentUser
     * @param parentGame
     * @param req
     */
    public void setRequirement(User currentUser, GameModel parentGame,
            GameRequirementModel req) {
        this.currentUser = currentUser;
        this.parentGame = parentGame;
        this.req = req;
        
        reqDescriptionTextArea.setText(req.getDescription());
        setRequirementName(req.getName());
        setRequirementType(req.getType());
        // setRequirementProgress();
        
        setAllowMultipleCards(parentGame.getDeck().getAllowsMultipleSelection());
        System.out.println("multiple selection : "+parentGame.getDeck().getAllowsMultipleSelection());
        
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
        
        ArrayList<String> deck = new ArrayList<>();
        
        // Add card values from the game's deck
        for (Double cardVal : parentGame.getDeck().getCards()) {
            deck.add(cardVal.toString());
        }
        
        ArrayList<Integer> selected = new ArrayList<Integer>();
        if (old != null) {
            selected = old.getCardsSelected();
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
                    VotePanel.this.repaint();
                }
            });
            
            estimateCardsPanel.add(estimateCard, BorderLayout.CENTER);
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
        
        ArrayList<Estimate> estimates = req.getEstimates();
        for (Estimate e : estimates) {
            if (e.getUsername().equals(currentUser.getUsername())) {
                alreadyVoted = true;
            }
        }
        
        ArrayList<Integer> selected = new ArrayList<Integer>();
        float estimate = 0;
        for (CardButton c : cards) {
            if (c.isCardSelected()) {
                estimate += c.getEstimateValue();
                selected.add(new Integer(cards.indexOf(c)));
            }
        }
        
        System.out.println("Estimate = " + estimate);
        Estimate est = new Estimate(currentUser, estimate, selected);
        
        if (alreadyVoted) {
            req.UpdateEstimate(old, est);
        }
        else {
            req.addEstimate(est);
        }
        old = est;
    }
    
    /**
     * Sets the progress for voting on this requirement
     * @param numCompleted
     * @param total
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
        
        JLabel lblRequirement = new JLabel("Requirement:");
        
        JScrollPane scrollPane = new JScrollPane();
        
        //JScrollPane scrollPane_1 = new JScrollPane();
        
        requirementNameLabel = new JLabel("<requirement>");
        
        JLabel lblDescription = new JLabel("Description:");
        
        JLabel lblType = new JLabel("Type:");
        
        requirementType = new JLabel("<type>");
        
        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectEstimateCard();
            }
        });
        btnSubmit.setEnabled(false);
        
        estimateCardsPanel = new JPanel(){
        	@Override
        	public void paint(Graphics g){
        		BufferedImage texture = ImageLoader.getImage("felt.png");
        		for(int x = 0; x < getWidth(); x += texture.getWidth()){
        			for(int y = 0; y < getHeight(); y+= texture.getHeight()){
        				g.drawImage(texture, x, y, null);
        			}
        		}
        		for(CardButton card:cards){
        			card.repaint();
        		}
        		
        	}        	
        };
        
        estimateCardsPanel.setMaximumSize(new Dimension(0, 0));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(estimateCardsPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        				.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        					.addComponent(lblRequirement)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(requirementNameLabel))
        						.addGroup(layout.createSequentialGroup()
        							.addGap(249)
        							.addComponent(lblType)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(requirementType))))
        				.addComponent(lblDescription, Alignment.LEADING)
        				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        					.addComponent(estimateLabel)
        					.addPreferredGap(ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
        					.addComponent(voteField)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(votesProgressBar, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(completedVotesField))
        				.addComponent(btnSubmit))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblRequirement)
        				.addComponent(requirementNameLabel)
        				.addComponent(lblType)
        				.addComponent(requirementType))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lblDescription)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(estimateLabel)
        				.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(voteField)
        						.addComponent(completedVotesField))
        					.addComponent(votesProgressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(estimateCardsPanel, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnSubmit)
        			.addContainerGap())
        );
        FlowLayout fl_estimateCardsPanel = new FlowLayout(FlowLayout.CENTER, 5, 5);
        estimateCardsPanel.setLayout(fl_estimateCardsPanel);
        
        reqDescriptionTextArea = new JTextArea();
        reqDescriptionTextArea.setEditable(false);
        reqDescriptionTextArea.setLineWrap(true);
        scrollPane.setViewportView(reqDescriptionTextArea);
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Sets the requirement name
     * @param text
     */
    protected void setRequirementName(String text) {
        requirementNameLabel.setText(text);
    }
    
    /**
     * Sets the requirement type
     * @param text
     */
    protected void setRequirementType(String text) {
        requirementType.setText(text);
    }
    
    protected String getCompletedVotesText() {
        return completedVotesField.getText();
    }
    
    /**
     * Sets the completed votes text
     * @param text
     */
    protected void setCompletedVotesText(String text) {
        completedVotesField.setText(text);
    }
    
    protected int getVotesProgressBarValue() {
        return votesProgressBar.getValue();
    }
    
    /**
     * Sets the progress bar value
     * @param value
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
    
    public void setAllowMultipleCards(boolean allow) {
        selectMultiple = allow;
    }
    
    private User currentUser;
    private GameModel parentGame;
    private GameRequirementModel req;
    
    private boolean selectMultiple;
    
    private JButton btnSubmit;
    
    private ArrayList<CardButton> cards;
    
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
