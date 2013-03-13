package com.permissionschecker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class App implements Parcelable {

	private String packageName;
	private String appName;
    private String[] permissionsList;
    private String[] permissionsListShortName;
    private byte[] iconBytes;
    public boolean isSystemApp;

    public App() {

    }

	public App(Parcel in) {
        packageName = in.readString();
        appName = in.readString();
        in.readStringArray(permissionsList);
        in.readStringArray(permissionsListShortName);
        in.readByteArray(iconBytes);
        isSystemApp = in.readByte() == 1;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(packageName);
        parcel.writeString(appName);
        parcel.writeStringArray(permissionsList);
        parcel.writeStringArray(permissionsListShortName);
        parcel.writeByteArray(iconBytes);
        parcel.writeByte((byte) (isSystemApp ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel parcel) {
            return new App(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new App[i];
        }
    };

	public BitmapDrawable getIcon(Context c) {
        Bitmap bmp = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length);
        return new BitmapDrawable(c.getResources(), bmp);
	}

	public void setIcon(BitmapDrawable icon) {
        Bitmap bitmap = icon.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        iconBytes = outputStream.toByteArray();
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

