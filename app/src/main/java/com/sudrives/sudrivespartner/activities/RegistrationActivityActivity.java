package com.sudrives.sudrivespartner.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.adapters.AboutUsAdapter;
import com.sudrives.sudrivespartner.adapters.VehicleNameAdapter;
import com.sudrives.sudrivespartner.adapters.VehicleTypesAdapter;
import com.sudrives.sudrivespartner.models.CityListModel;
import com.sudrives.sudrivespartner.models.LoginModel;
import com.sudrives.sudrivespartner.models.RegistrationModel;
import com.sudrives.sudrivespartner.models.StatesModel;
import com.sudrives.sudrivespartner.models.VehicleNameModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.ErrorLayout;
import com.sudrives.sudrivespartner.utils.GetLocation;
import com.sudrives.sudrivespartner.utils.Image_Picker;
import com.sudrives.sudrivespartner.utils.views.SearchableListDialog;
import com.sudrives.sudrivespartner.utils.views.SearchableSpinner;
import com.theartofdev.edmodo.cropper.CropImage;


import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.services.common.CommonUtils;

public class RegistrationActivityActivity extends Activity implements View.OnClickListener, NetworkConn.OnRequestResponse, CompoundButton.OnCheckedChangeListener {

    private TextView tvRagistrationMobileNo;
    private Spinner spinnerRegistrationVehicalType, spinner_registration_vehicalname;
    private Spinner spinnerRegistrationSearch;
    private TextView tvRegistrationUploadDrivingL;
    private TextView tvRegistrationUploadRC;
    private TextView tvFitenssCertificate;
    private TextView tvInsurance;
    private TextView tvPermit;
    private CircleImageView ivProfile;
    private ImageButton ib_profile;
    private Intent intent;
    private String strVehiclelicensePath = "", strVehicleRCPath = "", strFitness = "", strInsurance = "", strPermit = "", strProfilePath = "";
    private boolean isDtivingRC = false, isTakePic = true;
    private Image_Picker image_picker;
    private VehicleTypesAdapter mVehicleTypesAdapter;
    private VehicleNameAdapter mVehicleNameAdapter;
    private List<RegistrationModel.ResultBean.VehicleTypesBean> mVehicleTypesList = new ArrayList<>();
    private List<VehicleNameModel.Result> mVehicleNameList = new ArrayList<>();

    private List<StatesModel.Result> state = new ArrayList<>();
    private List<CityListModel.Result> city = new ArrayList<>();


    SearchableListDialog spinnerDialog;
    private AboutUsAdapter mAboutUsAdapter;
    private List<RegistrationModel.ResultBean.AboutUsBean> mAboutUsList = new ArrayList<>();
    private static final int INITIAL_REQUEST = 222;
    private SearchableSpinner sp_state, sp_city;

    EditText etVehicleName;

    TextView tvEnglish, tvHindi;
    Switch languageSwitch;
    androidx.appcompat.app.AlertDialog alertDialog1;
    private CheckBox cb_driver_as_a_daily, cb_driver_as_a_rental, cb_driver_as_a_outstation;

    private ConstraintLayout const_multiple_opt;
    private CheckBox check_mini, check_micro, check_sedan;
    private GetLocation getLocation;
    private LocationManager locationManager;
    private static final int INITIAL_REQUEST_LOCATIONS = 22222;

