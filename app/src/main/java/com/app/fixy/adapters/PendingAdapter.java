package com.app.fixy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.RequestModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    Context mContext;
    private InterfacesCall.IndexClick mClick;
    private ArrayList<RequestModel.ResponseBean> mData;

    public PendingAdapter(Context con, ArrayList<RequestModel.ResponseBean> data, InterfacesCall.IndexClick click) {
        mContext = con;
        mClick = click;
        mData = data;
    }

    @NonNull
    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAdapter.ViewHolder holder, final int position) {

        holder.txtName.setText(mData.get(holder.getAdapterPosition()).getFullname());
        holder.txtId.setText("ID: "+mData.get(holder.getAdapterPosition()).getId());
        holder.txtCoins.setText(mData.get(holder.getAdapterPosition()).getOriginal_price() + " Coins");

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.clickIndex(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_coins)
        TextView txtCoins;
        @BindView(R.id.txt_id)
        TextView txtId;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}