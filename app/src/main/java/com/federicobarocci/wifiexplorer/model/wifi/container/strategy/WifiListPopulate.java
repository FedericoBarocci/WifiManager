package com.federicobarocci.wifiexplorer.model.wifi.container.strategy;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

import java.util.List;

/**
 * Created by Federico
 */
public interface WifiListPopulate {
    void populate(List<WifiElement> wifiElementList);
}
