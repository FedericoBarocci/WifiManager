package com.federicobarocci.wifiexplorer.model.wifi;

import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListContainer;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListEnum;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist.WifiList;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Federico
 */
public class WifiKeeper {
    private final WifiListContainer wifiListContainer;

    private WifiShowMethods wifiShowMethods = WifiShowMethods.ALL_NETWORK;
    private WifiListEnum currentWifiList = WifiListEnum.NEAR;

    @Inject
    public WifiKeeper(WifiListContainer wifiListContainer) {
        this.wifiListContainer = wifiListContainer;
    }

    public void clear() {
        wifiListContainer.getList(WifiListEnum.NEAR).clear();
    }

    public void populate(List<WifiElement> wifiElementList) {
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

    public WifiElement getUnfilteredElement(String bssid) {
        return wifiListContainer.getList(WifiListEnum.SESSION).getByKey(bssid);
    }

    public boolean contains(String bssid) {
        return getUnfilteredElement(bssid) != null;
    }
}
