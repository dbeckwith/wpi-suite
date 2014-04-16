/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * 
 * @author Lukas
 */
public class NewGameRequirementsPanel extends javax.swing.JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = -4252474071295177531L;

	private SimpleListObserver requirementsListObserver;
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
                clearRequirements();
                for (GameRequirementModel req : RequirementsListModel
                        .getInstance().getAll()) {
                    addRequirement(req);
                }
                for (GameRequirementModel req : createdRequirements) {
                    addRequirement(req);
                }
            }
        };
        RequirementsListModel.getInstance().addListListener(
                requirementsListObserver);
	}
	
	public SimpleListObserver getRequirementsListObserver() {
	    return requirementsListObserver;
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

		requirementsTableScrollPane = new javax.swing.JScrollPane();
		requirementsTable = new javax.swing.JTable();
		addButton = new javax.swing.JButton();

		clearRequirements();
		requirementsTable
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		requirementsTable.getTableHeader().setReorderingAllowed(false);
		Font temp_Font;
		temp_Font = requirementsTable.getTableHeader().getFont();
		requirementsTable.getTableHeader().setFont(temp_Font.deriveFont(Font.BOLD));
		requirementsTableScrollPane.setViewportView(requirementsTable);

		addButton.setText("Create Requirement");
		addButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				parent.showPanel("newreqpanel");
			}
		});
		addButton.setIcon(ImageLoader.getIcon("newReq.png"));
		
		btnSelectAll = new JButton("Select All");
		btnSelectAll.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        setAllSelected(!allSelected);
		    }
		});
		
		JLabel lblGameRequirements = new JLabel("Game Requirements:");
		
		JButton reloadButton = new JButton("Reload from Requirements Manager");
		reloadButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        GetRequirementsController.getInstance().retrieveRequirements();
		    }
		});
		reloadButton.setIcon(ImageLoader.getIcon("reload.png"));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
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
        DefaultTableModel model = (DefaultTableModel) requirementsTable
                .getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(select, i, 0);
        }
    }

	private TableModel getEmptyTableModel() {
        return new javax.swing.table.DefaultTableModel(new Object[][] {

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
        };
    }
	
	private void checkAllSelected() {
	    DefaultTableModel model = (DefaultTableModel) requirementsTable
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
        if (requirementsTable.getColumnModel().getColumnCount() > 0) {
            requirementsTable.getColumnModel().getColumn(0).setMinWidth(60);
            requirementsTable.getColumnModel().getColumn(0).setMaxWidth(60);
        }
	    requirementsTable.getModel().addTableModelListener(
                new TableModelListener() {

                    @Override
                    public void tableChanged(TableModelEvent e) {
                        validateForm();
                        checkAllSelected();
                        parent.check();
                    }
                });
	}
	
	/**
	 * Adds a custom GameRequirementModel
	 * @param r
	 */
	public void addCustomRequirement(GameRequirementModel r) {
	    createdRequirements.add(r);
	    addRequirement(r);
	}

	private void addRequirement(GameRequirementModel r) {
		System.out.println("added requirement " + r.toString());
		DefaultTableModel model = (DefaultTableModel) requirementsTable
				.getModel();
		model.addRow(new Object[] { false, r, r.getDescription().toString(),
				r.getType().toString() });
		validateForm();
		parent.check();
	}

	/**
	 * @return the requirements in the requirement table
	 */
	public ArrayList<GameRequirementModel> getRequirementsFromTable() {
		DefaultTableModel model = (DefaultTableModel) requirementsTable
				.getModel();
		ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
		for (int i = 0; i < model.getRowCount(); i++) {
			if (!(Boolean) model.getValueAt(i, 0)) {
				continue;
			}
			requirements.add((GameRequirementModel) model.getValueAt(i, 1));
		}
		return requirements;
	}

	public void setEditGamePanel(NewGamePanel p) {
		parent = p;
	}

	/**
	 * Validates the requirements for a new game
	 * @return True if at least one requirement is checked
	 */
	public boolean validateForm() {
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
	 * @return The list of errors for NewGameRequirementPanel
	 */
	public ArrayList<String> getErrors() {
	    ArrayList<String> errors = new ArrayList<>();
	    if (!validateForm()) {
	        errors.add( "At least one requirement is needed");
	    }
	    return errors;
	}

	private NewGamePanel parent;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton addButton;
	private javax.swing.JScrollPane requirementsTableScrollPane;
	protected javax.swing.JTable requirementsTable;
	private JButton btnSelectAll;
}
