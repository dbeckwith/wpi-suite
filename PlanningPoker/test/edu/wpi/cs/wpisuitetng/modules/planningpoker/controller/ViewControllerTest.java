package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
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
    
    @Test
    public void testAddNewGameTab() {
        MainView mv = new MainView();
        ViewController vc = new ViewController(mv, new ToolbarView());
        int before = mv.getTabCount();
        vc.addNewGameTab();
        Assert.assertEquals(before + 1, mv.getTabCount());
        Assert.assertSame("New Game", mv.getTitleAt(before));
    }
    
    @Test
    public void testSaveNewGame() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        MainView mv = new MainView();
        ViewController vc = new ViewController(mv, new ToolbarView());
        int count = mv.getTabCount();
        vc.addNewGameTab();
        NewGamePanel ngp = (NewGamePanel) mv.getComponentAt(count);
        vc.saveNewGame(ngp);
        Assert.assertEquals(count, mv.getTabCount());
    }
    
    @Test
    public void testCancelNewGame() {
        MainView mv = new MainView();
        ViewController vc = new ViewController(mv, new ToolbarView());
        int count = mv.getTabCount();
        vc.addNewGameTab();
        NewGamePanel ngp = (NewGamePanel) mv.getComponentAt(count);
        vc.cancelNewGame(ngp);
        Assert.assertEquals(count, mv.getTabCount());
    }
}
