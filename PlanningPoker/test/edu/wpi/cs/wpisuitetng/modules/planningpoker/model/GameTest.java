
package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

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
        GameModel testgame = new GameModel(1, "Test Game", "something", null, new Date(System.currentTimeMillis() - 100000), GameType.DISTRIBUTED, GameStatus.PENDING);
        assertTrue(testgame.isEnded());
    }
    
    @Test
    public void TestRequirementNotCompleteBeforeDeadline() {
        GameModel testgame = new GameModel(2, "Test Game", "something", null, new Date(System.currentTimeMillis() + 100000000), GameType.DISTRIBUTED, GameStatus.PENDING);
        assertFalse(testgame.isEnded());
    }
    
}
