package com.sudrives.sudrivespartner.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.google.android.material.navigation.NavigationView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.sudrives.sudrivespartner.MyFirebaseMessagingService;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.wallet.ClaimWalletActivity;
import com.sudrives.sudrivespartner.fragments.CollectionsFragment;
import com.sudrives.sudrivespartner.fragments.FramentProfileFragment;
import com.sudrives.sudrivespartner.fragments.HistoryFragment;
import com.sudrives.sudrivespartner.fragments.MapsFragment;
import com.sudrives.sudrivespartner.fragments.ReportIssueFragmentFragment;
import com.sudrives.sudrivespartner.models.AcceptTrip;
import com.sudrives.sudrivespartner.models.AcceptedBookingModel;
import com.sudrives.sudrivespartner.models.IhaveArrivedAtPickupLocationModel;
import com.sudrives.sudrivespartner.models.NotificationCountModel;
import com.sudrives.sudrivespartner.models.ProfileModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.networks.SocketConnection;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.Image_Picker;
import com.sudrives.sudrivespartner.utils.LocationUpdatesService;
import com.sudrives.sudrivespartner.utils.Methods;
import com.sudrives.sudrivespartner.utils.interfaces.OnClickRequestes;
import com.sudrives.sudrivespartner.utils.interfaces.OnFragmentChange;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.emitter.Emitter;

import static com.sudrives.sudrivespartner.utils.AppConstants.DRIVER_HOME_STATUS;
import static com.sudrives.sudrivespartner.utils.AppConstants.DRIVER_HOME_STATUS_GET;
import static com.sudrives.sudrivespartner.utils.AppConstants.EVENT_FETCH_ACTIVE_SUBSCRIPTION;


public class HomeMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnClickRequestes, AppDialogs.SingleButoonCallback, SocketConnection.SocketStatusCallBacks,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AppDialogs.SubmitButoonCallback, AppDialogs.AcceptButoonCallback, View.OnClickListener,
        AppDialogs.OnOKButtonListener, OnFragmentChange, NetworkConn.OnRequestResponse {


    private AcceptTrip mAcceptTrip;
    private boolean exit = false;
    public static boolean showAcceptDialog = true;

    //


    private LinearLayout llNavHeader;
    public static boolean isUploadBitly = true;


    String TAG = "SocketConnection";
    private Fragment fragment;
    private Toolbar toolbar;
    private RelativeLayout ll_toolbarOptionNotification;
    private LinearLayout ll_toolbarOptionFilter;
    private TextView toolbarTitle, tvDriverLanguage, tvDriverName, tvDriverMobile;
    private NavigationView navigationView;
    private CircleImageView ivDriverProfile;

    public static OnFragmentChange mOnFragmentChange;
    public static OnClickRequestes mOnClickRequestes;

    GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest = new LocationRequest().create();

    private CountDownTimer customCountdownTimer;
    private DonutProgress mDonutProgress;

    AppDialogs mAppDialogs;
    Dialog loaderDialog;
    private View header;
    private DrawerLayout drawer;

    Locale myLocale;
    private String notificationTripId = "";
    private String notificationType = "";
    private String notificationId = "";

    private NotificationCountModel notificationCountModel;
    private ImageView notificationIcon;
    private TextView tv_notification_Count;
    private int currentFragment = 0;
    private int driver_home_status;

    private Button driver_home_location;


    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;

    // Monitors the state of the connection to the service.7891


    // new text view added
    private TextView balance_amount, tv_plan_price, tv_start_date, tv_end_date, validity_subs;



    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.requestLocationUpdates();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        try {
            bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                    Context.BIND_AUTO_CREATE);
        } catch (Exception e) {

            Log.e("Exception", "Exception: " + e.getMessage());
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_home_menu);
        //Intent i = new Intent(HomeMenuActivity.this, SubscriptionPlanActivity.class);
        //startActivity(i);
//        startService(new Intent(HomeMenuActivity.this, GPSTracker.class));
//        Toast.makeText(HomeMenuActivity.this ,"started",Toast.LENGTH_SHORT).show();

