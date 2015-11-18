package com.federicobarocci.wifimanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.federicobarocci.wifimanager.model.LocationKeeper;
import com.federicobarocci.wifimanager.model.WifiElement;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by federico on 07/11/15.
 */
public class DetailFragment extends Fragment {
    public static final String ARGS = "ScanResult";
    public static final String NAME = "Info";

    @Bind(R.id.card1)
    TextView card1;

    @Bind(R.id.card2)
    TextView card2;

    @Bind(R.id.card3)
    TextView card3;

    @Bind(R.id.card4)
    TextView card4;

    @Bind(R.id.card5)
    TextView card5;

    @Bind(R.id.card6)
    TextView card6;

    @Bind(R.id.card7)
    TextView card7;

    @Bind(R.id.card8)
    TextView card8;

    private WifiElement wifiElement;

    public static DetailFragment newInstance(WifiElement wifiElement) {
        Bundle args = new Bundle();

        args.putParcelable(ARGS, wifiElement);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiElement = getArguments().getParcelable(ARGS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        card1.setText(wifiElement.getSSID());
        card2.setText(wifiElement.getBSSID());
        card3.setText(String.format("%d dBm", wifiElement.getLevel()));
        card4.setText(wifiElement.getCapabilities());
        card5.setText(String.format("%d MHz", wifiElement.getFrequency()));
        card6.setText(String.format("%d/%d", wifiElement.getSignalLevel() + 1, wifiElement.RSSI_LEVEL));
        card7.setText(String.format("%f m", wifiElement.calculateDistance()));

        LocationKeeper locationKeeper = ((DetailActivity) getActivity()).locationExecutor.get(wifiElement);

        if (locationKeeper != null) {
            card8.setText(locationKeeper.toString());
        }
        else {
            card8.setText("No information available");
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
