package com.app.fixy.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.fixy.R;
import com.app.fixy.activities.BookingDetailActivity;
import com.app.fixy.adapters.BookingAdapter;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.models.CityModel;
import com.app.fixy.network.RetrofitClient;

import java.util.logging.Handler;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookedFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static BookedFragment fragment;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    BookingAdapter mAdapter;

    @BindView(R.id.recycleview)
    RecyclerView rvPast;

    InterfacesCall.IndexClick click = new InterfacesCall.IndexClick() {
        @Override
        public void clickIndex(int pos) {
            Intent intent = new Intent(getActivity(), BookingDetailActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.in, R.anim.out);
        }
    };

    public static BookedFragment newInstance(Context context) {
        fragment = new BookedFragment();
        mContext = context;
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragement_booked;
    }

    @Override
    protected void onCreateStuff() {
        rvPast.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvPast.setNestedScrollingEnabled(false);

        mAdapter = new BookingAdapter(mContext, click);
        rvPast.setAdapter(mAdapter);
        hitApi();
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void onClick(View view) {

    }

    void hitApi() {
        if (connectedToInternet(rvPast)) {
            showProgress();
            Call<CityModel> call = RetrofitClient.getInstance().request_history(
                    utils.getString(InterConst.ACCESS_TOKEN, ""),
                    deviceToken, InterConst.STATUS_BOOKING_REQUEST);
            call.enqueue(new Callback<CityModel>() {
                @Override
                public void onResponse(@NonNull Call<CityModel> call, @NonNull Response<CityModel> response) {
                    hideProgress();
                    if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {
                    } else if (response.body().getCode().equals(InterConst.ERROR_RESULT)) {
                        showSnackBar(rvPast, response.body().getMessage());
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

}
