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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * 
 * A glass pane that goes over the rest of the GUI for the interactive tutorial.
 * 
 * @author Team 9
 * @version Apr 26, 2014
 */
public class TutorialPane extends JComponent implements ActionListener, MouseMotionListener {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4832992249337947413L;
    private static TutorialPane instance = null;
    
    /**
     * 
     * @return TutorialPane.instance
     */
    public static TutorialPane getInstance() {
        if (TutorialPane.instance == null) {
            TutorialPane.instance = new TutorialPane();
        }
        
        return TutorialPane.instance;
    }
    
    private JFrame window;
    private Component highlightedComponent;
    private String highlightLabel = "";
//    private Rectangle highlightArea;
//    private String highlightLabel;
    private ActionListener nextButtonCallback;
    
    private TutorialPane() {
    	setLayout(null);
    	
    	JButton nextButton = new JButton("Next");
    	nextButton.setBounds(133, 122, 117, 25);
    	nextButton.addActionListener(this);
    	add(nextButton);
    	addMouseMotionListener(this);
    	
        window = null;
        
    }
    
    /**
     * 
     * @param comp
     */
	public void install(JFrame to) {
		window = to;
		to.setGlassPane(this);
		setVisible(true);

	}
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	if(highlightedComponent == null){
    		return;
    	}
    	
        Rectangle highlightArea = highlightedComponent.getBounds();
        final Point compPos = highlightedComponent.getLocationOnScreen();
        final Point pos = getLocationOnScreen();
        
        highlightArea.x = compPos.x - pos.x;
        highlightArea.y = compPos.y - pos.y;
        
        if (highlightArea != null) {
            g.setColor(new Color(200, 30, 30, 210));
            drawTransparentRect(g, highlightArea.x, highlightArea.y,
                    highlightArea.width, highlightArea.height);
            
            g.setColor(new Color(230, 30, 30));
            g.setFont(getFont().deriveFont(Font.BOLD));
            g.drawString(highlightLabel, highlightArea.x + highlightArea.width
                    + 10, highlightArea.y + highlightArea.height / 2
                    + g.getFontMetrics().getAscent() / 2);
        }
    }
    
    private void drawTransparentRect(Graphics g, int x, int y, int w, int h) {
        Color previousColor = g.getColor(); // store previous color
        g.setColor(new Color(100, 100, 200, 50));
        g.fillRect(x, y, w, h);
        g.setColor(previousColor);
    }
    
    @Override
    public boolean contains(int x, int y) {
        Component[] children = getComponents();
    	for(Component child:children){
    		if(child.getBounds().contains(x, y)){
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 
     */
    public void clear() {
    	highlightedComponent = null;
        highlightLabel = null;
        repaint();
    }
    
    /**
     * 
     * @param highlightedComponent
     * @param label
     */
    public void setHighlightArea(Component highlightedComponent, String label) {
    	this.highlightedComponent = highlightedComponent;
    	this.highlightLabel = label;
    }

    /**
     * set a listener to recieve an event the next time the next button is clicked
     * @param a
     */
    public void setNextButtonCallback(ActionListener a){
    	nextButtonCallback = a;
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(nextButtonCallback != null){
			nextButtonCallback.actionPerformed(new ActionEvent(this, 0, null));
			nextButtonCallback = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		repaint();		
	}
}
