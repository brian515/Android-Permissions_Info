package com.permissionschecker;

import java.util.ArrayList;

public class PermissionGroup {

    private ArrayList<PermissionDetail> permissionDetails;

    public PermissionGroup() {
        permissionDetails = new ArrayList<PermissionDetail>();
    }

    public boolean add(PermissionDetail detail) {
        return permissionDetails.add(detail);
    }

    public ArrayList<PermissionDetail> getPermissionDetails() {
        return permissionDetails;
    }


}
