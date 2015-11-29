package com.federicobarocci.wifiexplorer.model.wifi;

import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.common.WifiList;

/**
 * Created by federico on 24/11/15.
 */
public enum WifiShowMethods {
    OPEN_NETWORK {
        @Override
        public int size(WifiList wifiList) {
            return wifiList.filter(this).size();
        }

        @Override
        public WifiElement get(WifiList wifiList, int position) {
            return wifiList.filter(this).get(position);
        }

        @Override
        public boolean condition(WifiElement wifiElement) {
            return !wifiElement.isSecure();
        }
    },
    CLOSED_NETWORK {
        @Override
        public int size(WifiList wifiList) {
            return wifiList.filter(this).size();
        }

        @Override
        public WifiElement get(WifiList wifiList, int position) {
            return wifiList.filter(this).get(position);
        }

        @Override
        public boolean condition(WifiElement wifiElement) {
            return wifiElement.isSecure();
        }
    },
    ALL_NETWORK {
        @Override
        public int size(WifiList wifiList) {
            return wifiList.size();
        }

        @Override
        public WifiElement get(WifiList wifiList, int position) {
            return wifiList.get(position);
        }

        @Override
        public boolean condition(WifiElement wifiElement) {
            return true;
        }
    };

    public abstract int size(WifiList wifiList);
    public abstract WifiElement get(WifiList wifiList, int position);
    public abstract boolean condition(WifiElement wifiElement);
}
