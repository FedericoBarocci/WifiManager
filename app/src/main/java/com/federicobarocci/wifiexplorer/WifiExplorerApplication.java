package com.federicobarocci.wifiexplorer;

import android.app.Application;

import com.federicobarocci.wifiexplorer.component.DaggerWifiExplorerComponent;
import com.federicobarocci.wifiexplorer.component.WifiExplorerComponent;
import com.federicobarocci.wifiexplorer.module.DaggerModule;


/**
 * Created by Federico
 */
public class WifiExplorerApplication extends Application {

    private WifiExplorerComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerWifiExplorerComponent.builder()
                .daggerModule(new DaggerModule(this))
                .build();
        component.inject(this);
    }

    public WifiExplorerComponent getComponent() {
        return component;
    }
}
