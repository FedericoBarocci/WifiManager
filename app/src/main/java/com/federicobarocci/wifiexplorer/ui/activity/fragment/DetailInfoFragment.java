package com.federicobarocci.wifiexplorer.ui.activity.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.WifiExplorerApplication;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.ui.presenter.DataSetHandler;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Federico
 */
public class DetailInfoFragment extends Fragment implements FloatingActionButton.OnClickListener {
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

    @Bind(R.id.fab)
    FloatingActionButton fabButton;

    @Inject
    DataSetHandler dataSetHandler;

    private WifiElement wifiElement;
    private LocationKeeper locationKeeper;

    public static DetailInfoFragment newInstance(WifiElement wifiElement, LocationKeeper locationKeeper) {
        Bundle args = new Bundle();

        args.putParcelable(ARGS_WIFI, wifiElement);
        args.putParcelable(ARGS_LOCATION, locationKeeper);
        DetailInfoFragment fragment = new DetailInfoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiElement = getArguments().getParcelable(ARGS_WIFI);
        locationKeeper = getArguments().getParcelable(ARGS_LOCATION);

        ((WifiExplorerApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        fabButton.setOnClickListener(this);

        card1.setText(wifiElement.getSSID());
        card2.setText(wifiElement.getBSSID());
        card3.setText(wifiElement.getCapabilities());
        card4.setText(wifiElement.getLevelString());
        card5.setText(wifiElement.getFrequencyString());
        card6.setText(wifiElement.getSignalLevelString());
        card7.setText(wifiElement.getDistanceString());

        if (locationKeeper != null)
            card8.setText(locationKeeper.toString());
        else
            card8.setText(R.string.no_information);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(final View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        if (dataSetHandler.isFavourite(wifiElement))
            builder.setMessage(R.string.ask_remove_favourite);
        else
            builder.setMessage(R.string.ask_add_favourite);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                dataSetHandler.toggleSave(wifiElement);

                if (dataSetHandler.isFavourite(wifiElement))
                    Toast.makeText(v.getContext(), R.string.saved_wifi_element, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(v.getContext(), R.string.removed_wifi_element, Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }
}
