package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.ViewController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;

public class PlanningPokerTest {
    
    @Test
    public void testElements() {
        PlanningPoker poker = new PlanningPoker();
        ViewController vc = PlanningPoker.getViewController();
        assertSame("PlanningPoker", poker.getName());
        assertSame("PlanningPoker", poker.getTabs().get(0).getName());
        MainView mv = vc.getMainView();
        int before = mv.getTabCount();
        vc.addNewGameTab();
        Assert.assertEquals(before + 1, mv.getTabCount());
    }
    
}
