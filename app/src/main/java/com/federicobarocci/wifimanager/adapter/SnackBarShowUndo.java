package com.federicobarocci.wifimanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.federicobarocci.wifimanager.model.WifiElement;

/**
 * Created by federico on 16/11/15.
 */
public interface SnackBarShowUndo {
    void showUndo(RecyclerView.Adapter adapter, View view, WifiElement wifiElement);
}
