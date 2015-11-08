package com.federicobarocci.wifimanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by federico on 07/11/15.
 */
public class DetailMapFragment extends Fragment {
    public static Fragment newInstance() {
        Bundle args = new Bundle();
       // args.putInt(ARG_PAGE, page);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
