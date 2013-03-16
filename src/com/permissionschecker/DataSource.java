package com.permissionschecker;

import java.util.ArrayList;

/**
 * interface for a class that can be defined as a data source for the lists
 */
public interface DataSource {

    public abstract ArrayList<App> getAppsList();
    public abstract void setAppForDetail(App a);
    public abstract App getAppForDetail();
    public abstract void setPermissionDetailForDetail(PermissionDetail d);
    public abstract PermissionDetail getPermissionDetailForDetail();
}
