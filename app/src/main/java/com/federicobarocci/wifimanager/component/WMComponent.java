package com.federicobarocci.wifimanager.component;

import com.federicobarocci.wifimanager.MainActivity;
import com.federicobarocci.wifimanager.MapActivity;
import com.federicobarocci.wifimanager.WMApplication;
import com.federicobarocci.wifimanager.adapter.ScanResultAdapter;
import com.federicobarocci.wifimanager.model.DaggerModule;
import com.federicobarocci.wifimanager.model.TaskExecutor;
import com.federicobarocci.wifimanager.model.WifiUtilDelegate;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by federico on 20/10/15.
 */
@Singleton
@Component(modules = DaggerModule.class)
public interface WMComponent {
    WifiUtilDelegate provideWifiUtilDelegate();
    ScanResultAdapter provideScanResultAdapter();
    TaskExecutor provideTaskExecutor();

    void inject(WMApplication application);
    void inject(MainActivity mainActivity);
    //void inject(DetailActivity detailActivity);
    void inject(MapActivity mapActivity);
}
