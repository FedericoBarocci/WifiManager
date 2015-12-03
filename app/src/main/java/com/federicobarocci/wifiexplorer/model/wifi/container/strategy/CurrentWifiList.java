package com.federicobarocci.wifiexplorer.model.wifi.container.strategy;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist.WifiList;

import java.util.List;

/**
 * Created by Federico
 */
public class CurrentWifiList extends WifiList implements WifiListPopulate {
    @Override
    public void populate(List<WifiElement> wifiElementList) {
        clear();
        addAll(wifiElementList);
    }
}
