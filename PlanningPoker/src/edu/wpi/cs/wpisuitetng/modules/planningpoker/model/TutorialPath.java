package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main.TutorialPane;

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
     * overriding the grabComponent() method to get the component that the
     * user should be pointed to. The reason for this is that the component may
     * not exist when the path item is instantiated, and grabComponent() will
     * only be called when the user gets to that point in the tutorial, at which
     * point the component should exist and be visible.<br>
     * In order for PathItems to be notified of GUI changes, any time a GUI
     * element that might relate to a tutorial changes in a way that might cause
     * the tutorial to advance, it should call
     * {@link TutorialPane#fireGUIChanged(Component)} on
     * {@link TutorialPane#getInstance()}.
     * 
     * @author Team 9
     * @version Apr 27, 2014
     */
    public static abstract class PathItem {
        private String label;
        private Component comp;
        
        public PathItem(String label) {
            this.label = label;
            comp = null;
        }
        
        /**
         * 
         * This method will be called when the TutorialPane is notified of a
         * change in the GUI, and will be passed the component that changed. The
         * general contract of this method is that if the changed component was
         * the one that is or contains the component that should be highlighted
         * in this path item, it should cast the changed component and return
         * the component to be highlighted. If the changed component was not the
         * one you wanted, return null instead, indicating that the tutorial
         * should not move on and the changed component should be ignored.
         * 
         * @param changedComponent
         * @return
         */
        protected abstract Component grabComponent(Component changedComponent);
        
        public boolean setComponent(Component changedComponent) {
            return (comp = grabComponent(changedComponent)) != null;
        }
        
        public Component getComponent() {
            return comp;
        }
        
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
        
        public Component grabComponent(Component changedComponent) {
            return comp;
        }
    }
    
    private String name;
    
    /**
     * Creates a new tutorial path with the given name.
     */
    public TutorialPath(String name) {
        this.name = name;
        TutorialComponents.getInstance().addTutorial(this);
    }
    
    /**
     * Gets the name of this tutorial path.
     */
    public String getName() {
        return name;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        return true;
    }
}
