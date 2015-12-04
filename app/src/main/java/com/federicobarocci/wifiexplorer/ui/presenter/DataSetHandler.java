package com.federicobarocci.wifiexplorer.ui.presenter;

import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.ui.adapter.FavouritesAdapter;
import com.federicobarocci.wifiexplorer.ui.adapter.ScanResultAdapter;
import com.federicobarocci.wifiexplorer.ui.util.DataBaseAction;

import javax.inject.Inject;

/**
 * Created by Federico
 */
public class DataSetHandler {
    private final DataSetExecutor dataSetExecutor;
    private final FavouritesAdapter favouritesAdapter;
    private final ScanResultAdapter scanResultAdapter;

    @Inject
    public DataSetHandler(DataSetExecutor dataSetExecutor, FavouritesAdapter favouritesAdapter, ScanResultAdapter scanResultAdapter) {
        this.dataSetExecutor = dataSetExecutor;
        this.favouritesAdapter = favouritesAdapter;
        this.scanResultAdapter = scanResultAdapter;
    }

    public void onWifiListReceive() {
        dataSetExecutor.onWifiListReceive();
        favouritesAdapter.notifyDataSetChanged();
        scanResultAdapter.notifyDataSetChanged();
    }

//    public boolean isFavourite(WifiElement wifiElement) {
//        return dataSetExecutor.isSaved(wifiElement);
//    }

    public DataBaseAction getDataBaseAction(WifiElement wifiElement) {
        return dataSetExecutor.isSaved(wifiElement)
                ? DataBaseAction.IS_PRESENT
                : DataBaseAction.NOT_PRESENT;
    }

    public void toggleSave(WifiElement wifiElement) {
        dataSetExecutor.toggleSave(wifiElement);
        favouritesAdapter.notifyDataSetChanged();
        scanResultAdapter.notifyDataSetChanged();
    }

    public void scanWifi() {
        dataSetExecutor.wifiNeedToSetEnable();
        dataSetExecutor.clearWifiList();

        scanResultAdapter.notifyDataSetChanged();
        dataSetExecutor.startScan();
    }

    public boolean checkToInitialize() {
        boolean result = dataSetExecutor.wifiNeedToSetEnable();

        if (dataSetExecutor.isWifiListEmpty()) {
            scanWifi();
        }

        return result;
    }
}
