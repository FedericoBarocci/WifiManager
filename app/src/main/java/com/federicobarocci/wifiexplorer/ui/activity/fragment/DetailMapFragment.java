package com.federicobarocci.wifiexplorer.ui.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.model.db.DataBaseHandler;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Federico
 */
public class DetailMapFragment extends Fragment implements OnMapReadyCallback {
    public static final String ARGS_WIFI = "WifiElement";
    public static final String ARGS_LOCATION = "LocationKeeper";
    public static final String NAME = "Map";

    private static final int ZOOM_LEVEL = 19;

    private WifiElement wifiElement;
    private LocationKeeper locationKeeper;

    @Inject
    DataBaseHandler dataBaseHandler;

    public static DetailMapFragment newInstance(WifiElement wifiElement, LocationKeeper locationKeeper) {
        Bundle args = new Bundle();

        args.putParcelable(ARGS_WIFI, wifiElement);
        args.putParcelable(ARGS_LOCATION, locationKeeper);
        DetailMapFragment fragment = new DetailMapFragment();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (locationKeeper == null) {
            return;
        }

        final LatLng center = locationKeeper.getCenter().getLocation();
        final CameraUpdate locationCamera = CameraUpdateFactory.newLatLngZoom(center, ZOOM_LEVEL);

        map.setMyLocationEnabled(true);
        map.animateCamera(locationCamera);
        map.addMarker(new MarkerOptions()
                .position(center)
                .title(wifiElement.getSSID()));

        final CircleOptions options = new CircleOptions();
        options.center(center);
        options.radius(locationKeeper.getCenter().getRadius());
        options.fillColor(wifiElement.getLightColor());
        options.strokeColor(wifiElement.getBoldColor());
        options.strokeWidth(5);

        map.addCircle(options);
    }
}
