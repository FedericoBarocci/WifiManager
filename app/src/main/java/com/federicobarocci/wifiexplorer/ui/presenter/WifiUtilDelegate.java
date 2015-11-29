package com.federicobarocci.wifiexplorer.ui.presenter;

import android.net.wifi.WifiManager;

import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListEnum;
import com.federicobarocci.wifiexplorer.model.wifi.WifiShowMethods;
import com.federicobarocci.wifiexplorer.ui.adapter.ScanResultAdapter;

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

    /*public void onWifiListReceive() {
        wifiKeeper.populate(wifiManager.getScanResults());
        scanResultAdapter.notifyDataSetChanged();
    }*/

    public boolean wifiTestEnable() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            return true;
        }

        return false;
    }

    public void scanWifiAP() {
        wifiTestEnable();

        wifiKeeper.clear();
        scanResultAdapter.notifyDataSetChanged();
        wifiManager.startScan();
    }

    public boolean empty() {
        return wifiKeeper.size() == 0;
    }

    public void setWifiShowEnum(WifiShowMethods wifiShowMethods) {
        wifiKeeper.setWifiShowMethods(wifiShowMethods);
        scanResultAdapter.notifyDataSetChanged();
    }

    public void setWifiListEnum(WifiListEnum wifiListEnum) {
        wifiKeeper.setWifiListEnum(wifiListEnum);
        scanResultAdapter.notifyDataSetChanged();
    }

    public WifiListEnum getShowSelectionEnum() {
        return wifiKeeper.getWifiListEnum();
    }

    public WifiShowMethods getFilterSelection() {
        return wifiKeeper.getWifiShowMethodsEnum();
    }
}
