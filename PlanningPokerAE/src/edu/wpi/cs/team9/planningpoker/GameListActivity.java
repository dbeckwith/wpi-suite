/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
import edu.wpi.cs.team9.planningpoker.view.GameListAdapter;

/**
 * Displays a list of started, ended, and completed games to the user
 * @author akshay
 *
 */
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
		
		//get the project's name, set it to the title
		String project = getIntent().getStringExtra("project");
		setTitle(String.format("PlanningPoker : %s", project));
		
		label = (TextView)findViewById(R.id.label);
		
		gameListView = (ListView) findViewById(R.id.gameList);
		adapter = new GameListAdapter(this);
		gameListView.setAdapter(adapter);
		
		//when an item is clicked, show the game activity
		gameListView.setOnItemClickListener(new OnItemClickListener() {			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getBaseContext(), RequirementActivity.class);
				
				//stringify the game and pass it to the new activity
				String gameJson = ((GameModel)adapter.getItem(position)).toJSON();	
				
				intent.putExtra("game", gameJson);
				startActivity(intent); //start the requirement activity
				
			}
		});

		setProgressBarIndeterminateVisibility(true);
		GetGamesController.getInstance().requestGames();
		CurrentUserController.getInstance();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		GetGamesController.getInstance().setObserver(this);
		GetGamesController.getInstance().requestGames();
	}

	@Override
	public void receivedGames(final GameModel[] games) {
		//set the label to show whether games were found
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
				if(games == null || games.length == 0){
					label.setText("No Games Found");
					return;
				}
				label.setText("Games:");
			}
		});
		
		//sort the games by their status
		if(games != null){
			Arrays.sort(games, new Comparator<GameModel>() {
				@Override
				public int compare(GameModel lhs, GameModel rhs) {
					if(lhs.isClosed())
						return 1;
					if(lhs.isStarted()){
						return -1;
					}

					return 0;
				}
			});
		
		
			// add the games to the list
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Log.d(TAG, "reveivedGames()");
					adapter.clear();
					for (GameModel game : games) {
						if(game.isStarted() || game.isEnded() || game.isClosed())
							adapter.add(game);
					}
					adapter.notifyDataSetChanged();
					setProgressBarIndeterminateVisibility(false);
				}
			});
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh: // refresh games when the refresh button tis pressed
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
