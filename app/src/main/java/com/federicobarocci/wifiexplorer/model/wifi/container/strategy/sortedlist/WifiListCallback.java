package com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist;

import android.support.v7.util.SortedList;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

/**
 * Created by federico on 25/11/15.
 */
public class WifiListCallback extends SortedList.Callback<WifiElement> {
    @Override
    public int compare(WifiElement o1, WifiElement o2) {
        if (o1.getdBm() < o2.getdBm()) {
            return 1;
        }

        if (o1.getdBm() > o2.getdBm()) {
            return -1;
        }

        return 0;
    }

    @Override
    public void onInserted(int position, int count) {

    }

    @Override
    public void onRemoved(int position, int count) {

    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {

    }

    @Override
    public void onChanged(int position, int count) {

    }

    @Override
    public boolean areContentsTheSame(WifiElement oldItem, WifiElement newItem) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(WifiElement item1, WifiElement item2) {
        return false;
    }
}
