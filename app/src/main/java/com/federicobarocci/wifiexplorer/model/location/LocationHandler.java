package com.federicobarocci.wifiexplorer.model.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;

import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseManager;
import com.federicobarocci.wifiexplorer.model.location.hashmap.LocationMap;
import com.federicobarocci.wifiexplorer.model.service.FusedLocationService;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Federico
 */
public class LocationHandler {
    private final LocationMap locationMap = new LocationMap();

    private FusedLocationService fusedLocationService;

    @Inject
    public LocationHandler(Context context, DataBaseManager dataBaseManager) {
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                fusedLocationService = ((FusedLocationService.LocalBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {}
        };

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

    //public boolean contain(String bssid) {
//        return locationMap.containsKey(bssid);
//    }
}
