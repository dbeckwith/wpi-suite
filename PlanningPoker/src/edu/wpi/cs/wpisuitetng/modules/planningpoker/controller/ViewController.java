/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * TODO: Contributors' names
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ClosableTabComponent;

public class ViewController {
    
    private MainView mainView;
    private ToolbarView toolbar;
    
    /**
     * indicates if admin buttons are being shown if all games panel is selected
     */
    private boolean showAdmin;
    
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
    
    public void addUserPrefsTab() {
        final UserPreferencesPanel prefsPanel = UserPreferencesPanel.getPanel();
        mainView.addTab("Preferences", prefsPanel);
        mainView.setSelectedComponent(prefsPanel);
        
        mainView.setTabComponentAt(mainView.indexOfComponent(prefsPanel),
                new ClosableTabComponent(mainView) {
                    /**
                     * 
                     */
                    private static final long serialVersionUID = 3668078500346186662L;
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mainView.removeTabAt(mainView
                                .indexOfComponent(prefsPanel));
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
        
        RequirementsListModel.getInstance().removeListListener(
                e.getNewGameRequirementsPanel().getRequirementsListObserver());
        mainView.removeTabAt(mainView.indexOfComponent(e));
        
    }
    
    public void cancelNewGame(NewGamePanel e) {
        // int result = JOptionPane.showConfirmDialog(e,
        // "Are you sure you want to cancel this game?");
        // if(result == JOptionPane.OK_OPTION) {
        RequirementsListModel.getInstance().removeListListener(
                e.getNewGameRequirementsPanel().getRequirementsListObserver());
        mainView.removeTabAt(mainView.indexOfComponent(e));
        // }
    }
    
    public MainView getMainView() {
        return mainView;
    }
    
    /**
     * Called to manually end estimation on the currently selected game
     */
    public void endEstimation() {
        GameModel curr = mainView.getMainPanel().getSelectedGame();
        if (curr != null && !curr.isEnded()) {
            curr.setEnded(true);
            UpdateGamesController.getInstance().updateGame(curr);
        }
    }
    
    /**
     * displays the admin buttons if the user is the owner
     * 
     * @param game
     *        the currently displayed game
     */
    public void displayAdmin(GameModel game) {
        if (game != null
                && game.getOwner().equals(
                        ConfigManager.getConfig().getUserName())
                && !game.isEnded()) {
            toolbar.setAdminVisibility(true);
            showAdmin = true;
        }
        else {
            toolbar.setAdminVisibility(false);
            showAdmin = false;
        }
    }
    
    /**
     * changes state of admin buttons when a different tab in mainView is
     * displayed
     * 
     * @param index
     *        the tab now being displayed.
     */
    public void tabChanged(int index) {
        if (showAdmin && index == 0) {
            toolbar.setAdminVisibility(true);
        }
        else {
            toolbar.setAdminVisibility(false);
        }
    }
    
}
