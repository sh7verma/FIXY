package com.app.fixy.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.adapters.MyPagerAdapter;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.utils.Utils;

import butterknife.BindView;

/**
 * Created by Shubham verma on 16-08-2018.
 */

public class MyRequestFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    public static MyRequestFragment fragment;
    @SuppressLint("StaticFieldLeak")
    public static Context mContext;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.txt_booked)
    TextView txtBooked;
    @BindView(R.id.txt_pending)
    TextView txtPending;
    private LocalBroadcastManager broadcaster;
    MyPagerAdapter myPagerAdapter;
    public static Utils util;

    public static MyRequestFragment newInstance(Context mCont) {
        fragment = new MyRequestFragment();
        mContext = mCont;
        util = new Utils(mCont);
        util.setInt(InterConst.BOOKED_FRAG,InterConst.ONE);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_request;
    }

    @Override
    protected void onCreateStuff() {
        setViewPager();
    }

    private void setViewPager() {
        viewPager.setOffscreenPageLimit(0);
        myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), 2, mContext);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {

                    utils.setInt(InterConst.BOOKED_FRAG,InterConst.ONE);
                    txtBooked.setBackground(mContext.getResources().getDrawable(R.drawable.black_round));
                    txtPending.setBackground(mContext.getResources().getDrawable(R.drawable.grey_round_stroke));

                } else {
                    utils.setInt(InterConst.BOOKED_FRAG,InterConst.TWO);
                    txtBooked.setBackground(mContext.getResources().getDrawable(R.drawable.grey_round_stroke));
                    txtPending.setBackground(mContext.getResources().getDrawable(R.drawable.black_round));
                }

                getContext().sendBroadcast(new Intent(InterConst.FRAG_MY_REQUEST_CLICK));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((receiver),
                new IntentFilter(InterConst.FRAG_MY_REQUEST_CLICK));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    @Override
    protected void initListeners() {
        broadcaster = LocalBroadcastManager.getInstance(mContext);

        txtBooked.setOnClickListener(this);
        txtPending.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_booked:
                viewPager.setCurrentItem(0);
                break;
            case R.id.txt_pending:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (utils.getInt(InterConst.BOOKED_FRAG,-1) == InterConst.ONE){

                ((BookedFragment)myPagerAdapter.getItem(0)).updateAdater();
            }
            else {
                ((PendingFragment)myPagerAdapter.getItem(1)).updateAdater();
            }
        }
    };
}
