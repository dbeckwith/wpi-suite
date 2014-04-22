/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * This is a class to show the sub panel within NewGamePanel. The sub panel contains all requirements candidate for the user to add to the new game.
 * @author Team 9
 * @version 1.0
 */
public class NewGameRequirementsPanel extends javax.swing.JPanel {
    
    private static final long serialVersionUID = -4252474071295177531L;
    
    private final SimpleListObserver requirementsListObserver;
    private final ArrayList<GameRequirementModel> createdRequirements;
    
    // TODO: remember which requirements were checked off as added when the list updates
    
    /**
     * Creates new form GameRequirements
     */
    public NewGameRequirementsPanel() {
        setBackground(Color.WHITE);
        initComponents();
        createdRequirements = new ArrayList<>();
        requirementsTableScrollPane.getViewport().setBackground(Color.WHITE);
        clearRequirements();
        
        requirementsListObserver = new SimpleListObserver() {
            
            @Override
            public void listUpdated() {
                final List<GameRequirementModel> inTable = getAllRequirementsFromTable(true);
                for (GameRequirementModel req : RequirementsListModel
                        .getInstance().getAll()) {
                    if(!inTable.contains(req)){
                        addRequirement(req);
                    }
                }
            }
        };
        RequirementsListModel.getInstance().addListListener(
                requirementsListObserver);
        GetRequirementsController.getInstance().retrieveRequirements();
    }
    
    /**
     * Sets the game for editing
     * @param game the GameModel to load requirements from
     */
    public void setGame(GameModel game){
        final List<GameRequirementModel> gameReqs = game.getRequirements();
        clearRequirements();
        for(GameRequirementModel req : gameReqs){
            addRequirement(req, true);
        }
    }
    
    /**
     * Get a list of requirement observers
     * @return a list of requirement observers
     */
	public SimpleListObserver getRequirementsListObserver() {
	    return requirementsListObserver;
	}

