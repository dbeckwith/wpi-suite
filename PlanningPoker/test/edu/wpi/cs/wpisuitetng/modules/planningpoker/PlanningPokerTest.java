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
package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.ViewController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Tests the PlanningPoker class
 * 
 * @author Team 9
 * @version 1.0
 */
public class PlanningPokerTest {
    
    /**
     * Tests the elements of the planning poker class (mainview, toolbarview)
     */
    @Test
    public void testElements() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        final PlanningPoker poker = new PlanningPoker();
        final ViewController vc = PlanningPoker.getViewController();
        Assert.assertSame("PlanningPoker", poker.getName());
        Assert.assertSame("PlanningPoker", poker.getTabs().get(0).getName());
        final MainView mv = vc.getMainView();
        final int before = mv.getTabCount();
        vc.addNewGameTab();
        Assert.assertEquals(before + 1, mv.getTabCount());
    }
    
}
