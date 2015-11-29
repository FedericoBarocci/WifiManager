package com.federicobarocci.wifiexplorer.model.wifi.container.strategy.common;

import android.support.v7.util.SortedList;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiShowMethods;

import java.util.Iterator;

/**
 * Created by federico on 25/11/15.
 */
public class WifiList extends SortedList<WifiElement> implements Iterable<WifiElement> {

    public WifiList() {
        super(WifiElement.class, new WifiListCallback());
    }

    public WifiList filter(WifiShowMethods wifiShowMethods) {
        WifiList list = new WifiList();

        for(WifiElement wifiElement : this) {
            if (wifiShowMethods.condition(wifiElement)) {
                list.add(wifiElement);
            }
        }

        return list;
    }

    public void addUpdate(WifiElement wifiElement) {
        int position = indexOfKey(wifiElement);

        if (position == INVALID_POSITION) {
            add(wifiElement);
        }
        else {
            updateItemAt(position, wifiElement);
        }
    }

    private int indexOfKey(WifiElement wifiElement) {
        for(int i = 0; i < size(); i++) {
            if (wifiElement.getBSSID().equals(get(i).getBSSID())) {
                return i;
            }
        }

        return INVALID_POSITION;
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
