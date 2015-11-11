package com.federicobarocci.wifimanager.model;

import android.support.v4.util.Pair;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 11/11/15.
 */
public class DataBaseExecutor {

    private final DataBaseManager dataBaseManager;
    private final List<Pair<String, WifiElement>> elements;
//    private final Context context;

    @Inject
    public DataBaseExecutor(/*Context context, */DataBaseManager dataBaseManager) {
//        this.context = context;
        this.dataBaseManager = dataBaseManager;
        this.elements = dataBaseManager.select();
    }

    public void toggleSave(WifiElement wifiElement) {
        if (contains(wifiElement.getBSSID())) {
            //delete
            dataBaseManager.delete(wifiElement);
            remove(wifiElement.getBSSID());
        } else {
            //insert
            dataBaseManager.insert(wifiElement);
            elements.add(new Pair<String, WifiElement>(wifiElement.getBSSID(), wifiElement));
        }
    }

    public int size() {
        return elements.size();
    }

    private boolean contains(String key) {
        for(int i=0; i<elements.size(); i++) {
            if(elements.get(i).first.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private void remove(String key) {
        for(int i=0; i<elements.size(); i++) {
            if(elements.get(i).first.equals(key)) {
                elements.remove(i);
                return;
            }
        }
    }

    public WifiElement get(int position) {
        return elements.get(position).second;
    }
}