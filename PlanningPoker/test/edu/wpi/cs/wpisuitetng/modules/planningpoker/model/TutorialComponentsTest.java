package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TutorialComponentsTest {
    
    @Test
    public void testAddNamedTutorial() {
        TutorialPath tutorial = new TutorialPath("namedTutorial");
        assertFalse(TutorialComponents.getInstance().addTutorial(tutorial));
        assertNotNull(TutorialComponents.getInstance().getTutorial("namedTutorial"));
    }
    
    @Test
    public void testAddTutorialWithPanel() {
        TutorialPath tutorial = new TutorialPath("panelTutorial");
        assertFalse(TutorialComponents.getInstance().addTutorial(tutorial));
        assertNotNull(TutorialComponents.getInstance().getTutorial("panelTutorial"));
    }
    
}
