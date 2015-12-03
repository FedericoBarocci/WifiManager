package com.federicobarocci.wifiexplorer.component;

import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.module.DaggerModule;
import com.federicobarocci.wifiexplorer.ui.activity.DetailActivity;
import com.federicobarocci.wifiexplorer.ui.activity.FavouritesActivity;
import com.federicobarocci.wifiexplorer.ui.activity.MainActivity;
import com.federicobarocci.wifiexplorer.ui.activity.MapActivity;
import com.federicobarocci.wifiexplorer.ui.activity.fragment.DetailInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Federico
 */
@Singleton
@Component(modules = DaggerModule.class)
public interface WifiExplorerComponent {
    void inject(WifiExplorerApplication application);
    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
    void inject(MapActivity mapActivity);
    void inject(DetailInfoFragment detailInfoFragment);
    void inject(FavouritesActivity favouritesActivity);
}
