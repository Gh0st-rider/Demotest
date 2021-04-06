package com.sudrives.sudrivespartner.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class ActivityVerificationActivity extends Activity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    private RelativeLayout rlMain;
    private LinearLayout llMobileNo;
    private TextView tvResendOtp, tv_resendOtpTimer;
    private TextView tvVerificationMobileNo;
    private Intent intent;
    private boolean clickFlage = true;

    CountDownTimer countDownTimer;
    private EditText frstotp, secondotp, thirdotp, fourthotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_verification_activity);

        frstotp = findViewById(R.id.et_otp);
        secondotp = findViewById(R.id.et_otp1);
        thirdotp = findViewById(R.id.et_otp2);
        fourthotp = findViewById(R.id.et_otp3);


        // Hello world

        intent = getIntent();


        rlMain = findViewById(R.id.rlMain);
        llMobileNo = findViewById(R.id.ll_mobile_no);
        tv_resendOtpTimer = findViewById(R.id.tv_resendOtpTimer);
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
                if (editable.toString().length() > 0) {
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
                if (editable.toString().length() > 0) {
                    thirdotp.requestFocus();
                } else {
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

                if (editable.toString().length() > 0) {
                    fourthotp.requestFocus();
                } else {
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
                if (editable.toString().length() == 0) {
                    thirdotp.requestFocus();
                }
            }
        });



        // Get an instance of SmsRetrieverClient, used to start listening for a matching
// SMS message.
        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);

// Starts SmsRetriever, which waits for ONE matching SMS message until timeout
// (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
// action SmsRetriever#SMS_RETRIEVED_ACTION.
        Task<Void> task = client.startSmsRetriever();

