package com.federicobarocci.wifiexplorer.ui.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.model.location.LocationKeeper;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;

/**
 * Created by federico on 07/11/15.
 */
public class DetailMapFragment extends Fragment implements OnMapReadyCallback {
    public static final String ARGS_WIFI = "WifiElement";
    public static final String ARGS_LOCATION = "LocationKeeper";
    public static final String NAME = "Map";

    private WifiElement wifiElement;
    private LocationKeeper locationKeeper;
    //private SupportMapFragment mapFragment;

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

        //mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_fragment);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(this);
        //mapFragment.getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.setMyLocationEnabled(true);
        //LocationKeeper locationKeeper = ((DetailActivity) getActivity()).locationExecutor.get(wifiElement.getBSSID());

        if (locationKeeper != null) {
            LatLng center = locationKeeper.getCenter().getLocation();
            map.addMarker(new MarkerOptions()
                    .position(center)
                    .title(wifiElement.getSSID()));

            CircleOptions options = new CircleOptions();
            options.center(center);
            //Radius in meters
            options.radius(locationKeeper.getCenter().getRadius());
            options.fillColor(R.color.common_action_bar_splitter);
            options.strokeColor(R.color.common_plus_signin_btn_text_light_default);
            options.strokeWidth(10);
            map.addCircle(options);
        }
    }
}
