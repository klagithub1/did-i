package si.modrajagoda.didi;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import si.modrajagoda.didi.database.DatabaseHelper;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class Main extends FragmentActivity implements ActionBar.TabListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static final String TAB_TAG_HABITS = "habits";
	private static final String TAB_TAG_PROGRESS = "progress";

	private DatabaseHelper databaseHelper = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.title_habits)
				.setTag(TAB_TAG_HABITS).setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_progress)
				.setTag(TAB_TAG_PROGRESS).setTabListener(this));
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, show the tab contents in the
		// container
		if (tab.getTag() == TAB_TAG_HABITS) {
			Fragment fragment = new FragmentHabits();
			Bundle args = new Bundle();
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		}
		if (tab.getTag() == TAB_TAG_PROGRESS) {
			Fragment fragment = new FragmentProgress();
			Bundle args = new Bundle();
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
		}

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this,
					DatabaseHelper.class);
		}
		return databaseHelper;
	}

}
