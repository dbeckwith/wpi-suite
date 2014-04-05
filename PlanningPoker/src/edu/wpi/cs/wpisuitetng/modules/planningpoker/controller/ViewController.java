package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ClosableTabComponent;

public class ViewController {
    
    private MainView mainView;
    private ToolbarView toolbar;
    
    public ViewController(MainView mainView, ToolbarView toolbar) {
        this.mainView = mainView;
        this.toolbar = toolbar;
    }
    
    public void addNewGameTab() {
        final NewGamePanel editGame = new NewGamePanel();
        mainView.addTab("New Game", editGame);
        mainView.setSelectedComponent(editGame);
        
        mainView.setTabComponentAt(mainView.indexOfComponent(editGame),
                new ClosableTabComponent(mainView) {
                    /**
                     * 
                     */
                    private static final long serialVersionUID = 7088866301855075603L;
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cancelNewGame(editGame);
                    }
                });
        
    }
    
    public void saveNewGame(NewGamePanel e) {
        final GameModel newGame = new GameModel(e.getName(),
                e.getDescription(), e.getRequirements(), e.getEndDate(),
                e.getGameType(), GameStatus.PENDING);
        
        new Thread() {
            @Override
            public void run() {
                AddGameController.getInstance().addGame(newGame);
            }
        }.start();
        
        mainView.removeTabAt(mainView.indexOfComponent(e));
        
    }
    
    public void cancelNewGame(NewGamePanel e) {
        // int result = JOptionPane.showConfirmDialog(e,
        // "Are you sure you want to cancel this game?");
        // if(result == JOptionPane.OK_OPTION) {
        mainView.removeTabAt(mainView.indexOfComponent(e));
        // }
    }
    
    public MainView getMainView() {
        return mainView;
    }
    
}
