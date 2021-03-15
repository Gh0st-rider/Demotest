package com.sudrives.sudrivespartner.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRatingBar;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.LoginModel;
import com.sudrives.sudrivespartner.models.ProfileModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.Image_Picker;
import com.sudrives.sudrivespartner.utils.Validations;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.sudrives.sudrivespartner.utils.AppPreference.KEY_USER_ID;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    private TextView tvTrip;
    private CircleImageView ivProfile;
    private AppCompatRatingBar ratingbarProfile;
    private TextView tvYear;
    private TextView etMobileNumber;
    private TextView etVehicleType;
    private TextView tvVehicleNumber;
    private ImageButton editButton;

    private TextView tv_donation_Amount;


    private Image_Picker image_picker;
    private String imagePath = "";
    private ImageButton ib_profile;
    private Button submit;
    private CheckBox cb_driver_as_a_daily, cb_driver_as_a_rental, cb_driver_as_a_outstation;

    //changes for state name
    private TextView tv_state_name,unique_id;
    private String city, latitude, longitude, state;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        cb_driver_as_a_daily = findViewById(R.id.cb_driver_as_a_daily);
        cb_driver_as_a_rental = findViewById(R.id.cb_driver_as_a_rental);
        cb_driver_as_a_outstation = findViewById(R.id.cb_driver_as_a_outstation);

        unique_id = findViewById(R.id.unique_id);
        image_picker = new Image_Picker(this);
        tvTrip = findViewById(R.id.tv_trip);
        ib_profile = findViewById(R.id.ib_profile);
        ivProfile = findViewById(R.id.iv_profile);
        ratingbarProfile = findViewById(R.id.ratingbar_profile);
        tvYear = findViewById(R.id.tv_year);
        etMobileNumber = findViewById(R.id.et_mobile_number);
        etVehicleType = findViewById(R.id.et_vehicle_type);
        tvVehicleNumber = findViewById(R.id.tv_vehicle_number);
        submit = findViewById(R.id.btn_profile_submit);
        tv_donation_Amount = findViewById(R.id.tv_donation_Amount);
        tv_state_name = findViewById(R.id.tv_state_name);
        submit.setOnClickListener(this);
        unique_id.setText(AppPreference.loadStringPref(this,AppPreference.KEY_UNIQUE_NUMBER));
        ib_profile.setVisibility(View.GONE);

        findViewById(R.id.ib_back).setOnClickListener(this);

        editButton = findViewById(R.id.ib_edit_icon);

        editButton.setOnClickListener(this);

        ivProfile.setOnClickListener(this);
        ib_profile.setOnClickListener(this);

        //
        tv_state_name.setEnabled(false);
        tv_state_name.setOnClickListener(this);


        try {
            getProfileDetail();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        makeFieldUnEditable();
    }

    private EditText getEtName() {
        return (EditText) findViewById(R.id.et_name);
    }

    private EditText getEtEmailAddress() {
        return (EditText) findViewById(R.id.et_email_address);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_profile_submit:
                //TODO implement

                if (checkEmpty()) {
                    updateProfile();
                }
                break;
            case R.id.iv_profile:
            case R.id.ib_profile:
                pickPhoto();
                break;

            case R.id.ib_edit_icon:
                makeFieldEdittable();
                break;

            case R.id.ib_back:
                onBackPressed();
                break;

            case R.id.tv_state_name:
                Intent i = new Intent(this, PlacePickerActivity.class);
                startActivityForResult(i, 100);
                break;
        }
    }

    private void getProfileDetail() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.DRIVE_USER_ID, AppPreference.loadStringPref(this, KEY_USER_ID));

        NetworkConn networkConn = NetworkConn.getInstance(this);

        Log.e("Send Response", "Send Response: " + AppConstants.API_MOBLINE_NUMBER_CHECK + " :: " + jsonObject);

        networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.PROFILE_DETAIl, jsonObject.toString()), this, AppConstants.EVENT_PROFILE_DETAIL);

    }


    @Override
    public void onResume() {
        super.onResume();
        AppConstants.KEY_CONTEXT = ProfileActivity.this;
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        try {


            if (strEventType.equals(AppConstants.EVENT_PROFILE_DETAIL)) {

                Log.d("DriverProfile", response.toString());

                ProfileModel loginModel = new Gson().fromJson(response.toString(), ProfileModel.class);
                setProfile(loginModel);

            } else if (strEventType.equals(AppConstants.EVENT_PROFILE_UPDATE)) {

                Log.d("Updated Profile", response.toString());

                LoginModel loginBeen = new Gson().fromJson(response.toString(), LoginModel.class);

                AppPreference.saveStringPref(this, AppPreference.KEY_USER_ID, loginBeen.getResult().getUser_id());
                AppPreference.saveStringPref(this, AppPreference.KEY_TOKEN, "" + loginBeen.getResult().getToken());

                AppPreference.saveStringPref(this, AppPreference.KEY_NAME, "" + loginBeen.getResult().getFirst_name() + " " + loginBeen.getResult().getLast_name());
                AppPreference.saveStringPref(this, AppPreference.KEY_MOBILE, "" + loginBeen.getResult().getMobile());
                AppPreference.saveStringPref(this, AppPreference.KEY_PROFILE_IMAGE, "" + loginBeen.getResult().getProfile_img());


                Toast.makeText(this, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                makeFieldUnEditable();
                HomeMenuActivity.mOnClickRequestes.onSuccessfullOnclickRequest(AppConstants.KEY_VALUE_PROFILE_UPDATE);

            }
        } catch (Exception e) {

        }

    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }

    private void setProfile(ProfileModel profile) {

        tvVehicleNumber.setText(profile.getResult().getVehicle_number());
        tvTrip.setText(profile.getResult().getTrips() + getString(R.string._trip));
        tvYear.setText(profile.getResult().getYears() + getString(R.string._years));
        getEtName().setText(profile.getResult().getFirst_name());
        getEtEmailAddress().setText(profile.getResult().getEmail());
        etMobileNumber.setText(profile.getResult().getMobile());

        String donation = profile.getResult().getUser_donations();

        if (!TextUtils.isEmpty(donation)) {
            tv_donation_Amount.setText(getString(R.string.donation_Amount).concat(" : " + getString(R.string.rupee).concat(" ").concat(donation)));
        }

        etVehicleType.setText(profile.getResult().getDriver_vehicle());

        String imageURL = profile.getResult().getProfile_img();

        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(this).load(imageURL).into(ivProfile);
        }

        float rating = (float) profile.getResult().getRating();

        ratingbarProfile.setRating(rating);

        if (profile.getResult().getDaily().trim().equals("1")) {
            cb_driver_as_a_daily.setChecked(true);
        }

        if (profile.getResult().getRental().trim().equals("1")) {
            cb_driver_as_a_rental.setChecked(true);
        }

        if (profile.getResult().getOutstation().trim().equals("1")) {
            cb_driver_as_a_outstation.setChecked(true);
        }

        //changes for location name
        if (profile.getResult().getHome_address() != null) {
            tv_state_name.setText("" + profile.getResult().getHome_address());
        } else {

            tv_state_name.setHint(R.string.state_name);
        }

        latitude = "" + profile.getResult().getHome_latitude();
        longitude = "" + profile.getResult().getHome_longitude();
        state = "" + profile.getResult().getHome_state();
        city = "" + profile.getResult().getHome_city();


    }

    private void pickPhoto() {

        image_picker.imageOptions();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("dfgfdgfdg", "ffffffff: " + resultCode + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String hometown = data.getStringExtra("hometown");
            city = data.getStringExtra("city");
            state = data.getStringExtra("state");
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
            tv_state_name.setText("" + hometown);
        } else if (requestCode == Image_Picker.CAMERA_REQUEST) {
            Log.e("dfgfdgfdg", "ffffffff: " + resultCode + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);

            switch (resultCode) {
                case RESULT_OK:

                    image_picker.cropImage(this, Uri.fromFile(Image_Picker.IMAGE_PATH), 0);

                    break;
                case RESULT_CANCELED:

                    break;
            }
        } else {

            if (requestCode == Image_Picker.GALLERY_REQUEST) {

                switch (resultCode) {
                    case RESULT_OK:

                        Uri selectedImageURI = data.getData();
                        try {

                            Log.e("dfgfdgfdg", "ffffffff: " + image_picker.checkURI(selectedImageURI) + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);

                            if (image_picker.checkURI(selectedImageURI)) {
                                image_picker.cropImage(this, selectedImageURI, 0);
                            } else {
                                image_picker.cropImage(this, image_picker.getImageUrlWithAuthority(this, selectedImageURI), 0);
                            }
                        } catch (Exception ex) {

                            ex.printStackTrace();
                        }
                        break;

                    case RESULT_CANCELED:
                        //         Log.e("Get Data", ""+data.getData());

                        break;
                }
            } else {
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = result.getUri();

                        imagePath = image_picker.getRealPathFromURI(resultUri);


                        //   previewCapturedImage();


                        // user_image.setImageBitmap(myBitmap);
                        Glide.with(this)
                                .load(resultUri)
                                .into(ivProfile);


                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                        Exception error = result.getError();
                        Log.e("error", "profilePicError :::: " + error.getMessage());
                        error.printStackTrace();
                    }
                } else {

                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    private void updateProfile() {
        try {

            String strDaily = "0", strRental = "0", strOutstation = "0";


            if (cb_driver_as_a_daily.isChecked()) {
                strDaily = "1";
            }

            if (cb_driver_as_a_rental.isChecked()) {
                strRental = "1";
            }

            if (cb_driver_as_a_outstation.isChecked()) {
                strOutstation = "1";
            }


            NetworkConn networkConn = NetworkConn.getInstance(this);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(this, AppPreference.KEY_USER_ID));
            hashMap.put(AppConstants.KEY_VALUE_FIRST_NAME, getEtName().getText().toString().trim());
            hashMap.put(AppConstants.EMAIL, getEtEmailAddress().getText().toString().trim());
            hashMap.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(this, AppPreference.KEY_TOKEN));

            hashMap.put(AppConstants.KEY_VALUE_DAILY, strDaily);
            hashMap.put(AppConstants.KEY_VALUE_RENTAL, strRental);
            hashMap.put(AppConstants.KEY_VALUE_OUTSTATION, strOutstation);
            hashMap.put(AppConstants.KEY_latitude, latitude);
            hashMap.put(AppConstants.KEY_VALUE_longitude, longitude);
            hashMap.put(AppConstants.KEY_VALUE_city, city);
            hashMap.put(AppConstants.KEY_VALUE_state, state);
            hashMap.put(AppConstants.KEY_VALUE_home_address, tv_state_name.getText().toString());


            int fileType = 0;
            if (!TextUtils.isEmpty(imagePath))
                fileType = 1;
            else
                fileType = 0;
            hashMap.put(AppConstants.PROFILE_IMAGE, imagePath);


            Log.d("Send Registration", "Registration: " + AppConstants.PROFILE_UPDATE + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.PROFILE_UPDATE, hashMap, fileType), this, AppConstants.EVENT_PROFILE_UPDATE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkEmpty() {

        String email = getEtEmailAddress().getText().toString().trim();
        String name = getEtName().getText().toString().trim();

        if (!TextUtils.isEmpty(email)) {
            if (!Validations.getEmailValid(email)) {
                getEtEmailAddress().requestFocus();
                Toast.makeText(this, R.string.enter_valid_email, Toast.LENGTH_SHORT).show();
                return false;
            }

        }


        if (TextUtils.isEmpty(name)) {
            getEtName().requestFocus();
            Toast.makeText(this, R.string.please_fill_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cb_driver_as_a_daily.isChecked() && !cb_driver_as_a_rental.isChecked() && !cb_driver_as_a_outstation.isChecked()) {
            Toast.makeText(this, R.string.please_select_at_least_one_service, Toast.LENGTH_SHORT).show();
            return false;

        }


        return true;
    }

    private void makeFieldEdittable() {

        editButton.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);

        ib_profile.setVisibility(View.VISIBLE);


        ib_profile.setClickable(true);
        ivProfile.setClickable(true);
        tv_state_name.setEnabled(true);

        getEtEmailAddress().setFocusable(true);
        getEtEmailAddress().setFocusableInTouchMode(true);


        getEtName().setFocusable(true);
        getEtName().setFocusableInTouchMode(true);


        cb_driver_as_a_daily.setEnabled(true);
//        cb_driver_as_a_daily.setFocusableInTouchMode(true);

        cb_driver_as_a_rental.setEnabled(true);
//        cb_driver_as_a_rental.setFocusableInTouchMode(true);

        cb_driver_as_a_outstation.setEnabled(true);
//        cb_driver_as_a_outstation.setFocusableInTouchMode(true);


        getEtEmailAddress().requestFocus();
    }

    private void makeFieldUnEditable() {

        editButton.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);
        ib_profile.setVisibility(View.GONE);

        Log.d("Hide", "hide");

        ib_profile.setClickable(false);
        ivProfile.setClickable(false);
        tv_state_name.setEnabled(false);


        getEtEmailAddress().setFocusable(false);
        getEtEmailAddress().setFocusableInTouchMode(false);


        getEtName().setFocusable(false);
        getEtName().setFocusableInTouchMode(false);

        cb_driver_as_a_daily.setEnabled(false);
//        cb_driver_as_a_daily.setFocusableInTouchMode(false);

        cb_driver_as_a_rental.setEnabled(false);
//        cb_driver_as_a_rental.setFocusableInTouchMode(false);

        cb_driver_as_a_outstation.setEnabled(false);
//        cb_driver_as_a_outstation.setFocusableInTouchMode(false);


        editButton.requestFocus();
    }


}
