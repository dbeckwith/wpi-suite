/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Sam Carlberg
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UserUpdateController;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;

/**
 * A panel for changing user preferences. Right now it only includes
 * notification methods, but other functionality may be added in the future.
 * 
 * @author Sam Carlberg <slcarlberg@wpi.edu>
 */
public class UserPreferencesPanel extends javax.swing.JPanel {
    
    // stops Eclipse from complaining
    private static final long serialVersionUID = 3565380843068717833L;
    
    private static UserPreferencesPanel instance;
    
    /**
     * Gets the single instance of the panel.
     */
    public static UserPreferencesPanel getPanel() {
        if (instance == null) {
            instance = new UserPreferencesPanel();
        }
        return instance;
    }
    
    /**
     * Creates new form UserPreferencesPanel
     */
    private UserPreferencesPanel() {
        setBackground(Color.WHITE);
        initComponents();
        if (CurrentUserController.getInstance().getUser() != null) {
            emailBox.setSelected(CurrentUserController.getInstance().getUser().isNotifyByEmail());
            imBox.setSelected(CurrentUserController.getInstance().getUser().isNotifyByIM());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        notificationsPanel = new javax.swing.JPanel();
        notificationsPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Notification Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        notificationsPanel.setBackground(Color.WHITE);
        emailBox = new javax.swing.JCheckBox();
        emailBox.setBackground(Color.WHITE);
        imBox = new javax.swing.JCheckBox();
        imBox.setBackground(Color.WHITE);
        
        emailBox.setText("Receive E-mail");
        emailBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailBoxActionPerformed(evt);
            }
        });
        
        imBox.setText("Receive IM");
        imBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imBoxActionPerformed(evt);
            }
        });
        
        final javax.swing.GroupLayout notificationsPanelLayout = new javax.swing.GroupLayout(
                notificationsPanel);
        notificationsPanel.setLayout(notificationsPanelLayout);
        notificationsPanelLayout
                .setHorizontalGroup(notificationsPanelLayout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                notificationsPanelLayout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                notificationsPanelLayout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(imBox)
                                                        .addComponent(emailBox))
                                        .addContainerGap(
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)));
        notificationsPanelLayout
                .setVerticalGroup(notificationsPanelLayout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                notificationsPanelLayout
                                        .createSequentialGroup()
                                        .addContainerGap(
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(imBox)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(emailBox)));
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(notificationsPanel, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(399, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(notificationsPanel, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(368, Short.MAX_VALUE))
        );
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private void emailBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_emailBoxActionPerformed
        UserUpdateController.getInstance().setNotifyByEmail(emailBox.isSelected());
    }// GEN-LAST:event_emailBoxActionPerformed
    
    private void imBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_imBoxActionPerformed
        UserUpdateController.getInstance().setNotifyByIM(imBox.isSelected());
    }// GEN-LAST:event_imBoxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox emailBox;
    private javax.swing.JCheckBox imBox;
    private javax.swing.JPanel notificationsPanel;
    // End of variables declaration//GEN-END:variables
}
