package com.permissionschecker;

import android.graphics.drawable.Drawable;

public class App {

	private String packageName;
	private String appName;
    private String[] permissionsList;
    private String[] permissionsListShortName;
	private Drawable icon;
    public boolean isSystemApp;

	public App() {

	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String[] getPermissionsList() {
		return permissionsList;
	}

	public void setPermissionsList(String[] permissionsList) {
		this.permissionsList = permissionsList;

        if (permissionsList != null) {
            permissionsListShortName = new String[permissionsList.length];
            for (int i=0; i<permissionsList.length; i++) {
                permissionsListShortName[i] = stripPrefixesFromPermission(permissionsList[i]);
            }
        }
        else {
            permissionsListShortName = null;
        }
	}

    private String stripPrefixesFromPermission(String permissionName) {
        String[] components = permissionName.split("\\.");
        return components[components.length-1];
    }

    public String[] getPermissionsListShortName() {
        return permissionsListShortName;
    }

    public void setPermissionsListShortName(String[] permissionsListShortName) {
        this.permissionsListShortName = permissionsListShortName;
    }
}

