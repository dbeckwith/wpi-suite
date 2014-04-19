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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
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
        pp = new PlanningPoker();
        mv = new MainView();
        vc = new ViewController(mv, new ToolbarView());
    }
    
    @Test
    public void testAddNewGameTab() {
        int before = mv.getTabCount();
        vc.addNewGameTab();
        Assert.assertEquals(before + 1, mv.getTabCount());
        Assert.assertSame("New Game", mv.getTitleAt(before));
    }
    
    @Test
    public void testSaveNewGame() {
        int count = mv.getTabCount();
        vc.addNewGameTab();
        NewGamePanel ngp = (NewGamePanel) mv.getComponentAt(count);
        vc.saveNewGame(ngp);
        Assert.assertEquals(count, mv.getTabCount());
    }
    
    @Test
    public void testUpdateGame() {
        int count = mv.getTabCount();
        mv.getMainPanel().setSelectedGame(new GameModel("Test", "Test", new ArrayList<GameRequirementModel>(), null, null, null, null));
        vc.editGame();
        Assert.assertEquals("Edit Test", mv.getTitleAt(count));
        NewGamePanel ngp = (NewGamePanel) mv.getComponentAt(count);
        vc.updateGame(new GameModel(), ngp);
        Assert.assertEquals(count, mv.getTabCount());
    }
    
    @Test
    public void testCancelNewGame() {
        int count = mv.getTabCount();
        vc.addNewGameTab();
        NewGamePanel ngp = (NewGamePanel) mv.getComponentAt(count);
        vc.setCancelConfirm(new YesMockOptionPane());
        vc.cancelNewGame(ngp);
        Assert.assertEquals(count, mv.getTabCount());
    }
    
    @Test
    public void testCancelEditGame() {
        int count = mv.getTabCount();
        mv.getMainPanel().setSelectedGame(new GameModel("Test", "Test", new ArrayList<GameRequirementModel>(), null, null, null, null));
        vc.editGame();
        NewGamePanel ngp = (NewGamePanel) mv.getComponentAt(count);
        vc.setCancelConfirm(new YesMockOptionPane());
        vc.cancelEditGame(ngp);
        Assert.assertEquals(count, mv.getTabCount());
    }
    
    @Test
    public void testAddUserPrefsTab() {
        int before = mv.getTabCount();
        vc.addUserPrefsTab();
        Assert.assertEquals(before + 1, mv.getTabCount());
        Assert.assertSame("Preferences", mv.getTitleAt(before));
    }
    
    @Test
    public void testStartGame() {
        mv.getMainPanel().setSelectedGame(new GameModel("Test", "Test", new ArrayList<GameRequirementModel>(), null, null, null, GameModel.GameStatus.NEW));
        vc.startGame();
        MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    @Test
    public void testEndEstimation() {
        mv.getMainPanel().setSelectedGame(new GameModel("Test", "Test", new ArrayList<GameRequirementModel>(), null, null, null, GameModel.GameStatus.NEW));
        vc.endEstimation();
        MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    @Test
    public void testCloseGame() {
        mv.getMainPanel().setSelectedGame(new GameModel("Test", "Test", new ArrayList<GameRequirementModel>(), null, null, null, GameModel.GameStatus.NEW));
        vc.closeGame();
        MockRequest request = ((MockNetwork) Network.getInstance())
                .getLastRequestMade();
        if (request == null) {
            Assert.fail("request not sent");
        }
        Assert.assertTrue(request.isSent());
    }
    
    @Test
    public void testDisplayAdmin() {
        vc.displayAdmin(new GameModel("", "", new ArrayList<GameRequirementModel>(), null, null, GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.NEW, ""));
        Assert.assertTrue(vc.getAdminVisibility());
        vc.displayAdmin(new GameModel("", "", new ArrayList<GameRequirementModel>(), null, null, GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.NEW, "Me"));
        Assert.assertFalse(vc.getAdminVisibility());
        vc.displayAdmin(new GameModel("", "", new ArrayList<GameRequirementModel>(), null, null, GameModel.GameType.DISTRIBUTED, GameModel.GameStatus.COMPLETE, ""));
        Assert.assertTrue(vc.getAdminVisibility());
    }
}
