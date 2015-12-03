package com.federicobarocci.wifiexplorer.ui.util;

import android.graphics.Color;

/**
 * Created by Federico
 */
public class WifiColorGMap {
    private static final String RED_BOLD = "#66f44336";
    private static final String RED_LIGHT = "#33ef9a9a";
    private static final String GREEN_BOLD = "#664caf50";
    private static final String GREEN_LIGHT = "#33a5d6a7";

    public static int getRedBold() {
        return Color.parseColor(RED_BOLD);
    }

    public static int getRedLight() {
         return Color.parseColor(RED_LIGHT);
    }

    public static int getGreenBold() {
         return Color.parseColor(GREEN_BOLD);
    }

    public static int getGreenLight() {
         return Color.parseColor(GREEN_LIGHT);
    }
}
