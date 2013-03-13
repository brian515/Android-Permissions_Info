package com.permissionschecker;

import java.util.Comparator;

public class AppNameComparator implements Comparator<App> {

	@Override
	public int compare(App a1, App a2) {
		return a1.getAppName().compareToIgnoreCase(a2.getAppName());
	}

}
