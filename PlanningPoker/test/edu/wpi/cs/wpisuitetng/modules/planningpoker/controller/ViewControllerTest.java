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
 * 
 * @author Andrew
 * 
 */
public class ViewControllerTest {
    
    static PlanningPoker pp;
    static MainView mv;
    static ViewController vc;
    
    @BeforeClass
    static public void setUpBeforeClass() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        ViewControllerTest.pp = new PlanningPoker();
        ViewControllerTest.mv = new MainView();
        ViewControllerTest.vc = new ViewController(ViewControllerTest.mv,
                new ToolbarView());
    }
    
    @Test
    public void testAddNewGameTab() {
        int before = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.vc.addNewGameTab();
        Assert.assertEquals(before + 1, ViewControllerTest.mv.getTabCount());
        Assert.assertSame("New Game", ViewControllerTest.mv.getTitleAt(before));
    }
    
    @Test
    public void testSaveNewGame() {
        int count = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.vc.addNewGameTab();
        NewGamePanel ngp = (NewGamePanel) ViewControllerTest.mv
                .getComponentAt(count);
        ViewControllerTest.vc.saveNewGame(ngp);
        Assert.assertEquals(count, ViewControllerTest.mv.getTabCount());
    }
    
    @Test
    public void testUpdateGame() {
        int count = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, null));
        ViewControllerTest.vc.editGame();
        Assert.assertEquals("Edit Test",
                ViewControllerTest.mv.getTitleAt(count));
        NewGamePanel ngp = (NewGamePanel) ViewControllerTest.mv
                .getComponentAt(count);
        ViewControllerTest.vc.updateGame(new GameModel(), ngp);
        Assert.assertEquals(count, ViewControllerTest.mv.getTabCount());
    }
    
    @Test
    public void testCancelNewGame() {
        int count = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.vc.addNewGameTab();
        NewGamePanel ngp = (NewGamePanel) ViewControllerTest.mv
                .getComponentAt(count);
        ViewControllerTest.vc.setCancelConfirm(new YesMockOptionPane());
        ViewControllerTest.vc.cancelNewGame(ngp, false);
        Assert.assertEquals(count, ViewControllerTest.mv.getTabCount());
    }
    
    @Test
    public void testCancelEditGame() {
        int count = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, null));
        ViewControllerTest.vc.editGame();
        NewGamePanel ngp = (NewGamePanel) ViewControllerTest.mv
                .getComponentAt(count);
        ViewControllerTest.vc.setCancelConfirm(new YesMockOptionPane());
        ViewControllerTest.vc.cancelEditGame(ngp, false);
        Assert.assertEquals(count, ViewControllerTest.mv.getTabCount());
    }
    
    @Test
    public void testAddUserPrefsTab() {
        int before = ViewControllerTest.mv.getTabCount();
        ViewControllerTest.vc.addUserPrefsTab();
        Assert.assertEquals(before + 1, ViewControllerTest.mv.getTabCount());
        Assert.assertSame("Preferences",
                ViewControllerTest.mv.getTitleAt(before));
    }
    
    @Test
    public void testStartGame() {
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, GameModel.GameStatus.NEW));
        ViewControllerTest.vc.startGame();
        MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    @Test
    public void testEndEstimation() {
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, GameModel.GameStatus.NEW));
        ViewControllerTest.vc.endEstimation();
        MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    @Test
    public void testCloseGame() {
        ViewControllerTest.mv.getMainPanel().setSelectedGame(
                new GameModel("Test", "Test",
                        new ArrayList<GameRequirementModel>(), null, null,
                        null, GameModel.GameStatus.NEW));
        ViewControllerTest.vc.closeGame();
        MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
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
