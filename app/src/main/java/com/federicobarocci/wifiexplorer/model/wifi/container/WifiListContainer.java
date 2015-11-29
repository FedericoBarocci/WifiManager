package com.federicobarocci.wifiexplorer.model.wifi.container;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.CurrentWifiList;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.SessionWifiList;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.WifiListPopulate;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.common.WifiList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by federico on 28/11/15.
 */
public class WifiListContainer {

    private final Map<WifiListEnum, WifiListPopulate> mapList = new HashMap<>();

    public WifiListContainer(CurrentWifiList currentWifiList, SessionWifiList sessionWifiList) {
        mapList.put(WifiListEnum.NEAR, currentWifiList);
        mapList.put(WifiListEnum.SESSION, sessionWifiList);
    }

    public WifiList getList(WifiListEnum wifiListEnum) {
        return (WifiList) mapList.get(wifiListEnum);
    }

    public void populate(List<WifiElement> list) {
        for (WifiListPopulate wifiList : mapList.values()) {
            wifiList.populate(list);
        }
    }
}
