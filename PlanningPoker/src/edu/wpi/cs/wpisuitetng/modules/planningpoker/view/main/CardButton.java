/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * TODO: Contributors' names
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

public class CardButton extends JButton implements MouseListener {
	
	private static final float MARGIN = 0.03f;
	private static final float MARGIN_LOGO = 0.05f;
	private static final float FRONT_SUIT_SIZE = 0.3f;
	private static final float BACK_SUIT_SIZE = 0.7f;
	private static final float FONT_SIZE = 0.3f;
	private static final float FONT_SIZE_HOVER = 0.4f;
	private static BufferedImage[] suits;
	
	static {
		suits = new BufferedImage[4];
		final BufferedImage allSuits = ImageLoader.getImage("suits.png");
		final int sWidth = allSuits.getWidth()/2;
		final int sHeight = allSuits.getHeight()/2;
		suits[0] = allSuits.getSubimage(0, 0, sWidth, sHeight);
		suits[1] = allSuits.getSubimage(sWidth, 0, sWidth, sHeight);
		suits[2] = allSuits.getSubimage(0, sHeight, sWidth, sHeight);
		suits[3] = allSuits.getSubimage(sWidth, sHeight, sWidth, sHeight);
		
	}
	
	private final String value;
	private final int suitIndex;
	
	private boolean selected = false;
	
	private boolean hover;
	private boolean cardEnabled = true;
	
	public CardButton(String val){
		value = val;
		suitIndex = ((int)(Float.parseFloat(val)))%4;//(int)(Math.random()*suits.length);
		selected = false;
		this.addMouseListener(this);
		
	}
	
	public void paint(Graphics g){
		final Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		final int margin = (int)(getWidth()*MARGIN);
		final int suitMargin = (int)(getWidth()*MARGIN_LOGO);
		
		//draw drop shadow
		g2.setColor(Color.GRAY);
		g2.fillRect(margin, margin, getWidth()-margin, getHeight()-margin);
		
		
		g2.setColor(new Color(240,240,240));
		g2.fillRect(0, 0, getWidth()-margin*(2), getHeight()-margin*(2));	
		
		
		if(isEnabled()){
			final int suitSize = (int)(getWidth()*FRONT_SUIT_SIZE);
			
			//highlight card background
			if(hover || selected){
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, getWidth()-margin*(2), getHeight()-margin*(2));					
			}
			
			//draw suit logos on the corners
			g2.drawImage(suits[suitIndex], suitMargin*2, suitMargin*2, suitSize, suitSize, null);
			g2.drawImage(suits[suitIndex], getWidth()-suitSize-suitMargin*2, getHeight()-suitSize-suitMargin*2, suitSize, suitSize, null);
			
			//draw text
			g2.setColor(Color.BLACK);
			g2.setFont(new Font(g.getFont().getFontName(), Font.BOLD, (int)(getWidth()*(hover?FONT_SIZE_HOVER:FONT_SIZE))));
			final Rectangle2D r = g2.getFontMetrics().getStringBounds(value, g);
			g2.drawString(value, (int)(getWidth()-r.getWidth())/2,(int)(getHeight() - r.getHeight())/2 + g2.getFontMetrics().getAscent());
			
		} else {
			final int suitSize = (int)(getWidth()*BACK_SUIT_SIZE);
			g2.drawImage(suits[suitIndex], (getWidth()-suitSize)/2, (getHeight()-suitSize)/2, suitSize, suitSize, null);
		}
		
		//draw card outline
		g2.setColor(Color.BLACK);
		if(selected){
			g2.setColor(Color.GREEN);
		}
		g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
		g2.drawRect(1, 1, getWidth()-2, getHeight()-2);
	}
	
	public float getEstimateValue(){
		return Float.parseFloat(value);
	}
	
	public void setCardSelected(boolean selected){
		this.selected = selected;
	}
	
	public void setCardEnabled(boolean enabled){
		cardEnabled = enabled;
	}
	
	public boolean isCardSelected(){
		return selected;
	}
	
	public boolean isCardEnabled(){
		return cardEnabled;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(cardEnabled){
			hover = true;
			repaint();
		} else {
			mouseExited(e);
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		hover = false;
		repaint();
		
	}
}
