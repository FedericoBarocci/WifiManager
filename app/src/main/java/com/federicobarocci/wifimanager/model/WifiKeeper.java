package com.federicobarocci.wifimanager.model;

import android.net.wifi.ScanResult;
import android.support.v4.util.SimpleArrayMap;

import java.util.List;

/**
 * Created by federico on 03/11/15.
 */
public class WifiKeeper {
    //Avoid duplicate entries by check BSSID
    private SimpleArrayMap<CharSequence, ScanResult> wifiMap = new SimpleArrayMap<>();

    public void clear() {
        wifiMap.clear();
    }

    public void populate(List<ScanResult> scanResults) {
        for (ScanResult scanresult : scanResults) {
            wifiMap.put(scanresult.BSSID, scanresult);
        }
    }

    public int size() {
        return wifiMap.size();
    }

    public ScanResult get(int position) {
        return wifiMap.valueAt(position);
    }

    public CharSequence getTitle(int position) {
        ScanResult scanResult = wifiMap.valueAt(position);
        return String.format("%s (%s)", scanResult.SSID, scanResult.BSSID);
    }

    public CharSequence getInfo(int position) {
        return wifiMap.valueAt(position).capabilities;
    }
}
