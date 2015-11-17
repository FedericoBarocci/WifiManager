package com.federicobarocci.wifimanager.model;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.federicobarocci.wifimanager.WMApplication;
import com.federicobarocci.wifimanager.adapter.FavouritesAdapter;
import com.federicobarocci.wifimanager.adapter.ScanResultAdapter;
import com.federicobarocci.wifimanager.adapter.SnackBarUndoFavourites;
import com.federicobarocci.wifimanager.adapter.SnackBarUndoMain;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by federico on 20/10/15.
 */
@Module
public class DaggerModule {
    private final WMApplication application;

    public DaggerModule(WMApplication application) {
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
    LocationExecutor provideLocationExecutor(Context context) {
        return new LocationExecutor(context);
    }

    @Provides
    @Singleton
    WifiKeeper provideWifiKeeper(LocationExecutor locationExecutor) {
        return new WifiKeeper(locationExecutor);
    }

    @Provides
    @Singleton
    DataBaseManager provideDataBaseManager(Context context) {
        return new DataBaseManager(context);
    }

    @Provides
    @Singleton
    DataBaseExecutor provideDataBaseExecutor(DataBaseManager dataBaseManager) {
        return new DataBaseExecutor(dataBaseManager);
    }

    @Provides
    @Singleton
    SnackBarUndoMain provideSnackBarUndoMain(DataBaseExecutor dataBaseExecutor) {
        return new SnackBarUndoMain(dataBaseExecutor);
    }

    @Provides
    @Singleton
    SnackBarUndoFavourites provideSnackBarUndoFavourites(DataBaseExecutor dataBaseExecutor) {
        return new SnackBarUndoFavourites(dataBaseExecutor);
    }

    @Provides
    @Singleton
    WifiUtilDelegate provideWifiUtilDelegate(WifiManager wifiManager, WifiKeeper wifiKeeper, ScanResultAdapter adapter) {
        return new WifiUtilDelegate(wifiManager, wifiKeeper, adapter);
    }

    @Provides
    @Singleton
    ResourceProvider provideResourceProvider(WifiKeeper wifiKeeper, DataBaseExecutor dataBaseExecutor) {
        return new ResourceProvider(wifiKeeper, dataBaseExecutor);
    }

    @Provides
    @Singleton
    ScanResultAdapter provideScanResultAdapter(WifiKeeper wifiKeeper, SnackBarUndoMain snackBarUndoMain, ResourceProvider resourceProvider) {
        return new ScanResultAdapter(wifiKeeper, snackBarUndoMain, resourceProvider);
    }

    @Provides
    @Singleton
    FavouritesAdapter provideFavouritesAdapter(DataBaseExecutor dataBaseExecutor, SnackBarUndoFavourites snackBarUndoFavourites, ResourceProvider resourceProvider) {
        return new FavouritesAdapter(dataBaseExecutor, snackBarUndoFavourites, resourceProvider);
    }

    @Provides
    @Singleton
    TaskExecutor provideTaskExecutor(Context context, WifiUtilDelegate wifiUtilDelegate) {
        return new TaskExecutor(context, wifiUtilDelegate);
    }
}