/*
        if (AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase(AppConstants.KEY_VALUE_ENGLISH)) {
            setLocale("en");
        } else {
            setLocale("hi");
        }*/


        AppConstants.ONPAUSE = "no";


        try {
            if (AppConstants.mediaPlayer.isPlaying()) {
                AppConstants.mediaPlayer.stop();
                AppConstants.mediaPlayer.release();
            }
        } catch (Exception e) {

        } finally {
            if (getIntent() != null) {
                notificationTripId = getIntent().getStringExtra("tripid");
                notificationType = getIntent().getStringExtra("type");
            }

            // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

            mOnClickRequestes = this;
            mOnFragmentChange = this;

            mLocationClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();


            mLocationRequest.setInterval(60);
            mLocationRequest.setSmallestDisplacement(10);
            mLocationRequest.setFastestInterval(10);


            int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
            //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


            mLocationRequest.setPriority(priority);


            Methods.getDeviceResolutions(this);
            mAppDialogs = new AppDialogs(HomeMenuActivity.this);


            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            toolbarTitle = toolbar.findViewById(R.id.toolbarOptionTitle);
            ll_toolbarOptionNotification = toolbar.findViewById(R.id.toolbarOptionNotification);
            ll_toolbarOptionFilter = toolbar.findViewById(R.id.ll_toolbarOptionFilter);

            drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.humburger);


            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            // KeyboardUtil.setupUI(drawer, HomeMenuActivity.this);


            header = navigationView.getHeaderView(0);

            //llNavHeader = header.findViewById(R.id.llNavHeader);
        /*llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeMenuActivity.this,ProfileActivity.class));
            }
        });*/

            init();

            if (!TextUtils.isEmpty(notificationType) && notificationType.equals("new")) {
                if (!TextUtils.isEmpty(notificationTripId)) {

                    if (MyFirebaseMessagingService.notifManager != null)
                        MyFirebaseMessagingService.notifManager.cancel(MyFirebaseMessagingService.notificationId);

                    Log.d("NotificationTripId", notificationTripId);

                    emitNewRequestFromNotification(notificationTripId);

                    //onAcceptButtonSuccess(notificationTripId, AppConstants.KEY_VALUE_ACCEPT);

                }

            }

            fragment = new MapsFragment();
            toolbarTitle.setText(R.string.nav_home);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();

            navigationView.setCheckedItem(R.id.nav_home);

            drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                }

                @Override
                public void onDrawerOpened(@NonNull View drawerView) {
                    hideKeyboard(HomeMenuActivity.this);

                }

                @Override
                public void onDrawerClosed(@NonNull View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });
        }
        getDriverStatus();
        getWalletDetails();
    }


    private void getActiveSubscription(){
        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);
            //JSONObject jdata = new JSONObject();

            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));

            networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.API_FETCH_ACTIVE_SUBSCRIPTION, hashMap, 1), this, AppConstants.EVENT_FETCH_ACTIVE_SUBSCRIPTION);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getWalletDetails() {
        NetworkConn networkConn = NetworkConn.getInstance(this);
        networkConn.makeRequest(this, networkConn.createGetRequest(AppConstants.GET_WALLET_DETAILS), this, AppConstants.WALLET_DETAILS);
    }

    private void getDriverStatus() {
        NetworkConn networkConn = NetworkConn.getInstance(this);
        networkConn.makeRequest(this, networkConn.createGetRequest(AppConstants.GET_DRIVER_HOME_STATUS), this, DRIVER_HOME_STATUS);
    }

    private void updateDriverStatus() {
        JSONObject updateDriverStatus = new JSONObject();

        // HashMap<String, String> updateDriverStatus = new HashMap<>();
        if (driver_home_status == 0) {
            driver_home_status = 1;
            try {
                updateDriverStatus.put("status", driver_home_status);

            } catch (JSONException e) {

            }
        } else if (driver_home_status == 1) {

            {
                driver_home_status = 0;
            }
        }
        NetworkConn networkConn = NetworkConn.getInstance(this);
        networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.UPDATE_DRIVER_HOME_STATUS, updateDriverStatus.toString()), this, DRIVER_HOME_STATUS_GET);
    }

    private void emitNewRequestFromNotification(final String tipId) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {


//                    get_trip_details
//                            userid
//                    token
//                            language
//                    trip_id


                    JSONObject jProfileDate = new JSONObject();
                    jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));
                    jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN));
                    jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE));
                    jProfileDate.put(AppConstants.KEY_TRIP_ID, tipId);

                    SocketConnection.emitToServer(AppConstants.EMIT_GET_TRIP_DETAILS, jProfileDate);


                } catch (Exception e) {

                    Log.e(TAG, "onConnect: " + e.getMessage());
                }


            }
        }, 2000);

    }


    private void init() {

        notificationIcon = findViewById(R.id.iv_notification);
        //iv_hitravels = findViewById(R.id.iv_hitravels);
        tv_notification_Count = findViewById(R.id.tv_notification_Count);


        notificationIcon.setOnClickListener(this);
        ll_toolbarOptionFilter.setOnClickListener(this);

        tvDriverLanguage = header.findViewById(R.id.tv_driver_language);
        tvDriverName = header.findViewById(R.id.tv_driver_name);
        tvDriverMobile = header.findViewById(R.id.tv_driver_mobile);
        ivDriverProfile = header.findViewById(R.id.iv_driver_profile);
        driver_home_location = header.findViewById(R.id.driver_home_location);
        tv_plan_price = header.findViewById(R.id.tv_plan_price);
        tv_start_date = header.findViewById(R.id.tv_start_date);
        tv_end_date = header.findViewById(R.id.tv_end_date);
        validity_subs = header.findViewById(R.id.validity_subs);

        balance_amount = header.findViewById(R.id.balance_amount);


        if (AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase(AppConstants.KEY_VALUE_HINDI)) {

            tvDriverLanguage.setText(AppConstants.ENG);

        } else {

            tvDriverLanguage.setText(AppConstants.HIN);
        }

        setMenuUserInfo();


//        llNavHeader.setOnClickListener(this);
        tvDriverLanguage.setOnClickListener(this);
        tvDriverMobile.setOnClickListener(this);
        tvDriverName.setOnClickListener(this);
        ivDriverProfile.setOnClickListener(this);
        balance_amount.setOnClickListener(this);
        driver_home_location.setOnClickListener(this);
        //  iv_hitravels.setOnClickListener(this);

        SocketConnection.getInstance(this, this);

        getActiveSubscription();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(true);
        switch (id) {

            case R.id.nav_home:
                currentFragment = 0;

                if (fragment instanceof MapsFragment) {
                    emittGetProfile();

                } else {
                    goToPage(new MapsFragment(), getString(R.string.nav_home));
                    emittGetProfile();
                }
                break;

            case R.id.nav_collections:

                //  goToPage(new CollectionsFragment(), getString(R.string.nav_collection));

                startActivity(new Intent(this, CollectionActivity.class));


                break;
            case R.id.nav_history:
                currentFragment = 2;

                goToPage(new HistoryFragment(), getString(R.string.nav_history));

                // startActivity(new Intent(this, HistoryActivity.class));


                break;

            case R.id.nav_traffic_area:

                startActivity(new Intent(this, TrafficAreaActivity.class));

                break;

            case R.id.report_issue:

                startActivity(new Intent(this, ReportIssueActivity.class));

                break;

            case R.id.nav_contact_us:
                //  goToPage(new ContactUsFragment(), getString(R.string.nav_contact_us));

                startActivity(new Intent(this, ContactUsActivity.class));

                break;

            case R.id.nav_about_us:
                //  goToPage(new AboutUsFragment(), getString(R.string.nav_about_us));

                //startActivity(new Intent(this, AboutUsActivity.class));

                break;

            case R.id.nav_logout:


                logOutDialog();

                break;


            case R.id.nav_profile:


                startActivity(new Intent(this, ProfileActivity.class));

                break;


            case R.id.nav_documents:


                startActivity(new Intent(this, DocumentsActivity.class));

                break;

            case R.id.nav_claim_money:
                startActivity(new Intent(this, Request_cashout_Activity.class));
                break;

            case R.id.nav_subs:

                Intent i = new Intent(this, SubscriptionPlanActivity.class);
                startActivity(i);


                //Toast.makeText(getApplicationContext(),"Available Soon",Toast.LENGTH_LONG).show();

                break;

        }

        hideKeyboard(HomeMenuActivity.this);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    private void goToPage(Fragment mFragment, String title) {

        fragment = null;
        fragment = mFragment;

        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();

        if (title.trim().equalsIgnoreCase(getString(R.string.nav_report_issue))) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).addToBackStack(getString(R.string.nav_report_issue)).commit();

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();

        }

        if (title.trim().equalsIgnoreCase(getString(R.string.nav_collection))) {

            //  ll_toolbarOptionFilter.setVisibility(View.VISIBLE);

        } else {

            ll_toolbarOptionFilter.setVisibility(View.GONE);

        }


        Log.e("werewrewr", "YYYY: " + title);

        toolbar.setTitle("");
        toolbarTitle.setText(title);


    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        if (fragment.getFragmentManager() != null) {
            Log.e("dfdsfds", "FFFF: " + fragment.getFragmentManager().getBackStackEntryCount());
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            if (fragment.getFragmentManager() == null) {

                exitDialog();

                return;
            }


            if (fragment.getFragmentManager().getBackStackEntryCount() > 0) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
                fragment.getFragmentManager().popBackStack();
                toolbarTitle.setText(getString(R.string.nav_history));
            } else {
                exitDialog();


            }
        }


    }

    @Override
    public void onSuccessfullOnclickRequest(String from) {

        //loaderDialog = AppDialogs.showLoader(HomeMenuActivity.this);
        Log.e("te", "ooo: " + from);
        if (from.trim().equalsIgnoreCase(AppConstants.KEY_VALUE_ONLINE)) {

            emittGoOnline();

        } else if (from.trim().equalsIgnoreCase(AppConstants.KEY_VALUE_OFFLINE)) {

            emittGoOffline();
        } else if (from.trim().equalsIgnoreCase(AppConstants.KEY_VALUE_PROFILE_UPDATE)) {

            setMenuUserInfo();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();


        AppConstants.KEY_CONTEXT = HomeMenuActivity.this;

        setBadgeCount(AppConstants.notificationCount);


        navigationView.getMenu().getItem(currentFragment).setChecked(true);

        mLocationClient.connect();

        if (isUploadBitly) {
            callProfile();

        }


    }

    @Override
    protected void onDestroy() {
        // stopService(new Intent(this, LocationMonitoringService.class));
        super.onDestroy();
        SocketConnection.disconnect();
        mLocationClient.disconnect();


    }

    private void coonetToSocket() {

        try {
            SocketConnection.attachListener(AppConstants.ON_GET_PROFILE, onGetProfile);
            SocketConnection.attachListener(AppConstants.ON_ONLINE_OFFLINE, onDriverOnlineOffline);
            SocketConnection.attachListener(AppConstants.ON_CHANGE_DESTINATION_ADDRESS_DRIVER, onResponceChangeDestinationAddressDriver);
            SocketConnection.attachListener(AppConstants.ON_RESPONSE_NEW_BOOKING_NOTIFICATION, onResponceNewBookingNotification);
            SocketConnection.attachListener(AppConstants.ON_RESPONSE_BOOKING_ACCEPT, onResponceBookingAccept);
            SocketConnection.attachListener(AppConstants.ON_RESPONSE_BOOKING_ARRIVED_AT_PICKUP, onResponceBookingArrivedAtPickup);
            SocketConnection.attachListener(AppConstants.ON_RESPONSE_TRIP_START, onResponceTripStart);
            SocketConnection.attachListener(AppConstants.ON_RESPONSE_TRIP_END, onResponceTripEnd);
            SocketConnection.attachListener(AppConstants.ON_RESPONSE_TRIP_CANCEL, onResponceTripCancel);
            SocketConnection.attachListener(AppConstants.ON_RESPONSE_DRIVER_LAST_TRIP_STATUS, onResponceDriverLastTripStatus);
            SocketConnection.attachListener(AppConstants.ON_TRIP_END_SEND_SMS, onResponceTripEndSendSms);
            SocketConnection.attachListener(AppConstants.ON_RESPONCE_RATING_TO_USER, onResponceRatingToUser);
            SocketConnection.attachListener(AppConstants.ON_GET_NOTIFICATION_UNREAD, onGetNotificationCount);
            SocketConnection.attachListener(AppConstants.ON_RESPONCE_LOGOUT, onResponceLogout);

            try {
                requestNotificationUnReadCount();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }


   /* AppPreference.clear(HomeMenuActivity.this);

    setLocale("hi");
    startActivity(new Intent(HomeMenuActivity.this, ActivityLoginActivity.class));
    finishAffinity();*/

    public Emitter.Listener onResponceLogout = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceRatingToUser : " + args[0]);

            try {


                String id = "";


                JSONObject jdata = new JSONObject(args[0].toString());

                if (jdata.getInt(AppConstants.KEY_STATUS) == 1) {

                    onLogout();

                }


            } catch (Exception e) {

                Log.e(TAG, "onResponceRatingToUser Exception : " + e.getMessage());


                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceRatingToUser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceRatingToUser : " + args[0]);

            try {


                String id = "";


                JSONObject jdata = new JSONObject(args[0].toString());

                if (jdata.getInt(AppConstants.KEY_STATUS) == 1) {

                    MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdata, AppConstants.ON_RESPONCE_RATING_TO_USER);


                }


            } catch (Exception e) {

                Log.e(TAG, "onResponceRatingToUser Exception : " + e.getMessage());


                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceDriverLastTripStatus = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceDriverLastTripStatus : " + args[0]);

            try {


                String id = "";


                JSONObject jdata = new JSONObject(args[0].toString());

                if (jdata.getInt(AppConstants.KEY_STATUS) == 1) {
                    Log.e(TAG, "onResponceDriverLastTripStatus : " + args[0]);
                    Log.e(TAG, "onResponceDriverLastTripStatus 123 : " + args[0]);


                    if (jdata.getString("trip_status").trim().equalsIgnoreCase("1")) {

                        AcceptedBookingModel mAcceptedBookingModel = new Gson().fromJson(args[0].toString(), AcceptedBookingModel.class);

                        if (mAcceptedBookingModel.getStatus() == 1) {

                            if ((mAcceptedBookingModel.getResult().getUserid()).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {

                                JSONObject jdate = new JSONObject(args[0].toString());
                                AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "" + mAcceptedBookingModel.getResult().getBooking_info().getId());

                                MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.ON_RESPONSE_BOOKING_ACCEPT);
                            }
                        } else {
                            if (mAcceptedBookingModel.getError_code() == 461) {
                                AppPreference.clear(HomeMenuActivity.this);
                                startActivity(new Intent(HomeMenuActivity.this, SplashActivity.class));
                                finishAffinity();
                                return;
                            }
                        }


                    } else {
                        IhaveArrivedAtPickupLocationModel mIhaveArrivedAtPickupLocationModel = new Gson().fromJson(args[0].toString(), IhaveArrivedAtPickupLocationModel.class);
                        if ((mIhaveArrivedAtPickupLocationModel.getUserid()).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {

                            JSONObject jdate = new JSONObject(args[0].toString());
                            AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "" + mIhaveArrivedAtPickupLocationModel.getResult().getId());

                            if (jdata.getString("trip_status").trim().equalsIgnoreCase("7")) {

                                MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.ON_RESPONSE_BOOKING_ARRIVED_AT_PICKUP);

                            } else if (jdata.getString("trip_status").trim().equalsIgnoreCase("2")) {

                                MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.ON_RESPONSE_TRIP_START);

                            } else if (jdata.getString("trip_status").trim().equalsIgnoreCase("9")) {

                                MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.KEY_VALUE_UPLOAD_BILTI);

                            } else if (jdata.getString("trip_status").trim().equalsIgnoreCase("10")) {

                                MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.KEY_VALUE_RATE_USER);

                            }


                        }
                    }


                }


            } catch (Exception e) {

                Log.e(TAG, "onResponceDriverLastTripStatus Exception : " + e.getMessage());


                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceTripCancel = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceTripCancel : " + args[0]);

            try {


                String id = "";

                //IhaveArrivedAtPickupLocationModel mIhaveArrivedAtPickupLocationModel = new Gson().fromJson(args[0].toString(), IhaveArrivedAtPickupLocationModel.class);


                JSONObject jdata = new JSONObject(args[0].toString());
                if (jdata.getInt(AppConstants.KEY_STATUS) == 1) {

                    JSONObject result = jdata.getJSONObject(AppConstants.KEY_RESULT);

                    if (result.getString("booking_status").trim().equalsIgnoreCase("5")) {


                        AppDialogs.cancelBookingPopup(HomeMenuActivity.this, jdata.getString(AppConstants.KEY_MESSAGE), HomeMenuActivity.this);


                    } else {

                        AppDialogs.cancelBookingPopup(HomeMenuActivity.this, jdata.getString(AppConstants.KEY_MESSAGE), HomeMenuActivity.this);
                    }

                    Log.e(TAG, "onResponceTripCancel : " + args[0]);


                    AppConstants.KEY_VALUE_TRIPID = "0";
                    AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "0");

                    //  callProfile();


                }


            } catch (Exception e) {

                Log.e(TAG, "onResponceTripCancel Exception : " + e.getMessage());


                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceBookingArrivedAtPickup = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceBookingArrivedAtPickup : " + args[0]);

            try {


                String id = "";

                IhaveArrivedAtPickupLocationModel mIhaveArrivedAtPickupLocationModel = new Gson().fromJson(args[0].toString(), IhaveArrivedAtPickupLocationModel.class);

                if (mIhaveArrivedAtPickupLocationModel.getStatus() == 1) {
                    Log.e(TAG, "onResponceBookingArrivedAtPickup : " + args[0]);


                    if (mIhaveArrivedAtPickupLocationModel.getStatus() == 1) {
                        Log.e(TAG, "onResponceBookingArrivedAtPickup 123 : " + args[0]);
                        // if ((mIhaveArrivedAtPickupLocationModel.getResult().).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {
                        if ((mIhaveArrivedAtPickupLocationModel.getUserid()).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {


                            JSONObject jdate = new JSONObject(args[0].toString());
                            AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "" + mIhaveArrivedAtPickupLocationModel.getResult().getId());
                            MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.ON_RESPONSE_BOOKING_ARRIVED_AT_PICKUP);

                        }
                    }

                }


            } catch (Exception e) {

                Log.e(TAG, "onResponceBookingArrivedAtPickup Exception : " + e.getMessage());


                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceTripEnd = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            AppDialogs.hideLoader();

            Log.e(TAG, "onResponceTripEnd : " + args[0]);

            try {


//                {"status":1,"error_code":0,"error_line":967,"booking_id":"","result":"","distance":"0 Km","fare":"10","message":"Trip end successfully."}

                JSONObject jdata = new JSONObject(args[0].toString());

                if (jdata.getInt(AppConstants.KEY_STATUS) == 1) {
                    Log.e(TAG, "onResponceTripEnd : " + args[0]);

                    //if ((mAcceptedBookingModel.getResult().getUserid()).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {
                    AppConstants.KEY_VALUE_TRIPID = "0";

                    AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "0");
                    MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdata, AppConstants.ON_RESPONSE_TRIP_END);
                    //}
                } else {


                    AppDialogs.showToast(HomeMenuActivity.this, jdata.getString(AppConstants.KEY_MESSAGE));
                    IhaveArrivedAtPickupLocationModel mIhaveArrivedAtPickupLocationModel = new Gson().fromJson(args[0].toString(), IhaveArrivedAtPickupLocationModel.class);
                    if ((mIhaveArrivedAtPickupLocationModel.getUserid()).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {

                        JSONObject jdate = new JSONObject(args[0].toString());
                        AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "" + mIhaveArrivedAtPickupLocationModel.getResult().getId());


                        MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.ON_TRIP_END_SEND_SMS);


                    }

                }


            } catch (Exception e) {
                Log.e("sds", "onResponceTripEnd: " + e.getMessage());
                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceTripEndSendSms = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceTripEndSendSms : " + args[0]);

            try {


                String id = "";


                JSONObject jdata = new JSONObject(args[0].toString());

                if (jdata.getInt(AppConstants.KEY_STATUS) == 1) {
                    Log.e(TAG, "onResponceTripEndSendSms : " + args[0]);
                    Log.e(TAG, "onResponceTripEndSendSms 123 : " + args[0]);


                    IhaveArrivedAtPickupLocationModel mIhaveArrivedAtPickupLocationModel = new Gson().fromJson(args[0].toString(), IhaveArrivedAtPickupLocationModel.class);
                    if ((mIhaveArrivedAtPickupLocationModel.getUserid()).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {

                        JSONObject jdate = new JSONObject(args[0].toString());
                        AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "" + mIhaveArrivedAtPickupLocationModel.getResult().getId());


                        MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.ON_TRIP_END_SEND_SMS);


                    }


                }


            } catch (Exception e) {

                Log.e(TAG, "onResponceDriverLastTripStatus Exception : " + e.getMessage());


                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceTripStart = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceTripStart : " + args[0]);

            try {


                String id = "";

                final IhaveArrivedAtPickupLocationModel mIhaveArrivedAtPickupLocationModel = new Gson().fromJson(args[0].toString(), IhaveArrivedAtPickupLocationModel.class);


                if (mIhaveArrivedAtPickupLocationModel.getStatus() == 1) {
                    Log.e(TAG, "onResponceTripStart : " + args[0]);
                    // if ((mIhaveArrivedAtPickupLocationModel.getResult().).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {
                    if ((mIhaveArrivedAtPickupLocationModel.getUserid()).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {


                        JSONObject jdate = new JSONObject(args[0].toString());
                        AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "" + mIhaveArrivedAtPickupLocationModel.getResult().getId());
                        MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.ON_RESPONSE_TRIP_START);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HomeMenuActivity.this, mIhaveArrivedAtPickupLocationModel.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            } catch (Exception e) {
                Log.e("sds", "onResponceTripStart: " + e.getMessage());
                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceBookingAccept = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceBookingAccept : " + args[0]);

            try {


                String id = "";

                AcceptedBookingModel mAcceptedBookingModel = new Gson().fromJson(args[0].toString(), AcceptedBookingModel.class);

                if (mAcceptedBookingModel.getStatus() == 1) {

                    //  if ((mIhaveArrivedAtPickupLocationModel.getResult().).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {
                    if ((mAcceptedBookingModel.getResult().getUserid()).trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {


                        JSONObject jdate = new JSONObject(args[0].toString());
                        AppConstants.KEY_VALUE_TRIPID = "" + mAcceptedBookingModel.getResult().getBooking_info().getId();
                        AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "" + mAcceptedBookingModel.getResult().getBooking_info().getId());
                        MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdate, AppConstants.ON_RESPONSE_BOOKING_ACCEPT);
                    }
                } else if (mAcceptedBookingModel.getError_code() == 461) {
                    String responce = args[0].toString();
                    JSONObject jsonObject = new JSONObject(responce);

                    String token = jsonObject.optString("token");
                    String currentToken = AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN);

                    if (token.equalsIgnoreCase(currentToken)) {

                    } else {
                        AppPreference.clear(HomeMenuActivity.this);
                        startActivity(new Intent(HomeMenuActivity.this, SplashActivity.class));
                        finishAffinity();
                        return;
                    }
                } else {

                    AppDialogs.showToast(HomeMenuActivity.this, mAcceptedBookingModel.getMessage());
                }


            } catch (Exception e) {
                Log.e("sds", "onResponceBookingAccept: " + e.getMessage());
                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceNewBookingNotification = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Log.e(TAG, "onResponceNewBookingNotification : " + args[0]);

            try {


                String id = "";

                mAcceptTrip = new Gson().fromJson(args[0].toString(), AcceptTrip.class);

                for (int i = 0; i < mAcceptTrip.getDriver_id().size(); i++) {

                    if (mAcceptTrip.getDriver_id().get(i).equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {
                        id = mAcceptTrip.getDriver_id().get(i);
                        Log.e(TAG, "onResponceNewBookingNotification : " + id);

                        break;
                    }

                }


                if (id.trim().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {
                    Log.e(TAG, "onResponceNewBookingNotification : " + id);

                    if (mDonutProgress == null) {
                        if (showAcceptDialog) {
                            showAcceptDialog = false;
                            showAcceptDialog(mAcceptTrip);
                        }


                    }

                }


            } catch (Exception e) {
                Log.d("DialogException", "" + e.getMessage());
                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onGetProfile = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onGetProfile : " + args[0]);

            try {

                final ProfileModel loginBeen = new Gson().fromJson(args[0].toString(), ProfileModel.class);
                if (loginBeen.getResult().getUser_id().equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {


                    Log.e(TAG, "onGetProfile : " + (MapsFragment.mOnGetResponse != null));
                    Log.e(TAG, "onGetProfile : " + loginBeen.getResult().getMobile());

                    AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_TRIP_ID, "" + loginBeen.getResult().getTripid());
                    AppPreference.saveStringPref(HomeMenuActivity.this,AppPreference.KEY_UNIQUE_NUMBER,""+ loginBeen.getResult().getUnique_no());
                    AppConstants.KEY_VALUE_TRIPID = loginBeen.getResult().getTripid();


                    MapsFragment.mOnGetResponse.onSuccessfullGetResponse(loginBeen, AppConstants.ON_GET_PROFILE);

                    if (loginBeen.getResult().getDriver_trip_status() == 0) {
                        // emittGoOnline();
                    }


                    // if (runThread)
                    //  setMenuUserInfo(loginBeen);

//                    tvDriverMobile.setText(loginBeen.getResult().getMobile().trim());
//                    tvDriverName.setText(loginBeen.getResult().getFirst_name().trim() + " " + loginBeen.getResult().getLast_name().trim());

                   /* Glide.with(HomeMenuActivity.this).load(loginBeen.getResult().getProfile_img())
                            .thumbnail(0.5f)
                            .into(ivDriverProfile);*/

                }
            } catch (Exception e) {
                e.getStackTrace();
            }


        }
    };

    public Emitter.Listener onGetNotificationCount = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onGetNotification : " + args[0]);

            try {

                notificationCountModel = new Gson().fromJson(args[0].toString(), NotificationCountModel.class);
                if (notificationCountModel != null) {
                    if (notificationCountModel.getStatus() == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setBadgeCount(notificationCountModel.getResult());

                            }
                        });
                    }
                }

            } catch (Exception e) {
                e.getStackTrace();
            }


        }
    };

    private void setBadgeCount(final String result) {

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {

        AppConstants.notificationCount = result;


        if (result.equals("0")) {

            tv_notification_Count.setVisibility(View.GONE);

        } else {

            if (TextUtils.isEmpty(result)) {
                tv_notification_Count.setVisibility(View.GONE);
            } else {
                tv_notification_Count.setVisibility(View.VISIBLE);
                tv_notification_Count.setText(result);
            }
        }
    }

    public Emitter.Listener onDriverOnlineOffline = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onDriverOnlineOffline : " + args[0]);

            try {

                Log.e(TAG, "onDriverOnlineOffline : " + args[0]);
                //hidenLoader();
                JSONObject jdata = new JSONObject(args[0].toString());
                JSONObject result = jdata.getJSONObject(AppConstants.KEY_RESULT);

                Log.e(TAG, "onDriverOnlineOffline : " + result.getString(AppConstants.KEY_DRIVER_ID) + " " + AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));

                if (result.getString(AppConstants.KEY_DRIVER_ID).equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {

//                    {"status":1,"error_code":0,"error_line":280,"message":"Online status update successfully","result":{"driver_status":"Offline"}}


                    Log.e(TAG, "onDriverOnlineOffline : " + jdata);

                    MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdata, AppConstants.ON_ONLINE_OFFLINE);
                }
            } catch (Exception e) {
                e.getStackTrace();
            }


        }
    };


    public Emitter.Listener onResponceChangeDestinationAddressDriver = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.e(TAG, "onResponceChangeDestinationAddressDriver : " + args[0]);

            try {

                Log.e(TAG, "onResponceChangeDestinationAddressDriver : " + args[0]);
                //hidenLoader();
                JSONObject jdata = new JSONObject(args[0].toString());


                if (jdata.getString(AppConstants.KEY_DRIVER_ID).equalsIgnoreCase(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID))) {

//                    {"status":1,"error_code":0,"error_line":280,"message":"Online status update successfully","result":{"driver_status":"Offline"}}


                    Log.e(TAG, "onDriverOnlineOffline : " + jdata);

                    MapsFragment.mOnGetResponse.onSuccessfullGetResponse(jdata, AppConstants.ON_CHANGE_DESTINATION_ADDRESS_DRIVER);
                }
            } catch (Exception e) {
                e.getStackTrace();
            }


        }
    };


    //Emit Method call


    private void emittGoOnline() {
        try {

            //Call for Go online
           /*
                "{
                ""userid"" : ""20"",
                        ""token"" : ""1435565"",
                        ""language"" : ""hindi"",
                        ""driver_status"" : ""Online""
            }"*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_DRIVER_STATUS, AppConstants.KEY_VALUE_ONLINE);

            SocketConnection.emitToServer(AppConstants.EMIT_ONLIE_OFFLINE, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }

    private void emittGoOffline() {
        try {

            //Call for Go online
           /*
                "{
                ""userid"" : ""20"",
                        ""token"" : ""1435565"",
                        ""language"" : ""hindi"",
                        ""driver_status"" : ""Online""
            }"*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_DRIVER_STATUS, AppConstants.KEY_VALUE_OFFLINE);

            SocketConnection.emitToServer(AppConstants.EMIT_ONLIE_OFFLINE, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    private void emittLogout() {
        try {

            //Call for Go online
           /*
                "{
                ""userid"" : ""20"",
                        ""token"" : ""1435565"",
                        ""language"" : ""hindi"",
                        ""driver_status"" : ""Online""
            }"*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE));

            SocketConnection.emitToServer(AppConstants.EMIT_GET_LOGOUT, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    private void callProfile() {
        emittGetProfile();
    }

    private void emittGetProfile() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {

                    //Call for get profile
              /*  "{
                ""userid"" : ""20"",
                        ""token"" : ""1435565"",
                        ""language"" : ""hindi""
            }"*/


                    JSONObject jProfileDate = new JSONObject();
                    jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));
                    jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN));
                    jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE));

                    SocketConnection.emitToServer(AppConstants.EMIT_GET_PROFILE, jProfileDate);


                } catch (Exception e) {

                    Log.e(TAG, "onConnect: " + e.getMessage());
                }


            }
        }, 2000);


    }

