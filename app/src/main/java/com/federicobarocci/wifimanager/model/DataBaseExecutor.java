package com.federicobarocci.wifimanager.model;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 11/11/15.
 */
public class DataBaseExecutor {

    private final DataBaseManager dataBaseManager;
    private final LocationExecutor locationExecutor;
    private final List<WifiDBElement> elements;

    @Inject
    public DataBaseExecutor(DataBaseManager dataBaseManager, LocationExecutor locationExecutor) {
        this.dataBaseManager = dataBaseManager;
        this.locationExecutor = locationExecutor;
        this.elements = dataBaseManager.select();

        locationExecutor.populate(elements);
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
        if (locationExecutor.contain(wifiElement.getBSSID())) {
            LocationElement locationElement = locationExecutor.get(wifiElement.getBSSID()).getCenter();

            dataBaseManager.insert(wifiElement, locationElement);
            elements.add(new WifiDBElement(wifiElement, locationElement));
        }
        else {
            dataBaseManager.insert(wifiElement);
            elements.add(new WifiDBElement(wifiElement));
        }
    }

    public int size() {
        return elements.size();
    }

    public boolean contains(String bssid) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getBSSID().equals(bssid)) {
                return true;
            }
        }
        return false;
    }

    private void remove(String bssid) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getBSSID().equals(bssid)) {
                elements.remove(i);
                return;
            }
        }
    }

    public WifiDBElement getElement(String bssid) {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getBSSID().equals(bssid)) {
                return elements.get(i);
            }
        }
        return null;
    }

    public WifiDBElement get(int position) {
        return elements.get(position);
    }
}
