package com.federicobarocci.wifiexplorer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist.WifiList;
import com.federicobarocci.wifiexplorer.ui.activity.dialog.WifiLocationDialog;
import com.federicobarocci.wifiexplorer.ui.activity.dialog.WifiSecureDialog;
import com.federicobarocci.wifiexplorer.ui.presenter.FilterDelegate;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Federico
 */
public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback, WifiLocationDialog.WifiLocationDialogListener,
        WifiSecureDialog.WifiSecureDialogListener {

    private static final int ZOOM_LEVEL = 19;

    private GoogleMap map;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    WifiKeeper wifiKeeper;

    @Inject
    LocationHandler locationHandler;

    @Inject
    DataBaseHandler dataBaseHandler;

    @Inject
    FilterDelegate filterDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initializeInjectors();
        initializeViewComponents();
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);
        ((WifiExplorerApplication) getApplication()).getComponent().inject(this);
    }

    private void initializeViewComponents() {
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(R.string.activity_map_title);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        renderMap(map, wifiKeeper.getFilteredList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_map_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_filter_location:
                DialogFragment wifiLocationDialog = new WifiLocationDialog();
                wifiLocationDialog.show(getSupportFragmentManager(), "WifiLocationDialog");
                return true;

            case R.id.map_filter_secure:
                DialogFragment wifiSecureDialog = new WifiSecureDialog();
                wifiSecureDialog.show(getSupportFragmentManager(), "WifiLocationDialog");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogNearbyClick(DialogFragment dialog) {
        filterDelegate.showNearbyWifiList();
        renderMap(map, wifiKeeper.getFilteredList());
    }

    @Override
    public void onDialogSessionClick(DialogFragment dialog) {
        filterDelegate.showSessionWifiList();
        renderMap(map, wifiKeeper.getFilteredList());
    }

    @Override
    public void onDialogFavouriteClick(DialogFragment dialog) {
        renderMap(map, dataBaseHandler.getList());
    }

    @Override
    public void onDialogOpenNetworksClick(DialogFragment dialog) {
        filterDelegate.showOnlyOpenNetworks();
        renderMap(map, wifiKeeper.getFilteredList());
    }

    @Override
    public void onDialogSecureNetworksClick(DialogFragment dialog) {
        filterDelegate.showOnlyClosedNetworks();
        renderMap(map, wifiKeeper.getFilteredList());
    }

    @Override
    public void onDialogAllNetworksClick(DialogFragment dialog) {
        filterDelegate.showAllNetworks();
        renderMap(map, wifiKeeper.getFilteredList());
    }

    private void renderMap(GoogleMap map, WifiList wifiList) {
        map.clear();
        map.setMyLocationEnabled(true);

        final LatLng currentLatLng = locationHandler.getCurrentLatLng();
        if (currentLatLng != null) {
            final CameraUpdate locationCamera = CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM_LEVEL);
            map.animateCamera(locationCamera);
        }

        for(WifiElement wifiElement : wifiList) {
            LocationKeeper locationKeeper = locationHandler.get(wifiElement.getBSSID());

            if (locationKeeper == null)
                continue;

            LatLng center = locationKeeper.getCenter().getLocation();

            map.addMarker(new MarkerOptions().position(center).title(wifiElement.getSSID()));

            CircleOptions options = new CircleOptions();
            options.center(center);
            options.radius(locationKeeper.getCenter().getRadius());
            options.fillColor(wifiElement.getLightColor());
            options.strokeColor(wifiElement.getBoldColor());
            options.strokeWidth(5);

            map.addCircle(options);
        }
    }
}
