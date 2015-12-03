package com.federicobarocci.wifiexplorer.ui.activity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.federicobarocci.wifiexplorer.R;

/**
 * Created by Federico
 */
public class WifiLocationDialog extends DialogFragment implements DialogInterface.OnClickListener {

    public interface WifiLocationDialogListener {
        void onDialogNearbyClick(DialogFragment dialog);
        void onDialogSessionClick(DialogFragment dialog);
        void onDialogFavouriteClick(DialogFragment dialog);
    }

    private WifiLocationDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (WifiLocationDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WifiLocationDialogListener");
        }
    }

    @Override @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_location_title);
        builder.setItems(R.array.locations_array, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case 0:
                mListener.onDialogNearbyClick(WifiLocationDialog.this);
                break;
            case 1:
                mListener.onDialogSessionClick(WifiLocationDialog.this);
                break;
            case 2:
                mListener.onDialogFavouriteClick(WifiLocationDialog.this);
                break;
        }
    }
}
