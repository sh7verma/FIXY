package com.app.fixy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.app.fixy.R;
import com.app.fixy.customviews.CircleTransform;
import com.app.fixy.customviews.MaterialEditText;
import com.app.fixy.dialogs.PhotoSelectionDialog;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.models.LoginModel;
import com.app.fixy.network.RetrofitClient;
import com.app.fixy.utils.Consts;
import com.app.fixy.utils.Validations;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class CreateProfileActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 1;
    private static final int GALLERY = 2;
    private static final int CAMERA = 3;
    private static final int CAMERA_PERMISSION = 1;
    private static final int PIC = 12;
    @BindView(R.id.rv_main)
    RelativeLayout rvMain;

    @BindView(R.id.ed_name)
    MaterialEditText edName;
    @BindView(R.id.ll_no_photo)
    LinearLayout llNoPhoto;
    @BindView(R.id.ed_email)
    MaterialEditText edEmail;
    @BindView(R.id.ed_referral_code)
    MaterialEditText edReferralCode;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.rd_group)
    RadioGroup rdGroup;

    GoogleSignInClient mGoogleSignInClient;
    private File pathImageFile = null;
    private String imagePath = "";
    private String gender;


    @Override
    protected int getContentView() {
        return R.layout.activity_create_profile;
    }

    @Override
    protected void onCreateStuff() {
        signInGmail();
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rd_male:
                        gender = InterConst.MALE;
                        break;
                    case R.id.rd_female:
                        gender = InterConst.FEMALE;
                        break;
                }
            }
        });
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
        Consts.hideKeyboard(this);
        if (TextUtils.isEmpty(imagePath)) {

            showAlert(llNoPhoto, getString(R.string.profile_pic_validation));
        } else if (TextUtils.isEmpty(gender)) {

            showAlert(llNoPhoto, getString(R.string.gender_validation));
        } else if (Validations.checkNameValidation(this, edName)
                && Validations.checkEmailValidation(this, edEmail)) {
            hitapi();
        }
    }

    @OnClick(R.id.img_profile)
    void pickImageProfile() {

        Intent inProfileno = new Intent(this, PhotoSelectionDialog.class);
        inProfileno.putExtra(InterConst.TYPE, "2");// for add photo
        startActivityForResult(inProfileno, PIC);
    }

    @OnClick(R.id.ll_no_photo)
    void pickImage() {

        Intent inProfileno = new Intent(this, PhotoSelectionDialog.class);
        inProfileno.putExtra(InterConst.TYPE, "1");// for add photo
        startActivityForResult(inProfileno, PIC);

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

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PIC:
                    if (connectedToInternet()) {
                        if (data.getStringExtra(InterConst.RESULT_DATA_KEY).equalsIgnoreCase(InterConst.NULL)) {
                            hideProfilePic();//remove pic
                            pathImageFile = null;
                            utils.setString(InterConst.PROFILE_IMAGE, "");
                        } else if (data.getStringExtra(InterConst.RESULT_DATA_KEY).equalsIgnoreCase(InterConst.SHOW_PIC)) {
                            String picValue = "";
                            if (pathImageFile != null) {
                                picValue = imagePath;
                            } else {
                                picValue = utils.getString(InterConst.PROFILE_IMAGE, "");
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ActivityOptionsCompat option = ActivityOptionsCompat
                                        .makeSceneTransitionAnimation(CreateProfileActivity.this, imgProfile, "full_imageview");
                                Intent in = new Intent(CreateProfileActivity.this, ViewImageActivity.class);
                                in.putExtra("display", "" + picValue);
                                startActivity(in, option.toBundle());
                            } else {
                                Intent in = new Intent(CreateProfileActivity.this, ViewImageActivity.class);
                                in.putExtra("display", "" + picValue);
                                startActivity(in);
                                overridePendingTransition(0, 0);
                            }
                        } else {
                            imagePath = data.getStringExtra(InterConst.RESULT_DATA_KEY);
                            pathImageFile = new File(imagePath);
                            Log.e("IMage Path = ", data.getStringExtra(InterConst.RESULT_DATA_KEY));
                            showImage(pathImageFile);
                            showProfilePic();// on result
                        }
                    } else
                        showInternetAlert(llNoPhoto);
                    break;
                case RC_SIGN_IN:

                    // The Task returned from this call is always completed, no need to attach
                    // a listener.
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);
                    break;
                case RESULT_CANCELED:

                    break;

            }
        }

    }

    private void hideProfilePic() {
        llNoPhoto.setVisibility(View.VISIBLE);
        imgProfile.setVisibility(View.GONE);
    }

    private void showProfilePic() {
        llNoPhoto.setVisibility(View.GONE);
        imgProfile.setVisibility(View.VISIBLE);
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

    void showImage(File file) {
        if (file != null) {
            Picasso.get()
                    .load(file)
                    .transform(new CircleTransform())
                    .resize((int) (mHeight * 0.13), (int) (mHeight * 0.13))
                    .placeholder(R.mipmap.ic_profile)
                    .centerCrop(Gravity.TOP)
                    .error(R.mipmap.ic_profile).into(imgProfile, new Callback() {
                @Override
                public void onSuccess() {
                    Log.w(TAG, "onError:Sucess code=");
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

    private void signInGmail() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void hitapi() {

        Call<LoginModel> call;
        if (pathImageFile != null) {// if remove profile pic that coming from social media
            MultipartBody.Part imagePart = prepareFilePart(String.valueOf(pathImageFile),"profile_image");
            call = RetrofitClient.getInstance().create_profile(
                    createPartFromString(utils.getString(InterConst.ACCESS_TOKEN, "")),
                    createPartFromString(edName.getText().toString()),
                    createPartFromString(edEmail.getText().toString()),
                    createPartFromString(gender),
                    createPartFromString(InterConst.CREATE_PROFILE),
                    createPartFromString(edReferralCode.getText().toString()),
                    imagePart);
        } else {// to send image from social media

               /* call = RetrofitClient.getInstance().updateProfile(utils.getString(Consts.ACCESS_TOKEN, ""),
                        edFullName.getText().toString().trim(),
                        Consts.sendDate(edBday.getText().toString().trim()),
                        edInvitaionCode.getText().toString().trim(),
                        socialMediaImg);*/

            MultipartBody.Part imagePart = prepareFilePart("","profile_image");
            call = RetrofitClient.getInstance().create_profile(
                    createPartFromString(utils.getString(InterConst.ACCESS_TOKEN, "")),
                    createPartFromString(edName.getText().toString()),
                    createPartFromString(edEmail.getText().toString()),
                    createPartFromString(gender),
                    createPartFromString(InterConst.CREATE_PROFILE),
                    createPartFromString(edReferralCode.getText().toString()),
                    createPartFromString(utils.getString(InterConst.PROFILE_IMAGE, "")));
        }
        call.enqueue(new retrofit2.Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if ((response.body().getCode() == InterConst.SUCCESS_RESULT)) {
//                    Intent intent = new Intent(CreateProfileActivity.this, CreateProfileActivity.class);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                    if (pathImageFile != null) {
                        pathImageFile.deleteOnExit();
                    }
                } else if (response.body().getCode() == InterConst.ERROR_RESULT) {
                    showAlert(edEmail, response.body().getError().getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }



}
