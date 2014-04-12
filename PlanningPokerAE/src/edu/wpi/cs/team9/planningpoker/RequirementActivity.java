package edu.wpi.cs.team9.planningpoker;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import edu.wpi.cs.team9.planningpoker.controller.UpdateGamesController;
import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.team9.planningpoker.view.GameSummaryFragment;
import edu.wpi.cs.team9.planningpoker.view.GameSummaryFragment.RequirementListListener;
import edu.wpi.cs.team9.planningpoker.view.RequirementFragment;
import edu.wpi.cs.team9.planningpoker.view.RequirementFragment.RequirementFragmentListener;

public class RequirementActivity extends Activity implements RequirementFragmentListener {

	private static final String TAG = RequirementActivity.class.getSimpleName();

	RequirementPagerAdapter requirementAdapter;
	ViewPager viewPager;

	GameModel game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_requirement);
		
		game = new Gson().fromJson(getIntent().getExtras().getString("game"), GameModel.class);
		
		setTitle(String.format("PlanningPoker : %s", game.getName()));
				
		requirementAdapter = new RequirementPagerAdapter(getFragmentManager(), game, this);

		
		viewPager = (ViewPager) findViewById(R.id.pager);

		viewPager.setOffscreenPageLimit(game.getRequirements().size()+1);
		viewPager.setAdapter(requirementAdapter);
	
		viewPager.setCurrentItem(getIntent().getExtras().getInt("index"));
		
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.requirement, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			return true;
		} else if(id ==  android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void updated(GameRequirementModel requirement) {
		UpdateGamesController.getInstance().updateGame(game);		
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class RequirementPagerAdapter extends FragmentPagerAdapter {
		
		private GameModel game;
		private RequirementFragmentListener reqListener;
		
		public RequirementPagerAdapter(FragmentManager fm, GameModel game, RequirementFragmentListener rfl) {
			super(fm);
			this.game = game;
			this.reqListener = rfl;
		}

		@Override
		public Fragment getItem(int position) {
			if(position == 0){
				GameSummaryFragment gsf = new GameSummaryFragment();
				gsf.setGame(game);
				gsf.setListener(new RequirementListListener() {					
					@Override
					public void requirementSelected(GameRequirementModel selected) {
						viewPager.setCurrentItem(getTitleIndex(selected.getName()), true);						
					}
				});
				return gsf;
			} else {
				RequirementFragment rf = new RequirementFragment();
				rf.setRequirement(game.getRequirements().get(position-1));
				rf.setListener(reqListener);
				return rf;
			}
		}
		
		public int getTitleIndex(String s){
			ArrayList<GameRequirementModel> reqs = game.getRequirements();
			for(GameRequirementModel r:reqs){
				if(r.getName().equals(s)){
					return reqs.indexOf(r) + 1;
				}
			}
			return 0;
		}

		@Override
		public int getCount() {
			return game.getRequirements().size()+1;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if(position == 0){
				return game.getName();
			} else {
				return game.getRequirements().get(position-1).getName();
			}
		}
	}

}
