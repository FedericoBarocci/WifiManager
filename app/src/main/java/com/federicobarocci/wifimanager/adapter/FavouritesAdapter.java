package com.federicobarocci.wifimanager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.federicobarocci.wifimanager.R;
import com.federicobarocci.wifimanager.model.DataBaseExecutor;
import com.federicobarocci.wifimanager.model.WifiElement;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by federico on 11/11/15.
 */
public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    private final DataBaseExecutor dataBaseExecutor;

    @Inject
    public FavouritesAdapter(DataBaseExecutor dataBaseExecutor) {
        this.dataBaseExecutor = dataBaseExecutor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.wifiElement = dataBaseExecutor.get(position);
        viewHolder.txtViewTitle.setText(viewHolder.wifiElement.getTitle(position));
        viewHolder.textViewDetail.setText(viewHolder.wifiElement.getInfo(position));
        viewHolder.imgViewIcon.setImageResource(R.drawable.ic_perm_scan_wifi_black_24dp);
        viewHolder.imgSaveIcon.setImageResource(R.drawable.ic_headset);
    }

    @Override
    public int getItemCount() {
        return dataBaseExecutor.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public WifiElement wifiElement;

        @Bind(R.id.firstLine)
        public TextView txtViewTitle;

        @Bind(R.id.secondLine)
        public TextView textViewDetail;

        @Bind(R.id.listicon)
        public ImageView imgViewIcon;

        @Bind(R.id.saveicon)
        public ImageView imgSaveIcon;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
