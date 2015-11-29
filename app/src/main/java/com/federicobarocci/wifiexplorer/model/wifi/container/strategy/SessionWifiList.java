package com.federicobarocci.wifiexplorer.model.wifi.container.strategy;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.common.WifiList;

import java.util.List;

/**
 * Created by federico on 28/11/15.
 */
public class SessionWifiList extends WifiList implements WifiListPopulate {
    @Override
    public void populate(List<WifiElement> wifiElementList) {
        for (WifiElement wifiElement : wifiElementList) {
            addUpdate(wifiElement);
        }
    }
}
