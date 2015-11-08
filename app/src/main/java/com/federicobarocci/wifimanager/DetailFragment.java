package com.federicobarocci.wifimanager;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by federico on 07/11/15.
 */
public class DetailFragment extends Fragment {
    public static final String ARGS = "ScanResult";
    public static final String NAME = "Info";
    public static final int RSSI_LEVEL = 7;

    @Bind(R.id.card1)
    TextView card1;

    @Bind(R.id.card2)
    TextView card2;

    @Bind(R.id.card3)
    TextView card3;

    private ScanResult scanResult;

    public static DetailFragment newInstance(ScanResult scanResult) {
        Bundle args = new Bundle();

        args.putParcelable(ARGS, scanResult);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanResult = getArguments().getParcelable(ARGS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        card1.setText(String.format("%s %s %d", scanResult.BSSID, scanResult.capabilities, scanResult.frequency));
        card2.setText(String.format("RSSI: %d dBm", scanResult.level));
        card3.setText(String.format("Signal level: %d", WifiManager.calculateSignalLevel(scanResult.level, RSSI_LEVEL)));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
