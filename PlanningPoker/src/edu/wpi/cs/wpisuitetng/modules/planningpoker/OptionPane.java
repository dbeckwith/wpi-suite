package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.Component;

public interface OptionPane {
    /**
     * @see JOptionPane#showConfirmDialog(Component, Object, String, int);
     */
    int showConfirmDialog(Component parentComponent, Object message,
            String title, int optionType);
}
