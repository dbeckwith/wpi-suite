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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.team9.planningpoker.view.RequirementFragment.RequirementFragmentListener;

public class CompletedRequirementFragment extends Fragment {
	
	private static final String TAG = RequirementFragment.class.getSimpleName();
	
	private static final int COLUMNS = 4;
	
	private static final float[] deck = {0.5f, 1, 2, 3, 5, 10, 20, 50, 100};
		
	private static final TableLayout.LayoutParams rowLP = new TableLayout.LayoutParams(
	        TableLayout.LayoutParams.MATCH_PARENT,
	        TableLayout.LayoutParams.MATCH_PARENT,
	        1.0f);
	
	private static final TableRow.LayoutParams cellLP = new TableRow.LayoutParams(
	        TableRow.LayoutParams.MATCH_PARENT,
	        TableRow.LayoutParams.MATCH_PARENT,
	        1.0f);
	
	private RequirementFragmentListener listener;
	
	private GameRequirementModel requirement;
	
	private TextView nameView;
	private TextView descView;
	private TextView meanView;
	private TextView medianView;
	
	private ListView estimateListView;
	private EstimateListAdapter adapter;
		
	private boolean selectMultiple = true;
	
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

