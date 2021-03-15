package com.sudrives.sudrivespartner.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.fragments.CollectionsFragment;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.Methods;

import java.util.Locale;

public class CollectionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView bageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        findViewById(R.id.ib_back_button).setOnClickListener(this);
        findViewById(R.id.iv_notification).setOnClickListener(this);
        bageCount = findViewById(R.id.tv_notification_Count);

        if(TextUtils.isEmpty(AppConstants.notificationCount)){

            bageCount.setVisibility(View.GONE);
        }else {
            bageCount.setVisibility(View.VISIBLE);
            bageCount.setText(AppConstants.notificationCount);

        }


        getSupportFragmentManager().beginTransaction().replace(R.id.container,new CollectionsFragment()).commit();
        findViewById(R.id.ll_toolbarOptionFilter).setOnClickListener(this);

    }

    private void showFilterOption(View view) {

        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {


                try {


                    TextView tv_filter_weekily, tv_filter_daily, tv_filter_monthly, tv_filter_all;


                    final Dialog dialog = new Dialog(CollectionActivity.this, R.style.FilterDialogTheme);

                    LayoutInflater layoutInflater = (LayoutInflater) CollectionActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = layoutInflater.inflate(R.layout.filter_popup, null);


                    tv_filter_weekily = view.findViewById(R.id.tv_filter_weekily);
                    tv_filter_daily = view.findViewById(R.id.tv_filter_daily);
                    tv_filter_monthly = view.findViewById(R.id.tv_filter_monthly);
                    tv_filter_all = view.findViewById(R.id.tv_filter_all);


                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(view);
                    dialog.setCancelable(true);


                    tv_filter_weekily.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();
                            CollectionsFragment.mSubmitButoonCallback.onSubmitButtonSuccess("Weekly");


                        }
                    });

                    tv_filter_daily.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            CollectionsFragment.mSubmitButoonCallback.onSubmitButtonSuccess("Daily");

                        }
                    });

                    tv_filter_monthly.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            CollectionsFragment.mSubmitButoonCallback.onSubmitButtonSuccess("Monthly");

                        }
                    });

                    tv_filter_all.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            CollectionsFragment.mSubmitButoonCallback.onSubmitButtonSuccess("All");

                        }
                    });


                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = (Methods.getDeviceResolutions(CollectionActivity.this).widthPixels / 3);
                    lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.TOP | Gravity.RIGHT;


                    dialog.getWindow().setAttributes(lp);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();

                } catch (Exception e) {
                    Log.e("Exception", "showAcceptDialog: " + e.getMessage());

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_toolbarOptionFilter:
                showFilterOption(view);
                break;
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
        AppConstants.KEY_CONTEXT = CollectionActivity.this;
        if(TextUtils.isEmpty(AppConstants.notificationCount)){

            bageCount.setVisibility(View.GONE);

        }else {

            bageCount.setVisibility(View.VISIBLE);

            bageCount.setText(AppConstants.notificationCount);

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
