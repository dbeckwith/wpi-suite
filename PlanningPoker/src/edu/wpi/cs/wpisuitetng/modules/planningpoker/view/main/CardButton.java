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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * A card button is a button that is used to select a card from a deck for
 * estimating a requirement. It represents one value of a deck and may be
 * toggled on and off.
 * 
 * @author Team 9
 * @version 1.0
 */ 
public class CardButton extends JPanel implements MouseListener, ChangeListener {

    private static final long serialVersionUID = 2543023112833273691L;
    
    public static final DecimalFormat cardFormat = new DecimalFormat("0.#");

	private final Color HIGHLIGHT_COLOR = new Color(220, 232, 244);
    
    //private static final float MARGIN = 0.03f;
    private static final float MARGIN_LOGO = 0.05f;
    private static final float FRONT_SUIT_SIZE = 0.3f;
    private static final float BACK_SUIT_SIZE = 0.7f;
    private static final float FONT_SIZE = 0.3f;
    private static final float FONT_SIZE_HOVER = 0.4f;
    private static BufferedImage[] suits;
    
    static {
        CardButton.suits = new BufferedImage[4];
        BufferedImage allSuits = ImageLoader.getImage("suits.png");
        int sWidth = allSuits.getWidth() / 2;
        int sHeight = allSuits.getHeight() / 2;
        CardButton.suits[0] = allSuits.getSubimage(0, 0, sWidth, sHeight);
        CardButton.suits[1] = allSuits.getSubimage(sWidth, 0, sWidth, sHeight);
        CardButton.suits[2] = allSuits.getSubimage(0, sHeight, sWidth, sHeight);
        CardButton.suits[3] = allSuits.getSubimage(sWidth, sHeight, sWidth,
                sHeight);
        
    }
    
    private String value;
    private int suitIndex;
    
    private boolean selected = false;
    
    private boolean hover;
    private boolean cardEnabled = true;
    
    private boolean textInput = false;
    
    
    
    private JSpinner input;
    private double maxInput;
    
    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
    
    /**
     * Creates a new CardButton representing the given value.
     * 
     * @param val
     *            the value of this card
     */
    public CardButton(String val) {
    	textInput = false;
        value = CardButton.cardFormat.format(Float.parseFloat(val));
        suitIndex = ((int)getEstimateValue()+1)%4;//(int) (Math.random() * CardButton.suits.length);
        selected = false;
        addMouseListener(this);
        
    }
    
