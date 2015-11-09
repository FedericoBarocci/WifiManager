package com.federicobarocci.wifimanager;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by federico on 09/11/15.
 */
public class FusedLocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final String INTENT_FILTER = "Location_Changed";
    public static final String LOCATION = "Location_Extra";
    private static final long INTERVAL = 10000;
    private static final long FASTEST_INTERVAL = 5000;

    private final IBinder mBinder = new LocalBinder();
    private final FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    private final LocationRequest locationRequest;

    private GoogleApiClient googleApiClient;
    private Location location;

    public class LocalBinder extends Binder {
        FusedLocationService getService() {
            return FusedLocationService.this;
        }
    }

    public FusedLocationService() {
        this.locationRequest = createLocationRequest();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.e("Service", "onStartCommand");

        if (googleApiClient == null) {
            this.googleApiClient = buildGoogleApiClient();
        }

        if (googleApiClient != null && !googleApiClient.isConnected()) {
            googleApiClient.connect();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        fusedLocationProviderApi.removeLocationUpdates(googleApiClient, this);

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        this.location = fusedLocationProviderApi.getLastLocation(googleApiClient);
        fusedLocationProviderApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Intent intent = new Intent(INTENT_FILTER);
        intent.putExtra(LOCATION, location);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

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
