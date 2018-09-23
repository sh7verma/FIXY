package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.customviews.MaterialEditText;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.models.LoginModel;
import com.app.fixy.network.ApiInterface;
import com.app.fixy.network.RetrofitClient;
import com.app.fixy.utils.Consts;
import com.app.fixy.utils.Validations;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterNumberActivity extends BaseActivity {

    @BindView(R.id.ll_next)
    LinearLayout llNext;

    @BindView(R.id.ed_number)
    MaterialEditText edNumber;
    @BindView(R.id.txt_country_code)
    TextView txtCountryCode;

    @Override
    protected int getContentView() {
        return R.layout.activity_enter_number;
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initUI() {
        edNumber.setTypeface(typefaceMedium);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick(R.id.ll_next)
    void next() {
        Consts.hideKeyboard(this);
        if (Validations.checkPhoneValidation(this, edNumber)) {
            hitUserSignUp();
        }
    }


    @Override
    public void onClick(View view) {
    }


    public void hitUserSignUp() {
        ApiInterface apiInterface = RetrofitClient.getInstance();


        Call<LoginModel> call = apiInterface.create_user(txtCountryCode.getText().toString(),
                edNumber.getText().toString().trim(), InterConst.APPLICATION_MODE, InterConst.PLATFORM_TYPE, deviceToken,
                InterConst.USER_TYPE);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {

                if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {

                    setUserData(response.body().getResponse());

                    Intent intent = new Intent(EnterNumberActivity.this, OtpActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

                } else if (response.body().getCode().equals(InterConst.ERROR_RESULT)) {
                    showAlert(llNext, response.body().getError().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });

    }

    void setUserData(LoginModel.ResponseBean response) {
        utils.setString(InterConst.ACCESS_TOKEN, response.getAccess_token());
        utils.setString(InterConst.USER_ID, response.getId());
        utils.setString(InterConst.USER_NAME, response.getFullname());
        utils.setString(InterConst.PROFILE_STATUS, response.getProfile_status());
        utils.setString(InterConst.GENDER, response.getGender());
        utils.setString(InterConst.PROFILE_IMAGE, response.getProfile_pic());
        utils.setString(InterConst.EMAIL, response.getEmail());
    }


}
