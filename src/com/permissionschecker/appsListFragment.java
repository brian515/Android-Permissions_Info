package com.permissionschecker;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Fragment that displays a list of apps and is sortable by app name and number of permissions that it requests
 */

public class AppsListFragment extends ListFragment {

	private ArrayList<App> appArrayList;
    private ArrayList<App> origAppArrayList;
	private SortOrder sortOrder;
	private VisibleApps visibleApps;
	Menu menu;

	private enum SortOrder {
		NAME, NUM_PERMISSIONS
	}

	private enum VisibleApps {
		ALL, USER_INSTALLED
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout
		return inflater.inflate(R.layout.apps_list_fragment, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

        DataSource dataSource = (DataSource)getActivity().getApplication();
        origAppArrayList = dataSource.getAppsList();

        loadInitialData();
    }

	@Override
	public void onResume() {
		super.onResume();

        getListView().setFastScrollEnabled(true);


        // set what happens when an item in the is clicked
		ListView listView = getListView();
		listView.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent detailIntent = new Intent(getActivity(), AppDetailActivity.class);
				App a = appArrayList.get(i);
                DataSource dataSource = (DataSource)getActivity().getApplication();
                dataSource.setAppForDetail(a);
				startActivity(detailIntent);
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu m, MenuInflater inflater) {
		menu = m;
		inflater.inflate(R.menu.app_list_menu, menu);
		updateMenuTitles();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        // setup what happens when menu items are clicked
		switch (item.getItemId()) {
			case R.id.sort:
				if (sortOrder == SortOrder.NAME) {
					sortOrder = SortOrder.NUM_PERMISSIONS;
					sort(SortOrder.NUM_PERMISSIONS);
				}
				else {
					sortOrder = SortOrder.NAME;
					sort(SortOrder.NAME);
				}
				updateMenuTitles();
				reloadArrayAdapter();
				return true;
			case R.id.showHideSystemApps:
				if (visibleApps == VisibleApps.ALL) {
                    getOnlyUserInstalledApps();
   					visibleApps = VisibleApps.USER_INSTALLED;
				}
				else if (visibleApps == VisibleApps.USER_INSTALLED) {
					getAllApps();
					visibleApps = VisibleApps.ALL;
				}
				updateMenuTitles();
				sort(sortOrder);
				reloadArrayAdapter();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void updateMenuTitles() {
        // set menu titles since the menu can change based on how the list is sorted
		MenuItem showHideSystemAppsItem = menu.findItem(R.id.showHideSystemApps);
		if (visibleApps == VisibleApps.USER_INSTALLED)
			showHideSystemAppsItem.setTitle(R.string.showSystemApps);
		else
			showHideSystemAppsItem.setTitle(R.string.hideSystemApps);

		MenuItem sortItem = menu.findItem(R.id.sort);
		if (sortOrder == SortOrder.NUM_PERMISSIONS)
			sortItem.setTitle(R.string.sortName);
		else
			sortItem.setTitle(R.string.sortNumPermissions);
	}

	private void loadInitialData() {
        // show only user installed apps sorted by number of permissions
		visibleApps = VisibleApps.USER_INSTALLED;
		sortOrder = SortOrder.NUM_PERMISSIONS;
        getAllApps();
        getOnlyUserInstalledApps();
		sort(SortOrder.NUM_PERMISSIONS);
	}

    private void getAllApps() {
        // copy appArrayList from the activity so that we can modify it without messing with the
        // other views
        appArrayList = null;
        appArrayList = new ArrayList<App>();
        appArrayList.addAll(origAppArrayList);
    }

    private void getOnlyUserInstalledApps() {
        // remove system appsf rom the list
        appArrayList.clear();
        for (App a : origAppArrayList) {
            if (!a.isSystemApp)
                this.appArrayList.add(a);
        }
    }

	private void sort(SortOrder s) {
        // sort the list by the correct sort order
		switch (s) {
			case NAME:
				Collections.sort(appArrayList, new AppNameComparator());
				break;
			case NUM_PERMISSIONS:
				Collections.sort(appArrayList, new AppPermissionsComparator());
				break;
			default:
				break;
		}
		reloadArrayAdapter();
	}

	private void reloadArrayAdapter() {
        // after the data is changed, reload the list
		AppsArrayAdapter adapter = new AppsArrayAdapter(getActivity());
		setListAdapter(adapter);
		for (App a : appArrayList)
			adapter.add(a);
	}

	private class AppsArrayAdapter extends ArrayAdapter<App> {
        // setup and draw the list

		public AppsArrayAdapter(Context context) {
			super(context, R.layout.app_list_item);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			AppViewHolder viewHolder = null;

			if (row == null) {
                // if the row hasn't been created yet, inflate a layout and setup a view holder
				LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.app_list_item, null);
				viewHolder = new AppViewHolder();
				viewHolder.txAppName = (TextView)row.findViewById(R.id.app_name_textview);
				viewHolder.ivAppIcon = (ImageView)row.findViewById(R.id.app_icon_imageview);
				viewHolder.txAppNumberPermissions = (TextView)row.findViewById(R.id.app_number_permissions_textview);
				row.setTag(viewHolder);
			}
			else {
				viewHolder = (AppViewHolder)row.getTag();
			}

            // display the correct information the data in the row
			final App a = getItem(position);
			viewHolder.ivAppIcon.setImageDrawable(a.getIcon(getActivity()));
			viewHolder.txAppName.setText(a.getAppName());
			int permissionsCount = 0;
			if (a.getPermissionsList() != null)
				permissionsCount = a.getPermissionsList().length;

			if (permissionsCount != 1)
				viewHolder.txAppNumberPermissions.setText(permissionsCount + " " + getString(R.string.permissions));
			else
				viewHolder.txAppNumberPermissions.setText(permissionsCount + " " + getString(R.string.permission));

			return row;
		}
	}

	static class AppViewHolder {
		TextView txAppName;
		TextView txAppNumberPermissions;
		ImageView ivAppIcon;
    }

}
