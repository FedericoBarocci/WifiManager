package com.federicobarocci.wifiexplorer.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import com.federicobarocci.wifiexplorer.ui.adapter.ScanResultAdapter;
import com.federicobarocci.wifiexplorer.ui.presenter.DataSetHandler;
import com.federicobarocci.wifiexplorer.ui.presenter.FilterDelegate;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Federico
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.mainRecyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    FilterDelegate filterDelegate;

    @Inject
    DataSetHandler dataSetHandler;

    @Inject
    ScanResultAdapter scanResultAdapter;

    private BroadcastReceiver scanResultAvailableReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            dataSetHandler.onWifiListReceive();

            if(swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    private BroadcastReceiver fusedLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            intent.getParcelableExtra(FusedLocationService.LOCATION);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeInjectors();
        initializeViewComponents();

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

        startService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(fusedLocationReceiver,
                new IntentFilter(FusedLocationService.INTENT_LOCATION_CHANGED));

        if (dataSetHandler.checkToInitialize()) {
            Toast.makeText(this, R.string.notify_enable_scan, Toast.LENGTH_SHORT).show();
        }

        registerReceiver(scanResultAvailableReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);
        ((WifiExplorerApplication) getApplication()).getComponent().inject(this);
    }

    private void initializeViewComponents() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(scanResultAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final Menu menu = navigationView.getMenu();
        menu.findItem(R.id.navigation_near_wifi_list).setChecked(filterDelegate.isNearSelected());
        menu.findItem(R.id.navigation_session_wifi_list).setChecked(filterDelegate.isSessionSelected());
        menu.findItem(R.id.navigation_filter_all_networks).setChecked(filterDelegate.isFilterAllSelected());
        menu.findItem(R.id.navigation_filter_open_networks).setChecked(filterDelegate.isFilterOpenSelected());
        menu.findItem(R.id.navigation_filter_closed_networks).setChecked(filterDelegate.isFilterClosedSelected());
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.gps_auth_message);
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getBaseContext().unregisterReceiver(scanResultAvailableReceiver);
        stopService(new Intent(this, FusedLocationService.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(fusedLocationReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
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
        boolean keepSelection = false;

        switch (item.getItemId()) {
            case R.id.navigation_refresh:
                item.setChecked(false);
                this.onRefresh();
                break;

            case R.id.navigation_near_wifi_list:
                keepSelection = !item.isChecked();
                item.setChecked(true);

                navigationView.getMenu().findItem(R.id.navigation_session_wifi_list).setChecked(false);
                filterDelegate.showNearbyWifiList();
                break;

            case R.id.navigation_session_wifi_list:
                keepSelection = !item.isChecked();
                item.setChecked(true);

                navigationView.getMenu().findItem(R.id.navigation_near_wifi_list).setChecked(false);
                filterDelegate.showSessionWifiList();
                break;

            case R.id.navigation_favourites:
                item.setChecked(false);
                startActivity(new Intent(this, FavouritesActivity.class));
                break;

            case R.id.navigation_map:
                item.setChecked(false);
                startActivity(new Intent(this, MapActivity.class));
                break;

            case R.id.navigation_filter_all_networks:
                item.setChecked(true);

                navigationView.getMenu().findItem(R.id.navigation_filter_open_networks).setChecked(false);
                navigationView.getMenu().findItem(R.id.navigation_filter_closed_networks).setChecked(false);
                filterDelegate.showAllNetworks();
                break;

            case R.id.navigation_filter_open_networks:
                item.setChecked(true);

                navigationView.getMenu().findItem(R.id.navigation_filter_closed_networks).setChecked(false);
                navigationView.getMenu().findItem(R.id.navigation_filter_all_networks).setChecked(false);
                filterDelegate.showOnlyOpenNetworks();
                break;

            case R.id.navigation_filter_closed_networks:
                item.setChecked(true);

                navigationView.getMenu().findItem(R.id.navigation_filter_open_networks).setChecked(false);
                navigationView.getMenu().findItem(R.id.navigation_filter_all_networks).setChecked(false);
                filterDelegate.showOnlyClosedNetworks();
                break;
        }

        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawers();

        return keepSelection;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        dataSetHandler.scanWifi();
    }
}
