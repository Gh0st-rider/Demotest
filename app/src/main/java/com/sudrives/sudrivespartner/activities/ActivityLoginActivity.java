package com.sudrives.sudrivespartner.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.CountDownTimer;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.networks.MySMSBroadCastReceiver;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.ErrorLayout;
import com.sudrives.sudrivespartner.utils.Validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.regex.Pattern;


public class ActivityLoginActivity extends Activity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    private RelativeLayout rlTop;
    private TextView tvWelcomeToHaulage;
    private LinearLayout llMobileNo;
    private TextView tvByLoggingInYouAreAgreeingToOur;
    private WebView webView;
    String title = "";
    private Dialog teamsDialog;
    private boolean isopenRegistrationPage = true;
    private LocationManager locationManager;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS

    };
    private static final int INITIAL_REQUEST = 222;
    // private include errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        String language = AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE);

        Log.e("Lanugaue", "dsfdsf" + language);

        if (language.equalsIgnoreCase(AppConstants.KEY_VALUE_ENGLISH)) {
            AppPreference.saveStringPref(ActivityLoginActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_ENGLISH);
            setLocale("en");

        } else {
            AppPreference.saveStringPref(ActivityLoginActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);
            setLocale("hi");

        }


        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        getFireBaseTokenId();

        init();
        enableBroadcastReceiver();
        // makeTermsLink();

        //  errorLayout = (include) findViewById(R.id.error_layout);
    }

    private void init() {

        rlTop = findViewById(R.id.rl_top);
        tvWelcomeToHaulage = findViewById(R.id.tv_welcome_to_haulage);
        llMobileNo = findViewById(R.id.ll_mobile_no);
//        tvByLoggingInYouAreAgreeingToOur = findViewById(R.id.tv_by_logging_in_you_are_agreeing_to_our);


        setValue();

    }
    public void enableBroadcastReceiver()
    {

        ComponentName receiver = new ComponentName(this, MySMSBroadCastReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        // Toast.makeText(this, "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
    }

    private void setValue() {

        findViewById(R.id.btn_login).setOnClickListener(this);


/*

        getEtMobileNo().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if ((""+s).trim().length()>0){
                    getEtMobileNo().setText("+91"+s);
                }else if ((""+s).trim().length()==3){
                    getEtMobileNo().setText("");
                }


            }
        });
*/

    }


    private EditText getEtMobileNo() {
        return (EditText) findViewById(R.id.et_mobile_no);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //TODO implement


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (!canAccessExternalStorage() || !canAccessCoarseLocation() || !canAccessFineLocation() ) {
                        ActivityCompat.requestPermissions(this, INITIAL_PERMS, INITIAL_REQUEST);
                    } else {
                        checkValidations();
                    }
                } else {
                    checkValidations();
                }


                break;
        }
    }


    private void checkValidations() {

        ErrorLayout error = new ErrorLayout(this, getEtMobileNo().getRootView());

        if (getEtMobileNo().getText().toString().trim().isEmpty()) {

            error.showAlert(getResources().getString(R.string.error_please_enter_mobile_number), ErrorLayout.MsgType.Error, true);
            return;
        }


        if (!Validations.isValidMobile(getEtMobileNo().getText().toString().trim())) {


            error.showAlert(getResources().getString(R.string.error_please_enter_valid_mobile_number), ErrorLayout.MsgType.Error, true);
            return;
        }

        if (checkMobileNumberIsValidOrNot(getEtMobileNo().getText().toString().trim().replaceAll(Pattern.quote("+"), ""))) {


           // if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {


               /* Intent intent = new Intent(ActivityLoginActivity.this, ActivityVerificationForFCMActivity.class);
                intent.putExtra(AppConstants.KEY_VALUE_MOBILE, getEtMobileNo().getText().toString().trim());
                startActivity(intent);*/

                callLoginAPI();
            }


        } else {
            Toast.makeText(this, getString(R.string.error_please_enter_valid_mobile_number), Toast.LENGTH_SHORT).show();
        }


    }


    private void callLoginAPI() {

        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();
            jdata.put(AppConstants.KEY_MOBILE, getEtMobileNo().getText().toString().trim());
            jdata.put(AppConstants.KEY_USER_ROLE, "2");

            Log.e("Send Response", "Send Response: " + AppConstants.API_MOBLINE_NUMBER_CHECK + " :: " + jdata);

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_MOBLINE_NUMBER_CHECK, jdata.toString()), this, AppConstants.EVENT_API_MOBLINE_NUMBER_CHECK);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        isopenRegistrationPage = true;
    }

    @Override
    public void onResponse(final JSONObject response, String strEventType) {

        Log.e("ResponseLogin", "Response: " + response);

        try {

            if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_MOBLINE_NUMBER_CHECK)) {


                if (response.getInt(AppConstants.KEY_ERROR_CODE) == 0 || response.getInt(AppConstants.KEY_ERROR_CODE) == 2 || response.getInt(AppConstants.KEY_ERROR_CODE) == 3) {
                    switchActivity(ActivityVerificationForFCMActivity.class, response.getString(AppConstants.KEY_USER_ID), AppConstants.KEY_VALUE_REGISTER);
                }else if (response.getInt(AppConstants.KEY_ERROR_CODE) == 1) {
                    if (isopenRegistrationPage) {
                        isopenRegistrationPage = false;
                        switchActivity(ActivityVerificationForFCMActivity.class, response.getString(AppConstants.KEY_USER_ID), AppConstants.KEY_VALUE_LOGIN);
                    }
                }
            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_ABOUT_US)) {
                if (response.optInt("status") == 1) {
                    String description = response.optJSONObject("result").getString("discription");
                    loadWebPage(description);
                } else {
                    Toast.makeText(this, response.getString(getString(R.string.message)), Toast.LENGTH_SHORT).show();
                }
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

    private void switchActivityRegistration(Class targetClass) {

        startActivity(new Intent(this, targetClass).putExtra(AppConstants.KEY_MOBILE, getEtMobileNo().getText().toString().trim()));

    }

    private void switchActivity(Class targetClass, String userId, String type) {

        startActivity(new Intent(this, targetClass)
                .putExtra(AppConstants.KEY_MOBILE, getEtMobileNo().getText().toString().trim())
                .putExtra(AppConstants.KEY_USER_ID, userId)
                .putExtra(AppConstants.KEY_TYPE, type)

        );

    }

    private void switchActivity(Class targetClass, String userId) {

        startActivity(new Intent(this, targetClass)
                .putExtra(AppConstants.KEY_MOBILE, getEtMobileNo().getText().toString().trim())
                .putExtra(AppConstants.KEY_USER_ID, userId)


        );

    }


    private void getFireBaseTokenId() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log.e(TAG, "getInstanceId failed : " + token);

                        AppPreference.saveStringPref(ActivityLoginActivity.this, AppPreference.KEY_FCM_TOKEN, token);

                        // Log.d(TAG, token);
                    }
                });


    }

