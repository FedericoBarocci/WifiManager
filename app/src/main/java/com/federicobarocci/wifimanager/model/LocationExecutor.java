package com.federicobarocci.wifimanager.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.federicobarocci.wifimanager.FusedLocationService;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by federico on 17/11/15.
 */
public class LocationExecutor {
    private static final String TAG = "LocationExecutor";

   // private final WifiKeeper wifiKeeper;
    private final Context context;

    private FusedLocationService fusedLocationService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.v(TAG, "Service: " + name + " connected");
            fusedLocationService = ((FusedLocationService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v(TAG, "Service: " + name + " disconnected");
        }
    };

    private Map<String, LocationKeeper> elements = new HashMap<>();

    @Inject
    public LocationExecutor (Context context/*, WifiKeeper wifiKeeper*/) {
       // this.wifiKeeper = wifiKeeper;
        this.context = context;
        Intent intent = new Intent(context, FusedLocationService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    
    public void store(WifiElement wifiElement) {
        if(fusedLocationService.isLocationAvailable()) {
            Location location = fusedLocationService.getLocation();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            LocationElement locationElement = new LocationElement(latLng, wifiElement.calculateDistance());

            if (elements.containsKey(wifiElement.getBSSID())) {
                elements.get(wifiElement.getBSSID()).addNear(locationElement);
            } else {
                elements.put(wifiElement.getBSSID(), new LocationKeeper(locationElement));
            }
        }
    }

    public LocationKeeper get(WifiElement wifiElement) {
        if(elements.containsKey(wifiElement.getBSSID())) {
            return elements.get(wifiElement.getBSSID());
        }
        else {
            return null;
        }
    }
}
