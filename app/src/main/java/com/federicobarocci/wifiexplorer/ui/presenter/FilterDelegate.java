package com.federicobarocci.wifiexplorer.ui.presenter;

import com.federicobarocci.wifiexplorer.model.wifi.WifiShowMethods;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListEnum;

import javax.inject.Inject;

/**
 * Created by federico on 01/12/15.
 */
public class FilterDelegate {

    private final WifiUtilDelegate wifiUtilDelegate;

    @Inject
    public FilterDelegate(WifiUtilDelegate wifiUtilDelegate) {
        this.wifiUtilDelegate = wifiUtilDelegate;
    }

    public void showAllNetworks() {
        wifiUtilDelegate.setWifiShowEnum(WifiShowMethods.ALL_NETWORK);
    }

    public void showOnlyOpenNetworks() {
        wifiUtilDelegate.setWifiShowEnum(WifiShowMethods.OPEN_NETWORK);
    }

    public void showOnlyClosedNetworks() {
        wifiUtilDelegate.setWifiShowEnum(WifiShowMethods.CLOSED_NETWORK);
    }

    public void showNearbyWifiList() {
        wifiUtilDelegate.setWifiListEnum(WifiListEnum.NEAR);
    }

    public void showSessionWifiList() {
        wifiUtilDelegate.setWifiListEnum(WifiListEnum.SESSION);
    }

    public boolean isNearSelected() {
        return wifiUtilDelegate.getShowSelectionEnum() == WifiListEnum.NEAR;
    }

    public boolean isSessionSelected() {
        return wifiUtilDelegate.getShowSelectionEnum() == WifiListEnum.SESSION;
    }

    public boolean isFilterAllSelected() {
        return wifiUtilDelegate.getFilterSelection() == WifiShowMethods.ALL_NETWORK;
    }

    public boolean isFilterOpenSelected() {
        return wifiUtilDelegate.getFilterSelection() == WifiShowMethods.OPEN_NETWORK;
    }

    public boolean isFilterClosedSelected() {
        return wifiUtilDelegate.getFilterSelection() == WifiShowMethods.CLOSED_NETWORK;
    }
}
