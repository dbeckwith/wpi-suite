package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.DeckOptionsPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.TutorialPane;

public class NewGameTutorialController implements ActionListener {

	private enum Step {
		 CreateGame, EditName, EditDescription, AddRequirements, CreateDeck, AddCards, SaveDeck, SaveGame, StartGame
	}

	private ViewController viewController;
	private MainView mainView;
	private ToolbarView toolbar;

	private TutorialPane tPane;

	private NewGamePanel newTab;

	private Step currentStep;

	public NewGameTutorialController() {
		viewController = PlanningPoker.getViewController();

		mainView = viewController.getMainView();
		toolbar = viewController.getToolbarView();

		tPane = TutorialPane.getInstance();

		currentStep = Step.CreateGame;

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.printf("\t[TUTORIAL] at %s, called by %s\n", currentStep.toString(), e.getActionCommand());
		
		switch (currentStep) {
			case CreateGame:
				((JButton)e.getSource()).removeActionListener(this);
				currentStep = Step.EditName;
				viewController.setNewGameCallback(this);
				tPane.setHighlightArea(toolbar.getCommonButtons()
						.getNewGameButton(), "Create a new game.");
				break;
	
			case EditName:
				currentStep = Step.EditDescription;
				newTab = (NewGamePanel) e.getSource();
				Component nameField = newTab.getDescriptionPanel().getNameField();
				tPane.setHighlightArea(nameField, "Edit name");
				tPane.setNextButtonCallback(this);
				break;
				
			case EditDescription:
				currentStep = Step.AddRequirements;
				Component descField = newTab.getDescriptionPanel().getDescriptionField();
				tPane.setHighlightArea(descField, "Edit the description");
				tPane.setNextButtonCallback(this);
				break;
				
			case AddRequirements:
				currentStep = Step.CreateDeck;
				tPane.setNextButtonCallback(this);
				tPane.setHighlightArea(newTab.getCardLayoutPanel(), "Select or add some requirements");
				break;
			case CreateDeck:
				currentStep = Step.AddCards;
				DeckOptionsPanel deckOpt = newTab.getDescriptionPanel().getDeckOptionsPanel();
				deckOpt.setNewDeckButtonCallback(this);
				tPane.setHighlightArea(deckOpt.getNewDeckButton(), "Click to create a new deck");
				break;
			case AddCards:
				break;
			case SaveDeck:
				break;
			case SaveGame:
				break;
			case StartGame:
				break;
			default:
				break;


		}

	}

}
