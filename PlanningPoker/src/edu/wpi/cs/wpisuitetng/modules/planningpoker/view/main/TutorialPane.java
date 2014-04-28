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
import java.awt.event.ComponentListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.TutorialPath;
import javax.swing.JButton;

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
    private TutorialPath path;
    private int currentItemIndex;
    private TutorialPath.PathItem currentItem;
    private Rectangle highlightArea;
    
    private ComponentListener currentItemCompListener = new ComponentListener() {
        @Override
        public void componentMoved(java.awt.event.ComponentEvent e) {
            getCurrCompBounds();
        };
        
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            getCurrCompBounds();
        };
        
        @Override
        public void componentHidden(java.awt.event.ComponentEvent e) {
            getCurrCompBounds();
        }
        
        @Override
        public void componentShown(java.awt.event.ComponentEvent e) {
            getCurrCompBounds();
        };
    };
    
    private TutorialPane() {
        
        JButton btnNext = new JButton("Next");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap(340, Short.MAX_VALUE)
                    .addComponent(btnNext)
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnNext)
                    .addContainerGap(262, Short.MAX_VALUE))
        );
        setLayout(groupLayout);
        window = null;
        path = null;
        currentItemIndex = 0;
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
        if (highlightArea != null) {
            g.setColor(new Color(240, 30, 30, 210));
            //            drawThickRect(g, highlightArea.x, highlightArea.y,
            //                    highlightArea.width, highlightArea.height, 3);
            drawTransparentRect(g, highlightArea.x, highlightArea.y,
                    highlightArea.width, highlightArea.height);
            
            g.setColor(new Color(255, 30, 30));
            g.setFont(getFont().deriveFont(Font.BOLD));
            g.drawString(currentItem.getLabel(), highlightArea.x
                    + highlightArea.width + 10, highlightArea.y
                    + highlightArea.height / 2 + g.getFontMetrics().getAscent()
                    / 2);
        }
    }
    
    private void drawTransparentRect(Graphics g, int x, int y, int w, int h) {
        Color previousColor = g.getColor(); // store previous color
        g.setColor(new Color(100, 100, 200, 50));
        g.fillRect(x, y, w, h);
        g.setColor(previousColor);
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
        for (Component comp : getComponents()) {
            if (comp.contains(SwingUtilities.convertPoint(this, x, y, comp))) {
                return true;
            }
        }
        return false;
    }
    
    public void setPath(TutorialPath path) {
        this.path = path;
        currentItemIndex = -1;
        currentItem = null;
        highlightArea = null;
        nextItem(null);
    }
    
    private void getCurrCompBounds() {
        if (currentItem != null) {
            if (currentItem.getComponent().isVisible()) {
                highlightArea = currentItem.getComponent().getBounds();
                Point compPos = currentItem.getComponent()
                        .getLocationOnScreen();
                Point pos = getLocationOnScreen();
                highlightArea.x = compPos.x - pos.x;
                highlightArea.y = compPos.y - pos.y;
            }
            else {
                highlightArea = null;
            }
        }
        repaint();
    }
    
    private void nextItem(Component changedComponent) {
        if (currentItem != null) {
            currentItem.getComponent().removeComponentListener(
                    currentItemCompListener);
        }
        
        if (path != null) {
            if (currentItemIndex < path.size()) {
                if (path.get(currentItemIndex + 1).setComponent(
                        changedComponent)) {
                    currentItemIndex++;
                    currentItem = path.get(currentItemIndex);
                    currentItem.setComponent(changedComponent);
                    currentItem.getComponent().addComponentListener(
                            currentItemCompListener);
                    
                    getCurrCompBounds();
                }
                else {
                    System.out
                            .println("next path item didn't like the changed component");
                }
            }
            else {
                currentItem = null;
                repaint();
            }
        }
    }
    
    public void fireGUIChanged(Component changedComponent) {
        nextItem(changedComponent);
    }
}
