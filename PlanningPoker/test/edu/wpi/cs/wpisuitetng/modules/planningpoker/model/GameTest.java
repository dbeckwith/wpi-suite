
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
    	ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
        GameModel testgame = new GameModel(1, "Test Game", "something", requirements, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING);
        assertTrue(testgame.isEnded());
    }
    
    @Test
    public void TestRequirementNotCompleteBeforeDeadline() {
    	ArrayList<GameRequirementModel> requirements = new ArrayList<GameRequirementModel>();
        GameModel testgame = new GameModel(2, "Test Game", "something", requirements, new Date(System.currentTimeMillis() + 100000000), GameType.DISTRIBUTED, GameStatus.PENDING);
        assertFalse(testgame.isEnded());
    }
}
