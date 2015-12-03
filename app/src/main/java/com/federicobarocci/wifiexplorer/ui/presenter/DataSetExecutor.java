package com.federicobarocci.wifiexplorer.ui.presenter;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Federico
 */
public class DataSetExecutor {
    private final WifiKeeper wifiKeeper;
    private final DataBaseHandler dataBaseHandler;
    private final LocationHandler locationHandler;
    private final WifiManager wifiManager;

    @Inject
    public DataSetExecutor(WifiKeeper wifiKeeper, DataBaseHandler dataBaseHandler, LocationHandler locationHandler, WifiManager wifiManager) {
        this.wifiKeeper = wifiKeeper;
        this.dataBaseHandler = dataBaseHandler;
        this.locationHandler = locationHandler;
        this.wifiManager = wifiManager;
    }

    public void onWifiListReceive() {
        List<ScanResult> scanResultList = wifiManager.getScanResults();
        List<WifiElement> wifiElementList = new ArrayList<>(scanResultList.size());

        for (ScanResult scanResult : scanResultList) {
            wifiElementList.add(new WifiElement(scanResult));
        }

        wifiKeeper.populate(wifiElementList);
        dataBaseHandler.updateScanResults(wifiElementList);
        locationHandler.populate(wifiElementList);
    }

    public boolean isFavourite(WifiElement wifiElement) {
        return dataBaseHandler.contains(wifiElement);
    }

    public void toggleSave(WifiElement wifiElement) {
        dataBaseHandler.toggleSave(wifiElement);
    }

    public boolean wifiNeedToSetEnable() {
        if (wifiManager.isWifiEnabled()) {
            return false;
        }

        wifiManager.setWifiEnabled(true);
        return true;
    }

    public void startScan() {
        wifiManager.startScan();
    }

    public void clearWifiList() {
        wifiKeeper.clear();
    }

    public boolean isWifiListEmpty() {
        return wifiKeeper.size() == 0;
    }
}