	private void initComponents() {

		requirementsTableScrollPane = new javax.swing.JScrollPane();
		requirementsTable = new javax.swing.JTable();
		addButton = new javax.swing.JButton();

		clearRequirements();
		requirementsTable
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		requirementsTable.getTableHeader().setReorderingAllowed(false);
		final Font temp_Font;
		temp_Font = requirementsTable.getTableHeader().getFont();
		requirementsTable.getTableHeader().setFont(temp_Font.deriveFont(Font.BOLD));
		requirementsTableScrollPane.setViewportView(requirementsTable);

		addButton.setText("Create Requirement");
		addButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				parentPanel.showPanel("newreqpanel");
			}
		});
		addButton.setIcon(ImageLoader.getIcon("newReq.png"));
		
		btnSelectAll = new JButton("Select All");
		btnSelectAll.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        setAllSelected(!allSelected);
		    }
		});
		
		final JLabel lblGameRequirements = new JLabel("Game Requirements:");
		
		final JButton reloadButton = new JButton("Reload from Requirements Manager");
		reloadButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        GetRequirementsController.getInstance().retrieveRequirements();
		    }
		});
		reloadButton.setIcon(ImageLoader.getIcon("reload.png"));

		final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		layout.setHorizontalGroup(
		    layout.createParallelGroup(Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup()
		            .addContainerGap()
		            .addGroup(layout.createParallelGroup(Alignment.LEADING)
		                .addComponent(requirementsTableScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
		                .addGroup(layout.createSequentialGroup()
		                    .addComponent(addButton)
		                    .addPreferredGap(ComponentPlacement.RELATED)
		                    .addComponent(reloadButton))
		                .addComponent(lblGameRequirements)
		                .addComponent(btnSelectAll, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
		            .addContainerGap())
		);
		layout.setVerticalGroup(
		    layout.createParallelGroup(Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup()
		            .addContainerGap()
		            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
		                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
		                .addComponent(reloadButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(ComponentPlacement.UNRELATED)
		            .addComponent(lblGameRequirements)
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addComponent(requirementsTableScrollPane, GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addComponent(btnSelectAll)
		            .addContainerGap())
		);
		setLayout(layout);
	}// </editor-fold>//GEN-END:initComponents

	private boolean allSelected = false;

	private void setAllSelected(boolean select) {
	    allSelected = select;
        btnSelectAll.setText(allSelected ? "Deselect All" : "Select All");
        final DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(select, i, 0);
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private TableModel getEmptyTableModel() {
        return new javax.swing.table.DefaultTableModel(new Object[][] {
                
        }, new String[] { "Add", "Name", "Description", "Type" }) {
            /**
             * 
             */
            private static final long serialVersionUID = 3245971487236783965L;
            private final Class[] types = new Class[] { java.lang.Boolean.class,
                    java.lang.Object.class, java.lang.String.class,
                    java.lang.String.class };
            private final boolean[] canEdit = new boolean[] { true, false, false, false };
            
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }
    
    /**
     * setups up form with for editing a game
     * @param reqs the requirements in the edited game
     */
    public void setupData(List<GameRequirementModel> reqs){
        //add all requirements to the game
        for (GameRequirementModel req: reqs){
            addCustomRequirement(req);  
        }
        final DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        //select all requirements that are currently in the game
        for (int i = 0; i < model.getRowCount(); i++){
            if(reqs.contains((GameRequirementModel) model.getValueAt(i, 1))){
                model.setValueAt(true, i, 0);
            }
        }
    }
    
    private void checkAllSelected() {
        final DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (!(Boolean) model.getValueAt(i, 0)) {
                allSelected = false;
                btnSelectAll.setText("Select All");
                return;
            }
        }
        allSelected = true;
        btnSelectAll.setText("Deselect All");
    }
    
    private void clearRequirements() {
        requirementsTable.setModel(getEmptyTableModel());
        requirementsTable.getModel().addTableModelListener(
                new TableModelListener() {
                    
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        canValidateForm();
                        checkAllSelected();
                        if(parentPanel != null){
                            parentPanel.check();
                        }
                    }
                });
    }
    
    /**
     * This method add the requirement created by user in planning poker game (not in requirement manager)
     * @param r the requirement to be add
     */
    public void addCustomRequirement(GameRequirementModel r) {
        createdRequirements.add(r);
        addRequirement(r);
    }
    
    private void addRequirement(GameRequirementModel r) {
        addRequirement(r, false);
    }
    
    private void addRequirement(GameRequirementModel r, boolean selected) {
        System.out.println("added requirement " + r.toString());
        final DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        model.addRow(new Object[] { selected, r, r.getDescription().toString(),
                r.getType().toString() });
        canValidateForm();
        parentPanel.check();
    }
    
    
    /**
     * gets all selected requirements from the table
     * @return all selected requirements from the table
     */
    public ArrayList<GameRequirementModel> getRequirementsFromTable() {
        return getAllRequirementsFromTable(false);
    }
    
    /**
     * gets requirements from the table
     * @param all true if all requirements, flase if only slective requirements
     * @return requirements from table
     */
    public ArrayList<GameRequirementModel> getAllRequirementsFromTable(boolean all) {
        final DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        final ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (!(Boolean) model.getValueAt(i, 0) && !all) {
                continue;
            }
            requirements.add((GameRequirementModel) model.getValueAt(i, 1));
        }
        return requirements;
    }
    
    /**
     * set the parent panel for this sub panel 
     * @param p the parent panel
     */
    public void setEditGamePanel(NewGamePanel p) {
        parentPanel = p;
    }
    
    /**
     * make sure that there is at least one checked requirement to create a game
     * @return whether the user has entered valid input
     */
    public boolean canValidateForm() {
        boolean hasRequirement = false;
        
        // make sure at least one requirement is checked
        for (int i = 0; i < requirementsTable.getRowCount(); i++) {
            if ((Boolean) requirementsTable.getValueAt(i, 0)) {
                hasRequirement = true;
                break;
            }
        }
        
        return hasRequirement;
    }
    
    /**
     * get the list of errors about filling the form 
     * @return the list of errors
     */
    public ArrayList<String> getErrors() {
        final ArrayList<String> errors = new ArrayList<>();
        if (!canValidateForm()) {
            errors.add( "At least one requirement is needed");
        }
        return errors;
    }
    
    private NewGamePanel parentPanel;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JScrollPane requirementsTableScrollPane;
    protected javax.swing.JTable requirementsTable;
    private JButton btnSelectAll;
}
