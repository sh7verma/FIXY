package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.app.fixy.R;
import com.app.fixy.adapters.GooglePlaceAdapter;
import com.app.fixy.adapters.NearByPlaceAdapter;
import com.app.fixy.customviews.FloatingEditText;
import com.app.fixy.interfaces.AddressInterface;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.models.GooglePlaceModal;
import com.app.fixy.models.NearbyPlaceModel;
import com.app.fixy.network.ApiInterface;
import com.app.fixy.network.RetrofitClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAddressActivity extends BaseActivity implements AddressInterface {


    @BindView(R.id.et_search)
    FloatingEditText etSearch;
    @BindView(R.id.recycleview)
    RecyclerView recycleView;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    @BindView(R.id.img_back)
    ImageView icBack;
    @BindView(R.id.ll_current_location)
    LinearLayout llCurrentLocation;

    int EXTRA = -1;
    private List<GooglePlaceModal.PredictionsBean> list;
    private GooglePlaceAdapter placeAdapter;
    private NearByPlaceAdapter nearByPlaceAdapter;
    private List<NearbyPlaceModel.ResultsBean> listNearBy;
    private GoogleApiClient mGoogleApiClient;
    private final int ADDRESS_RESULT = 12;
    Location mCurrentLocation;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_address;
    }

    @Override
    protected void initUI() {
        mCurrentLocation = getIntent().getParcelableExtra(InterConst.LOCATION_DATA_EXTRA);

    }

    @Override
    protected void onCreateStuff() {
        buildGoogleApiClient();
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    text = s.toString();
                    imgCancel.setVisibility(View.VISIBLE);
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, 10);
//                    getPlaceNearbyPlaceUrl(s.toString());
//                    getPlaceAutoCompleteUrl(s.toString());
                } else {
                    recycleView.setVisibility(View.GONE);
                    imgCancel.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    String text;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (connectedToInternet()) {

                getPlaceAutoCompleteUrl(text);
//                getPlaceNearbyPlaceUrl(text);
            } else {
//                Intent intent1 = new Intent(SearchAddressActivity.this,InternetDialog.class);
//                startActivity(intent1);
            }
        }
    };
    Handler handler = new Handler();

    @Override
    protected void initListener() {
        icBack.setOnClickListener(this);
        imgCancel.setOnClickListener(this);
        llCurrentLocation.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_cancel:
                etSearch.setText("");
                break;
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                break;
            case R.id.ll_current_location:
                intent = new Intent(SearchAddressActivity.this, MapAddressActivity.class);
                intent.putExtra(InterConst.LOCATION_DATA_EXTRA, mCurrentLocation);
                startActivityForResult(intent, ADDRESS_RESULT);
                overridePendingTransition(R.anim.in, R.anim.out);
                break;
        }
    }


    public void getPlaceAutoCompleteUrl(String input) {
        //nearby service
//        https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=30.6577781,76.732713&radius=500&name=max&key=AIzaSyBAPTeUV-04HFtCIt3Ac8MtVFqim9CDlV4

        //autocomplete
//        https://maps.googleapis.com/maps/api/place/autocomplete/json?input=ncm+
// &location=30.6577781,76.7327138&radius=1000&types=geocode&language=en&key=AIzaSyBAPTeUV-04HFtCIt3Ac8MtVFqim9CDlV4
        ApiInterface apiInterface = RetrofitClient.getGoogleClient().create(ApiInterface.class);
        ;
        StringBuilder urlString = new StringBuilder();
        urlString.append("json?input=");
        try {
            urlString.append(URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&location=");
        urlString.append(mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude()); // append lat long of current location to show nearby results.
        urlString.append("&radius=500");
        urlString.append("&sensor=true");
//        urlString.append("&type=geocode");
//        urlString.append("&type=address");
        urlString.append("&language=en");
        urlString.append("&key=" + getString(R.string.mapKey));
        Log.d("Destination", "apiInter URL " + urlString.toString());
        Call<GooglePlaceModal> call = apiInterface.getGooglePlaces(urlString.toString());
        call.enqueue(new Callback<GooglePlaceModal>() {
            @Override
            public void onResponse(Call<GooglePlaceModal> call, Response<GooglePlaceModal> response) {
                Log.d("Destination", "apiInter response " + response.body().getPredictions().size());
                if (response.body().getPredictions().size() > 0) {
                    recycleView.setVisibility(View.VISIBLE);
                    list = new ArrayList<>();
                    list = response.body().getPredictions();

                    placeAdapter = new GooglePlaceAdapter(SearchAddressActivity.this, list, mWidth, getAddressInterface());
                    recycleView.setAdapter(placeAdapter);
                    placeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GooglePlaceModal> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setInterface(this);
    }

    public void getPlaceNearbyPlaceUrl(String input) {
        //nearby service
//        https://maps.googleapis.com/maps/api/place/nearbysearch/json?
// location=30.6577781,76.732713&radius=500&name=max&key=AIzaSyBAPTeUV-04HFtCIt3Ac8MtVFqim9CDlV4

        //autocomplete
//        https://maps.googleapis.com/maps/api/place/autocomplete/json?input=ncm+
// &location=30.6577781,76.7327138&radius=1000&types=geocode&language=en&key=AIzaSyBAPTeUV-04HFtCIt3Ac8MtVFqim9CDlV4
        ApiInterface apiInterface = RetrofitClient.getClientNearBy().create(ApiInterface.class);
        ;
        StringBuilder urlString = new StringBuilder();
        urlString.append("json?keyword=");
        try {
            urlString.append(URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&limit=20");
        urlString.append("&location=");
        urlString.append(mCurrentLocation.getLatitude() + "," + mCurrentLocation.getLongitude()); // append lat long of current location to show nearby results.
        urlString.append("&sensor=true");
        urlString.append("&radius=5000");
//        urlString.append("&types=geocode");
        urlString.append("&language=en");
        urlString.append("&key=" + getString(R.string.mapKey));
        Log.d("Destination", "apiInter URL " + urlString.toString());
        Call<NearbyPlaceModel> call = apiInterface.getGoogleNearByPlaces(urlString.toString());
        call.enqueue(new Callback<NearbyPlaceModel>() {
            @Override
            public void onResponse(Call<NearbyPlaceModel> call, Response<NearbyPlaceModel> response) {
                Log.d("Destination", "apiInter response " + response.body().getResults().size());
                if (response.body().getResults().size() > 0) {
                    recycleView.setVisibility(View.VISIBLE);
                    listNearBy = new ArrayList<>();
                    listNearBy = response.body().getResults();
                    nearByPlaceAdapter = new NearByPlaceAdapter(SearchAddressActivity.this, listNearBy, mWidth, addressInterface);
                    recycleView.setAdapter(nearByPlaceAdapter);
                    nearByPlaceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NearbyPlaceModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onClick(int pos, InterConst.GOOGLE searchType) {
        utils.hideKeyboard(this);
        getLocationById(list.get(pos).getPlace_id(), pos);//autocomplete api
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

    }

    public void getLocationById(String placeId, final int position) {
        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places != null && places.getStatus().isSuccess()) {
                            Place myPlace = places.get(0);
                            LatLng queriedLocation = myPlace.getLatLng();
                            Location upLoc = new Location("newL");
                            upLoc.setLongitude(queriedLocation.longitude);
                            upLoc.setLatitude(queriedLocation.latitude);
                            Intent intent = new Intent(SearchAddressActivity.this, MapAddressActivity.class);
                            intent.putExtra(InterConst.LOCATION_DATA_EXTRA, upLoc);
                            intent.putExtra(InterConst.EXTRA, list.get(position).getDescription());
                            startActivityForResult(intent, ADDRESS_RESULT);
                            overridePendingTransition(R.anim.in, R.anim.out);

                        }
                        places.release();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADDRESS_RESULT:
                    Intent intent = new Intent();
                    intent.putExtra(InterConst.LOCATION_DATA_EXTRA, data.getParcelableExtra(InterConst.LOCATION_DATA_EXTRA));
                    intent.putExtra(InterConst.EXTRA, data.getStringExtra(InterConst.EXTRA));
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                    break;
            }
        }
    }
}
