package com.federicobarocci.wifimanager.model;

import android.net.wifi.ScanResult;
import android.support.v4.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 03/11/15.
 */
public class WifiKeeper {
    private final LocationExecutor locationExecutor;

    //Avoid duplicate entries by check BSSID
    private List<Pair<String, WifiElement>> wifiList = new ArrayList<>();

    @Inject
    public WifiKeeper(LocationExecutor locationExecutor) {
        this.locationExecutor = locationExecutor;
    }

    public void clear() {
        wifiList.clear();
    }

    public void populate(List<ScanResult> scanResults) {
        wifiList.clear();

        for (ScanResult scanresult : scanResults) {
            Pair<String, WifiElement> wifiElement = WifiElement.create(scanresult);
            wifiList.add(wifiElement);
            locationExecutor.store(wifiElement.second);
        }
    }

    public int size() {
        return wifiList.size();
    }

    public WifiElement get(int position) {
        return wifiList.get(position).second;
    }

    public List<WifiElement> getAll() {
        List<WifiElement> list = new ArrayList<>();

        for (int i = 0; i< wifiList.size(); i++) {
            list.add(wifiList.get(i).second);
        }

        return list;
    }

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
