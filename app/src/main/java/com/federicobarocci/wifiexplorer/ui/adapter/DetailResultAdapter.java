package com.federicobarocci.wifiexplorer.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 07/11/15.
 */
public class DetailResultAdapter extends FragmentPagerAdapter {
    private List<Pair<String, Fragment>> fragments = new ArrayList<>();

    @Inject
    public DetailResultAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position).second;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).first;
    }

    public void addFragments(List<Pair<String, Fragment>> fragments) {
        this.fragments = fragments;
    }
}
