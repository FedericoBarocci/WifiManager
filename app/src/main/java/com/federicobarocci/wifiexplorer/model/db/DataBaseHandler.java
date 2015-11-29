package com.federicobarocci.wifiexplorer.model.db;

import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseManager;
import com.federicobarocci.wifiexplorer.model.location.LocationElement;
import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist.WifiList;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 11/11/15.
 */
public class DataBaseHandler {

    private final DataBaseManager dataBaseManager;
    private final LocationHandler locationHandler;
    private final WifiList wifiList;

    @Inject
    public DataBaseHandler(DataBaseManager dataBaseManager, LocationHandler locationHandler) {
        this.dataBaseManager = dataBaseManager;
        this.locationHandler = locationHandler;
        this.wifiList = dataBaseManager.selectWifiElements();

        //locationHandler.populate(elements);
    }

    public boolean toggleSave(WifiElement wifiElement) {
        if (contains(wifiElement.getBSSID())) {
            delete(wifiElement.getBSSID());
            return false;
        }
        else {
            save(wifiElement);
            return true;
        }
    }

    private void delete(String key) {
        dataBaseManager.delete(key);
        remove(key);
    }

    private void save(WifiElement wifiElement) {
        if (locationHandler.contain(wifiElement.getBSSID())) {
            LocationElement locationElement = locationHandler.get(wifiElement.getBSSID()).getCenter();

            dataBaseManager.insert(wifiElement, locationElement);
            //elements.add(new DataBaseElement(wifiElement, locationElement));
        }
        else {
            dataBaseManager.insert(wifiElement);
            //elements.add(new DataBaseElement(wifiElement));
        }

        wifiList.add(wifiElement);
    }

    public int size() {
        return wifiList.size();
    }

    public boolean contains(String bssid) {
        for (int i = 0; i < wifiList.size(); i++) {
            if (wifiList.get(i).getBSSID().equals(bssid)) {
                return true;
            }
        }
        return false;
    }

    private void remove(String bssid) {
        for (int i = 0; i < wifiList.size(); i++) {
            if (wifiList.get(i).getBSSID().equals(bssid)) {
                wifiList.removeItemAt(i);
                return;
            }
        }
    }

    /*public DataBaseElement getElement(String bssid) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getBSSID().equals(bssid)) {
                return elements.get(i);
            }
        }
        return null;
    }*/

    public WifiElement get(int position) {
        return wifiList.get(position);
    }

    public void updateScanResults(List<WifiElement> wifiElementList) {
        for(WifiElement wifiElement : wifiElementList) {
            wifiList.addUpdate(wifiElement, false);
        }
    }
}