    private static final String[] INITIAL_PERMS = {

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    private static final String[] INITIAL_PERMS_LOCATIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private boolean isFitness;
    private boolean isInsurance;
    private boolean isPermit;
    private boolean isProfile;
    CheckBox chk_terms_con;
    TextView read_terms_con;
    AppDialogs appDialogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        intent = getIntent();
        appDialogs = new AppDialogs();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        getLocation = new GetLocation(RegistrationActivityActivity.this, "RegistrationActivityActivity");
        Log.e("fgdfg", "ffffffff 1234567890: " + intent.getStringExtra(AppConstants.KEY_USER_ID));
        image_picker = new Image_Picker(this);

        ib_profile = findViewById(R.id.ib_profile);
        ivProfile = findViewById(R.id.iv_profile);

        sp_city = findViewById(R.id.sp_city);
        sp_state = findViewById(R.id.sp_state);
        chk_terms_con = findViewById(R.id.chk_terms);
        read_terms_con = findViewById(R.id.read_terms_con);
        read_terms_con.setOnClickListener(this);

        cb_driver_as_a_daily = findViewById(R.id.cb_driver_as_a_daily);
        cb_driver_as_a_rental = findViewById(R.id.cb_driver_as_a_rental);
        cb_driver_as_a_outstation = findViewById(R.id.cb_driver_as_a_outstation);

        const_multiple_opt = findViewById(R.id.const_multiple_opt);
        check_mini = findViewById(R.id.cb_mini);
        check_micro = findViewById(R.id.cb_micro);
        check_sedan = findViewById(R.id.cb_sedan);

        tvRagistrationMobileNo = findViewById(R.id.et_mobile);
        spinner_registration_vehicalname = findViewById(R.id.spinner_registration_vehicalname);
        spinnerRegistrationVehicalType = findViewById(R.id.spinner_registration_vehicalType);
        spinnerRegistrationSearch = findViewById(R.id.spinner_registration_search);
        tvRegistrationUploadDrivingL = findViewById(R.id.tv_registration_uploadDrivingL);
        tvRegistrationUploadRC = findViewById(R.id.tv_registration_uploadRC);
        tvFitenssCertificate = findViewById(R.id.tv_fitness_certificate);
        tvInsurance = findViewById(R.id.tv_insurance);
        tvPermit = findViewById(R.id.tv_permit);


        tvFitenssCertificate.setOnClickListener(this);
        tvInsurance.setOnClickListener(this);
        tvPermit.setOnClickListener(this);


        etVehicleName = findViewById(R.id.et_vehicle_name);

        tvEnglish = findViewById(R.id.tv_english);
        tvHindi = findViewById(R.id.tv_hindi);
        languageSwitch = findViewById(R.id.language_switch);


        String language = AppPreference.loadStringPref(RegistrationActivityActivity.this, AppPreference.KEY_LANGUAGE);

        if (language.equalsIgnoreCase("hindi")) {
            languageSwitch.setChecked(true);
        } else {
            languageSwitch.setChecked(false);
        }


        languageSwitch.setOnCheckedChangeListener(this);
        spinnerDialog = new SearchableListDialog();
        checkLocationOnOff();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkLocationOnOff() {

        if (canAccessCoarseLocation() && canAccessFineLocation()) {

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                if (appDialogs != null){
                    if (appDialogs.networkDialogLoader.isShowing()) {
                        appDialogs.hideLoader();
                    }
                    appDialogs.showLoader(RegistrationActivityActivity.this);
                    getLocation.checkLocationPermission();
                }


            } else {
                showSettingsAlert();
            }
        } else {
            ActivityCompat.requestPermissions(this, INITIAL_PERMS_LOCATIONS, INITIAL_REQUEST_LOCATIONS);
        }
    }

