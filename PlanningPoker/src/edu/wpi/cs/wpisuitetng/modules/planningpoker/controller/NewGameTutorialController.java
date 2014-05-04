/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.DeckOptionsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.TutorialPane;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Guides the user through the process of creating and starting a new game
 *
 * @author Team 9
 * @version 1.0
 */
public class NewGameTutorialController implements ActionListener {

	private enum Step {
	     CreateGame("Click this button to start creating a new Planning Poker Game."),
         EditName("Games are given name by default, but we should change it to something more descriptive.\nEnter a new name and click Next to continue."),
         EditDescription("Now, enter in a description of what the game is for.\nClick Next to continue.\n\nIf you do not add a description, this tutorial will add one for you."),
         AddRequirements("We need to add at least one requirement to our game. Create a new requirement or select an existing one.\nClick Next to continue.\n\nIf you do not select any requirements, this tutorial will add one for you."), 
         CreateDeck("We can select a deck, or just a number input with an option maximum. For this tutorial, we will create a new deck."),
         AddCards("Let's add a few cards to our deck and give them some different values.\nWhen you feel the deck is ready, click Next to continue."),
         SaveDeck("Now let's save the deck to use it in the game."),
         SaveGame("There is some more information we can enter in, but for now, we can save this game.\n\nNote: This step will save the game so that it can be voted on later. Once games are saved, they cannot be deleted. If you do not want to save this game, quit the tutorial now."),
         StartGame("All games must be manually started by clicking this button.\n\nNote: This step will start the game so that all users can start voting on it. Once a game has been started, it can no longer be edited. If you do not want to start the game now, quit the tutorial now."),
         Finished("Congratulations, we have created a new Planning Poker Game!");
		 
		 private String message;
		 
		 public String getMessage() {
		     return message;
		 }
 		 
		 private Step(String msg){
			 message = msg;
		 }
	}

	private ViewController viewController;
	private MainView mainView;
	private ToolbarView toolbar;

	private TutorialPane tPane;

	private NewGamePanel newTab;

	private Step currentStep;
	
	private boolean tutorialStarted = false;
	private boolean quit = false;

