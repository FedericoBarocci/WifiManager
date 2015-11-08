package com.federicobarocci.wifimanager.component;

import com.federicobarocci.wifimanager.DetailActivity;
import com.federicobarocci.wifimanager.DetailFragment;
import com.federicobarocci.wifimanager.adapter.DetailResultAdapter;
import com.federicobarocci.wifimanager.model.DetailModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by federico on 07/11/15.
 */
@Singleton
@Component(modules = DetailModule.class)
public interface WMDetailComponent {
    DetailResultAdapter provideDetailResultAdapter();

    void inject(DetailActivity detailActivity);
    void inject(DetailFragment detailFragment);
}
