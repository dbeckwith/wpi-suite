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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.Estimate;

/**
 * an adapter for the list of estimates
 * @author akshay
 *
 */
public class EstimateListAdapter extends ArrayAdapter<Estimate> {

	/**
	 * Constructor
	 * @param context
	 */
	public EstimateListAdapter(Context context) {
		super(context, R.layout.estimate_list_item, R.id.username);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = super.getView(position, convertView, parent);
		
		TextView estimateView = (TextView)view.findViewById(R.id.estimate);
		estimateView.setText(Card.cardNumberFormat.format(getItem(position).getEstimate()));
		
		return view;		
	}

}
