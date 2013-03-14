package com.permissionschecker;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: briancharous
 * Date: 3/14/13
 * Time: 3:06 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DataSource {

    public abstract ArrayList<App> getAppsList();
    public abstract void setAppForDetail(App a);
    public abstract App getAppForDetail();
    public abstract void setPermissionDetailForDetail(PermissionDetail d);
    public abstract PermissionDetail getPermissionDetailForDetail();
}
