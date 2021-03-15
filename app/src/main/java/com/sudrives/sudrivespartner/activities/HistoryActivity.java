package com.sudrives.sudrivespartner.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.fragments.HistoryFragment;
import com.sudrives.sudrivespartner.utils.AppConstants;

import java.util.Locale;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{


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
        setContentView(R.layout.activity_history);


        findViewById(R.id.ib_back_button).setOnClickListener(this);
        findViewById(R.id.ib_notification).setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HistoryFragment()).commit();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ib_back_button:
                finish();
                break;
            case R.id.ib_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConstants.KEY_CONTEXT = HistoryActivity.this;

    }
}
