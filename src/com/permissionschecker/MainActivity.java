package com.permissionschecker;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class MainActivity extends Activity {

	ViewPager viewPager;
	TabsAdapter tabsAdapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		viewPager = (ViewPager)findViewById(R.id.pager);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		tabsAdapter = new TabsAdapter(this, viewPager);
		tabsAdapter.addTab(actionBar.newTab().setText("Apps"), appsListFragment.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText("Permissions"), PermissionsListFragment.class, null);

		if (savedInstanceState != null)
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}
}
