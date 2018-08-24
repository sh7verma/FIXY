package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.customviews.FloatingEditText;
import com.app.fixy.customviews.MaterialEditText;
import com.app.fixy.helper.GoogleMapInitiate;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.service.FetchAddressIntentService;
import com.app.fixy.utils.Animations;
import com.app.fixy.utils.Consts;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;

public class AddAddressActivity extends BaseActivity implements OnMapReadyCallback, InterfacesCall.MapInterface {

    @BindView(R.id.txt_change)
    TextView txtChange;
    @BindView(R.id.txt_address)
    MaterialEditText txtAddress;
    @BindView(R.id.et_label)
    FloatingEditText etLabelName;
    @BindView(R.id.txt_home)
    TextView txtHome;
    @BindView(R.id.txt_work)
    TextView txtWork;
    @BindView(R.id.txt_other)
    TextView txtOther;


    public static InterfacesCall.LocationInterface locationInterface;
    private boolean isGettingAddress;
    private AddressResultReceiver mResultReceiver;
    private Location mCurrentLocation;
    private final int ADDRESS_RESULT = 1;

    Location myLocation;
    String locationName;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void onCreateStuff() {
        new GoogleMapInitiate(this, null);
        mResultReceiver = new AddressResultReceiver(new Handler());
    }

    public static void setInterface(InterfacesCall.LocationInterface location) {
        locationInterface = location;
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationInterface.onpause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationInterface.onresume();
        GoogleMapInitiate.setInterface(this);
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {
        txtChange.setOnClickListener(this);
        txtHome.setOnClickListener(this);
        txtWork.setOnClickListener(this);
        txtOther.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txt_change:
                intent = new Intent(this, SearchAddressActivity.class);
                startActivityForResult(intent, ADDRESS_RESULT);
                overridePendingTransition(R.anim.in, R.anim.out);
                break;
            case R.id.txt_home:
                selectAddressType(InterConst.HOME);
                break;
            case R.id.txt_work:
                selectAddressType(InterConst.WORK);
                break;
            case R.id.txt_other:
                selectAddressType(InterConst.OTHER);
                break;
        }
    }

    private void selectAddressType(int type) {
        switch (type) {
            case InterConst.HOME:
                txtHome.setBackground(getResources().getDrawable(R.drawable.pink_solid_round_corner));
                txtWork.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));
                txtOther.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));

                txtHome.setTextColor(getResources().getColor(R.color.white));
                txtWork.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtOther.setTextColor(getResources().getColor(R.color.colorPrimary));
                etLabelName.setVisibility(View.GONE);
//                Animations.collapse(etLabelName);
                break;
            case InterConst.WORK:
                txtHome.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));
                txtWork.setBackground(getResources().getDrawable(R.drawable.pink_solid_round_corner));
                txtOther.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));

                txtHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtWork.setTextColor(getResources().getColor(R.color.white));
                txtOther.setTextColor(getResources().getColor(R.color.colorPrimary));
                etLabelName.setVisibility(View.GONE);
//                Animations.collapse(etLabelName);
                break;
            case InterConst.OTHER:
                txtHome.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));
                txtWork.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));
                txtOther.setBackground(getResources().getDrawable(R.drawable.pink_solid_round_corner));

                txtHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtWork.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtOther.setTextColor(getResources().getColor(R.color.white));
                etLabelName.setVisibility(View.VISIBLE);
//                Animations.expand(etLabelName);
                break;
            default:
                txtHome.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));
                txtWork.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));
                txtOther.setBackground(getResources().getDrawable(R.drawable.pink_stroke_round));

                txtHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtWork.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtOther.setTextColor(getResources().getColor(R.color.colorPrimary));
                etLabelName.setVisibility(View.GONE);
//                Animations.collapse(etLabelName);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case ADDRESS_RESULT:
                    myLocation = data.getParcelableExtra(InterConst.LOCATION_DATA_EXTRA);
                    locationName = data.getStringExtra(InterConst.EXTRA);
                    txtAddress.setText(locationName);

                    break;
            }
        }
    }

    @Override
    public void onLocationUpdate(LocationResult location) {
        if (mCurrentLocation == null) {
            mCurrentLocation = location.getLastLocation();
            startIntentService();
        }
        Log.d("ADD ADD", "Location " + location.getLastLocation().getLatitude());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }


    // ===============GOOGLE ADREESS FETCHER==================
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            String mAddressOutput = resultData.getString(InterConst.RESULT_DATA_KEY);
//            displayAddressOutput();
            mAddressOutput = mAddressOutput.replace("\n", " ");
            // Show a toast message if an address was found.
            if (resultCode == InterConst.SUCCESS_RESULT) {
//                Toast.makeText(mContext, "result found", Toast.LENGTH_SHORT).show();
                txtAddress.setText(mAddressOutput);
                isGettingAddress = true;


            } else {

                isGettingAddress = false;
            }
        }
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(InterConst.RECEIVER, mResultReceiver);
        intent.putExtra(InterConst.LOCATION_DATA_EXTRA, mCurrentLocation);
        startService(intent);
    }
}
