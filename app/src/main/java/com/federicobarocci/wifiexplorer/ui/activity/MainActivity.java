package com.federicobarocci.wifiexplorer.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.model.service.FusedLocationService;
import com.federicobarocci.wifiexplorer.ui.presenter.TaskExecutor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.mainRecyclerView)
    RecyclerView recyclerView;

    /*@Bind(R.id.fab)
    FloatingActionButton fab;*/

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    TaskExecutor taskExecutor;

    private BroadcastReceiver scanResultAvailableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            taskExecutor.onWifiListReceive();

            if(swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
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

        //taskExecutor.checkPlayServices(this);

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            taskExecutor.buildAlertMessageNoGps(this);
        }

        startService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(fusedLocationReceiver, new IntentFilter(FusedLocationService.INTENT_LOCATION_CHANGED));

        taskExecutor.checkToInitialize();
        registerReceiver(scanResultAvailableReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);

        // Perform injection so that when this call returns all dependencies will be available for use.
        ((WifiExplorerApplication) getApplication()).getComponent().inject(this);
        //WifiExplorerComponent getComponent = DaggerWifiExplorerComponent.builder().vehicleModule(new DaggerModule(this)).build();
        //Vehicle vehicle = getComponent.provideVehicle();
    }

    private void initializeViewComponents() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(((WifiExplorerApplication) getApplication()).getComponent().provideScanResultAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getBaseContext().unregisterReceiver(scanResultAvailableReceiver);
        stopService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fusedLocationReceiver);
    }

    /*@Override
    protected void onResume() {
        super.onResume();

        Log.e("MAIN", "onResume");
        navigationView.setNavigationItemSelectedListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
    }*/

    @Override
    protected void onStart() {
        super.onStart();

        //Log.e("MAIN", "onStart");
        //navigationView.setNavigationItemSelectedListener(this);
        //swipeRefreshLayout.setOnRefreshListener(this);

        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
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
   /* @OnClick(R.id.fab)
    public void fabClick(View view) {
        taskExecutor.scanWifi();
    }*/

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
        switch (item.getItemId()) {
            case R.id.navigation_refresh:
                item.setChecked(false);
                this.onRefresh();
                break;

            case R.id.navigation_favourites:
                item.setChecked(false);
                startActivity(new Intent(this, FavouritesActivity.class));
                break;

            case R.id.navigation_map:
                item.setChecked(false);
                startActivity(new Intent(this, MapActivity.class));
                break;

            case R.id.navigation_filter_open_network:
                item.setChecked(!item.isChecked());
                navigationView.getMenu().findItem(R.id.navigation_filter_closed_network).setChecked(false);
                taskExecutor.showOnlyOpenNetwork(item.isChecked());
                break;

            case R.id.navigation_filter_closed_network:
                item.setChecked(!item.isChecked());
                navigationView.getMenu().findItem(R.id.navigation_filter_open_network).setChecked(false);
                taskExecutor.showOnlyClosedNetwork(item.isChecked());
                break;
        }

        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawers();

        return false;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        taskExecutor.scanWifi();
    }
}
