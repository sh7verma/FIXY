package com.app.fixy.activities;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.app.fixy.R;
import com.app.fixy.adapters.AdsAdapter;
import com.app.fixy.customviews.GridSpacingItemDecoration;

import butterknife.BindView;

public class WorkersAdsListActivity extends BaseActivity {

    @BindView(R.id.rv_ads)
    RecyclerView rvAds;

    @BindView(R.id.img_back)
    ImageView imgBack;

    AdsAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_workers_ads_list;
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initUI() {

        mAdapter = new AdsAdapter(mContext, mHeight);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        rvAds.setLayoutManager(mLayoutManager);
        rvAds.addItemDecoration(new GridSpacingItemDecoration(2, (int) (mHeight * 0.010), true));
        rvAds.setItemAnimator(new DefaultItemAnimator());
        rvAds.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
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
