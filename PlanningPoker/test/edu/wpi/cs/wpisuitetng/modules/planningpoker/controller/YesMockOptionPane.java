package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import java.awt.Component;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.OptionPane;

public class YesMockOptionPane implements OptionPane {
    
    @Override
    public int showConfirmDialog(Component parentComponent, Object message,
            String title, int optionType) {
        return JOptionPane.YES_OPTION;
    }
    
}
