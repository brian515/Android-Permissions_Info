package com.permissionschecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * This class isn't ready yet (the class works, but the data isn't), but in theory, it should display a permission's name and description with a list of all
 * the apps that request that permission
 */

public class PermissionDetailActivity extends Activity {

    private PermissionDetail detail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.permission_detail);

        DataSource dataSource = (DataSource)getApplication();
        detail = dataSource.getPermissionDetailForDetail();

        TextView nameTextView = (TextView)findViewById(R.id.permission_detail_name_textview);
        nameTextView.setText(detail.permissionName);
        if (detail.permissionInfo != null) {
            //try {
            TextView descTextView = (TextView)findViewById(R.id.permission_description_textview);
            descTextView.setText(detail.permissionInfo.loadDescription(getPackageManager()));
            //}
            //catch ()
        }

        AppsListFragment appsListFragment = new AppsListFragment();
        Bundle appsBundle = new Bundle();
        appsBundle.putParcelableArrayList("data", detail.matchingApps);
        appsListFragment.setArguments(appsBundle);
        getFragmentManager().beginTransaction().replace(R.id.permission_apps_permissions_fragment,appsListFragment).commit();

    }
}