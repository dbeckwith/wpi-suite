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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

/**
 * A card button is a button that is used to select a card from a deck for
 * estimating a requirement. It represents one value of a deck and may be
 * toggled on and off.
 * 
 * @author Team 9
 * @version 1.0
 */
public class ButtonCard extends Card implements MouseListener {
    
    private static final long serialVersionUID = 2543023112833273691L;
    
    
    private final Color HIGHLIGHT_COLOR = new Color(220, 232, 244);
    
    private static final float FONT_SIZE = 0.3f;
    private static final float FONT_SIZE_HOVER = 0.4f;
    
    private boolean hover;
    
    /**
     * Creates a new CardButton representing the given value.
     * 
     * @param val
     *        the value of this card
     */
    public ButtonCard(float val) {
        super(val);
        addMouseListener(this);
    }
    
    @Override
    public void setCardSelected(boolean select) {
        super.setCardSelected(select);
        if (select) {
            setColors(HIGHLIGHT_COLOR, Color.BLUE);
        }
        else {
            setColors(null, null);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        setCardSelected(!isCardSelected());
        notifyListeners();
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        hover = true;
        setColors(HIGHLIGHT_COLOR, Color.BLUE);
        repaint();
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        hover = false;
        if (!isCardSelected()) {
            setColors(null, null);
        }
        repaint();
        
    }
    
    
    @Override
    public void paintCard(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        
        final String valueStr = Card.decimalFormat.format(getEstimateValue());
        
        g2.setColor(Color.BLACK);
        g2.setFont(new Font(g.getFont().getFontName(), Font.BOLD,
                (int) (getWidth() * ((hover || isCardSelected()) ? ButtonCard.FONT_SIZE_HOVER
                        : ButtonCard.FONT_SIZE))));
        final Rectangle2D r = g2.getFontMetrics().getStringBounds(valueStr, g);
        g2.drawString(valueStr, (int) (getWidth() - r.getWidth()) / 2,
                (int) (getHeight() - r.getHeight()) / 2 + g2.getFontMetrics().getAscent());
        
    }
    
    
}
