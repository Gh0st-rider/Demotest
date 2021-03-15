package com.sudrives.sudrivespartner.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.fragments.ContactUsFragment;
import com.sudrives.sudrivespartner.utils.AppConstants;

import java.util.Locale;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView bagecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        findViewById(R.id.ib_back_button).setOnClickListener(this);
        findViewById(R.id.iv_notification).setOnClickListener(this);

        bagecount = findViewById(R.id.tv_notification_Count);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ContactUsFragment()).commit();

        if(TextUtils.isEmpty(AppConstants.notificationCount)){

            bagecount.setVisibility(View.GONE);

        }else {

            bagecount.setVisibility(View.VISIBLE);

            bagecount.setText(AppConstants.notificationCount);

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ib_back_button:
                finish();
                break;
            case R.id.iv_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppConstants.KEY_CONTEXT = ContactUsActivity.this;
        if(TextUtils.isEmpty(AppConstants.notificationCount)){

            bagecount.setVisibility(View.GONE);

        }else {

            bagecount.setVisibility(View.VISIBLE);

            bagecount.setText(AppConstants.notificationCount);

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
}
