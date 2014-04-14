package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.Component;

import javax.swing.JOptionPane;

public class DefaultOptionPane implements OptionPane {
    
    @Override
    public int showConfirmDialog(Component parentComponent, Object message,
            String title, int optionType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title,
                optionType);
    }
    
}
