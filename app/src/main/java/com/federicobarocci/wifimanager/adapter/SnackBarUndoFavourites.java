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
public class SnackBarUndoFavourites implements View.OnClickListener, SnackBarShowUndo {
    private final DataBaseExecutor dataBaseExecutor;

    private RecyclerView.Adapter adapter;
    private View view;
    private WifiElement wifiElement;

    @Inject
    public SnackBarUndoFavourites(DataBaseExecutor dataBaseExecutor) {
        this.dataBaseExecutor = dataBaseExecutor;
    }

    @Override
    public void showUndo(RecyclerView.Adapter adapter, View view, WifiElement wifiElement) {
        this.adapter = adapter;
        this.view = view;
        this.wifiElement = wifiElement;

        dataBaseExecutor.toggleSave(wifiElement);
        adapter.notifyDataSetChanged();
        Snackbar.make(view, R.string.removed_wifi_element, Snackbar.LENGTH_INDEFINITE).setAction(R.string.undo, this).show();
    }

    @Override
    public void onClick(View v) {
        dataBaseExecutor.toggleSave(wifiElement);
        adapter.notifyDataSetChanged();
        Snackbar.make(view, R.string.restored_wifi_element, Snackbar.LENGTH_SHORT).show();
    }
}
