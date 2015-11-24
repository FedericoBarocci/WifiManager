package com.federicobarocci.wifiexplorer.model.wifi;

import android.support.v4.util.Pair;

import java.util.List;

/**
 * Created by federico on 24/11/15.
 */
public enum WifiShowStrategy {
    OPEN_NETWORK {
        @Override
        public int size(List<Pair<String, WifiElement>> wifiList) {
            int count = 0;

            for (int i = 0; i < wifiList.size(); i++) {
                if (!wifiList.get(i).second.isSecure()) {
                    count++;
                }
            }

            return count;
        }

        @Override
        public WifiElement get(List<Pair<String, WifiElement>> wifiList, int position) {
            int count = 0;
            int i = 0;

            while ( i < wifiList.size() && count != position) {
                if (!wifiList.get(i).second.isSecure()) {
                    count++;
                }
                i++;
            }

            if(count == position) return wifiList.get(i).second;
            else return null;
        }
    },
    CLOSED_NETWORK {
        @Override
        public int size(List<Pair<String, WifiElement>> wifiList) {
            int count = 0;

            for (int i = 0; i < wifiList.size(); i++) {
                if (wifiList.get(i).second.isSecure()) {
                    count++;
                }
            }

            return count;
        }

        @Override
        public WifiElement get(List<Pair<String, WifiElement>> wifiList, int position) {
            int count = 0;
            int i = 0;

            while (i < wifiList.size() && count != position) {
                if (wifiList.get(i).second.isSecure()) {
                    count++;
                }

                i++;
            }

            if(count == position) return wifiList.get(i).second;
            else return null;
        }
    },
    ALL_NETWORK {
        @Override
        public int size(List<Pair<String, WifiElement>> wifiList) {
            return wifiList.size();
        }

        @Override
        public WifiElement get(List<Pair<String, WifiElement>> wifiList, int position) {
            return wifiList.get(position).second;
        }
    };

    abstract int size(List<Pair<String, WifiElement>> wifiList);
    abstract WifiElement get(List<Pair<String, WifiElement>> wifiList, int position);

    /*private final ShowAllNetworkStrategy showAllNetworkStrategy;
    private final ShowOpenNetworkStrategy showOpenNetworkStrategy;
    private final ShowClosedNetworkStrategy showClosedNetworkStrategy;

    @Inject
    public WifiShowStrategy(ShowAllNetworkStrategy showAllNetworkStrategy,
                            ShowOpenNetworkStrategy showOpenNetworkStrategy,
                            ShowClosedNetworkStrategy showClosedNetworkStrategy) {
        this.showAllNetworkStrategy = showAllNetworkStrategy;
        this.showOpenNetworkStrategy = showOpenNetworkStrategy;
        this.showClosedNetworkStrategy = showClosedNetworkStrategy;
    }*/
}
