/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jdesktop.swingx.image.GaussianBlurFilter;

/**
 * 
 * A glass pane that goes over the rest of the GUI for the interactive tutorial.
 * 
 * @author Team 9
 * @version Apr 26, 2014
 */
public class TutorialPane extends JComponent implements ActionListener,
		MouseMotionListener {

	/**
     * 
     */
	private static final long serialVersionUID = -4832992249337947413L;
	private static TutorialPane instance = null;

	private static final long REPAINT_INTERVAL = 1000/60;
	
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
	GaussianBlurFilter blur = new GaussianBlurFilter(10);
	private BufferedImage windowImage;
	private JFrame window;
	private Rectangle highlightArea = null;
	private Component highlightedComponent;
	private ActionListener nextButtonCallback;
	private JPanel dialogPanel;
	private JTextArea text;
	private JButton nextButton;
	private JButton quitButton;

	private long lastRepaint = 0;
	
	/**
	 * Constructor
	 */
	private TutorialPane() {
		setLayout(null);
		addMouseMotionListener(this);


		
		dialogPanel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				if(windowImage != null){
					g2.drawImage(
							windowImage.getSubimage(
									dialogPanel.getX(),
									dialogPanel.getY(),
									dialogPanel.getWidth(),
									dialogPanel.getHeight()),
							blur, 0, 0);

				}
			}
		};
		dialogPanel.setBackground(Color.WHITE);
		dialogPanel.setBounds(42, 32, 368, 197);
		add(dialogPanel);

		nextButton = new JButton("Next");
		nextButton.setFont(new Font("Tahoma", Font.BOLD, 13));

		text = new JTextArea();
		text.setFont(new Font("Dialog", Font.BOLD, 12));
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		text.setEnabled(false);
		text.setEditable(false);
		text.setDisabledTextColor(Color.BLACK);
		text.setOpaque(false);
		text.setBackground(new Color(0,0,0,0));

		quitButton = new JButton("Quit Tutorial");
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		quitButton.setIcon(new ImageIcon(TutorialPane.class
				.getResource("/res/Delete.png")));
		GroupLayout gl_dialogPanel = new GroupLayout(dialogPanel);
		gl_dialogPanel.setHorizontalGroup(
			gl_dialogPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_dialogPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_dialogPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_dialogPanel.createSequentialGroup()
							.addComponent(quitButton)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(nextButton))
						.addComponent(text, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_dialogPanel.setVerticalGroup(
			gl_dialogPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_dialogPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(text, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_dialogPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(quitButton)
						.addComponent(nextButton))
					.addGap(13))
		);
		dialogPanel.setLayout(gl_dialogPanel);
		nextButton.addActionListener(this);
		nextButton.setVisible(false);
		dialogPanel.setVisible(false);

	}

	/**
	 * Adds itself to the given JFrame's glass pane
	 * 
	 * @param comp
	 */
	public void install(JFrame to) {
		window = to;
		to.setGlassPane(this);
		setVisible(true);
		repaint();

	}

	/**
	 * Sets an action listener to receive and events when the quit button is
	 * clicked
	 * 
	 * @param a
	 */
	public void setQuitListener(ActionListener a) {
		ActionListener[] quitters = quitButton.getActionListeners();
		for (ActionListener q : quitters) {
			quitButton.removeActionListener(q);
		}
		quitButton.addActionListener(a);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		if (highlightedComponent == null || !highlightedComponent.isVisible()) {
			highlightArea = null;
			return;
		}

		highlightArea = highlightedComponent.getBounds();

		final Point compPos;

		try {
			compPos = highlightedComponent.getLocationOnScreen();
		} catch (Exception e) {
			return;
		}

		final Point pos = getLocationOnScreen();

		highlightArea.x = compPos.x - pos.x;
		highlightArea.y = compPos.y - pos.y;

		placePanel(highlightArea);

		if(windowImage == null || windowImage.getWidth() != getWidth() || windowImage.getHeight() != getHeight()){
			windowImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		}
		
		window.getContentPane().paintComponents(windowImage.getGraphics());

		
		
		

		Rectangle dialogRect = dialogPanel.getBounds();
		Point dialogCenter = new Point((int) dialogRect.getCenterX(),
				(int) dialogRect.getCenterY());

		// System.out.println(highlightArea);

		if (highlightArea != null) {

			g.setColor(new Color(100, 100, 220, 255));
			g2.setStroke(new BasicStroke(5));
			
			g2.drawRect(dialogRect.x, dialogRect.y, dialogRect.width, dialogRect.height);

			ArrayList<Point> edges = new ArrayList<Point>();
			edges.add(new Point((int) highlightArea.getCenterX(),
					(int) highlightArea.getMinY()));
			edges.add(new Point((int) highlightArea.getCenterX(),
					(int) highlightArea.getMaxY()));
			edges.add(new Point((int) highlightArea.getMinX(),
					(int) highlightArea.getCenterY()));
			edges.add(new Point((int) highlightArea.getMaxX(),
					(int) highlightArea.getCenterY()));

			Point lineEnd = edges.get(0);
			for (Point p : edges) {
				if (p.distance(dialogCenter) < lineEnd.distance(dialogCenter)) {
					lineEnd = p;
				}
			}


			drawHighlightRect(g2, highlightArea.x, highlightArea.y,
					highlightArea.width, highlightArea.height);
			g.drawLine(dialogCenter.x, dialogCenter.y, lineEnd.x, lineEnd.y);


			
			g2.setStroke(new BasicStroke());

		}
	}

	/**
	 * Highlights the given rectangle by dimming the screen and drawing a border
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	private void drawHighlightRect(Graphics2D g, int x, int y, int w, int h) {
		Color previousColor = g.getColor(); // store previous color
		g.setColor(new Color(0, 0, 0, 60));
		g.fillRect(0, 0, getWidth(), y);
		g.fillRect(0, y, x, h);
		g.fillRect(x + w, y, getWidth() - x - w, h);
		g.fillRect(0, y + h, getWidth(), getHeight() - y - h);
		

//		highlightedComponent.repaint();

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

		g.setColor(new Color(100, 100, 220, 255));
		g.drawRect(x, y, w, h);

		g.setColor(previousColor);
	}

	/**
	 * Calculates an optimal location to place the tutorial dialog relative to
	 * the given rectangle
	 * 
	 * @param c
	 */
	private void placePanel(final Rectangle c) {

		// 9 possible areas around the target
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
		rects.add(new Rectangle(c.x, 0, c.width, c.y));// north
		rects.add(new Rectangle(c.x + c.width, c.y, getWidth() - c.x - c.width,
				c.height));// east
		rects.add(new Rectangle(c.x, c.y + c.height, c.width, getHeight() - c.y
				- c.height));// south
		rects.add(new Rectangle(0, c.y, c.x, c.y + c.height));// west

		rects.add(new Rectangle(0, 0, c.x, c.y));// north west
		rects.add(new Rectangle(c.x + c.width, 0, getWidth() - c.x - c.width,
				c.y));// north east
		rects.add(new Rectangle(c.x, c.y, getWidth() - c.width - c.x,
				getHeight() - c.y - c.height));// south east
		rects.add(new Rectangle(0, c.y, c.x, getHeight() - c.y - c.height));// south
																			// west

		// pick the largest box
		Rectangle dir = rects.get(0); // the direction to place the tutorial box
										// in
		for (Rectangle r : rects) {
			if (r.width * r.height > dir.width * dir.height) {
				dir = r;
			}
		}

		// center the rectangle on the largest one
		Rectangle cur = dialogPanel.getBounds();
		cur.x = dir.x + (dir.width - cur.width) / 2;
		cur.y = dir.y + (dir.height - cur.height) / 2;

		if (cur.x < 0) {
			cur.x = 0;
		} else if (cur.x + cur.width > getWidth()) {
			cur.x = getWidth() - cur.width;
		}

		if (cur.y < 0) {
			cur.y = 0;
		} else if (cur.y + cur.height > getHeight()) {
			cur.y = getHeight() - cur.height;
		}

		dialogPanel.setBounds(cur);

	}

	@Override
	public boolean contains(int x, int y) {

		if (highlightedComponent == null || highlightArea == null) {
			return false;
		}

		Component[] children = getComponents();
		for (Component child : children) {
			if (child.getBounds().contains(x, y)) {
				return true;
			}
		}

		if (!highlightArea.contains(x, y)) {
			return true;
		}

		return false;
	}

	/**
	 * Clears and hides the pane. TutorialPane does not block event after
	 * clearing
	 */
	public void clear() {
		highlightedComponent = null;
		dialogPanel.setVisible(false);
		dialogPanel.repaint();
		repaint();
	}

	/**
	 * Sets the component to be highlighted, as well as the message to display.
	 * All mouse events not on the component are blocked.
	 * 
	 * @param highlightedComponent
	 * @param label
	 */
	public void setHighlightArea(Component highlightedComponent, String label) {
		this.highlightedComponent = highlightedComponent;

		text.setText(label);
		dialogPanel.setVisible(true);

		repaint();
	}

	/**
	 * set a listener to recieve an event the next time the next button is
	 * clicked
	 * 
	 * @param a
	 */
	public void setNextButtonCallback(ActionListener a) {
		nextButtonCallback = a;
		dialogPanel.setVisible(true);
		nextButton.setVisible(a != null);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (nextButtonCallback != null) {
			ActionListener call = nextButtonCallback; // save in case it sets a
														// new one
			nextButtonCallback = null;
			dialogPanel.setVisible(false);
			nextButton.setVisible(false);
			call.actionPerformed(new ActionEvent(this, 0, null));
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}
}
