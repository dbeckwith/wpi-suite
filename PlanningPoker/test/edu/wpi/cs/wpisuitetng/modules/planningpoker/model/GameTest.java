
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel.GameType;

/**
 * @author Lukas, Dan
 *
 */
public class GameTest {
	
    @Test
    public void TestRequirementEndsAfterDeadline() {
    	ArrayList<User> users = new ArrayList<User>();
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	users.add(kevin);
    	ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
        GameModel testgame = new GameModel(1, "Test Game", "something", requirements, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
        assertTrue(testgame.isEnded());
    }
    
    @Test
    public void TestRequirementNotCompleteBeforeDeadline() {
    	ArrayList<User> users = new ArrayList<User>();
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	users.add(kevin);
    	ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
        GameModel testgame = new GameModel(2, "Test Game", "something", requirements, new Date(System.currentTimeMillis() + 100000000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
        assertFalse(testgame.isEnded());
    }
    
    @Test
    public void TestNotAllUsersVoted() {
    	ArrayList<User> users = new ArrayList<User>();
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	users.add(kevin);
    	ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
    	GameModel testgame = new GameModel(3, "Test Game", "something", requirements, new Date(System.currentTimeMillis() + 100000000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
    	assertFalse(testgame.isEnded());
    }
   
    @Test
    public void TestAllVoted(){
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	ArrayList<User> users = new ArrayList<User>();
    	users.add(kevin);
    	Estimate estimate1 = new Estimate(kevin, 5);
    	ArrayList<Estimate> estimates = new ArrayList<Estimate>();
    	estimates.add(estimate1);
    	GameRequirementModel testRequirement = new GameRequirementModel( 1, "Test Requirement", "something", "type?", estimates);
    	ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
    	requirements.add(testRequirement);
    	GameModel testgame = new GameModel(3, "Test Game", "soemthing", requirements, new Date(System.currentTimeMillis() + 10000000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
    	assertTrue(testRequirement.allVoted(testgame));
    	assertTrue(testgame.isEnded());
    	
    }
}
