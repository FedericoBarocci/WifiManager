package com.federicobarocci.wifiexplorer.ui.adapter.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

/**
 * Created by federico on 16/11/15.
 */
public interface SnackBarShowUndo {
    void showUndo(RecyclerView.Adapter adapter, View view, WifiElement wifiElement);
}
