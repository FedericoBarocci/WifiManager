package com.federicobarocci.wifiexplorer;

import android.app.Application;

import com.federicobarocci.wifiexplorer.component.DaggerWifiExplorerComponent;
import com.federicobarocci.wifiexplorer.component.WifiExplorerComponent;
import com.federicobarocci.wifiexplorer.module.DaggerModule;


/**
 * Created by federico on 03/11/15.
 */
public class WifiExplorerApplication extends Application {

    /*@Inject
    WifiManager wifiManager; // for some reason.*/

    private WifiExplorerComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerWifiExplorerComponent.builder()
                .daggerModule(new DaggerModule(this))
                .build();
        getComponent().inject(this); // As of now, LocationManager should be injected into this.
    }

    public WifiExplorerComponent getComponent() {
        return component;
    }
}
