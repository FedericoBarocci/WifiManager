package com.federicobarocci.wifiexplorer.model.db;

import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseElement;
import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseManager;
import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist.WifiList;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Federico
 */
public class DataBaseHandler {

    private final DataBaseManager dataBaseManager;
    private final LocationHandler locationHandler;
    private final WifiKeeper wifiKeeper;
    private final WifiList wifiList;

    @Inject
    public DataBaseHandler(DataBaseManager dataBaseManager, LocationHandler locationHandler, WifiKeeper wifiKeeper) {
        this.dataBaseManager = dataBaseManager;
        this.locationHandler = locationHandler;
        this.wifiKeeper = wifiKeeper;
        this.wifiList = dataBaseManager.selectWifiElements();
    }

    public DataBaseElement toggleSave(WifiElement wifiElement) {
        if (wifiList.contains(wifiElement)) {
            return delete(wifiElement);
        }
        else {
            save(wifiElement);
            return null;
        }
    }

    private DataBaseElement delete(WifiElement wifiElement) {
        wifiList.remove(wifiElement);
        return dataBaseManager.delete(wifiElement.getBSSID());
    }

    private void save(WifiElement wifiElement) {
        final LocationKeeper locationKeeper = locationHandler.get(wifiElement.getBSSID());

        if (locationKeeper == null) {
            dataBaseManager.insert(wifiElement);
        }
        else {
            dataBaseManager.insert(wifiElement, locationKeeper.getCenter());
        }

        wifiList.add(wifiElement);
    }

    private void update(WifiElement wifiElement) {
        final LocationKeeper locationKeeper = locationHandler.get(wifiElement.getBSSID());

        if (locationKeeper == null) {
            dataBaseManager.update(wifiElement);
        }
        else {
            dataBaseManager.update(wifiElement, locationKeeper.getCenter());
        }
    }

    public int size() {
        return wifiList.size();
    }

    public boolean contains(WifiElement wifiElement) {
        return wifiList.contains(wifiElement);
    }

    public WifiElement get(int position) {
        return wifiList.get(position);
    }

    public void updateScanResults(List<WifiElement> wifiElementList) {
        for (WifiElement wifiElement : wifiElementList) {
            if (wifiList.addUpdate(wifiElement, false)) {
                update(wifiElement);
            }
        }
    }

    public void restore(DataBaseElement dataBaseElement) {
        WifiElement wifiElement = wifiKeeper.getUnfilteredElement(dataBaseElement.bssid);

        if (wifiElement == null) {
            wifiElement = dataBaseElement.toWifiElement();
        }

        wifiList.add(wifiElement);
        dataBaseManager.insert(dataBaseElement);
    }

    public WifiList getList() {
        return wifiList;
    }
}
