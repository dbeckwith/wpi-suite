/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;

/**
 * shows information about a requirement from a completed or ended game
 * @author akshay
 *
 */
public class CompletedRequirementFragment extends Fragment {
	
	private static final String TAG = RequirementFragment.class.getSimpleName();

	private GameRequirementModel requirement;
	
	private TextView nameView;
	private TextView descView;
	private TextView meanView;
	private TextView medianView;
	
	private ListView estimateListView;
	private EstimateListAdapter adapter;
		
	public CompletedRequirementFragment() {
	}
	
	public void setRequirement(GameRequirementModel req){
		this.requirement = req;		
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View root = inflater.inflate(R.layout.fragment_requirement_complete, null);

		nameView = (TextView)root.findViewById(R.id.req_name);
		descView = (TextView)root.findViewById(R.id.description);
		meanView = (TextView)root.findViewById(R.id.mean);
		medianView = (TextView)root.findViewById(R.id.median);
		
		nameView.setText(requirement.getName());
		descView.setText(requirement.getDescription());
		meanView.setText("Mean: "+ requirement.getEstimateMean());
		medianView.setText("Median: "+ requirement.getEstimateMedian());
		
		estimateListView = (ListView)root.findViewById(R.id.list);
		adapter = new EstimateListAdapter(getActivity());
		estimateListView.setAdapter(adapter);
		
		adapter.addAll(requirement.getEstimates());
		adapter.notifyDataSetChanged();
	

		Log.d(TAG, "onCreateView()");
		return root;
	}	
}

