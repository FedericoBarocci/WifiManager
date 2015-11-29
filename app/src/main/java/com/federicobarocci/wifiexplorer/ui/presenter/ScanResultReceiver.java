package com.federicobarocci.wifiexplorer.ui.presenter;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
import com.federicobarocci.wifiexplorer.ui.adapter.FavouritesAdapter;
import com.federicobarocci.wifiexplorer.ui.adapter.ScanResultAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 29/11/15.
 */
public class ScanResultReceiver {
    private final WifiKeeper wifiKeeper;
    private final DataBaseHandler dataBaseHandler;
    private final LocationHandler locationHandler;
    private final WifiManager wifiManager;
    private final ScanResultAdapter scanResultAdapter;
    private final FavouritesAdapter favouritesAdapter;

    @Inject
    public ScanResultReceiver(WifiKeeper wifiKeeper, DataBaseHandler dataBaseHandler, LocationHandler locationHandler, WifiManager wifiManager, ScanResultAdapter scanResultAdapter, FavouritesAdapter favouritesAdapter) {
        this.wifiKeeper = wifiKeeper;
        this.dataBaseHandler = dataBaseHandler;
        this.locationHandler = locationHandler;
        this.wifiManager = wifiManager;
        this.scanResultAdapter = scanResultAdapter;
        this.favouritesAdapter = favouritesAdapter;
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

        scanResultAdapter.notifyDataSetChanged();
        favouritesAdapter.notifyDataSetChanged();
    }
}
