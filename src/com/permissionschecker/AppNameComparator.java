package com.permissionschecker;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: briancharous
 * Date: 3/7/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppNameComparator implements Comparator<App> {

	@Override
	public int compare(App a1, App a2) {
		return a1.getAppName().compareToIgnoreCase(a2.getAppName());
	}

}
