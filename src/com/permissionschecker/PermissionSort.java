package com.permissionschecker;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

public class PermissionSort {

    private Context mContext;

    public PermissionSort(Context c) {
        mContext = c;
    }

    public ArrayList<PermissionDetail> sortAppsByPermission(ArrayList<App> apps) {
        HashMap<String, PermissionDetail> map = new HashMap<String, PermissionDetail>();
        for (App app : apps) {
            if (app.getPermissionsList() != null) { // some apps require no permissions to run
                for (int i=0; i<app.getPermissionsList().length; i++) {
                    String permission = app.getPermissionsList()[i];
                    if (map.containsKey(permission)) {
                        // if this bucket already exists, add the current app to it
                        PermissionDetail d = map.get(permission);
                        d.addApp(app);
                    }
                    else {
                        // if the bucket doesn't exist, make it, then add the app to it
                        PermissionDetail newD = new PermissionDetail(permission, mContext);
                        newD.addApp(app);
                        map.put(permission, newD);
                    }
                }
            }
        }
        // pull the PermissionDetails out of the HashMap and put them into an ArrayList that we can use to make the list
        ArrayList<PermissionDetail> returnList = new ArrayList<PermissionDetail>(map.keySet().size());
        for (String key : map.keySet()) {
            returnList.add(map.get(key));
        }
        return returnList;
    }
}