// Listen for success/failure of the start Task. If in a background thread, this
// can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Successfully started retriever, expect broadcast intent
                // ...
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // Failed to start retriever, inspect Exception for more details
                // ...
            }
        });


        setValue();

        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_resendOtpTimer.setText("Resend in: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvResendOtp.setEnabled(true);
                tvResendOtp.setTextColor(ContextCompat.getColor(ActivityVerificationActivity.this, R.color.black));
            }

        }.start();

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

        if (intent.getStringExtra(AppConstants.KEY_TYPE).trim().equalsIgnoreCase(AppConstants.KEY_VALUE_LOGIN)) {

            callOTPLoginAPI();
        } else {
            callOTPRegistrationAPI();
        }


    }


    private void callOTPLoginAPI() {

        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();
            jdata.put(AppConstants.KEY_MOBILE, intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE));
            jdata.put(AppConstants.KEY_USER_ROLE, "2");

            Log.e("Send Response", "Send Response: " + AppConstants.API_MOBLINE_NUMBER_CHECK + " :: " + jdata);

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_MOBLINE_NUMBER_CHECK, jdata.toString()), this, AppConstants.EVENT_API_MOBLINE_NUMBER_CHECK);
            clickFlage = true;
        } catch (Exception e) {
            e.printStackTrace();
        }


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
            jdata.put(AppConstants.KEY_USER_ID, intent.getStringExtra(AppConstants.KEY_USER_ID));

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
/*

            {
                "userid":"178",
                    "mobile":"9855666655",
                    "otp":"2109"
            }
*/

            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject hashMap = new JSONObject();

            hashMap.put(AppConstants.KEY_TYPE, intent.getStringExtra(AppConstants.KEY_TYPE));
            hashMap.put(AppConstants.KEY_USER_ID, intent.getStringExtra(AppConstants.KEY_USER_ID));
            hashMap.put(AppConstants.KEY_VALUE_MOBILE, intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE));
            hashMap.put(AppConstants.KEY_OTP, otp.trim());
            hashMap.put(AppConstants.KEY_USER_ROLE, "2");


            Log.e("Send Registration", "Registration: " + AppConstants.API_OTP_VERIFY_NEW + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_OTP_VERIFY_NEW, hashMap.toString()), this, AppConstants.EVENT_API_OTP_VERIFY_NEW);

            clickFlage = true;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        clickFlage = true;
        LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter("otp"));
    }

    @Override
    protected void onDestroy() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
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
                AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 1);
                if (AppPreference.loadStringPref(ActivityVerificationActivity.this, AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase("")) {
                    AppPreference.saveStringPref(this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);
                }


                if (loginBeen.getError_code().trim().equalsIgnoreCase("4")) {


                    AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 2);

                    showPopup(loginBeen.getMessage());

                } else if (loginBeen.getError_code().trim().equalsIgnoreCase("2")) {

                    switchActivityRegistration(RegistrationActivityActivity.class, loginBeen.getError_code().trim(), loginBeen);
                    AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 2);
                } else if (loginBeen.getError_code().trim().equalsIgnoreCase("3")) {
                    Intent intent = new Intent(this, ContentUploadDocumentActivity.class);
                    intent.putExtra(AppConstants.KEY_MOBILE, loginBeen.getResult().getMobile());
                    intent.putExtra(AppConstants.KEY_USER_ID, loginBeen.getResult().getUser_id());
                    startActivity(intent);
                } else {

                    switchActivityRegistration(HomeMenuActivity.class);
                }


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_OTP_VERIFY_NEW) || strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_CHANGE_MODE_TO_DRIVER)) {


                LoginModel loginBeen = new Gson().fromJson(response.toString(), LoginModel.class);


                AppPreference.saveStringPref(this, AppPreference.KEY_USER_ID, loginBeen.getResult().getUser_id());
                AppPreference.saveStringPref(this, AppPreference.KEY_TOKEN, "" + loginBeen.getResult().getToken());

                AppPreference.saveStringPref(this, AppPreference.KEY_NAME, "" + loginBeen.getResult().getFirst_name() + " " + loginBeen.getResult().getLast_name());
                AppPreference.saveStringPref(this, AppPreference.KEY_MOBILE, "" + loginBeen.getResult().getMobile());
                AppPreference.saveStringPref(this, AppPreference.KEY_PROFILE_IMAGE, "" + loginBeen.getResult().getProfile_img());


                if (loginBeen.getError_code().trim().equalsIgnoreCase("4")) {


                    AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 2);

                    showPopup(loginBeen.getMessage());

                } else if (loginBeen.getError_code().trim().equalsIgnoreCase("2")) {
                    switchActivityRegistration(RegistrationActivityActivity.class, loginBeen.getError_code().trim(), loginBeen);
                    AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 2);
                } else if (loginBeen.getError_code().trim().equalsIgnoreCase("3")) {
                    AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 3);

                    Intent intent = new Intent(this, ContentUploadDocumentActivity.class);
                    intent.putExtra(AppConstants.KEY_MOBILE, loginBeen.getResult().getMobile());
                    intent.putExtra(AppConstants.KEY_USER_ID, loginBeen.getResult().getUser_id());
                    startActivity(intent);

                } else {

                    AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 0);


                    AppPreference.saveStringPref(this, AppPreference.KEY_USER_ID, loginBeen.getResult().getUser_id());
                    AppPreference.saveStringPref(this, AppPreference.KEY_TOKEN, "" + loginBeen.getResult().getToken());

                    AppPreference.saveStringPref(this, AppPreference.KEY_NAME, "" + loginBeen.getResult().getFirst_name() + " " + loginBeen.getResult().getLast_name());
                    AppPreference.saveStringPref(this, AppPreference.KEY_MOBILE, "" + loginBeen.getResult().getMobile());
                    AppPreference.saveStringPref(this, AppPreference.KEY_PROFILE_IMAGE, "" + loginBeen.getResult().getProfile_img());
                    AppPreference.saveStringPref(this, AppPreference.KEY_ERROR_CODE, "" + 1);

                    switchActivityRegistration(HomeMenuActivity.class);
                }


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_MOBLINE_NUMBER_CHECK)) {

                Toast.makeText(ActivityVerificationActivity.this, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_LONG).show();


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_VERIFICATION)) {


                Toast.makeText(ActivityVerificationActivity.this, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_LONG).show();

            }


        } catch (Exception e) {

            Log.e("dfdsf", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }

    private void uploadProfileImage() {


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

        startActivity(new Intent(this, targetClass));
        finishAffinity();

    }


    private void switchActivityRegistration(Class targetClass, String type, LoginModel loginBeen) {

        Intent intent = null;

        if (type.trim().equalsIgnoreCase("2")) {


            Log.e("dfgdfg", "ffffffF: " + loginBeen.getResult().getUser_id() + " kkkkk :" + loginBeen.getResult().getUserid());

            intent = new Intent(this, targetClass);
            intent.putExtra(AppConstants.KEY_MOBILE, loginBeen.getResult().getMobile());
            intent.putExtra(AppConstants.KEY_USER_ID, loginBeen.getResult().getUser_id());

            Log.e("dfgdfg", "ffffffF: " + loginBeen.getResult().getUser_id() + " kkkkk :" + loginBeen.getResult().getUserid());


            startActivity(intent);


        } else {

        }


    }


    private void showPopup(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new androidx.appcompat.app.AlertDialog.Builder(ActivityVerificationActivity.this).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        changeModeToDriver();

                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle(getString(R.string.change_mode_to_driver))
                        .setMessage(message)
                        .setCancelable(false)
                        .show();

            }
        });

    }

    private void changeModeToDriver() {


        try {

            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject hashMap = new JSONObject();
            hashMap.put(AppConstants.KEY_USER_ID, intent.getStringExtra(AppConstants.KEY_USER_ID));
            Log.e("Send Registration", "Registration: " + AppConstants.API_OTP_VERIFY_NEW + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_CHANGE_MODE_TO_DRIVER, hashMap.toString()), this, AppConstants.EVENT_API_CHANGE_MODE_TO_DRIVER);

            clickFlage = true;

        } catch (Exception e) {
            e.printStackTrace();
        }


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


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                Log.e("dfgdfgdf", "dfdsfd: " + message);

                if (message.length() >= 4) {
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

    @Override
    protected void onPause() {
        super.onPause();
    }
}


