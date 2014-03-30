
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
 * @author Lukas
 *
 */
public class GameTest {
	
    @Test
    public void TestRequirementEndsAfterDeadline() {
    	ArrayList<User> users = new ArrayList<User>();
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	users.add(kevin);
        GameModel testgame = new GameModel(1, "Test Game", "something", null, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
        assertTrue(testgame.isEnded());
    }
    
    @Test
    public void TestRequirementNotCompleteBeforeDeadline() {
    	ArrayList<User> users = new ArrayList<User>();
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	users.add(kevin);
        GameModel testgame = new GameModel(2, "Test Game", "something", null, new Date(System.currentTimeMillis() + 100000000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
        assertFalse(testgame.isEnded());
    }
    
    @Test
    public void TestNotAllUsersVoted() {
    	ArrayList<User> users = new ArrayList<User>();
    	User kevin = new User("Kevin", "kpmartin", "password", 1);
    	users.add(kevin);
    	GameModel testgame = new GameModel(3, "Test Game", "something", null, new Date(System.currentTimeMillis() + 100000000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
    	assertFalse(testgame.isEnded());
    }
    
    @Test
    public void TestAllUsersVoted() {
    	ArrayList<User> users = new ArrayList<User>();
    	GameModel testgame = new GameModel(3, "Test Game", "something", null, new Date(System.currentTimeMillis() + 100000000), GameType.DISTRIBUTED, GameStatus.PENDING, users);
    	assertFalse(testgame.isEnded());
    }
}
