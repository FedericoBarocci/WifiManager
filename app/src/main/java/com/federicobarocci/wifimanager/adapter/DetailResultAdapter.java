package com.federicobarocci.wifimanager.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.federicobarocci.wifimanager.DetailFragment;
import com.federicobarocci.wifimanager.DetailMapFragment;

import javax.inject.Inject;

/**
 * Created by federico on 07/11/15.
 */
public class DetailResultAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Info", "Map" };

    private ScanResult scanResult;

    @Inject
    public DetailResultAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return DetailFragment.newInstance(scanResult);
        else {
            return DetailFragment.newInstance(scanResult);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
