package com.federicobarocci.wifiexplorer.model.location.hashmap;

import com.federicobarocci.wifiexplorer.model.location.LocationElement;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by federico on 25/11/15.
 */
public class LocationMap extends HashMap<String, LocationKeeper> {

    public void put(String bssid, double latitude, double longitude, double radius) {
        super.put(bssid, new LocationKeeper(new LocationElement(new LatLng(latitude, longitude), radius)));
    }

    public void insert(String bssid, LocationElement locationElement) {
        final LocationKeeper locationKeeper = get(bssid);

        if (locationKeeper == null) {
            put(bssid, new LocationKeeper(locationElement));
        } else {
            locationKeeper.addNear(locationElement);
        }

    }
}
