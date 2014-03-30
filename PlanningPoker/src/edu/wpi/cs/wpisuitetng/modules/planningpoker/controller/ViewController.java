package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGameEditPanel;

public class ViewController {
	
	private MainView mainView;
	private ToolbarView toolbar;
	
	public ViewController(MainView mainView, ToolbarView toolbar){
		this.mainView = mainView;
		this.toolbar = toolbar;		
	}
	
	public void addNewGame(){
		NewGameEditPanel editGame = new NewGameEditPanel();
		mainView.addTab("New Game", editGame);
		mainView.setSelectedComponent(editGame);
	}

}