//    private void emittUpdateDriverLatlng(double latitude, double longitude) {
//
//        try {
//
//            //Call for update lat lng of driver
//           /* "{
//            ""userid"" : ""20"",
//                    ""token"" : ""1435565"",
//                    ""language"" : ""hindi""
//            ""lat"" : ""22.75"",
//                    ""lang"" : ""75.52"",
//                    ""tripid"" : ""3""
//        }"*/
//
//
//            JSONObject jProfileDate = new JSONObject();
//            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));
//            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN));
//            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE));
//            jProfileDate.put(AppConstants.KEY_LAT, latitude);
//            jProfileDate.put(AppConstants.KEY_LANG, longitude);
//            jProfileDate.put(AppConstants.KEY_TRIP_ID, AppConstants.KEY_VALUE_TRIPID);
//            jProfileDate.put(AppConstants.ANGLE, AppConstants.ANGLE_VALUE);
//
//
//            Log.e("sdfdsfd", "emittUpdateDriverLatlng: " + jProfileDate);
//            SocketConnection.emitToServer(AppConstants.EMIT_GET_DRIVER_CURRENT_LOCATION_UPDATE, jProfileDate);
//
//
//        } catch (Exception e) {
//
//            Log.e(TAG, "onConnect: " + e.getMessage());
//        }
//
//    }

    private void emittBookingAccept(String id) {

        try {

            //Call for update lat lng of driver
           /* "{
            ""userid"" : ""20"",
                    ""token"" : ""1435565"",
                    ""language"" : ""hindi""
            ""lat"" : ""22.75"",
                    ""lang"" : ""75.52"",
                    ""tripid"" : ""3""
        }"*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_TRIP_ID, id);
//            jProfileDate.put(AppConstants.KEY_LANGUAGE, longitude);
            // jProfileDate.put(AppConstants.KEY_TRIP_ID, longitude);


            Log.e("dsfdsf", "TTTTT: " + jProfileDate);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_BOOKING_ACCEPT, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    @Override
    public void onAcceptButtonSuccess(String id, String from) {

        if (from.trim().equalsIgnoreCase(AppConstants.KEY_VALUE_ACCEPT)) {

            emittBookingAccept(id);

        }
    }


    @Override
    public void singleButtonSuccess(String from) {


        if (from.trim().equalsIgnoreCase("Cancel")) {

            callProfile();
        }


    }

    @Override
    public void onSubmitButtonSuccess(String text) {

    }


    @Override
    public void onSocketConnected() {


        Log.e(TAG, "onSocketConnected 123456: ");


        SocketConnection.emitToServer(AppConstants.EMIT_ROOM, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));

        coonetToSocket();


        checkBookingAcceptForNotification();


    }

    @Override
    public void onSocketDisconnected() {
//        SocketConnection.emitToServer(AppConstants.EMIT_DISCONNECT, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));

        Log.e(TAG, "onSocketDisConnected 123456: ");


    }

    @Override
    public void onSocketReconnected() {

        Log.e(TAG, "onSocketReConnected 123456: ");

    }

    @Override
    public void onSocketConnectionError() {

    }

    @Override
    public void onSocketConnectionTimeout() {

    }

    private void hidenLoader() {

        if (loaderDialog != null && loaderDialog.isShowing()) {
            AppDialogs.hideLoader();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d(TAG, "== Error On onConnected() Permission not granted");
            //Permission not granted by user so cancel the further execution.

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Changed Location ", "" + location.getLatitude() + " " + location.getLongitude());

        // emittUpdateDriverLatlng(location.getLatitude(), location.getLongitude());
        // Toast.makeText(HomeMenuActivity.this, "Lat: " + location.getLatitude() + "\nLang" + location.getLongitude(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_driver_name:
            case R.id.tv_driver_mobile:
            case R.id.iv_driver_profile:

//            case R.id.llNavHeader:


//                goToPage(new FramentProfileFragment(), getResources().getString(R.string.nav_profile));
//                navigationView.getMenu().getItem(currentFragment).setChecked(false);


                drawer.closeDrawer(GravityCompat.START);

                break;


            case R.id.tv_driver_language:

                if (AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase(AppConstants.KEY_VALUE_ENGLISH)) {

                    AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);
                    tvDriverLanguage.setText(AppConstants.HIN);

                    setLocale("hi");


                } else {

                    AppPreference.saveStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE, AppConstants.KEY_VALUE_ENGLISH);
                    tvDriverLanguage.setText(AppConstants.ENG);

                    setLocale("en");

                }


                break;

            case R.id.iv_notification:
            case R.id.toolbarOptionNotification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;


            case R.id.ll_toolbarOptionFilter:

                showFilterOption(view);

                break;
            case R.id.driver_home_location:

                updateDriverStatus();

                break;

            case R.id.balance_amount:
                startActivity(new Intent(this, ClaimWalletActivity.class));
                break;


           /* case R.id.iv_hitravels:

                OpenApp(this);

                break;*/


        }
        hideKeyboard(HomeMenuActivity.this);


    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClickOK() {

        callProfile();


    }

    private void showFilterOption(View view) {

        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {


                try {


                    TextView tv_filter_weekily, tv_filter_daily, tv_filter_monthly, tv_filter_all;


                    final Dialog dialog = new Dialog(HomeMenuActivity.this, R.style.FilterDialogTheme);

                    LayoutInflater layoutInflater = (LayoutInflater) HomeMenuActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                            CollectionsFragment.mSubmitButoonCallback.onSubmitButtonSuccess(getString(R.string.weekly));


                        }
                    });

                    tv_filter_daily.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            CollectionsFragment.mSubmitButoonCallback.onSubmitButtonSuccess(getString(R.string.daily));

                        }
                    });

                    tv_filter_monthly.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            CollectionsFragment.mSubmitButoonCallback.onSubmitButtonSuccess(getString(R.string.month));

                        }
                    });

                    tv_filter_all.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            CollectionsFragment.mSubmitButoonCallback.onSubmitButtonSuccess(getString(R.string.all));

                        }
                    });


                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = (Methods.getDeviceResolutions(HomeMenuActivity.this).widthPixels / 3);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("I am inside0", "I am inside 33333");
        if (requestCode == Image_Picker.CAMERA_REQUEST || requestCode == Image_Picker.GALLERY_REQUEST || requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.e("I am inside0", "I am inside0");
            if (fragment instanceof FramentProfileFragment) {
                Log.e("I am inside", "I am inside");

                fragment.onActivityResult(requestCode, resultCode, data);

            } else if (fragment instanceof ReportIssueFragmentFragment) {
                Log.e("I am inside0", "I am inside0 1234");

                fragment.onActivityResult(requestCode, resultCode, data);

            } else if (fragment instanceof MapsFragment) {
                Log.e("I am inside0", "I am inside0 1234");

                fragment.onActivityResult(requestCode, resultCode, data);

            }

        }


    }

    ConstraintLayout ll_acceptPopup;
    LinearLayout ll_accept_popup_dropLocation;
    ImageView ll_accept_popup_cross;

    TextView tv_accept_popup_timeCount, tv_accept_popup_pickupLocation, tv_accept_popup_dropLocation, tv_accept_popup_userName, tv_accept_popup_time, tv_accept_popup_collect;

    CountDownTimer mCustomCountdownTimer;


    private void showAcceptDialog(final AcceptTrip mAcceptTrip) {


      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {


                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {

                            newRequestDialog(mAcceptTrip);
                        }
                    });



                } catch (Exception e) {

                    Log.e(TAG, "onConnect: " + e.getMessage());
                }


            }
        }, 100);
