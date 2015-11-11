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

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;

import com.federicobarocci.wifimanager.adapter.DetailResultAdapter;
import com.federicobarocci.wifimanager.component.DaggerWMDetailComponent;
import com.federicobarocci.wifimanager.model.DetailModule;
import com.federicobarocci.wifimanager.model.WifiElement;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeInjectors();
        initializeViewComponents();
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);
        DaggerWMDetailComponent.builder()
                .detailModule(new DetailModule(this))
                .build().inject(this);
    }

    private void initializeViewComponents() {
        WifiElement wifiElement = getIntent().getParcelableExtra(EXTRA_NAME);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(wifiElement.getSSID());

        detailResultAdapter.addFragments(buildFragments(wifiElement));
        viewPager.setAdapter(detailResultAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private List<Pair<String, Fragment>> buildFragments(WifiElement wifiElement) {
        List<Pair<String, Fragment>> listFragments = new ArrayList<>();

        listFragments.add(Pair.<String, Fragment>create(DetailFragment.NAME, DetailFragment.newInstance(wifiElement)));
        listFragments.add(Pair.<String, Fragment>create(DetailMapFragment.NAME, DetailMapFragment.newInstance(wifiElement)));

        return listFragments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_actions, menu);
        return true;
    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

     */
}
