package com.sudrives.sudrivespartner.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatRatingBar;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.HomeMenuActivity;
import com.sudrives.sudrivespartner.models.LoginModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.Image_Picker;
import com.sudrives.sudrivespartner.utils.Validations;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.sudrives.sudrivespartner.utils.AppPreference.KEY_USER_ID;

public class FramentProfileFragment extends Fragment implements View.OnClickListener, NetworkConn.OnRequestResponse {

    private TextView tvTrip;
    private CircleImageView ivProfile;
    private AppCompatRatingBar ratingbarProfile;
    private TextView tvYear;
    private TextView etMobileNumber;
    private TextView etVehicleType;
    private TextView tvVehicleNumber;
    private ImageButton editButton;

    private Context mContext;

    private Image_Picker image_picker;
    private String imagePath = "";
    private ImageButton ib_profile;
    private Button submit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.frament_profile, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image_picker = new Image_Picker(mContext);




        tvTrip = view.findViewById(R.id.tv_trip);
        ib_profile = view.findViewById(R.id.ib_profile);
        ivProfile = view.findViewById(R.id.iv_profile);
        ratingbarProfile = view.findViewById(R.id.ratingbar_profile);
        tvYear = view.findViewById(R.id.tv_year);
        etMobileNumber = view.findViewById(R.id.et_mobile_number);
        etVehicleType = view.findViewById(R.id.et_vehicle_type);
        tvVehicleNumber = view.findViewById(R.id.tv_vehicle_number);
        submit = view.findViewById(R.id.btn_profile_submit);
        submit.setOnClickListener(this);

        editButton = view.findViewById(R.id.ib_edit_icon);

        editButton.setOnClickListener(this);

        ivProfile.setOnClickListener(this);
        ib_profile.setOnClickListener(this);

        try {
            getProfileDetail();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        makeFieldUnEditable();

    }

    private EditText getEtName() {
        return (EditText) getView().findViewById(R.id.et_name);
    }

    private EditText getEtEmailAddress() {
        return (EditText) getView().findViewById(R.id.et_email_address);
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
        }
    }

    private void getProfileDetail() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.DRIVE_USER_ID, AppPreference.loadStringPref(getActivity(), KEY_USER_ID));

        NetworkConn networkConn = NetworkConn.getInstance(mContext);

        Log.e("Send Response", "Send Response: " + AppConstants.API_MOBLINE_NUMBER_CHECK + " :: " + jsonObject);

        networkConn.makeRequest(mContext, networkConn.createRawDataRequest(AppConstants.PROFILE_DETAIl, jsonObject.toString()), this, AppConstants.EVENT_PROFILE_DETAIL);

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        try {


            if (strEventType.equals(AppConstants.EVENT_PROFILE_DETAIL)) {

                Log.d("DriverProfile", response.toString());

                LoginModel loginModel = new Gson().fromJson(response.toString(), LoginModel.class);
                setProfile(loginModel);

            } else if (strEventType.equals(AppConstants.EVENT_PROFILE_UPDATE)) {

                Log.d("Updated Profile", response.toString());

                LoginModel loginBeen = new Gson().fromJson(response.toString(), LoginModel.class);

                AppPreference.saveStringPref(mContext, AppPreference.KEY_USER_ID, loginBeen.getResult().getUser_id());
                AppPreference.saveStringPref(mContext, AppPreference.KEY_TOKEN, "" + loginBeen.getResult().getToken());

                AppPreference.saveStringPref(mContext, AppPreference.KEY_NAME, "" + loginBeen.getResult().getFirst_name() + " " + loginBeen.getResult().getLast_name());
                AppPreference.saveStringPref(mContext, AppPreference.KEY_MOBILE, "" + loginBeen.getResult().getMobile());
                AppPreference.saveStringPref(mContext, AppPreference.KEY_PROFILE_IMAGE, "" + loginBeen.getResult().getProfile_img());


                Toast.makeText(mContext, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_SHORT).show();

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

    private void setProfile(LoginModel profile) {

        tvVehicleNumber.setText(profile.getResult().getVehicle_number());
        tvTrip.setText(profile.getResult().getTrips() + getString(R.string._trip));
        tvYear.setText(profile.getResult().getYears() + getString(R.string._years));
        getEtName().setText(profile.getResult().getFirst_name());
        getEtEmailAddress().setText(profile.getResult().getEmail());
        etMobileNumber.setText(profile.getResult().getMobile());

        etVehicleType.setText(profile.getResult().getDriver_vehicle());

        String imageURL = profile.getResult().getProfile_img();

        if (!TextUtils.isEmpty(imageURL)) {
            Glide.with(mContext).load(imageURL).into(ivProfile);
        }

        float rating = (float) profile.getResult().getRating();

        ratingbarProfile.setRating(rating);
    }

    private void pickPhoto() {

        image_picker.imageOptions();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("dfgfdgfdg", "ffffffff: " + resultCode + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);
        if (requestCode == Image_Picker.CAMERA_REQUEST) {
            Log.e("dfgfdgfdg", "ffffffff: " + resultCode + "  :::: " + RESULT_OK + " :::::: " + RESULT_CANCELED);

            switch (resultCode) {
                case RESULT_OK:

                    image_picker.cropImage(mContext, Uri.fromFile(Image_Picker.IMAGE_PATH), 1);

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
                                image_picker.cropImage(mContext, selectedImageURI, 1);
                            } else {
                                image_picker.cropImage(mContext, image_picker.getImageUrlWithAuthority(getActivity(), selectedImageURI), 1);
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
                        Glide.with(mContext)
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
            NetworkConn networkConn = NetworkConn.getInstance(mContext);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID));
            hashMap.put(AppConstants.KEY_VALUE_FIRST_NAME, getEtName().getText().toString().trim());
            hashMap.put(AppConstants.EMAIL, getEtEmailAddress().getText().toString().trim());
            hashMap.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_TOKEN));

            int fileType = 0;
            if (!TextUtils.isEmpty(imagePath))
                fileType = 1;
            else
                fileType = 0;
            hashMap.put(AppConstants.PROFILE_IMAGE, imagePath);


            Log.e("Send Registration", "Registration: " + AppConstants.PROFILE_UPDATE + "  :: " + hashMap.toString());

            networkConn.makeRequest(mContext, networkConn.createFormDataRequest(AppConstants.PROFILE_UPDATE, hashMap, fileType), this, AppConstants.EVENT_PROFILE_UPDATE);

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
                Toast.makeText(mContext, R.string.enter_valid_email, Toast.LENGTH_SHORT).show();
                return false;
            }

        }


        if (TextUtils.isEmpty(name)) {
            getEtName().requestFocus();
            Toast.makeText(mContext, R.string.please_fill_name, Toast.LENGTH_SHORT).show();
            return false;
        }





        return true;
    }

    private void makeFieldEdittable () {

        editButton.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);


        ib_profile.setClickable(true);
        ivProfile.setClickable(true);

        getEtEmailAddress().setFocusable(true);
        getEtEmailAddress().setFocusableInTouchMode(true);


        getEtName().setFocusable(true);
        getEtName().setFocusableInTouchMode(true);

        getEtEmailAddress().requestFocus();
    }

    private void makeFieldUnEditable () {

        editButton.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);


        ib_profile.setClickable(false);
        ivProfile.setClickable(false);

        getEtEmailAddress().setFocusable(false);
        getEtEmailAddress().setFocusableInTouchMode(false);


        getEtName().setFocusable(false);
        getEtName().setFocusableInTouchMode(false);

        editButton.requestFocus();
    }

}
