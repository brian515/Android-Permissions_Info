package com.permissionschecker;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class MainActivity extends Activity {

	ViewPager viewPager;
	TabsAdapter tabsAdapter;
    public ArrayList<App> appArrayList;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        loadData();

        setContentView(R.layout.main);

		viewPager = (ViewPager)findViewById(R.id.pager);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		tabsAdapter = new TabsAdapter(this, viewPager);
		tabsAdapter.addTab(actionBar.newTab().setText("Apps"), AppsListFragment.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText("Permissions"), PermissionsListFragment.class, null);

		if (savedInstanceState != null)
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));

	}

    private void loadData() {
        PackageRetriever retriever = new PackageRetriever(this);
        appArrayList = retriever.getAllApps();
    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}
}
