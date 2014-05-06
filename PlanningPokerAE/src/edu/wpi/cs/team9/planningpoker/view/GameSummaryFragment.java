/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker.view;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;

/**
 * shows the game's basic information and a list of requirements
 * @author akshay
 *
 */
public class GameSummaryFragment extends Fragment implements OnItemClickListener{
	
	private static final String TAG = GameSummaryFragment.class.getSimpleName();
	
	private TextView nameText;
	private TextView descriptionText;
	private ListView requirementListView;
	
	private RequirementListAdapter adapter;
	
	private GameModel game;
	
	private RequirementListListener listener;

	/**
	 * Blank Constructor needed for Android
	 */
	public GameSummaryFragment(){
		
	}
	
	/**
	 * sets the game to display
	 * @param game
	 */
	public void setGame(GameModel game){
		this.game = game;		
	}
	
	/**
	 * Sets the listener for requirement selections
	 * @param l
	 */
	public void setListener(RequirementListListener l){
		this.listener = l;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View root = inflater.inflate(R.layout.fragment_game_summary, null);
		
		nameText = (TextView)root.findViewById(R.id.name);
		descriptionText = (TextView)root.findViewById(R.id.description);
		
		requirementListView = (ListView)root.findViewById(R.id.requirementList);
		
		adapter = new RequirementListAdapter(getActivity(), R.layout.game_list_requirement_item, R.id.text1);	
		requirementListView.setAdapter(adapter);
		requirementListView.setOnItemClickListener(this);
		
		nameText.setText(game.getName());
		descriptionText.setText(game.getDescription());
		
		List<GameRequirementModel> requirements = game.getRequirements();
		
		for(GameRequirementModel req:requirements){
			Log.d(TAG, "added requirement to list : "+req.getName());
			adapter.add(req);
		}
		
		adapter.notifyDataSetChanged();
		
		return root;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(listener != null){
			listener.requirementSelected(adapter.getItem(position));
		}
	}
	
	/**
	 * Notified when a requirement is clicked on
	 * @author akshay
	 */
	public interface RequirementListListener{
		public void requirementSelected(GameRequirementModel requirement);
	}
}
