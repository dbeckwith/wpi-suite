package edu.wpi.cs.team9.planningpoker;

import java.util.Arrays;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.team9.planningpoker.controller.GetGamesController;
import edu.wpi.cs.team9.planningpoker.controller.GetGamesController.GetGamesObserver;
import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.team9.planningpoker.model.GameModel.GameStatus;
import edu.wpi.cs.team9.planningpoker.view.GameListAdapter;

public class GameListActivity extends Activity implements GetGamesObserver {

	private static final String TAG = GameListActivity.class.getSimpleName();

	private TextView label;
	private ListView gameListView;
	private GameListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_game_list);
		//Intent intent = getIntent();
		
		String project = getIntent().getStringExtra("project");
		setTitle(String.format("PlanningPoker : %s", project));
		
		label = (TextView)findViewById(R.id.label);
		
		gameListView = (ListView) findViewById(R.id.gameList);
		adapter = new GameListAdapter(this);
		gameListView.setAdapter(adapter);
		
		gameListView.setOnItemClickListener(new OnItemClickListener() {			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getBaseContext(), RequirementActivity.class);
				
				String gameJson = ((GameModel)adapter.getItem(position)).toJSON();	
				
				intent.putExtra("game", gameJson);
				startActivity(intent);
				
			}
		});

		setProgressBarIndeterminateVisibility(true);
		GetGamesController.getInstance().setObserver(this);
		GetGamesController.getInstance().requestGames();
		CurrentUserController.getInstance();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		GetGamesController.getInstance().requestGames();
	}

	@Override
	public void receivedGames(final GameModel[] games) {
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
				if(games == null){
					label.setText("No Games Found");
					return;
				}
				label.setText("Games:");
			}
		});
		
		Arrays.sort(games, new Comparator<GameModel>() {
			@Override
			public int compare(GameModel lhs, GameModel rhs) {
				if(lhs.getStatus().equals(GameStatus.PENDING)){
					return -1;
				} else return 1;
			}
		});
		
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
