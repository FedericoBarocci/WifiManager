package com.federicobarocci.wifiexplorer.ui.activity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.federicobarocci.wifiexplorer.R;

/**
 * Created by federico on 01/12/15.
 */
public class WifiLocationDialog extends DialogFragment {

    public interface WifiLocationDialogListener {
        public void onDialogNearbyClick(DialogFragment dialog);
        public void onDialogSessionClick(DialogFragment dialog);
        public void onDialogFavouriteClick(DialogFragment dialog);
    }

    WifiLocationDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (WifiLocationDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement WifiLocationDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_location_title)
                .setItems(R.array.locations_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
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
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
