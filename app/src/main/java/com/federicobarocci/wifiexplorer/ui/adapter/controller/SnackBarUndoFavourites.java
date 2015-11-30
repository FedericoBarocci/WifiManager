package com.federicobarocci.wifiexplorer.ui.adapter.controller;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.db.sqlite.DataBaseElement;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

import javax.inject.Inject;

/**
 * Created by federico on 16/11/15.
 */
public class SnackBarUndoFavourites implements View.OnClickListener, SnackBarShowUndo {
    private final DataBaseHandler dataBaseHandler;

    private RecyclerView.Adapter adapter;
    private View view;
    private DataBaseElement dataBaseElement;

    @Inject
    public SnackBarUndoFavourites(DataBaseHandler dataBaseHandler) {
        this.dataBaseHandler = dataBaseHandler;
    }

    @Override
    public void showUndo(RecyclerView.Adapter adapter, View view, WifiElement wifiElement) {
        this.adapter = adapter;
        this.view = view;
        //this.wifiElement = wifiElement;

        dataBaseElement = dataBaseHandler.toggleSave(wifiElement);
        adapter.notifyDataSetChanged();
        Snackbar.make(view, R.string.removed_wifi_element, Snackbar.LENGTH_INDEFINITE).setAction(R.string.undo, this).show();
    }

    @Override
    public void onClick(View v) {
        dataBaseHandler.restore(dataBaseElement);
        adapter.notifyDataSetChanged();
        Snackbar.make(view, R.string.restored_wifi_element, Snackbar.LENGTH_SHORT).show();
    }
}