    private void setValue() {

        if (intent != null) {
            tvRagistrationMobileNo.setText(intent.getStringExtra(AppConstants.KEY_MOBILE));
        }
        findViewById(R.id.but_registration).setOnClickListener(this);
        //findViewById(R.id.tv_registration_change).setOnClickListener(this);
        findViewById(R.id.ll_registration_drivingRC).setOnClickListener(this);
        findViewById(R.id.ll_registration_drivingLicense).setOnClickListener(this);
        tvRegistrationUploadDrivingL.setOnClickListener(this);
        tvRegistrationUploadRC.setOnClickListener(this);

        ivProfile.setOnClickListener(this);
        ib_profile.setOnClickListener(this);


        state.add(0, new StatesModel.Result("0", getResources().getString(R.string.select_state), "0"));
        ArrayAdapter<StatesModel.Result> stateresultArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, state);
        sp_state.setAdapter(stateresultArrayAdapter);
        sp_state.setTitle(getString(R.string.select_or_search_state));
        sp_state.setPositiveButton(getString(R.string.text_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeWindow(sp_state);
                dialog.dismiss();
            }
        });


        city.add(0, new CityListModel.Result("0", "0", getResources().getString(R.string.select_city), "0"));
        ArrayAdapter<CityListModel.Result> cityresultArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, city);

        sp_city.setAdapter(cityresultArrayAdapter);
        sp_city.setTitle(getString(R.string.select_or_search_city));
        sp_city.setPositiveButton(getString(R.string.text_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                closeWindow(sp_city);
                dialog.dismiss();
            }
        });

        mVehicleTypesList.clear();
        mVehicleTypesList.add(new RegistrationModel.ResultBean.VehicleTypesBean("0", "", getResources().getString(R.string.vehicle_type), "",
                "", "", ""));

        mVehicleTypesAdapter = new VehicleTypesAdapter(RegistrationActivityActivity.this, R.layout.raw_item_layout, mVehicleTypesList);
        spinnerRegistrationVehicalType.setAdapter(mVehicleTypesAdapter);


        mVehicleNameList.clear();
        mVehicleNameList.add(new VehicleNameModel.Result("", "", "", "0", getResources().getString(R.string.select_vehicle_name), "0"));
        mVehicleNameAdapter = new VehicleNameAdapter(RegistrationActivityActivity.this, R.layout.raw_item_layout, mVehicleNameList);
        spinner_registration_vehicalname.setAdapter(mVehicleNameAdapter);


        callGetTypes();
        callState();

    }

    private void callGetTypes() {


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();


            Log.e("Send Response", "Send Response: " + AppConstants.API_GET_TYPE + " :: " + jdata);

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_GET_TYPE, jdata.toString()), this, AppConstants.EVENT_API_GET_TYPE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callGetVihicalNameTypes(String id) {


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();
            jdata.put("vehicle_type_id", id);


            Log.e("Send Response", "Send Response: " + AppConstants.API_GET_TYPE + " :: " + jdata);

            networkConn.makeRequestForCity(this, networkConn.createRawDataRequest(AppConstants.API_GET_VEHICLE_NAME, jdata.toString()), this, AppConstants.EVENT_API_GET_VEHICLE_NAME);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void closeWindow(View v) {

        try {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } catch (Exception ignored) {

            Log.e("Exception", "Exception: " + ignored.getMessage());
        }
    }


    private void callState() {


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();


            Log.e("Send Response", "Send Response: " + AppConstants.API_STATE + " :: " + jdata);

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_STATE, jdata.toString()), this, AppConstants.EVENT_API_STATE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void callCity(String state_id) {


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();
            jdata.put("state_id", state_id);
            Log.e("Send Response", "Send Response: " + AppConstants.API_CITY + " :: " + jdata);
            networkConn.makeRequestForCity(this, networkConn.createRawDataRequest(AppConstants.API_CITY, jdata.toString()), this, AppConstants.EVENT_API_CITY);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private EditText getEtRegistrationFName() {
        return (EditText) findViewById(R.id.et_registration_fName);
    }

   /* private EditText getEtRegistrationLName() {
        return (EditText) findViewById(R.id.et_registration_lName);
    }*/

    private EditText getEtRegistrationVehicalNo() {
        return (EditText) findViewById(R.id.et_registration_vehicalNo);
    }


    private EditText getEtRegistrationLocation() {
        return (EditText) findViewById(R.id.et_registration_location);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.but_registration:
                //TODO implement

                checkValidation();
                break;

           /* case R.id.tv_registration_change:
                //TODO implement
                onBackPressed();
                break;*/


            case R.id.iv_profile:
            case R.id.ib_profile:

                CommonUtils.hideKeyboard(this, tvInsurance);
                isProfile = true;
                startCheckPermissions();

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
            case R.id.tv_fitness_certificate:
                CommonUtils.hideKeyboard(this, tvFitenssCertificate);

                isFitness = true;
                startCheckPermissions();
                break;
            case R.id.tv_insurance:
                CommonUtils.hideKeyboard(this, tvInsurance);

                isInsurance = true;
                startCheckPermissions();
                break;
            case R.id.tv_permit:

                CommonUtils.hideKeyboard(this, tvPermit);

                isPermit = true;
                startCheckPermissions();
                break;

            case R.id.read_terms_con:

                Intent i = new Intent(RegistrationActivityActivity.this, MainActivity.class);
                i.putExtra("value", "https://sudrives.com/terms&condition.html");
                startActivity(i);

                break;
        }
    }



    private void checkValidation() {

        ErrorLayout error = new ErrorLayout(this, findViewById(R.id.error_layout));


        if (strProfilePath.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_profile), ErrorLayout.MsgType.Error, true);
            return;

        }


        if (getEtRegistrationFName().getText().toString().trim().isEmpty()) {

            error.showAlert(getResources().getString(R.string.full_name), ErrorLayout.MsgType.Error, true);

            return;

        }

