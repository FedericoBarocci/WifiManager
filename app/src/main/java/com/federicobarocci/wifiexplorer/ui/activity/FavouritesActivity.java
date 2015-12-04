package com.federicobarocci.wifiexplorer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.ui.adapter.FavouritesAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Federico
 */
public class FavouritesActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.favouritesRecyclerView)
    RecyclerView recyclerView;

    @Inject
    FavouritesAdapter favouritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

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
            ab.setTitle(R.string.activity_favourites_title);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favouritesAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_favourite_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_favourites:
                startActivity(new Intent(this, MapActivity.class)
                        .putExtra(MapActivity.EXTRA_WIFI_FAVOURITES, true));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
