package com.federicobarocci.wifiexplorer.ui.util;

import android.graphics.Color;

import com.federicobarocci.wifiexplorer.R;

/**
 * Created by federico on 24/11/15.
 */
public class WifiColorGMap {
    private static final String RED_BOLD = "#66f44336";
    private static final String RED_LIGHT = "#33ef9a9a";
    private static final String GREEN_BOLD = "#664caf50";
    private static final String GREEN_LIGHT = "#33a5d6a7";

    public static int getRedBold() {
//        Color color = R.color.wifi_red_bold;
//        return color;
        return Color.parseColor(RED_BOLD);
    }

    public static int getRedLight() {
//        Color color = R.color.wifi_red_light;
//        return color;
         return Color.parseColor(RED_LIGHT);
    }

    public static int getGreenBold() {
//        Color color = R.color.wifi_green_bold;
//        return color;
         return Color.parseColor(GREEN_BOLD);
    }

    public static int getGreenLight() {
//        Color color = R.color.wifi_green_light;
//        return color;
         return Color.parseColor(GREEN_LIGHT);
    }
}
