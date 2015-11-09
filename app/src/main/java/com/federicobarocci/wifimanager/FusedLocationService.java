package com.federicobarocci.wifimanager;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by federico on 09/11/15.
 */
public class FusedLocationService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final long INTERVAL = 10000;
    private static final long FASTEST_INTERVAL = 5000;

    private static final long ONE_MIN = 1000 * 60;
    private static final long REFRESH_TIME = ONE_MIN * 5;
    private static final float MINIMUM_ACCURACY = 50.0f;

    private GoogleApiClient googleApiClient;
    private Location location;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;


    FusedLocationReceiver locationReceiver = null;
    Activity locationActivity;

    public FusedLocationService(Activity locationActivity, FusedLocationReceiver locationReceiver) {
        this.locationReceiver = locationReceiver;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        this.locationActivity = locationActivity;

        googleApiClient = new GoogleApiClient.Builder(locationActivity)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        //displayLocation();
        //startLocationUpdates();
        Location currentLocation = fusedLocationProviderApi.getLastLocation(googleApiClient);
        if (currentLocation != null && currentLocation.getTime() > REFRESH_TIME) {
            location = currentLocation;
        } else {
            fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            // Schedule a Thread to unregister location listeners
            Executors.newScheduledThreadPool(1).schedule(new Runnable() {
                @Override
                public void run() {
                    fusedLocationProviderApi.removeLocationUpdates(googleApiClient,
                            FusedLocationService.this);
                }
            }, ONE_MIN, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (null == this.location || this.location.getAccuracy() < this.location.getAccuracy()) {
            this.location = location;
// let's inform my client class through the receiver
            locationReceiver.onLocationChanged();
            //if the accuracy is not better, remove all location updates for this listener
            if (this.location.getAccuracy() < MINIMUM_ACCURACY) {
                fusedLocationProviderApi.removeLocationUpdates(googleApiClient, this);
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public Location getLocation() {
        return this.location;
    }
}
