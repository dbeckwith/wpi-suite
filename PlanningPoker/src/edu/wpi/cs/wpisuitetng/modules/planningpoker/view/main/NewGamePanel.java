/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;



/**
 * This is a class to show the panel when the user clicks create game button.
 * 
 * @author Team 9
 * @version 1.0
 */
public class NewGamePanel extends JPanel implements AncestorListener {
    private static final long serialVersionUID = 6206697919180272913L;
    private int validateMode = 0;
    
    // TODO: New requirement panel needs to be scrollable vertically
    /**
     * Creates new form EditGame
     */
    public NewGamePanel() {
        setBackground(Color.WHITE);
        
        initComponents();
        
        addAncestorListener(this);
        
        NewGamePanel.setErrorBorder(newReqName, false);
        NewGamePanel.setErrorBorder(newReqDesc, false);
        
        gameDescription.setEditGamePanel(this);
        final GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
                Alignment.LEADING).addComponent(gameDescription,
                GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE));
        gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
                Alignment.LEADING).addComponent(gameDescription,
                GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE));
        panel_1.setLayout(gl_panel_1);
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
                hasChanged = true;
                newReqNameValid = (newReqName.getText() != null && !newReqName
                        .getText().isEmpty());
                NewGamePanel.setErrorBorder(newReqName, newReqNameValid);
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
                hasChanged = true;
                newReqDescValid = (newReqDesc.getText() != null && !newReqDesc
                        .getText().isEmpty());
                NewGamePanel.setErrorBorder(newReqDesc, newReqDescValid);
                checkNewRequirement();
                
            }
        });
        
        undoButton.setVisible(false);
        check();
        checkNewRequirement();
    }
    
    /**
     * Constructor for editing a game Fills all fields with information from the
     * given game
     * 
     * @param game
     *        the GameModel to load from
     */
    public NewGamePanel(GameModel game) {
        this();
        validateMode = 1;
        
        setData(game);
        check();
        checkNewRequirement();
        undoButton.setEnabled(false);
        saveButton.setEnabled(false);
    }
    
    public void setData(GameModel g) {
        game = g;
        
        gameDescription.setGame(game);
        newGameRequirementsPanel.setGame(game);
        
        undoButton.setVisible(true);
        saveButton.setEnabled(false);
        undoButton.setEnabled(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {
        hasChanged = false;
        saveButton = new javax.swing.JButton();
        
        saveButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(saveGameCallback != null){
					ActionListener call = saveGameCallback;
					saveGameCallback = null;
					call.actionPerformed(new ActionEvent(this, 0, ""));
				}
			}
		});
        
        saveButton.setToolTipText("Save the new game and close this tab.");
        cancelButton = new javax.swing.JButton();
        cancelButton.setToolTipText("Cancel creation of this game.");
        
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
        newGameRequirementsCard.setBorder(new EtchedBorder(
                EtchedBorder.LOWERED, null, null));
        
        errorLabel = new JLabel("At least one requirement is needed");
        errorLabel.setForeground(Color.RED);
        
        undoButton = new JButton("Undo Changes");
        undoButton.setToolTipText("Undo all changes made to this game so far and revert it back to its last saved state."); // $codepro.audit.disable lineLength
        undoButton.setIcon(ImageLoader.getIcon("undo-icon.png"));
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setData(game);
            }
        });
        
        scrollPane_1 = new JScrollPane();
        scrollPane_1
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        scrollPane_1,
                                                                        GroupLayout.DEFAULT_SIZE,
                                                                        323,
                                                                        Short.MAX_VALUE)
                                                                .addGap(12)
                                                                .addComponent(
                                                                        newGameRequirementsCard,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        631,
                                                                        GroupLayout.PREFERRED_SIZE))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        saveButton)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        undoButton,
                                                                        GroupLayout.PREFERRED_SIZE,
                                                                        136,
                                                                        GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        cancelButton)
                                                                .addPreferredGap(
                                                                        ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        errorLabel)
                                                                .addContainerGap()))));
        layout.setVerticalGroup(layout
                .createParallelGroup(Alignment.TRAILING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.TRAILING)
                                                .addComponent(
                                                        newGameRequirementsCard,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        492, Short.MAX_VALUE)
                                                .addComponent(
                                                        scrollPane_1,
                                                        GroupLayout.DEFAULT_SIZE,
                                                        492, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(saveButton)
                                                .addComponent(cancelButton)
                                                .addComponent(undoButton)
                                                .addComponent(errorLabel))
                                .addContainerGap()));
        
        panel_1 = new JPanel();
        scrollPane_1.setViewportView(panel_1);
        gameDescription = new edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main
        		.NewGameDescriptionPanel();
        newGameRequirementsCard.setLayout(new CardLayout(0, 0));
        
        newGameRequirementsPanel = new NewGameRequirementsPanel();
        newGameRequirementsPanel.setEditGamePanel(this);
        newGameRequirementsCard.add(newGameRequirementsPanel, "reqlistpanel");
        
        newDeckPanel = new NewDeckPanel();
        newGameRequirementsCard.add(newDeckPanel, "newdeckpanel");
        
        newRequirementPanel = new JPanel();
        newRequirementPanel.setBackground(Color.WHITE);
        newGameRequirementsCard.add(newRequirementPanel, "newreqpanel");
        
        nameLabel = new JLabel("Requirement Name: *");
        
        newReqName = new JTextField();
        newReqName.setColumns(10);
        
        descLabel = new JLabel("Requirement Description: *");
        
        saveNewReqButton = new JButton("Save Requirement");
        saveNewReqButton.setMinimumSize(new Dimension(170, 31));
        saveNewReqButton.setMaximumSize(new Dimension(170, 31));
        saveNewReqButton.setIcon(ImageLoader.getIcon("Save.png"));
        saveNewReqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO: get new ID incremented off of existing requirements
                final GameRequirementModel newRequirement = new GameRequirementModel(
                        0, newReqName.getText(), newReqDesc.getText(),
                        newReqType.getSelectedItem().toString());
                
                newGameRequirementsPanel.addCustomRequirement(newRequirement,
                        true);
                
                newReqName.setText("");
                newReqDesc.setText("");
                newReqType.setSelectedIndex(0);
                showPanel("reqlistpanel");
            }
        });
        
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
        cancelNewReqButton.setMaximumSize(new Dimension(150, 31));
        cancelNewReqButton.setMinimumSize(new Dimension(150, 31));
        cancelNewReqButton.setIcon(ImageLoader.getIcon("backArrow.png"));
        cancelNewReqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                showPanel("reqlistpanel");
            }
        });
        
        newReqErrorsLabel = new JLabel("ERRORS");
        newReqErrorsLabel.setForeground(Color.RED);
        final GroupLayout gl_newRequirementPanel = new GroupLayout(
                newRequirementPanel);
        gl_newRequirementPanel
                .setHorizontalGroup(gl_newRequirementPanel
                        .createParallelGroup(Alignment.TRAILING)
                        .addGroup(
                                gl_newRequirementPanel
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                gl_newRequirementPanel
                                                        .createParallelGroup(
                                                                Alignment.LEADING)
                                                        .addComponent(
                                                                scrollPane)
                                                        .addComponent(
                                                                newReqName,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                683,
                                                                Short.MAX_VALUE)
                                                        .addComponent(nameLabel)
                                                        .addComponent(descLabel)
                                                        .addGroup(
                                                                gl_newRequirementPanel
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                typeLabel)
                                                                        .addPreferredGap(
                                                                                ComponentPlacement
                                                                                .RELATED)
                                                                        .addComponent(
                                                                                newReqType,
                                                                                GroupLayout
                                                                                .PREFERRED_SIZE,
                                                                                GroupLayout
                                                                                .DEFAULT_SIZE,
                                                                                GroupLayout
                                                                                .PREFERRED_SIZE))
                                                        .addGroup(
                                                                gl_newRequirementPanel
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                cancelNewReqButton)
                                                                        .addPreferredGap(
                                                                                ComponentPlacement
                                                                                .RELATED)
                                                                        .addComponent(
                                                                                saveNewReqButton)
                                                                        .addPreferredGap(
                                                                                ComponentPlacement
                                                                                .RELATED)
                                                                        .addComponent(
                                                                                newReqErrorsLabel)))
                                        .addContainerGap()));
        gl_newRequirementPanel
                .setVerticalGroup(gl_newRequirementPanel
                        .createParallelGroup(Alignment.LEADING)
                        .addGroup(
                                gl_newRequirementPanel
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(nameLabel)
                                        .addGap(5)
                                        .addComponent(newReqName,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(
                                                ComponentPlacement.UNRELATED)
                                        .addComponent(descLabel)
                                        .addPreferredGap(
                                                ComponentPlacement.RELATED)
                                        .addComponent(scrollPane,
                                                GroupLayout.DEFAULT_SIZE, 264,
                                                Short.MAX_VALUE)
                                        .addPreferredGap(
                                                ComponentPlacement.UNRELATED)
                                        .addGroup(
                                                gl_newRequirementPanel
                                                        .createParallelGroup(
                                                                Alignment.BASELINE)
                                                        .addComponent(typeLabel)
                                                        .addComponent(
                                                                newReqType,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(
                                                ComponentPlacement.UNRELATED)
                                        .addGroup(
                                                gl_newRequirementPanel
                                                        .createParallelGroup(
                                                                Alignment.BASELINE)
                                                        .addComponent(
                                                                cancelNewReqButton)
                                                        .addComponent(
                                                                saveNewReqButton)
                                                        .addComponent(
                                                                newReqErrorsLabel))
                                        .addGap(5)));
        newRequirementPanel.setLayout(gl_newRequirementPanel);
        
        setLayout(layout);
    }// </editor-fold>//GEN-END:initComponents
    
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        boolean alreadyReturned = false;
        if (!(newGameRequirementsPanel.canValidateForm() && gameDescription
                .canValidateForm())) {
            check();
            alreadyReturned = true;
            
        }
        if (!alreadyReturned){
            if (game == null) {
                PlanningPoker.getViewController().saveNewGame(this);
            }
            else {
                PlanningPoker.getViewController().updateGame(game, this);
            }
        }
          
        
    }
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (game == null) {
            PlanningPoker.getViewController().cancelNewGame(this, false);
        }
        else {
            PlanningPoker.getViewController().cancelEditGame(this, false);
        }
    }
    
    /**
     * Shows the NewGamePanel
     * 
     * @param panel
     *        name of the panel to be shown
     */
    protected void showPanel(String panel) {
        if (panel.equals("newdeckpanel")) {
            newDeckPanel.resetFields();
        }
        ((CardLayout) newGameRequirementsCard.getLayout()).show(
                newGameRequirementsCard, panel);
    }
    
    @Override
    public String getName() {
        return gameDescription.getNameField().getText();
    }
    
    /**
     * Gets selected deck
     * 
     * @return
     */
    public DeckModel getDeck() {
        return gameDescription.getDeck();
    }
    
    /**
     * Gets game description
     * 
     * @return game description
     */
    public String getDescription() {
        return gameDescription.getDescriptionField().getText();
    }
    
    /**
     * Gets the end date of the game
     * 
     * @return the end date of the game
     */
    public Date getEndDate() {
        return gameDescription.getDate();
    }
    
    /**
     * Get the requirements of the new game
     * 
     * @return the requirements of the new game
     */
    public ArrayList<GameRequirementModel> getRequirements() {
        return newGameRequirementsPanel.getRequirementsFromTable();
    }
    
    /**
     * Checks if game description panel and requirements are properly entered
     */
    public void check() {
        if (validateMode == 0) {
            saveButton.setEnabled(gameDescription.canValidateForm()
                    && newGameRequirementsPanel.canValidateForm());
        }
        else {
            boolean oldHasDeadline = game.hasDeadline();
            boolean newHasDeadline = gameDescription.getDate() != null;
            boolean sameDateStatus;
            if (oldHasDeadline == newHasDeadline) {
                if (oldHasDeadline) {
                    sameDateStatus = game.getEndTime().compareTo(
                            gameDescription.getDate()) == 0;
                }
                else {
                    sameDateStatus = true;
                }
            }
            else {
                sameDateStatus = false;
            }
            
            saveButton.setEnabled(gameDescription.canValidateForm()
                    && newGameRequirementsPanel.canValidateForm()
                    && !(game.getDeck().getName().equals(getDeck().getName())
                    		&& game.getDeck().getMaxEstimate() == getDeck().getMaxEstimate()
                            && sameDateStatus && game.getRequirements()
                            .equals(newGameRequirementsPanel
                                    .getRequirementsFromTable())));
            
            setHasChanged(!(game.getDeck().getName().equals(getDeck().getName())
                            && game.getDeck().getMaxEstimate() == getDeck().getMaxEstimate()
                            && sameDateStatus && game.getRequirements()
                            .equals(newGameRequirementsPanel
                                    .getRequirementsFromTable())));
            
            undoButton.setEnabled(hasChanged);
            
        }
        final ArrayList<String> errors = new ArrayList<>();
        errors.addAll(gameDescription.getErrors());
        errors.addAll(newGameRequirementsPanel.getErrors());
        
        if (!errors.isEmpty()) {
            errorLabel.setText(errors.get(0));
        }
        else {
            errorLabel.setText("");
        }
    }
    
    /**
     * Checks that name and description for new requirement are properly entered
     */
    public void checkNewRequirement() {
        if (!newReqNameValid) {
            newReqErrorsLabel.setVisible(true);
            newReqErrorsLabel.setText("Name field is required");
        }
        else if (!newReqDescValid) {
            newReqErrorsLabel.setVisible(true);
            newReqErrorsLabel.setText("Description field is required");
        }
        else {
            newReqErrorsLabel.setVisible(false);
        }
        saveNewReqButton.setEnabled(newReqNameValid && newReqDescValid);
    }
    
    /**
     * Set a components border color to indicate and error
     * 
     * @param c
     *        the component
     * @param valid
     *        whether or not it is valid
     */
    private static void setErrorBorder(JComponent c, boolean valid) {
        if (!valid) {
            c.setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        else {
            c.setBorder(BorderFactory.createEtchedBorder());
        }
    }
    
    /**
     * get the panel containing requirements of the new game
     * 
     * @return the panel containing requirements of the new game
     */
    public NewGameRequirementsPanel getNewGameRequirementsPanel() {
        return newGameRequirementsPanel;
    }
    
    public void setHasChanged(boolean b) {
        hasChanged = b;
    }
    
    public boolean getHasChanged() { // $codepro.audit.disable booleanMethodNamingConvention
        return hasChanged;
    }
    
    /**
     * Sets the selected deck in the combo box of saved decks to the newest deck
     */
    public void setNewDeck(){ // $codepro.audit.disable accessorMethodNamingConvention
        gameDescription.setNewDeck();
    }
    
    public NewGameDescriptionPanel getDescriptionPanel(){
    	return gameDescription;
    } 
    
    public NewGameRequirementsPanel getRequirementsPanel(){
    	return newGameRequirementsPanel;
    }
    
    public NewDeckPanel getNewDeckPanel(){
    	return newDeckPanel;
    }
    
    public JPanel getCardLayoutPanel(){
    	return newGameRequirementsCard;
    }
    
    public void setNewGameCallback(ActionListener e){
    	System.out.println("setnewgame callback" + gameDescription.getName());
    	newGameCallback = e;
    }
    
	@Override
	public void ancestorAdded(AncestorEvent event) {
		if(newGameCallback != null){
			newGameCallback.actionPerformed(new ActionEvent(this, 0, "newgame"));
			newGameCallback = null;
		}
	}

	@Override
	public void ancestorMoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ancestorRemoved(AncestorEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public JButton getSaveButton() {
		return saveButton;
	}
	
	public void setSaveGameCallback(ActionListener a){
		saveGameCallback = a;
	}

    private GameModel game = null;
    private ActionListener newGameCallback;
    
    private ActionListener saveGameCallback;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private JPanel newGameRequirementsCard;
    private NewGameRequirementsPanel newGameRequirementsPanel;
    private NewDeckPanel newDeckPanel;
    private NewGameDescriptionPanel gameDescription;

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
    private JScrollPane scrollPane;
    private JLabel errorLabel;
    private JLabel newReqErrorsLabel;
    private JButton undoButton;
    private boolean hasChanged;
    private JScrollPane scrollPane_1;
    private JPanel panel_1;
    public NewGameDescriptionPanel getGameDescriptionPanel() {
        return gameDescription;
    }
}