    public CardButton(){
    	textInput = true;
    	input = new JSpinner();
    	input.setModel(new SpinnerNumberModel(1, 0, Double.MAX_VALUE-1, 0.5));
    	
    	input.setEditor(new JSpinner.NumberEditor(input, "0.0"));
    	JFormattedTextField txt = ((JSpinner.NumberEditor) input.getEditor()).getTextField();
    	((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
    	txt.setHorizontalAlignment(JTextField.CENTER);
    	txt.setBackground(new Color(255,255,255,0));

    	input.addChangeListener(this);
    	input.setOpaque(false);
    	//input.setBorder(null);
    	
    	value = "";
    	
    	GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(input, gbc);

    	cardEnabled = true;
    	selected = true;
    }
    
    
    public void addActionListener(ActionListener e){
    	if(e != null){
    		listeners.add(e);
    	}
    }
        
    @Override
    public void paintComponent(Graphics g) {
    	
    	if(textInput){
    		input.setFont(input.getFont().deriveFont(getWidth()*FONT_SIZE));
    	}
    	
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int suitMargin = (int) (getWidth() * CardButton.MARGIN_LOGO);
        
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        if (isEnabled()) {
            int suitSize = (int) (getWidth() * CardButton.FRONT_SUIT_SIZE);
            //highlight card background
            if (!textInput && (hover || selected)) {
                g2.setColor(HIGHLIGHT_COLOR);
                g2.fillRect(0, 0, getWidth(), getHeight());

            }
            
            //draw suit logos on the corners
            g2.drawImage(CardButton.suits[suitIndex], suitMargin * 2,
                    suitMargin * 2, suitSize, suitSize, null);
            g2.drawImage(CardButton.suits[suitIndex], getWidth() - suitSize
                    - suitMargin * 2, getHeight() - suitSize - suitMargin * 2,
                    suitSize, suitSize, null);
            
            if(!textInput){
	            //draw text
	            g2.setColor(Color.BLACK);
	            g2.setFont(new Font(g.getFont().getFontName(), Font.BOLD,
	                    (int) (getWidth() * ((hover || selected) ? CardButton.FONT_SIZE_HOVER
	                            : CardButton.FONT_SIZE))));
	            Rectangle2D r = g2.getFontMetrics().getStringBounds(value, g);
	            g2.drawString(value, (int) (getWidth() - r.getWidth()) / 2,
	                    (int) (getHeight() - r.getHeight()) / 2
	                            + g2.getFontMetrics().getAscent());
            } else {
            	if(maxInput != DeckModel.NO_LIMIT){
            		g2.setColor(Color.BLACK);
            		String maxString = "Max : "+ cardFormat.format(((SpinnerNumberModel)input.getModel()).getMaximum());
            		g2.setFont(g.getFont().deriveFont(getWidth()*FONT_SIZE*0.3f));
            		Rectangle2D r = g2.getFontMetrics().getStringBounds(maxString, g);
            		g2.drawString(maxString, (int) (getWidth() - r.getWidth()) / 2,
    	                    (int) (getHeight() - r.getHeight()) / 2
    	                            + g2.getFontMetrics().getAscent() + input.getHeight()/2);
            	}
            }
            
        }
        else {
            int suitSize = (int) (getWidth() * CardButton.BACK_SUIT_SIZE);
            g2.drawImage(CardButton.suits[suitIndex],
                    (getWidth() - suitSize) / 2, (getHeight() - suitSize) / 2,
                    suitSize, suitSize, null);
        }
        
        //draw card outline
        g2.setColor(Color.BLACK);
        if (selected && !textInput) {
            g2.setColor(Color.BLUE);
            g2.drawRect(2, 2, getWidth() -4, getHeight() -4 );
        }
        g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
        g2.drawRect(1, 1, getWidth() -3, getHeight() -3 );
    }
    
    /**
     * Gets the estimate value represented by this button.
     * 
     * @return the estimate value
     */
    public float getEstimateValue() {
    	if(textInput){
    		float enteredValue = ((Number)(input.getModel().getValue())).floatValue();
    		if(maxInput != DeckModel.NO_LIMIT){
    			return (float) Math.min(maxInput, enteredValue);
    		} else {
    			return enteredValue;
    		}
		} else {
    		return Float.parseFloat(value);
    	}
    }
    
    /**
     * Sets whether or not this card is selected and its value should be
     * considered in the total estimation.
     * 
     * @param selected
     *            true if the card should be selected, false otherwise
     */
    public void setCardSelected(boolean selected) {
        this.selected = selected;
    }
    
    public void setLimit(int limit){
    	maxInput = limit;
    	double cardLimit = (limit == DeckModel.NO_LIMIT)?Double.MAX_VALUE:limit;
    	input.setModel(new SpinnerNumberModel(1, 0,cardLimit, 0.5));
    }
    
    /**
     * sets the value of the card button
     * @param val
     */
    public void setValue(float val){
    	value = cardFormat.format(val);
    	if(input != null){
    		input.getModel().setValue(Double.valueOf(value));
    	}
    }
    
    /**
     * Sets whether or not this card should be enabled and selectable.
     * 
     * @param enabled
     *            true if this card is enabled, false otherwise
     */
    public void setCardEnabled(boolean enabled) {
        cardEnabled = enabled;
    }
    
    /**
     * Gets whether this card is selected or not.
     * 
     * @return true if this card is selected, false otherwise
     */
    public boolean isCardSelected() {
        return selected;
    }
    
    /**
     * Gets whether this card is enabled or not.
     * 
     * @return true if this card is enabled, false otherwise
     */
    public boolean isCardEnabled() {
        return cardEnabled;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	for(ActionListener al : listeners){
    		al.actionPerformed(new ActionEvent(this, 0, null));
    	}
    	
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (cardEnabled) {
            hover = true;
            repaint();
        }
        else {
            mouseExited(e);
        }
        
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        hover = false;
        repaint();
        
    }

	@Override
	public void stateChanged(ChangeEvent e) {
		float inputValue = ((Number)input.getValue()).floatValue();
		if(maxInput != DeckModel.NO_LIMIT && inputValue > maxInput){
			input.setValue(maxInput);
		} else {
			if(inputValue == Float.POSITIVE_INFINITY){
				input.setValue(0);
			}
		}
		for(ActionListener l:listeners){
			l.actionPerformed(new ActionEvent(this, 0, null));
		}
	}
}
