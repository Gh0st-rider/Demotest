package com.sudrives.sudrivespartner.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;

import java.util.Locale;

public class ChooseLanguageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_continue;
    private LinearLayout ll_english,ll_hindi;
    private ImageView iv_english, iv_hindi;
    private String strLanguage ="en";
    private TextView tv_title, tv_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);


        btn_continue = findViewById(R.id.btn_continue);
        ll_english = findViewById(R.id.ll_english);
        iv_english = findViewById(R.id.iv_english);
        ll_hindi = findViewById(R.id.ll_hindi);
        iv_hindi = findViewById(R.id.iv_hindi);
        tv_title = findViewById(R.id.tv_title);
        tv_select = findViewById(R.id.tv_select);

        ll_english.setOnClickListener(this);
        ll_hindi.setOnClickListener(this);
        btn_continue.setOnClickListener(this);

        setValue();
    }


    private void setValue(){

        btn_continue.setText(getString(R.string.continues1));
        tv_title.setText(getString(R.string.choose_language));
        tv_select.setText(getString(R.string.you_can_chnage_the_languge_later_from_menu_section));

        strLanguage ="en";
        AppPreference.saveStringPref(ChooseLanguageActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_ENGLISH);
        iv_english.setImageResource(R.drawable.ic_check_black_24dp);
        iv_hindi.setImageResource(R.drawable.ic_check_white_24dp);
        btn_continue.setBackgroundColor(getResources().getColor(R.color.dark_yellow));
        btn_continue.setEnabled(true);
        btn_continue.setClickable(true);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.ll_english:
                strLanguage ="en";
                setLocale(strLanguage);
                AppPreference.saveStringPref(ChooseLanguageActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_ENGLISH);
                iv_english.setImageResource(R.drawable.ic_check_black_24dp);
                iv_hindi.setImageResource(R.drawable.ic_check_white_24dp);
                btn_continue.setBackgroundColor(getResources().getColor(R.color.dark_yellow));
                btn_continue.setEnabled(true);
                btn_continue.setClickable(true);
                break;

             case R.id.ll_hindi:
                 strLanguage ="hi";
                 setLocale(strLanguage);
                 AppPreference.saveStringPref(ChooseLanguageActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);
                 iv_hindi.setImageResource(R.drawable.ic_check_black_24dp);
                 iv_english.setImageResource(R.drawable.ic_check_white_24dp);
                 btn_continue.setBackgroundColor(getResources().getColor(R.color.dark_yellow));
                 btn_continue.setEnabled(true);
                 btn_continue.setClickable(true);
                break;

             case R.id.btn_continue:
                 switchActivity(ActivityLoginActivity.class);
                break;


        }

    }

    public void setLocale(String lang) {


        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        setValue();
    }

    private void switchActivity(Class targetClass) {

        startActivity(new Intent(this, targetClass));
        finish();
    }
}
