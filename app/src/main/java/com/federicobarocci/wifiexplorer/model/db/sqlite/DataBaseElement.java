package com.federicobarocci.wifiexplorer.model.db.sqlite;

import com.federicobarocci.wifiexplorer.model.location.LocationElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by federico on 18/11/15.
 */
public class DataBaseElement {
    private final String bssid;
    private final String ssid;
    private final String capabilities;
    private final boolean hasLocationInfo;
    
    private double latitude;
    private double longitude;
    private double radius;

    public DataBaseElement(String bssid, String ssid, String capabilities, double latitude, double longitude, double radius) {
        this.bssid = bssid;
        this.ssid = ssid;
        this.capabilities = capabilities;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.hasLocationInfo = true;
    }

    public DataBaseElement(WifiElement wifiElement, LocationElement locationElement) {
        this.bssid = wifiElement.getBSSID();
        this.ssid = wifiElement.getSSID();
        this.capabilities = wifiElement.getCapabilities();
        this.latitude = locationElement.getLocation().latitude;
        this.longitude = locationElement.getLocation().longitude;
        this.radius = locationElement.getRadius();
        this.hasLocationInfo = true;
    }

    public DataBaseElement(WifiElement wifiElement) {
        this.bssid = wifiElement.getBSSID();
        this.ssid = wifiElement.getSSID();
        this.capabilities = wifiElement.getCapabilities();
        this.hasLocationInfo = false;
    }

    public String getBSSID() {
        return bssid;
    }

    public String getSSID() {
        return ssid;
    }

    /*public String getCapabilities() {
        return capabilities;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRadius() {
        return radius;
    }*/

    public boolean hasLocation() {
        return hasLocationInfo;
    }

    public WifiElement toWifiElement() {
        return new WifiElement(bssid, ssid, capabilities);
    }

    public LocationElement toLocationElement() {
        return new LocationElement(new LatLng(latitude, longitude), radius);
    }
}