*/


        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                newRequestDialog(mAcceptTrip);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void newRequestDialog(final AcceptTrip mAcceptTrip) {
        try {

//            Bundle bundle = new Bundle();
//            bundle.putSerializable("AcceptTrip", mAcceptTrip);

           /* Intent intent = new Intent(HomeMenuActivity.this, AcceptRejectActivity.class);

            intent.putExtra("AcceptTrip", mAcceptTrip);

            startActivity(intent);
*/


            mAppDialogs = new AppDialogs(AppConstants.KEY_CONTEXT);


            Log.e("dfgdfgdfg", "showAcceptDialog showAcceptDialog");

            final MediaPlayer mediaPlayer = MediaPlayer.create(AppConstants.KEY_CONTEXT, R.raw.timmer_mussic);

            final Dialog dialog = new Dialog(AppConstants.KEY_CONTEXT, R.style.MyDialogTheme);

            LayoutInflater layoutInflater = (LayoutInflater) AppConstants.KEY_CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = layoutInflater.inflate(R.layout.new_accept_popup, null);


            ll_acceptPopup = view.findViewById(R.id.ll_acceptPopup);
            ll_accept_popup_cross = view.findViewById(R.id.ll_accept_popup_cross);
            mDonutProgress = view.findViewById(R.id.donut_accept_popup_progress);
            tv_accept_popup_timeCount = view.findViewById(R.id.tv_accept_popup_timeCount);
            tv_accept_popup_pickupLocation = view.findViewById(R.id.tv_accept_popup_pickupLocation);
            tv_accept_popup_dropLocation = view.findViewById(R.id.tv_accept_popup_dropLocation);
            ll_accept_popup_dropLocation = view.findViewById(R.id.ll_accept_popup_dropLocation);
            tv_accept_popup_userName = view.findViewById(R.id.tv_accept_popup_userName);
            tv_accept_popup_time = view.findViewById(R.id.tv_accept_popup_time);
            tv_accept_popup_collect = view.findViewById(R.id.tv_accept_popup_collect);


            tv_accept_popup_pickupLocation.setText(mAcceptTrip.getResult().getBook_from_address());
            tv_accept_popup_dropLocation.setText(mAcceptTrip.getResult().getBook_to_address());
            tv_accept_popup_userName.setText(getResources().getString(R.string.to) + " " + mAcceptTrip.getResult().getUser_details().getFirst_name()
                    + " " + mAcceptTrip.getResult().getUser_details().getLast_name());

//            CircleImageView imageView = findViewById(R.id.user_image);
          //  Glide.with(this)
            //        .load(mAcceptTrip.getResult().getUser_details().getProfile_img())
              //      .into(imageView);

            tv_accept_popup_time.setText(mAcceptTrip.getResult().getEta());


            tv_accept_popup_collect.setText(getResources().getString(R.string.rupee) + " " + mAcceptTrip.getResult().getTotal_fare() + " " + getResources().getString(R.string.estimated_trip_amount));


            if (mAcceptTrip.getResult().getType_of_booking() != null) {
                if (mAcceptTrip.getResult().getType_of_booking().trim().equalsIgnoreCase("1")) {
                    ll_accept_popup_dropLocation.setVisibility(View.GONE);
                }

            }


            mDonutProgress.setMax(mAcceptTrip.getPopup_time());

            mCustomCountdownTimer = new CountDownTimer(mAcceptTrip.getPopup_time() * 1000, 1000) {

                @SuppressLint("NewApi")
                public void onTick(long millisUntilFinished) {

                    if (mDonutProgress == null) {
                        mDonutProgress = view.findViewById(R.id.donut_accept_popup_progress);
                    }

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
                    mDonutProgress = null;
                    showAcceptDialog = true;

                    dialog.dismiss();

                }
            };

            mCustomCountdownTimer.start();


            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view);
            dialog.setCancelable(false);

            ll_accept_popup_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mDonutProgress = null;

                    //  AppConstants.SHOW_BOOKING_POPUP = 0;
                    mediaPlayer.stop();
                    mCustomCountdownTimer.cancel();
                    showAcceptDialog = true;

                    dialog.dismiss();


                }
            });


            mDonutProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mDonutProgress = null;

                    mCustomCountdownTimer.cancel();
                    // AppConstants.SHOW_BOOKING_POPUP = 0;
                    mediaPlayer.stop();
                    showAcceptDialog = true;

                    dialog.dismiss();

                    if (mAcceptTrip.getError_code() == 461) {
                        AppPreference.clear(AppConstants.KEY_CONTEXT);
                        startActivity(new Intent(AppConstants.KEY_CONTEXT, SplashActivity.class));
                        HomeMenuActivity.this.finishAffinity();
                        return;
                    }

                    onAcceptButtonSuccess(mAcceptTrip.getResult().getId(), AppConstants.KEY_VALUE_ACCEPT);
                }
            });


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
            //lp.gravity = Gravity.BOTTOM;

            dialog.getWindow().setAttributes(lp);
            // dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.show();


        } catch (Exception e) {
            Log.e("Exception", "showAcceptDialog: " + e.getMessage());

        }
    }

    @Override
    public void onFragmentChange(Fragment mFragment, String title) {
        goToPage(mFragment, title);
    }

    public void logOutDialog() {

        new AlertDialog.Builder(HomeMenuActivity.this)
                .setMessage(R.string.are_you_sure_you_want_to_logout)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


//                        emittGoOffline();
                        emittLogout();

                        //     SocketConnection.disconnect();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        navigationView.getMenu().getItem(currentFragment).setChecked(true);
                    }
                })
                .show();
    }

    private void setMenuUserInfo() {


//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {

        tvDriverMobile.setText(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_NAME));
        tvDriverName.setText(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_MOBILE));

        Log.e("fdgdfgdfg", "PPPPPP: " + tvDriverName.getText().toString());
        setImage(AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_PROFILE_IMAGE));

