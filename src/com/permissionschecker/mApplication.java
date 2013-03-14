package com.permissionschecker;

import android.app.Application;

import java.util.ArrayList;

public class mApplication extends Application implements DataSource {

    private ArrayList<App> appArrayList;
    private App appForDetail;
    private PermissionDetail permissionDetail;

    @Override
    public void onCreate() {
        super.onCreate();
        loadData();
    }

    @Override
    public ArrayList<App> getAppsList() {
        return appArrayList;
    }

    @Override
    public void setAppForDetail(App a) {
        appForDetail = a;
    }

    @Override
    public App getAppForDetail() {
        return appForDetail;
    }

    @Override
    public void setPermissionDetailForDetail(PermissionDetail d) {
       permissionDetail = d;
    }

    @Override
    public PermissionDetail getPermissionDetailForDetail() {
        return permissionDetail;
    }

    private void loadData() {
        PackageRetriever retriever = new PackageRetriever(this);
        appArrayList = retriever.getAllApps();
    }
}
