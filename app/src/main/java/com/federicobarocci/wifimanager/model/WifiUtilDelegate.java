package com.federicobarocci.wifimanager.model;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.federicobarocci.wifimanager.adapter.ScanResultAdapter;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by federico on 03/11/15.
 */
public class WifiUtilDelegate {
    private final WifiManager wifiManager;
    private final WifiKeeper wifiKeeper;
    private final ScanResultAdapter scanResultAdapter;

    @Inject
    public WifiUtilDelegate(WifiManager wifiManager, WifiKeeper wifiKeeper, ScanResultAdapter adapter) {
        this.wifiManager = wifiManager;
        this.wifiKeeper = wifiKeeper;
        this.scanResultAdapter = adapter;
    }

    public void onWifiListReceive() {
        wifiKeeper.populate(wifiManager.getScanResults());
        scanResultAdapter.notifyDataSetChanged();
    }

    public boolean wifiTestEnable() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            return true;
        }

        return false;
    }

    public void scanWifiAP() {
        this.wifiTestEnable();

        wifiKeeper.clear();
        scanResultAdapter.notifyDataSetChanged();
        wifiManager.startScan();
    }

    public boolean empty() {
        return wifiKeeper.size() == 0;
    }
}
