package com.app.fixy.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterfacesCall;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    private final Context mContext;
    Bitmap bitmap =null;
    InterfacesCall.IndexClick mClick;
    public PendingAdapter(Context con, InterfacesCall.IndexClick click) {
        mContext = con;
        mClick = click;
    }

    @NonNull
    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pending, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAdapter.ViewHolder holder, final int position) {

        holder.viewBooking.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.clickIndex(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    public class ViewBooking {
        @BindView(R.id.ll_main)
        LinearLayout llMain;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewBooking viewBooking = new ViewBooking();
        LinearLayout llMain;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(viewBooking, itemView);


        }
    }
}