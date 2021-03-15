package com.sudrives.sudrivespartner.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.LoginModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.ErrorLayout;
import com.sudrives.sudrivespartner.utils.Image_Picker;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import io.fabric.sdk.android.services.common.CommonUtils;


public class ContentUploadDocumentActivity extends Activity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    private LinearLayout llRegistrationDrivingLicense;
    private TextView tvRegistrationUploadDrivingL;
    private LinearLayout llRegistrationDrivingRC;
    private TextView tvRegistrationUploadRC;
    private LinearLayout llFitnessCertificate;
    private TextView tvFitnessCertificate;
    private LinearLayout llInsurance;
    private TextView tvInsurance;
    private LinearLayout llPermit;
    private TextView tvPermit;
    private String strVehiclelicensePath = "", strVehicleRCPath = "", strFitness = "", strInsurance = "", strPermit = "";
    private boolean isDtivingRC = false, isTakePic = true;
    private Intent intent;
    private static final String[] INITIAL_PERMS = {

            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };
    private boolean isFitness;
    private boolean isInsurance;
    private boolean isPermit;
    private boolean isProfile;
    private static final int INITIAL_REQUEST = 222;
    private boolean clickFlage = true;

    private Image_Picker image_picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_upload_document);
        intent = getIntent();
        image_picker = new Image_Picker(this);

        llRegistrationDrivingLicense = (LinearLayout) findViewById(R.id.ll_registration_drivingLicense);
        tvRegistrationUploadDrivingL = (TextView) findViewById(R.id.tv_registration_uploadDrivingL);
        llRegistrationDrivingRC = (LinearLayout) findViewById(R.id.ll_registration_drivingRC);
        tvRegistrationUploadRC = (TextView) findViewById(R.id.tv_registration_uploadRC);
        llFitnessCertificate = (LinearLayout) findViewById(R.id.ll_fitness_certificate);
        tvFitnessCertificate = (TextView) findViewById(R.id.tv_fitness_certificate);
        llInsurance = (LinearLayout) findViewById(R.id.ll_insurance);
        tvInsurance = (TextView) findViewById(R.id.tv_insurance);
        llPermit = (LinearLayout) findViewById(R.id.ll_permit);
        tvPermit = (TextView) findViewById(R.id.tv_permit);
        findViewById(R.id.but_save).setOnClickListener(this);
        findViewById(R.id.but_skip).setOnClickListener(this);

        llRegistrationDrivingLicense.setOnClickListener(this);
        tvRegistrationUploadDrivingL.setOnClickListener(this);

        llRegistrationDrivingRC.setOnClickListener(this);
        tvRegistrationUploadRC.setOnClickListener(this);

        llFitnessCertificate.setOnClickListener(this);
        tvFitnessCertificate.setOnClickListener(this);

        llInsurance.setOnClickListener(this);
        tvInsurance.setOnClickListener(this);

        llPermit.setOnClickListener(this);
        tvPermit.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        clickFlage = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_save:
                //TODO implement
                if (clickFlage) {

                    clickFlage = false;
                    checkValidation();

                }
                break;
            case R.id.but_skip:
                //TODO implement

                Intent intent = new Intent(this, HomeMenuActivity.class);
                startActivity(intent);
                finishAffinity();

                break;

            case R.id.ll_registration_drivingLicense:
            case R.id.tv_registration_uploadDrivingL:
                //TODO implement

                isDtivingRC = false;
                isTakePic = false;

                startCheckPermissions();

                break;

            case R.id.ll_registration_drivingRC:
            case R.id.tv_registration_uploadRC:
                //TODO implement

                CommonUtils.hideKeyboard(this, tvRegistrationUploadDrivingL);

                isDtivingRC = true;
                isTakePic = false;
                startCheckPermissions();
                break;
            case R.id.ll_fitness_certificate:
            case R.id.tv_fitness_certificate:
                CommonUtils.hideKeyboard(this, tvFitnessCertificate);

                isFitness = true;
                startCheckPermissions();
                break;
            case R.id.ll_insurance:
            case R.id.tv_insurance:
                CommonUtils.hideKeyboard(this, tvInsurance);

                isInsurance = true;
                startCheckPermissions();
                break;
            case R.id.ll_permit:
            case R.id.tv_permit:

                CommonUtils.hideKeyboard(this, tvPermit);

                isPermit = true;
                startCheckPermissions();
                break;
        }
    }


    void startCheckPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canAccessExternalStorage()) {
                ActivityCompat.requestPermissions(this, INITIAL_PERMS, INITIAL_REQUEST);
            } else {
                pickPhoto();
            }
        } else {
            pickPhoto();
        }
    }

    private boolean canAccessExternalStorage() {
        return (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case INITIAL_REQUEST: {

                Log.e("dsfdsf", "YYYYYY: " + grantResults.length);

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    pickPhoto();
            }
            break;

        }
    }


    private void pickPhoto() {

        image_picker.imageOptions();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        isTakePic = true;
        if (requestCode == Image_Picker.CAMERA_REQUEST) {
            Log.e("dfgfdgfdg", "ffffffff: " + resultCode + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);

            switch (resultCode) {
                case RESULT_OK:

                    //image_picker.cropImage(this, Uri.fromFile(Image_Picker.IMAGE_PATH), 0);

                    Uri selectedImageURI = Uri.fromFile(Image_Picker.IMAGE_PATH);
                    Log.e("selectedImageUri", selectedImageURI.toString());


                    if (isFitness) {
                        isFitness = false;

                        if (image_picker.checkURI(selectedImageURI)) {

//                            strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                            strFitness = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);
                        } else {

                            strFitness = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                        }

                        tvFitnessCertificate.setText(new File(strFitness).getName());
                        Log.e("File URI", "strFitness" + strFitness);
                        Log.e("File URI", "strFitness" + new File(strFitness).getName());
                        return;
                    }

                    if (isInsurance) {
                        isInsurance = false;

                        if (image_picker.checkURI(selectedImageURI)) {

//                            strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                            strInsurance = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);
                        } else {

                            strInsurance = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                        }

                        tvInsurance.setText(new File(strInsurance).getName());
                        Log.e("File URI", "strInsurance" + strInsurance);
                        Log.e("File URI", "strInsurance" + new File(strInsurance).getName());
                        return;
                    }

                    if (isPermit) {
                        isPermit = false;

                        if (image_picker.checkURI(selectedImageURI)) {

//                            strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                            strPermit = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);
                        } else {

                            strPermit = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                        }

                        tvPermit.setText(new File(strPermit).getName());
                        Log.e("File URI", "strPermit" + strPermit);
                        Log.e("File URI", "strPermit" + new File(strPermit).getName());
                        return;
                    }


                    if (isProfile) {
                        isProfile = false;

                        if (image_picker.checkURI(selectedImageURI)) {

//                            strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                            // strPermit = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);
                            image_picker.cropImage(ContentUploadDocumentActivity.this, selectedImageURI, 0);
                        } else {

                            //strPermit = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                            image_picker.cropImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI), 0);

                        }

//                        tvPermit.setText(new File(strPermit).getName());

                        return;
                    }


                    if (isDtivingRC) {


                        if (image_picker.checkURI(selectedImageURI)) {

//                            strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                            strVehicleRCPath = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);
                        } else {

                            strVehicleRCPath = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                        }

                        tvRegistrationUploadRC.setText(new File(strVehicleRCPath).getName());
                        Log.e("File URI", "profilePicfileUrifileUri" + strVehicleRCPath);
                        Log.e("File URI", "profilePicfileUrifileUri" + new File(strVehicleRCPath).getName());

                    } else {


                        if (image_picker.checkURI(selectedImageURI)) {

                            strVehiclelicensePath = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);
//                            strVehiclelicensePath = image_picker.getRealPathFromURI(selectedImageURI);
                        } else {

                            strVehiclelicensePath = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehiclelicensePath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                        }
                        Log.e("File URI", "profilePicfileUrifileUri" + strVehiclelicensePath);
                        Log.e("File URI", "profilePicfileUrifileUri" + new File(strVehiclelicensePath).getName());

                        tvRegistrationUploadDrivingL.setText(new File(strVehiclelicensePath).getName());
                        //   previewCapturedImage();

                    }
                    break;

            }
        } else if (requestCode == Image_Picker.GALLERY_REQUEST) {

            switch (resultCode) {
                case RESULT_OK:

                    Uri selectedImageURI = data.getData();
                    try {

                        if (isFitness) {


                            isFitness = false;


                            if (image_picker.checkURI(selectedImageURI)) {

                                //strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                                strFitness = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);

                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                strFitness = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                            }
                            tvFitnessCertificate.setText(new File(strFitness).getName());

                            Log.e("File URI", "strFitness" + strFitness);
                            Log.e("File URI", "strFitness" + new File(strFitness).getName());

                            return;


                        }

                        if (isInsurance) {

                            isInsurance = false;


                            if (image_picker.checkURI(selectedImageURI)) {

                                //strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                                strInsurance = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);

                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                strInsurance = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                            }
                            tvInsurance.setText(new File(strInsurance).getName());

                            Log.e("File URI", "strInsurance" + strInsurance);
                            Log.e("File URI", "strInsurance" + new File(strInsurance).getName());

                            return;


                        }

                        if (isPermit) {

                            isPermit = false;


                            if (image_picker.checkURI(selectedImageURI)) {

                                //strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                                strPermit = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);

                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                strPermit = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                            }
                            tvPermit.setText(new File(strPermit).getName());

                            Log.e("File URI", "strPermit" + strPermit);
                            Log.e("File URI", "strPermit" + new File(strPermit).getName());

                            return;


                        }

                        if (isDtivingRC) {


                            if (image_picker.checkURI(selectedImageURI)) {

                                //strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                                strVehicleRCPath = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);

                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                strVehicleRCPath = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                            }
                            tvRegistrationUploadRC.setText(new File(strVehicleRCPath).getName());

                            Log.e("File URI", "profilePicfileUrifileUri" + strVehicleRCPath);
                            Log.e("File URI", "profilePicfileUrifileUri" + new File(strVehicleRCPath).getName());


                        }


                        if (isProfile) {

                            isProfile = false;
                            if (image_picker.checkURI(selectedImageURI)) {

                                //strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                                //strVehicleRCPath = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);

                                image_picker.cropImage(ContentUploadDocumentActivity.this, selectedImageURI, 0);


                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                // strVehicleRCPath = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                image_picker.cropImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI), 0);

                            }


                        } else {


                            if (image_picker.checkURI(selectedImageURI)) {

                                strVehiclelicensePath = image_picker.resizeImage(ContentUploadDocumentActivity.this, selectedImageURI);
//                                    strVehiclelicensePath = image_picker.getRealPathFromURI(selectedImageURI);
                            } else {

                                strVehiclelicensePath = image_picker.resizeImage(ContentUploadDocumentActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                            }
                            Log.e("File URI", "profilePicfileUrifileUri" + strVehiclelicensePath);
                            Log.e("File URI", "profilePicfileUrifileUri" + new File(strVehiclelicensePath).getName());
                            tvRegistrationUploadDrivingL.setText(new File(strVehiclelicensePath).getName());


                            //   previewCapturedImage();

                        }


                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                    break;


            }
        } else {


            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void checkValidation() {

        ErrorLayout error = new ErrorLayout(this, findViewById(R.id.error_layout));

        if (strVehiclelicensePath.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_driving_license), ErrorLayout.MsgType.Error, true);
            clickFlage = true;
            return;
        }

        if (strVehicleRCPath.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_rc), ErrorLayout.MsgType.Error, true);
            clickFlage = true;
            return;
        }

        if (strFitness.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_fitness), ErrorLayout.MsgType.Error, true);
            clickFlage = true;
            return;
        }

        if (strInsurance.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_insurance), ErrorLayout.MsgType.Error, true);
            clickFlage = true;
            return;
        }

        if (strPermit.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_permit), ErrorLayout.MsgType.Error, true);
            clickFlage = true;
            return;
        }

        callRegistrationAPI();


    }

    private void callRegistrationAPI() {


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_TYPE, "registration");
            hashMap.put(AppConstants.KEY_USER_ID, intent.getStringExtra(AppConstants.KEY_USER_ID));

            hashMap.put(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH, strVehiclelicensePath);
            hashMap.put(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH, strVehicleRCPath);


            hashMap.put(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE, strFitness);
            hashMap.put(AppConstants.KEY_VALUE_INSURANCE, strInsurance);
            hashMap.put(AppConstants.KEY_VALUE_PERMIT, strPermit);

            hashMap.put(AppConstants.KEY_USER_ROLE, "2");


            Log.e("Send Registration", "Registration: " + AppConstants.API_REGISTRATION_THREE + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.API_REGISTRATION_THREE, hashMap, 1), this, AppConstants.EVENT_API_REGISTRATION_THREE);

            clickFlage = true;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onResponse(JSONObject response, String strEventType) {
        try {

            if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_REGISTRATION_THREE)) {


                LoginModel loginBeen = new Gson().fromJson(response.toString(), LoginModel.class);

                AppPreference.saveStringPref(this, AppPreference.KEY_USER_ID, loginBeen.getResult().getUser_id());
                AppPreference.saveStringPref(this, AppPreference.KEY_TOKEN, "" + loginBeen.getResult().getToken());

                AppPreference.saveStringPref(this, AppPreference.KEY_NAME, "" + loginBeen.getResult().getFirst_name() + " " + loginBeen.getResult().getLast_name());
                AppPreference.saveStringPref(this, AppPreference.KEY_MOBILE, "" + loginBeen.getResult().getMobile());
                AppPreference.saveStringPref(this, AppPreference.KEY_PROFILE_IMAGE, "" + loginBeen.getResult().getProfile_img());
                AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 1);
                if (AppPreference.loadStringPref(ContentUploadDocumentActivity.this, AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase("")) {
                    AppPreference.saveStringPref(this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);
                }


                Intent intent = new Intent(this, HomeMenuActivity.class);
                startActivity(intent);
                finishAffinity();


            }

        } catch (Exception e) {
            clickFlage = true;
            e.printStackTrace();
        }

    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }
    public void setLocale(String lang) {


        try {

            Locale myLocale = new Locale(lang);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            onConfigurationChanged(conf);

        } catch (Exception e) {

        }


        //  restartActivity();

    }
}
