package com.permissionschecker;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PermissionsListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.apps_list_fragment, container, false);
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        PermissionSort sorter = new PermissionSort(getActivity());
        MainActivity mainActivity = (MainActivity)getActivity();
        ArrayList<App> appList = mainActivity.appArrayList;
        ArrayList<PermissionDetail> permissionsList = sorter.sortAppsByPermission(appList);

        PermissionsArrayAdapter adapter = new PermissionsArrayAdapter(getActivity());
        setListAdapter(adapter);
        for (PermissionDetail p : permissionsList)
            adapter.add(p);

    }

    @Override
    public void onCreateOptionsMenu(Menu m, MenuInflater inflater) {
        inflater.inflate(R.menu.permissions_list_menu, m);
    }

    @Override
    public void onResume() {
        super.onResume();

        ListView listView = getListView();

    }

    private class PermissionsArrayAdapter extends ArrayAdapter<PermissionDetail> {

        public PermissionsArrayAdapter(Context context) {
            super(context, R.layout.app_list_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            PermissionDetailViewHolder viewHolder = null;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.app_list_item, null);
                viewHolder = new PermissionDetailViewHolder();
                viewHolder.txName = (TextView)row.findViewById(R.id.app_name_textview);
                row.setTag(viewHolder);
            }
            else {
                viewHolder = (PermissionDetailViewHolder)row.getTag();
            }

            final PermissionDetail p = getItem(position);
            viewHolder.txName.setText(p.permissionName);
            return row;
        }
    }

    static class PermissionDetailViewHolder {
        TextView txName;
    }

}