//        if (getEtRegistrationLName().getText().toString().trim().isEmpty()) {
//            error.showAlert(getResources().getString(R.string.error_please_enter_last_name), ErrorLayout.MsgType.Error, true);
//            return;
//
//        }

        if (getEtRegistrationVehicalNo().getText().toString().trim().isEmpty()) {
            error.showAlert(getResources().getString(R.string.error_please_enter_vehicle_no), ErrorLayout.MsgType.Error, true);
            return;

        }


       /* String dlNumber = (getEtRegistrationVehicalNo().getText().toString().trim()).substring(0, 2);

        Log.e("dfgdfg", "fdfdsf: " + dlNumber);*/


        /*if (dlNumber.trim().equalsIgnoreCase("dl")) {

            if (Methods.isVehicleNumberDL(getEtRegistrationVehicalNo().getText().toString().trim()) ||  Methods.isVehicleNumber(getEtRegistrationVehicalNo().getText().toString().trim())){


            }else {
                error.showAlert(getResources().getString(R.string.error_please_enter_valid_vehicle_no), ErrorLayout.MsgType.Error, true);
                return;
            }


           *//* if (!Methods.isVehicleNumber(getEtRegistrationVehicalNo().getText().toString().trim()) || !Methods.isVehicleNumberDL(getEtRegistrationVehicalNo().getText().toString().trim())) {
                error.showAlert(getResources().getString(R.string.error_please_enter_valid_vehicle_no), ErrorLayout.MsgType.Error, true);
                return;

            }*//*
        } else {
            if (!Methods.isVehicleNumber(getEtRegistrationVehicalNo().getText().toString().trim())) {
                error.showAlert(getResources().getString(R.string.error_please_enter_valid_vehicle_no), ErrorLayout.MsgType.Error, true);
                return;

            }
        }
*/

       /* if (getEtRegistrationLocation().getText().toString().trim().isEmpty()) {
            error.showAlert(getResources().getString(R.string.error_please_enter_location), ErrorLayout.MsgType.Error, true);
            return;

        }*/

        if (mVehicleTypesList.get(spinnerRegistrationVehicalType.getSelectedItemPosition()).getId().trim().equalsIgnoreCase("0")) {
            error.showAlert(getResources().getString(R.string.error_please_select_vehicle_type), ErrorLayout.MsgType.Error, true);
            return;

        }

        if (mVehicleNameList.get(spinner_registration_vehicalname.getSelectedItemPosition()).id.trim().equalsIgnoreCase("0")) {
            error.showAlert(getResources().getString(R.string.error_please_select_vehicle_name), ErrorLayout.MsgType.Error, true);
            return;

        }

      /*  if (TextUtils.isEmpty(etVehicleName.getText().toString().trim())) {
            error.showAlert(getResources().getString(R.string.error_please_add_vehicle_name), ErrorLayout.MsgType.Error, true);
            return;
        }
*/
       /* if (mAboutUsList.get(spinnerRegistrationSearch.getSelectedItemPosition()).getId().trim().equalsIgnoreCase("0")) {
            error.showAlert(getResources().getString(R.string.error_why_using_movecabs), ErrorLayout.MsgType.Error, true);
            return;

        }

*/


        if (state.get(sp_state.getSelectedItemPosition()).id.trim().equalsIgnoreCase("0")) {
            error.showAlert(getResources().getString(R.string.please_select_state), ErrorLayout.MsgType.Error, true);
            return;

        }

        if (city.get(sp_city.getSelectedItemPosition()).id.trim().equalsIgnoreCase("0")) {
            error.showAlert(getResources().getString(R.string.please_select_city), ErrorLayout.MsgType.Error, true);
            return;

        }



       /*

       if (strVehiclelicensePath.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_driving_license), ErrorLayout.MsgType.Error, true);
            return;

        }

       if (strVehicleRCPath.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_rc), ErrorLayout.MsgType.Error, true);
            return;

        }

        if (strFitness.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_fitness), ErrorLayout.MsgType.Error, true);
            return;

        }
        if (strInsurance.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_insurance), ErrorLayout.MsgType.Error, true);
            return;

        }
        if (strPermit.trim().equalsIgnoreCase("")) {
            error.showAlert(getResources().getString(R.string.error_please_upload_permit), ErrorLayout.MsgType.Error, true);
            return;

        }*/

        if (!cb_driver_as_a_daily.isChecked() && !cb_driver_as_a_rental.isChecked() && !cb_driver_as_a_outstation.isChecked()) {
            error.showAlert(getResources().getString(R.string.please_select_at_least_one_service), ErrorLayout.MsgType.Error, true);
            return;

        }
        if (!chk_terms_con.isChecked()) {
            error.showAlert(getResources().getString(R.string.please_select_terms_and_con), ErrorLayout.MsgType.Error, true);
            return;

        }

        callVerificationAPI();

    }


    private void callVerificationAPI() {


        try {

           /* "userid" => "",
                    "otp" => "",
                    "firstname" => "",
                    "lastname" => "",
                    "mobile" => "",
                    "vehicle_number" => "",
                    "vehicle_types" => "",
                    "address" => "",
                    "hear_us" => "",
                    "type" => "registration", //login registration
                    "vehicle_name" => "",
                     // "driving_license" => "",
                     // "rc_number" => "",
                    "daily" => "",
                    "rental" => "",
                    "outstation" => "",
                    "state_id" => "",
                    "city_id" => "",
                    "language" => "",*/


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


            final NetworkConn networkConn = NetworkConn.getInstance(this);
            final HashMap<String, String> jdata = new HashMap<>();
            jdata.put("userid", intent.getStringExtra(AppConstants.KEY_USER_ID));
            jdata.put("firstname", getEtRegistrationFName().getText().toString());
            jdata.put("mobile", intent.getStringExtra(AppConstants.KEY_MOBILE));
            jdata.put("vehicle_number", getEtRegistrationVehicalNo().getText().toString());

            String vehicleTypes = mVehicleTypesList.get(spinnerRegistrationVehicalType.getSelectedItemPosition()).getId();
            //String data = "";
            if (check_mini.isChecked()){
               // vehicleTypes +=
                vehicleTypes += ",2";
            }
            if (check_micro.isChecked()){

                vehicleTypes += ",3";
            }
            if (check_sedan.isChecked()){

                vehicleTypes += ",1";
            }

            jdata.put("vehicle_types", vehicleTypes);
            jdata.put("type", "registration");
            jdata.put("vehicle_name", mVehicleNameList.get(spinner_registration_vehicalname.getSelectedItemPosition()).name);
            jdata.put("daily", strDaily);
            jdata.put("rental", strRental);
            jdata.put("outstation", strOutstation);
            jdata.put("state_id", state.get(sp_state.getSelectedItemPosition()).id);
            jdata.put("city_id", city.get(sp_city.getSelectedItemPosition()).id);
            jdata.put(AppConstants.PROFILE_IMAGE, strProfilePath);
            jdata.put(AppConstants.KEY_MOBILE, intent.getStringExtra(AppConstants.KEY_MOBILE).trim());
//            jdata.put(AppConstants.KEY_USER_ROLE, "2");

            Log.e("Send Registration", "Registration: " + AppConstants.API_REGISTRATION_TWO + "  :: " + jdata);
            if (vehicleTypes.length()>1){

                androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.DialogTheme);
// ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = this.getLayoutInflater();
                View view = inflater.inflate(R.layout.layout_consent_multi_cab, null);



                final CheckBox checkBox = view.findViewById(R.id.textcheck);
                final Button btn_submitconsent = view.findViewById(R.id.btn_submitconsent);
                Button btn_cancel = view.findViewById(R.id.btn_cancel);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()){
                            btn_submitconsent.setEnabled(true);
                            btn_submitconsent.setBackground(ContextCompat.getDrawable(RegistrationActivityActivity.this,R.drawable.rounded_corner_shape_primary));
                        }else {
                            btn_submitconsent.setEnabled(false);
                            btn_submitconsent.setBackground(ContextCompat.getDrawable(RegistrationActivityActivity.this,R.drawable.rounded_corner_shape_grey));

                        }
                    }
                });

                btn_submitconsent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        networkConn.makeRequest(RegistrationActivityActivity.this, networkConn.createFormDataRequest(AppConstants.API_REGISTRATION_TWO, jdata, 1), RegistrationActivityActivity.this, AppConstants.EVENT_API_REGISTRATION_TWO);
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.dismiss();
                    }
                });
                dialogBuilder.setView(view);
                alertDialog1 = dialogBuilder.create();

                alertDialog1.show();


            }else {
                networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.API_REGISTRATION_TWO, jdata, 1), this, AppConstants.EVENT_API_REGISTRATION_TWO);
            }


        } catch (Exception e) {
            e.printStackTrace();
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


                    if (isFitness) {
                        isFitness = false;

                        if (image_picker.checkURI(selectedImageURI)) {

//                            strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                            strFitness = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);
                        } else {

                            strFitness = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                        }

                        tvFitenssCertificate.setText(new File(strFitness).getName());
                        Log.e("File URI", "strFitness" + strFitness);
                        Log.e("File URI", "strFitness" + new File(strFitness).getName());
                        return;
                    }

                    if (isInsurance) {
                        isInsurance = false;

                        if (image_picker.checkURI(selectedImageURI)) {

//                            strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                            strInsurance = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);
                        } else {

                            strInsurance = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
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
                            strPermit = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);
                        } else {

                            strPermit = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
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
                            // strPermit = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);
                            image_picker.cropImage(RegistrationActivityActivity.this, selectedImageURI, 0);
                        } else {

                            //strPermit = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                            image_picker.cropImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI), 0);
                        }

//                        tvPermit.setText(new File(strPermit).getName());

                        return;
                    }


                    if (isDtivingRC) {


                        if (image_picker.checkURI(selectedImageURI)) {

//                            strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                            strVehicleRCPath = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);
                        } else {

                            strVehicleRCPath = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
//                            strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                        }

                        tvRegistrationUploadRC.setText(new File(strVehicleRCPath).getName());
                        Log.e("File URI", "profilePicfileUrifileUri" + strVehicleRCPath);
                        Log.e("File URI", "profilePicfileUrifileUri" + new File(strVehicleRCPath).getName());

                    } else {


                        if (image_picker.checkURI(selectedImageURI)) {

                            strVehiclelicensePath = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);
//                            strVehiclelicensePath = image_picker.getRealPathFromURI(selectedImageURI);
                        } else {

                            strVehiclelicensePath = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
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
                                strFitness = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);

                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                strFitness = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                            }
                            tvFitenssCertificate.setText(new File(strFitness).getName());

                            Log.e("File URI", "strFitness" + strFitness);
                            Log.e("File URI", "strFitness" + new File(strFitness).getName());

                            return;


                        }

                        if (isInsurance) {

                            isInsurance = false;


                            if (image_picker.checkURI(selectedImageURI)) {

                                //strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                                strInsurance = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);

                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                strInsurance = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));


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
                                strPermit = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);

                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                strPermit = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                            }
                            tvPermit.setText(new File(strPermit).getName());

                            Log.e("File URI", "strPermit" + strPermit);
                            Log.e("File URI", "strPermit" + new File(strPermit).getName());

                            return;


                        }

                        if (isDtivingRC) {


                            if (image_picker.checkURI(selectedImageURI)) {

                                //strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                                strVehicleRCPath = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);

                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                strVehicleRCPath = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));


                            }
                            tvRegistrationUploadRC.setText(new File(strVehicleRCPath).getName());

                            Log.e("File URI", "profilePicfileUrifileUri" + strVehicleRCPath);
                            Log.e("File URI", "profilePicfileUrifileUri" + new File(strVehicleRCPath).getName());


                        }


                        if (isProfile) {

                            isProfile = false;
                            if (image_picker.checkURI(selectedImageURI)) {

                                //strVehicleRCPath = image_picker.getRealPathFromURI(selectedImageURI);
                                //strVehicleRCPath = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);

                                image_picker.cropImage(RegistrationActivityActivity.this, selectedImageURI, 0);


                            } else {

//                                    strVehicleRCPath = image_picker.getRealPathFromURI(image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                // strVehicleRCPath = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
                                image_picker.cropImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI), 0);


                            }


                        } else {


                            if (image_picker.checkURI(selectedImageURI)) {

                                strVehiclelicensePath = image_picker.resizeImage(RegistrationActivityActivity.this, selectedImageURI);
//                                    strVehiclelicensePath = image_picker.getRealPathFromURI(selectedImageURI);
                            } else {

                                strVehiclelicensePath = image_picker.resizeImage(RegistrationActivityActivity.this, image_picker.getImageUrlWithAuthority(this, selectedImageURI));
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


            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();

                    strProfilePath = image_picker.getRealPathFromURI(resultUri);


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


    @Override
    public void onResponse(final JSONObject response, String strEventType) {
        Log.e("city", "Response: " + response);

        try {

            if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_REGISTRATION_TWO)) {
                LoginModel loginBeen = new Gson().fromJson(response.toString(), LoginModel.class);

                AppPreference.saveStringPref(this, AppPreference.KEY_USER_ID, loginBeen.getResult().getUser_id());
                AppPreference.saveStringPref(this, AppPreference.KEY_TOKEN, "" + loginBeen.getResult().getToken());
                AppPreference.saveStringPref(this, AppPreference.KEY_NAME, "" + loginBeen.getResult().getFirst_name() + " " + loginBeen.getResult().getLast_name());
                AppPreference.saveStringPref(this, AppPreference.KEY_MOBILE, "" + loginBeen.getResult().getMobile());
                AppPreference.saveStringPref(this, AppPreference.KEY_PROFILE_IMAGE, "" + loginBeen.getResult().getProfile_img());
                AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 3);

                if (AppPreference.loadStringPref(RegistrationActivityActivity.this, AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase("")) {
                    AppPreference.saveStringPref(this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);
                }


                Intent intent = new Intent(this, ContentUploadDocumentActivity.class);
                intent.putExtra(AppConstants.KEY_MOBILE, loginBeen.getResult().getMobile());
                intent.putExtra(AppConstants.KEY_USER_ID, loginBeen.getResult().getUser_id());
                startActivity(intent);


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_STATE)) {

                StatesModel mStatesModel = new Gson().fromJson(response.toString(), StatesModel.class);

                state.clear();
                state = mStatesModel.result;
                state.add(0, new StatesModel.Result("0", getResources().getString(R.string.select_state), "0"));
                ArrayAdapter<StatesModel.Result> resultArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, state);
                sp_state.setAdapter(resultArrayAdapter);
                sp_state.setTitle(getString(R.string.select_or_search_state));

                int pos = 0;
                String stateName = getStateName(AppConstants.VALUE_LATITUDE, AppConstants.VALUE_LONGITUDE).trim();

                if (!stateName.isEmpty()) {
                    for (int i = 0; i < state.size(); i++) {
                        if (state.get(i).name.trim().equalsIgnoreCase(stateName.trim())) {
                            pos = i;
                            break;
                        }
                    }
                }

                sp_state.setSelection(pos);
                sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (!state.get(position).id.trim().equalsIgnoreCase("0")) {
                            callCity("" + state.get(position).id);

                            closeWindow(sp_state);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_CITY)) {

                CityListModel cityModel = new Gson().fromJson(response.toString(), CityListModel.class);
                city.clear();
                city = cityModel.result;
                city.add(0, new CityListModel.Result("0", "0", getResources().getString(R.string.select_city), "0"));
                ArrayAdapter<CityListModel.Result> resultArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, city);
                Log.e("citylist", response.toString());
                sp_city.setAdapter(resultArrayAdapter);
                sp_city.setTitle(getString(R.string.select_or_search_city));


                int pos = 0;


                if (!cityName.isEmpty()) {
                    for (int i = 0; i < city.size(); i++) {
                        if (city.get(i).name.trim().equalsIgnoreCase(cityName.trim())) {
                            pos = i;
                            break;
                        }
                    }
                }


                sp_city.setSelection(pos);

                sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.e("id", city.get(position).id);
                        closeWindow(sp_city);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_GET_TYPE)) {
                mVehicleTypesList.clear();
                RegistrationModel registrationBeen = new Gson().fromJson(response.toString(), RegistrationModel.class);
                mVehicleTypesList.add(new RegistrationModel.ResultBean.VehicleTypesBean("0", "", getResources().getString(R.string.vehicle_type), "",
                        "", "", ""));
                Log.e("vehicleshashank", response.toString());
                mVehicleTypesList.addAll(registrationBeen.getResult().getVehicle_types());

                mVehicleTypesAdapter = new VehicleTypesAdapter(RegistrationActivityActivity.this, R.layout.raw_item_layout, mVehicleTypesList);
                spinnerRegistrationVehicalType.setAdapter(mVehicleTypesAdapter);
                spinnerRegistrationVehicalType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (!mVehicleTypesList.get(position).getId().trim().equalsIgnoreCase("0"))
                            callGetVihicalNameTypes(mVehicleTypesList.get(position).getId().trim());
                        Log.e("vehicleid", mVehicleTypesList.get(position).getId().trim());
                        if (mVehicleTypesList.get(position).getId().trim().equalsIgnoreCase("2")) {
                            //mini micro
                            const_multiple_opt.setVisibility(View.VISIBLE);

                            check_micro.setVisibility(View.VISIBLE);

                            check_mini.setVisibility(View.GONE);
                            check_sedan.setVisibility(View.GONE);

                        }else if (mVehicleTypesList.get(position).getId().trim().equalsIgnoreCase("3")) {
                            //micro
                            const_multiple_opt.setVisibility(View.VISIBLE);

                            check_mini.setVisibility(View.VISIBLE);
                            check_micro.setVisibility(View.GONE);
                            check_sedan.setVisibility(View.GONE);

                        }else if (mVehicleTypesList.get(position).getId().trim().equalsIgnoreCase("1")) {
                            //sedan
                            const_multiple_opt.setVisibility(View.VISIBLE);
                            check_mini.setVisibility(View.VISIBLE);
                            check_micro.setVisibility(View.VISIBLE);
                            check_sedan.setVisibility(View.GONE);

                        }else if (mVehicleTypesList.get(position).getId().trim().equalsIgnoreCase("6")) {
                            //lux sedan
                            const_multiple_opt.setVisibility(View.VISIBLE);
                            check_mini.setVisibility(View.VISIBLE);
                            check_micro.setVisibility(View.VISIBLE);
                            check_sedan.setVisibility(View.VISIBLE);
                        }else {
                             const_multiple_opt.setVisibility(View.GONE);
                             check_mini.setVisibility(View.GONE);
                             check_micro.setVisibility(View.GONE);
                             check_sedan.setVisibility(View.GONE);
                         }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                mAboutUsList.add(new RegistrationModel.ResultBean.AboutUsBean("0", getResources().getString(R.string.select_hear), ""));
                //mAboutUsList.addAll(registrationBeen.getResult().getAbout_us());

                mAboutUsList.add(new RegistrationModel.ResultBean.AboutUsBean("1", getString(R.string.business_usage), ""));
                mAboutUsList.add(new RegistrationModel.ResultBean.AboutUsBean("2", getString(R.string.personal_usage), ""));

                mAboutUsAdapter = new AboutUsAdapter(RegistrationActivityActivity.this, R.layout.raw_item_layout, mAboutUsList);
                spinnerRegistrationSearch.setAdapter(mAboutUsAdapter);


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_GET_VEHICLE_NAME)) {

                VehicleNameModel mVehicleNameModel = new Gson().fromJson(response.toString(), VehicleNameModel.class);
                mVehicleNameList.clear();
                mVehicleNameList.add(new VehicleNameModel.Result("", "", "", "0", getResources().getString(R.string.select_vehicle_name), "0"));
                mVehicleNameList.addAll(mVehicleNameModel.result);
                mVehicleNameAdapter = new VehicleNameAdapter(RegistrationActivityActivity.this, R.layout.raw_item_layout, mVehicleNameList);
                spinner_registration_vehicalname.setAdapter(mVehicleNameAdapter);


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_VERIFICATION)) {


                Toast.makeText(this, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                switchActivity(ActivityVerificationActivity.class, response.getString(AppConstants.KEY_USER_ID));


//                AppDialogs.singleButtonVersionDialog(this, R.layout.success_dialog1, getResources().getString(R.string.text_confirmation), R.drawable.done, response.getString(AppConstants.KEY_MESSAGE),
//                        getString(R.string.ok),
//                        new AppDialogs.SingleButoonCallback() {
//                            @Override
//                            public void singleButtonSuccess(String from) {
//                                try {
//
//
//                                    switchActivity(ActivityVerificationActivity.class, response.getString(AppConstants.KEY_USER_ID));
//                                } catch (Exception e) {
//
//                                }
//
//
//                            }
//                        }, true);


            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override

    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

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
            case INITIAL_REQUEST_LOCATIONS: {

                Log.e("dsfdsf", "YYYYYY: " + grantResults.length);

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                    checkLocationOnOff();
            }
            break;

        }
    }

    private void switchActivity(Class targetClass, String userId) {

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


        startActivity(new Intent(this, targetClass)
                .putExtra(AppConstants.KEY_VALUE_FIRST_NAME, getEtRegistrationFName().getText().toString())
                .putExtra(AppConstants.KEY_VALUE_VEHICLE_NO, getEtRegistrationVehicalNo().getText().toString().toUpperCase())
                .putExtra(AppConstants.KEY_VALUE_LOCATION, getEtRegistrationLocation().getText().toString())
                .putExtra(AppConstants.KEY_VALUE_VEHICLE_TYPE, mVehicleTypesList.get(spinnerRegistrationVehicalType.getSelectedItemPosition()).getId())
                .putExtra(AppConstants.KEY_VALUE_ABOUT_US, mAboutUsList.get(spinnerRegistrationSearch.getSelectedItemPosition()).getId())
                .putExtra(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH, strVehiclelicensePath)
                .putExtra(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH, strVehicleRCPath)
                .putExtra(AppConstants.KEY_VALUE_MOBILE, intent.getStringExtra(AppConstants.KEY_MOBILE).trim())
                .putExtra(AppConstants.KEY_USER_ID, userId)
                .putExtra(AppConstants.KEY_TYPE, AppConstants.KEY_VALUE_REGISTER)
                .putExtra(AppConstants.KEY_VEHICLE_NAME, etVehicleName.getText().toString().trim())

                .putExtra(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE, strFitness)
                .putExtra(AppConstants.KEY_VALUE_INSURANCE, strInsurance)
                .putExtra(AppConstants.KEY_VALUE_PERMIT, strPermit)
                .putExtra(AppConstants.PROFILE_IMAGE, strProfilePath)


                .putExtra(AppConstants.KEY_VALUE_DAILY, strDaily)
                .putExtra(AppConstants.KEY_VALUE_RENTAL, strRental)
                .putExtra(AppConstants.KEY_VALUE_OUTSTATION, strOutstation)


        );

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            tvHindi.setTextColor(getResources().getColor(R.color.text_color));
            tvEnglish.setTextColor(getResources().getColor(R.color.dark_grey));

            AppPreference.saveStringPref(RegistrationActivityActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);

            setLocale("hi");

        } else {

            tvEnglish.setTextColor(getResources().getColor(R.color.text_color));
            tvHindi.setTextColor(getResources().getColor(R.color.dark_grey));

            AppPreference.saveStringPref(RegistrationActivityActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_ENGLISH);

            setLocale("en");

        }
    }


    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);

        //  reloadResource();

    }


    private void reloadResource() {

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

//    public void setLocale(String lang) {
//
//        Locale myLocale = new Locale(lang);
//        Resources res = getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = res.getConfiguration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//        onConfigurationChanged(conf);
//
//
//        //  restartActivity();
//
//    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        recreate();
        super.onConfigurationChanged(newConfig);

    }

    private boolean canAccessFineLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean canAccessCoarseLocation() {
        return (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private AlertDialog alertDialog;

    public void showSettingsAlert() {

        if (alertDialog != null && alertDialog.isShowing()) {

            alertDialog.dismiss();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Setting Dialog Title
        builder.setTitle(R.string.GPSAlertDialogTitle);

        //Setting Dialog Message
        builder.setMessage(R.string.GPSAlertDialogMessage);

        //On Pressing Setting button
        builder.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        //On pressing cancel button
        builder.setNegativeButton(R.string.no_thanks, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    public void onLocationUpdate(Location location) {
        try {

            appDialogs.hideLoader();

            if (location != null) {
                if (location.getLatitude() != 0 && location.getLongitude() != 0) {
                    AppConstants.VALUE_LATITUDE = location.getLatitude();
                    AppConstants.VALUE_LONGITUDE = location.getLongitude();

                    setValue();

                    getLocation.disConnectGoogleClient();
                    getLocation.stopLocationUpdates();

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String cityName = "";
    String stateName1 = "";

    public String getStateName(double lat, double lng) {
        Geocoder geocoder = new Geocoder(RegistrationActivityActivity.this, Locale.getDefault());
        String stateName = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);

            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            stateName = obj.getAdminArea();
            stateName1 = obj.getAdminArea();

            if (obj.getSubAdminArea() != null) {

                cityName = obj.getSubAdminArea();
            } else {
                cityName = obj.getLocality();
            }


            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return stateName;
    }


}
