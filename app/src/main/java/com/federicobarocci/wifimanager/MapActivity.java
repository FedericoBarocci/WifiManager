package com.federicobarocci.wifimanager;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.federicobarocci.wifimanager.model.LocationExecutor;
import com.federicobarocci.wifimanager.model.LocationKeeper;
import com.federicobarocci.wifimanager.model.WifiElement;
import com.federicobarocci.wifimanager.model.WifiKeeper;
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
    LocationExecutor locationExecutor;

    @Inject
    WifiKeeper wifiKeeper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initializeInjectors();
        initializeViewComponents();
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);
        ((WMApplication) getApplication()).getComponent().inject(this);
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

        for(WifiElement wifiElement : wifiKeeper.getAll()) {
            LocationKeeper locationKeeper = locationExecutor.get(wifiElement.getBSSID());

            if (locationKeeper != null) {
                LatLng center = locationKeeper.getCenter().getLocation();
                map.addMarker(new MarkerOptions()
                        .position(center)
                        .title(wifiElement.getSSID()));

                CircleOptions options = new CircleOptions();
                options.center(center);
                //Radius in meters
                options.radius(locationKeeper.getCenter().getRadius());
                options.fillColor(R.color.common_action_bar_splitter);
                options.strokeColor(R.color.common_plus_signin_btn_text_light_default);
                options.strokeWidth(10);
                map.addCircle(options);
            }
        }
    }
}
