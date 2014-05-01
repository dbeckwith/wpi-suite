package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.DeckOptionsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.TutorialPane;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class NewGameTutorialController implements ActionListener {

	private enum Step {
		 CreateGame("Click on this button to start creating a new Planning Poker Game."),
		 EditName("Games have a name by default, but you should change it to something more readable. Enter a new name and click Next to continue."),
		 EditDescription("Now, enter in a description for what your game is for. Click Next to continue"),
		 AddRequirements("You need to add at least one requirement to your game. Create a new requirement or select an existing one."), 
		 CreateDeck("You can select a deck, or just a number input with an option maximum. For this tutorial, we will create a new deck"),
		 AddCards("Add a few cards to your deck and give them some different values. When you feel your deck is ready, click Next to continue."),
		 SaveDeck("Now save your deck to use it in the game."),
		 SaveGame("There is some more information you can enter in, but for now, we can save this game."),
		 StartGame("Since we didn't specify a deadline, the game needs to be manually started.");
		 
		 public String message;
		 
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
	
	private boolean quit = false;

	public NewGameTutorialController() {
		viewController = PlanningPoker.getViewController();

		mainView = viewController.getMainView();
		toolbar = viewController.getToolbarView();

		tPane = TutorialPane.getInstance();
		
		tPane.setQuitListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				tPane.clear();
				quit = true;				
			}
		});

		currentStep = Step.CreateGame;

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(quit){
			return;
		}
		System.out.printf("\t[TUTORIAL] at %s, called by %s\n", currentStep.toString(), e.getActionCommand());
		
		switch (currentStep) {
			case CreateGame:
				((JButton)e.getSource()).removeActionListener(this);
				viewController.setNewGameCallback(this);
				tPane.setHighlightArea(toolbar.getCommonButtons()
						.getNewGameButton(), currentStep.message);
				currentStep = Step.EditName;
				break;
	
			case EditName:
				newTab = (NewGamePanel) e.getSource();
				Component nameField = newTab.getDescriptionPanel().getNameField();
				tPane.setHighlightArea(nameField, currentStep.message);
				tPane.setNextButtonCallback(this);

				currentStep = Step.EditDescription;
				break;
				
			case EditDescription:
				if(newTab.getDescriptionPanel().getNameField().getText().isEmpty()){
					newTab.getDescriptionPanel().getNameField().setText("Tutorial Game");
				}
				Component descField = newTab.getDescriptionPanel().getDescriptionField();
				tPane.setHighlightArea(descField, currentStep.message);
				tPane.setNextButtonCallback(this);

				currentStep = Step.AddRequirements;
				break;
				
			case AddRequirements:
				if(newTab.getDescriptionPanel().getDescriptionField().getText().isEmpty()){
					newTab.getDescriptionPanel().getDescriptionField().setText("Tutorial Game Description");
				}
				tPane.setNextButtonCallback(this);
				tPane.setHighlightArea(newTab.getCardLayoutPanel(), currentStep.message);

				currentStep = Step.CreateDeck;
				break;
			case CreateDeck:
				if(!newTab.getRequirementsPanel().canValidateForm()){
					newTab.getRequirementsPanel().addCustomRequirement(
							new GameRequirementModel(
									new Requirement(RequirementsListModel.getInstance().getSize(), 
											"Tutorial Requirement",
											"Tutorial Requirement Description")), true);
				}
				DeckOptionsPanel deckOpt = newTab.getDescriptionPanel().getDeckOptionsPanel();
				deckOpt.setNewDeckButtonCallback(this);
				tPane.setHighlightArea(deckOpt.getNewDeckButton(),currentStep.message);

				currentStep = Step.AddCards;
				break;
			case AddCards:				
				tPane.setNextButtonCallback(this);
				newTab.getNewDeckPanel().getCreateDeckButton().setEnabled(false);
				tPane.setHighlightArea(newTab.getCardLayoutPanel(), currentStep.message);

				currentStep = Step.SaveDeck;
				break;
			case SaveDeck:
				newTab.getNewDeckPanel().setSaveDeckCallback(this);
				newTab.getNewDeckPanel().getCreateDeckButton().setEnabled(true);
				tPane.setHighlightArea(newTab.getNewDeckPanel().getCreateDeckButton(), currentStep.message);
				currentStep = Step.SaveGame;
				break;
			case SaveGame:
				newTab.setSaveGameCallback(this);
				tPane.setHighlightArea(newTab.getSaveButton(), currentStep.message);
				break;
			case StartGame:
				tPane.clear();
				mainView.getMainPanel().setVisibilityCallback(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				break;
			default:
				break;


		}

	}

}
