package com.app.fixy.activities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.adapters.SearchCategoryAdapter;
import com.app.fixy.adapters.SearchServiceAdapter;
import com.app.fixy.customviews.MaterialEditText;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.ServicesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchServiceActivity extends BaseActivity {


    static public int mSelected = -1;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recycleview)
    RecyclerView rvService;
    @BindView(R.id.rv_category_slots)
    RecyclerView rvCategorySlots;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.ed_search)
    MaterialEditText edSearch;
    SearchServiceAdapter mServiceAdapter;
    SearchCategoryAdapter mCategoryAdapter;

    ArrayList<ServicesModel.ResponseBean.CategoriesBean.SubcategoriesBean> mServicesList = new ArrayList<>();
    ArrayList<ServicesModel.ResponseBean.CategoriesBean> mCategoriesList = new ArrayList<>();

    ArrayList<ServicesModel.ResponseBean.CategoriesBean.SubcategoriesBean> mServicesListTemp = new ArrayList<>();
    ArrayList<ServicesModel.ResponseBean.CategoriesBean> mCategoriesListTemp = new ArrayList<>();

    InterfacesCall.IndexClick clickService = new InterfacesCall.IndexClick() {
        @Override
        public void clickIndex(int pos) {

        }
    };

    InterfacesCall.IndexClick clickCategory = new InterfacesCall.IndexClick() {
        @Override
        public void clickIndex(int pos) {
            mSelected = pos;

            mServicesList = new ArrayList<>();
            mServicesList.addAll(mCategoriesList.get(mSelected).getSubcategories());

            mServiceAdapter = new SearchServiceAdapter(mContext, mHeight, clickService, mServicesList);
            rvService.setAdapter(mServiceAdapter);

            mCategoryAdapter = new SearchCategoryAdapter(mContext, mHeight, clickCategory, mCategoriesList);
            rvCategorySlots.setAdapter(mCategoryAdapter);
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_search_service;
    }

    @Override
    protected void onCreateStuff() {
        resetAdapters();
    }

    @Override
    protected void initUI() {
        txtTitle.setText(R.string.looking_for);

        rvService.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvCategorySlots.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvService.setNestedScrollingEnabled(false);
        rvCategorySlots.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initListener() {
        imgClear.setOnClickListener(this);
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSelected = -1;
                resetAdapters();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    imgClear.setVisibility(View.GONE);
                    resetAdapters();
                } else {
                    imgClear.setVisibility(View.VISIBLE);

                    mCategoriesListTemp = new ArrayList<>();
                    for (int j = 0; j < mCategoriesList.size(); j++) {
                        if (mCategoriesList.get(j).getCategory_name().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                            mCategoriesListTemp.add(mCategoriesList.get(j));
                        }
                        mCategoryAdapter = new SearchCategoryAdapter(mContext, mHeight, clickCategory, mCategoriesListTemp);
                        rvCategorySlots.setAdapter(mCategoryAdapter);
                    }

                    mServicesListTemp = new ArrayList<>();
                    for (int j = 0; j < mServicesList.size(); j++) {
                        if (mServicesList.get(j).getCategory_name().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                            mServicesListTemp.add(mServicesList.get(j));
                        }
                        mServiceAdapter = new SearchServiceAdapter(mContext, mHeight, clickService, mServicesListTemp);
                        rvService.setAdapter(mServiceAdapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void resetAdapters() {
        mCategoriesList = new ArrayList<>();
        mCategoriesList = getIntent().getParcelableArrayListExtra(InterConst.EXTRA);
        mServicesList = new ArrayList<>();
        for (int i = 0; i < mCategoriesList.size(); i++) {
            mServicesList.addAll(mCategoriesList.get(i).getSubcategories());
        }

        mServiceAdapter = new SearchServiceAdapter(mContext, mHeight, clickService, mServicesList);
        mCategoryAdapter = new SearchCategoryAdapter(mContext, mHeight, clickCategory, mCategoriesList);

        rvService.setAdapter(mServiceAdapter);
        rvCategorySlots.setAdapter(mCategoryAdapter);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_clear:
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
