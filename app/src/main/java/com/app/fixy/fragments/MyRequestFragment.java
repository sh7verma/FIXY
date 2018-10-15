package com.app.fixy.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.adapters.MyPagerAdapter;

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

    public static MyRequestFragment newInstance(Context mCont) {
        fragment = new MyRequestFragment();
        mContext = mCont;
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
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), 2, mContext));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    txtBooked.setBackground(mContext.getResources().getDrawable(R.drawable.black_round));
                    txtPending.setBackground(mContext.getResources().getDrawable(R.drawable.grey_round_stroke));
                } else {
                    txtBooked.setBackground(mContext.getResources().getDrawable(R.drawable.grey_round_stroke));
                    txtPending.setBackground(mContext.getResources().getDrawable(R.drawable.black_round));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initListeners() {
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
}
