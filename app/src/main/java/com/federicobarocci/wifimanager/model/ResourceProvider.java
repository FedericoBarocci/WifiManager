package com.federicobarocci.wifimanager.model;

import com.federicobarocci.wifimanager.R;

import javax.inject.Inject;

/**
 * Created by federico on 12/11/15.
 */
public class ResourceProvider {
    private final WifiKeeper wifiKeeper;
    private final DataBaseExecutor dataBaseExecutor;

    @Inject
    public ResourceProvider(WifiKeeper wifiKeeper, DataBaseExecutor dataBaseExecutor) {
        this.wifiKeeper = wifiKeeper;
        this.dataBaseExecutor = dataBaseExecutor;
    }

    public int getWifiResource(WifiElement wifiElement) {
        if (!wifiKeeper.contains(wifiElement.getBSSID())) {
            return R.drawable.ic_signal_wifi_0_bar_black_24dp;
        }

        return wifiElement.isSecure() ?
                WifiSecureImageEnum.values()[wifiElement.getSignalLevel()].getResource() :
                WifiImageEnum.values()[wifiElement.getSignalLevel()].getResource();
    }

    public int getSavedResource() {
        return R.drawable.ic_action_favourite;
    }

    public int getSavedResource(WifiElement wifiElement) {
        return dataBaseExecutor.contains(wifiElement.getBSSID()) ?
                R.drawable.ic_action_favourite :
                R.drawable.ic_action_not_favourite;
    }
}
