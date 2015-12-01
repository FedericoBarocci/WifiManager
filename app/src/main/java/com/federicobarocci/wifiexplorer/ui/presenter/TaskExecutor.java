package com.federicobarocci.wifiexplorer.ui.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.model.wifi.container.WifiListEnum;
import com.federicobarocci.wifiexplorer.model.wifi.WifiShowMethods;

import javax.inject.Inject;

/**
 * Created by federico on 03/11/15.
 */
public class TaskExecutor {
    private final Context context;
    private final WifiUtilDelegate wifiUtilDelegate;
    private final ScanResultReceiver scanResultReceiver;

    @Inject
    public TaskExecutor(Context context, WifiUtilDelegate wifiUtilDelegate, ScanResultReceiver scanResultReceiver) {
        this.context = context;
        this.wifiUtilDelegate = wifiUtilDelegate;
        this.scanResultReceiver = scanResultReceiver;
    }

    public void checkToInitialize() {
        if (wifiUtilDelegate.wifiTestEnable()) {
            Toast.makeText(context, R.string.notify_enable_scan, Toast.LENGTH_SHORT).show();
        }

        if (wifiUtilDelegate.empty()) {
            scanWifi();
        }
    }

    public void scanWifi() {
        wifiUtilDelegate.scanWifiAP();
        Toast.makeText(context, R.string.notify_scan, Toast.LENGTH_SHORT).show();
    }

    public void onWifiListReceive() {
        Toast.makeText(context, R.string.notify_scan_success, Toast.LENGTH_SHORT).show();
        //wifiUtilDelegate.onWifiListReceive();
        scanResultReceiver.onWifiListReceive();
    }

    public void buildAlertMessageNoGps(Context activityContext) {
        final Context activityContextFinal = activityContext;
        final AlertDialog.Builder builder = new AlertDialog.Builder(activityContextFinal);

        builder.setMessage(R.string.gps_auth_message);
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog,
                                @SuppressWarnings("unused") final int id) {
                activityContextFinal.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                dialog.cancel();
            }
        });

        final AlertDialog alert = builder.create();
        alert.show();
    }
}
