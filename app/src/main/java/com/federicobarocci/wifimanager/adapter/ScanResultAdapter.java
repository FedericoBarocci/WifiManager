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
import android.widget.Toast;

import com.federicobarocci.wifimanager.DetailActivity;
import com.federicobarocci.wifimanager.R;
import com.federicobarocci.wifimanager.model.DataBaseExecutor;
import com.federicobarocci.wifimanager.model.WifiElement;
import com.federicobarocci.wifimanager.model.WifiKeeper;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by federico on 29/10/15.
 */
public class ScanResultAdapter extends RecyclerView.Adapter<ScanResultAdapter.ViewHolder> {
    private final WifiKeeper wifiKeeper;
    private final DataBaseExecutor dataBaseExecutor;

    @Inject
    public ScanResultAdapter(WifiKeeper wifiKeeper, DataBaseExecutor dataBaseExecutor) {
        this.wifiKeeper = wifiKeeper;
        this.dataBaseExecutor = dataBaseExecutor;
    }

    @Override
    public ScanResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemLayoutView, dataBaseExecutor);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.wifiElement = wifiKeeper.get(position);
        viewHolder.txtViewTitle.setText(viewHolder.wifiElement.getTitle(position));
        viewHolder.textViewDetail.setText(viewHolder.wifiElement.getInfo(position));
        viewHolder.imgViewIcon.setImageResource(R.drawable.ic_perm_scan_wifi_black_24dp);
        viewHolder.imgSaveIcon.setImageResource(R.drawable.ic_headset);
    }

    @Override
    public int getItemCount() {
        return wifiKeeper.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final DataBaseExecutor dataBaseExecutor;
        public WifiElement wifiElement;

        @Bind(R.id.firstLine)
        public TextView txtViewTitle;

        @Bind(R.id.secondLine)
        public TextView textViewDetail;

        @Bind(R.id.listicon)
        public ImageView imgViewIcon;

        @Bind(R.id.saveicon)
        public ImageView imgSaveIcon;

        public ViewHolder(View view, DataBaseExecutor dataBaseExecutor) {
            super(view);
            ButterKnife.bind(this, view);
            this.dataBaseExecutor = dataBaseExecutor;
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
        public void onClickSave(View v) {
            Toast.makeText(v.getContext(), "Cliccato " + txtViewTitle.getText(), Toast.LENGTH_SHORT).show();
            dataBaseExecutor.toggleSave(wifiElement);
        }
    }
}
