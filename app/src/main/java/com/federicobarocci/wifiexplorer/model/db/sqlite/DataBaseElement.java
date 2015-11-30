package com.federicobarocci.wifiexplorer.model.db.sqlite;

import com.federicobarocci.wifiexplorer.model.location.LocationElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by federico on 18/11/15.
 */
public class DataBaseElement {
    public final String bssid;
    public final String ssid;
    public final String capabilities;
    public final double latitude;
    public final double longitude;
    public final double radius;

    public DataBaseElement(String bssid, String ssid, String capabilities, double latitude, double longitude, double radius) {
        this.bssid = bssid;
        this.ssid = ssid;
        this.capabilities = capabilities;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }

    public WifiElement toWifiElement() {
        return new WifiElement(bssid, ssid, capabilities);
    }

    public LocationElement toLocationElement() {
        return new LocationElement(new LatLng(latitude, longitude), radius);
    }
}
