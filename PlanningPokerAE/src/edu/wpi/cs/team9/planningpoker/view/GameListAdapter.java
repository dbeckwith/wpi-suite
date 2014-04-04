package edu.wpi.cs.team9.planningpoker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.team9.planningpoker.model.GameModel.GameStatus;

public class GameListAdapter extends ArrayAdapter<GameModel> {

	public GameListAdapter(Context context) {
		super(context, R.layout.game_list_item);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View view;
		if(convertView == null){
			view = inflater.inflate(R.layout.game_list_item, null);
		} else {
			view = convertView;
		}
		
		GameModel game = getItem(position);
		ImageView statusView = (ImageView)view.findViewById(R.id.status);	
		TextView nameView = (TextView)view.findViewById(R.id.name);
		
		int icon = (game.isEnded())?R.drawable.ic_status_complete:R.drawable.ic_status_pending;
		
		statusView.setImageResource(icon);
		nameView.setText(game.getName());
		
		return view;
	}


}
