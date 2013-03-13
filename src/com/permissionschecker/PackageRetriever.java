package com.permissionschecker;

import android.content.Context;
import android.content.pm.*;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: briancharous
 * Date: 3/6/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class PackageRetriever {

	private ArrayList<App> appList;
	private Context context;

	public PackageRetriever(Context c) {
		/**
		 * Construct a new {@link PackageRetriever} object with {@link Context} object
		 */
		this.context = c;
		appList = new ArrayList<App>();
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

			returnList.add(a);
		}

		return returnList;
	}

	public ArrayList<App> getUserInstalledApps() {
		final List<ApplicationInfo> packageList = context.getPackageManager().getInstalledApplications(0);

		ArrayList<App> returnList = new ArrayList<App>();

		for (ApplicationInfo info : packageList) {
			if ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
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

				returnList.add(a);
			}
		}

		return returnList;
	}
}
