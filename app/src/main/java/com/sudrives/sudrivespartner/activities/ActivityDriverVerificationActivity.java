package com.sudrives.sudrivespartner.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.LoginModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.ErrorLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

public class ActivityDriverVerificationActivity extends Activity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    private RelativeLayout rlMain;
    private LinearLayout llMobileNo;
    private TextView tvResendOtp;
    private TextView tvVerificationMobileNo;
    private Intent intent;
    private boolean clickFlage = true;

    private EditText frstotp, secondotp, thirdotp, fourthotp;
    private String strUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_verification_activity);

        frstotp = findViewById(R.id.et_otp);
        secondotp = findViewById(R.id.et_otp1);
        thirdotp = findViewById(R.id.et_otp2);
        fourthotp = findViewById(R.id.et_otp3);


        // Hello world

        intent = getIntent();
        if (intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE)!=null){
            strUserId = intent.getStringExtra("UserId");
        }
        rlMain = findViewById(R.id.rlMain);
        llMobileNo = findViewById(R.id.ll_mobile_no);

        tvResendOtp = findViewById(R.id.tv_resendOtp);
        tvVerificationMobileNo = findViewById(R.id.tv_verification_mobileNo);
        tvVerificationMobileNo = findViewById(R.id.tv_verification_mobileNo);


        frstotp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length()>0) {
                    secondotp.requestFocus();
                }
            }
        });

        secondotp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length()>0) {
                    thirdotp.requestFocus();
                }else {
                    frstotp.requestFocus();
                }

            }
        });

        thirdotp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length()>0) {
                    fourthotp.requestFocus();
                }else {
                    secondotp.requestFocus();
                }
            }
        });


        fourthotp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length()==0) {
                    thirdotp.requestFocus();
                }
            }
        });


        setValue();

    }


    private void setValue() {

        tvVerificationMobileNo.setText(intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE));


        findViewById(R.id.btn_Verify).setOnClickListener(this);
        tvResendOtp.setOnClickListener(this);

    }


//    private EditText getEtOtp() {
//        return (EditText) findViewById(R.id.et_otp);
//    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Verify:
                //TODO implement


                if (clickFlage) {

                    clickFlage = false;
                    checkValidation();

                }
                break;

            case R.id.tv_resendOtp:
                //TODO implement


                resendOTP();

                break;


        }
    }

    private void resendOTP() {


            callOTPRegistrationAPI();



    }





    private void checkValidation() {


        if (frstotp.getText().toString().trim().isEmpty()) {
            ErrorLayout error = new ErrorLayout(this, frstotp.getRootView());
            error.showAlert(getResources().getString(R.string.error_please_enter_otp), ErrorLayout.MsgType.Error, true);
            return;

        }

        if (secondotp.getText().toString().trim().isEmpty()) {
            ErrorLayout error = new ErrorLayout(this, secondotp.getRootView());
            error.showAlert(getResources().getString(R.string.error_please_enter_otp), ErrorLayout.MsgType.Error, true);
            return;

        }

        if (thirdotp.getText().toString().trim().isEmpty()) {
            ErrorLayout error = new ErrorLayout(this, thirdotp.getRootView());
            error.showAlert(getResources().getString(R.string.error_please_enter_otp), ErrorLayout.MsgType.Error, true);
            return;

        }

        if (fourthotp.getText().toString().trim().isEmpty()) {
            ErrorLayout error = new ErrorLayout(this, fourthotp.getRootView());
            error.showAlert(getResources().getString(R.string.error_please_enter_otp), ErrorLayout.MsgType.Error, true);
            return;

        }
        String otp = frstotp.getText().toString().trim().concat(secondotp.getText().toString().trim()).concat(thirdotp.getText().toString().trim()).concat(fourthotp.getText().toString().trim());


        if (intent.getStringExtra(AppConstants.KEY_TYPE).trim().equalsIgnoreCase(AppConstants.KEY_VALUE_LOGIN)) {


            callLoginAPI(otp);
        } else {
            callRegistrationAPI(otp);
        }


    }

    private void callOTPRegistrationAPI() {


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();

            jdata.put(AppConstants.KEY_MOBILE, intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE));
            jdata.put(AppConstants.KEY_USER_ID, intent.getStringExtra(AppConstants.DRIVE_USER_ID));

            Log.e("Send Registration", "Registration: " + AppConstants.API_VERIFICATION + "  :: " + jdata);

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_VERIFICATION, jdata.toString()), this, AppConstants.EVENT_API_VERIFICATION);
            clickFlage = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void callLoginAPI(String otp) {


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();


            jdata.put(AppConstants.KEY_TYPE, intent.getStringExtra(AppConstants.KEY_TYPE));
            jdata.put(AppConstants.KEY_USER_ID, intent.getStringExtra(AppConstants.KEY_USER_ID));
            jdata.put(AppConstants.KEY_VALUE_MOBILE, intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE));
            jdata.put(AppConstants.KEY_OTP, otp);
            jdata.put(AppConstants.KEY_USER_ROLE, "2");


            Log.e("Send Registration", "Registration: " + AppConstants.API_LOGIN + "  :: " + jdata.toString());

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_LOGIN, jdata.toString()), this, AppConstants.EVENT_API_LOGIN);
            clickFlage = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void callRegistrationAPI(String otp) {


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_TYPE, intent.getStringExtra(AppConstants.KEY_TYPE));
            hashMap.put(AppConstants.KEY_USER_ID, intent.getStringExtra(AppConstants.KEY_USER_ID));
            hashMap.put(AppConstants.KEY_VALUE_FIRST_NAME, intent.getStringExtra(AppConstants.KEY_VALUE_FIRST_NAME));
