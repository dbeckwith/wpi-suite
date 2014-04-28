package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TutorialComponentsTest {
    
    @Test
    public void testAddNamedTutorial() {
        // creating a tutorial adds it to TutorialComponents
        TutorialPath tutorial = new TutorialPath("namedTutorial");
        
        // can't add it a second time, so this should return false
        assertFalse(TutorialComponents.getInstance().addTutorial(tutorial));
        
        // assert that it's been stored
        assertNotNull(TutorialComponents.getInstance().getTutorial("namedTutorial"));
        
        // make sure that the stored tutorial is the same as the one created
        assertEquals(tutorial, TutorialComponents.getInstance().getTutorial("namedTutorial"));
    }
    
}
