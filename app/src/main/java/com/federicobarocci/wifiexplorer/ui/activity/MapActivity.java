package com.federicobarocci.wifiexplorer.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
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
 * Created by federico on 05/11/15.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    /*Bind(R.id.nav_view)
    NavigationView navigationView;*/

    @Inject
    WifiKeeper wifiKeeper;

    @Inject
    LocationHandler locationHandler;

    @Inject
    DataBaseHandler dataBaseHandler;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.activity_map_title);
        //navigationView.setNavigationItemSelectedListener(this);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
//        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_actions, menu);
        return true;
    }*/

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);

        for (String bssid : locationHandler.getAllKeys()) {
            LocationKeeper locationKeeper = locationHandler.get(bssid);
            String ssid;

            if (wifiKeeper.contains(bssid)) {
                ssid = wifiKeeper.getElement(bssid).getSSID();
            }
            else if (dataBaseHandler.contains(bssid)) {
                ssid = dataBaseHandler.getElement(bssid).getSSID();
            }
            else {
                ssid = bssid;
            }

            LatLng center = locationKeeper.getCenter().getLocation();
            map.addMarker(new MarkerOptions()
                    .position(center)
                    .title(ssid));

            CircleOptions options = new CircleOptions();
            options.center(center);
            //Radius in meters
            options.radius(locationKeeper.getCenter().getRadius());
            options.fillColor(R.color.common_action_bar_splitter);
            options.strokeColor(R.color.common_plus_signin_btn_text_light_default);
            options.strokeWidth(10);
            map.addCircle(options);
        }

        /*for(WifiElement wifiElement : wifiKeeper.getAll()) {
            LocationKeeper locationHandler = locationHandler.get(wifiElement.getBSSID());

            if (locationHandler != null) {
                LatLng center = locationHandler.getCenter().getLocation();
                map.addMarker(new MarkerOptions()
                        .position(center)
                        .title(wifiElement.getSSID()));

                CircleOptions options = new CircleOptions();
                options.center(center);
                //Radius in meters
                options.radius(locationHandler.getCenter().getRadius());
                options.fillColor(R.color.common_action_bar_splitter);
                options.strokeColor(R.color.common_plus_signin_btn_text_light_default);
                options.strokeWidth(10);
                map.addCircle(options);
            }
        }*/
    }
}
