package com.app.fixy.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.models.ServicesModel;
import com.app.fixy.network.RetrofitClient;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_request_service)
    TextView txtRequestService;

    ServicesModel.ResponseBean.CategoriesBean.SubcategoriesBean mServiceDetail;

    @Override
    protected int getContentView() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void onCreateStuff() {
        mServiceDetail = getIntent().getParcelableExtra(InterConst.EXTRA);
        setData();
    }

    void setData() {
        txtPrice.setText(mServiceDetail.getCategory_price() + " Coins");
        txtTitle.setText(mServiceDetail.getCategory_name());
        txtName.setText(mServiceDetail.getCategory_name());
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {
        txtRequestService.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_request_service:
                hitApi();
                break;
        }
    }

    private void hitApi() {
        if (connectedToInternet(txtTitle)) {
            showProgress();
            Call<ServicesModel> call = RetrofitClient.getInstance().create_request(
                    utils.getString(InterConst.ACCESS_TOKEN, ""), utils.getString(InterConst.DEVICE_ID,""),
                    mServiceDetail.getId(), "0"
                    , utils.getString(InterConst.CITY_ID, ""), utils.getString(InterConst.CITY_NAME, "")
                    , "", "20", "20", "", "");
            call.enqueue(new Callback<ServicesModel>() {
                @Override
                public void onResponse(@NonNull Call<ServicesModel> call, @NonNull Response<ServicesModel> response) {
                    hideProgress();
                    if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {
                    } else if (response.body().getCode().equals(InterConst.ERROR_RESULT)) {
                        showSnackBar(txtTitle, response.body().getMessage());
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
}
