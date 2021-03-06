package com.permissionschecker;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class displays a list of permissions sorted by group and then sorted by name within the group
 */

public class PermissionsListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout from XML
        return inflater.inflate(R.layout.apps_list_fragment, container, false);
	}

    ArrayList<PermissionDetail> permissionsList_;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the list of all permissions, then sort them
        PermissionSort sorter = new PermissionSort(getActivity());
        ArrayList<App> appList;
        DataSource dataSource = (DataSource)getActivity().getApplication();
        boolean getAllApps = getArguments().getBoolean("allApps");
        if (getAllApps)
            appList = dataSource.getAppsList();
        else {
            appList = new ArrayList<App>();
            appList.add(dataSource.getAppForDetail());
        }
        ArrayList<PermissionDetail> permissionsList = sorter.sortAppsByPermission(appList);
        Collections.sort(permissionsList, new PermissionGroupComparator());
        permissionsList_ = addHeaders(permissionsList);

        // add the permissions to the list
        PermissionsArrayAdapter adapter = new PermissionsArrayAdapter(getActivity());
        setListAdapter(adapter);
        for (PermissionDetail p : permissionsList_)
            adapter.add(p);
    }

    private ArrayList<PermissionDetail> addHeaders(ArrayList<PermissionDetail> details) {
        // insert nulls into the list so that we can know where to put the headers in the list
        details.add(0, null);
        for (int i=1; i<details.size()-1; i++) {
            PermissionDetail cur = details.get(i);
            PermissionDetail next = details.get(i+1);
            try {
                if (!cur.permissionInfo.group.equalsIgnoreCase(next.permissionInfo.group)) {
                    details.add(i + 1, null);
                    i++;
                }
            }
            catch (NullPointerException e) {

            }

        }

        return details;
    }

    private String stripPrefixesFromPermission(String permissionName) {
        // get only the permission's name
        // (i.e. if the permission is android.permission.INTERNET, return INTERNET
        String[] components = permissionName.split("\\.");
        String name = components[components.length-1];
        return name.replace("_", " ");
    }

    @Override
    public void onCreateOptionsMenu(Menu m, MenuInflater inflater) {
        inflater.inflate(R.menu.permissions_list_menu, m);
    }

    @Override
    public void onResume() {
        super.onResume();
        getListView().setFastScrollEnabled(true);

        // setup what happens when an item in the list is clicked
        getListView().setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // show a dialog explaining what the permission does

                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle(permissionsList_.get(i).permissionName);
                dialog.setMessage(permissionsList_.get(i).permissionInfo.loadDescription(getActivity().getPackageManager()));
                dialog.show();

                /* This is broken but I don't have time to fix it now, so just show an alert saying what the permission does instead

                DataSource dataSource = (DataSource)getActivity().getApplication();
                dataSource.setPermissionDetailForDetail(permissionsList_.get(i));

                Intent detailIntent = new Intent(getActivity(), PermissionDetailActivity.class);
                startActivity(detailIntent);
                */
            }
        });
    }

    private class PermissionsArrayAdapter extends ArrayAdapter<PermissionDetail> {

        public PermissionsArrayAdapter(Context context) {
            super(context, R.layout.app_list_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            PermissionDetailViewHolder detailViewHolder = null;
            HeaderViewHolder headerViewHolder = null;
            int type = getItemViewType(position);

            if (row == null) {

                // if the row is null, inflate the layout
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (type == 0) {
                    // it's a header, inflate a header view
                    row = inflater.inflate(R.layout.list_header, null);
                    headerViewHolder = new HeaderViewHolder();
                    headerViewHolder.txTitle = (TextView)row.findViewById(R.id.header_textview);
                    row.setTag(headerViewHolder);
                }
                else {
                    // it's a "regular" row, so inflate that layout
                    row = inflater.inflate(R.layout.permission_list_item, null);
                    detailViewHolder = new PermissionDetailViewHolder();
                    detailViewHolder.txName = (TextView)row.findViewById(R.id.permission_name_textview);
                    row.setTag(detailViewHolder);
                }
            }
            else {
                if (type == 0) {
                    headerViewHolder = (HeaderViewHolder)row.getTag();
                }
                else {
                    detailViewHolder = (PermissionDetailViewHolder)row.getTag();
                }
            }


            // set the data in the list
            final PermissionDetail p = getItem(position);
            if (type == 0) {
                String headerTitle = "";
                if (getCount() == 1) {
                    headerTitle = getString(R.string.no_permissions_required);
                }
                else {
                    final PermissionDetail pNext = getItem(position+1);
                    headerTitle = pNext.permissionInfo.group;
                    if (headerTitle == null)
                        headerTitle = "OTHER";
                }
                headerViewHolder.txTitle.setText(stripPrefixesFromPermission(headerTitle));

            }
           else {
                detailViewHolder.txName.setText(p.permissionName);
           }
            return row;
        }

        @Override
        public int getItemViewType(int position) {
            if (getItem(position) == null)
                return 0;
            else
                return 1;

            // if it's a header, return 0, otherwise return 1
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEnabled (int position) {
            if (getItemViewType(position) == 0)
                return false;
            else
                return true;
        }

    }

    static class PermissionDetailViewHolder {
        TextView txName;
    }

    static class HeaderViewHolder {
        TextView txTitle;
    }

}
