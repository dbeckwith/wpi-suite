package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * 
 * A class for a list of components that the user can be guided through as a
 * tutorial.
 * 
 * @author Team 9
 * @version Apr 27, 2014
 */
public class TutorialPath extends ArrayList<TutorialPath.PathItem> {
    
    /**
     * 
     */
    private static final long serialVersionUID = 9065382263997689976L;
    
    /**
     * 
     * An abstract class representing one stop in the tutorial.<br>
     * To use this class, add an anonymous class of this type to a TutorialPath,
     * overriding the getComponent() method to get the component that the
     * user should be pointed to. The reason for this is that the component may
     * not
     * exist when the path item is instantiated, and getComponent() will only be
     * called when the user gets to that point in the tutorial, at which point
     * the component should exist and be visible.
     * 
     * @author Team 9
     * @version Apr 27, 2014
     */
    public static abstract class PathItem {
        private String label;
        
        public PathItem(String label) {
            this.label = label;
        }
        
        public abstract Component getComponent();
        
        public String getLabel() {
            return label;
        }
    }
    
    /**
     * 
     * A convenience subclass of PathItem.<br>
     * If the target component for the PathItem will always exist and be
     * visible,
     * it is easier to just pass it in as a reference to this class rather than
     * make an anonymous subclass of PathItem.
     * 
     * @author Team 9
     * @version Apr 27, 2014
     */
    public static class StaticPathItem extends PathItem {
        private Component comp;
        
        public StaticPathItem(Component comp, String label) {
            super(label);
            this.comp = comp;
        }
        
        public Component getComponent() {
            return comp;
        }
    }
    
    private String name;
    private JPanel panel;
    
    /**
     * Creates a new tutorial path with the given name.
     */
    public TutorialPath(String name) {
        this(name, null);
    }
    
    /**
     * Creates a new tutorial path with the given name and associated with the
     * given JPanel.
     */
    public TutorialPath(String name, JPanel panel) {
        this.name = name;
        this.panel = panel;
        TutorialComponents.getInstance().addTutorial(this);
    }
    
    /**
     * Gets the name of this tutorial path.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the JPanel associated with this tutorial. May be null.
     */
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((panel == null) ? 0 : panel.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        TutorialPath other = (TutorialPath) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (panel == null) {
            if (other.panel != null)
                return false;
        }
        else if (!panel.equals(other.panel))
            return false;
        return true;
    }
}
