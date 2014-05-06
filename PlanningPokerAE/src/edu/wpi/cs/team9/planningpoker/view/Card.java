/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker.view;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;

/**
 * A estimation card that shows a value and can be selected
 * @author akshay
 *
 */
public class Card implements View.OnClickListener{
	
	public static final DecimalFormat cardNumberFormat = new DecimalFormat("0.#");
	
	private static final int[] SUIT_DRAWABLES = {
		R.drawable.suit_clubs,
		R.drawable.suit_diamonds,
		R.drawable.suit_hearts,
		R.drawable.suit_spades,
		R.drawable.ic_goat
	};
	
	//private Context context;
	
	private View view;
	private double value;
	
	private LinearLayout contentLayout;
	private TextView text;
	
	private CardListener listener;
	
	private boolean selected = false;
	
	/**
	 * Constructor
	 * @param ctx the context of the application
	 * @param value the value of the card
	 * @param listener the selection listener
	 */
	public Card(Context ctx, double value, CardListener listener){
		//this.context = ctx;
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listener = listener;
	  	this.value = value;
	  
		view = (View)inflater.inflate(R.layout.card_button, null);
		contentLayout = (LinearLayout)view.findViewById(R.id.innerLayout);
		text = (TextView)view.findViewById(R.id.text);			
		
		int suit = SUIT_DRAWABLES[(int)(Math.random()*4)];
		
		((ImageView)view.findViewById(R.id.iconTop)).setImageResource(suit);
		((ImageView)view.findViewById(R.id.iconBottom)).setImageResource(suit);
		text.setText(cardNumberFormat.format(value));
		
		view.setOnClickListener(this);		
	}

	/**
	 * Sets the card's selection state
	 * @param selected
	 */
	public void setCardSelected(boolean selected){
		this.selected = selected;
		if(selected){
			contentLayout.setBackgroundResource(R.drawable.bg_card_highlight);
			text.setTextColor(Color.WHITE);
		} else {
			contentLayout.setBackgroundResource(R.drawable.bg_card);
			text.setTextColor(Color.BLACK);
		}
	}
	
	/**
	 * 
	 * @return whether the card is selected or not
	 */
	public boolean isCardSelected(){
		return selected;
	}
	
	/**
	 * 
	 * @return the view to be displayed by the card
	 */
	public View getView(){
		return view;
	}
	
	/**
	 * @return the value of the card
	 * 
	 */
	public double getValue(){
		return value;
	}

	@Override
	public void onClick(View v) {
		setCardSelected(!selected);


		if(listener != null){
			listener.cardSelected(this);
		}
	}
	
	/**
	 * interface to listen to changes from the card
	 * @author akshay
	 *
	 */
	public interface CardListener{
		public void cardSelected(Card c);
	}

	
}
