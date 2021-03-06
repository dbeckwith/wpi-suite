/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.DefaultOptionPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.OptionPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.RequirementsListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.DocumentationPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ClosableTabComponent;

/**
 * This controller is used to control GUI display
 * 
 * @author Team 9
 * @version 1.0
 */
public class ViewController {
    
    private final MainView mainView;
    private final ToolbarView toolbar;
    private OptionPane cancelConfirm;
    
    private ActionListener newGameCallback = null;
    
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
        cancelConfirm = new DefaultOptionPane();
        
    }
    
    /**
     * Adds a new tab for a new game
     */
    public void addNewGameTab() {
        final NewGamePanel editGame = new NewGamePanel();
        mainView.addTab("New Game", editGame);
        mainView.setSelectedComponent(editGame);
        
        mainView.setTabComponentAt(mainView.indexOfComponent(editGame), new ClosableTabComponent(
                mainView) {
            private static final long serialVersionUID = 7088866301855075603L;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelNewGame(editGame, true);
            }
        });
        
        if (newGameCallback != null) {
            editGame.setNewGameCallback(newGameCallback);
            newGameCallback = null;
        }
        
    }
    
    /**
     * Adds a user preferences tab
     */
    public void addUserPrefsTab() {
        final UserPreferencesPanel prefsPanel = UserPreferencesPanel.getPanel();
        mainView.addTab("Preferences", prefsPanel);
        mainView.setSelectedComponent(prefsPanel);
        
        mainView.setTabComponentAt(mainView.indexOfComponent(prefsPanel), new ClosableTabComponent(
                mainView) {
            private static final long serialVersionUID = 3668078500346186662L;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.removeTabAt(mainView.indexOfComponent(prefsPanel));
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
        
        final GameModel newGame = new GameModel(e.getName(), e.getDescription(),
                e.getRequirements(), e.getDeck(), e.getEndDate(), GameStatus.NEW, ConfigManager
                        .getConfig().getUserName());
        
        RequirementsListModel.getInstance().removeListListener(
                e.getNewGameRequirementsPanel().getRequirementsListObserver());
        mainView.removeTabAt(mainView.indexOfComponent(e));
        mainView.setSelectedIndex(0);
        
        GameListModel.getInstance().addGame(newGame);
        
        JTree theTree = mainView.getMainPanel().getTree();
        DefaultTreeModel treeModel = (DefaultTreeModel) theTree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) theTree.getModel().getRoot();
        
        @SuppressWarnings("rawtypes")
        Enumeration treeEnum = rootNode.depthFirstEnumeration();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode();
        while (treeEnum.hasMoreElements()) {
            node = (DefaultMutableTreeNode) treeEnum.nextElement();
            if (node.getUserObject() != null && node.getUserObject() instanceof GameModel) {
                if (((GameModel) node.getUserObject()).equals(newGame)) {
                    theTree.setSelectionPath(new TreePath(treeModel.getPathToRoot(node)));
                    break;
                }
            }
        }
        
        AddGameController.getInstance().addGame(newGame);
    }
    
    /**
     * Updates a game model based in the information in the NewGamePanel
     * 
     * @param game
     *        the game to be updated
     * @param e
     *        the panel used to update the game
     */
    public void updateGame(GameModel game, NewGamePanel e) {
        final DeckModel d = e.getDeck();
        final GameModel newGame = new GameModel(e.getName(), e.getDescription(),
                e.getRequirements(), d, e.getEndDate(), GameStatus.NEW, ConfigManager.getConfig()
                        .getUserName());
        game.editCopyFrom(newGame);
        UpdateGamesController.getInstance().updateGame(game);
        RequirementsListModel.getInstance().removeListListener(
                e.getNewGameRequirementsPanel().getRequirementsListObserver());
        mainView.removeTabAt(mainView.indexOfComponent(e));
        mainView.setSelectedIndex(0);
    }
    
    
    /**
     * Cancels creation of a new game
     * 
     * @param e
     *        The NewGamePanel to cancel
     * @param hasChanged
     *        flag to indicate if the game has changed
     */
    public void cancelNewGame(NewGamePanel e, boolean hasChanged) {
        
        if (hasChanged) {
            final int result = cancelConfirm.showConfirmDialog(e,
                    "Are you sure you want to cancel creation of this game?", "Cancel Game",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                RequirementsListModel.getInstance().removeListListener(
                        e.getNewGameRequirementsPanel().getRequirementsListObserver());
                mainView.removeTabAt(mainView.indexOfComponent(e));
            }
        }
        else {
            RequirementsListModel.getInstance().removeListListener(
                    e.getNewGameRequirementsPanel().getRequirementsListObserver());
            mainView.removeTabAt(mainView.indexOfComponent(e));
        }
    }
    
    
    /**
     * Cancels the editing of a game
     * 
     * @param e
     *        the NewGamePanel to cancel
     * @param hasChanged
     *        flag to indicate if the game has changed
     */
    public void cancelEditGame(NewGamePanel e, boolean hasChanged) {
        
        if (hasChanged) {
            final int result = cancelConfirm.showConfirmDialog(e,
                    "Are you sure you would like cancel editing this game? (changes will "
                            + "not be saved)", "Cancel Edit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                RequirementsListModel.getInstance().removeListListener(
                        e.getNewGameRequirementsPanel().getRequirementsListObserver());
                mainView.removeTabAt(mainView.indexOfComponent(e));
            }
        }
        else {
            RequirementsListModel.getInstance().removeListListener(
                    e.getNewGameRequirementsPanel().getRequirementsListObserver());
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
    
    public ToolbarView getToolbarView() {
        return toolbar;
    }
    
    /**
     * Called to manually end estimation on the currently selected game
     */
    public void endEstimation() {
        final GameModel curr = mainView.getMainPanel().getSelectedGame();
        if (curr != null && !curr.isEnded()) {
            curr.setEnded(true);
            UpdateGamesController.getInstance().updateGame(curr);
            EmailController.getInstance().sendGameEndNotifications(curr);
        }
    }
    
    /**
     * displays the admin buttons if the user is the owner
     * 
     * @param game
     *        the currently displayed game
     */
    public void displayAdmin(GameModel game) {
        if (game != null && game.getOwner().equals(ConfigManager.getConfig().getUserName())
                && !game.isClosed() && mainView.getSelectedIndex() == 0) {
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
        cancelConfirm = o;
    }
    
    /**
     * Opens a new tab to edit the currently selected game
     */
    public void editGame() {
        final GameModel current = mainView.getMainPanel().getSelectedGame();
        final NewGamePanel editGame = new NewGamePanel(current);
        mainView.addTab("Edit " + current.getName(), editGame);
        mainView.setSelectedComponent(editGame);
        
        mainView.setTabComponentAt(mainView.indexOfComponent(editGame), new ClosableTabComponent(
                mainView) {
            private static final long serialVersionUID = 7088866301855075603L;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                cancelEditGame(editGame, editGame.getHasChanged());
            }
        });
    }
    
    /**
     * starts a game so it allows uses to vote its requirements
     */
    public void startGame() {
        final GameModel curr = mainView.getMainPanel().getSelectedGame();
        if (curr.deadlinePassed()) {
            final Object[] options = { "OK" };
            JOptionPane.showOptionDialog(mainView,
                    "Game deadline has passed, edit game deadline before starting game.",
                    "Deadline Passed Error", JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        }
        else if (curr != null && !curr.isStarted()) {
            curr.startGame();
            UpdateGamesController.getInstance().updateGame(curr);
        }
        
    }
    
    
    /**
     * @return true if admin controls are visible
     */
    public boolean getAdminVisibility() { // $codepro.audit.disable booleanMethodNamingConvention
        return showAdmin;
    }
    
    /**
     * Adds a help documentation panel
     */
    public void addDocumentationPanel() {
        final DocumentationPanel docPanel = DocumentationPanel.getPanel();
        mainView.addTab("Planning Poker Help", docPanel);
        mainView.setSelectedComponent(docPanel);
        
        mainView.setTabComponentAt(mainView.indexOfComponent(docPanel), new ClosableTabComponent(
                mainView) {
            private static final long serialVersionUID = -1818991807577187941L;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.removeTabAt(mainView.indexOfComponent(docPanel));
            }
        });
        
    }
    
    /**
     * Sets a callback to recieve a single event when the next game is created
     * 
     * @param e
     */
    public void setNewGameCallback(ActionListener a) {
        newGameCallback = a;
    }
    
    
}
