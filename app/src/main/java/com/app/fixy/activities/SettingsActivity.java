package com.app.fixy.activities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.fixy.R;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;


    @Override
    protected int getContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                moveBack();
                break;
        }
    }

    void moveBack() {
        finish();
        overridePendingTransition(R.anim.in, R.anim.out);
    }

}
