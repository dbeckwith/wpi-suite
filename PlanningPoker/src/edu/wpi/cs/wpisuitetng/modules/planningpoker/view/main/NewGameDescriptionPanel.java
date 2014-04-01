/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

/**
 * 
 * @author Lukas
 */
public class NewGameDescriptionPanel extends javax.swing.JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2620634004622077214L;
    
    /**
     * Creates new form GameDescription
     */
    public NewGameDescriptionPanel() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        gameType = new javax.swing.ButtonGroup();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionField = new javax.swing.JTextPane();
        selectDeadline = new javax.swing.JCheckBox();
        comboMonth = new javax.swing.JComboBox();
        comboDay = new javax.swing.JComboBox();
        comboYear = new javax.swing.JComboBox();
        notifyEmail = new javax.swing.JCheckBox();
        notifySMS = new javax.swing.JCheckBox();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        
        nameLabel.setText("Name:");
        
        descriptionLabel.setText("Description:");
        
        jScrollPane1.setViewportView(descriptionField);
        
        selectDeadline.setText("Deadline");
        
        comboMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
                "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December" }));
        
        comboDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
                "1", "12" }));
        
        comboYear.setEditable(true);
        comboYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
                "2014", "2015", "2016", "2017", "2018", "2019", "2020" }));
        
        notifyEmail.setText("Notify by Email");
        
        notifySMS.setText("Notify by SMS");
        
        gameType.add(jRadioButton1);
        jRadioButton1.setText("Distributed Game");
        
        gameType.add(jRadioButton2);
        jRadioButton2.setText("Live Game");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                false)
                                                .addComponent(jScrollPane1)
                                                .addComponent(
                                                        nameField,
                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(
                                                                                        selectDeadline)
                                                                                .addComponent(
                                                                                        jRadioButton1)
                                                                                .addComponent(
                                                                                        jRadioButton2))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                        146,
                                                                        Short.MAX_VALUE)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(
                                                                                        notifyEmail)
                                                                                .addComponent(
                                                                                        notifySMS))
                                                                .addGap(58, 58,
                                                                        58))
                                                .addComponent(nameLabel)
                                                .addComponent(descriptionLabel)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        comboMonth,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(
                                                                        comboDay,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(
                                                                        comboYear,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)));
        layout.setVerticalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(nameLabel)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameField,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(descriptionLabel)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        100, Short.MAX_VALUE)
                                .addGroup(
                                        layout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGap(75, 75,
                                                                        75)
                                                                .addComponent(
                                                                        selectDeadline)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(
                                                                                        comboMonth,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(
                                                                                        comboDay,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(
                                                                                        comboYear,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(
                                                                                        notifyEmail)
                                                                                .addComponent(
                                                                                        jRadioButton1))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(
                                                                                        notifySMS)
                                                                                .addComponent(
                                                                                        jRadioButton2))))
                                .addGap(24, 24, 24)));
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboDay;
    private javax.swing.JComboBox comboMonth;
    private javax.swing.JComboBox comboYear;
    private javax.swing.JTextPane descriptionField;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.ButtonGroup gameType;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JCheckBox notifyEmail;
    private javax.swing.JCheckBox notifySMS;
    private javax.swing.JCheckBox selectDeadline;
    // End of variables declaration//GEN-END:variables
}