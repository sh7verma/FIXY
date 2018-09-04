package com.app.fixy.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.fixy.R;
import com.app.fixy.customviews.CircleTransform;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.models.ProfileModel;
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
import retrofit2.Call;
import retrofit2.Response;

public class CreateProfileActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 1;
    private static final int GALLERY = 2;
    private static final int CAMERA = 3;
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

    String mPath;
    GoogleSignInClient mGoogleSignInClient;

    public void choosePhotoFromGallary() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_create_profile;
    }

    @Override
    protected void onCreateStuff() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn();
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
        hitApi();
    }

    @OnClick(R.id.ll_background)
    void pickImage() {
        if (mPermission.checkPermissionForExternalStorage() && mPermission.checkPermissionForCamera()) {
            showPictureDialog();
        } else {
            mPermission.requestCameraStoragePermission();
        }
    }

    @OnClick(R.id.img_referral)
    void imgReferral() {
        showCustomSnackBar(rvMain, getString(R.string.referral_code),
                getString(R.string.enter_referral_code_detail));
    }

    @Override
    public void onClick(View view) {

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri selectedImage = data.getData();
                cropImage(selectedImage);

            }
        } else if (requestCode == CAMERA) {
            Uri selectedImage = data.getData();
            cropImage(selectedImage);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                showImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Log.w(TAG, "signInResult: code=" + account);

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
                "Gallery",
                "Camera"};
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
            mPath = String.valueOf(path);

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

    void hitApi() {
        Call<ProfileModel> call = apiInterface.create_profile(createPartFromString(""),
                createPartFromString(edName.getText().toString()),
                createPartFromString(edEmail.getText().toString()),
                createPartFromString("m"),
                createPartFromString(edName.getText().toString()),
                prepareFilePart(mPath, "profile_image"));
        call.enqueue(new retrofit2.Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if (response.body().getResponse().getCode() == InterConst.SUCCESS_RESULT) {

                    Intent intent = new Intent(mContext, CongratulationActivity.class);
                    finish();
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {

            }
        });


    }

}
