package com.permissionschecker;

import java.util.Comparator;

public class AppPermissionsComparator implements Comparator<App> {

	@Override
	public int compare(App a1, App a2) {
		// sort by permissions in descending order
		Integer a1NumPermissions = 0;
		Integer a2NumPermissions = 0;
		if (a1.getPermissionsList() != null)
			a1NumPermissions = a1.getPermissionsList().length;
		if (a2.getPermissionsList() != null)
			a2NumPermissions = a2.getPermissionsList().length;

		return a2NumPermissions.compareTo(a1NumPermissions);
	}

}
