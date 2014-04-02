/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * 
 * @author Lukas
 */
public class NewGamePanel extends JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6206697919180272913L;
    
    /**
     * Creates new form EditGame
     */
    public NewGamePanel() {
        
        initComponents();
        
        gameDescription.setEditGamePanel(this);
        gameRequirements.setEditGamePanel(this);
        
        // dummy requirements for test
        gameRequirements.addRequirement(new GameRequirementModel(0,
                "Test Requirement 1", "The cow", "User story"));
        gameRequirements.addRequirement(new GameRequirementModel(1,
                "Test Requirement 2", "elepahnt", "User story"));
        gameRequirements.addRequirement(new GameRequirementModel(2,
                "Test Requirement 5", "queso", "User story"));
        
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        gameRequirements = new edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGameRequirementsPanel();
        gameDescription = new edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGameDescriptionPanel();
        
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        
        saveButton.setIcon(ImageLoader.getIcon("Save.png"));
        cancelButton.setIcon(ImageLoader.getIcon("Delete.png"));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(saveButton)
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(cancelButton))
        				.addComponent(gameDescription, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addComponent(gameRequirements, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(gameRequirements, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        					.addGap(21))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(gameDescription, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(saveButton)
        						.addComponent(cancelButton))))
        			.addContainerGap())
        );
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveButtonActionPerformed
        PlanningPoker.getViewController().saveNewGame(this);
    }// GEN-LAST:event_saveButtonActionPerformed
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
        PlanningPoker.getViewController().cancelNewGame(this);
    }// GEN-LAST:event_cancelButtonActionPerformed
    
    @Override
    public String getName() {
        return gameDescription.nameField.getText();
    }
    
    public String getDescription() {
        return gameDescription.descriptionField.getText();
    }
    
    public GameType getGameType() {
        if (gameDescription.distributed.isSelected()) {
            return GameType.DISTRIBUTED;
        } else {
            return GameType.LIVE;
        }
    }
    
    public Date getEndDate() {
        return gameDescription.getDate();
    }
    
    public ArrayList<GameRequirementModel> getRequirements() {
        return gameRequirements.getRequirementsFromTable();
    }
    
    public void check() {
        saveButton
                .setEnabled((gameDescription.validateForm() && gameRequirements
                        .validateForm()));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGameDescriptionPanel gameDescription;
    private edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGameRequirementsPanel gameRequirements;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
