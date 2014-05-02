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
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import edu.wpi.cs.wpisuitetng.modules.core.models.Carrier;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
            emailBox.setSelected(CurrentUserController.getInstance().getUser()
                    .isNotifyByEmail());
            smsBox.setSelected(CurrentUserController.getInstance().getUser()
                    .isNotifyBySMS());
            boolean b = emailBox.isSelected();
            
            lblEmail.setVisible(b);
            emailField.setVisible(b);
            saveEmailButton.setVisible(b);
            emailField.setText(CurrentUserController.getInstance().getUser()
                    .getEmail());
            saveEmailButton.setEnabled(false);
            
            b = smsBox.isSelected();
            lblPhoneNumber.setVisible(b);
            phoneNumberField.setVisible(b);
            phoneNumberField.setText(CurrentUserController.getInstance()
                    .getUser().getPhoneNumber());
            btnSaveSms.setVisible(b);
            btnSaveSms.setEnabled(false);
            lblCarrier.setVisible(b);
            carrierBox.setVisible(b);
            carrierBox.setSelectedItem(CurrentUserController.getInstance()
                    .getUser().getCarrier());
            lblMsgAndData.setVisible(b);
            lblInvalidPhone.setVisible(false);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        
        notificationsPanel = new javax.swing.JPanel();
        notificationsPanel.setBorder(new TitledBorder(new LineBorder(new Color(
                184, 207, 229)), "Notification Options", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        notificationsPanel.setBackground(Color.WHITE);
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(notificationsPanel,
                                        GroupLayout.PREFERRED_SIZE, 804,
                                        GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(38, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(notificationsPanel,
                                        GroupLayout.PREFERRED_SIZE, 172,
                                        GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(84, Short.MAX_VALUE)));
        final GridBagLayout gbl_notificationsPanel = new GridBagLayout();
        gbl_notificationsPanel.columnWidths = new int[] { 16, 162, 73, 336, 83,
                89, 0 };
        gbl_notificationsPanel.rowHeights = new int[] { 40, 42, 10, 47, 0 };
        gbl_notificationsPanel.columnWeights = new double[] { 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_notificationsPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        notificationsPanel.setLayout(gbl_notificationsPanel);
        
        emailField = new JTextField();
        emailField
                .setToolTipText("The email at which you will recieve notifications.");
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
            
            private void validate() {
                final String email = emailField.getText();
                errorEmailLabel.setVisible(false);
                if (email.equals(CurrentUserController.getInstance().getUser()
                        .getEmail())) {
                    saveEmailButton.setEnabled(false);
                }
                else {
                    saveEmailButton.setEnabled(false);
                    
                    if (!extractPhoneNumber(email).isEmpty()) {
                        saveEmailButton.setEnabled(true);
                    }
                    else {
                        errorEmailLabel.setVisible(true);
                    }
                }
            }
            
        });
        emailBox = new javax.swing.JCheckBox();
        emailBox.setFont(new Font("Tahoma", Font.BOLD, 11));
        emailBox.setBackground(Color.WHITE);
        
        emailBox.setText("Receive E-mail");
        emailBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailBoxActionPerformed(evt);
            }
        });
        btnSaveSms = new JButton("Save");
        btnSaveSms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateSMS();
            }
        });
        smsBox = new javax.swing.JCheckBox();
        smsBox.setFont(new Font("Tahoma", Font.BOLD, 11));
        smsBox.setBackground(Color.WHITE);
        
        smsBox.setText("Receive SMS");
        smsBox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsBoxActionPerformed(evt);
            }
        });
        final GridBagConstraints gbc_smsBox = new GridBagConstraints();
        gbc_smsBox.anchor = GridBagConstraints.WEST;
        gbc_smsBox.insets = new Insets(0, 0, 5, 5);
        gbc_smsBox.gridx = 1;
        gbc_smsBox.gridy = 0;
        notificationsPanel.add(smsBox, gbc_smsBox);
        
        phoneNumberField = new JTextField();
        phoneNumberField.setColumns(10);
        phoneNumberField.getDocument().addDocumentListener(
                new DocumentListener() {
                    
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
                    
                    private void validate() {
                        final String phoneNumber = phoneNumberField.getText();
                        final Carrier carrier = (Carrier) carrierBox
                                .getSelectedItem();
                        lblInvalidPhone.setVisible(false);
                        if (phoneNumber.equals(CurrentUserController
                                .getInstance().getUser().getPhoneNumber())
                                && (carrier.equals(CurrentUserController
                                        .getInstance().getUser().getCarrier()))) {
                            btnSaveSms.setEnabled(false);
                        }
                        else {
                            btnSaveSms.setEnabled(false);
                            phoneNumberIsGood = !(extractPhoneNumber(phoneNumber)
                                    .isEmpty());
                            if (phoneNumberIsGood) {
                                if ((carrierBox.getSelectedItem()
                                        .equals(Carrier.UNKNOWN))) {
                                    lblInvalidPhone
                                            .setText("Select a carrier!");
                                    lblInvalidPhone.setVisible(true);
                                }
                                else {
                                    btnSaveSms.setEnabled(true);
                                }
                            }
                            else {
                                lblInvalidPhone.setText("Invalid Phone #");
                                lblInvalidPhone.setVisible(true);
                            }
                        }
                    }
                });
        
        lblPhoneNumber = new JLabel("Phone # :");
        
        
        lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
        final GridBagConstraints gbc_lblPhoneNumber = new GridBagConstraints();
        gbc_lblPhoneNumber.anchor = GridBagConstraints.EAST;
        gbc_lblPhoneNumber.insets = new Insets(0, 0, 5, 5);
        gbc_lblPhoneNumber.gridx = 2;
        gbc_lblPhoneNumber.gridy = 0;
        notificationsPanel.add(lblPhoneNumber, gbc_lblPhoneNumber);
        final GridBagConstraints gbc_phoneNumberField = new GridBagConstraints();
        gbc_phoneNumberField.fill = GridBagConstraints.HORIZONTAL;
        gbc_phoneNumberField.insets = new Insets(0, 0, 5, 5);
        gbc_phoneNumberField.gridx = 3;
        gbc_phoneNumberField.gridy = 0;
        notificationsPanel.add(phoneNumberField, gbc_phoneNumberField);
        final GridBagConstraints gbc_btnSaveSms = new GridBagConstraints();
        gbc_btnSaveSms.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnSaveSms.insets = new Insets(0, 0, 5, 5);
        gbc_btnSaveSms.gridx = 4;
        gbc_btnSaveSms.gridy = 0;
        notificationsPanel.add(btnSaveSms, gbc_btnSaveSms);
        
        lblInvalidPhone = new JLabel("Invalid Phone #");
        lblInvalidPhone.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblInvalidPhone.setForeground(Color.RED);
        lblInvalidPhone.setVisible(false);
        final GridBagConstraints gbc_lblInvalidPhone = new GridBagConstraints();
        gbc_lblInvalidPhone.fill = GridBagConstraints.VERTICAL;
        gbc_lblInvalidPhone.anchor = GridBagConstraints.WEST;
        gbc_lblInvalidPhone.insets = new Insets(0, 0, 5, 0);
        gbc_lblInvalidPhone.gridx = 5;
        gbc_lblInvalidPhone.gridy = 0;
        notificationsPanel.add(lblInvalidPhone, gbc_lblInvalidPhone);
        
        lblMsgAndData = new JLabel("Msg & data rates may apply");
        lblMsgAndData.setFont(new Font("Tahoma", Font.ITALIC, 11));
        final GridBagConstraints gbc_lblMsgAndData = new GridBagConstraints();
        gbc_lblMsgAndData.fill = GridBagConstraints.VERTICAL;
        gbc_lblMsgAndData.anchor = GridBagConstraints.EAST;
        gbc_lblMsgAndData.insets = new Insets(0, 0, 5, 5);
        gbc_lblMsgAndData.gridx = 1;
        gbc_lblMsgAndData.gridy = 1;
        notificationsPanel.add(lblMsgAndData, gbc_lblMsgAndData);
        
        
        lblCarrier = new JLabel("Carrier :");
        lblCarrier.setFont(new Font("Tahoma", Font.BOLD, 11));
        final GridBagConstraints gbc_lblCarrier = new GridBagConstraints();
        gbc_lblCarrier.anchor = GridBagConstraints.EAST;
        gbc_lblCarrier.insets = new Insets(0, 0, 5, 5);
        gbc_lblCarrier.gridx = 2;
        gbc_lblCarrier.gridy = 1;
        notificationsPanel.add(lblCarrier, gbc_lblCarrier);
        
        carrierBox = new JComboBox<Carrier>();
        final Vector<Carrier> v = new Vector<Carrier>(Arrays.asList(Carrier
                .values()));
        v.remove(Carrier.UNKNOWN);
        v.add(0, Carrier.UNKNOWN);
        carrierBox.setModel(new DefaultComboBoxModel<Carrier>(v));
        carrierBox.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                carrierBoxActionPerformed(e);
                
            }
            
        });
        final GridBagConstraints gbc_carrierBox = new GridBagConstraints();
        gbc_carrierBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_carrierBox.insets = new Insets(0, 0, 5, 5);
        gbc_carrierBox.gridx = 3;
        gbc_carrierBox.gridy = 1;
        notificationsPanel.add(carrierBox, gbc_carrierBox);
        final GridBagConstraints gbc_emailBox = new GridBagConstraints();
        gbc_emailBox.anchor = GridBagConstraints.WEST;
        gbc_emailBox.insets = new Insets(0, 0, 0, 5);
        gbc_emailBox.gridx = 1;
        gbc_emailBox.gridy = 3;
        notificationsPanel.add(emailBox, gbc_emailBox);
        
        lblEmail = new JLabel("Email : ");
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
        final GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.anchor = GridBagConstraints.EAST;
        gbc_lblEmail.insets = new Insets(0, 0, 0, 5);
        gbc_lblEmail.gridx = 2;
        gbc_lblEmail.gridy = 3;
        notificationsPanel.add(lblEmail, gbc_lblEmail);
        final GridBagConstraints gbc_emailField = new GridBagConstraints();
        gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
        gbc_emailField.insets = new Insets(0, 0, 0, 5);
        gbc_emailField.gridx = 3;
        gbc_emailField.gridy = 3;
        notificationsPanel.add(emailField, gbc_emailField);
        
        errorEmailLabel = new JLabel("Invalid Email!");
        errorEmailLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        errorEmailLabel.setForeground(Color.RED);
        errorEmailLabel.setVisible(false);
        
        saveEmailButton = new JButton("Save");
        saveEmailButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                updateEmail();
            }
            
        });
        final GridBagConstraints gbc_saveEmailButton = new GridBagConstraints();
        gbc_saveEmailButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_saveEmailButton.insets = new Insets(0, 0, 0, 5);
        gbc_saveEmailButton.gridx = 4;
        gbc_saveEmailButton.gridy = 3;
        notificationsPanel.add(saveEmailButton, gbc_saveEmailButton);
        final GridBagConstraints gbc_errorEmailLabel = new GridBagConstraints();
        gbc_errorEmailLabel.anchor = GridBagConstraints.WEST;
        gbc_errorEmailLabel.gridx = 5;
        gbc_errorEmailLabel.gridy = 3;
        notificationsPanel.add(errorEmailLabel, gbc_errorEmailLabel);
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private void emailBoxActionPerformed(java.awt.event.ActionEvent evt) {
        UserUpdateController.getInstance().setNotifyByEmail(
                emailBox.isSelected());
        final boolean b = emailBox.isSelected();
        
        lblEmail.setVisible(b);
        emailField.setVisible(b);
        saveEmailButton.setVisible(b);
        errorEmailLabel.setVisible(false);
        if (b) {
            emailField.setText(CurrentUserController.getInstance().getUser()
                    .getEmail());
        }
    }
    
    private void smsBoxActionPerformed(java.awt.event.ActionEvent evt) {
        UserUpdateController.getInstance().setNotifyBySMS(smsBox.isSelected());
        final boolean b = smsBox.isSelected();
        lblPhoneNumber.setVisible(b);
        phoneNumberField.setVisible(b);
        btnSaveSms.setVisible(b);
        lblCarrier.setVisible(b);
        lblInvalidPhone.setVisible(false);
        carrierBox.setVisible(b);
        lblMsgAndData.setVisible(b);
        if (b) {
            phoneNumberField.setText(CurrentUserController.getInstance()
                    .getUser().getPhoneNumber());
            carrierBox.setSelectedItem(CurrentUserController.getInstance()
                    .getUser().getCarrier());
        }
        
    }
    
    private void carrierBoxActionPerformed(java.awt.event.ActionEvent e) {
        final String phoneNumberText = phoneNumberField.getText();
        if (!phoneNumberText.isEmpty()
                && !phoneNumberText.equals(CurrentUserController.getInstance()
                        .getUser().getPhoneNumber())) {
            if (!phoneNumberIsGood) {
                lblInvalidPhone.setText("Invalid Phone #");
                lblInvalidPhone.setVisible(true);
                btnSaveSms.setEnabled(false);
            }
            else {
                if (!carrierBox.getSelectedItem().equals(Carrier.UNKNOWN)) {
                    lblInvalidPhone.setVisible(false);
                    btnSaveSms.setEnabled(true);
                }
                else {
                    lblInvalidPhone.setText("Select a carrier!");
                    btnSaveSms.setEnabled(false);
                    lblInvalidPhone.setVisible(true);
                }
            }
        }
        else {
            lblInvalidPhone.setVisible(false);
        }
    }
    
    private void updateEmail() {
        final String email = emailField.getText();
        UserUpdateController.getInstance().updateEmail(email);
        saveEmailButton.setEnabled(false);
    }
    
    private void updateSMS() {
        String extracted = extractPhoneNumber(phoneNumberField.getText());
        UserUpdateController.getInstance().updatePhoneNumber(extracted);
        final Carrier selectedCarrier = (Carrier) carrierBox.getSelectedItem();
        UserUpdateController.getInstance().updatePhoneCarrier(selectedCarrier);
        btnSaveSms.setEnabled(false);
    }
    
    private String extractPhoneNumber(String inputText) {
        final Pattern phonePattern;
        final Matcher phoneMatcher;
        final String PHONE_PATTERN = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})";
        
        phonePattern = Pattern.compile(PHONE_PATTERN);
        phoneMatcher = phonePattern.matcher(inputText);
        String returnString;
        
        if (phoneMatcher.matches()) {
            returnString = phoneMatcher.group(2) + phoneMatcher.group(3)
                    + phoneMatcher.group(4);
        }
        else {
            returnString = "";
        }
        
        return returnString;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox emailBox;
    private javax.swing.JCheckBox smsBox;
    private javax.swing.JPanel notificationsPanel;
    private JTextField emailField;
    private JLabel lblEmail;
    private JButton saveEmailButton;
    private JLabel errorEmailLabel;
    private JTextField phoneNumberField;
    private JLabel lblInvalidPhone;
    private JLabel lblCarrier;
    private JLabel lblPhoneNumber;
    private JButton btnSaveSms;
    private JComboBox<Carrier> carrierBox;
    private JLabel lblMsgAndData;
    private boolean phoneNumberIsGood;
}
