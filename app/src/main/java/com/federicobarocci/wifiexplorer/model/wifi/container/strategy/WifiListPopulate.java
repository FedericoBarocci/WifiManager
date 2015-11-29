package com.federicobarocci.wifiexplorer.model.wifi.container.strategy;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

import java.util.List;

/**
 * Created by federico on 28/11/15.
 */
public interface WifiListPopulate {
    void populate(List<WifiElement> wifiElementList);
}