//            }
//        });

    }

    private void setImage(String profile_img) {
        Glide.with(HomeMenuActivity.this).load(profile_img)
                .thumbnail(0.5f)
                .into(ivDriverProfile);
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


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        recreate();
        super.onConfigurationChanged(newConfig);

    }

    private void checkBookingAcceptForNotification() {


        if (getIntent().getStringExtra("Type") != null) {

            if (getIntent().getStringExtra("Type").trim().equalsIgnoreCase("new")) {

                onAcceptButtonSuccess(getIntent().getStringExtra(AppConstants.KEY_TRIPID), AppConstants.KEY_VALUE_ACCEPT);
            }


        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (hasFocus) {

            if (Methods.isNetworkConnected(this)) {
                //  Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HomeMenuActivity.this, "Internet Disconnected", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void restartActivity() {


        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("from", "home");
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intent);

    }

    private void requestNotificationUnReadCount() throws JSONException {

        JSONObject jProfileDate = new JSONObject();
        jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_USER_ID));
        jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_TOKEN));
        jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(HomeMenuActivity.this, AppPreference.KEY_LANGUAGE));


        SocketConnection.emitToServer(AppConstants.EMIT_GET_NOTIFICATION_UNREAD_COUNT, jProfileDate);


    }

    @Override
    protected void onPause() {
        super.onPause();

        AppConstants.ONPAUSE = "yes";
    }

    private void showNewRequestView() {

        LinearLayout linearLayout = findViewById(R.id.accept_pop_pup);

        linearLayout.setVisibility(View.VISIBLE);
    }

    private void exitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setMessage(getString(R.string.are_your_sure_you_want_to_exit));

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onLogout() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//in this case, it should be 0
                manager.cancel(AppConstants.notificationId);
