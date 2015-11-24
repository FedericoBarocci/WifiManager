package com.federicobarocci.wifiexplorer.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.federicobarocci.wifiexplorer.model.wifi.WifiKeeper;
import com.federicobarocci.wifiexplorer.ui.adapter.controller.SnackBarShowUndo;
import com.federicobarocci.wifiexplorer.ui.adapter.controller.SnackBarUndoMain;
import com.federicobarocci.wifiexplorer.ui.activity.DetailActivity;
import com.federicobarocci.wifiexplorer.R;
import com.federicobarocci.wifiexplorer.ui.util.ResourceProvider;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by federico on 29/10/15.
 */
public class ScanResultAdapter extends RecyclerView.Adapter<ScanResultAdapter.ViewHolder> {
    private final WifiKeeper wifiKeeper;
    private final SnackBarUndoMain snackBarUndoMain;
    private final ResourceProvider resourceProvider;

    @Inject
    public ScanResultAdapter(WifiKeeper wifiKeeper, SnackBarUndoMain snackBarUndoMain, ResourceProvider resourceProvider) {
        this.wifiKeeper = wifiKeeper;
        this.snackBarUndoMain = snackBarUndoMain;
        this.resourceProvider = resourceProvider;
    }

    @Override
    public ScanResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemLayoutView, snackBarUndoMain, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        WifiElement wifiElement = wifiKeeper.get(position);

        viewHolder.wifiElement = wifiElement;
        viewHolder.textViewTitle.setText(wifiElement.getSSID());
        viewHolder.textViewBSSID.setText(wifiElement.getBSSID());
        viewHolder.textViewDetail.setText(wifiElement.getCapabilities());
        viewHolder.imgViewIcon.setImageResource(resourceProvider.getWifiResource(wifiElement));
        viewHolder.imgSaveIcon.setImageResource(resourceProvider.getSavedResource(wifiElement));
    }

    @Override
    public int getItemCount() {
        return wifiKeeper.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final SnackBarShowUndo snackBarShowUndo;
        private final RecyclerView.Adapter adapter;

        public WifiElement wifiElement;

        @Bind(R.id.firstLine)
        public TextView textViewTitle;

        @Bind(R.id.secondLine)
        public TextView textViewBSSID;

        @Bind(R.id.thirdLine)
        public TextView textViewDetail;

        @Bind(R.id.network_info_img)
        public ImageView imgViewIcon;

        @Bind(R.id.saveicon)
        public ImageView imgSaveIcon;

        public ViewHolder(View view, SnackBarShowUndo snackBarShowUndo, RecyclerView.Adapter adapter) {
            super(view);
            ButterKnife.bind(this, view);

            this.snackBarShowUndo = snackBarShowUndo;
            this.adapter = adapter;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_NAME, wifiElement);

            context.startActivity(intent);
        }

        @OnClick(R.id.saveicon)
        public void onClickSave(View view) {
            snackBarShowUndo.showUndo(adapter, (RecyclerView) view.getParent().getParent(), wifiElement);
        }
    }
}
