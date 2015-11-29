package com.federicobarocci.wifiexplorer.module;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseManager;
import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.CurrentWifiList;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.SessionWifiList;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListContainer;
import com.federicobarocci.wifiexplorer.ui.adapter.FavouritesAdapter;
import com.federicobarocci.wifiexplorer.ui.adapter.ScanResultAdapter;
import com.federicobarocci.wifiexplorer.ui.adapter.controller.SnackBarUndoFavourites;
import com.federicobarocci.wifiexplorer.ui.adapter.controller.SnackBarUndoMain;
import com.federicobarocci.wifiexplorer.ui.presenter.ScanResultReceiver;
import com.federicobarocci.wifiexplorer.ui.presenter.TaskExecutor;
import com.federicobarocci.wifiexplorer.ui.presenter.WifiUtilDelegate;
import com.federicobarocci.wifiexplorer.ui.util.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by federico on 20/10/15.
 */
@Module
public class DaggerModule {
    private final WifiExplorerApplication application;

    public DaggerModule(WifiExplorerApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    WifiManager provideWifiManager() {
        return (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
    }

    @Provides
    @Singleton
    CurrentWifiList provideCurrentWifiList() {
        return new CurrentWifiList();
    }

    @Provides
    @Singleton
    SessionWifiList provideSessionWifiList() {
        return new SessionWifiList();
    }

    @Provides
    @Singleton
    LocationHandler provideLocationHandler(Context context, DataBaseManager dataBaseManager) {
        return new LocationHandler(context, dataBaseManager);
    }

    @Provides
    @Singleton
    WifiListContainer provideWifiListContainer(CurrentWifiList currentWifiList, SessionWifiList sessionWifiList) {
        return new WifiListContainer(currentWifiList, sessionWifiList);
    }

    @Provides
    @Singleton
    WifiKeeper provideWifiKeeper(WifiListContainer wifiListContainer) {
        return new WifiKeeper(wifiListContainer);
    }

    @Provides
    @Singleton
    DataBaseManager provideDataBaseManager(Context context) {
        return new DataBaseManager(context);
    }

    @Provides
    @Singleton
    DataBaseHandler provideDataBaseHandler(DataBaseManager dataBaseManager, LocationHandler locationHandler) {
        return new DataBaseHandler(dataBaseManager, locationHandler);
    }

    @Provides
    @Singleton
    SnackBarUndoMain provideSnackBarUndoMain(DataBaseHandler dataBaseHandler) {
        return new SnackBarUndoMain(dataBaseHandler);
    }

    @Provides
    @Singleton
    SnackBarUndoFavourites provideSnackBarUndoFavourites(DataBaseHandler dataBaseHandler) {
        return new SnackBarUndoFavourites(dataBaseHandler);
    }

    @Provides
    @Singleton
    WifiUtilDelegate provideWifiUtilDelegate(WifiManager wifiManager, WifiKeeper wifiKeeper, ScanResultAdapter adapter) {
        return new WifiUtilDelegate(wifiManager, wifiKeeper, adapter);
    }

    @Provides
    @Singleton
    ResourceProvider provideResourceProvider(WifiKeeper wifiKeeper, DataBaseHandler dataBaseHandler) {
        return new ResourceProvider(wifiKeeper, dataBaseHandler);
    }

    @Provides
    @Singleton
    ScanResultAdapter provideScanResultAdapter(WifiKeeper wifiKeeper, SnackBarUndoMain snackBarUndoMain, ResourceProvider resourceProvider) {
        return new ScanResultAdapter(wifiKeeper, snackBarUndoMain, resourceProvider);
    }

    @Provides
    @Singleton
    FavouritesAdapter provideFavouritesAdapter(DataBaseHandler dataBaseHandler, SnackBarUndoFavourites snackBarUndoFavourites, ResourceProvider resourceProvider) {
        return new FavouritesAdapter(dataBaseHandler, snackBarUndoFavourites, resourceProvider);
    }

    @Provides
    @Singleton
    ScanResultReceiver provideScanResultReceiver(WifiKeeper wifiKeeper, DataBaseHandler dataBaseHandler, LocationHandler locationHandler, WifiManager wifiManager, ScanResultAdapter scanResultAdapter, FavouritesAdapter favouritesAdapter) {
        return new ScanResultReceiver(wifiKeeper, dataBaseHandler, locationHandler, wifiManager, scanResultAdapter, favouritesAdapter);
    }

    @Provides
    @Singleton
    TaskExecutor provideTaskExecutor(Context context, WifiUtilDelegate wifiUtilDelegate, ScanResultReceiver scanResultReceiver) {
        return new TaskExecutor(context, wifiUtilDelegate, scanResultReceiver);
    }
}