	/**
	 * Constructor
	 */
	public NewGameTutorialController() {
		viewController = PlanningPoker.getViewController();

		mainView = viewController.getMainView();
		toolbar = viewController.getToolbarView();

		tPane = TutorialPane.getInstance();
		
		tPane.setQuitListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				quit = true;	
				reset();
				tPane.setVisible(false);
				tPane.repaint();
			}
		});

		currentStep = Step.CreateGame;

	}


	/**
	 * Handles the events this tutorial subscribed to in order
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == toolbar.getHelpButtons().getTutorialButton()){
			tutorialStarted = true;
			tPane.setVisible(true);
		}
		
		if(quit || !tutorialStarted){

			return;
		}

//        Logger.getGlobal().info("\t[TUTORIAL] at "+ currentStep.toString() + 
//                ", called by " + e.getActionCommand());
		
		switch (currentStep) {
			case CreateGame:
				createGame();
				break;
	
			case EditName:
				newTab = (NewGamePanel)e.getSource();
				editName();
				break;
				
			case EditDescription:
				editDescription();
				break;
				
			case AddRequirements:
				addRequirements();
				break;
				
			case CreateDeck:
				createDeck();
				break;
				
			case AddCards:				
				addCards();
				break;
				
			case SaveDeck:
				saveDeck();
				break;
				
			case SaveGame:
				saveGame();
				break;
				
			case StartGame:
				startGame();
				break;
				
			case Finished:
				reset();
				quit = true;	
				reset();
				tPane.setVisible(false);
				tPane.repaint();
				JOptionPane.showMessageDialog(mainView, Step.Finished.getMessage());
				break;

			default:
				break;
		}

	}
	
	/**
	 * Resets the controller so that it can start again
	 */
	public void reset(){
	    //reset current panel if needed
	    switch(currentStep){
            case SaveDeck:
                //reset create deck to normal operating mode
                newTab.getNewDeckPanel().setTutorial(false);
                newTab.getNewDeckPanel().validateInput();
                newTab.getNewDeckPanel().getCreateDeckButton().setEnabled(true);
                newTab.getNewDeckPanel().getCancelDeckButton().setEnabled(true);
                break;
            default:
                break;
        }
		tPane.clear();
		tPane.setNextButtonCallback(null);
		quit = false;
		currentStep = Step.CreateGame;
		newTab = null;
		toolbar.getHelpButtons().getTutorialButton().setEnabled(true);
		tutorialStarted = false;
		viewController.setNewGameCallback(null);
	}
	
	/**
	 * Sets up the tutorial for creating a new game
	 */
	private void createGame(){
		toolbar.getHelpButtons().getTutorialButton().setEnabled(false);
		viewController.setNewGameCallback(this);
		tPane.setHighlightArea(toolbar.getCommonButtons()
				.getNewGameButton(), Step.CreateGame.getMessage());
		currentStep = Step.EditName;
	}
	
	/**
	 * Sets up the tutorial for editing the game name
	 */
	private void editName(){
		Component nameField = newTab.getDescriptionPanel().getNameField();
		tPane.setHighlightArea(nameField, Step.EditName.getMessage());
		tPane.setNextButtonCallback(this);

		currentStep = Step.EditDescription;
	}
	
	/**
	 * Sets up the tutorial for editing the description	 * 
	 * Sets a default value if the name is invalid
	 */
	private void editDescription(){
		if(newTab.getDescriptionPanel().getNameField().getText().isEmpty()){
			newTab.getDescriptionPanel().getNameField().setText("Tutorial Game");
		}
		Component descField = newTab.getDescriptionPanel().getDescriptionField();
		tPane.setHighlightArea(descField, Step.EditDescription.getMessage());
		tPane.setNextButtonCallback(this);

		currentStep = Step.AddRequirements;
	}
	
	/**
	 * Sets up the game for adding/selecting requirements
	 * adds a default value if the description is invalid
	 */
	private void addRequirements(){
		if(newTab.getDescriptionPanel().getDescriptionField().getText().isEmpty()){
			newTab.getDescriptionPanel().getDescriptionField().setText("Tutorial Game Description");
		}
		tPane.setNextButtonCallback(this);
		tPane.setHighlightArea(newTab.getCardLayoutPanel(), Step.AddRequirements.getMessage());

		currentStep = Step.CreateDeck;
	}
	
	/**
	 * Sets up the game for creating a new deck
	 * adds a default requirement if there are none selected
	 */
	private void createDeck(){
		if(!newTab.getRequirementsPanel().isFormValid()){
			newTab.getRequirementsPanel().addCustomRequirement(
					new GameRequirementModel(
							new Requirement(RequirementsListModel.getInstance().getSize(), 
									"Tutorial Requirement",
									"Tutorial Requirement Description")), true);
		}
		DeckOptionsPanel deckOpt = newTab.getDescriptionPanel().getDeckOptionsPanel();
		deckOpt.setNewDeckButtonCallback(this);
		tPane.setHighlightArea(deckOpt.getNewDeckButton(),Step.CreateDeck.getMessage());

		currentStep = Step.AddCards;
	}
	
	/**
	 * Sets up the tutorial for adding cards to the new deck
	 */
	private void addCards(){
	    System.out.println("addCards()");
		tPane.setNextButtonCallback(this);
		newTab.getNewDeckPanel().setTutorial(true);
		newTab.getNewDeckPanel().getCreateDeckButton().setEnabled(false);
		newTab.getNewDeckPanel().getCancelDeckButton().setEnabled(false);
		tPane.setHighlightArea(newTab.getCardLayoutPanel(), Step.AddCards.getMessage());

		currentStep = Step.SaveDeck;
	}
	
	/**
	 * Sets up the tutorial for saving the new deck
	 */
	private void saveDeck(){
	    if (newTab.getNewDeckPanel().isUnedited()) {
            newTab.getNewDeckPanel().getAddCardButton().doClick();
            newTab.getNewDeckPanel().getAddCardButton().doClick();
            newTab.getNewDeckPanel().getAddCardButton().doClick();
            newTab.getNewDeckPanel().getAddCardButton().doClick();
            newTab.getNewDeckPanel().getAddCardButton().doClick();
	    }
	    
		newTab.getNewDeckPanel().setSaveDeckCallback(this);
		newTab.getNewDeckPanel().setTutorial(false);
		newTab.getNewDeckPanel().validateInput();
	    newTab.getNewDeckPanel().getCreateDeckButton().setEnabled(true);
	    newTab.getNewDeckPanel().getCancelDeckButton().setEnabled(true);
		tPane.setHighlightArea(newTab.getNewDeckPanel().getCreateDeckButton(), Step.SaveDeck.getMessage());
		currentStep = Step.SaveGame;
	}
	
	/**
	 * Sets up the tutorial for saving the new game
	 */
	private void saveGame(){
		newTab.setSaveGameCallback(this);
		tPane.setHighlightArea(newTab.getSaveButton(), Step.SaveGame.getMessage());
		currentStep = Step.StartGame;
	}
	
	/**
	 * Sets up the tutorial for starting the new game
	 */
	private void startGame(){
		tPane.clear();
		mainView.getMainPanel().setVisibilityCallback(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JComponent start = toolbar.getAdminButtons().getStartGameButton();
				toolbar.getAdminButtons().setStartGameCallback(NewGameTutorialController.this);
				tPane.setHighlightArea(start, Step.StartGame.getMessage());
			}
		});
		currentStep = Step.Finished;

		
	}

}
