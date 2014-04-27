package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.awt.Component;
import java.util.ArrayList;

public class TutorialPath extends ArrayList<TutorialPath.PathItem> {
    
    /**
     * 
     */
    private static final long serialVersionUID = 9065382263997689976L;
    
    public static class PathItem {
        public Component component;
        public String label;
        
        public PathItem(Component component, String label) {
            this.component = component;
            this.label = label;
        }
    }
    
}
