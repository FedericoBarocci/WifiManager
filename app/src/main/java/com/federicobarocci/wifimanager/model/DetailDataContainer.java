package com.federicobarocci.wifimanager.model;

import android.net.wifi.ScanResult;

/**
 * Created by federico on 07/11/15.
 */
public class DetailDataContainer {
    private ScanResult scanResult;

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }
}
