package com.federicobarocci.wifimanager.model;

import android.content.Context;
import android.widget.Toast;

import com.federicobarocci.wifimanager.R;

import javax.inject.Inject;

/**
 * Created by federico on 03/11/15.
 */
public class TaskExecutor {
    private final Context context;
    private final WifiUtilDelegate wifiUtilDelegate;

    @Inject
    public TaskExecutor(Context context, WifiUtilDelegate wifiUtilDelegate) {
        this.context = context;
        this.wifiUtilDelegate = wifiUtilDelegate;
    }

    public void checkToInitialize() {
        if (wifiUtilDelegate.wifiTestEnable()) {
            Toast.makeText(context, R.string.notify_enable_scan, Toast.LENGTH_SHORT).show();
        }

        if (wifiUtilDelegate.empty()) {
            this.scanWifi();
        }
    }

    public void scanWifi() {
        wifiUtilDelegate.scanWifiAP();
        Toast.makeText(context, R.string.notify_scan, Toast.LENGTH_SHORT).show();
    }

    public void onWifiListReceive() {
        Toast.makeText(context, R.string.notify_scan_success, Toast.LENGTH_SHORT).show();
        wifiUtilDelegate.onWifiListReceive();
    }
}
