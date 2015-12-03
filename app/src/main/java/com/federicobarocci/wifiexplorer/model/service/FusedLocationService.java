package com.federicobarocci.wifiexplorer.model.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Federico
 */
public class FusedLocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static final String INTENT_LOCATION_CHANGED = "Location_Changed";
    public static final String LOCATION = "Location_Extra";

    private static final long INTERVAL = 10000;
    private static final long FASTEST_INTERVAL = 5000;

    private final IBinder mBinder = new LocalBinder();
    private final FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location location;

    public class LocalBinder extends Binder {
        public FusedLocationService getService() {
            return FusedLocationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.locationRequest = createLocationRequest();
        this.googleApiClient = buildGoogleApiClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        locationProvider.removeLocationUpdates(googleApiClient, this);

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        this.location = locationProvider.getLastLocation(googleApiClient);
        locationProvider.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;

        Intent intent = new Intent(INTENT_LOCATION_CHANGED);
        intent.putExtra(LOCATION, location);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isLocationUnavailable() {
        return location == null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    protected synchronized GoogleApiClient buildGoogleApiClient() {
        return new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected LocationRequest createLocationRequest() {
        return LocationRequest.create()
                .setInterval(INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}
