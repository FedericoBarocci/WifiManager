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
public class WifiSecureDialog extends DialogFragment {

    public interface WifiSecureDialogListener {
        public void onDialogOpenNetworksClick(DialogFragment dialog);
        public void onDialogSecureNetworksClick(DialogFragment dialog);
        public void onDialogAllNetworksClick(DialogFragment dialog);
    }

    WifiSecureDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (WifiSecureDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement WifiSecureDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_secure_title)
                .setItems(R.array.secure_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                mListener.onDialogOpenNetworksClick(WifiSecureDialog.this);
                                break;
                            case 1:
                                mListener.onDialogSecureNetworksClick(WifiSecureDialog.this);
                                break;
                            case 2:
                                mListener.onDialogAllNetworksClick(WifiSecureDialog.this);
                                break;
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
