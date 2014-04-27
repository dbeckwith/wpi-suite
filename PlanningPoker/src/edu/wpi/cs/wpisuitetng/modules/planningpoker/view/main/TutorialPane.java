/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * 
 * A glass pane that goes over the rest of the GUI for the interactive tutorial.
 * 
 * @author Team 9
 * @version Apr 26, 2014
 */
public class TutorialPane extends JComponent {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4832992249337947413L;
    private static TutorialPane instance = null;
    
    public static TutorialPane getInstance() {
        if (TutorialPane.instance == null) {
            TutorialPane.instance = new TutorialPane();
        }
        
        return TutorialPane.instance;
    }
    
    private JFrame window;
    private Rectangle highlightArea;
    private String highlightLabel;
    
    private TutorialPane() {
        window = null;
        highlightArea = null;
        highlightLabel = null;
    }
    
    public void install(Component comp) {
        window = null;
        while (comp != null && !(comp instanceof JFrame)) {
            comp = comp.getParent();
        }
        if (comp != null) {
            window = (JFrame) comp;
            window.setGlassPane(this);
            setVisible(true);
            System.out.println("Tutorial pane installed successfully!");
        }
        else {
            System.err.println("Tutorial pane installed unsuccessfully!");
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (highlightArea != null) {
            g.drawRect(highlightArea.x, highlightArea.y, highlightArea.width,
                    highlightArea.height);
            g.drawString(highlightLabel, highlightArea.x + highlightArea.width
                    + 10, highlightArea.y + highlightArea.height);
        }
    }
    
    public void clear() {
        highlightArea = null;
        highlightLabel = null;
        repaint();
    }
    
    public void setHighlightArea(Component highlightedComponent, String label) {
        if (window != null) {
            highlightArea = highlightedComponent.getBounds();
            Point compPos = highlightedComponent.getLocationOnScreen();
            Point pos = getLocationOnScreen();
            System.out.println(highlightArea);
            System.out.println(compPos);
            System.out.println(pos);
            highlightArea.x = compPos.x - pos.x;
            highlightArea.y = compPos.y - pos.y;
            System.out.println(highlightArea);
            highlightLabel = label;
            repaint();
        }
        else {
            System.err.println("Tutorial pane not installed!");
        }
    }
    
}
