package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.ViewController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;

public class PlanningPokerTest {
    
    @Test
    public void testElements() {
        PlanningPoker poker = new PlanningPoker();
        ViewController vc = PlanningPoker.getViewController();
        Assert.assertSame("PlanningPoker", poker.getName());
        Assert.assertSame("PlanningPoker", poker.getTabs().get(0).getName());
        MainView mv = vc.getMainView();
        int before = mv.getTabCount();
        vc.addNewGameTab();
        Assert.assertEquals(before + 1, mv.getTabCount());
    }
    
}