//    private void makeTermsLink() {
//        SpannableString ss = new SpannableString(getString(R.string.by_logging_in_you_are_agreed_to_our));
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
//
//                // Toast.makeText(ActivityLoginActivity.this, "Terms", Toast.LENGTH_SHORT).show();
//
//                try {
//
//                    title = getResources().getString(R.string.terms_and_condotion);
//
//                    Log.e("RRRR", "FFFFF: " + title);
//
//                    getTermsAndDriveWithUs("term-and-condition-driver");
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                //  startActivity(new Intent(LogInActivity.this, TermsAndConditionAndPrivacyActivity.class));
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//
//        };
//
//        ClickableSpan clickableSpan1 = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
//
//                // Toast.makeText(ActivityLoginActivity.this, "Privacy", Toast.LENGTH_SHORT).show();
//                //  startActivity(new Intent(LogInActivity.this, TermsAndConditionAndPrivacyActivity.class));
//
//                try {
//                    title = getResources().getString(R.string.privacy_policy);
//                    Log.e("RRRR", "FFFFF: " + title);
//
//                    getTermsAndDriveWithUs("privacy-policies-driver");
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//
//        };
//
//        if (AppPreference.loadStringPref(ActivityLoginActivity.this, AppPreference.KEY_LANGUAGE).equalsIgnoreCase(AppConstants.KEY_VALUE_ENGLISH)) {
//
//            ss.setSpan(clickableSpan, 37, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            ss.setSpan(clickableSpan1, 59, 73, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            //  ss.setSpan(new UnderlineSpan(), 31, 70, 0);
//
//
//            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 37, 54, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 59, 73, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//        } else {
//
//            ss.setSpan(clickableSpan, 21, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            ss.setSpan(clickableSpan1, 42, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            //  ss.setSpan(new UnderlineSpan(), 31, 70, 0);
//
//
//            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 21, 37, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 42, 56, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//        }
//
//
//        TextView textView = findViewById(R.id.tv_by_logging_in_you_are_agreeing_to_our);
//        textView.setText(ss);
//        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        textView.setHighlightColor(Color.TRANSPARENT);
//    }

    private void showTermsAndPrivactDialog(String data) {


        Log.e("dfgdfgdfg", "fgdfg: " + data);
        if (teamsDialog != null && teamsDialog.isShowing()) {

            teamsDialog.dismiss();
        }


        teamsDialog = new Dialog(this, R.style.DialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.terms_view, null);
        teamsDialog.setContentView(view);


//        teamsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        teamsDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);


        TextView tvTitle = teamsDialog.findViewById(R.id.tv_title);

        tvTitle.setText(title);

        ImageButton cancel = teamsDialog.findViewById(R.id.ib_cancel);
        TextView webView = teamsDialog.findViewById(R.id.wv_terms_or_privacy);

        // webView.loadData(data, "text/html", "UTF-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            webView.setText(Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT));
        } else {
            webView.setText(Html.fromHtml(data));
        }



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  webView = null;
                teamsDialog.dismiss();

            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(teamsDialog.getWindow().getAttributes());
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        teamsDialog.getWindow().setAttributes(lp);
        teamsDialog.show();

    }


    private void getTermsAndDriveWithUs(String pageName) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.KEY_PAGE_NAME, pageName);

        NetworkConn network = NetworkConn.getInstance(this);

        network.makeRequest(this, network.createRawDataRequest(AppConstants.ABOUT_US, jsonObject.toString()), this, AppConstants.EVENT_ABOUT_US);

    }

    private void loadWebPage(String data) {

        showTermsAndPrivactDialog(data);


    }

    public void setLocale(String lang) {


        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);


    }

