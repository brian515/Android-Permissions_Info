package com.permissionschecker;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;

import java.util.ArrayList;

public class AppDetailActivity extends Activity {

	private App app;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.app_detail);


        DataSource dataSource = (DataSource)getApplication();
        app = dataSource.getAppForDetail();

        PermissionsListFragment permissionsListFragment = new PermissionsListFragment();
        ArrayList<App> appList = new ArrayList<App>(1);
        appList.add(app);
        Bundle args = new Bundle();
        args.putBoolean("allApps", false);
        permissionsListFragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.app_detail_permissions_fragment,permissionsListFragment).commit();

		setupContent();
	}

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

	private void setupContent() {
		ImageView iconImageView = (ImageView)findViewById(R.id.app_detail_icon_imageview);
		iconImageView.setImageDrawable(app.getIcon(this));

		TextView nameTextView = (TextView)findViewById(R.id.app_detail_name_textview);
		nameTextView.setText(app.getAppName());

		TextView packageTextView = (TextView)findViewById(R.id.app_detail_package_textview);
		packageTextView.setText(app.getPackageName());
	}

}