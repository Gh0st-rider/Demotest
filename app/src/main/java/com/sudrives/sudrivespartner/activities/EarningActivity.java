package com.sudrives.sudrivespartner.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.EarningData;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.DrawPathmain;
import com.sudrives.sudrivespartner.utils.Methods;

import java.util.Date;
import java.util.Locale;

public class EarningActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private EarningData earningData;

    private LinearLayout llEarningsBookingDetails;
    private TextView tvEarningsBookingId;
    private TextView tvEarningsTime;
    private TextView tvEarningsDate;
    private LinearLayout llEarningsLocationDetails;
    private LinearLayout llEarningsPickupLocation;
    private ImageView ivPickupLocation;
    private TextView tvEarningsPickupLocation;
    private LinearLayout llEarningsDropOffLocation;
    private ImageView ivDropOffLocation;
    private TextView tvEarningsDropOffLocation;
    private TextView tvEarningsTotalDistance;
    private TextView tvEarningsTripTime;
    private LinearLayout llEarningDetails;
    private TextView tvEarningsTotalDistanceAmount;
    private TextView tvEarningsCashCollected;
    private TextView tvTvEarningsHaulagePayout;
    private TextView tv_donation_amount;
    private TextView tv_notification_Count;
    private LatLng origin;
    private LatLng destination;
    private RelativeLayout toolbarOptionNotification;

    public final int TIMEOUT = 5000;
    private Runnable runnable;
    private Handler handler;
    private boolean isSet = true;
    private boolean isPath = true;

    ProgressBar progressBar;
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
        setContentView(R.layout.earning_activity);

        if (getIntent() != null) {
            earningData = (EarningData) getIntent().getSerializableExtra(AppConstants.EARNING_DATA);
        }

        init();

//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void init() {
        llEarningsBookingDetails = findViewById(R.id.ll_earnings_booking_details);
        tvEarningsBookingId = findViewById(R.id.tv_earnings_booking_id);
        tvEarningsTime = findViewById(R.id.tv_earnings_time);
        tvEarningsDate = findViewById(R.id.tv_earnings_date);
        llEarningsLocationDetails = findViewById(R.id.ll_earnings_location_details);
        llEarningsPickupLocation = findViewById(R.id.ll_earnings_pickup_location);
        ivPickupLocation = findViewById(R.id.iv_pickup_location);
        tvEarningsPickupLocation = findViewById(R.id.tv_earnings_pickup_location);
        llEarningsDropOffLocation = findViewById(R.id.ll_earnings_drop_Off_location);
        ivDropOffLocation = findViewById(R.id.iv_drop_Off_location);
        tvEarningsDropOffLocation = findViewById(R.id.tv_earnings_drop_Off_location);
        tvEarningsTotalDistance = findViewById(R.id.tv_earnings_total_distance);
        tvEarningsTripTime = findViewById(R.id.tv_earnings_trip_time);
        llEarningDetails = findViewById(R.id.ll_Earning_details);
        tvEarningsTotalDistanceAmount = findViewById(R.id.tv_earnings_total_distance_amount);
        tvEarningsCashCollected = findViewById(R.id.tv_earnings_cash_collected);
        tvTvEarningsHaulagePayout = findViewById(R.id.tv_tv_earnings_haulage_payout);
        toolbarOptionNotification = findViewById(R.id.toolbarOptionNotification);
        tv_notification_Count = findViewById(R.id.tv_notification_Count);

        tv_donation_amount = findViewById(R.id.tv_donation_amount);

        findViewById(R.id.ib_back_button).setOnClickListener(this);
        findViewById(R.id.ib_notification).setOnClickListener(this);
        toolbarOptionNotification.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_bar);


        if(TextUtils.isEmpty(AppConstants.notificationCount)){

            tv_notification_Count.setVisibility(View.GONE);
        }else {
            tv_notification_Count.setVisibility(View.VISIBLE);
            tv_notification_Count.setText(AppConstants.notificationCount);

        }


        setEarnignData();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(true);

//        Location location = mMap.getMyLocation();
//
//        if (location != null)
//            setMarker(location);


        // mMap.setOnMyLocationChangeListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMyLocationChange(Location location) {

                progressBar.setVisibility(View.GONE);

                new DrawPathmain(EarningActivity.this, mMap, origin, destination, "", "earnings");

                setMarker(origin);

                mMap.setMyLocationEnabled(false);
            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                llEarningsBookingDetails.setVisibility(View.GONE);
                llEarningsLocationDetails.setVisibility(View.GONE);
                llEarningDetails.setVisibility(View.GONE);

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        llEarningsBookingDetails.setVisibility(View.VISIBLE);
                        llEarningsLocationDetails.setVisibility(View.VISIBLE);
                        llEarningDetails.setVisibility(View.VISIBLE);

                    }
                };

                setHandler();

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConstants.KEY_CONTEXT = EarningActivity.this;
    }

    private void setHandler() {
        handler = new Handler();
        handler.postDelayed(runnable, TIMEOUT);

        handler.removeCallbacks(runnable, TIMEOUT);

    }

    private void setEarnignData() {

        tvEarningsBookingId.setText(earningData.bookingId);
        tvEarningsTime.setText(Methods.getTimeStampToTime(earningData.time));
        tvEarningsDate.setText(getDate(earningData.time));
        tvEarningsPickupLocation.setText(earningData.pickUpLocation);
        tvEarningsDropOffLocation.setText(earningData.dropOffLocation);
        tvEarningsTotalDistance.setText(earningData.totalDistance);
        tvEarningsTripTime.setText(earningData.tripTime);
        tvEarningsTotalDistanceAmount.setText(earningData.totalDistance);
        tvEarningsCashCollected.setText(getResources().getString(R.string.rupee) + " " + earningData.cashCollected);
        tvTvEarningsHaulagePayout.setText(getResources().getString(R.string.rupee) + " " + earningData.haulagePayout);
        tv_donation_amount.setText(getString(R.string.rupee).concat(" "+earningData.donationAmount));

        origin = new LatLng(earningData.originlat, earningData.originlng);
        destination = new LatLng(earningData.destlat, earningData.destlng);


        if (earningData.type_of_booking.trim().equalsIgnoreCase("1")){
            llEarningsDropOffLocation.setVisibility(View.GONE);

        }



        // onMapReady(mMap);
    }

    private String getTime(String time) {

        String date = "";
        try {
            long l = Long.parseLong(time);

            Date d = new Date((long) l * 1000);
            date = DateFormat.format("HH:mm a", d).toString();
            System.out.println(date);


        } catch (Exception e) {

            Log.e("Exception", "getTime: " + e.getMessage());
        }


        return date;

    }

    private String getDate(String time) {

        String date = "";
        try {
            long l = Long.parseLong(time);

            Date d = new Date((long) l * 1000);
            date = DateFormat.format("dd-MMM-yyyy", d).toString();
            System.out.println(date);


        } catch (Exception e) {

            Log.e("Exception", "getTime: " + e.getMessage());
        }


        return date;


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ib_back_button:
                finish();
                break;
            case R.id.toolbarOptionNotification:
            case R.id.ib_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;

        }

    }

    private void setMarker(LatLng location) {


        Log.e("dfgdfg", "fffff: 55555552222222");
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 14);

        if (isSet) {
            isSet = false;
            mMap.animateCamera(cameraUpdate);

        }
    }
}
