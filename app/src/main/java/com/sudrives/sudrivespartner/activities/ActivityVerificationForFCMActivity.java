package com.sudrives.sudrivespartner.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;

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

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
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
import java.util.concurrent.TimeUnit;

public class ActivityVerificationForFCMActivity extends Activity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    private RelativeLayout rlMain;
    private LinearLayout llMobileNo;
    private TextView tvResendOtp;
    private TextView tvVerificationMobileNo, tv_resendOtpTimer;
    private Intent intent;
    private boolean clickFlage = true;
    //firebase auth object
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private EditText frstotp, secondotp, thirdotp, fourthotp, fifthotp, sixotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_verification_activity);

        mAuth = FirebaseAuth.getInstance();

        frstotp = findViewById(R.id.et_otp);
        secondotp = findViewById(R.id.et_otp1);
        thirdotp = findViewById(R.id.et_otp2);
        fourthotp = findViewById(R.id.et_otp3);
        fifthotp = findViewById(R.id.et_otp4);
        sixotp = findViewById(R.id.et_otp5);


        // Hello world

        intent = getIntent();


        rlMain = findViewById(R.id.rlMain);
        llMobileNo = findViewById(R.id.ll_mobile_no);

        tvResendOtp = findViewById(R.id.tv_resendOtp);
        tvVerificationMobileNo = findViewById(R.id.tv_verification_mobileNo);
        tvVerificationMobileNo = findViewById(R.id.tv_verification_mobileNo);
        tv_resendOtpTimer = findViewById(R.id.tv_resendOtpTimer);


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
                if (editable.toString().length() > 0) {
                    fifthotp.requestFocus();
                } else {
                    thirdotp.requestFocus();
                }
            }
        });


        fifthotp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (editable.toString().length() > 0) {
                    sixotp.requestFocus();
                } else {
                    fourthotp.requestFocus();
                }
            }
        });


        sixotp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0) {
                    fifthotp.requestFocus();
                }
            }
        });


        setValue();

    }


    private void setValue() {

        callSMSServise();


        tvVerificationMobileNo.setText("+91-" + intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE));

        findViewById(R.id.btn_Verify).setOnClickListener(this);
        tvResendOtp.setOnClickListener(this);

    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                Log.e("verification code", code);
                if (code.length() >= 4) {
                    String first = String.valueOf(code.charAt(0));
                    String second = String.valueOf(code.charAt(1));
                    String third = String.valueOf(code.charAt(2));
                    String fourth = String.valueOf(code.charAt(3));
                    String fifth = String.valueOf(code.charAt(4));
                    String six = String.valueOf(code.charAt(5));
                    frstotp.setText(first);
                    secondotp.setText(second);
                    thirdotp.setText(third);
                    fourthotp.setText(fourth);
                    fifthotp.setText(fifth);
                    sixotp.setText(six);

                    verifyVerificationCode(code);

                }


            }else {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("FirebaseException", e.getMessage());
            Toast.makeText(ActivityVerificationForFCMActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
            Toast.makeText(ActivityVerificationForFCMActivity.this, "OTP Sent", Toast.LENGTH_LONG).show();
            Log.e("mVerificationId", s);

            startTimer();

        }
    };


    private void startTimer() {
        tvResendOtp.setClickable(false);
        tvResendOtp.setEnabled(false);

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
//                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                tv_resendOtpTimer.setText(getString(R.string.send_sms_again_in) + " " + (millisUntilFinished / 1000) + " " + getString(R.string.sec));

            }

            public void onFinish() {
                //mTextField.setText("done!");
                tv_resendOtpTimer.setText("");
                tvResendOtp.setClickable(true);
                tvResendOtp.setEnabled(true);
            }
        }.start();
    }

    private void verifyVerificationCode(String code) {
        try {
            //creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //signing the user
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            clickFlage = true;
            Crashlytics.logException(e);
            Toast.makeText(this, R.string.wrong_otp_entered, Toast.LENGTH_SHORT).show();

        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ActivityVerificationForFCMActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                           /* Intent intent = new Intent(VerifyPhoneActivity.this, ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);*/
//                            Toast.makeText(ActivityVerificationForFCMActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            if (intent.getStringExtra(AppConstants.KEY_TYPE).trim().equalsIgnoreCase(AppConstants.KEY_VALUE_LOGIN)) {


                                callLoginAPI("4040");
                            } else {
                                callRegistrationAPI("4040");
                            }


                        } else {
                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            try {
                                clickFlage = true;
                                Toast.makeText(ActivityVerificationForFCMActivity.this, message, Toast.LENGTH_SHORT).show();
                               /* Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                                snackbar.setAction("Dismiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                                snackbar.show();*/
                            } catch (Exception e) {

                            }

                        }
                    }
                });
    }

    private void callSMSServise() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + intent.getStringExtra(AppConstants.KEY_VALUE_MOBILE),
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);


    }


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
                //resendOTP();
                callSMSServise();
                break;


        }
    }

    private void resendOTP() {

//        if (intent.getStringExtra(AppConstants.KEY_TYPE).trim().equalsIgnoreCase(AppConstants.KEY_VALUE_LOGIN)) {
//
//            callOTPLoginAPI();
//        } else {
//            callOTPRegistrationAPI();
//        }


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


        if (fifthotp.getText().toString().trim().isEmpty()) {
            ErrorLayout error = new ErrorLayout(this, fourthotp.getRootView());
            error.showAlert(getResources().getString(R.string.error_please_enter_otp), ErrorLayout.MsgType.Error, true);
            return;

        }


        if (sixotp.getText().toString().trim().isEmpty()) {
            ErrorLayout error = new ErrorLayout(this, fourthotp.getRootView());
            error.showAlert(getResources().getString(R.string.error_please_enter_otp), ErrorLayout.MsgType.Error, true);
            return;

        }


        String otp = frstotp.getText().toString().trim().concat(secondotp.getText().toString().trim()).concat(thirdotp.getText().toString().trim())
                .concat(fourthotp.getText().toString().trim()).concat(fifthotp.getText().toString().trim()).concat(sixotp.getText().toString().trim());


        verifyVerificationCode(otp);


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

    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {
        Log.e("LoginResponse", "Response: " + response);
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
                if (AppPreference.loadStringPref(ActivityVerificationForFCMActivity.this, AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase("")) {
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

                Toast.makeText(ActivityVerificationForFCMActivity.this, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_LONG).show();


            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_VERIFICATION)) {


                Toast.makeText(ActivityVerificationForFCMActivity.this, response.getString(AppConstants.KEY_MESSAGE), Toast.LENGTH_LONG).show();

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

                new androidx.appcompat.app.AlertDialog.Builder(ActivityVerificationForFCMActivity.this).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
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


    /*private BroadcastReceiver receiver = new BroadcastReceiver() {
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
    };*/

    @Override
    protected void onPause() {
        super.onPause();
    }
}


