package com.app.fixy.helper;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;


import com.app.fixy.R;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.utils.Utils;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MarshMallowPermission {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 3;
    public static final int CALL_PERMISSION_REQUEST_CODE = 4;
    public static final int CONTACT_PERMISSION_REQUEST_CODE = 5;
    public static final int READ_PHONE_STATE_PERMISSION_REQUEST_CODE = 6;

    Activity activity;
    Utils util;
    private int isFirstTime=0;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
        util = new Utils(activity);
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener) {
        Snackbar.make(activity.findViewById(android.R.id.content), activity.getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE).setActionTextColor(ContextCompat.getColor(activity, R.color.red)).setAction(activity.getString(actionStringId), listener).show();
    }

    public boolean checkPermissionForCamera() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForCamera() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
//            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean checkPermissionForExternalStorage() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermissionForAudio() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Toast.makeText(activity, "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean checkPermissionForLocation() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPermissionForLocation() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (isFirstTime == 0) {
                isFirstTime = 1;
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);


            } else if (isFirstTime == 1) {
                snackBarLocation();
            }


        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showSnackbar(R.string.location_permission,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    LOCATION_PERMISSION_REQUEST_CODE);
                        }
                    });

        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void snackBarLocation() {

        Snackbar.make(activity.findViewById(android.R.id.content), R.string.enable_location_setting,
                Snackbar.LENGTH_INDEFINITE).setAction(R.string.enable,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        activity.startActivity(intent);
                    }
                })
                .setActionTextColor(ContextCompat.getColor(activity, R.color.red)).show();


    }
    public boolean checkPermissionForCall() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
            Toast.makeText(activity, "Call permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean requestContactPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
//            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE},
                    CONTACT_PERMISSION_REQUEST_CODE);
            return false;
        }
       else if (ContextCompat.checkSelfPermission(activity, READ_CONTACTS) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(activity, new String[]{READ_CONTACTS,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, CONTACT_PERMISSION_REQUEST_CODE);
            return false;
        } else {
            return true;
         }
    }

    public boolean checkPermissionForPhoneState() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
            util.setBoolean(InterConst.READ_PHONE_STATE_PERMISSION, false);
            showSnackbar(R.string.read_phone_state_permission,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
// Request permission
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE},
                                    READ_PHONE_STATE_PERMISSION_REQUEST_CODE);
                        }
                    });
        } else {
            if (!util.getBoolean(InterConst.READ_PHONE_STATE_PERMISSION, false))
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_PERMISSION_REQUEST_CODE);
            if (util.getBoolean(InterConst.READ_PHONE_STATE_PERMISSION, false)) {
                showSnackbar(R.string.read_phone_state_permission,
                        android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
// Request permission
                                util.setBoolean(InterConst.READ_PHONE_STATE_PERMISSION, false);
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                activity.startActivity(intent);
                            }
                        });
            }
            util.setBoolean(InterConst.READ_PHONE_STATE_PERMISSION, true);
        }
    }
}
