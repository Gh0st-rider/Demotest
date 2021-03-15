package com.sudrives.sudrivespartner.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.utils.AppConstants;

import java.util.Locale;

public class ThankYouActivityActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvThankyouMessage;
    private TextView tvThankyouOk;
    private Intent intent;
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
        setContentView(R.layout.thank_you_activity);

        intent = getIntent();

        tvThankyouMessage = (TextView) findViewById(R.id.tv_thankyou_message);
        tvThankyouOk = (TextView) findViewById(R.id.tv_thankyou_ok);

        setValue();

    }


    private void setValue(){


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        }, 2000);


        tvThankyouMessage.setText(getResources().getString(R.string.registration_approval_will_be_sent_on_email)+" "+intent.getStringExtra(AppConstants.KEY_MOBILE));


        tvThankyouOk.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.tv_thankyou_ok:



                onBackPressed();

                break;
        }


    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();


        switchActivity(ActivityLoginActivity.class);


    }

    @SuppressLint("NewApi")
    private void switchActivity(Class targetClass) {

        startActivity(new Intent(this, targetClass));
        finishAffinity();

    }
}
