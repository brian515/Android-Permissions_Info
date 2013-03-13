package com.permissionschecker;

import android.content.Context;
import android.content.pm.*;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PackageRetriever {

	private Context context;

	public PackageRetriever(Context c) {
		/**
		 * Construct a new {@link PackageRetriever} object with {@link Context} object
		 */
		this.context = c;
	}

	public ArrayList<App> getAllApps() {
		final List<ApplicationInfo> packageList = context.getPackageManager().getInstalledApplications(0);

		ArrayList<App> returnList = new ArrayList<App>();

		for (ApplicationInfo info : packageList) {
			PackageInfo packageInfo = null;
			try {
				packageInfo = context.getPackageManager().getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
			}
			catch (PackageManager.NameNotFoundException e) {
				Log.i("debug", e.getLocalizedMessage());
			}
			App a = new App();
			a.setAppName(info.loadLabel(context.getPackageManager()).toString());
			a.setIcon(info.loadIcon(context.getPackageManager()));
			a.setPackageName(info.packageName);
			a.setPermissionsList(packageInfo.requestedPermissions);

            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                a.isSystemApp = false;
            }
            else {
                a.isSystemApp = true;
            }

			returnList.add(a);
		}

		return returnList;
	}
}
