package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.MockRequest;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class UpdateGamesControllerTest {
    
    @Test
    public void test() {
        Network.initNetwork(new MockNetwork());
        Network.getInstance().setDefaultNetworkConfiguration(
                new NetworkConfiguration("http://wpisuitetng"));
        UpdateGamesController ugc = UpdateGamesController.getInstance();
        ugc.updateGame(GameListModel.getInstance());
        MockRequest request = ((MockNetwork)Network.getInstance()).getLastRequestMade();
        if(request == null) {
            fail("request not sent");
        }
        assertTrue(request.isSent());
    }
    
}
