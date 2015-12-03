package com.federicobarocci.wifiexplorer.module;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseManager;
import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListContainer;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.CurrentWifiList;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.SessionWifiList;
import com.federicobarocci.wifiexplorer.ui.adapter.FavouritesAdapter;
import com.federicobarocci.wifiexplorer.ui.adapter.ScanResultAdapter;
import com.federicobarocci.wifiexplorer.ui.adapter.controller.SnackBarUndoFavourites;
import com.federicobarocci.wifiexplorer.ui.adapter.controller.SnackBarUndoMain;
import com.federicobarocci.wifiexplorer.ui.presenter.DataSetExecutor;
import com.federicobarocci.wifiexplorer.ui.presenter.DataSetHandler;
import com.federicobarocci.wifiexplorer.ui.presenter.FilterDelegate;
import com.federicobarocci.wifiexplorer.ui.util.ResourceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Federico
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
    DataBaseHandler provideDataBaseHandler(DataBaseManager dataBaseManager, LocationHandler locationHandler, WifiKeeper wifiKeeper) {
        return new DataBaseHandler(dataBaseManager, locationHandler, wifiKeeper);
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
    FilterDelegate provideFilterDelegate(WifiKeeper wifiKeeper, ScanResultAdapter scanResultAdapter) {
        return new FilterDelegate(wifiKeeper, scanResultAdapter);
    }

    @Provides
    @Singleton
    DataSetExecutor provideDataSetExecutor(WifiKeeper wifiKeeper, DataBaseHandler dataBaseHandler, LocationHandler locationHandler, WifiManager wifiManager) {
        return new DataSetExecutor(wifiKeeper, dataBaseHandler, locationHandler, wifiManager);
    }

    @Provides
    @Singleton
    DataSetHandler provideDataSetHandler(DataSetExecutor dataSetExecutor, FavouritesAdapter favouritesAdapter, ScanResultAdapter scanResultAdapter) {
        return new DataSetHandler(dataSetExecutor, favouritesAdapter, scanResultAdapter);
    }
}
