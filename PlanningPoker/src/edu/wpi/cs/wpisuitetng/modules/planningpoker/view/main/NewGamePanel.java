/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addComponent(
																		saveButton)
																.addGap(16)
																.addComponent(
																		cancelButton))
												.addComponent(
														gameDescription,
														GroupLayout.PREFERRED_SIZE,
														428,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(newGameRequirementsCard,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		gameDescription,
																		GroupLayout.DEFAULT_SIZE,
																		290,
																		Short.MAX_VALUE)
																.addGap(18)
																.addGroup(
																		layout.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						saveButton)
																				.addComponent(
																						cancelButton)))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(12)
																.addComponent(
																		newGameRequirementsCard,
																		GroupLayout.DEFAULT_SIZE,
																		322,
																		Short.MAX_VALUE)))
								.addContainerGap()));
		newGameRequirementsCard.setLayout(new CardLayout(0, 0));

		newGameRequirementsPanel = new NewGameRequirementsPanel();
		newGameRequirementsCard.add(newGameRequirementsPanel, "reqlistpanel");

		newDeckPanel = new NewDeckPanel();
		newGameRequirementsCard.add(newDeckPanel, "newdeckpanel");

		newRequirementPanel = new JPanel();
		newGameRequirementsCard.add(newRequirementPanel, "newreqpanel");
		GridBagLayout gbl_newRequirementPanel = new GridBagLayout();
		gbl_newRequirementPanel.columnWidths = new int[] { 99, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0 };
		gbl_newRequirementPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0 };
		gbl_newRequirementPanel.columnWeights = new double[] { 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_newRequirementPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		newRequirementPanel.setLayout(gbl_newRequirementPanel);

		nameLabel = new JLabel("Name:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 0;
		newRequirementPanel.add(nameLabel, gbc_nameLabel);

		newReqNameError = new JLabel("* Required field!");
		newReqNameError.setFont(new Font("Dialog", Font.BOLD, 12));
		newReqNameError.setForeground(new java.awt.Color(255, 0, 0));
		GridBagConstraints gbc_newReqNameError = new GridBagConstraints();
		gbc_newReqNameError.anchor = GridBagConstraints.WEST;
		gbc_newReqNameError.insets = new Insets(0, 0, 5, 5);
		gbc_newReqNameError.gridx = 1;
		gbc_newReqNameError.gridy = 0;
		newRequirementPanel.add(newReqNameError, gbc_newReqNameError);

		newReqName = new JTextField();
		GridBagConstraints gbc_newReqName = new GridBagConstraints();
		gbc_newReqName.gridwidth = 11;
		gbc_newReqName.insets = new Insets(0, 0, 5, 5);
		gbc_newReqName.fill = GridBagConstraints.HORIZONTAL;
		gbc_newReqName.gridx = 0;
		gbc_newReqName.gridy = 1;
		newRequirementPanel.add(newReqName, gbc_newReqName);
		newReqName.setColumns(10);

		descLabel = new JLabel("Description:");
		GridBagConstraints gbc_descLabel = new GridBagConstraints();
		gbc_descLabel.anchor = GridBagConstraints.WEST;
		gbc_descLabel.insets = new Insets(0, 0, 5, 5);
		gbc_descLabel.gridx = 0;
		gbc_descLabel.gridy = 3;
		newRequirementPanel.add(descLabel, gbc_descLabel);

		saveNewReqButton = new JButton("Save Requirement");
		saveNewReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO: get new ID incremented off of existing requirements
				newGameRequirementsPanel
						.addRequirement(new GameRequirementModel(0, newReqName
								.getText(), newReqDesc.getText(),
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
		GridBagConstraints gbc_newReqDescError = new GridBagConstraints();
		gbc_newReqDescError.anchor = GridBagConstraints.WEST;
		gbc_newReqDescError.insets = new Insets(0, 0, 5, 5);
		gbc_newReqDescError.gridx = 1;
		gbc_newReqDescError.gridy = 3;
		newRequirementPanel.add(newReqDescError, gbc_newReqDescError);

		newReqDesc = new JTextArea();
		newReqDesc.setLineWrap(true);
		GridBagConstraints gbc_newReqDesc = new GridBagConstraints();
		gbc_newReqDesc.gridheight = 5;
		gbc_newReqDesc.gridwidth = 11;
		gbc_newReqDesc.insets = new Insets(0, 0, 5, 5);
		gbc_newReqDesc.fill = GridBagConstraints.BOTH;
		gbc_newReqDesc.gridx = 0;
		gbc_newReqDesc.gridy = 4;
		newRequirementPanel.add(newReqDesc, gbc_newReqDesc);

		typeLabel = new JLabel("Type:");
		GridBagConstraints gbc_typeLabel = new GridBagConstraints();
		gbc_typeLabel.anchor = GridBagConstraints.WEST;
		gbc_typeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_typeLabel.gridx = 0;
		gbc_typeLabel.gridy = 9;
		newRequirementPanel.add(typeLabel, gbc_typeLabel);

		newReqType = new JComboBox<String>();
		newReqType.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Epic", "Theme", "User story", "Non-functional dependency",
				"Scenario" }));
		GridBagConstraints gbc_newReqType = new GridBagConstraints();
		gbc_newReqType.gridwidth = 2;
		gbc_newReqType.anchor = GridBagConstraints.WEST;
		gbc_newReqType.insets = new Insets(0, 0, 5, 5);
		gbc_newReqType.gridx = 0;
		gbc_newReqType.gridy = 10;
		newRequirementPanel.add(newReqType, gbc_newReqType);
		GridBagConstraints gbc_saveNewReqButton = new GridBagConstraints();
		gbc_saveNewReqButton.gridwidth = 2;
		gbc_saveNewReqButton.insets = new Insets(0, 0, 0, 5);
		gbc_saveNewReqButton.gridx = 0;
		gbc_saveNewReqButton.gridy = 11;
		newRequirementPanel.add(saveNewReqButton, gbc_saveNewReqButton);

		cancelNewReqButton = new JButton("Return to List");
		cancelNewReqButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showPanel("reqlistpanel");
			}
		});
		GridBagConstraints gbc_cancelNewReqButton = new GridBagConstraints();
		gbc_cancelNewReqButton.insets = new Insets(0, 0, 0, 5);
		gbc_cancelNewReqButton.gridx = 7;
		gbc_cancelNewReqButton.gridy = 11;
		newRequirementPanel.add(cancelNewReqButton, gbc_cancelNewReqButton);

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
		} else {
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
}
