package com.federicobarocci.wifiexplorer.model.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseManager;
import com.federicobarocci.wifiexplorer.model.location.hashmap.LocationMap;
import com.federicobarocci.wifiexplorer.model.service.FusedLocationService;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 17/11/15.
 */
public class LocationHandler {
    private static final String TAG = "LocationHandler";

    private FusedLocationService fusedLocationService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "Service: " + name + " connected");
            fusedLocationService = ((FusedLocationService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "Service: " + name + " disconnected");
        }
    };

    private final LocationMap locationMap = new LocationMap();

    @Inject
    public LocationHandler(Context context, DataBaseManager dataBaseManager) {
        Intent intent = new Intent(context, FusedLocationService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        locationMap.putAll(dataBaseManager.selectLocationElements());
    }

    public LatLng getCurrentLatLng() {
        if (fusedLocationService == null || fusedLocationService.isLocationUnavailable()) {
            return null;
        }

        final Location location = fusedLocationService.getLocation();

        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public void populate(List<WifiElement> wifiElementList) {
        //NB. fusedLocationService may be not yet available...
        if (fusedLocationService == null || fusedLocationService.isLocationUnavailable()) {
            return;
        }

        final Location location = fusedLocationService.getLocation();
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        for (WifiElement wifiElement : wifiElementList) {
            locationMap.insert(wifiElement.getBSSID(), new LocationElement(latLng, wifiElement.calculateDistance()));
        }
    }

    public LocationKeeper get(String bssid) {
        return locationMap.get(bssid);
    }

    public boolean contain(String bssid) {
        return locationMap.containsKey(bssid);
    }


//    public void populate(List<DataBaseElement> dbElements) {
//        for (DataBaseElement dataBaseElement : dbElements) {
//            if (dataBaseElement.hasLocation()) {
//                if (locationMap.containsKey(dataBaseElement.getBSSID())) {
//                    locationMap.get(dataBaseElement.getBSSID()).setCenter(dataBaseElement.toLocationElement());
//                } else {
//                    locationMap.put(dataBaseElement.getBSSID(), new LocationKeeper(dataBaseElement.toLocationElement()));
//                }
//            }
//        }
//    }

//    public Set<String> getAllKeys() {
//        return locationMap.keySet();
//    }
}
