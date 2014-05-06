/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import edu.wpi.cs.team9.planningpoker.Config;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.Estimate;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;

/**
 * An adapter for the list of requirements in the game fragment
 * @author akshay
 */
public class RequirementListAdapter extends ArrayAdapter<GameRequirementModel> {
	
	private static final String TAG = RequirementListAdapter.class.getSimpleName();

	/**
	 * Constructor
	 * @param context the context of the application
	 * @param resource the view to use as a resource
	 * @param textViewResourceId 
	 */
	public RequirementListAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = super.getView(position, convertView, parent);
		
		ImageView imageView = (ImageView)view.findViewById(R.id.status);
		
		if(hasUserEstimated(getItem(position))){
			imageView.setImageResource(R.drawable.ic_status_complete);
		} else {
			imageView.setImageResource(R.drawable.ic_status_pending);
		}
		
		return view;		
	}
	
	/**
	 * returns whether the current user has voted for the given requirement
	 * @param m
	 * @return
	 */
	public boolean hasUserEstimated(GameRequirementModel m){
		ArrayList<Estimate> estimates = m.getEstimates();
		String user = Config.getUserName(getContext());
		for(Estimate e:estimates){
			try {
				if(e.getUsername().equals(user)){
					return true;
				}
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		return false;		
	}

}
