/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Component;
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

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
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
    
    private User current_user;
    private GameModel parent_game;
    private GameRequirementModel req;
    
    /**
     * Creates new form VotePanel
     */
    public VotePanel() {
        initComponents();
        parent_game = null;
        req = null;
    }
    
    public void setRequirement(User current_user, GameModel parent_game,
            GameRequirementModel req) {
        this.current_user = current_user;
        this.parent_game = parent_game;
        this.req = req;
        
        reqDescriptionTextArea.setText(req.getDescription());
        setRequirementName(req.getName());
        setRequirementType(req.getType());
        // setRequirementProgress();
        
        boolean already_voted = false;
        
        for (Estimate e : req.getEstimates()) {
            if (e.getUser() != null && current_user != null
                    && e.getUser().equals(current_user)) {
                already_voted = true;
                break;
            }
        }
        
        
        ArrayList<String> deck = new ArrayList<>();
        deck.add("0.5");
        deck.add("1");
        deck.add("2");
        deck.add("3");
        deck.add("5");
        deck.add("10");
        
        estimateCardsPanel.removeAll();
        for (String estimate : deck) {
            JButton estimate_card = new JButton();
            
            estimate_card.setText(estimate);
            estimate_card.setPreferredSize(new Dimension(80, 120));
            estimate_card.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectEstimateCard((JButton) e.getSource());
                }
            });
            estimate_card.setEnabled(!already_voted);
            
            estimateCardsPanel.add(estimate_card);
        }
        validate();
        repaint();
    }
    
    private void selectEstimateCard(JButton selected_card_button) {
        // TODO Update this once current user controller made into a singleton
        CurrentUserController userController = new CurrentUserController();
        req.addEstimate(new Estimate(userController.getUser(), Float
                .parseFloat(selected_card_button.getText())));
        new Thread() {
            public void run() {
                UpdateGamesController.getInstance().updateGame(parent_game);
            }
        }.start();
        
        for (Component c : estimateCardsPanel.getComponents()) {
            ((JButton) c).setEnabled(false);
        }
    }
    
    protected void setRequirementProgress(int num_completed, int total) {
        setCompletedVotesText(num_completed + "/" + total);
        setVotesProgressBarValue((int) (100f * num_completed / total));
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
        
        JScrollPane scrollPane_1 = new JScrollPane();
        
        requirementNameLabel = new JLabel("");
        
        JLabel lblDescription = new JLabel("Description:");
        
        JLabel lblType = new JLabel("Type:");
        
        requirementType = new JLabel("");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                        .addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
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
                            .addPreferredGap(ComponentPlacement.RELATED, 324, Short.MAX_VALUE)
                            .addComponent(voteField)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(votesProgressBar, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(completedVotesField)))
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
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(estimateLabel)
                        .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(voteField)
                                .addComponent(completedVotesField))
                            .addComponent(votesProgressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addContainerGap())
        );
        
        estimateCardsPanel = new JPanel();
        scrollPane_1.setViewportView(estimateCardsPanel);
        estimateCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        reqDescriptionTextArea = new JTextArea();
        reqDescriptionTextArea.setEditable(false);
        reqDescriptionTextArea.setLineWrap(true);
        scrollPane.setViewportView(reqDescriptionTextArea);
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel completedVotesField;
    private javax.swing.JLabel estimateLabel;
    private javax.swing.JLabel voteField;
    private javax.swing.JProgressBar votesProgressBar;
    private JTextArea reqDescriptionTextArea;
    private JPanel estimateCardsPanel;
    private JLabel requirementNameLabel;
    private JLabel requirementType;
    
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
}
