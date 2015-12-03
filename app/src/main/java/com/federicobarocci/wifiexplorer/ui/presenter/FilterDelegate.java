package com.federicobarocci.wifiexplorer.ui.presenter;

import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiShowMethods;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListEnum;
import com.federicobarocci.wifiexplorer.ui.adapter.ScanResultAdapter;

import javax.inject.Inject;

/**
 * Created by Federico
 */
public class FilterDelegate {
    private final WifiKeeper wifiKeeper;
    private final ScanResultAdapter scanResultAdapter;

    @Inject
    public FilterDelegate(WifiKeeper wifiKeeper, ScanResultAdapter scanResultAdapter) {
        this.wifiKeeper = wifiKeeper;
        this.scanResultAdapter = scanResultAdapter;
    }

    public void showAllNetworks() {
        setWifiShowEnum(WifiShowMethods.ALL_NETWORK);
    }

    public void showOnlyOpenNetworks() {
        setWifiShowEnum(WifiShowMethods.OPEN_NETWORK);
    }

    public void showOnlyClosedNetworks() {
        setWifiShowEnum(WifiShowMethods.CLOSED_NETWORK);
    }

    public void showNearbyWifiList() {
        setWifiListEnum(WifiListEnum.NEAR);
    }

    public void showSessionWifiList() {
        setWifiListEnum(WifiListEnum.SESSION);
    }

    public boolean isNearSelected() {
        return getShowSelectionEnum() == WifiListEnum.NEAR;
    }

    public boolean isSessionSelected() {
        return getShowSelectionEnum() == WifiListEnum.SESSION;
    }

    public boolean isFilterAllSelected() {
        return getFilterSelection() == WifiShowMethods.ALL_NETWORK;
    }

    public boolean isFilterOpenSelected() {
        return getFilterSelection() == WifiShowMethods.OPEN_NETWORK;
    }

    public boolean isFilterClosedSelected() {
        return getFilterSelection() == WifiShowMethods.CLOSED_NETWORK;
    }

    private void setWifiShowEnum(WifiShowMethods wifiShowMethods) {
        wifiKeeper.setWifiShowMethods(wifiShowMethods);
        scanResultAdapter.notifyDataSetChanged();
    }

    private void setWifiListEnum(WifiListEnum wifiListEnum) {
        wifiKeeper.setWifiListEnum(wifiListEnum);
        scanResultAdapter.notifyDataSetChanged();
    }

    private WifiListEnum getShowSelectionEnum() {
        return wifiKeeper.getWifiListEnum();
    }

    private WifiShowMethods getFilterSelection() {
        return wifiKeeper.getWifiShowMethodsEnum();
    }
}
