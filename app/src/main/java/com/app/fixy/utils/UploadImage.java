//package com.app.fixy.utils;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//
//import com.theartofdev.edmodo.cropper.CropImage;
//
//import static android.support.v4.app.ActivityCompat.startActivityForResult;
//
///**
// * Created by Shubham verma on 04-09-2018.
// */
//
//public class UploadImage {
//
//    public UploadImage() {
//
//    }
//
//    private void showPictureDialog() {
//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
//        pictureDialog.setTitle("Select Action");
//        String[] pictureDialogItems = {
//                "Gallery",
//                "Camera"};
//        pictureDialog.setItems(pictureDialogItems,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                choosePhotoFromGallary();
//                                break;
//                            case 1:
//                                takePhotoFromCamera();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
//    }
//
//    void cropImage(Uri path) {
//        CropImage.activity(path)
//                .start(this);
//    }
//
//    public void choosePhotoFromGallary() {
//
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//        startActivityForResult(galleryIntent, GALLERY);
//
//    }
//
//    private void takePhotoFromCamera() {
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
//    }
//
//}
