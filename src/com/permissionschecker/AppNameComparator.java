package com.permissionschecker;

import java.util.Comparator;

/**
 * Implement Comparator so that Apps can be sorted by name
 */

public class AppNameComparator implements Comparator<App> {

	@Override
	public int compare(App a1, App a2) {
		return a1.getAppName().compareToIgnoreCase(a2.getAppName());
	}

}
