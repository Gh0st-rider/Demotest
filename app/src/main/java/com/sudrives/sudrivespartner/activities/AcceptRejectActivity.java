package com.sudrives.sudrivespartner.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.AcceptTrip;
import com.sudrives.sudrivespartner.networks.SocketConnection;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONObject;

import java.util.Locale;

public class AcceptRejectActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer;

    private LinearLayout ll_acceptPopup;
    private ImageView ll_accept_popup_cross;

    private TextView tv_accept_popup_timeCount, tv_accept_popup_pickupLocation, tv_accept_popup_dropLocation, tv_accept_popup_userName, tv_accept_popup_time, tv_accept_popup_collect;

    private CountDownTimer mCustomCountdownTimer;
    private DonutProgress mDonutProgress;
    private AcceptTrip mAcceptTrip;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_accept_popup);
        intent = getIntent();
        mAcceptTrip = (AcceptTrip) intent.getSerializableExtra("AcceptTrip");


        // Log.e("dfgdfg", "sdfdsfds: "+intent.getStringExtra("AcceptTrip"));

        init();


    }


    private void init() {

        mediaPlayer = MediaPlayer.create(AcceptRejectActivity.this, R.raw.timmer_mussic);
        ll_acceptPopup = findViewById(R.id.ll_acceptPopup);
        ll_accept_popup_cross = findViewById(R.id.ll_accept_popup_cross);
        mDonutProgress = findViewById(R.id.donut_accept_popup_progress);
        tv_accept_popup_timeCount = findViewById(R.id.tv_accept_popup_timeCount);
        tv_accept_popup_pickupLocation = findViewById(R.id.tv_accept_popup_pickupLocation);
        tv_accept_popup_dropLocation = findViewById(R.id.tv_accept_popup_dropLocation);
        tv_accept_popup_userName = findViewById(R.id.tv_accept_popup_userName);
        tv_accept_popup_time = findViewById(R.id.tv_accept_popup_time);
        tv_accept_popup_collect = findViewById(R.id.tv_accept_popup_collect);


       // setValue();

    }


    private void setValue() {

        tv_accept_popup_pickupLocation.setText(mAcceptTrip.getResult().getBook_from_address());
        tv_accept_popup_dropLocation.setText(mAcceptTrip.getResult().getBook_to_address());
        tv_accept_popup_userName.setText(getResources().getString(R.string.to) + " " + mAcceptTrip.getResult().getUser_details().getFirst_name()
                + " " + mAcceptTrip.getResult().getUser_details().getLast_name());


        tv_accept_popup_time.setText(mAcceptTrip.getResult().getEta());


        if (mAcceptTrip.getResult().getIs_online_payment_accept().trim().equalsIgnoreCase("0")) {
            tv_accept_popup_collect.setText(getResources().getString(R.string.collect_payment_at_pickup_location));

        } else {
            tv_accept_popup_collect.setText("");
        }
        mDonutProgress.setMax(mAcceptTrip.getPopup_time());

        mCustomCountdownTimer = new CountDownTimer(mAcceptTrip.getPopup_time() * 1000, 1000) {

            @SuppressLint("NewApi")
            public void onTick(long millisUntilFinished) {

              /*  if (mDonutProgress == null) {
                    mDonutProgress = findViewById(R.id.donut_accept_popup_progress);
                }*/

                mDonutProgress.setProgress((int) (millisUntilFinished / 1000));
                //minutes_value.setText((int) (millisUntilFinished/1000)+" "+activity.getResources().getString(R.string.secound));

                if ((millisUntilFinished / 1000) > 10) {
                    tv_accept_popup_timeCount.setText(getResources().getString(R.string.within) + " " + (int) (millisUntilFinished / 1000) + " " + getResources().getString(R.string.seconds));

                } else {
                    tv_accept_popup_timeCount.setText(getResources().getString(R.string.within) + " " + (int) (millisUntilFinished / 1000) + " " + getResources().getString(R.string.second));

                }
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();

            }

            public void onFinish() {
                // AppConstants.SHOW_BOOKING_POPUP = 0;
                mediaPlayer.stop();
                mDonutProgress.setProgress(0);

                HomeMenuActivity.showAcceptDialog = true;

                finish();


            }
        };

        mCustomCountdownTimer.start();

        mDonutProgress.setOnClickListener(this);
        ll_accept_popup_cross.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.donut_accept_popup_progress:


                mCustomCountdownTimer.cancel();
                // AppConstants.SHOW_BOOKING_POPUP = 0;
                mediaPlayer.stop();
                HomeMenuActivity.showAcceptDialog = true;


                if (mAcceptTrip.getError_code() == 461) {
                    AppPreference.clear(AcceptRejectActivity.this);
                    startActivity(new Intent(AcceptRejectActivity.this, SplashActivity.class));
                    finishAffinity();
                    return;
                }

                emittBookingAccept(mAcceptTrip.getResult().getId());

                finish();

                break;
            case R.id.ll_accept_popup_cross:


                //  AppConstants.SHOW_BOOKING_POPUP = 0;
                mediaPlayer.stop();
                mCustomCountdownTimer.cancel();
                HomeMenuActivity.showAcceptDialog = true;

                finish();
                break;

        }
    }


    private void emittBookingAccept(String id) {

        try {


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(AcceptRejectActivity.this, AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(AcceptRejectActivity.this, AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(AcceptRejectActivity.this, AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_TRIP_ID, id);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_BOOKING_ACCEPT, jProfileDate);


        } catch (Exception e) {

            Log.e("AcceptReject", "emittBookingAccept: " + e.getMessage());
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
