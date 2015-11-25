package com.federicobarocci.wifiexplorer.model.wifi.container;

import android.support.v7.util.SortedList;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiShowStrategy;

import java.util.Iterator;

/**
 * Created by federico on 25/11/15.
 */
public class WifiList extends SortedList<WifiElement> implements Iterable<WifiElement> {

    public WifiList() {
        super(WifiElement.class, new WifiListCallback());
    }

    public WifiList filter(WifiShowStrategy wifiShowStrategy) {
        WifiList list = new WifiList();

        for(WifiElement wifiElement : this) {
            if (wifiShowStrategy.condition(wifiElement)) {
                list.add(wifiElement);
            }
        }

        return list;
    }

    public WifiElement getKey(String bssid) {
        for(WifiElement wifiElement : this) {
            if (wifiElement.getBSSID().equals(bssid)) {
                return wifiElement;
            }
        }

        return null;
    }

    @Override
    public WifiElement get(int position) {
        try {
            return super.get(position);
        }
        catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public Iterator<WifiElement> iterator() {
        return new WifiListIterator(this);
    }
}
