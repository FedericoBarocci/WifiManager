package com.federicobarocci.wifiexplorer.model.wifi.container.strategy;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist.WifiList;

import java.util.List;

/**
 * Created by Federico
 */
public class SessionWifiList extends WifiList implements WifiListPopulate {
    @Override
    public void populate(List<WifiElement> wifiElementList) {
        for (WifiElement wifiElement : this) {
            wifiElement.invalidate();
        }

        for (WifiElement wifiElement : wifiElementList) {
            addUpdate(wifiElement, true);
        }
    }
}
