package com.federicobarocci.wifiexplorer.component;

import com.federicobarocci.wifiexplorer.ui.activity.DetailActivity;
import com.federicobarocci.wifiexplorer.ui.activity.MainActivity;
import com.federicobarocci.wifiexplorer.ui.activity.MapActivity;
import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.ui.adapter.FavouritesAdapter;
import com.federicobarocci.wifiexplorer.ui.adapter.ScanResultAdapter;
import com.federicobarocci.wifiexplorer.module.DaggerModule;
import com.federicobarocci.wifiexplorer.ui.presenter.TaskExecutor;
import com.federicobarocci.wifiexplorer.ui.presenter.WifiUtilDelegate;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by federico on 20/10/15.
 */
@Singleton
@Component(modules = DaggerModule.class)
public interface WifiExplorerComponent {
    WifiUtilDelegate provideWifiUtilDelegate();
    ScanResultAdapter provideScanResultAdapter();
    TaskExecutor provideTaskExecutor();
    FavouritesAdapter provideFavouritesAdapter();

    void inject(WifiExplorerApplication application);
    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
    void inject(MapActivity mapActivity);
}
