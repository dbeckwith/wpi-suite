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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ImageLoader;

/**
 * @version 1.0
 * @author Team 9
 *
 */
public class SpinnerCard extends Card implements ChangeListener, MouseListener{

    private static final float FONT_SIZE = 0.3f;
    
    private static final BufferedImage deleteIcon = ImageLoader.getImage("Delete.png");
	
    private final JSpinner input;
    private final double maxInput;
    
    private ActionListener deleteListener = null;
    
    /**
     * 
     * @param val
     * @param limit
     */
    public SpinnerCard(float val, float limit){
    	super(val);
    	
    	maxInput = limit;
    	final double cardLimit = (limit == DeckModel.NO_LIMIT)?Float.MAX_VALUE:limit;
    	
    	input = new JSpinner();
    	input.setModel(new SpinnerNumberModel(val, 0, cardLimit, 0.5));
    	input.setEditor(new JSpinner.NumberEditor(input, "0.0"));
    
    	final JFormattedTextField text = ((JSpinner.NumberEditor) input.getEditor()).getTextField();
    	((NumberFormatter) text.getFormatter()).setAllowsInvalid(false);
    	text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    	text.setBackground(new Color(255, 255, 255, 0));
    
    	input.addChangeListener(this);
    	input.setOpaque(false);
    
    	final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
    	
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		add(input, gbc);
		
    	addMouseListener(this);
		
    }

    public void setDeleteListener(ActionListener listener){
    	deleteListener = listener;
    }
    
    @Override
    public void setEstimateValue(float val){
    	input.getModel().setValue(Double.valueOf(val));
    	repaint();
    }
    
    @Override
    public float getEstimateValue(){
    	final float enteredValue = ((Number)(input.getModel().getValue())).floatValue();
    	if(maxInput != DeckModel.NO_LIMIT){
			return (float) Math.min(maxInput, enteredValue);
		} else {
			return enteredValue;
		}
    }
    
	@Override
	public void stateChanged(ChangeEvent e) {
		final float inputValue = ((Number)input.getValue()).floatValue();
		if(maxInput != DeckModel.NO_LIMIT && inputValue > maxInput){
			input.setValue(maxInput);
		} else {
			if(inputValue == Float.POSITIVE_INFINITY){
				input.setValue(0);
			}
		}
		notifyListeners();

	}

	@Override
	public void paintCard(Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
        
		input.setFont(input.getFont().deriveFont(getWidth() * FONT_SIZE));
		
		if(maxInput != DeckModel.NO_LIMIT){
    		g2.setColor(Color.BLACK);
    		final String maxString = "Max : " + decimalFormat.format(((SpinnerNumberModel)input.getModel()).getMaximum());
    		g2.setFont(g.getFont().deriveFont(getWidth() * FONT_SIZE * 0.3f));
    		final Rectangle2D r = g2.getFontMetrics().getStringBounds(maxString, g);
    		g2.drawString(maxString, (int) (getWidth() - r.getWidth()) / 2,
                    (int) (getHeight() - r.getHeight()) / 2
                            + g2.getFontMetrics().getAscent() + input.getHeight() / 2);
    	}
		
		if(deleteListener != null){
			g2.drawImage(deleteIcon, getWidth() - deleteIcon.getWidth() - 2, 2, null);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(deleteListener == null){
			return;
		}
		final Rectangle deleteRect = new Rectangle(getWidth() - deleteIcon.getWidth() - 2, 2, deleteIcon.getWidth(), deleteIcon.getHeight());
		if(deleteRect.contains(e.getPoint())){
			
			deleteListener.actionPerformed(new ActionEvent(this, 0, null));
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	@Override
	public boolean isCardSelected() {
	    return true;
	}

	

}
