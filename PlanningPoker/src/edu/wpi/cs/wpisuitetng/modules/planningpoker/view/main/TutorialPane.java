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
import java.awt.event.ComponentAdapter;
import java.awt.event.FocusAdapter;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JFrame;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.TutorialPath;

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
    private Iterator<TutorialPath.PathItem> pathIter;
    private TutorialPath.PathItem currentItem;
    private Rectangle highlightArea;
    
    private FocusAdapter nextPathItemListener = new FocusAdapter() {
        
        @Override
        public void focusGained(java.awt.event.FocusEvent e) {
            nextItem();
        };
    };
    
    private ComponentAdapter currentItemCompListener = new ComponentAdapter() {
        @Override
        public void componentMoved(java.awt.event.ComponentEvent e) {
            getCurrCompBounds();
        };
        
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            getCurrCompBounds();
        };
    };
    
    private TutorialPane() {
        window = null;
        pathIter = null;
        currentItem = null;
        highlightArea = null;
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
        if (currentItem != null) {
            g.setColor(new Color(240, 30, 30, 210));
            drawThickRect(g, highlightArea.x, highlightArea.y,
                    highlightArea.width, highlightArea.height, 3);
            
            g.setColor(new Color(255, 30, 30));
            g.setFont(getFont().deriveFont(Font.BOLD));
            g.drawString(currentItem.label, highlightArea.x
                    + highlightArea.width + 10, highlightArea.y
                    + highlightArea.height / 2 + g.getFontMetrics().getAscent()
                    / 2);
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
    
    public void setPath(TutorialPath path) {
        pathIter = path.iterator();
        currentItem = null;
        highlightArea = null;
        nextItem();
    }
    
    private void getCurrCompBounds() {
        if (currentItem != null) {
            highlightArea = currentItem.component.getBounds();
            Point compPos = currentItem.component.getLocationOnScreen();
            Point pos = getLocationOnScreen();
            highlightArea.x = compPos.x - pos.x;
            highlightArea.y = compPos.y - pos.y;
        }
        repaint();
    }
    
    public void nextItem() {
        if (currentItem != null) {
            currentItem.component.removeFocusListener(nextPathItemListener);
            currentItem.component
                    .removeComponentListener(currentItemCompListener);
        }
        
        if (pathIter.hasNext()) {
            currentItem = pathIter.next();
            currentItem.component.addFocusListener(nextPathItemListener);
            currentItem.component.addComponentListener(currentItemCompListener);
            
            getCurrCompBounds();
        }
        else {
            currentItem = null;
            repaint();
        }
    }
    
}
