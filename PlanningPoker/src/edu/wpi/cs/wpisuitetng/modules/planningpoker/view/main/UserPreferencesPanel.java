/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UserUpdateController;

import java.awt.Color;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A panel for changing user preferences. Right now it only includes
 * notification methods, but other functionality may be added in the future.
 * 
 * @author Team 9
 * @version 1.0
 */
public class UserPreferencesPanel extends javax.swing.JPanel {
    
    // stops Eclipse from complaining
    private static final long serialVersionUID = 3565380843068717833L;
    
    private static UserPreferencesPanel instance;
    
    /**
     * Gets the single instance of the panel.
     * 
     * @return the instance of the UserPreferencesPanel
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
            boolean b = emailBox.isSelected();
            lblEmail.setVisible(b);
            emailField.setVisible(b);
            saveButton.setVisible(b);
            emailField.setText(CurrentUserController.getInstance().getUser().getEmail());
            saveButton.setEnabled(false);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        
        notificationsPanel = new javax.swing.JPanel();
        notificationsPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)),
                "Notification Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
        
        lblEmail = new JLabel("Email: ");
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
        
        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                validate();
            }
            
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                validate();
            }
            
            @Override
            public void removeUpdate(DocumentEvent arg0) {
                validate();
            }
            
            public void validate() {
                String email = emailField.getText();
                errorLabel.setVisible(false);
                if (email.equals(CurrentUserController.getInstance().getUser().getEmail())) {
                    saveButton.setEnabled(false);
                }
                else {
                    saveButton.setEnabled(false);
                    Pattern emailPattern;
                    Matcher emailMatcher;
                    final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    emailPattern = Pattern.compile(EMAIL_PATTERN);
                    emailMatcher = emailPattern.matcher(email);
                    
                    if (emailMatcher.matches()) {
                        saveButton.setEnabled(true);
                    }
                    else {
                        errorLabel.setVisible(true);
                    }
                }
            }
            
        });
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                updateEmail();
            }
            
        });
        
        errorLabel = new JLabel("Invalid Email!");
        errorLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        
        final javax.swing.GroupLayout notificationsPanelLayout = new javax.swing.GroupLayout(
                notificationsPanel);
        notificationsPanelLayout
                .setHorizontalGroup(notificationsPanelLayout
                        .createParallelGroup(Alignment.LEADING)
                        .addGroup(
                                notificationsPanelLayout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                notificationsPanelLayout
                                                        .createParallelGroup(Alignment.LEADING)
                                                        .addGroup(
                                                                notificationsPanelLayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(imBox)
                                                                        .addGap(199)
                                                                        .addComponent(errorLabel))
                                                        .addGroup(
                                                                notificationsPanelLayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(emailBox)
                                                                        .addPreferredGap(
                                                                                ComponentPlacement.UNRELATED)
                                                                        .addComponent(lblEmail)
                                                                        .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                emailField,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                149,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                        .addComponent(saveButton)))
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        notificationsPanelLayout.setVerticalGroup(notificationsPanelLayout.createParallelGroup(
                Alignment.LEADING).addGroup(
                notificationsPanelLayout
                        .createSequentialGroup()
                        .addContainerGap(17, Short.MAX_VALUE)
                        .addGroup(
                                notificationsPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(imBox).addComponent(errorLabel))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(
                                notificationsPanelLayout
                                        .createParallelGroup(Alignment.BASELINE)
                                        .addComponent(emailBox)
                                        .addComponent(lblEmail)
                                        .addComponent(emailField, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveButton))));
        notificationsPanel.setLayout(notificationsPanelLayout);
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap()
                        .addComponent(notificationsPanel, 133, 410, GroupLayout.PREFERRED_SIZE) //manually change min to 133
                        .addContainerGap(85, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(notificationsPanel, GroupLayout.PREFERRED_SIZE, 86,
                                GroupLayout.PREFERRED_SIZE).addContainerGap(203, Short.MAX_VALUE)));
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private void emailBoxActionPerformed(java.awt.event.ActionEvent evt) {
        UserUpdateController.getInstance().setNotifyByEmail(emailBox.isSelected());
        boolean b = emailBox.isSelected();
        lblEmail.setVisible(b);
        emailField.setVisible(b);
        saveButton.setVisible(b);
        errorLabel.setVisible(false);
        if (b) {
            emailField.setText(CurrentUserController.getInstance().getUser().getEmail());
        }
    }
    
    private void imBoxActionPerformed(java.awt.event.ActionEvent evt) {
        UserUpdateController.getInstance().setNotifyByIM(imBox.isSelected());
    }
    
    private void updateEmail() {
        String email = emailField.getText();
        UserUpdateController.getInstance().updateEmail(email);
        saveButton.setEnabled(false);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox emailBox;
    private javax.swing.JCheckBox imBox;
    private javax.swing.JPanel notificationsPanel;
    private JTextField emailField;
    private JLabel lblEmail;
    private JButton saveButton;
    private JLabel errorLabel;
}
