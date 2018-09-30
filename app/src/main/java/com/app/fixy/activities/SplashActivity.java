package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterConst;

public class SplashActivity extends BaseActivity {

    private static final long TIME_OUT = 2000;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initUI() {
        openNextActivity();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {

    }

    void openNextActivity() {
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (!utils.getString(InterConst.NUMBER_REGISTERED, "0").equals(InterConst.NUMBER_IS_REGISTERED)) {
                    Intent in = new Intent(SplashActivity.this, EnterNumberActivity.class);
                    startActivity(in);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                } else if (!utils.getString(InterConst.PROFILE_STATUS, "0").equals(InterConst.PROFILE_IS_CREATED)) {
                    Intent in = new Intent(SplashActivity.this, CreateProfileActivity.class);
                    startActivity(in);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                } else if (utils.getString(InterConst.PROFILE_STATUS, "0").equals(InterConst.PROFILE_IS_CREATED)) {
                    Intent in = new Intent(SplashActivity.this, LandingActivity.class);
                    startActivity(in);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            }
        }, TIME_OUT);
    }
}