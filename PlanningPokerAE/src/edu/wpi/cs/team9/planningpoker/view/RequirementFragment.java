package edu.wpi.cs.team9.planningpoker.view;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;

public class RequirementFragment extends Fragment {
	
		private static final String TAG = RequirementFragment.class.getSimpleName();
	
		private static final String ARG_SECTION_NUMBER = "section_number";
		private static final String ARG_REQUIREMENT = "req_json";
		
		private GameRequirementModel requirement;
		
		private TextView nameView;
		private TextView descView;

		public RequirementFragment() {
		}
		
		public void setRequirement(GameRequirementModel req){
			this.requirement = req;		
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			
			View root = inflater.inflate(R.layout.fragment_requirement, null);
			
			nameView = (TextView)root.findViewById(R.id.req_name);
			descView = (TextView)root.findViewById(R.id.req_desc);			
			
			nameView.setText(requirement.getName());
			descView.setText(requirement.getDescription());
						
			Log.d(TAG, "onCreateView()");
			return root;
		}
}
