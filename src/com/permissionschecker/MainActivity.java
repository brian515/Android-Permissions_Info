package com.permissionschecker;

import android.app.*;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class MainActivity extends Activity {

	ViewPager viewPager;
	TabsAdapter tabsAdapter;
    private App appForDetail;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


        // set what's displayed by loading from the XML
        setContentView(R.layout.main);


        // setup tabs
		viewPager = (ViewPager)findViewById(R.id.pager);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		tabsAdapter = new TabsAdapter(this, viewPager);
        Bundle appsArgs = new Bundle();
        appsArgs.putBoolean("allApps", true);
		tabsAdapter.addTab(actionBar.newTab().setText("Apps"), AppsListFragment.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText("Permissions"), PermissionsListFragment.class, appsArgs);

		if (savedInstanceState != null)
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
        // save what tab we're on if the activity is killed
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}
}
