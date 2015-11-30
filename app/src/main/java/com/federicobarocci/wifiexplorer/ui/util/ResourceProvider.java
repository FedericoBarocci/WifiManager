package com.federicobarocci.wifiexplorer.ui.util;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;

import javax.inject.Inject;

/**
 * Created by federico on 12/11/15.
 */
public class ResourceProvider {
    private final WifiKeeper wifiKeeper;
    private final DataBaseHandler dataBaseHandler;

    @Inject
    public ResourceProvider(WifiKeeper wifiKeeper, DataBaseHandler dataBaseHandler) {
        this.wifiKeeper = wifiKeeper;
        this.dataBaseHandler = dataBaseHandler;
    }

    public int getWifiResource(WifiElement wifiElement) {
        if (wifiKeeper.contains(wifiElement.getBSSID()) && wifiElement.isLineOfSight()) {
            return wifiElement.isSecure() ?
                    WifiSecureImageEnum.values()[wifiElement.getSignalLevel()].getResource() :
                    WifiImageEnum.values()[wifiElement.getSignalLevel()].getResource();
        }
        else {
            return R.drawable.ic_signal_wifi_0_bar_black_24dp;
        }
    }

    public int getSavedResource() {
        return R.drawable.ic_action_favourite;
    }

    public int getSavedResource(WifiElement wifiElement) {
        return dataBaseHandler.contains(wifiElement) ?
                R.drawable.ic_action_favourite :
                R.drawable.ic_action_not_favourite;
    }
}
