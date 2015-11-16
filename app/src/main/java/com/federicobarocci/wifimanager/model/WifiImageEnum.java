package com.federicobarocci.wifimanager.model;

import com.federicobarocci.wifimanager.R;

/**
 * Created by federico on 12/11/15.
 */
public enum WifiImageEnum {
    LEVEL_0 {
        public int getResource() {
            return R.drawable.ic_signal_wifi_1_bar_black_24dp;
        }
    },
    LEVEL_1 {
        public int getResource() {
            return R.drawable.ic_signal_wifi_2_bar_black_24dp;
        }
    },
    LEVEL_2 {
        public int getResource() {
            return R.drawable.ic_signal_wifi_3_bar_black_24dp;
        }
    },
    LEVEL_3 {
        public int getResource() {
            return R.drawable.ic_signal_wifi_4_bar_black_24dp;
        }
    };

    abstract int getResource();
}
