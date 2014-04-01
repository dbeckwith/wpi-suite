/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;

/**
 * 
 * @author Lukas
 */
public class NewGameRequirementsPanel extends javax.swing.JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4252474071295177531L;
    
    /**
     * Creates new form GameRequirements
     */
    public NewGameRequirementsPanel() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        jScrollPane1 = new javax.swing.JScrollPane();
        requirementsTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        countError = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        
        requirementsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                
                }, new String[] { "Add", "Name", "Description", "Type" }) {
            /**
                     * 
                     */
            private static final long serialVersionUID = 3245971487236783965L;
            Class[] types = new Class[] { java.lang.Boolean.class,
                    java.lang.Object.class, java.lang.String.class,
                    java.lang.String.class };
            boolean[] canEdit = new boolean[] { true, false, false, false };
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        requirementsTable
                .setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        requirementsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(requirementsTable);
        if (requirementsTable.getColumnModel().getColumnCount() > 0) {
            requirementsTable.getColumnModel().getColumn(0).setMinWidth(40);
            requirementsTable.getColumnModel().getColumn(0).setMaxWidth(40);
        }
        
        addButton.setText("Add Requirement");
        
        countError.setForeground(new java.awt.Color(255, 0, 0));
        countError.setText("At least one requirement is needed!");
        
        jButton1.setText("Remove Requirement");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addButton)
                                .addGap(18, 18, 18)
                                .addComponent(countError)
                                .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE).addComponent(jButton1)
                                .addContainerGap())
                .addComponent(jScrollPane1,
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        javax.swing.GroupLayout.DEFAULT_SIZE, 506,
                        Short.MAX_VALUE));
        layout.setVerticalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addComponent(jScrollPane1,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        306, Short.MAX_VALUE)
                                .addGap(11, 11, 11)
                                .addGroup(
                                        layout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(addButton)
                                                .addComponent(countError)
                                                .addComponent(jButton1))
                                .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        int select = requirementsTable.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        model.removeRow(select);
        validateForm();
        parent.check();
    }// GEN-LAST:event_jButton1ActionPerformed
    
    
    public void addRequirement(GameRequirementModel r) {
        System.out.println("added requirement " + r.toString());
        DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        model.addRow(new Object[] { false, r, r.getDescription().toString(),
                r.getType().toString() });
        requirementsTable.setModel(model);
        validateForm();
        parent.check();
    }
    
    public ArrayList<GameRequirementModel> getRequirementsFromTable() {
        DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
        for (int i = 0; i < model.getRowCount(); i++) {
            requirements.add((GameRequirementModel) model.getValueAt(i, 1));
            System.out.println("found " + requirements.get(i).toString()
                    + " in table");
        }
        return requirements;
    }
    
    public void setEditGamePanel(NewGamePanel p) {
        parent = p;
    }
    
    public boolean validateForm() {
        boolean hasRequirement = requirementsTable.getRowCount() > 0;
        countError.setVisible(!hasRequirement);
        return hasRequirement;
    }
    
    private NewGamePanel parent;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel countError;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable requirementsTable;
    // End of variables declaration//GEN-END:variables
}
