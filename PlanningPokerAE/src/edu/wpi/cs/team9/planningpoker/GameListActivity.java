package edu.wpi.cs.team9.planningpoker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import edu.wpi.cs.team9.planningpoker.controller.GetGamesController;
import edu.wpi.cs.team9.planningpoker.controller.GetGamesController.GetGamesObserver;
import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.team9.planningpoker.view.GameListAdapter;

public class GameListActivity extends Activity implements GetGamesObserver {

	private static final String TAG = GameListActivity.class.getSimpleName();

	private ExpandableListView gameListView;
	private GameListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_game_list);

		gameListView = (ExpandableListView) findViewById(R.id.gameList);
		adapter = new GameListAdapter(this);
		gameListView.setAdapter(adapter);
		
		gameListView.setOnChildClickListener(new OnChildClickListener() {			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent(getBaseContext(), RequirementActivity.class);
				
				String gameJson = ((GameModel)adapter.getGroup(groupPosition)).toJSON();	
				
				intent.putExtra("game", gameJson);
				intent.putExtra("index", childPosition);
				startActivity(intent);
				return true;
			}
		});

		setProgressBarIndeterminateVisibility(true);
		GetGamesController.getInstance().setObserver(this);
		GetGamesController.getInstance().requestGames();
	}

	@Override
	public void receivedGames(final GameModel[] games) {
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "reveivedGames()");
				adapter.clear();
				for (GameModel game : games) {
					adapter.add(game);
				}
				adapter.notifyDataSetChanged();
				setProgressBarIndeterminateVisibility(false);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			setProgressBarIndeterminateVisibility(true);
			GetGamesController.getInstance().requestGames();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_list, menu);
		return true;
	}

}
