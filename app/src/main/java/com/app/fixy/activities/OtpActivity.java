package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fixy.R;
import com.app.fixy.customviews.MaterialEditText;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.models.LoginModel;
import com.app.fixy.network.RetrofitClient;
import com.app.fixy.utils.Consts;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends BaseActivity {

    public CountDownTimer mTimer;
    public long time;
    @BindView(R.id.ed_first)
    MaterialEditText edFirst;
    @BindView(R.id.ed_second)
    MaterialEditText edSecond;
    @BindView(R.id.ed_third)
    MaterialEditText edThird;
    @BindView(R.id.ed_fourth)
    MaterialEditText edFourth;
    @BindView(R.id.ll_next)
    LinearLayout llNext;

    @BindView(R.id.txt_timer)
    TextView txtTimer;
    @BindView(R.id.ll_call)
    LinearLayout llCall;


    @Override
    protected int getContentView() {
        return R.layout.activity_otp;
    }

    @Override
    protected void onCreateStuff() {
        llNext.setEnabled(false);
    }

    @Override
    protected void initUI() {
        edFirst.setTypeface(typefaceMedium);
        edSecond.setTypeface(typefaceMedium);
        edThird.setTypeface(typefaceMedium);
        edFourth.setTypeface(typefaceMedium);
    }

    @Override
    protected void initListener() {

        edFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edFirst.getText().toString().length() == 1) {
                    edSecond.requestFocus();
                    edSecond.setEnabled(true);
                    if (edFirst.getText().toString().length() > 0 && edSecond.getText().toString().length() > 0
                            && edThird.getText().toString().length() > 0 && edFourth.getText().toString().length() > 0) {
                        checkOTP();
                    }
                } else {
                    if (edFirst.getText().toString().length() > 0) {
                        edFirst.setText(edFirst.getText().toString().substring(0, 1));
                        edSecond.requestFocus();
                        llNext.setEnabled(false);

                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        edSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                Log.d("1", "1");
                if (edSecond.getText().toString().length() == 1) {
                    edThird.requestFocus();
                    edThird.setEnabled(true);
                    if (edFirst.getText().toString().length() > 0 && edSecond.getText().toString().length() > 0
                            && edThird.getText().toString().length() > 0 && edFourth.getText().toString().length() > 0) {
                        checkOTP();
                    }
                } else if (edSecond.getText().toString().length() == 0) {
                    edFirst.setSelection(edFirst.getText().toString().length());
                    edFirst.requestFocus();
                    llNext.setEnabled(false);
                } else {
                    edSecond.setText(edSecond.getText().toString().substring(0, 1));
                    edThird.requestFocus();
                    llNext.setEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                Log.d("2", "2");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Log.d("3", "3");
            }
        });
        edThird.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edThird.getText().toString().length() == 1) {
                    edFourth.requestFocus();
                    edFourth.setEnabled(true);
                    if (edFirst.getText().toString().length() > 0 && edSecond.getText().toString().length() > 0
                            && edThird.getText().toString().length() > 0 && edFourth.getText().toString().length() > 0) {
                        checkOTP();
                    }
                } else if (edThird.getText().toString().length() == 0) {
                    edSecond.setSelection(edSecond.getText().toString().length());
                    edSecond.requestFocus();
                    llNext.setEnabled(false);

                } else {
                    edThird.setText(edThird.getText().toString().substring(0, 1));
                    edFourth.requestFocus();
                    llNext.setEnabled(false);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (edThird.getText().toString().length() == 1) {
                    edFourth.requestFocus();
                }
            }
        });
        edFourth.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (edFourth.getText().toString().length() == 1) {
                    if (edFirst.getText().toString().length() > 0 && edSecond.getText().toString().length() > 0
                            && edThird.getText().toString().length() > 0 && edFourth.getText().toString().length() > 0) {
                        checkOTP();
                    }
                } else if (edFourth.getText().toString().length() == 0) {
                    edThird.setSelection(edThird.getText().toString().length());
                    edThird.requestFocus();
                    llNext.setEnabled(false);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected Context getContext() {
        return this;
    }

    private void checkOTP() {
        if (connectedToInternet()) {
            llNext.setEnabled(true);
            hitOtpApi();
        } else {
            Toast.makeText(mContext, R.string.internet, Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.ll_next)
    void next() {
        Consts.hideKeyboard(this);
        hitOtpApi();
    }

    private String makeOTP() {
        StringBuilder builder = new StringBuilder();
        builder.append(edFirst.getText().toString());
        builder.append(edSecond.getText().toString());
        builder.append(edThird.getText().toString());
        builder.append(edFourth.getText().toString());
        return builder.toString();
    }

    public void hitOtpApi() {

        Call<LoginModel> call = RetrofitClient.getInstance().confirm_otp(
                utils.getString(InterConst.ACCESS_TOKEN, ""),
                deviceToken,
                makeOTP());
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                if (response.body().getCode().equals(InterConst.SUCCESS_RESULT)) {

                    setUserData(response.body().getResponse());

                    Intent intent = new Intent(OtpActivity.this, CreateProfileActivity.class);
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

    @Override
    public void onClick(View view) {

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
