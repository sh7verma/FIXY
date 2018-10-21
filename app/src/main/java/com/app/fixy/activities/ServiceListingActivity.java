package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.adapters.SearchServiceAdapter;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.ServicesModel;

import java.util.ArrayList;

import butterknife.BindView;

public class ServiceListingActivity extends BaseActivity {

    @BindView(R.id.rv_services)
    RecyclerView rvService;
    @BindView(R.id.txt_service_name)
    TextView txtServiceName;

    SearchServiceAdapter mServiceAdapter;
    ServicesModel.ResponseBean.CategoriesBean mCategories;
    ArrayList<ServicesModel.ResponseBean.CategoriesBean.SubcategoriesBean> mServicesList = new ArrayList<>();

    InterfacesCall.IndexClick clickService = new InterfacesCall.IndexClick() {
        @Override
        public void clickIndex(int pos) {
            Intent intent = new Intent(mContext, ServiceDetailActivity.class);
            intent.putExtra(InterConst.EXTRA, mServicesList.get(pos));
            startActivity(intent);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_service_listing;
    }

    @Override
    protected void onCreateStuff() {
        mCategories = getIntent().getParcelableExtra(InterConst.EXTRA);
        mServicesList = mCategories.getSubcategories();

        txtServiceName.setText(mCategories.getCategory_name());
        rvService.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mServiceAdapter = new SearchServiceAdapter(mContext, mHeight, clickService, mServicesList);
        rvService.setAdapter(mServiceAdapter);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {

    }
}
