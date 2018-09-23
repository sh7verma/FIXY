package com.app.fixy.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fixy.R;
import com.app.fixy.customviews.MaterialEditText;
import com.app.fixy.helper.GoogleMapInitiate;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.interfaces.InterfacesCall;
import com.app.fixy.service.FetchAddressIntentService;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;

public class MapAddressActivity extends BaseActivity implements OnMapReadyCallback, InterfacesCall.MapInterface {

    public static InterfacesCall.LocationInterface locationInterface;
    @BindView(R.id.et_address)
    MaterialEditText etAddress;
    @BindView(R.id.txt_done)
    TextView txtDone;
    @BindView(R.id.img_back)
    ImageView icBack;
    Location myLocation;
    String addressName;
    private GoogleMap mMap;
    private AddressResultReceiver mResultReceiver;
    private boolean isGettingAddress = false;
    private double lat, lon;

    public static void setInterface(InterfacesCall.LocationInterface location) {
        locationInterface = location;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_map_address;
    }

    @Override
    protected void onCreateStuff() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        new GoogleMapInitiate(this, mapFragment, 2);
        setData();
        mResultReceiver = new AddressResultReceiver(new Handler());

    }

    private void setData() {
        myLocation = getIntent().getParcelableExtra(InterConst.LOCATION_DATA_EXTRA);
        addressName = getIntent().getStringExtra(InterConst.EXTRA);
        etAddress.setText(addressName);
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

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(InterConst.RECEIVER, mResultReceiver);
        intent.putExtra(InterConst.LOCATION_DATA_EXTRA, myLocation);
        startService(intent);
    }

    public void setMyLocationMarker(Location location) {

        if (location == null || location.getLongitude() == 00) {
            return;
        }
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myLocation)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (ActivityCompat.checkSelfPermission(MapAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MapAddressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initListener() {
        txtDone.setOnClickListener(this);
        icBack.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_done:
                Intent intent = new Intent();
                intent.putExtra(InterConst.LOCATION_DATA_EXTRA, myLocation);
                intent.putExtra(InterConst.EXTRA, addressName);
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                break;
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                break;
        }
    }

    @Override
    public void onLocationUpdate(LocationResult location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMyLocationMarker(myLocation);
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                isGettingAddress = false;
                if (mMap != null && mMap.getCameraPosition().target != null) {

//                    marker.setPosition(mMap.getCameraPosition().target);
//                    etAddress.setText(R.string.fetching);
                }
            }
        });
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Location updateLocation = new Location("update");
                lat = mMap.getCameraPosition().target.latitude;
                lon = mMap.getCameraPosition().target.longitude;
                updateLocation.setLatitude(lat);
                updateLocation.setLongitude(lon);
                myLocation = updateLocation;
                startIntentService();//setOnCameraIdleListener

            }
        });
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
            if (resultCode == Integer.parseInt(InterConst.SUCCESS_RESULT)) {
//                Toast.makeText(mContext, "result found", Toast.LENGTH_SHORT).show();
                addressName = mAddressOutput;
                etAddress.setText(mAddressOutput);
                isGettingAddress = true;


            } else {
                isGettingAddress = false;
                etAddress.setText(mAddressOutput);
            }
        }
    }
}
