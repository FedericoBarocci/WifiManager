package com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by federico on 25/11/15.
 */
public class WifiListIterator implements Iterator<WifiElement> {
    private final WifiList wifiList;
    private int cursor;

    public WifiListIterator(WifiList wifiList) {
        this.wifiList = wifiList;
        this.cursor = 0;
    }

    @Override
    public boolean hasNext() {
        return cursor < wifiList.size();
    }

    @Override
    public WifiElement next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }

        return wifiList.get(cursor++);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
