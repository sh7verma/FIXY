package com.app.fixy.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.CityModel;
import com.app.fixy.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.ViewHolder> {

    Context con;
    Utils utils;
    ArrayList<CityModel.ResponseBean> mData;
    InterfacesCall.IndexClick mClick;

    public SelectCityAdapter(Context con, ArrayList<CityModel.ResponseBean> Model, InterfacesCall.IndexClick click) {
        this.con = con;
        mData = Model;
        mClick = click;
        utils = new Utils(con);
    }

    @Override
    public SelectCityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_city_item, parent, false);
        return new SelectCityAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SelectCityAdapter.ViewHolder holder, final int position) {
        holder.txtCityName.setText(mData.get(position).getCity_name());

        if (utils.getString(InterConst.CITY_ID, "").equals(mData.get(position).getId())) {
            holder.icCheck.setImageDrawable(ContextCompat.getDrawable(con, R.mipmap.ic_gender_s));
        } else {
            holder.icCheck.setImageDrawable(ContextCompat.getDrawable(con, R.mipmap.ic_gender_un));
        }
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_main)
        public LinearLayout llMain;
        @BindView(R.id.txt_city_name)
        public TextView txtCityName;
        @BindView(R.id.ic_check)
        public ImageView icCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
