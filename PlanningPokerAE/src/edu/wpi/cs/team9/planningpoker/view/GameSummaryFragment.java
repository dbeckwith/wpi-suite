package edu.wpi.cs.team9.planningpoker.view;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;

public class GameSummaryFragment extends Fragment implements OnItemClickListener{
	
	private static final String TAG = GameSummaryFragment.class.getSimpleName();
	
	private TextView nameText;
	private TextView descriptionText;
	private ListView requirementListView;
	
	private RequirementListAdapter adapter;
	
	private GameModel game;
	
	private RequirementListListener listener;

	public GameSummaryFragment(){
		
	}
	
	public void setGame(GameModel game){
		this.game = game;		
	}
	
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
		
		ArrayList<GameRequirementModel> requirements = game.getRequirements();
		
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
	
	public interface RequirementListListener{
		public void requirementSelected(GameRequirementModel requirement);
	}
}
