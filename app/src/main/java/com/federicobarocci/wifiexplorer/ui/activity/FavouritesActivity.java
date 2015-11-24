package com.federicobarocci.wifiexplorer.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.WifiExplorerApplication;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by federico on 10/11/15.
 */
public class FavouritesActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.favouritesRecyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        initializeInjectors();
        initializeViewComponents();
    }

    private void initializeInjectors() {
        ButterKnife.bind(this);
    }

    private void initializeViewComponents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.activity_favourites_title);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(((WifiExplorerApplication) getApplication()).getComponent().provideFavouritesAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
