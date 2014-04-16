package edu.wpi.cs.team9.planningpoker.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.Estimate;

public class EstimateListAdapter extends ArrayAdapter<Estimate> {

	public EstimateListAdapter(Context context) {
		super(context, R.layout.estimate_list_item, R.id.username);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view = super.getView(position, convertView, parent);
		
		TextView estimateView = (TextView)view.findViewById(R.id.estimate);
		estimateView.setText(Card.cardNumberFormat.format(getItem(position).getEstimate()));
		
		return view;		
	}

}
