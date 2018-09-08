package com.app.fixy.dialogs;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.app.fixy.BuildConfig;
import com.app.fixy.R;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.utils.Utils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by app on 9/19/2016.
 */
public class PhotoSelectionDialog extends Activity {

    final int CAMERA_INTENT = 4;
    final int GALLERY_INTENT = 5;
    private long mSystemTime;
    String type;
    Utils utils;

    private static final int MULTIPLE_PERMISSIONS = 3;
    private static final int GALLERY_PERMISSION = 12;
    int mScreenwidth, mScreenheight;
    final int WRITE_EXTERNAL_ID = 1;
    final int READ_EXTERNAL_ID = 2;
    String[] permissions = new String[]{
            WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    @BindView(R.id.ll_gallery)
    LinearLayout llGallery;
    @BindView(R.id.ll_view)
    LinearLayout llView;
    @BindView(R.id.ll_camera)
    LinearLayout llCamera;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = this.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        setContentView(R.layout.alert_dialog_photo_selection);
        getDefaults();
        getWindow().setLayout((int)(mScreenwidth), ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);
        initUI();
    }

    void getDefaults() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenwidth = dm.widthPixels;
        mScreenheight = dm.heightPixels;
        utils = new Utils(PhotoSelectionDialog.this);
        type = getIntent().getStringExtra(InterConst.TYPE);
    }

    void initUI() {
        if (type.equals("1")) {
            llView.setVisibility(View.GONE);
            llDelete.setVisibility(View.GONE);
        } else {
            llView.setVisibility(View.VISIBLE);
            llDelete.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.ll_delete)
    void onDelete() {
        Intent in = new Intent();
        in.putExtra(InterConst.RESULT_DATA_KEY, InterConst.NULL);
        setResult(RESULT_OK, in);
        finish();
    }

    @OnClick(R.id.ll_view)
    void onViewPhoto() {
        Intent in = new Intent();
        in.putExtra(InterConst.RESULT_DATA_KEY, InterConst.SHOW_PIC);
        setResult(RESULT_OK, in);
        finish();
    }

    @OnClick(R.id.ll_camera)
    void onCamera() {
//        if ((android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP)) {
//            MarshMallowPermission marshMallowPermission = new MarshMallowPermission(PhotoSelectionDialog.this);
//            if (marshMallowPermission.checkPermissionForExternalStorage()) {
//                startCameraActivity();
//            } else {
//                marshMallowPermission.requestPermissionForExternalStorage();
//            }
//        } else {
//            startCameraActivity();
//        }
        if (checkPermissions()) {
            startCameraActivity();
        }
    }

    @OnClick(R.id.ll_gallery)
    void onGallery() {
        if (ContextCompat.checkSelfPermission(PhotoSelectionDialog.this, READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(PhotoSelectionDialog.this, new String[]{READ_EXTERNAL_STORAGE}, READ_EXTERNAL_ID);
        } else {
            showGallery();
        }
    }



    protected void startCameraActivity() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_INTENT);
        /*Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mSystemTime = System.currentTimeMillis();
        File f = new File(Environment.getExternalStorageDirectory(), "OryxHub" + mSystemTime + ".png");
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            Uri photoURI = FileProvider.getUriForFile(PhotoSelectionDialog.this,
                    BuildConfig.APPLICATION_ID + ".provider", f);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        }
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivityForResult(cameraIntent, CAMERA_INTENT);*/
    }

    void showGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, GALLERY_INTENT);

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CAMERA_INTENT:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    cropImage(selectedImage);
                   /* File dir = Environment.getExternalStorageDirectory();
                    File f = new File(dir, "OryxHub" + mSystemTime + ".png");
                    Log.e("camera photo", "is " + f.getAbsolutePath());
                    Uri rr = Uri.fromFile(new File(f.getAbsolutePath()));
                    beginCrop(rr);*/
                }
                break;
            case GALLERY_INTENT:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    cropImage(selectedImage);

                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    File file = new File(resultUri.getPath());
                    Intent in = new Intent();
                    in.putExtra(InterConst.RESULT_DATA_KEY, getRealPathFromURI(getImageUri(PhotoSelectionDialog.this, getBitmap(file.getPath()))));
                    setResult(RESULT_OK, in);
                    finish();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void cropImage(Uri path) {
        CropImage.activity(path)
                .start(this);

    }

    private void handleCrop(Intent result) {
        try {
            /*Uri ur = Crop.getOutput(result);
            String picturePath = FileUtils.getPath(PhotoSelectionDialog.this, ur);
            Intent in = new Intent();
            in.putExtra("filePath", getRealPathFromURI(getImageUri(PhotoSelectionDialog.this, getBitmap(picturePath))));
            setResult(RESULT_OK, in);
            finish();*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(PhotoSelectionDialog.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Bitmap getBitmap(String path) {
        int rotation = getImageOrientation(path);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap sourceBitmap = BitmapFactory.decodeFile(path, options);

        Bitmap imgBm = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix,
                true);

        return imgBm;
    }

    public int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(PhotoSelectionDialog.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GALLERY_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openFacebookGallery();
                }
                break;
            case WRITE_EXTERNAL_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    startCameraActivity();
                break;
            case READ_EXTERNAL_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    showGallery();
                break;
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length == 2) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
                        startCameraActivity();
                    }
                } else if (grantResults.length == 1) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
                        startCameraActivity();
                    }
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    public void copy(String urls, long dst) throws IOException {
//        URL url = new URL(urls);
//        InputStream input = url.openStream();
//        try {
//            File storagePath = Environment.getExternalStorageDirectory();
//            OutputStream output = new FileOutputStream(storagePath + "/" + dst + ".png");
//            try {
//                byte[] buffer = new byte[1024];
//                int bytesRead = 0;
//                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
//                    output.write(buffer, 0, bytesRead);
//                }
//            } finally {
//                output.close();
//            }
//        } finally {
//            input.close();
//        }
//
//    }
//
//    public class DownloadFileFromURL extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... f_url) {
//            int count;
//            String path = null;
//            try {
//                URL url = new URL(f_url[0]);
//                URLConnection conection = url.openConnection();
//                conection.connect();
//                int lenghtOfFile = conection.getContentLength();
//                String pathRoot = generateNoMedia();
//                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//                File storagePath = Environment.getExternalStorageDirectory();
//                path = pathRoot + "/" + System.currentTimeMillis() + ".png";
//                OutputStream output = new FileOutputStream(path);
//                byte data[] = new byte[1024];
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
//                    output.write(data, 0, count);
//                }
//
//                output.flush();
//                output.close();
//                input.close();
//
//            } catch (Exception e) {
//                Log.e("Error: ", e.getMessage());
//            }
//
//            return path;
//        }
//
//        /**
//         * Updating progress bar
//         */
//        protected void onProgressUpdate(String... progress) {
//            Log.e("ima", "post progress " + progress);
//        }
//
//        /**
//         * After completing background task
//         * Dismiss the progress dialog
//         **/
//        @Override
//        protected void onPostExecute(String file_url) {
//            File file = new File(file_url);
//            Uri uri = Uri.fromFile(file);
//            Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
//            Crop.of(uri, destination).asSquare().start(PhotoSelectionDialog.this);
//            Log.e("ima", "post " + file_url);
//        }
//
//    }
//
//    public String generateNoMedia() {
//        String root = Environment.getExternalStorageDirectory()
//                .toString();
//        File myDir = new File(root + "/Oryxre/fbGallery");
//        if (!myDir.exists())
//            myDir.mkdirs();
//        String fileName = ".nomedia";
//        File ff = new File(myDir, fileName);
//        if (!ff.exists()) {
//            try {
//                ff.createNewFile();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return myDir.getAbsolutePath();
//    }
}