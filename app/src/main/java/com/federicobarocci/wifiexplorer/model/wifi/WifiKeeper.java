package com.federicobarocci.wifiexplorer.model.wifi;

import android.net.wifi.ScanResult;
import android.support.v4.util.Pair;

import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 03/11/15.
 */
public class WifiKeeper {
    private final WifiList wifiList;
    private final LocationHandler locationHandler;

    private WifiShowStrategy wifiShowStrategy = WifiShowStrategy.ALL_NETWORK;

    @Inject
    public WifiKeeper(WifiList wifiList, LocationHandler locationHandler) {
        this.wifiList = wifiList;
        this.locationHandler = locationHandler;
    }

    public void clear() {
        wifiList.clear();
    }

    public void populate(List<ScanResult> scanResults) {
        wifiList.clear();

        for (ScanResult scanResult : scanResults) {
            WifiElement wifiElement = new WifiElement(scanResult);
            wifiList.add(wifiElement);
            locationHandler.store(wifiElement);
        }
    }

    public int size() {
        return wifiShowStrategy.size(wifiList);
    }

    public WifiElement get(int position) {
        return wifiShowStrategy.get(wifiList, position);
    }

    public WifiList getFilteredList() {
        return wifiList.filter(wifiShowStrategy);
    }

    public boolean contains(String bssid) {
        return wifiList.getKey(bssid) != null;
    }

    public WifiElement getElement(String bssid) {
        return wifiList.getKey(bssid);
    }

    public void setWifiShowStrategy(WifiShowStrategy wifiShowStrategy) {
        this.wifiShowStrategy = wifiShowStrategy;
    }

    /*private boolean update(String key, ScanResult scanResult) {
        for(int i = 0; i < wifiList.size(); i++) {
            Pair<String, WifiElement> pair = wifiList.get(i);

            if(pair.first.equals(key)) {
                wifiList.set(i, new Pair<>(scanResult.BSSID, new WifiElement(scanResult)));

                return true;
            }
        }

        return false;
    }*/
}
