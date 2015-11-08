package com.federicobarocci.wifimanager;

import android.app.Application;

import com.federicobarocci.wifimanager.component.DaggerWMComponent;
import com.federicobarocci.wifimanager.component.WMComponent;
import com.federicobarocci.wifimanager.model.DaggerModule;
import com.federicobarocci.wifimanager.model.DetailModule;


/**
 * Created by federico on 03/11/15.
 */
public class WMApplication extends Application {

    /*@Inject
    WifiManager wifiManager; // for some reason.*/

    private WMComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerWMComponent.builder()
                .daggerModule(new DaggerModule(this))
                .build();
        getComponent().inject(this); // As of now, LocationManager should be injected into this.
    }

    public WMComponent getComponent() {
        return component;
    }
}
