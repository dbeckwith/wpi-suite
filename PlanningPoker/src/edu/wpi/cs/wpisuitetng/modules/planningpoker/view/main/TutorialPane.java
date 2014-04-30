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
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

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
    
    private static final int BORDER = 3;
    
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
    private Rectangle highlightArea = null;
    private Component highlightedComponent;
    private String highlightLabel = "";
//    private Rectangle highlightArea;
//    private String highlightLabel;
    private ActionListener nextButtonCallback;
    private JPanel dialogPanel;
    private JTextArea text;
    private JButton nextButton;
    
    private TutorialPane() {
    	setLayout(null);
    	addMouseMotionListener(this);
    	
    	dialogPanel = new JPanel();
    	dialogPanel.setBackground(new Color(255,255,255,0));
    	dialogPanel.setOpaque(false);
    	dialogPanel.setBounds(42, 32, 295, 162);
    	add(dialogPanel);
    	
    	nextButton = new JButton("Next");
    	
    	text = new JTextArea();
    	text.setWrapStyleWord(true);
    	text.setLineWrap(true);
    	text.setEnabled(false);
    	text.setEditable(false);
    	text.setDisabledTextColor(Color.BLACK);
    	GroupLayout gl_dialogPanel = new GroupLayout(dialogPanel);
    	gl_dialogPanel.setHorizontalGroup(
    		gl_dialogPanel.createParallelGroup(Alignment.LEADING)
    			.addGroup(Alignment.TRAILING, gl_dialogPanel.createSequentialGroup()
    				.addContainerGap()
    				.addComponent(nextButton))
    			.addComponent(text, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
    	);
    	gl_dialogPanel.setVerticalGroup(
    		gl_dialogPanel.createParallelGroup(Alignment.LEADING)
    			.addGroup(Alignment.TRAILING, gl_dialogPanel.createSequentialGroup()
    				.addComponent(text, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
    				.addPreferredGap(ComponentPlacement.RELATED)
    				.addComponent(nextButton))
    	);
    	dialogPanel.setLayout(gl_dialogPanel);
    	nextButton.addActionListener(this);
    	
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
    	
    	if(highlightedComponent == null || !highlightedComponent.isVisible()){
    		highlightArea = null;
    		return;
    	}
    	
        highlightArea = highlightedComponent.getBounds();
        final Point compPos = highlightedComponent.getLocationOnScreen();
        final Point pos = getLocationOnScreen();
        
        
        highlightArea.x = compPos.x - pos.x;
        highlightArea.y = compPos.y - pos.y;
        
        placePanel(highlightArea);

        //System.out.println(highlightArea);
        
        if (highlightArea != null) {
        	
           // g.setColor(new Color(200, 30, 30, 210));
            drawHighlightRect(g, highlightArea.x, highlightArea.y,
                    highlightArea.width, highlightArea.height);
            
//            g.setColor(new Color(230, 30, 30));
//            g.setFont(getFont().deriveFont(Font.BOLD));
//            g.drawString(highlightLabel, highlightArea.x + highlightArea.width
//                    + 10, highlightArea.y + highlightArea.height / 2
//                    + g.getFontMetrics().getAscent() / 2);
        }
    }
    
    private void drawHighlightRect(Graphics g, int x, int y, int w, int h) {
        Color previousColor = g.getColor(); // store previous color
    	g.setColor(new Color(0,0,0,50));
    	g.fillRect(0, 0, getWidth(), y);
    	g.fillRect(0, y, x, h);
    	g.fillRect(x+w, y, getWidth()-x-w, h);
    	g.fillRect(0, y+h, getWidth(), getHeight()-y-h);
    	

        g.setColor(new Color(100,100,220,255));
        for(int i = 0; i < BORDER;i++){
        	g.drawRect(x+i, y+i, w-2*i, h-2*i);
        }
        g.setColor(previousColor);
    }
    
    private void placePanel(final Rectangle c){
    	ArrayList<Rectangle> rects = new ArrayList<Rectangle>(){{
    		add(new Rectangle(c.x, 0, c.width, c.y));//north
    		add(new Rectangle(c.x+c.width, c.y, getWidth()-c.x-c.width, c.height));//east
    		add(new Rectangle(c.x, c.y+c.height,c.width,getHeight()-c.y-c.height));//south
    		add(new Rectangle(0, c.y, c.x, c.y+c.height));//west

    		add(new Rectangle(0, 0, c.x, c.y));//north west
    		add(new Rectangle(c.x+c.width, 0, getWidth()-c.x-c.width, c.y));//north east
    		add(new Rectangle(c.x, c.y,getWidth()-c.width-c.x,getHeight()-c.y-c.height));//south east
    		add(new Rectangle(0, c.y, c.x,getHeight()-c.y-c.height));//south west
    	}};
    	
    	Rectangle dir = rects.get(0);
    	for(Rectangle r:rects){
    		if(r.width*r.height > dir.width*dir.height){
    			dir = r;
    		}
    	}
    	
    	Rectangle cur = dialogPanel.getBounds();
    	cur.x = dir.x + (dir.width-cur.width)/2;
    	cur.y = dir.y + (dir.height-cur.height)/2;
    	
    	dialogPanel.setBounds(cur);
    	
    }
    
    @Override
    public boolean contains(int x, int y) {
        Component[] children = getComponents();
    	for(Component child:children){
    		if(child.getBounds().contains(x, y)){
    			return true;
    		}
    	}
    	
    	if(highlightArea != null && !highlightArea.contains(x, y)){
    		return true;
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
    	text.setText(label);
    	System.out.printf("\t[TUTORIAL] Set highlight to %s, \"%s\"\n", highlightedComponent.toString(), label);
    	repaint();
    }

    /**
     * set a listener to recieve an event the next time the next button is clicked
     * @param a
     */
    public void setNextButtonCallback(ActionListener a){
    	nextButtonCallback = a;
    	nextButton.setVisible(true);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(nextButtonCallback != null){
			ActionListener call = nextButtonCallback; //save in case it sets a new one
			nextButtonCallback = null;
			nextButton.setVisible(false);
			call.actionPerformed(new ActionEvent(this, 0, null));
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		repaint();		
	}
}
