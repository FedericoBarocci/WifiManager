package com.federicobarocci.wifimanager.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.federicobarocci.wifimanager.R;
import com.federicobarocci.wifimanager.model.DataBaseExecutor;
import com.federicobarocci.wifimanager.model.WifiElement;

import javax.inject.Inject;

/**
 * Created by federico on 16/11/15.
 */
public class SnackBarUndoMain implements SnackBarShowUndo {
    private final DataBaseExecutor dataBaseExecutor;

    private RecyclerView.Adapter adapter;
    private View view;
    private WifiElement wifiElement;

    @Inject
    public SnackBarUndoMain(DataBaseExecutor dataBaseExecutor) {
        this.dataBaseExecutor = dataBaseExecutor;
    }

    @Override
    public void showUndo(RecyclerView.Adapter adapter, View view, WifiElement wifiElement) {
        this.adapter = adapter;
        this.view = view;
        this.wifiElement = wifiElement;

        boolean removed = dataBaseExecutor.toggleSave(wifiElement);
        int message = removed ? R.string.removed_wifi_element : R.string.new_wifi_element;

        adapter.notifyDataSetChanged();
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