//dismiss notification panel
                Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                sendBroadcast(it);

                mService.removeLocationUpdates();
                AppPreference.clear(HomeMenuActivity.this);
                startActivity(new Intent(HomeMenuActivity.this, ActivityLoginActivity.class));
                finishAffinity();

                // setLocale("hi");


            }
        });


    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {
        Log.d("Response", "" + response.toString());

        if (strEventType.equals(AppConstants.WALLET_DETAILS)) {
            int status = response.optInt("status");

            if (status == 1) {

                try {
                    JSONObject result = response.getJSONObject("result");
                    String wallet_amount = result.getString("wallet_amount");
                    balance_amount.setText("Earned-" + wallet_amount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else if (strEventType.equals(DRIVER_HOME_STATUS)) {
            Log.d("Response", "" + response.toString());
            int status = response.optInt("status");
            if (status == 1) {
                try {
                    JSONObject result = response.getJSONObject("result");
                    driver_home_status = Integer.parseInt(result.getString("home_status"));
                    if (driver_home_status == 1) {
                        driver_home_location.setBackground(getResources().getDrawable(R.drawable.btn_offline));
                    } else {
                        driver_home_location.setBackground(getResources().getDrawable(R.drawable.btn_online));

                    }

                } catch (JSONException e) {

                }

            }
        } else if (strEventType.equals(DRIVER_HOME_STATUS_GET)) {
            Log.d("Response", "" + response.toString());
            int status = response.optInt("status");
            if (status == 1) {
                try {
                    JSONObject result = response.getJSONObject("result");
                    driver_home_status = result.getInt("home_status");
                    if (driver_home_status == 1) {

                        driver_home_location.setBackground(getResources().getDrawable(R.drawable.btn_offline));
                    } else {
                        driver_home_location.setBackground(getResources().getDrawable(R.drawable.btn_online));
                    }
                } catch (JSONException e) {

                }
            }
        }else if (strEventType.equalsIgnoreCase(EVENT_FETCH_ACTIVE_SUBSCRIPTION)) {

            Log.e("ResponseFETCHSUBS",response.toString());
            int status = response.optInt("status");
            if (status == 1) {

                try {
                    JSONObject result = response.getJSONObject("result");

                    String priceSubs = result.getString("payment_amount");
                    String startDate = result.getString("subscription_start_date");
                    String endDate = result.getString("subscription_end_date");
                    String planValidity = result.getString("validity");

                    tv_plan_price.setText("Price: "+priceSubs);
                    tv_start_date.setText("Start: "+startDate);
                    tv_end_date.setText("End: "+endDate);
                    validity_subs.setText("Plan: "+planValidity);



                }catch (JSONException e){

                }

            }

        }

    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }
}
