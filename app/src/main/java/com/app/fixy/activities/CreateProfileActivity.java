package com.app.fixy.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.fixy.R;
import com.app.fixy.customviews.CircleTransform;
import com.app.fixy.utils.MarshMallowPermission;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CreateProfileActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 1;
    private static final int GALLERY = 2;
    private static final int CAMERA = 3;
    private static final int CAMERA_PERMISSION = 1;
    @BindView(R.id.rv_main)
    RelativeLayout rvMain;

    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ed_referral_code)
    EditText edReferralCode;
    @BindView(R.id.img_profile)
    ImageView imgProfile;

    MarshMallowPermission marshMallowPermission;
    GoogleSignInClient mGoogleSignInClient;

    public void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }


    private void takePhotoFromCamera() {
        if (marshMallowPermission.checkPermissionForCamera()){

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
        else {
            marshMallowPermission.requestPermissionForCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION:
            {
                if (grantResults.length == 2) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
//                        startCameraActivity();
                    }
                } else if (grantResults.length == 1) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permissions granted.
//                        startCameraActivity();
//                        startCameraActivity();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_create_profile;
    }

    @Override
    protected void onCreateStuff() {
        marshMallowPermission = new MarshMallowPermission(this);
        signInGmail();
    }
    private void signInGmail() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void initUI() {
        edName.setTypeface(typefaceMedium);
        edEmail.setTypeface(typefaceMedium);
        edReferralCode.setTypeface(typefaceMedium);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick(R.id.txt_done)
    void done() {
        signInGmail();
        /*Intent intent = new Intent(mContext, CongratulationActivity.class);
        finish();
        startActivity(intent);*/
    }

    @OnClick(R.id.ll_background)
    void pickImage() {
        if (mPermission.checkPermissionForExternalStorage()) {
            showPictureDialog();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    mPermission.snackBarStorage();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    @OnClick(R.id.img_referral)
    void imgReferral() {
        showCustomSnackBar(rvMain, getString(R.string.referral_code), getString(R.string.enter_referral_code_detail));
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case RC_SIGN_IN:

                    // The Task returned from this call is always completed, no need to attach
                    // a listener.
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);
                    break;
                case RESULT_CANCELED:

                    break;
                case GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        cropImage(selectedImage);

                    }
                    break;
                case CAMERA:
                    Uri selectedImage = data.getData();
                    cropImage(selectedImage);
                    break;
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = result.getUri();
                        showImage(resultUri);
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                    break;
            }


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.w(TAG, "signInResult: code=" + account);
            mGoogleSignInClient.signOut();

            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {

        edName.setText(account.getDisplayName());
        edEmail.setText(account.getEmail());

        if (account.getPhotoUrl() != null) {
            Picasso.get()
                    .load(account.getPhotoUrl().toString())
                    .transform(new CircleTransform())
                    .resize((int) (mHeight * 0.13), (int) (mHeight * 0.13))
                    .placeholder(R.mipmap.ic_profile)
                    .centerCrop()
                    .error(R.mipmap.ic_profile).into(imgProfile, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Log.w(TAG, "onError:failed code=" + e.getMessage());

                }
            });
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_profile)
                    .transform(new CircleTransform())
                    .resize((int) (mHeight * 0.13), (int) (mHeight * 0.13))
                    .into(imgProfile);
        }
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    void cropImage(Uri path) {
        CropImage.activity(path)
                .start(this);

    }

    void showImage(Uri path) {
        if (path != null) {
            Picasso.get()
                    .load(path)
                    .transform(new CircleTransform())
                    .resize((int) (mHeight * 0.13), (int) (mHeight * 0.13))
                    .placeholder(R.mipmap.ic_profile)
                    .centerCrop(Gravity.TOP)
                    .error(R.mipmap.ic_profile).into(imgProfile, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Log.w(TAG, "onError:failed code=" + e.getMessage());

                }
            });
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_profile)
                    .transform(new CircleTransform())
                    .resize((int) (mHeight * 0.13), (int) (mHeight * 0.13))
                    .into(imgProfile);
        }

    }

}
