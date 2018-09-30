package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.customviews.MaterialEditText;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.models.UserModel;
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
        if (connectedToInternet(llNext)) {
            showProgress();
            Call<UserModel> call = apiInterface.create_user(txtCountryCode.getText().toString(),
                    edNumber.getText().toString().trim(), InterConst.APPLICATION_MODE,
                    InterConst.PLATFORM_TYPE,
                    deviceToken,
                    InterConst.USER_TYPE);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    hideProgress();
                    if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {
                        setUserData(response.body().getResponse());

                        if (!utils.getString(InterConst.NUMBER_REGISTERED, "0").equals(InterConst.NUMBER_IS_REGISTERED)) {
                            Intent intent = new Intent(EnterNumberActivity.this, OtpActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                        } else if (!utils.getString(InterConst.PROFILE_STATUS, "0").equals(InterConst.PROFILE_IS_CREATED)) {
                            Intent in = new Intent(mContext, CreateProfileActivity.class);
                            startActivity(in);
                            finish();
                            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                        } else if (utils.getString(InterConst.PROFILE_STATUS, "0").equals(InterConst.PROFILE_IS_CREATED)) {
                            Intent in = new Intent(mContext, LandingActivity.class);
                            startActivity(in);
                            finish();
                            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                        }

                    } else if (response.body().getCode().equals(InterConst.ERROR_RESULT)) {
                        showAlert(llNext, response.body().getError().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    hideProgress();
                    t.printStackTrace();
                }
            });
        }
    }

}
