package com.federicobarocci.wifimanager.model;


import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.federicobarocci.wifimanager.DetailActivity;
import com.federicobarocci.wifimanager.adapter.DetailResultAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by federico on 07/11/15.
 */
@Module
public class DetailModule {
    private final DetailActivity detailActivity;

    public DetailModule(DetailActivity detailActivity) {
        this.detailActivity = detailActivity;
    }

    @Provides
    public DetailActivity provideDetailActivity() {
        return detailActivity;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return detailActivity.getSupportFragmentManager();
    }

    @Provides
    @Singleton
    DetailResultAdapter provideDetailResultAdapter(FragmentManager fragmentManager) {
        return new DetailResultAdapter(fragmentManager);
    }

    @Provides
    @Singleton
    DetailDataContainer provideDetailDataContainer() {
        return new DetailDataContainer();
    }
}
