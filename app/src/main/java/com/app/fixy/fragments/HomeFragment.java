package com.app.fixy.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.activities.SearchServiceActivity;
import com.app.fixy.activities.ServicesListActivity;
import com.app.fixy.activities.WorkersAdsListActivity;
import com.app.fixy.adapters.RecommendedServicesAdapter;
import com.app.fixy.adapters.WorkersAdsAdapter;
import com.app.fixy.dialogs.SelectCityDialog;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.CityModel;
import com.app.fixy.models.ServicesModel;
import com.app.fixy.network.RetrofitClient;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shubham verma on 16-08-2018.
 */

public class HomeFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static HomeFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.txt_city)
    TextView txtCity;
    @BindView(R.id.img_search)
    ImageView imgSearch;

    @BindView(R.id.rv_recommended_services)
    RecyclerView rvRecommendedServices;
    @BindView(R.id.rv_workers_ads)
    RecyclerView rvWorkersAds;
    @BindView(R.id.txt_view_all_services)
    TextView txtViewAllServices;
    @BindView(R.id.txt_view_all_ads)
    TextView txtViewAllAds;

    @BindView(R.id.img_main_first)
    ImageView imgFirst;
    @BindView(R.id.txt_main_first)
    TextView txtFirst;

    @BindView(R.id.img_main_second)
    ImageView imgSecond;
    @BindView(R.id.txt_main_second)
    TextView txtSecond;

    @BindView(R.id.img_main_third)
    ImageView imgThird;
    @BindView(R.id.txt_main_third)
    TextView txtThird;

    @BindView(R.id.img_main_forth)
    ImageView imgForth;
    @BindView(R.id.txt_main_forth)
    TextView txtForth;

    @BindView(R.id.img_main_fifth)
    ImageView imgFifth;
    @BindView(R.id.txt_main_fifth)
    TextView txtFifth;

    @BindView(R.id.img_main_sixth)
    ImageView imgSixth;
    @BindView(R.id.txt_main_sixth)
    TextView txtSixth;

    RecommendedServicesAdapter mAdapterServices;
    WorkersAdsAdapter mAdapterAds;

    LinearLayoutManager mLayoutManagerServices;
    LinearLayoutManager mLayoutManagerAds;

    private ArrayList<CityModel.ResponseBean> cityList;
    private ArrayList<ServicesModel.ResponseBean.CategoriesBean> mCategoriesList;

    public static HomeFragment newInstance(Context context) {
        fragment = new HomeFragment();
        mContext = context;
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateStuff() {
        mLayoutManagerServices = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvRecommendedServices.setLayoutManager(mLayoutManagerServices);
        rvRecommendedServices.setNestedScrollingEnabled(false);
        mAdapterServices = new RecommendedServicesAdapter(getActivity(), mHeight);
        rvRecommendedServices.setAdapter(mAdapterServices);

        mLayoutManagerAds = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvWorkersAds.setLayoutManager(mLayoutManagerAds);
        rvWorkersAds.setNestedScrollingEnabled(false);
        mAdapterAds = new WorkersAdsAdapter(getActivity(), mHeight);
        rvWorkersAds.setAdapter(mAdapterAds);
    }

    @Override
    protected void initListeners() {
        txtViewAllServices.setOnClickListener(this);
        txtViewAllAds.setOnClickListener(this);
        txtCity.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txt_view_all_services:
                if (mCategoriesList != null) {
                    intent = new Intent(mContext, ServicesListActivity.class);
                    intent.putExtra(InterConst.EXTRA, mCategoriesList);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.in, R.anim.out);
                }
                break;

            case R.id.txt_view_all_ads:
                intent = new Intent(mContext, WorkersAdsListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in, R.anim.out);

                break;
            case R.id.txt_city:
                if (cityList == null) {
                    hitApi();
                } else {
                    openAvailableCity();
                }
                break;
            case R.id.img_search:
                intent = new Intent(getActivity(), SearchServiceActivity.class);
                intent.putExtra(InterConst.EXTRA, mCategoriesList);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in, R.anim.out);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtCity.setText(utils.getString(InterConst.CITY_NAME, getString(R.string.select_city)));
        if (TextUtils.isEmpty(utils.getString(InterConst.CITY_ID, ""))) {
            hitApi();
        } else {
            hitServiceApi();
        }
    }

    void hitApi() {
        if (connectedToInternet(rvRecommendedServices)) {
            showProgress();
            Call<CityModel> call = RetrofitClient.getInstance().city(utils.getString(InterConst.ACCESS_TOKEN, ""),
                    deviceToken);
            call.enqueue(new Callback<CityModel>() {
                @Override
                public void onResponse(@NonNull Call<CityModel> call, @NonNull Response<CityModel> response) {
                    hideProgress();
                    if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {
                        cityList = response.body().getResponse();
                        openAvailableCity();
                    } else if (response.body().getCode().equals(InterConst.ERROR_RESULT)) {
                        showSnackBar(rvRecommendedServices, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CityModel> call, @NonNull Throwable t) {
                    hideProgress();
                    t.printStackTrace();
                }
            });
        }
    }

    void hitServiceApi() {
        if (connectedToInternet(rvRecommendedServices)) {
            showProgress();
            Call<ServicesModel> call = RetrofitClient.getInstance().services(utils.getString(InterConst.ACCESS_TOKEN, ""),
                    deviceToken, utils.getString(InterConst.CITY_ID, ""));
            call.enqueue(new Callback<ServicesModel>() {
                @Override
                public void onResponse(@NonNull Call<ServicesModel> call, @NonNull Response<ServicesModel> response) {
                    hideProgress();
                    if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {
                        setMainServiceData(response.body().getResponse().getCategories());
                    } else if (response.body().getCode().equals(InterConst.ERROR_RESULT)) {
                        showSnackBar(rvRecommendedServices, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ServicesModel> call, @NonNull Throwable t) {
                    hideProgress();
                    t.printStackTrace();
                }
            });
        }
    }

    private void openAvailableCity() {
        SelectCityDialog dialog = new SelectCityDialog(mContext, R.style.pullBottomfromTop, cityList, new InterfacesCall.Callback() {
            @Override
            public void selected(int pos) {
                utils.setString(InterConst.CITY_NAME, cityList.get(pos).getCity_name());
                utils.setString(InterConst.CITY_ID, cityList.get(pos).getId());
                txtCity.setText(cityList.get(pos).getCity_name());
                hitServiceApi();
            }
        });
        dialog.show();
    }

    private void setMainServiceData(ArrayList<ServicesModel.ResponseBean.CategoriesBean> categories) {
        mCategoriesList = categories;

        if (categories != null) {
            if (categories.size() >= 1)
                txtFirst.setText(categories.get(0).getCategory_name());
            if (categories.size() >= 2)
                txtSecond.setText(categories.get(1).getCategory_name());
            if (categories.size() >= 3)
                txtThird.setText(categories.get(2).getCategory_name());
            if (categories.size() >= 4)
                txtForth.setText(categories.get(3).getCategory_name());
            if (categories.size() >= 5)
                txtFifth.setText(categories.get(4).getCategory_name());
            if (categories.size() >= 6)
                txtSixth.setText(categories.get(5).getCategory_name());
        }
    }
}
