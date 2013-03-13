package com.permissionschecker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class AppDetailActivity extends Activity {

	private App app;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.app_detail);

		app = new App();
		app.setAppName(getIntent().getStringExtra("name"));
		app.setPackageName(getIntent().getStringExtra("package"));
		app.setPermissionsList(getIntent().getStringArrayExtra("permissions"));
        app.setPermissionsListShortName(getIntent().getStringArrayExtra("permissionsshort"));

		byte[] b = getIntent().getByteArrayExtra("icon");
		Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
		app.setIcon(new BitmapDrawable(getResources(),bmp));


		setupContent();
	}

	private void setupContent() {
		ImageView iconImageView = (ImageView)findViewById(R.id.app_detail_icon_imageview);
		iconImageView.setImageDrawable(app.getIcon());

		TextView nameTextView = (TextView)findViewById(R.id.app_detail_name_textview);
		nameTextView.setText(app.getAppName());

		TextView packageTextView = (TextView)findViewById(R.id.app_detail_package_textview);
		packageTextView.setText(app.getPackageName());

        ListView permissionListView = (ListView)findViewById(R.id.app_detail_permissions_listview);
        PermissionsArrayAdapter adapter = new PermissionsArrayAdapter(this);
        if (app.getPermissionsList() == null)
            adapter.add(getString(R.string.no_permissions_required));
        else
            adapter.addAll(app.getPermissionsListShortName());

        permissionListView.setAdapter(adapter);
        permissionListView.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showInfoDialog(app.getPermissionsList()[i]);
            }
        });
	}

    private void showInfoDialog(String permissionName) {
        String permissionDescription;
        PermissionInfo pi = null;
        try {
            pi = getPackageManager().getPermissionInfo(permissionName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("debug", e.getLocalizedMessage());
        }
        try {
            permissionDescription = pi.loadDescription(getPackageManager()).toString();
        }
        catch (NullPointerException e) {
            permissionDescription = getString(R.string.no_permission_info);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(permissionName);
        builder.setMessage(permissionDescription);
        AlertDialog infoDialog = builder.create();
        infoDialog.show();
    }

    private class PermissionsArrayAdapter extends ArrayAdapter<String> {

        public PermissionsArrayAdapter(Context context) {
            super(context, R.layout.permission_list_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            PermissionsViewHolder viewHolder = null;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.permission_list_item, null);
                viewHolder = new PermissionsViewHolder();
                viewHolder.txPermissionName = (TextView)row.findViewById(R.id.permission_name_textview);
                row.setTag(viewHolder);
            }
            else {
                viewHolder = (PermissionsViewHolder)row.getTag();
            }
            viewHolder.txPermissionName.setText(getItem(position));

            return row;
        }
    }

    static class PermissionsViewHolder {
        TextView txPermissionName;
    }
}