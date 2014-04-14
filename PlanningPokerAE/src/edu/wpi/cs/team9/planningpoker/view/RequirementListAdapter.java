package edu.wpi.cs.team9.planningpoker.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import edu.wpi.cs.team9.planningpoker.Config;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.Estimate;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;

public class RequirementListAdapter extends ArrayAdapter<GameRequirementModel> {
	
	private static final String TAG = RequirementListAdapter.class.getSimpleName();

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
