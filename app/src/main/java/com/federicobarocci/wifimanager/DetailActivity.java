/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.federicobarocci.wifimanager;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String EXTRA_NAME = "scan_result";
    public static final int RSSI_LEVEL = 7;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.card1)
    TextView card1;

    @Bind(R.id.card2)
    TextView card2;

    @Bind(R.id.card3)
    TextView card3;

    @Bind(R.id.backdrop)
    MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeInjectors();
        initializeViewComponents(savedInstanceState);
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);
        ((WMApplication) getApplication()).getComponent().inject(this);
    }

    private void initializeViewComponents(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        final ScanResult scanResult = getIntent().getParcelableExtra(EXTRA_NAME);
        collapsingToolbar.setTitle(scanResult.SSID);
        //collapsingToolbar.setExpandedTitleColor(R.color.trasparent);
        //collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(getBaseContext(), android.R.color.transparent));
        card1.setText(String.format("%s %s %d", scanResult.BSSID, scanResult.capabilities, scanResult.frequency));
        card2.setText(String.format("RSSI: %d dBm", scanResult.level));
        card3.setText(String.format("Signal level: %d", WifiManager.calculateSignalLevel(scanResult.level, RSSI_LEVEL)));

        /*map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this);

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);*/

        //Glide.with(this).load(R.drawable.cheese_1).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    /*@OnClick(R.id.fab_showmap)
    public void fabClick(View view) {
        startActivity(new Intent(this, MapActivity.class));
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }
}
