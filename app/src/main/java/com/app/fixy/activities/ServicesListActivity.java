package com.app.fixy.activities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.app.fixy.R;
import com.app.fixy.adapters.AllServicesAdapter;

import butterknife.BindView;

public class ServicesListActivity extends BaseActivity {

    @BindView(R.id.rv_services)
    RecyclerView rvServices;

    @BindView(R.id.img_back)
    ImageView icBack;

    AllServicesAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_view_all_services;
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initUI() {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvServices.setLayoutManager(mLayoutManager);
        mAdapter = new AllServicesAdapter(mContext, mHeight);
        rvServices.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        icBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
