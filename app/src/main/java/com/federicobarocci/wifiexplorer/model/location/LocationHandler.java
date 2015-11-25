package com.federicobarocci.wifiexplorer.model.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseElement;
import com.federicobarocci.wifiexplorer.model.service.FusedLocationService;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private Map<String, LocationKeeper> elements = new HashMap<>();

    @Inject
    public LocationHandler(Context context) {
        Intent intent = new Intent(context, FusedLocationService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    
    public void store(WifiElement wifiElement) {
        if (fusedLocationService.isLocationAvailable()) {
            Location location = fusedLocationService.getLocation();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            LocationElement locationElement = new LocationElement(latLng, wifiElement.calculateDistance());

            if (elements.containsKey(wifiElement.getBSSID())) {
                elements.get(wifiElement.getBSSID()).addNear(locationElement);
            }
            else {
                elements.put(wifiElement.getBSSID(), new LocationKeeper(locationElement));
            }
        }
    }

    public LocationKeeper get(String bssid) {
        return elements.get(bssid);
    }

    public boolean contain(String bssid) {
        return elements.containsKey(bssid);
    }

    public void populate(List<DataBaseElement> dbElements) {
        for (DataBaseElement dataBaseElement : dbElements) {
            if (dataBaseElement.hasLocation()) {
                if (elements.containsKey(dataBaseElement.getBSSID())) {
                    elements.get(dataBaseElement.getBSSID()).setCenter(dataBaseElement.toLocationElement());
                } else {
                    elements.put(dataBaseElement.getBSSID(), new LocationKeeper(dataBaseElement.toLocationElement()));
                }
            }
        }
    }

    public Set<String> getAllKeys() {
        return elements.keySet();
    }
}
