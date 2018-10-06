package com.app.fixy.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


import com.app.fixy.interfaces.InterConst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by Gary Hallberg on 23/09/2017.
 */

public class FetchAddressIntentService extends IntentService {

    private static final int SUCCESS_RESULT = 200;
    private static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";
    protected ResultReceiver mReceiver;

    public FetchAddressIntentService() {
        super("name");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String errorMessage = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //get the location passed to this service through extras

        Location location = intent.getParcelableExtra(InterConst.LOCATION_DATA_EXTRA);
        mReceiver = intent.getParcelableExtra(InterConst.RECEIVER);

        List<Address> addresses = null;

        try {
            if (location == null || location.getLongitude() == 0) {
                return;
            }
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            errorMessage = "Service not available";
            Log.e(TAG, errorMessage, e);
            e.printStackTrace();
        } catch (IllegalArgumentException illegalArgumentException) {
            //catch invalid latitude and longitude
            errorMessage = "Invalid latitude and longitude used";
            Log.e(TAG, errorMessage + ". " + "Latitude = " + location.getLatitude() + ", Longitude = " + location.getLongitude());
        }

        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found";
                Log.e(TAG, errorMessage);
            }
            deliverResultsToReceiver(FAILURE_RESULT, errorMessage, null);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            //Fetch the address lines using getAddressLine
            //join them and send them to the GallleryActivity

            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));

            }
            Log.i(TAG, "Address Found");
            deliverResultsToReceiver(SUCCESS_RESULT, TextUtils.join(System.getProperty("line.separator"), addressFragments),address);
        }


    }

    private void deliverResultsToReceiver(int failureResult, String message, Address address) {

        Bundle bundle = new Bundle();
        bundle.putString(InterConst.RESULT_DATA_KEY, message);
        if (address!=null) {
            bundle.putString(InterConst.LOCATION_DATA_EXTRA, address.getPostalCode());
        }
        mReceiver.send(failureResult, bundle);
    }

}
