package com.app.fixy.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
import butterknife.OnClick;
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

    @BindView(R.id.rv_recommended_services)
    RecyclerView rvRecommendedServices;
    @BindView(R.id.rv_workers_ads)
    RecyclerView rvWorkersAds;
    @BindView(R.id.txt_view_all_services)
    TextView txtViewAllServices;
    @BindView(R.id.txt_view_all_ads)
    TextView txtViewAllAds;

    RecommendedServicesAdapter mAdapterServices;
    WorkersAdsAdapter mAdapterAds;

    LinearLayoutManager mLayoutManagerServices;
    LinearLayoutManager mLayoutManagerAds;

    private ArrayList<CityModel.ResponseBean> cityList;

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
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txt_view_all_services:
                intent = new Intent(mContext, ServicesListActivity.class);
                startActivity(intent);
                break;

            case R.id.txt_view_all_ads:
                intent = new Intent(mContext, WorkersAdsListActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_city:
                if (cityList == null) {
                    hitApi();
                } else {
                    openAvailableCity();
                }
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

    @OnClick(R.id.ic_search)
    public void searchService() {
        Intent intent = new Intent(getActivity(), SearchServiceActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in, R.anim.out);
    }

    void hitApi() {
        if (connectedToInternet(rvRecommendedServices)) {
            showProgress();
            Call<CityModel> call = RetrofitClient.getInstance().city(utils.getString(InterConst.ACCESS_TOKEN, ""),
                    deviceToken);
            call.enqueue(new Callback<CityModel>() {
                @Override
                public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                    hideProgress();
                    if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {
                        cityList = response.body().getResponse();
                        if (TextUtils.isEmpty(utils.getString(InterConst.CITY_ID, ""))) {
                            openAvailableCity();
                        }
                    } else if (response.body().equals(InterConst.ERROR_RESULT)) {
                        showSnackBar(rvRecommendedServices, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<CityModel> call, Throwable t) {
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
                public void onResponse(Call<ServicesModel> call, Response<ServicesModel> response) {
                    hideProgress();
                    if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {

                    } else if (response.body().equals(InterConst.ERROR_RESULT)) {
                        showSnackBar(rvRecommendedServices, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ServicesModel> call, Throwable t) {
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
}
