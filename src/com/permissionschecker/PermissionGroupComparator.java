package com.permissionschecker;

import java.util.Comparator;

/**
 * Class that compares permissions by group so that they can be sorted by group
 */

public class PermissionGroupComparator implements Comparator<PermissionDetail> {

	@Override
	public int compare(PermissionDetail p1, PermissionDetail p2) {
        if (p1.permissionInfo != null && p2.permissionInfo != null)
            if (p1.permissionInfo.group != null && p2.permissionInfo.group != null)
                if(p1.permissionInfo.group.compareToIgnoreCase(p2.permissionInfo.group) == 0)
                    return p1.permissionName.compareToIgnoreCase(p2.permissionName);
                else
                    return p1.permissionInfo.group.compareToIgnoreCase(p2.permissionInfo.group);
            else if (p1.permissionInfo.group == null && p2.permissionInfo.group != null)
                return 1;
            else if (p2.permissionInfo.group == null && p1.permissionInfo.group != null)
                return -1;
            else
                return 0;
        else
            if (p1.permissionInfo == null)
                return 1;
            if (p2.permissionInfo == null)
                return -1;
        return 0;
	}

}
