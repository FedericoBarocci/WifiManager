package com.federicobarocci.wifimanager.model;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.federicobarocci.wifimanager.FavouritesActivity;
import com.federicobarocci.wifimanager.WMApplication;
import com.federicobarocci.wifimanager.adapter.FavouritesAdapter;
import com.federicobarocci.wifimanager.adapter.ScanResultAdapter;

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
    WifiKeeper provideWifiKeeper() {
        return new WifiKeeper();
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
    WifiUtilDelegate provideWifiUtilDelegate(WifiManager wifiManager, WifiKeeper wifiKeeper, ScanResultAdapter adapter) {
        return new WifiUtilDelegate(wifiManager, wifiKeeper, adapter);
    }

    @Provides
    @Singleton
    ScanResultAdapter provideScanResultAdapter(WifiKeeper wifiKeeper, DataBaseExecutor dataBaseExecutor) {
        return new ScanResultAdapter(wifiKeeper, dataBaseExecutor);
    }

    @Provides
    @Singleton
    FavouritesAdapter provideFavouritesAdapter(DataBaseExecutor dataBaseExecutor) {
        return new FavouritesAdapter(dataBaseExecutor);
    }

    @Provides
    @Singleton
    TaskExecutor provideTaskExecutor(Context context, WifiUtilDelegate wifiUtilDelegate) {
        return new TaskExecutor(context, wifiUtilDelegate);
    }
}