//            hashMap.put(AppConstants.KEY_VALUE_LAST_NAME, intent.getStringExtra(AppConstants.KEY_VALUE_LAST_NAME));
            hashMap.put(AppConstants.KEY_VALUE_MOBILE, intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE));
            hashMap.put(AppConstants.KEY_VALUE_VEHICLE_NO, intent.getStringExtra(AppConstants.KEY_VALUE_VEHICLE_NO));
            hashMap.put(AppConstants.KEY_VALUE_VEHICLE_TYPE, intent.getStringExtra(AppConstants.KEY_VALUE_VEHICLE_TYPE));
            hashMap.put(AppConstants.KEY_VALUE_LOCATION, intent.getStringExtra(AppConstants.KEY_VALUE_LOCATION));
            hashMap.put(AppConstants.KEY_VALUE_ABOUT_US, intent.getStringExtra(AppConstants.KEY_VALUE_ABOUT_US));
            hashMap.put(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH, intent.getStringExtra(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH));
            hashMap.put(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH, intent.getStringExtra(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH));


            hashMap.put(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE, intent.getStringExtra(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE));
            hashMap.put(AppConstants.KEY_VALUE_INSURANCE, intent.getStringExtra(AppConstants.KEY_VALUE_INSURANCE));
            hashMap.put(AppConstants.KEY_VALUE_PERMIT, intent.getStringExtra(AppConstants.KEY_VALUE_PERMIT));
            hashMap.put(AppConstants.PROFILE_IMAGE, intent.getStringExtra(AppConstants.PROFILE_IMAGE));




            hashMap.put(AppConstants.KEY_VEHICLE_NAME, intent.getStringExtra(AppConstants.KEY_VEHICLE_NAME));

            hashMap.put(AppConstants.KEY_VALUE_DAILY, intent.getStringExtra(AppConstants.KEY_VALUE_DAILY));
            hashMap.put(AppConstants.KEY_VALUE_RENTAL, intent.getStringExtra(AppConstants.KEY_VALUE_RENTAL));
            hashMap.put(AppConstants.KEY_VALUE_OUTSTATION, intent.getStringExtra(AppConstants.KEY_VALUE_OUTSTATION));


            hashMap.put(AppConstants.KEY_OTP, otp.trim());
            hashMap.put(AppConstants.KEY_USER_ROLE, "2");


            Log.e("Send Registration", "Registration: " + AppConstants.API_REGISTRATION + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.API_REGISTRATION, hashMap, 1), this, AppConstants.EVENT_API_REGISTRATION);

            clickFlage = true;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        clickFlage  = true;
       /* LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter("otp"));*/
    }

    @Override
    protected void onDestroy() {

        //LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {
        Log.e("Response", "Response: " + response);
        clickFlage = true;
        try {

            if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_UPLOAD_PROFILE_IMAGE)) {

                switchActivityRegistration(ThankYouActivityActivity.class);

            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_REGISTRATION)) {

                uploadProfileImage();

            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_LOGIN)) {


                LoginModel loginBeen = new Gson().fromJson(response.toString(), LoginModel.class);

                AppPreference.saveStringPref(this, AppPreference.KEY_USER_ID, loginBeen.getResult().getUser_id());
                AppPreference.saveStringPref(this, AppPreference.KEY_TOKEN, "" + loginBeen.getResult().getToken());

                AppPreference.saveStringPref(this, AppPreference.KEY_NAME, "" + loginBeen.getResult().getFirst_name() + " " + loginBeen.getResult().getLast_name());
                AppPreference.saveStringPref(this, AppPreference.KEY_MOBILE, "" + loginBeen.getResult().getMobile());
                AppPreference.saveStringPref(this, AppPreference.KEY_PROFILE_IMAGE, "" + loginBeen.getResult().getProfile_img());

                if (AppPreference.loadStringPref(ActivityDriverVerificationActivity.this, AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase("")) {
                    AppPreference.saveStringPref(this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);
                }


                switchActivityRegistration(HomeMenuActivity.class);

            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_MOBLINE_NUMBER_CHECK)) {

                Toast.makeText(ActivityDriverVerificationActivity.this, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_LONG).show();


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_VERIFICATION)) {


                Toast.makeText(ActivityDriverVerificationActivity.this, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_LONG).show();

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

    private void uploadProfileImage(){


        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_USER_ID, intent.getStringExtra(AppConstants.KEY_USER_ID));
            hashMap.put(AppConstants.PROFILE_IMAGE, intent.getStringExtra(AppConstants.PROFILE_IMAGE));

            Log.e("Send Registration", "Registration: " + AppConstants.API_REGISTRATION + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.API_UPLOAD_PROFILE_IMAGE, hashMap, 1), this, AppConstants.EVENT_API_UPLOAD_PROFILE_IMAGE);

            clickFlage = true;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void switchActivity(Class targetClass) {

        startActivity(new Intent(this, targetClass));

    }

    private void switchActivityRegistration(Class targetClass) {

        startActivity(new Intent(this, targetClass)
                .putExtra(AppConstants.KEY_MOBILE, tvVerificationMobileNo.getText().toString()));
        finishAffinity();

    }

   /* private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");

//                Log.e("dfgdfgdf", "dfdsfd: "+message);
//
//                getEtOtp().setText(message.trim());
//
//
//
//                findViewById(R.id.btn_Verify).callOnClick();
//
//                getEtOtp().setSelection(getEtOtp().getText().toString().trim().length());

                if (message.length() == 4) {

                    String first = String.valueOf(message.charAt(0));
                    String second = String.valueOf(message.charAt(1));
                    String third = String.valueOf(message.charAt(2));
                    String fourth = String.valueOf(message.charAt(3));

                    frstotp.setText(first);
                    secondotp.setText(second);
                    thirdotp.setText(third);
                    fourthotp.setText(fourth);

                    checkValidation();

                }

            }
        }
    };
*/

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


