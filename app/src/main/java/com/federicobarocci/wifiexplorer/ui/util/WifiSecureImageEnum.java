package com.federicobarocci.wifiexplorer.ui.util;

import com.federicobarocci.wifiexplorer.R;

/**
 * Created by federico on 12/11/15.
 */
public enum WifiSecureImageEnum {
    LEVEL_0_WPA {
        public int getResource() {
            return R.drawable.ic_signal_wifi_1_bar_lock_black_24dp;
        }
    },
    LEVEL_1_WPA {
        public int getResource() {
            return R.drawable.ic_signal_wifi_2_bar_lock_black_24dp;
        }
    },
    LEVEL_2_WPA {
        public int getResource() {
            return R.drawable.ic_signal_wifi_3_bar_lock_black_24dp;
        }
    },
    LEVEL_3_WPA {
        public int getResource() {
            return R.drawable.ic_signal_wifi_4_bar_lock_black_24dp;
        }
    };

    abstract int getResource();
}
