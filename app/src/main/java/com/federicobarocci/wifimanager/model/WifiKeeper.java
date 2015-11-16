package com.federicobarocci.wifimanager.model;

import android.net.wifi.ScanResult;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by federico on 03/11/15.
 */
public class WifiKeeper {
    //Avoid duplicate entries by check BSSID
    private List<Pair<String, WifiElement>> wifiList = new ArrayList<>();

    public void clear() {
        wifiList.clear();
    }

    public void populate(List<ScanResult> scanResults) {
        wifiList.clear();

        for (ScanResult scanresult : scanResults) {
//            if (!update(scanresult.BSSID, scanresult)) {
                wifiList.add(WifiElement.create(scanresult));
//            }
        }
    }

    public int size() {
        return wifiList.size();
    }

    public WifiElement get(int position) {
        return wifiList.get(position).second;
    }

    /*public String getTitle(int position) {
        WifiElement wifiElement = wifiList.get(position).second;
        return String.format("%s (%s)", wifiElement.getSSID(), wifiElement.getBSSID());
    }

    public CharSequence getInfo(int position) {
        return wifiList.get(position).second.getCapabilities() + "   " + wifiList.get(position).second.getLevel() + " dBm";
    }*/

    private boolean update(String key, ScanResult scanResult) {
        for(int i=0; i<wifiList.size(); i++) {
            Pair<String, WifiElement> pair = wifiList.get(i);

            if(pair.first.equals(key)) {
                wifiList.set(i, WifiElement.create(scanResult));

                return true;
            }
        }

        return false;
    }

    public boolean contains(String key) {
        for(int i=0; i<wifiList.size(); i++) {
            if (wifiList.get(i).first.equals(key)) {
                return true;
            }
        }

        return false;
    }
}
