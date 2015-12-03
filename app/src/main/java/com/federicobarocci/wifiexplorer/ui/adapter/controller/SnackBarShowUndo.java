package com.federicobarocci.wifiexplorer.ui.adapter.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

/**
 * Created by Federico
 */
public interface SnackBarShowUndo {
    void showUndo(RecyclerView.Adapter adapter, View view, WifiElement wifiElement);
}
