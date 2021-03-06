/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.GameModel;

/**
 * Adpater for the list of game. Shows the game's name and an icon
 * @author akshay
 *
 */
public class GameListAdapter extends ArrayAdapter<GameModel>{
	
	private Context context;
	
	/**
	 * Constructor
	 * @param context
	 */
	public GameListAdapter(Context context) {
		super(context, R.layout.game_list_item);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View view;
		if(convertView == null){
			view = inflater.inflate(R.layout.game_list_item, null);
		} else {
			view = convertView;
		}
		
		GameModel game = getItem(position);
		ImageView statusView = (ImageView)view.findViewById(R.id.status);	
		TextView nameView = (TextView)view.findViewById(R.id.name);
		
		//pick the icon according to the status
		int icon = R.drawable.ic_status_pending;
		
		if(game.isEnded()){
			icon = R.drawable.ic_status_complete;
		} 
		
		if (game.isClosed()){
			icon = R.drawable.ic_status_closed;
		}
		
		statusView.setImageResource(icon);
		nameView.setText(game.getName());
		
		return view;
	}
}
