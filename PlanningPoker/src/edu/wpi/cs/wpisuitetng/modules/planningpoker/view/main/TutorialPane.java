/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
    
    /**
     * 
     * @return
     */
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
    
    /**
     * 
     * @param comp
     */
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
            g.setColor(new Color(200, 30, 30, 210));
            drawThickRect(g, highlightArea.x, highlightArea.y,
                    highlightArea.width, highlightArea.height, 3);
            
            g.setColor(new Color(230, 30, 30));
            g.setFont(getFont().deriveFont(Font.BOLD));
            g.drawString(highlightLabel, highlightArea.x + highlightArea.width
                    + 10, highlightArea.y + highlightArea.height / 2
                    + g.getFontMetrics().getAscent() / 2);
        }
    }
    
    private void drawThickRect(Graphics g, int x, int y, int w, int h,
            int thickness) {
        for (int i = Math.round(-thickness / 2f); i <= Math
                .round(thickness / 2f); i++) {
            g.drawRect(x + i, y + i, w - i * 2, h - i * 2);
        }
    }
    
    @Override
    public boolean contains(int x, int y) {
        // overwrite contains so that the mouse cursor can change like normal
        // when it hovers over components below the glass pane
        return false;
    }
    
    /**
     * 
     */
    public void clear() {
        highlightArea = null;
        highlightLabel = null;
        repaint();
    }
    
    /**
     * 
     * @param highlightedComponent
     * @param label
     */
    public void setHighlightArea(Component highlightedComponent, String label) {
        if (window != null) {
            highlightArea = highlightedComponent.getBounds();
            final Point compPos = highlightedComponent.getLocationOnScreen();
            final Point pos = getLocationOnScreen();
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
