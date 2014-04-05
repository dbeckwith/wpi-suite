package edu.wpi.cs.team9.planningpoker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.team9.planningpoker.view.RequirementFragment;

public class RequirementActivity extends Activity implements
		ActionBar.TabListener {

	private static final String TAG = RequirementActivity.class.getSimpleName();

	RequirementPagerAdapter requirementAdapter;
	ViewPager viewPager;

	GameModel game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_requirement);
		
		game = new Gson().fromJson(getIntent().getExtras().getString("game"), GameModel.class);
		
		Log.d(TAG, "1");
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


		Log.d(TAG, "2");
		
		requirementAdapter = new RequirementPagerAdapter(getFragmentManager(), game);

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(requirementAdapter);


		Log.d(TAG, "3");
		
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
		

		Log.d(TAG, "4");

		for (int i = 0; i < requirementAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(requirementAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		

		Log.d(TAG, "5");
		
		viewPager.setCurrentItem(getIntent().getExtras().getInt("index"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.requirement, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class RequirementPagerAdapter extends FragmentPagerAdapter {
		
		private GameModel game;

		public RequirementPagerAdapter(FragmentManager fm, GameModel game) {
			super(fm);
			this.game = game;
		}

		@Override
		public Fragment getItem(int position) {
			RequirementFragment rf = new RequirementFragment();
			rf.setRequirement(game.getRequirements().get(position));
			return rf;
		}

		@Override
		public int getCount() {
			return game.getRequirements().size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return game.getRequirements().get(position).getName();
		}
	}



}
