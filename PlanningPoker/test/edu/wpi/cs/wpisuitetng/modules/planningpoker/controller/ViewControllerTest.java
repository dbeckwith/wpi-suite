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

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.PlanningPoker;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockRequest;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.NewGamePanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the ViewController class
 * 
 * @author Team 9
 * @version 1.0
 */
public class ViewControllerTest {
    
    static PlanningPoker pp = new PlanningPoker();
    static MainView mv = new MainView();
    static ViewController vc = new ViewController(ViewControllerTest.mv,
            new ToolbarView());
    
    /**
     * Initializes the mock network
     */
    @BeforeClass
    static public void prepare() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
    }
    
    /**
     * Ensures that the tab count changes correctly when a new game tab is opened.
     */
    @Test
    public void testAddNewGameTab() {
        final int before = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.vc.addNewGameTab();
        Assert.assertEquals(before + 1, ViewControllerTest.mv.getTabCount());
        Assert.assertSame("New Game", ViewControllerTest.mv.getTitleAt(before));
    }
    
    /**
     * Ensures that the tab count changes correctly when a new game tab is closed.
     */
    @Test
    public void testSaveNewGame() {
        final int count = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.vc.addNewGameTab();
        final NewGamePanel ngp = (NewGamePanel) ViewControllerTest.mv
                .getComponentAt(count);
        ViewControllerTest.vc.saveNewGame(ngp);
        Assert.assertEquals(count, ViewControllerTest.mv.getTabCount());
    }
    
    /**
     * Ensures that the tab count changes correctly when finished updating a game
     */
    @Test
    public void testUpdateGame() {
        final int count = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, null));
        ViewControllerTest.vc.editGame();
        Assert.assertEquals("Edit Test",
                ViewControllerTest.mv.getTitleAt(count));
        final NewGamePanel ngp = (NewGamePanel) ViewControllerTest.mv
                .getComponentAt(count);
        ViewControllerTest.vc.updateGame(new GameModel(), ngp);
        Assert.assertEquals(count, ViewControllerTest.mv.getTabCount());
    }
    
    /**
     * Ensures the tab count changes when creating a new game is cancelled
     */
    @Test
    public void testCancelNewGame() {
        final int count = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.vc.addNewGameTab();
        final NewGamePanel ngp = (NewGamePanel) ViewControllerTest.mv
                .getComponentAt(count);
        ViewControllerTest.vc.setCancelConfirm(new YesMockOptionPane());
        ViewControllerTest.vc.cancelNewGame(ngp, false);
        Assert.assertEquals(count, ViewControllerTest.mv.getTabCount());
    }
    
    /**
     * Ensures tab count changes with user cancels editing a game
     */
    @Test
    public void testCancelEditGame() {
        final int count = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, null));
        ViewControllerTest.vc.editGame();
        final NewGamePanel ngp = (NewGamePanel) ViewControllerTest.mv
                .getComponentAt(count);
        ViewControllerTest.vc.setCancelConfirm(new YesMockOptionPane());
        ViewControllerTest.vc.cancelEditGame(ngp, false);
        Assert.assertEquals(count, ViewControllerTest.mv.getTabCount());
    }
    
    /**
     * Ensures tab count increments when user opens the preferences tab
     */
    @Test
    public void testAddUserPrefsTab() {
        final int before = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.vc.addUserPrefsTab();
        Assert.assertEquals(before + 1, ViewControllerTest.mv.getTabCount());
        Assert.assertSame("Preferences",
                ViewControllerTest.mv.getTitleAt(before));
    }
    
    /**
     * Ensures startGame method creates a network request
     */
    @Test
    public void testStartGame() {
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, GameModel.GameStatus.NEW));
        ViewControllerTest.vc.startGame();
        final MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    /**
     * Ensures endEstimation creates a network request
     */
    @Test
    public void testEndEstimation() {
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, GameModel.GameStatus.NEW));
        ViewControllerTest.vc.endEstimation();
        final MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    /**
     * Ensures closeGame makes a network request
     */
    @Test
    public void testCloseGame() {
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, GameModel.GameStatus.NEW));
        ViewControllerTest.vc.closeGame();
        final MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    /**
     * Tests that displayAdmin correctly changes the visibility of the admin controls
     */
    @Test
    public void testDisplayAdmin() {
        ViewControllerTest.vc.displayAdmin(new GameModel("", "",
                new ArrayList<GameRequirementModel>(), null, null,
                GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.NEW, ""));
        Assert.assertTrue(ViewControllerTest.vc.getAdminVisibility());
        ViewControllerTest.vc
                .displayAdmin(new GameModel("", "",
                        new ArrayList<GameRequirementModel>(), null, null,
                        GameModel.GameType.DISTRIBUTED,
                        GameModel.GameStatus.NEW, "Me"));
        Assert.assertFalse(ViewControllerTest.vc.getAdminVisibility());
        ViewControllerTest.vc.displayAdmin(new GameModel("", "",
                new ArrayList<GameRequirementModel>(), null, null,
                GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.COMPLETE,
                ""));
        Assert.assertTrue(ViewControllerTest.vc.getAdminVisibility());
    }
}
