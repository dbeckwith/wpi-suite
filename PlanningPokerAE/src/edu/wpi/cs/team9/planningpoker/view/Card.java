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

public class Card implements View.OnClickListener{
	
	public static final DecimalFormat cardNumberFormat = new DecimalFormat("0.#");
	
	private static final int[] SUIT_DRAWABLES = {
		R.drawable.suit_clubs,
		R.drawable.suit_diamonds,
		R.drawable.suit_hearts,
		R.drawable.suit_spades,
		R.drawable.ic_goat
	};
	
	private View view;
	private float value;
	
	private LinearLayout contentLayout;
	private TextView text;
	
	private CardListener listener;
	
	private boolean selected = false;
	
	public Card(Context ctx, float value, CardListener listener){
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
	
	public boolean isCardSelected(){
		return selected;
	}
	
	public View getView(){
		return view;
	}
	
	public float getValue(){
		return value;
	}

	@Override
	public void onClick(View v) {
		setCardSelected(!selected);

		if(listener != null){
			listener.cardSelected(this);
		}
	}
	
	public interface CardListener{
		public void cardSelected(Card c);
	}

	
}
