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
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.federicobarocci.wifimanager.adapter.DetailResultAdapter;
import com.federicobarocci.wifimanager.component.DaggerWMDetailComponent;
import com.federicobarocci.wifimanager.component.WMDetailComponent;
import com.federicobarocci.wifimanager.model.DetailDataContainer;
import com.federicobarocci.wifimanager.model.DetailModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "scan_result";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Inject
    DetailResultAdapter detailResultAdapter;
/*
    @Inject
    DetailDataContainer detailDataContainer;

    private ScanResult scanResult;

    private WMDetailComponent component;

    public ScanResult getScanResult() {
        return scanResult;
    }

    public WMDetailComponent getComponent() {
        return component;
    }
*/
    private WMDetailComponent component;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeInjectors();
        initializeViewComponents();
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);

        component = DaggerWMDetailComponent.builder()
                .detailModule(new DetailModule(this))
                .build();
        component.inject(this);
    }

    private void initializeViewComponents() {
        ScanResult scanResult = getIntent().getParcelableExtra(EXTRA_NAME);
        //detailDataContainer.setScanResult(scanResult);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(scanResult.SSID);

        detailResultAdapter.setScanResult(scanResult);
        viewPager.setAdapter(detailResultAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //viewPager.setAdapter(new DetailResultAdapter(getSupportFragmentManager()));
        //viewPager.setTag(scanResult);
//        toolbar.setTitle(scanResult.SSID);
        //Toast.makeText(getBaseContext(), scanResult.SSID, Toast.LENGTH_SHORT).show();
        /*card1.setText(String.format("%s %s %d", scanResult.BSSID, scanResult.capabilities, scanResult.frequency));
        card2.setText(String.format("RSSI: %d dBm", scanResult.level));
        card3.setText(String.format("Signal level: %d", WifiManager.calculateSignalLevel(scanResult.level, RSSI_LEVEL)));*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
