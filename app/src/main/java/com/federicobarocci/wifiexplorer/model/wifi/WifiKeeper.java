package com.federicobarocci.wifiexplorer.model.wifi;

import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListEnum;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist.WifiList;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListContainer;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 03/11/15.
 */
public class WifiKeeper {
    private final WifiListContainer wifiListContainer;
//    private final LocationHandler locationHandler;

    private WifiShowMethods wifiShowMethods = WifiShowMethods.ALL_NETWORK;
    private WifiListEnum currentWifiList = WifiListEnum.NEAR;

    @Inject
    public WifiKeeper(WifiListContainer wifiListContainer/*, LocationHandler locationHandler*/) {
        this.wifiListContainer = wifiListContainer;
//        this.locationHandler = locationHandler;
    }

    public void clear() {
        wifiListContainer.getList(WifiListEnum.NEAR).clear();
    }

    public void populate(List<WifiElement> wifiElementList) {
//        //List<WifiElement> list = new ArrayList<>(scanResults.size());
//
//        for (WifiElement wifiElement : wifiElementList) {
//            //WifiElement wifiElement = new WifiElement(scanResult);
//            //list.add(wifiElement);
//            locationHandler.store(wifiElement);
//        }

        wifiListContainer.populate(wifiElementList);
    }

    public int size() {
        return wifiShowMethods.size(wifiListContainer.getList(currentWifiList));
    }

    public WifiElement get(int position) {
        return wifiShowMethods.get(wifiListContainer.getList(currentWifiList), position);
    }

    public WifiList getFilteredList() {
        return wifiListContainer.getList(currentWifiList).filter(wifiShowMethods);
    }

    public boolean contains(String bssid) {
        return wifiListContainer.getList(currentWifiList).getKey(bssid) != null;
    }

    public WifiElement getElement(String bssid) {
        return wifiListContainer.getList(currentWifiList).getKey(bssid);
    }

    public void setWifiShowMethods(WifiShowMethods wifiShowMethods) {
        this.wifiShowMethods = wifiShowMethods;
    }

    public void setWifiListEnum(WifiListEnum wifiListEnum) {
        this.currentWifiList = wifiListEnum;
    }

    public WifiListEnum getWifiListEnum() {
        return currentWifiList;
    }

    public WifiShowMethods getWifiShowMethodsEnum() {
        return wifiShowMethods;
    }

    /*private boolean addUpdate(String key, ScanResult scanResult) {
        for(int i = 0; i < wifiList.size(); i++) {
            Pair<String, WifiElement> pair = wifiList.get(i);

            if(pair.first.equals(key)) {
                wifiList.set(i, new Pair<>(scanResult.BSSID, new WifiElement(scanResult)));

                return true;
            }
        }

        return false;
    }*/
}
