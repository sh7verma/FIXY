package com.app.fixy.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.fragments.CoinsFragment;
import com.app.fixy.fragments.HomeFragment;
import com.app.fixy.fragments.MyRequestFragment;
import com.app.fixy.fragments.ProfileFragment;
import com.app.fixy.utils.Consts;

import butterknife.BindView;

public class LandingActivity extends BaseActivity {

    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.img_home)
    ImageView imgHome;
    @BindView(R.id.txt_home)
    TextView txtHome;
    @BindView(R.id.view_home)
    View viewHome;

    @BindView(R.id.ll_bookings)
    LinearLayout llBookings;
    @BindView(R.id.img_bookings)
    ImageView imgBookings;
    @BindView(R.id.txt_bookings)
    TextView txtBookings;
    @BindView(R.id.view_bookings)
    View viewBookings;

    @BindView(R.id.ll_coins)
    LinearLayout llCoins;
    @BindView(R.id.img_coins)
    ImageView imgCoins;
    @BindView(R.id.txt_coins)
    TextView txtCoins;
    @BindView(R.id.view_coins)
    View viewCoins;

    @BindView(R.id.ll_profile)
    LinearLayout llProfile;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.txt_profile)
    TextView txtProfile;
    @BindView(R.id.view_profile)
    View viewProfile;

    // Current fragment selected
    int mViewSelection = Consts.FRAG_NULL;

    @Override
    protected int getContentView() {
        return R.layout.activity_landing;
    }

    @Override
    protected void onCreateStuff() {
        loadFragment(HomeFragment.newInstance(mContext), Consts.FRAG_HOME);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {
        llHome.setOnClickListener(this);
        llBookings.setOnClickListener(this);
        llCoins.setOnClickListener(this);
        llProfile.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }


    public void loadFragment(Fragment fragment, int selected) {
        hideKeyboard(this);

        imgHome.setImageResource(R.mipmap.ic_home);
        txtHome.setTextColor(getContext().getResources().getColor(R.color.grey_text));
        viewHome.setBackgroundColor(getContext().getResources().getColor(R.color.white));

        imgBookings.setImageResource(R.mipmap.ic_booking);
        txtBookings.setTextColor(getContext().getResources().getColor(R.color.grey_text));
        viewBookings.setBackgroundColor(getContext().getResources().getColor(R.color.white));

        imgCoins.setImageResource(R.mipmap.ic_coins);
        txtCoins.setTextColor(getContext().getResources().getColor(R.color.grey_text));
        viewCoins.setBackgroundColor(getContext().getResources().getColor(R.color.white));

        imgProfile.setImageResource(R.mipmap.ic_profile);
        txtProfile.setTextColor(getContext().getResources().getColor(R.color.grey_text));
        viewProfile.setBackgroundColor(getContext().getResources().getColor(R.color.white));

        if (selected == Consts.FRAG_HOME) {
            imgHome.setImageResource(R.mipmap.ic_home_a);
            txtHome.setTextColor(getContext().getResources().getColor(R.color.app_color));
            viewHome.setBackgroundColor(getContext().getResources().getColor(R.color.app_color));
        } else if (selected == Consts.FRAG_BOOKINGS) {
            imgBookings.setImageResource(R.mipmap.ic_booking_a);
            txtBookings.setTextColor(getContext().getResources().getColor(R.color.app_color));
            viewBookings.setBackgroundColor(getContext().getResources().getColor(R.color.app_color));
        } else if (selected == Consts.FRAG_COINS) {
            imgCoins.setImageResource(R.mipmap.ic_coins_a);
            txtCoins.setTextColor(getContext().getResources().getColor(R.color.app_color));
            viewCoins.setBackgroundColor(getContext().getResources().getColor(R.color.app_color));
        } else if (selected == Consts.FRAG_PROFILE) {
            imgProfile.setImageResource(R.mipmap.ic_profile_s);
            txtProfile.setTextColor(getContext().getResources().getColor(R.color.app_color));
            viewProfile.setBackgroundColor(getContext().getResources().getColor(R.color.app_color));
        }

        if (mViewSelection != selected) {
            mViewSelection = selected;
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.commit();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                loadFragment(HomeFragment.newInstance(mContext), Consts.FRAG_HOME);

                break;
            case R.id.ll_bookings:
                loadFragment(MyRequestFragment.newInstance(mContext), Consts.FRAG_BOOKINGS);

                break;
            case R.id.ll_coins:
                loadFragment(CoinsFragment.newInstance(mContext), Consts.FRAG_COINS);

                break;
            case R.id.ll_profile:
                loadFragment(ProfileFragment.newInstance(mContext), Consts.FRAG_PROFILE);

                break;
        }

    }
}
