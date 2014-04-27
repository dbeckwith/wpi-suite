package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import javax.swing.JPanel;

import org.junit.Test;

import static org.junit.Assert.*;

public class TutorialComponentsTest {
    
    @Test
    public void testAddNamedTutorial() {
        TutorialPath tutorial = new TutorialPath("namedTutorial");
        assertNotNull(TutorialComponents.getInstance().getTutorial("namedTutorial"));
    }
    
    @Test
    public void testAddTutorialWithPanel() {
        JPanel panel = new JPanel();
        TutorialPath tutorial = new TutorialPath("panelTutorial", panel);
        assertNotNull(TutorialComponents.getInstance().getTutorial("panelTutorial"));
        assertNotNull(TutorialComponents.getInstance().getTutorial(panel));
    }
    
}
