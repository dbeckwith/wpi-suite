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

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.DefaultOptionPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.OptionPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ClosableTabComponent;

public class ViewController {
    
    private final MainView mainView;
    private final ToolbarView toolbar;
    private OptionPane cancelConfirm;
    
    /**
     * indicates if admin buttons are being shown if all games panel is selected
     */
    private boolean showAdmin;
    
    /**
     * Creates a new ViewController
     * 
     * @param mainView
     *        The MainView for this ViewController to control
     * @param toolbar
     *        The ToolbarView for this ViewController to control
     */
    public ViewController(MainView mainView, ToolbarView toolbar) {
        this.mainView = mainView;
        this.toolbar = toolbar;
        this.cancelConfirm = new DefaultOptionPane();
    }
    
    /**
     * Adds a new tab for a new game
     */
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
    
    /**
     * Adds a user preferences tab
     */
    public void addUserPrefsTab() {
        final UserPreferencesPanel prefsPanel = UserPreferencesPanel.getPanel();
        mainView.addTab("Preferences", prefsPanel);
        mainView.setSelectedComponent(prefsPanel);
        
        mainView.setTabComponentAt(mainView.indexOfComponent(prefsPanel),
                new ClosableTabComponent(mainView) {
                    private static final long serialVersionUID = 3668078500346186662L;
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mainView.removeTabAt(mainView
                                .indexOfComponent(prefsPanel));
                    }
                });
    }
    
    /**
     * Saves a GameModel based on the information in the NewGamePanel
     * 
     * @param e
     *        The NewGamePanel to create a game from
     */
    public void saveNewGame(NewGamePanel e) {
        //TODO add save as new here
        DeckModel d = e.getDeck();
        final GameModel newGame = new GameModel(e.getName(),
                e.getDescription(), e.getRequirements(), new DeckModel(
                        d.toString(), d.getCards(),
                        d.getAllowsMultipleSelection()), e.getEndDate(),
                e.getGameType(), GameStatus.NEW, ConfigManager.getConfig()
                        .getUserName());
        
        AddGameController.getInstance().addGame(newGame);
        
        RequirementsListModel.getInstance().removeListListener(
                e.getNewGameRequirementsPanel().getRequirementsListObserver());
        mainView.removeTabAt(mainView.indexOfComponent(e));
        
    }
    
    /**
     * Cancels creation of a new game
     *
     * @param e The NewGamePanel to cancel
     */
    public void cancelNewGame(NewGamePanel e) {
        final int result = cancelConfirm.showConfirmDialog(e,
                "Are you sure you want to cancel this game?", "Cancel Game",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            RequirementsListModel.getInstance().removeListListener(
                    e.getNewGameRequirementsPanel()
                            .getRequirementsListObserver());
            mainView.removeTabAt(mainView.indexOfComponent(e));
        }
    }
    
    /**
     * Returns the MainView object for this ViewController
     *
     * @return The MainView object
     */
    public MainView getMainView() {
        return mainView;
    }
    
    /**
     * Called to manually end estimation on the currently selected game
     */
    public void endEstimation() {
        final GameModel curr = mainView.getMainPanel().getSelectedGame();
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
                && !game.isClosed()) {
            toolbar.setAdminVisibility(true);
            toolbar.showStartButtonGroup(game.getStatus() == GameStatus.NEW);
            if (game.getStatus() == GameStatus.COMPLETE) {
                toolbar.setEndGame(false);
            }
            else {
                toolbar.setEndGame(true);
            }
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
    
    /**
     * closes a game so it can't be edited
     */
    public void closeGame() {
        final GameModel curr = mainView.getMainPanel().getSelectedGame();
        if (curr != null && !curr.isClosed()) {
            curr.closeGame();
            UpdateGamesController.getInstance().updateGame(curr);
        }
    }
    
    /**
     * Set the type of OptionPane for the cancel game confirmation.
     * 
     * @param o
     *        the OptionPane to set
     */
    public void setCancelConfirm(OptionPane o) {
        this.cancelConfirm = o;
    }

    public void editGame() {
        // TODO Auto-generated method stub
        
    }
    
    public void startGame() {
        final GameModel curr = mainView.getMainPanel().getSelectedGame();
        if (curr != null && !curr.isStarted()) {
            curr.startGame();
            UpdateGamesController.getInstance().updateGame(curr);
        }
    }
}
