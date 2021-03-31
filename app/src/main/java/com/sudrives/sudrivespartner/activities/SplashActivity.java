package com.sudrives.sudrivespartner.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.VideoView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.AppSignatureHashHelper;
import com.sudrives.sudrivespartner.utils.Methods;
import com.sudrives.sudrivespartner.utils.interfaces.OnLocationUpdateListner;

import org.json.JSONObject;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity implements OnLocationUpdateListner, MediaPlayer.OnPreparedListener, NetworkConn.OnRequestResponse {


    public final int TIMEOUT = 2000;
    private static final int INITIAL_REQUEST = 222;
    private Runnable runnable;
    private Handler handler;
    private AlertDialog alertDialog;
    RelativeLayout rel_allow;
    Button btn_permission;

    private String[] INITIAL_PERMS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE


    };
    private LocationManager locationManager;
    private String from = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        if (getIntent() != null) {

            from = getIntent().getStringExtra("from");

            if (from != null) {

                if (from.equalsIgnoreCase("home")) {
                    startActivity(new Intent(this, HomeMenuActivity.class));
                    finishAffinity();

                    return;
                }
            }
        }



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



        String language = AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE);

        String tttt = " <#> Your OTP code is: 8577 dH+Z9qprZyr";

        Log.e("Lanugaue", "fdsfds: " + AppConstants.SMS_KEY);
        Log.e("Lanugaue", "fdsfds: " + tttt.replace(AppConstants.SMS_KEY, ""));

     //   setLocale("en");

       /* if (language.equalsIgnoreCase(AppConstants.KEY_VALUE_ENGLISH)) {
            setLocale("en");

        } else {
            setLocale("hi");
            AppPreference.saveStringPref(SplashActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);
        }*/

        //playVideo();

        runnable = new Runnable() {
            @Override
            public void run() {
                setValue();
            }
        };
        // For Production Code is "dH+Z9qprZyr";
        String message="<#>Your AndroidHunt OTP is: 8686."+getHashCode(this);
        Log.e("fff","Hash Code For This App is  "+getHashCode(this)+"\n"+message);

        AppConstants.SMS_KEY = getHashCode(this).trim();

        Log.e("fff","Hash Code For This App is 1234  "+AppConstants.SMS_KEY);


      //  printHashKey(getApplicationContext());

        rel_allow = findViewById(R.id.rel_allow);
        btn_permission = findViewById(R.id.btn_permission);
        btn_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        init();


    }


    private void init() {

        startCheckPermissions();


    }


    private void startCheckPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canAccessExternalStorage() || !canAccessCoarseLocation() &&!canAccessFineLocation()) {
                Log.e("Ask Permission", "Ask Permission");
                this.requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
                //rel_allow.setVisibility(View.VISIBLE);

                return;
            }
            setHandler();

        } else {
            setHandler();
        }


    }


    private void setValue() {


        Intent intent = null;

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


            if (Methods.checkNetworkConnectivity(SplashActivity.this)) {

                //checkVersion();

                if (AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim().isEmpty()) {
                    switchActivity(ChooseLanguageActivity.class);
                }else if (!AppPreference.loadStringPref(this, AppPreference.KEY_USER_ID).trim().equalsIgnoreCase("")) {

                    if (AppPreference.loadStringPref(this, AppPreference.KEY_ERROR_CODE).trim().equalsIgnoreCase("3")){

                        intent = new Intent(this, ContentUploadDocumentActivity.class);
                        intent.putExtra(AppConstants.KEY_MOBILE, AppPreference.loadStringPref(this, AppPreference.KEY_MOBILE));
                        intent.putExtra(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(this, AppPreference.KEY_USER_ID));
                        startActivity(intent);

                    }else  if (AppPreference.loadStringPref(this, AppPreference.KEY_ERROR_CODE).trim().equalsIgnoreCase("2")){

                        switchActivity(ActivityLoginActivity.class);

                    }else {
                        switchActivity(HomeMenuActivity.class);
                    }

                } else {
                    switchActivity(ActivityLoginActivity.class);
                }


            }else {

                AppDialogs.noNetworkConnectionDialog(SplashActivity.this);
            }
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings

            showSettingsAlert();
        }

    }

    private void setHandler() {

        handler = new Handler();
        handler.postDelayed(runnable, TIMEOUT);


    }


    private void switchActivity(Class targetClass) {

        startActivity(new Intent(this, targetClass));
        finish();
    }

    private boolean canAccessExternalStorage() {
        return (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }


    private boolean canAccessFineLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean canAccessCoarseLocation() {
        return (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private boolean canAccessReadReceiveSMS() {
        return (hasPermission(Manifest.permission.READ_SMS) && hasPermission(Manifest.permission.RECEIVE_SMS));
    }


    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(SplashActivity.this, perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case INITIAL_REQUEST: {


                if (grantResults != null && grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        init();
                    }
                }



            /*    Log.e("Requesty Code", ""+requestCode);

                boolean value = false;

                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        value = true;
                    }
                }

                if (value) {
                    setValue();
                } else {
                    init();
                }*/
                break;
            }


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        handler.removeCallbacks(runnable);
    }


    @Override
    public void locationUpdate(Location location) {

    }

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
                setValue();
            }
        });
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void setLocale(String lang) {


        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        init();

    }



    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }


    private void checkVersion() {

        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            JSONObject jdata = new JSONObject();

            jdata.put(AppConstants.KEY_APP_VERSION, AppConstants.VERSION_CODE);

            Log.e("Send Response", "Send Response: " + AppConstants.API_CHECK_VERSION + " :: " + jdata);

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_CHECK_VERSION, jdata.toString()), this, AppConstants.EVENT_API_CHECK_VERSION);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onResponse(JSONObject response, String strEventType) {
        Log.e("Response", "Response: " + response);

        try {

                Intent intent = null;

                if (AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim().isEmpty()) {
                    switchActivity(ChooseLanguageActivity.class);
                }else if (!AppPreference.loadStringPref(this, AppPreference.KEY_USER_ID).trim().equalsIgnoreCase("")) {

                    if (AppPreference.loadStringPref(this, AppPreference.KEY_ERROR_CODE).trim().equalsIgnoreCase("3")){

                        intent = new Intent(this, ContentUploadDocumentActivity.class);
                        intent.putExtra(AppConstants.KEY_MOBILE, AppPreference.loadStringPref(this, AppPreference.KEY_MOBILE));
                        intent.putExtra(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(this, AppPreference.KEY_USER_ID));
                        startActivity(intent);

                    }else  if (AppPreference.loadStringPref(this, AppPreference.KEY_ERROR_CODE).trim().equalsIgnoreCase("2")){

                        switchActivity(ActivityLoginActivity.class);

                    }else {
                        switchActivity(HomeMenuActivity.class);
                    }

                } else {
                    switchActivity(ActivityLoginActivity.class);
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

    public static String getHashCode(Context context){
        AppSignatureHashHelper appSignature = new AppSignatureHashHelper(context);
        Log.e(" getAppSignatures ",""+appSignature.getAppSignatures());
        return appSignature.getAppSignatures().get(0);

    }

   /* public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e("printHashKeyHashKey" , hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("printHashKey()", String.valueOf(e));
        } catch (Exception e) {
            Log.e("printHashKey()", String.valueOf(e));
        }
    }*/
}
