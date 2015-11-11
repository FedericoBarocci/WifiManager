package com.federicobarocci.wifimanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.federicobarocci.wifimanager.model.TaskExecutor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.mainRecyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    TaskExecutor taskExecutor;

    private BroadcastReceiver scanResultAvailableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            taskExecutor.onWifiListReceive();
        }
    };

    private BroadcastReceiver fusedLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(FusedLocationService.LOCATION);
            Toast.makeText(getApplicationContext(), String.format("lat %f long %f",
                    location.getLatitude(), location.getLongitude()), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeInjectors();
        initializeViewComponents();

        checkPlayServices();

        startService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(fusedLocationReceiver, new IntentFilter(FusedLocationService.INTENT_LOCATION_CHANGED));

        taskExecutor.checkToInitialize();
        registerReceiver(scanResultAvailableReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);

        // Perform injection so that when this call returns all dependencies will be available for use.
        ((WMApplication) getApplication()).getComponent().inject(this);
        //WMComponent getComponent = DaggerWMComponent.builder().vehicleModule(new DaggerModule(this)).build();
        //Vehicle vehicle = getComponent.provideVehicle();
    }

    private void initializeViewComponents() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(((WMApplication) getApplication()).getComponent().provideScanResultAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onDestroy() {
        getBaseContext().unregisterReceiver(scanResultAvailableReceiver);
        stopService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fusedLocationReceiver);

        super.onDestroy();
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(fusedLocationReceiver, new IntentFilter(FusedLocationService.INTENT_LOCATION_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fusedLocationReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(fusedLocationReceiver, new IntentFilter(FusedLocationService.INTENT_LOCATION_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fusedLocationReceiver);
    }
*/
    @OnClick(R.id.fab)
    public void fabClick(View view) {
        taskExecutor.scanWifi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.main_show_favourites:
                startActivity(new Intent(this, FavouritesActivity.class));
                return true;

            case R.id.main_show_map:
                startActivity(new Intent(this, MapActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        Toast.makeText(getApplicationContext(), "Ciao", Toast.LENGTH_SHORT).show();

        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawers();

        return true;
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }

            return false;
        }

        return true;
    }
}
