package com.app.fixy.activities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.adapters.SearchCategoryAdapter;
import com.app.fixy.adapters.SearchServiceAdapter;
import com.app.fixy.customviews.MaterialEditText;
import com.app.fixy.interfaces.InterfacesCall;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchServiceActivity extends BaseActivity {


    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recycleview)
    RecyclerView rvService;
    @BindView(R.id.rv_category_slots)
    RecyclerView rvCategorySlots;
    @BindView(R.id.ic_clear)
    ImageView icClear;
    @BindView(R.id.ed_search)
    MaterialEditText edSearch;

    SearchServiceAdapter serviceAdapter;
    SearchCategoryAdapter categoryAdapter;

    InterfacesCall.IndexClick clickService = new InterfacesCall.IndexClick() {
        @Override
        public void clickIndex(int pos) {

        }
    };

    InterfacesCall.IndexClick clickCategory = new InterfacesCall.IndexClick() {
        @Override
        public void clickIndex(int pos) {

        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_search_service;
    }

    @Override
    protected void onCreateStuff() {

        rvService.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvCategorySlots.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvService.setNestedScrollingEnabled(false);
        rvCategorySlots.setNestedScrollingEnabled(false);

        serviceAdapter = new SearchServiceAdapter(mContext, mHeight, clickService);
        categoryAdapter = new SearchCategoryAdapter(mContext, mHeight, clickCategory);

        rvService.setAdapter(serviceAdapter);
        rvCategorySlots.setAdapter(categoryAdapter);
        rvCategorySlots.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initUI() {
        txtTitle.setText(R.string.looking_for);
    }

    @Override
    protected void initListener() {
        icClear.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ic_clear:
                edSearch.setText("");
                break;
        }
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
    }

    @OnClick(R.id.img_back)
    void back() {
        onBackPressed();
    }
}
