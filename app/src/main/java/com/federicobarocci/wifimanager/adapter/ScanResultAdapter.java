package com.federicobarocci.wifimanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.federicobarocci.wifimanager.DetailActivity;
import com.federicobarocci.wifimanager.R;
import com.federicobarocci.wifimanager.model.WifiKeeper;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by federico on 29/10/15.
 */
public class ScanResultAdapter extends RecyclerView.Adapter<ScanResultAdapter.ViewHolder> {
    private final WifiKeeper wifiKeeper;

    @Inject
    public ScanResultAdapter(WifiKeeper wifiKeeper) {
        this.wifiKeeper = wifiKeeper;
    }

    @Override
    public ScanResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.scanResult = wifiKeeper.get(position);
        viewHolder.txtViewTitle.setText(wifiKeeper.getTitle(position));
        viewHolder.textViewDetail.setText(wifiKeeper.getInfo(position));
        viewHolder.imgViewIcon.setImageResource(R.drawable.ic_perm_scan_wifi_black_24dp);
    }

    @Override
    public int getItemCount() {
        return wifiKeeper.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ScanResult scanResult;

        @Bind(R.id.firstLine)
        public TextView txtViewTitle;

        @Bind(R.id.secondLine)
        public TextView textViewDetail;

        @Bind(R.id.listicon)
        public ImageView imgViewIcon;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_NAME, scanResult);

            context.startActivity(intent);
        }
    }
}
