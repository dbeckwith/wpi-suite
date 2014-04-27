package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

public abstract class Card extends JPanel {


    public static final DecimalFormat decimalFormat = new DecimalFormat("0.#");
    
    protected static final Color BACKGROUND_COLOR = Color.WHITE;
    protected static final Color BORDER_COLOR = Color.BLACK;
	
    private static final float MARGIN_LOGO = 0.05f;
    private static final float FRONT_SUIT_SIZE = 0.3f;
    		
    private static BufferedImage[] suits;
    
    static {
        Card.suits = new BufferedImage[4];
        BufferedImage allSuits = ImageLoader.getImage("suits.png");
        int sWidth = allSuits.getWidth() / 2;
        int sHeight = allSuits.getHeight() / 2;
        Card.suits[0] = allSuits.getSubimage(0, 0, sWidth, sHeight);
        Card.suits[1] = allSuits.getSubimage(sWidth, 0, sWidth, sHeight);
        Card.suits[2] = allSuits.getSubimage(0, sHeight, sWidth, sHeight);
        Card.suits[3] = allSuits.getSubimage(sWidth, sHeight, sWidth, sHeight);   
    }
    
	private float value;
	private boolean cardSelected;
	
    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	private int suitIndex;
	private Color background;
	private Color border;
	
	public Card(float val){
		value = val;
		suitIndex = ((int)val+1)%4;
		cardSelected = false;
		background = BACKGROUND_COLOR;
		border = BORDER_COLOR;
	}
	
    public final void addActionListener(ActionListener e){
    	if(e != null){
    		listeners.add(e);
    	}
    }
    
    /**
     * notifies all action listeners of an event
     */
    public final void notifyListeners(){
		for(ActionListener al : listeners){
			al.actionPerformed(new ActionEvent(this, 0, null));
		}
    }
	
    /**
     * Gets whether this card is selected or not.
     * 
     * @return true if this card is selected, false otherwise
     */
	public boolean isCardSelected(){
		return cardSelected;
	}
	
    /**
     * Sets whether or not this card is selected and its value should be
     * considered in the total estimation.
     * 
     * @param selected
     *            true if the card should be selected, false otherwise
     */
	public void setCardSelected(boolean select){
		cardSelected = select;
	}
	
    /**
     * Gets the estimate value represented by this button.
     * 
     * @return the estimate value
     */

	public float getEstimateValue(){
		return value;		
	}
	
    /**
     * sets the value of the card button
     * @param val
     */
	public void setEstimateValue(float val){
		value = Math.max(0, val);
		repaint();
	}
	
	public void setColors(Color bgColor, Color borderColor){
		background = (bgColor == null)?BACKGROUND_COLOR:bgColor;
		border = (borderColor == null)?BORDER_COLOR:borderColor;
	}
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        int suitMargin = (int) (getWidth() * MARGIN_LOGO);
		
        g2.setColor(background);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        int suitSize = (int) (getWidth() * FRONT_SUIT_SIZE);
        
        //draw suit logos on the corners
        g2.drawImage(suits[suitIndex], suitMargin * 2,
                suitMargin * 2, suitSize, suitSize, null);
        g2.drawImage(suits[suitIndex], getWidth() - suitSize
                - suitMargin * 2, getHeight() - suitSize - suitMargin * 2,
                suitSize, suitSize, null);
        
        //draw card outline
        g2.setColor(border);
        if (cardSelected) {
            g2.drawRect(1, 1, getWidth() -3, getHeight() - 3 );
            g2.drawRect(2, 2, getWidth() -5, getHeight() - 5 );
            
        }
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        
		paintCard(g);
	}
	
	public abstract void paintCard(Graphics g);

	
}