//    private boolean checkMobileNumberIsValidOrNot (String number) {
//
//        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
//        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt("91"));
//        Phonenumber.PhoneNumber phoneNumber = null;
//        try {
//            phoneNumber = phoneNumberUtil.parse(number, "IN");  //if you want to pass region code
//           // phoneNumber = phoneNumberUtil.parse(number, isoCode);
//        } catch (NumberParseException e) {
//            System.err.println(e);
//        }
//
//        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
//        if (isValid) {
//            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
//            Toast.makeText(this, "Phone Number is Valid " , Toast.LENGTH_LONG).show();
//            return true;
//        } else {
//            Toast.makeText(this, "Phone Number is Invalid " , Toast.LENGTH_LONG).show();
//            return false;
//        }
//
//
//    }

    private boolean checkMobileNumberIsValidOrNot(String number) {

        String firstNumber = String.valueOf(number.charAt(0));

        int digitnumber = Integer.parseInt(firstNumber);

        return digitnumber >= 6;
    }

    private boolean canAccessExternalStorage() {
        return (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean canAccessReadReceiveSMS() {
        return (hasPermission(Manifest.permission.READ_SMS) && hasPermission(Manifest.permission.RECEIVE_SMS));
    }


    private boolean canAccessFineLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean canAccessCoarseLocation() {
        return (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case INITIAL_REQUEST: {

                boolean value = false;

                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        value = true;
                    }
                }

                if (value) {
                    checkValidations();
                }

            }
            break;

        }
    }


}
