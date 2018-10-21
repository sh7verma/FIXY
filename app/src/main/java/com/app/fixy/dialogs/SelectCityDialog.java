package com.app.fixy.dialogs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.adapters.SelectCityAdapter;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.CityModel;

import java.util.ArrayList;

import butterknife.BindView;

public class SelectCityDialog extends BaseDialog {

    private final InterfacesCall.Callback callback;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_mess)
    TextView txtMess;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    ArrayList<CityModel.ResponseBean> list;
    SelectCityAdapter mAdapter;

    public SelectCityDialog(@NonNull Context context, int themeResId, ArrayList<CityModel.ResponseBean> cityList, InterfacesCall.Callback call) {
        super(context, themeResId);
        list = cityList;
        callback = call;

        WindowManager.LayoutParams wmlp = this.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wmlp);
    }

    @Override
    public InterfacesCall.IndexClick getInterfaceInstance() {
        return this;
    }

    @Override
    protected void onCreateStuff() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SelectCityAdapter(getContext(), list, indexClick);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getContentView() {
        return R.layout.dialog_select_city;
    }

    @Override
    public void clickIndex(int pos) {
        dismiss();
        callback.selected(pos);
        mAdapter.notifyDataSetChanged();
    }
}
