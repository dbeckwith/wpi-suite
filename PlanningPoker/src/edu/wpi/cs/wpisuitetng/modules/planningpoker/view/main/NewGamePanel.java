/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.EmailController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * 
 * @author Lukas
 */
public class NewGamePanel extends JPanel {
	/**
     * 
     */
	private static final long serialVersionUID = 6206697919180272913L;

	// TODO: New requirement panel needs to be scrollable vertically
	/**
	 * Creates new form EditGame
	 */
	public NewGamePanel() {
	    setBackground(Color.WHITE);

		initComponents();

		gameDescription.setEditGamePanel(this);
		newGameRequirementsPanel.setEditGamePanel(this);
		newDeckPanel.setEditGamePanel(this);

		newReqName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				validate();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				validate();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				validate();
			}

			private void validate() {

				newReqNameValid = (newReqName.getText() != null && !newReqName
						.getText().isEmpty());
				newReqNameError.setVisible(!newReqNameValid);
				checkNewRequirement();
			}
		});

		newReqDesc.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				validate();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				validate();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				validate();
			}

			private void validate() {

				newReqDescValid = (newReqDesc.getText() != null && !newReqDesc
						.getText().isEmpty());
				newReqDescError.setVisible(!newReqDescValid);
				checkNewRequirement();
			}
		});

		check();
		checkNewRequirement();
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

		newGameRequirementsCard = new JPanel();
		
		errorLabel = new JLabel("ERRORS");
		errorLabel.setForeground(Color.RED);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		layout.setHorizontalGroup(
		    layout.createParallelGroup(Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup()
		            .addGroup(layout.createParallelGroup(Alignment.LEADING)
		                .addGroup(layout.createSequentialGroup()
		                    .addContainerGap()
		                    .addComponent(saveButton)
		                    .addGap(16)
		                    .addComponent(cancelButton)
		                    .addPreferredGap(ComponentPlacement.RELATED)
		                    .addComponent(errorLabel))
		                .addComponent(gameDescription, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addComponent(newGameRequirementsCard, GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
		    layout.createParallelGroup(Alignment.TRAILING)
		        .addGroup(layout.createSequentialGroup()
		            .addComponent(gameDescription, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
		            .addGap(18)
		            .addGroup(layout.createParallelGroup(Alignment.BASELINE)
		                .addComponent(saveButton)
		                .addComponent(cancelButton)
		                .addComponent(errorLabel))
		            .addContainerGap())
		        .addComponent(newGameRequirementsCard, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		newGameRequirementsCard.setLayout(new CardLayout(0, 0));

		newGameRequirementsPanel = new NewGameRequirementsPanel();
		newGameRequirementsCard.add(newGameRequirementsPanel, "reqlistpanel");

		newDeckPanel = new NewDeckPanel();
		newGameRequirementsCard.add(newDeckPanel, "newdeckpanel");

		newRequirementPanel = new JPanel();
		newRequirementPanel.setBackground(Color.WHITE);
		newGameRequirementsCard.add(newRequirementPanel, "newreqpanel");

		nameLabel = new JLabel("Requirement Name:");

		newReqNameError = new JLabel("* Required field!");
		newReqNameError.setFont(new Font("Dialog", Font.BOLD, 12));
		newReqNameError.setForeground(new java.awt.Color(255, 0, 0));

		newReqName = new JTextField();
		newReqName.setColumns(10);

		descLabel = new JLabel("Requirement Description:");

		saveNewReqButton = new JButton("Save Requirement");
		saveNewReqButton.setIcon(ImageLoader.getIcon("Save.png"));
		saveNewReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO: get new ID incremented off of existing requirements
				newGameRequirementsPanel
                        .addCustomRequirement(new GameRequirementModel(0,
                                newReqName.getText(), newReqDesc.getText(),
								(String) newReqType.getSelectedItem()));
				newGameRequirementsPanel.requirementsTable.setValueAt(true,
						newGameRequirementsPanel.requirementsTable
								.getRowCount() - 1, 0);
				newReqName.setText("");
				newReqDesc.setText("");
				newReqType.setSelectedIndex(0);
				showPanel("reqlistpanel");
			}
		});

		newReqDescError = new JLabel("* Required field!");
		newReqDescError.setForeground(new java.awt.Color(255, 0, 0));
		
		scrollPane = new JScrollPane();
		
		newReqDesc = new JTextArea();
		scrollPane.setViewportView(newReqDesc);
		newReqDesc.setLineWrap(true);
				
						typeLabel = new JLabel("Type:");
		
				newReqType = new JComboBox<String>();
				newReqType.setModel(new DefaultComboBoxModel<String>(new String[] {
						"Epic", "Theme", "User story", "Non-functional dependency",
						"Scenario" }));

		cancelNewReqButton = new JButton("Return to List");
		cancelNewReqButton.setIcon(ImageLoader.getIcon("backArrow.png"));
		cancelNewReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showPanel("reqlistpanel");
			}
		});
		GroupLayout gl_newRequirementPanel = new GroupLayout(newRequirementPanel);
		gl_newRequirementPanel.setHorizontalGroup(
		    gl_newRequirementPanel.createParallelGroup(Alignment.LEADING)
		        .addGroup(gl_newRequirementPanel.createSequentialGroup()
		            .addGroup(gl_newRequirementPanel.createParallelGroup(Alignment.LEADING)
		                .addGroup(Alignment.TRAILING, gl_newRequirementPanel.createParallelGroup(Alignment.TRAILING)
		                    .addGroup(Alignment.LEADING, gl_newRequirementPanel.createSequentialGroup()
		                        .addContainerGap()
		                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE))
		                    .addGroup(Alignment.LEADING, gl_newRequirementPanel.createSequentialGroup()
		                        .addContainerGap()
		                        .addComponent(newReqName, GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE))
		                    .addGroup(Alignment.LEADING, gl_newRequirementPanel.createSequentialGroup()
		                        .addContainerGap()
		                        .addComponent(nameLabel)
		                        .addGap(46)
		                        .addComponent(newReqNameError))
		                    .addGroup(Alignment.LEADING, gl_newRequirementPanel.createSequentialGroup()
		                        .addContainerGap()
		                        .addComponent(descLabel)
		                        .addGap(5)
		                        .addComponent(newReqDescError))
		                    .addGroup(Alignment.LEADING, gl_newRequirementPanel.createSequentialGroup()
		                        .addContainerGap()
		                        .addComponent(saveNewReqButton)
		                        .addGap(68)
		                        .addComponent(cancelNewReqButton)))
		                .addGroup(gl_newRequirementPanel.createSequentialGroup()
		                    .addContainerGap()
		                    .addComponent(typeLabel)
		                    .addGap(15)
		                    .addComponent(newReqType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		            .addContainerGap())
		);
		gl_newRequirementPanel.setVerticalGroup(
		    gl_newRequirementPanel.createParallelGroup(Alignment.LEADING)
		        .addGroup(gl_newRequirementPanel.createSequentialGroup()
		            .addContainerGap()
		            .addGroup(gl_newRequirementPanel.createParallelGroup(Alignment.LEADING)
		                .addComponent(nameLabel)
		                .addComponent(newReqNameError))
		            .addGap(5)
		            .addComponent(newReqName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(ComponentPlacement.UNRELATED)
		            .addGroup(gl_newRequirementPanel.createParallelGroup(Alignment.LEADING)
		                .addComponent(descLabel)
		                .addComponent(newReqDescError))
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addGroup(gl_newRequirementPanel.createParallelGroup(Alignment.LEADING)
		                .addGroup(gl_newRequirementPanel.createSequentialGroup()
		                    .addGap(4)
		                    .addComponent(typeLabel))
		                .addComponent(newReqType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		            .addGap(150)
		            .addGroup(gl_newRequirementPanel.createParallelGroup(Alignment.LEADING)
		                .addGroup(gl_newRequirementPanel.createSequentialGroup()
		                    .addGap(1)
		                    .addComponent(saveNewReqButton))
		                .addComponent(cancelNewReqButton))
		            .addContainerGap())
		);
		newRequirementPanel.setLayout(gl_newRequirementPanel);

		setLayout(layout);
	}// </editor-fold>//GEN-END:initComponents

	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveButtonActionPerformed
		if (!(newGameRequirementsPanel.validateForm() && gameDescription
				.validateForm())) {
			check();
			return;
		}

		PlanningPoker.getViewController().saveNewGame(this);
		EmailController.getInstance().sendNotifications();
	}// GEN-LAST:event_saveButtonActionPerformed

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		PlanningPoker.getViewController().cancelNewGame(this);
	}// GEN-LAST:event_cancelButtonActionPerformed

	protected void showPanel(String panel) {
		((CardLayout) newGameRequirementsCard.getLayout()).show(
				newGameRequirementsCard, panel);
	}

	@Override
	public String getName() {
		return gameDescription.nameField.getText();
	}

	/**
	 * Gets selected deck
	 * 
	 * @return
	 */
	public DeckModel getDeck() {
		return gameDescription.getDeck();
	}

	public String getDescription() {
		return gameDescription.descriptionField.getText();
	}

	public GameType getGameType() {
		if (gameDescription.distributed.isSelected()) {
			return GameType.DISTRIBUTED;
        }
        else {
			return GameType.LIVE;
		}
	}

	public Date getEndDate() {
		return gameDescription.getDate();
	}

	public ArrayList<GameRequirementModel> getRequirements() {
		return newGameRequirementsPanel.getRequirementsFromTable();
	}

	/**
	 * Checks if game description panel and requirements are properly entered
	 */
	public void check() {
		saveButton.setEnabled(gameDescription.validateForm()
				&& newGameRequirementsPanel.validateForm());
		ArrayList<String> errors = new ArrayList<>();
		errors.addAll(gameDescription.getErrors());
		errors.addAll(newGameRequirementsPanel.getErrors());
        errorLabel.setText(errors.isEmpty()? "" : errors.get(0));
	}

	/**
	 * Checks that name and description for new requirement are properly entered
	 */
	public void checkNewRequirement() {
		saveNewReqButton.setEnabled(newReqNameValid && newReqDescValid);
	}

	public NewGameRequirementsPanel getNewGameRequirementsPanel() {
		return newGameRequirementsPanel;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton cancelButton;
	private JPanel newGameRequirementsCard;
	private edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGameRequirementsPanel newGameRequirementsPanel;
	private NewDeckPanel newDeckPanel;
	private edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGameDescriptionPanel gameDescription;
	private javax.swing.JButton saveButton;
	private JPanel newRequirementPanel;
	private JLabel nameLabel;
	private JTextField newReqName;
	private JComboBox<String> newReqType;
	private JLabel descLabel;
	private JLabel typeLabel;
	private JButton saveNewReqButton;
	private JButton cancelNewReqButton;
	private JTextArea newReqDesc;
	private Boolean newReqNameValid = false;
	private Boolean newReqDescValid = false;
	private JLabel newReqNameError;
	private JLabel newReqDescError;
	private JScrollPane scrollPane;
	private JLabel errorLabel;
}
