package com.federicobarocci.wifiexplorer.ui.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by federico on 07/11/15.
 */
public class DetailFragment extends Fragment {
    public static final String ARGS_WIFI = "WifiElement";
    public static final String ARGS_LOCATION = "LocationKeeper";
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
    private LocationKeeper locationKeeper;

    public static DetailFragment newInstance(WifiElement wifiElement, LocationKeeper locationKeeper) {
        Bundle args = new Bundle();

        args.putParcelable(ARGS_WIFI, wifiElement);
        args.putParcelable(ARGS_LOCATION, locationKeeper);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiElement = getArguments().getParcelable(ARGS_WIFI);
        locationKeeper = getArguments().getParcelable(ARGS_LOCATION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        card1.setText(wifiElement.getSSID());
        card2.setText(wifiElement.getBSSID());
        card3.setText(wifiElement.getCapabilities());
        card4.setText(wifiElement.getLevelString());
        card5.setText(wifiElement.getFrequencyString());
        card6.setText(wifiElement.getSignalLevelString());
        card7.setText(wifiElement.getDistanceString());

        //LocationKeeper locationKeeper = ((DetailActivity) getActivity()).locationExecutor.get(wifiElement.getBSSID());

        if (locationKeeper != null) {
            card8.setText(locationKeeper.toString());
        }
        else {
            card8.setText(R.string.no_information);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
