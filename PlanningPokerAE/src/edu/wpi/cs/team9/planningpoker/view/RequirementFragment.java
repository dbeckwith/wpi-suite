package edu.wpi.cs.team9.planningpoker.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;

public class RequirementFragment extends Fragment {
	
		private static final String TAG = RequirementFragment.class.getSimpleName();
	
		private static final String ARG_SECTION_NUMBER = "section_number";
		private static final String ARG_REQUIREMENT = "req_json";
		
		private static final int[] deck = {1, 2, 3, 5, 10, 20, 50, 100};
		
		private GameRequirementModel requirement;
		
		private TextView nameView;
		private TextView descView;
		private TableLayout table;

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
			
			table = (TableLayout)root.findViewById(R.id.buttonLayout);
			setUpCardTable();

			Log.d(TAG, "onCreateView()");
			return root;
		}
		
		private void setUpCardTable(){
		  LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  TableLayout.LayoutParams rowLP = new TableLayout.LayoutParams(
		        TableLayout.LayoutParams.MATCH_PARENT,
		        TableLayout.LayoutParams.MATCH_PARENT,
		        1.0f);
		  
			TableRow.LayoutParams cellLP = new TableRow.LayoutParams(
			        TableRow.LayoutParams.MATCH_PARENT,
			        TableRow.LayoutParams.MATCH_PARENT,
			        1.0f);
			
			TableRow[] rows = new TableRow[deck.length/3 + 1];
			for(int i = 0; i < rows.length; i++){
				rows[i] = new TableRow(getActivity());
			}
			
			for(int i = 0; i < deck.length; i++){
				View v = (View)inflater.inflate(R.layout.card_button, null);
				Button b = (Button)v.findViewById(R.id.button);
				b.setText(""+deck[i]);
				rows[i/3].addView(v, cellLP);
			}
			
			for(TableRow row:rows){
				while(row.getChildCount() < 3){
					row.add(new View())
				}
				table.addView(row, rowLP);
			}
			
			
			
			
		}
}







