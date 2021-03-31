package com.sudrives.sudrivespartner.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.maps.android.PolyUtil;
import com.michael.easydialog.EasyDialog;
import com.sudrives.sudrivespartner.BuildConfig;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.HomeMenuActivity;
import com.sudrives.sudrivespartner.activities.SplashActivity;
import com.sudrives.sudrivespartner.activities.chatmodule.ChatActivity;
import com.sudrives.sudrivespartner.adapters.BookingHistoryAdapter;
import com.sudrives.sudrivespartner.helper.VolleySingleton;
import com.sudrives.sudrivespartner.interfaces.iBeginTripListner;
import com.sudrives.sudrivespartner.interfaces.iDriveArrivedClickListner;
import com.sudrives.sudrivespartner.interfaces.iOnDonateListner;
import com.sudrives.sudrivespartner.models.AcceptedBookingModel;
import com.sudrives.sudrivespartner.models.BookingDetailsBeans;
import com.sudrives.sudrivespartner.models.DeclineRequestModel;
import com.sudrives.sudrivespartner.models.EndTripModel;
import com.sudrives.sudrivespartner.models.IhaveArrivedAtPickupLocationModel;
import com.sudrives.sudrivespartner.models.LoginModel;
import com.sudrives.sudrivespartner.models.ProfileModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.networks.SocketConnection;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.HeadService;
import com.sudrives.sudrivespartner.utils.DrawPathmain;
import com.sudrives.sudrivespartner.utils.Image_Picker;
import com.sudrives.sudrivespartner.utils.Methods;
import com.sudrives.sudrivespartner.utils.interfaces.OnGetResponse;
import com.sudrives.sudrivespartner.utils.interfaces.ReportIssueClickListner;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, OnGetResponse, GoogleMap.OnMyLocationChangeListener,
        AppDialogs.SubmitButoonCallback, AppDialogs.OnOKButtonListener, NetworkConn.OnRequestResponse, AppDialogs.DeclineRequestSubmitButton, AppDialogs.UserRatingSubmitButton, iOnDonateListner, iBeginTripListner, iDriveArrivedClickListner, ReportIssueClickListner {


    public static OnGetResponse mOnGetResponse;
    AlertDialog myAlertDialog = null;
    private Dialog dialogForUserRating = null;
    IhaveArrivedAtPickupLocationModel mIhaveArrivedAtPickupLocationModel;
    EndTripModel mEndTripModel;
    private String TAG = "MapsFragment";
    private AcceptedBookingModel mAcceptedBookingModel;
    private GoogleMap mMap;
    private LatLng mCenterLatLong;
    private View mapView;
    private View mLayouView;
    //    private Context mContext;
    private boolean isSet = true;
    //Driver Info
    private TextView tv_home_drive_vehicle_info, tv_home_drive_name;
    private RatingBar rb_home_drive_rating;
    private CircleImageView iv_home_drive_info;
    private LinearLayout ll_home_drinver_info;
    private List<DeclineRequestModel.ResultBean.DeclineRequestBean> mDeclineRequestList = new ArrayList<>();
    private Image_Picker image_picker;
    private TextView ib_go, ib_go_IhaveArrive;
    private Switch tb_onlineOffline;
    private boolean isOnlineOffline = false;
    String userID="";
    String name = "";
    private Dialog donationDialog;


    boolean isStarted = false;

    //Pin location
    private CardView cv_pinlocation;
    private TextView tv_pinlocation_location;

    //Online Offline

    private LinearLayout ll_online_offline;
    private Button but_online_offline_online, but_online_offline_offline;

    //After Accept booking Top
    private LinearLayout ll_booking_accepted_top, ll_booking_accepted_bottom, ll_Drop_Off_location;
    private Button ll_booking_accepted_bottom_mobile;
    private TextView tv_booking_accepted_top_pickup_location, tv_booking_accepted_top_Drop_Off_location;
    private Button btn_booking_accepted_bottom_cancel;
    private TextView btn_booking_accepted_bottom_arrived_at_pickup_location;
    private TextView tv_booking_accepted_bottom_booking_id, tv_booking_accepted_bottom_vehicle_name, tv_booking_accepted_bottom_client_name,
            tv_booking_accepted_bottom_ETA, tv_booking_accepted_bottom_vehicle_number;
    private LatLng curentLocation;
    private LatLng destinationLocation = null;

    CircleImageView accept_bottom_user_image;

    TextView tv_drop_off;


    //I have Arrive
    private LinearLayout ll_IHA;
    private TextView tv_IHA_pickup_location, tv_IHA_drop_Off_location, tv_IHA_name, tv_IHA_time, tv_IHA_reach_time;
    private ImageView iv_IHA_pickup_location_icon, iv_IHA_pickup_location;
    private Button but_bigin_trip, but_end_trip,but_emer;

    LinearLayout ll_end_trip;

    String endTripId = "";


    private LocationCallback mLocationCallback;

    RelativeLayout upperLayout;
    ProgressBar progressBar;
    private boolean isGetProfile = true;
    private float angle;

    RatingBar ratingBar;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;


    TextView tvTotalRide, tvTotalAmount,tv_status;
    ImageView iv_healthStatus;

    //BottomSheet recent rides
    BottomSheetBehavior sheetBehavior;
    LinearLayout layoutBottomSheet;
    RecyclerView rec_recentbookings;
    List<BookingDetailsBeans.ResultBean> resultBeans;
    BookingHistoryAdapter bookingHistoryAdapter;
    ImageView arrowleft, arrowright;
    TextView chat_text;

     AlertDialog alertDialog3;
    TextView bookings;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {

                    Log.d("Lcoationarray", location.getLatitude() + " " + location.getLongitude());

                }
            }
        };



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mOnGetResponse = this;

        mLayouView = inflater.inflate(R.layout.map_fragment, null);

        return mLayouView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        upperLayout = view.findViewById(R.id.upper_layout);
        progressBar = view.findViewById(R.id.progress_bar);


        init();


        //emittGetProfile();

        // cancelBooking();


//        try {
//            getProfileDetail();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        getPerdayRide();

    }

    @Override
    public void onDetach() {
        // mContext.stopService(new Intent(mContext, LocationMonitoringService.class));

        if (mMap != null)
            mMap.clear();

//        mContext = null;
        super.onDetach();
    }

    private void init() {

        //Go Button
        ib_go = mLayouView.findViewById(R.id.ib_go);
        ib_go_IhaveArrive = mLayouView.findViewById(R.id.ib_go_IhaveArrive);

        //Driver Info
        tv_home_drive_vehicle_info = mLayouView.findViewById(R.id.tv_home_drive_vehicle_info);
        tv_home_drive_vehicle_info = mLayouView.findViewById(R.id.tv_home_drive_vehicle_info);
        tv_home_drive_name = mLayouView.findViewById(R.id.tv_home_drive_name);
        rb_home_drive_rating = mLayouView.findViewById(R.id.rb_home_drive_rating);
        iv_home_drive_info = mLayouView.findViewById(R.id.iv_home_drive_info);
        ll_home_drinver_info = mLayouView.findViewById(R.id.ll_home_drinver_info);

        tv_drop_off = mLayouView.findViewById(R.id.tv_drop_off);

        iv_healthStatus = mLayouView.findViewById(R.id.iv_healthStatus);
        tv_status = mLayouView.findViewById(R.id.tv_status);

        tv_status.setOnClickListener(this);
        iv_healthStatus.setOnClickListener(this);

        ll_home_drinver_info.setVisibility(View.GONE);

        //Pin cLocation
        cv_pinlocation = mLayouView.findViewById(R.id.cv_pinlocation);
        // tv_pinlocation_location = mLayouView.findViewById(R.id.tv_pinlocation_location); comment by ashu this is driver current location
        cv_pinlocation.setVisibility(View.GONE);


        //Online offline


        tb_onlineOffline = mLayouView.findViewById(R.id.tb_onlineOffline);
        tvTotalRide = mLayouView.findViewById(R.id.tv_total_ride);
        tvTotalAmount = mLayouView.findViewById(R.id.tv_total_amount);

        ll_online_offline = mLayouView.findViewById(R.id.ll_online_offline);
        but_online_offline_online = mLayouView.findViewById(R.id.but_online_offline_online);
        but_online_offline_offline = mLayouView.findViewById(R.id.but_online_offline_offline);

        ll_online_offline.setVisibility(View.GONE);
        but_online_offline_online.setOnClickListener(this);
        but_online_offline_offline.setOnClickListener(this);

        //After Accept booking Top And Bottom
        ll_Drop_Off_location = mLayouView.findViewById(R.id.ll_Drop_Off_location);
        ll_booking_accepted_top = mLayouView.findViewById(R.id.ll_booking_accepted_top);
        tv_booking_accepted_top_pickup_location = mLayouView.findViewById(R.id.tv_booking_accepted_top_pickup_location);
        tv_booking_accepted_top_Drop_Off_location = mLayouView.findViewById(R.id.tv_booking_accepted_top_Drop_Off_location);

        ll_booking_accepted_top.setVisibility(View.GONE);


        ll_booking_accepted_bottom = mLayouView.findViewById(R.id.ll_booking_accepted_bottom);
        ll_booking_accepted_bottom_mobile = mLayouView.findViewById(R.id.btnCallDriver);

        btn_booking_accepted_bottom_cancel = mLayouView.findViewById(R.id.btn_booking_accepted_bottom_cancel);
        btn_booking_accepted_bottom_arrived_at_pickup_location = mLayouView.findViewById(R.id.btn_booking_accepted_bottom_arrived_at_pickup_location);
        tv_booking_accepted_bottom_booking_id = mLayouView.findViewById(R.id.tv_booking_accepted_bottom_booking_id);
        tv_booking_accepted_bottom_vehicle_name = mLayouView.findViewById(R.id.tv_booking_accepted_bottom_vehicle_name);
        tv_booking_accepted_bottom_client_name = mLayouView.findViewById(R.id.tv_booking_accepted_bottom_client_name);
        ratingBar = mLayouView.findViewById(R.id.ratingBar);
        // tv_booking_accepted_bottom_mobile = mLayouView.findViewById(R.id.tv_booking_accepted_bottom_mobile);
        // tv_booking_accepted_bottom_price = mLayouView.findViewById(R.id.tv_booking_accepted_bottom_price);
        tv_booking_accepted_bottom_ETA = mLayouView.findViewById(R.id.tv_booking_accepted_bottom_ETA);
        tv_booking_accepted_bottom_vehicle_number = mLayouView.findViewById(R.id.tv_vehicleNumber);
        accept_bottom_user_image = mLayouView.findViewById(R.id.iv_driver_img);
        ll_booking_accepted_bottom.setVisibility(View.GONE);


        btn_booking_accepted_bottom_cancel.setOnClickListener(this);
        btn_booking_accepted_bottom_arrived_at_pickup_location.setOnClickListener(this);

        //I heve arrive
        iv_IHA_pickup_location = mLayouView.findViewById(R.id.iv_IHA_pickup_location);
        iv_IHA_pickup_location_icon = mLayouView.findViewById(R.id.iv_IHA_pickup_location_icon);
        ll_IHA = mLayouView.findViewById(R.id.ll_IHA);
        tv_IHA_pickup_location = mLayouView.findViewById(R.id.tv_IHA_pickup_location);
        tv_IHA_drop_Off_location = mLayouView.findViewById(R.id.tv_IHA_drop_Off_location);
        tv_IHA_name = mLayouView.findViewById(R.id.tv_IHA_name);
        tv_IHA_time = mLayouView.findViewById(R.id.tv_IHA_time);
        tv_IHA_reach_time = mLayouView.findViewById(R.id.tv_IHA_reach_time);

        ll_IHA.setVisibility(View.GONE);

        but_bigin_trip = mLayouView.findViewById(R.id.but_bigin_trip);
        but_end_trip = mLayouView.findViewById(R.id.but_end_trip);
        ll_end_trip = mLayouView.findViewById(R.id.ll_end_trip);
        but_emer = mLayouView.findViewById(R.id.but_emer);
        bookings = mLayouView.findViewById(R.id.bookings);
        but_emer.setOnClickListener(this);
        but_end_trip.setOnClickListener(this);
        but_bigin_trip.setOnClickListener(this);

        ll_booking_accepted_bottom_mobile.setOnClickListener(this);
        ib_go.setOnClickListener(this);
        ib_go_IhaveArrive.setOnClickListener(this);
        chat_text = mLayouView.findViewById(R.id.chat_text);

        chat_text.setOnClickListener(this);

        tb_onlineOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.e("dfgdfg", "gggggg: " + isChecked);
                if (isOnlineOffline) {

                    isOnlineOffline = false;
                    if (isChecked) {

                        HomeMenuActivity.mOnClickRequestes.onSuccessfullOnclickRequest(AppConstants.KEY_VALUE_ONLINE);


                    } else {
                        HomeMenuActivity.mOnClickRequestes.onSuccessfullOnclickRequest(AppConstants.KEY_VALUE_OFFLINE);

                    }
                }

            }
        });

        getDataHealthCheck();


        layoutBottomSheet = mLayouView.findViewById(R.id.bottom_sheet);
        arrowleft = mLayouView.findViewById(R.id.arrow_left);
        arrowright = mLayouView.findViewById(R.id.arrow_right);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        rec_recentbookings = mLayouView.findViewById(R.id.rec_recentbookings);
        rec_recentbookings.setLayoutManager(new LinearLayoutManager(getActivity()));
        resultBeans = new ArrayList<>();
        bookingHistoryAdapter = new BookingHistoryAdapter(resultBeans, this, getActivity());

        rec_recentbookings.setAdapter(bookingHistoryAdapter);


        try {
            getHistoryList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tb_onlineOffline.setVisibility(View.GONE);
        layoutBottomSheet.setVisibility(View.GONE);

        layoutBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                else if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        //btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:

                        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (isAdded()) {
                    //transitionBottomSheetBackgroundColor(slideOffset);
                    animateBottomSheetArrows(slideOffset);
                }
            }
        });
        //desdiag();
    }

    private void animateBottomSheetArrows(float slideOffset) {
        // Animate counter-clockwise
        arrowleft.setRotation(slideOffset * -180);
        // Animate clockwise
        arrowright.setRotation(slideOffset * 180);
    }
    private void transitionBottomSheetBackgroundColor(float slideOffset) {
        int colorFrom = getResources().getColor(R.color.quantum_grey);
        int colorTo = getResources().getColor(R.color.quantum_grey500);
        layoutBottomSheet.setBackgroundColor(interpolateColor(slideOffset,
                colorFrom, colorTo));
    }

    /**
     * This function returns the calculated in-between value for a color
     * given integers that represent the start and end values in the four
     * bytes of the 32-bit int. Each channel is separately linearly interpolated
     * and the resulting calculated values are recombined into the return value.
     *
     * @param fraction The fraction from the starting to the ending values
     * @param startValue A 32-bit int value representing colors in the
     * separate bytes of the parameter
     * @param endValue A 32-bit int value representing colors in the
     * separate bytes of the parameter
     * @return A value that is calculated to be the linearly interpolated
     * result, derived by separating the start and end values into separate
     * color channels and interpolating each one separately, recombining the
     * resulting values in the same way.
     */
    private int interpolateColor(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;
        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;
        return ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));
    }
    private void getHistoryList() throws JSONException {


        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
        jsonObject.put(AppConstants.KEY_TRIP_TYPE, "history");
        jsonObject.put(AppConstants.KEY_LAT, "");
        jsonObject.put(AppConstants.KEY_LANG, "");


        jsonObject.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
        jsonObject.put(AppConstants.KEY_TYPE_FOR, "driver");

        Log.e("fddfgfdg", "Hisotory: "+AppConstants.USER_BOOKINGS +"  "+jsonObject);


        NetworkConn networkConn = NetworkConn.getInstance(getActivity());

        networkConn.makeRequest(getActivity(), networkConn.createRawDataRequest(AppConstants.USER_BOOKINGS, jsonObject.toString()), this, AppConstants.EVENT_USER_BOOKINGS);

    }



    @Override
    public void onPause() {

        Log.d("Pausestate", "pause");
        if (dialogForUserRating != null) {

            if (dialogForUserRating.isShowing())
                dialogForUserRating.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //this.mContext = context;

        AppConstants.KEY_CONTEXT = context;
    }

    @Override
    public void onDestroy() {

        Log.e("Destroyed", "Destroy");
        super.onDestroy();

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {




        try {


            mMap = googleMap;


            try {
                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                boolean success = mMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                getActivity(), R.raw.uber_style));

                if (!success) {
                    Log.e("mapstyle", "Style parsing failed.");
                }
            } catch (Resources.NotFoundException e) {
                Log.e("mapstyle", "Can't find style.", e);
            }

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.getUiSettings().setScrollGesturesEnabled(true);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.5937, 78.9629), 5.0f));

//        Location location = mMap.getMyLocation();
//
//        if (location != null)
//            setMarker(location);


            mMap.setOnMyLocationChangeListener(this);


            TypedValue tv = new TypedValue();
            int actionBarHeight;
            if (getActivity().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());


                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
// position on right bottom
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

                int height = Methods.getDeviceResolutions(getActivity()).heightPixels;
                int width = Methods.getDeviceResolutions(getActivity()).widthPixels;

                Log.e("ghjghjh", "actionBarHeight: " + (height * .4) + "   :: " + (width * .05) + "   ::  " + (height));


                rlp.setMargins(0, (int) (height * 0.4), (int) (width * .05), 0);


            }


            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {


                    Log.d("Camera postion change" + "", cameraPosition + "");
                    mCenterLatLong = cameraPosition.target;


                }
            });

        } catch (Exception e) {

            e.getStackTrace();

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.but_online_offline_online:

                HomeMenuActivity.mOnClickRequestes.onSuccessfullOnclickRequest(AppConstants.KEY_VALUE_ONLINE);


//                new AlertDialog.Builder(getActivity())
//                        .setMessage(R.string.are_you_sure_you_want_to_go_online)
//                        .setCancelable(false)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                HomeMenuActivity.mOnClickRequestes.onSuccessfullOnclickRequest(AppConstants.KEY_VALUE_ONLINE);
//
//
//                            }
//                        })
//                        .setNegativeButton(R.string.no, null)
//                        .show();

                break;


            case R.id.but_online_offline_offline:

                HomeMenuActivity.mOnClickRequestes.onSuccessfullOnclickRequest(AppConstants.KEY_VALUE_OFFLINE);


//                new AlertDialog.Builder(getActivity())
//                        .setMessage(R.string.are_you_sure_you_want_to_go_offline)
//                        .setCancelable(false)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                HomeMenuActivity.mOnClickRequestes.onSuccessfullOnclickRequest(AppConstants.KEY_VALUE_OFFLINE);
//
//
//                            }
//                        })
//                        .setNegativeButton(R.string.no, null)
//                        .show();

                break;


            case R.id.btnCallDriver:


                //  Log.e("Call", "Call: " + "tel:" + tv_booking_accepted_bottom_mobile.getText().toString().trim());

                String number = tv_booking_accepted_bottom_client_name.getTag().toString();

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);

                break;


            case R.id.btn_booking_accepted_bottom_cancel:

                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.are_you_sure_you_want_to_cancel_booking)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                cancelBooking();


                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();

                //emittCancelBooking();
                break;


            case R.id.btn_booking_accepted_bottom_arrived_at_pickup_location:

                AppDialogs.arriveDialog(getActivity(), this);

//                new AlertDialog.Builder(getActivity())
//                        .setMessage(R.string.are_you_sure_you_reach_at_pickup_location)
//                        .setCancelable(false)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                emittIHaveArrive();
//
//
//                            }
//                        })
//                        .setNegativeButton(R.string.no, null)
//                        .show();


                break;


            case R.id.but_bigin_trip:

                AppDialogs.beginTripOtpPopup(getActivity(), "", this);

//

//                new AlertDialog.Builder(getActivity())
//                        .setMessage(R.string.are_you_sure_you_want_to_start_trip)
//                        .setCancelable(false)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                emittBeginTripe();
//
//
//                            }
//                        })
//                        .setNegativeButton(R.string.no, null)
//                        .show();


                break;

            case R.id.but_end_trip:

                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.are_you_sure_you_want_to_end_trip)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                emittSendOTPForEndTrip();


                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .show();


                break;

            case R.id.ib_go:

                Log.e("fgdfgdfg", "YYYYYYY: " + Settings.canDrawOverlays(getActivity()));
                isStarted = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity())) {

                    //If the draw over permission is not available open the settings screen
                    //to grant the permission.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getActivity().getPackageName()));
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                } else {


                    if (mIhaveArrivedAtPickupLocationModel.getResult().getType_of_booking() != null) {
                        if (mIhaveArrivedAtPickupLocationModel.getResult().getType_of_booking().trim().equalsIgnoreCase("1")) {

                            goToMap(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_lat(), mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_long(),
                                    mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_lat(), mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_long(),
                                    getResources().getString(R.string.pickup), "No");
                            break;
                        }

                    }


                    goToMap(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_lat(), mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_long(),
                            mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_lat(), mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_long(),
                            getResources().getString(R.string.pickup), getResources().getString(R.string.destination));
                }


                break;

            case R.id.ib_go_IhaveArrive:

                isStarted = false;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity())) {

                    //If the draw over permission is not available open the settings screen
                    //to grant the permission.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getActivity().getPackageName()));
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                } else {
                    goToMap("" + curentLocation.latitude, "" + curentLocation.longitude,
                            mAcceptedBookingModel.getResult().getBooking_info().getBook_from_lat(), mAcceptedBookingModel.getResult().getBooking_info().getBook_from_long(), getResources().getString(R.string.current), getResources().getString(R.string.pickup));
                }


                break;

            case R.id.iv_healthStatus:


            case R.id.tv_status:

                View view1 = this.getLayoutInflater().inflate(R.layout.easydiag_healthstatus, null);

                new EasyDialog(getActivity())
                        // .setLayoutResourceId(R.layout.layout_tip_content_horizontal)//layout resource id
                        .setLayout(view1)
                        .setBackgroundColor(getActivity().getResources().getColor(R.color.black))
                        // .setLocation(new location[])//point in screen
                        .setLocationByAttachedView(tv_status)
                        .setGravity(EasyDialog.GRAVITY_BOTTOM)
                        .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                        .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                        .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                        .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                        .setTouchOutsideDismiss(true)
                        .setMatchParent(true)
                        .setMarginLeftAndRight(24, 24)
                        .setOutsideColor(getActivity().getResources().getColor(R.color.text_grey_trans))
                        .show();

                break;


            case R.id.chat_text:

                Intent i = new Intent(getActivity(), ChatActivity.class);
                i.putExtra("driver_id",userID);
                startActivity(i);

                break;

            case R.id.but_emer:

                    dialogEmergency();

                break;
        }


    }


    private void dialogEmergency(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_emergency, null);
        dialogBuilder.setView(dialogView);


        LinearLayout ll_police = dialogView.findViewById(R.id.ll_police);
        LinearLayout ll_call = dialogView.findViewById(R.id.ll_call);
        LinearLayout ll_customer = dialogView.findViewById(R.id.ll_customer);

        Button dismiss = dialogView.findViewById(R.id.dismiss);

        ll_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:100"));
                startActivity(intent);
            }
        });

        ll_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+918586024074"));
                startActivity(intent);
            }
        });

        ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog3.dismiss();
            }
        });

        alertDialog3 = dialogBuilder.create();
        alertDialog3.show();


    }

    private void goToMap(String oLat, String oLon, String dLat, String dLon, String pickup, String destination) {

        Locale strLanguage;

        if (AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE).trim().equalsIgnoreCase(AppConstants.KEY_VALUE_ENGLISH)) {

            strLanguage = new Locale("en");

        } else {

            strLanguage = new Locale("hi");
        }


        String uri = "";
        if (destination.trim().equalsIgnoreCase("No")) {

            uri = String.format(strLanguage, "http://maps.google.com/maps"
            );
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        } else {

            uri = String.format(strLanguage, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)",
                    Double.parseDouble(oLat), Double.parseDouble(oLon), pickup,
                    Double.parseDouble(dLat), Double.parseDouble(dLon), destination);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        }


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(intent);
            getActivity().startService(new Intent(getActivity(), HeadService.class));
        } catch (Exception ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(unrestrictedIntent);
                getActivity().startService(new Intent(getActivity(), HeadService.class));
            } catch (Exception innerEx) {
                Toast.makeText(getActivity(), "Please install a maps application", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onSubmitOTP(String otp) {
        emittBeginTripe(otp);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (donationDialog != null) {
            donationDialog.dismiss();
        }


        image_picker = new Image_Picker(getActivity());
        isSet = true;
        checkEmmitMethod();
        getDataHealthCheck();


    }

    private void emittSendOTPForEndTrip() {
        try {

            AppDialogs.showLoader(getActivity());

            //Call for Go online
           /*
                "{
""userid"" : ""20"",
""token"" : ""1435565"",
""language"" : ""hindi"",
""lat"" : ""22.75"",
""lang"" : ""75.52"",
""tripid"" : ""3"",
""typefor"" : ""user"",  user/ driver
}
"
*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_TRIPID, mIhaveArrivedAtPickupLocationModel.getResult().getId());

            jProfileDate.put(AppConstants.KEY_TYPE_FOR, AppConstants.KEY_VALUE_DRIVER);
            jProfileDate.put(AppConstants.KEY_LAT, curentLocation.latitude);
            jProfileDate.put(AppConstants.KEY_LANG, curentLocation.longitude);


            Log.e("dsfds", "emittSendOTPForEndTrip: " + jProfileDate);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_TRIP_END_SEND_SMS, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }

    private void emittEndTripe(String otp) {
        try {

            //Call for Go online
           /*
                "{
""userid"" : ""20"",
""token"" : ""1435565"",
""language"" : ""hindi"",
""lat"" : ""22.75"",
""lang"" : ""75.52"",
""tripid"" : ""3"",
""typefor"" : ""user"",  user/ driver
}
"
*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_TRIPID, mIhaveArrivedAtPickupLocationModel.getResult().getId());

            jProfileDate.put(AppConstants.KEY_TYPE_FOR, AppConstants.KEY_VALUE_DRIVER);
            jProfileDate.put(AppConstants.KEY_LAT, curentLocation.latitude);
            jProfileDate.put(AppConstants.KEY_LANG, curentLocation.longitude);

            // jProfileDate.put(AppConstants.KEY_OTP, otp);
            Log.e("dsfds", "emittEndTripe: " + jProfileDate);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_TRIP_END, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    private void emittBeginTripe(String otp) {


        try {


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_TRIP_ID, mIhaveArrivedAtPickupLocationModel.getResult().getId());


            jProfileDate.put(AppConstants.KEY_LAT, curentLocation.latitude);
            jProfileDate.put(AppConstants.KEY_LANG, curentLocation.longitude);

            //   jProfileDate.put(AppConstants.ANGLE, angle);

            jProfileDate.put(AppConstants.KEY_START_TRIP, otp);
            Log.e("", "emittBeginTripe: " + jProfileDate);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_TRIP_START, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    private void emittIHaveArrive() {
        try {

            //Call for Go online
           /*
                "{
""userid"" : ""20"",
""token"" : ""1435565"",
""language"" : ""hindi"",
""trip_id"" : ""3""
}
"*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_TRIP_ID, mAcceptedBookingModel.getResult().getBooking_info().getId());


            Log.e("dfdsfdsf", "emittIHaveArrive: " + jProfileDate);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_DRIVER_ARRIVED_AT_PICKUP, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    private void emittBookingStatus(String status, String tripId) {
        try {

            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));


            jProfileDate.put(AppConstants.KEY_TRIPID, tripId);


            jProfileDate.put(AppConstants.KEY_STATUS, status);


            Log.e("dfdsfdsf", "emittBookingStatus: " + jProfileDate);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_DRIVER_LAST_TRIP_STATUS, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    private void emittCancelBooking(String id, String comment) {
        try {

            //Call for Go online
           /*
             "{
        userid :   '84',
        token :    '86',
        language : 'hindi',
        typefor : 'user',  user / driver
        tripid : '2',
        cancel_order_id : '4',
        cancel_msg : 'loren lipson',
  }"*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_TRIPID, mAcceptedBookingModel.getResult().getBooking_info().getId());
            jProfileDate.put(AppConstants.KEY_TYPE_FOR, "driver");
            jProfileDate.put(AppConstants.KEY_CANCEL_ORDER_ID, id);
            jProfileDate.put(AppConstants.KEY_CANCEL_MSG, comment);


            Log.e("dfdsfdsf", "emittCancelBooking: " + jProfileDate);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_TRIP_CANCEL, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }

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
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_DRIVER_STATUS, AppConstants.KEY_VALUE_ONLINE);

            SocketConnection.emitToServer(AppConstants.EMIT_ONLIE_OFFLINE, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    private void emittGetProfile() {

        try {

            //Call for get profile
              /*  "{
                ""userid"" : ""20"",
                        ""token"" : ""1435565"",
                        ""language"" : ""hindi""
            }"*/


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));

            SocketConnection.emitToServer(AppConstants.EMIT_GET_PROFILE, jProfileDate);

            Log.d("ProfileData", jProfileDate.toString());

        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }

    private void emmitUserRationg(String rating, String comment) {
        try {

            //Call for Go online
           /*

           "{
        ""userid"":""404"",
        ""tripid"":""1"",
        ""rating_vote"":""2"",
        ""driverid"":""402"",
        ""comment"":""loren lipsonm"",
        ""token"":""459257675""

}"

           */


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, mEndTripModel.getResult().getUser_details().getUser_id());
            jProfileDate.put(AppConstants.KEY_DRIVERID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_TRIPID, mEndTripModel.getResult().getId());
            jProfileDate.put(AppConstants.KEY_RATING_VOTE, rating);
            jProfileDate.put(AppConstants.KEY_COMMENT, comment);
            Log.e(TAG, "emmitUserRationg: " + jProfileDate.toString());
            SocketConnection.emitToServer(AppConstants.EMIT_GET_RATING_TO_USER, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }

    }


    @Override
    public void onSuccessfullGetResponse(final ProfileModel loginModel, final String from) {

        try {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateUI();


                    if (from.trim().equalsIgnoreCase(AppConstants.ON_GET_PROFILE)) {


                        Log.e("dfg", "onSuccessfullGetResponse: " + loginModel.getResult().getDriver_trip_status());

                        if (loginModel.getResult().getDriver_trip_status() == 0) {
                            setDriverInfo(loginModel);

                        } else {

                            emittBookingStatus("" + loginModel.getResult().getDriver_trip_status(), loginModel.getResult().getTripid());

                            Log.e("", "");
                            Log.e("", "");
                        }


                    }
                }
            });


        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    @Override
    public void onSuccessfullGetResponse(final JSONObject json, final String from) {

//            {"status":1,"error_code":0,"error_line":280,"message":"Online status update successfully","result":{"driver_status":"Offline"}}

        ((Activity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    destinationLocation = null;

                    if (from.trim().equalsIgnoreCase(AppConstants.ON_CHANGE_DESTINATION_ADDRESS_DRIVER)) {

                        setDestinationChange(json);

                  /*  mIhaveArrivedAtPickupLocationModel.getResult().setBook_to_address(json.getJSONArray("result").getJSONObject((json.getJSONArray("result").length()-1)).getString("changed_to_address"));

                        Log.e("ghgfh", "fgfgfg: "+json.getJSONArray("result").getJSONObject((json.getJSONArray("result").length()-1)).getString("changed_to_address"));

                        tv_IHA_drop_Off_location.setText(json.getJSONArray("result").getJSONObject((json.getJSONArray("result").length()-1)).getString("changed_to_address"));
*/
                    } else if (from.trim().equalsIgnoreCase(AppConstants.ON_TRIP_END_SEND_SMS)) {

                        emittEndTripe("");

                        // AppDialogs.endTripOtpPopup(getActivity(), "", MapsFragment.this);

                    } else {
                        updateUI();

                        if (from.trim().equalsIgnoreCase(AppConstants.KEY_VALUE_RATE_USER)) {

                            mEndTripModel = new Gson().fromJson(json.toString(), EndTripModel.class);
                            userRatingPopup();

                        } else if (from.trim().equalsIgnoreCase(AppConstants.KEY_VALUE_UPLOAD_BILTI)) {


                            mEndTripModel = new Gson().fromJson(json.toString(), EndTripModel.class);

//                            if (mEndTripModel.getResult().getNeed_builty().equals("1")) {
//
//                                uploadBiltiAlert();
                            // } else {
                            userRatingPopup();
                            // }


                        } else if (from.trim().equalsIgnoreCase(AppConstants.ON_RESPONCE_RATING_TO_USER)) {

                            if (SocketConnection.getSocket() != null && SocketConnection.getSocket().connected()) {

                                emittGetProfile();
                            }

                        } else if (from.trim().equalsIgnoreCase(AppConstants.ON_RESPONSE_TRIP_CANCEL)) {

                            mMap.clear();
                            emittGetProfile();
                        } else if (from.trim().equalsIgnoreCase(AppConstants.ON_RESPONSE_TRIP_END)) {

                            mMap.clear();
//                        {"status":1,"error_code":0,"error_line":967,"booking_id":"","result":"","distance":"0 Km","fare":"10","message":"Trip end successfully."}


                            mEndTripModel = new Gson().fromJson(json.toString(), EndTripModel.class);

                            AppDialogs.fareSummeryPopup(getActivity(), getActivity().getResources().getString(R.string.rupee) + " " + mEndTripModel.getResult().getTotal_fare(), mEndTripModel.getResult().getTotal_distance(), mEndTripModel.getBooking_id(),mEndTripModel.getResult().getIs_online_payment_accept(), MapsFragment.this, MapsFragment.this);

                        } else if (from.trim().equalsIgnoreCase(AppConstants.ON_RESPONSE_TRIP_START)) {

                            if (json.optInt("error_code") == 461) {
                                AppPreference.clear(getActivity());
                                startActivity(new Intent(getActivity(), SplashActivity.class));
                                ((Activity) getActivity()).finishAffinity();
                                return;
                            }

                            setDriverEndTrip(json);

                        } else if (from.trim().equalsIgnoreCase(AppConstants.ON_RESPONSE_BOOKING_ARRIVED_AT_PICKUP)) {

                            setDriverBeginTrip(json);

                        } else if (from.trim().equalsIgnoreCase(AppConstants.ON_RESPONSE_BOOKING_ACCEPT)) {


                            if (json.optInt("error_code") == 461) {


                                String token = json.optString("token");
                                String currentToken = AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN);

                                if (token.equalsIgnoreCase(currentToken)) {

                                } else {

                                    AppPreference.clear(getActivity());
                                    startActivity(new Intent(getActivity(), SplashActivity.class));
                                    ((Activity) getActivity()).finishAffinity();
                                    return;
                                }


                            }

                            setDriverIhaveArrive(json);


                        } else if (from.trim().equalsIgnoreCase(AppConstants.ON_ONLINE_OFFLINE)) {

                            // ll_home_drinver_info.setVisibility(View.VISIBLE);
                            cv_pinlocation.setVisibility(View.VISIBLE);
                            ll_online_offline.setVisibility(View.GONE);


                            JSONObject driver_status = json.getJSONObject("result");

                            if (driver_status.getString("driver_status").trim().equalsIgnoreCase(AppConstants.KEY_VALUE_OFFLINE)) {
                                int p = (int) (Methods.getDeviceResolutions(getActivity()).widthPixels * .1);

                                but_online_offline_online.setVisibility(View.VISIBLE);
                                but_online_offline_offline.setVisibility(View.GONE);
                                // tb_onlineOffline.setPadding(0, 0, p, 0);
                                tb_onlineOffline.setVisibility(View.VISIBLE);
                                layoutBottomSheet.setVisibility(View.VISIBLE);
                                tb_onlineOffline.setChecked(false);


                            } else {

                                int p = (int) (Methods.getDeviceResolutions(getActivity()).widthPixels * .1);
                                // tb_onlineOffline.setPadding(p, 0, 0, 0);
                                tb_onlineOffline.setVisibility(View.VISIBLE);
                                layoutBottomSheet.setVisibility(View.VISIBLE);
                                tb_onlineOffline.setChecked(true);


                                but_online_offline_online.setVisibility(View.GONE);
                                but_online_offline_offline.setVisibility(View.VISIBLE);
                            }
                            isOnlineOffline = true;


                        } else {

                      /*  ll_home_drinver_info.setVisibility(View.VISIBLE);
                        cv_pinlocation.setVisibility(View.VISIBLE);
                        ll_online_offline.setVisibility(View.VISIBLE);

                        JSONObject driver_status = json.getJSONObject("result");

                        if (driver_status.getString("driver_status").trim().equalsIgnoreCase(AppConstants.KEY_VALUE_OFFLINE)) {

                            but_online_offline_online.setVisibility(View.VISIBLE);
                            but_online_offline_offline.setVisibility(View.GONE);

                        } else {

                            but_online_offline_online.setVisibility(View.GONE);
                            but_online_offline_offline.setVisibility(View.VISIBLE);
                        }*/
                        }
                    }

                } catch (Exception e) {

                    Log.e("sfsdf", "EEEEE: " + e.getMessage());
                    e.getStackTrace();
                }
            }
        });


    }

    @Override
    public void onDonateListner(String tripId, String amount, Dialog dialog) {

        // Toast.makeText(getActivity(), "Donation Done", Toast.LENGTH_SHORT).show();
        dialog.dismiss();


//        {
//            "userid":19,
//                "token":2042917955,
//                "version":1,
//                "version":1,
//                "trip_id":16
//            "amount":"10"
//        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jsonObject.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jsonObject.put(AppConstants.KEY_APP_VERSION, String.valueOf(BuildConfig.VERSION_CODE));
            jsonObject.put(AppConstants.TRIP_ID, tripId);
            jsonObject.put(AppConstants.KEY_AMOUNT, amount);
            donateAmount(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void donateAmount(JSONObject jsonObject) {

        NetworkConn.getInstance(getActivity()).makeRequest(getActivity(), NetworkConn.getInstance(getActivity()).createRawDataRequest(AppConstants.DONATE_AMOUNT, jsonObject.toString()), this, AppConstants.EVENT_DONATE_AMOUNT);

    }

    private void getDataHealthCheck(){
        NetworkConn.getInstance(getActivity()).makeRequest(getActivity(), NetworkConn.getInstance(getActivity()).createGetRequest(AppConstants.API_CHECK_HEALTH),this, AppConstants.EVENT_API_CHECK_HEALTH);
    }

    @Override
    public void onSubmitButtonSuccess(String text) {

        emittEndTripe(text);

    }


    private void setDriverIhaveArrive(final JSONObject json) {

        ((Activity) getActivity()).runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                try {


                    ll_booking_accepted_top.setVisibility(View.VISIBLE);
                    ll_booking_accepted_bottom.setVisibility(View.VISIBLE);

                    ib_go_IhaveArrive.setVisibility(View.VISIBLE);

                    mAcceptedBookingModel = new Gson().fromJson(json.toString(), AcceptedBookingModel.class);

                    tv_booking_accepted_top_pickup_location.setText(mAcceptedBookingModel.getResult().getBooking_info().getBook_from_address());
                    tv_booking_accepted_top_Drop_Off_location.setText(mAcceptedBookingModel.getResult().getBooking_info().getBook_to_address());
                    tv_booking_accepted_bottom_booking_id.setText(mAcceptedBookingModel.getResult().getBooking_info().getBooking_id());
                    tv_booking_accepted_bottom_vehicle_name.setText(mAcceptedBookingModel.getResult().getBooking_info().getVehicle_name());
                    userID = mAcceptedBookingModel.getResult().getBooking_info().getUser_details().getUser_id();
                    tv_booking_accepted_bottom_client_name.setText(mAcceptedBookingModel.getResult().getBooking_info().getUser_details().getFirst_name());
                    tv_booking_accepted_bottom_client_name.setTag(mAcceptedBookingModel.getResult().getBooking_info().getUser_details().getMobile());

                    //    tv_booking_accepted_bottom_mobile.setText(mAcceptedBookingModel.getResult().getBooking_info().getUser_details().getMobile());
                    tv_booking_accepted_bottom_ETA.setText(mAcceptedBookingModel.getResult().getBooking_info().getMinutes_to_reach());
                    tv_booking_accepted_bottom_vehicle_number.setText(mAcceptedBookingModel.getResult().getBooking_info().getDriver_details().getVehicle_number());
                    ratingBar.setRating(mAcceptedBookingModel.getResult().getBooking_info().getUser_details().getAvg_rating());

                    Glide.with(getActivity()).load(mAcceptedBookingModel.getResult().getBooking_info().getUser_details().getProfile_img()).into(accept_bottom_user_image);


                    //tv_booking_accepted_bottom_price.setText(getActivity().getResources().getString(R.string.rupee) + " " + mAcceptedBookingModel.getResult().getBooking_info().getBooking_fee());

                    if (mAcceptedBookingModel.getResult().getBooking_info().getType_of_booking().trim().equalsIgnoreCase("1")) {

                        ll_Drop_Off_location.setVisibility(View.GONE);
                    }


                    LatLng origin = curentLocation;
                    LatLng destination = new LatLng(Double.parseDouble(mAcceptedBookingModel.getResult().getBooking_info().getBook_from_lat()), Double.parseDouble(mAcceptedBookingModel.getResult().getBooking_info().getBook_from_long()));
                    //destinationLocation = destination;

                    Log.e("fdgfdgfd", "setDriverIhaveArrive: " + origin + " ::::  " + destination);


                    new DrawPathmain(getActivity(), mMap, origin, destination, "", getResources().getString(R.string.pickup) + " " + mAcceptedBookingModel.getResult().getBooking_info().getMinutes_to_reach());


                } catch (Exception e) {
                    e.getStackTrace();
                }

            }
        });

    }

    private void setDriverBeginTrip(final JSONObject json) {
        ((Activity) getActivity()).runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                try {
                    ll_IHA.setVisibility(View.VISIBLE);
                    but_bigin_trip.setVisibility(View.VISIBLE);

                    Log.d("", "");


                    mIhaveArrivedAtPickupLocationModel = new Gson().fromJson(json.toString(), IhaveArrivedAtPickupLocationModel.class);


                    tv_IHA_pickup_location.setText(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_address());
                    tv_IHA_drop_Off_location.setText(mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_address());
                    tv_IHA_name.setText(getActivity().getResources().getString(R.string.text_booking_id) + " " + mIhaveArrivedAtPickupLocationModel.getResult().getBooking_id());
                    tv_IHA_time.setText(mIhaveArrivedAtPickupLocationModel.getResult().getBooking_date_time());

                    tv_IHA_reach_time.setText(mIhaveArrivedAtPickupLocationModel.getResult().getMinutes_to_reach() + " " + getActivity().getResources().getString(R.string.to_teach_destination));
                    tv_IHA_reach_time.setVisibility(View.GONE);


                    if (mIhaveArrivedAtPickupLocationModel.getResult().getType_of_booking().trim().equalsIgnoreCase("1")) {

                        iv_IHA_pickup_location_icon.setVisibility(View.GONE);
                        tv_IHA_drop_Off_location.setVisibility(View.GONE);
                        iv_IHA_pickup_location.setVisibility(View.VISIBLE);

                    }


                    LatLng origin = new LatLng(Double.parseDouble(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_lat()), Double.parseDouble(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_long()));

                    LatLng destination = new LatLng(Double.parseDouble(mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_lat()), Double.parseDouble(mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_long()));
                    destinationLocation = destination;


                    new DrawPathmain(getActivity(), mMap, origin, destination, getResources().getString(R.string.pickup), getResources().getString(R.string.destination) + " " + mIhaveArrivedAtPickupLocationModel.getResult().getTotal_time());


                } catch (Exception e) {
                    e.getStackTrace();
                }

            }
        });

    }


    private void setDestinationChange(final JSONObject json) {

        ((Activity) getActivity()).runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                try {


                    final JSONObject jData = json.getJSONArray("result").getJSONObject(json.getJSONArray("result").length() - 1);


                    tv_IHA_drop_Off_location.setText(jData.getString("changed_to_address"));


                    final LatLng origin = new LatLng(Double.parseDouble(jData.getString("changed_from_lat")), Double.parseDouble(jData.getString("changed_from_long")));

                    final LatLng destination = new LatLng(Double.parseDouble(jData.getString("changed_to_lat")), Double.parseDouble(jData.getString("changed_to_long")));
                    destinationLocation = destination;

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    final AlertDialog alertDialog;
// ...Irrelevant code for customizing the buttons and title
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_destination_change, null);
                    dialogBuilder.setView(dialogView);

                    TextView tv_Destination = dialogView.findViewById(R.id.tv_destination);
                    tv_Destination.setText(jData.getString("changed_to_address"));
                    Button btn_apply = dialogView.findViewById(R.id.btn_apply);
                    SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                            .findFragmentById(R.id.mapdiag);

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //googleMap.setMapStyle(R.raw.uber_style);
                            googleMap.setMapStyle(
                                    MapStyleOptions.loadRawResourceStyle(
                                            getActivity(), R.raw.uber_style));
                            googleMap.getUiSettings().setZoomControlsEnabled(false);
                            googleMap.getUiSettings().setScrollGesturesEnabled(true);

                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 14.0f));
                            // Creating a marker
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Setting the position for the marker
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin1));
                            markerOptions.position(destination);
                            googleMap.addMarker(markerOptions);

                        }
                    });

                    alertDialog = dialogBuilder.create();
                    alertDialog.show();
                    btn_apply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            //new DrawPathmain(getActivity(), mMap, origin, destination, getResources().getString(R.string.pickup), getResources().getString(R.string.destination) + " " + mIhaveArrivedAtPickupLocationModel.getResult().getTotal_time());

                            alertDialog.dismiss();

                        }
                    });
                    new DrawPathmain(getActivity(), mMap, origin, destination, getResources().getString(R.string.pickup), getResources().getString(R.string.destination) + " " + mIhaveArrivedAtPickupLocationModel.getResult().getTotal_time());


                } catch (Exception e) {
                    e.getStackTrace();
                }

            }
        });

    }


    private void setDriverEndTrip(final JSONObject json) {

        ((Activity) getActivity()).runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                try {


                    ll_IHA.setVisibility(View.VISIBLE);
                    but_end_trip.setVisibility(View.VISIBLE);
                    ll_end_trip.setVisibility(View.VISIBLE);
                    tv_drop_off.setVisibility(View.VISIBLE);
                    ib_go.setVisibility(View.VISIBLE);

                    mIhaveArrivedAtPickupLocationModel = new Gson().fromJson(json.toString(), IhaveArrivedAtPickupLocationModel.class);

                    tv_IHA_pickup_location.setText(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_address());
                    tv_IHA_drop_Off_location.setText(mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_address());
                    tv_IHA_name.setText(getActivity().getResources().getString(R.string.text_booking_id) + " " + mIhaveArrivedAtPickupLocationModel.getResult().getBooking_id());
                    tv_IHA_time.setText(mIhaveArrivedAtPickupLocationModel.getResult().getBooking_date_time());

                    tv_IHA_reach_time.setText(mIhaveArrivedAtPickupLocationModel.getResult().getMinutes_to_reach() + " " + getActivity().getResources().getString(R.string.to_teach_destination));
                    tv_IHA_reach_time.setVisibility(View.VISIBLE);

                    if (mIhaveArrivedAtPickupLocationModel.getResult().getType_of_booking().trim().equalsIgnoreCase("1")) {

                        tv_IHA_reach_time.setVisibility(View.GONE);
                        iv_IHA_pickup_location_icon.setVisibility(View.GONE);
                        tv_IHA_drop_Off_location.setVisibility(View.GONE);
                        iv_IHA_pickup_location.setVisibility(View.VISIBLE);

                    }


                    LatLng origin = new LatLng(Double.parseDouble(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_lat()), Double.parseDouble(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_long()));

                    LatLng destination = new LatLng(Double.parseDouble(mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_lat()), Double.parseDouble(mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_long()));
                    destinationLocation = destination;


                    new DrawPathmain(getActivity(), mMap, origin, destination, getResources().getString(R.string.pickup), getResources().getString(R.string.destination) + " " + mIhaveArrivedAtPickupLocationModel.getResult().getTotal_time());


                } catch (Exception e) {
                    e.getStackTrace();
                }

            }
        });

    }

    private void setDriverInfo(final ProfileModel loginModel) {

        try {

            ((Activity) getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Driver Info
                    Log.e("dfdf", "setDriverInfo: " + loginModel.getResult().getFirst_name());

                    //ll_home_drinver_info.setVisibility(View.VISIBLE);
                    cv_pinlocation.setVisibility(View.VISIBLE);

                    tv_home_drive_vehicle_info.setText(loginModel.getResult().getVehicle_number());
                    tv_home_drive_name.setText(loginModel.getResult().getFirst_name());
//            rb_home_drive_rating.setRating(loginModel.getResult().get);


                    // Glide.with(getActivity()).load(loginModel.getResult().getProfile_img())
                    Glide.with(getActivity()).load(loginModel.getResult().getProfile_img())
                            .thumbnail(0.5f)
                            .into(iv_home_drive_info);

                    if (loginModel.getResult().getDriver_status().trim().equalsIgnoreCase(AppConstants.KEY_VALUE_OFFLINE)) {
                        ll_online_offline.setVisibility(View.GONE);
                        but_online_offline_online.setVisibility(View.VISIBLE);
                        but_online_offline_offline.setVisibility(View.GONE);
                        tb_onlineOffline.setVisibility(View.VISIBLE);
                        layoutBottomSheet.setVisibility(View.VISIBLE);
                        tb_onlineOffline.setChecked(false);
                        //  tb_onlineOffline.setPadding(0, 0, 0, 0);


                    } else {
                        ll_online_offline.setVisibility(View.GONE);
                        but_online_offline_online.setVisibility(View.GONE);
                        but_online_offline_offline.setVisibility(View.VISIBLE);
                        tb_onlineOffline.setVisibility(View.VISIBLE);
                        layoutBottomSheet.setVisibility(View.VISIBLE);
                        tb_onlineOffline.setChecked(true);

                        int p = (int) (Methods.getDeviceResolutions(getActivity()).widthPixels * .1);
                        // tb_onlineOffline.setPadding(p, 0, 0, 0);


                    }


                    isOnlineOffline = true;

                    tv_home_drive_vehicle_info.setText(loginModel.getResult().getDriver_vehicle() + " | " + loginModel.getResult().getVehicle_number());

                    rb_home_drive_rating.setRating((float) loginModel.getResult().getRating());
//
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onMyLocationChange(Location location) {

        /*this is comment by ashu*/

       /* try {
            tv_pinlocation_location.setText(Methods.getAddress(getActivity(), location.getLatitude(), location.getLongitude()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Log.d("LocationBearing", "" + location.getBearing());

        angle = location.getBearing();
        AppConstants.ANGLE_VALUE = angle;

        // Toast.makeText(getActivity(), ""+angle, Toast.LENGTH_SHORT).show();

        emittUpdateDriverLatlng(location.getLatitude(), location.getLongitude());

        // mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 60, location.getBearing())));


        setMarker(location);
        checkDriverOnPath(location);

    }

    private void setMarker(Location location) {

        upperLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);


        curentLocation = new LatLng(location.getLatitude(), location.getLongitude());

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)             // Sets the center of the map to current location
                .zoom(15)                   // Sets the zoom
                .bearing(location.getBearing()) // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                .build();

        if (isSet) {
            isSet = false;

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }

    private void checkDriverOnPath(Location location) {

        if (destinationLocation != null) {

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


            boolean isPolylineClicked = false;
            for (Polyline polyline : DrawPathmain.polylines) {
                if (PolyUtil.isLocationOnPath(latLng, polyline.getPoints(), false, 10)) {
                    isPolylineClicked = true;
                }
            }

//            if (!isPolylineClicked) {
//                // Other OnMapClickListener behavior
//
//                new DrawPathmain(getActivity(), mMap, latLng, destinationLocation, "");
//
//            }

        }


//        if (destinationLocation != null) {
//
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//
//            boolean isPolylineClicked = true;
//
//            if (DrawPathmain.polylines != null) {
//
//                if (DrawPathmain.polylines.size() > 1) {
//
//                    for (Polyline polyline : DrawPathmain.polylines) {
//                        if (!PolyUtil.isLocationOnPath(latLng, polyline.getPoints(), false, 10)) {
//                            isPolylineClicked = false;
//                        }
//                    }
//                }
//            }
//
//            if (!isPolylineClicked) {
//                // Other OnMapClickListener behavior
//
//                new DrawPathmain(getActivity(), mMap, latLng, destinationLocation, "");
//
//            }
//
//        }

    }


    @Override
    public void onClickOK() {

        // uploadBiltiAlert();

        userRatingPopup();


    }


//    private String getDirectionsUrl(LatLng origin, LatLng dest) {
//
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//
//        // Sensor enabled
//        String sensor = "sensor=false";
//        String mode = "mode=driving";
//
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
//
//        // Output format
//        String output = "json";
//
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyDoOdu4ky_wGwjM8GDsrkD1ipRt82Ac8q4";
//
//
//        return url;
//    }
//
//    private String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try {
//            URL url = new URL(strUrl);
//
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            urlConnection.connect();
//
//            iStream = urlConnection.getInputStream();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            data = sb.toString();
//
//            br.close();
//
//        } catch (Exception e) {
//            Log.d("Exception", e.toString());
//        } finally {
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }

    private void updateUI() {
///////// Crash
       /* (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mMap != null)
                    mMap.clear();
                ll_home_drinver_info.setVisibility(View.GONE);
                cv_pinlocation.setVisibility(View.GONE);
                ll_online_offline.setVisibility(View.GONE);
                tb_onlineOffline.setVisibility(View.GONE);
                ll_booking_accepted_top.setVisibility(View.GONE);
                ll_booking_accepted_bottom.setVisibility(View.GONE);
                ib_go.setVisibility(View.GONE);
                ib_go_IhaveArrive.setVisibility(View.GONE);
                ll_IHA.setVisibility(View.GONE);
                but_bigin_trip.setVisibility(View.GONE);
                but_end_trip.setVisibility(View.GONE);
                tv_drop_off.setVisibility(View.GONE);
            }
        });*/
        if (mMap != null)
            mMap.clear();
        ll_home_drinver_info.setVisibility(View.GONE);
        cv_pinlocation.setVisibility(View.GONE);
        ll_online_offline.setVisibility(View.GONE);
        tb_onlineOffline.setVisibility(View.GONE);
        layoutBottomSheet.setVisibility(View.GONE);
        ll_booking_accepted_top.setVisibility(View.GONE);
        ll_booking_accepted_bottom.setVisibility(View.GONE);
        ib_go.setVisibility(View.GONE);
        ib_go_IhaveArrive.setVisibility(View.GONE);
        ll_IHA.setVisibility(View.GONE);
        but_bigin_trip.setVisibility(View.GONE);
        but_end_trip.setVisibility(View.GONE);
        ll_end_trip.setVisibility(View.GONE);

        tv_drop_off.setVisibility(View.GONE);

    }

    private void checkEmmitMethod() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        Log.d("ProfileDetail", response.toString());

        try {

            if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_GET_TYPE)) {

                DeclineRequestModel mDeclineRequestModel = new Gson().fromJson(response.toString(), DeclineRequestModel.class);
                mDeclineRequestList.clear();
                mDeclineRequestList.addAll(mDeclineRequestModel.getResult().getDecline_request());
                AppDialogs.declineRequestPopup(getActivity(), mDeclineRequestList, this);

            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_UPLOAD_BILTI)) {

                AppDialogs.showToast(getActivity(), response.getString(AppConstants.KEY_MESSAGE));
                HomeMenuActivity.isUploadBitly = true;
                userRatingPopup();

            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_PROFILE_DETAIL)) {

                // ll_home_drinver_info.setVisibility(View.VISIBLE);

                LoginModel loginModel = new Gson().fromJson(response.toString(), LoginModel.class);


                Glide.with(iv_home_drive_info).load(loginModel.getResult().getProfile_img()).into(iv_home_drive_info);
                tv_home_drive_name.setText(loginModel.getResult().getFirst_name());
                rb_home_drive_rating.setRating((float) loginModel.getResult().getRating());
                tv_home_drive_vehicle_info.setText(loginModel.getResult().getVehicle_number());

            } else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_DONATE_AMOUNT)) {

                if (response.optInt("status") == 1) {

                    showDonationCompleteDialog();

                    // uploadBiltiAlert();

                } else {

                    Toast.makeText(getActivity(), response.optString("message"), Toast.LENGTH_SHORT).show();

                }

            }else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_API_CHECK_HEALTH)) {
                Log.e("healthcheck",response.toString());

                if (response.optInt("status") == 1){

                    try{

                        JSONObject jsonObject = response.getJSONObject("result");

                        String healthStatus = jsonObject.optString("health_status");
                        String expiredate = jsonObject.optString("health_checkup_expire_date");


                        if (healthStatus.equalsIgnoreCase("Healthy")){
                            iv_healthStatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_petrol_station_healthy));
                        }

                        if (healthStatus.equalsIgnoreCase("Normal")){
                            iv_healthStatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_petrol_station_warning));

                        }

                        if (healthStatus.equalsIgnoreCase("Unhealthy")){
                            iv_healthStatus.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_petrol_station_unhealthy));

                        }

                            String originalString = expiredate;

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = new Date();
                            System.out.println(formatter.format(date));



                            //Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
                            findDifference(formatter.format(date),originalString);
                            //System.out.println("\n"+newstr+"\n");





                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }else {

                    Toast.makeText(getActivity(), response.optString("message"), Toast.LENGTH_SHORT).show();
                }

            }else if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_USER_BOOKINGS)){


                    if (response != null) {

                        Log.e("HistoryRecords", response.toString());


                        if (response.getInt(AppConstants.KEY_STATUS) == 0) {

                            Log.d("HistoryRecord","NoHistory");
                            //setNoRecordFound();

                            bookings.setText("No booking Yet");


                        } else {
                            BookingDetailsBeans bookingDetailsBeans = new Gson().fromJson(response.toString(), BookingDetailsBeans.class);

                            //tv_norecordfound.setVisibility(View.GONE);
                            rec_recentbookings.setVisibility(View.VISIBLE);
                            resultBeans.addAll(bookingDetailsBeans.result);
                            bookingHistoryAdapter.notifyDataSetChanged();

                        }



                    } else {

                        //setNoRecordFound();

                        //  Toast.makeText(mContext, R.string.no_booking_found, Toast.LENGTH_SHORT).show();
                    }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void showDonationCompleteDialog() {

        donationDialog = new Dialog(getActivity());

        donationDialog.setContentView(R.layout.donation_done_layout);

        donationDialog.setCancelable(false);

        donationDialog.findViewById(R.id.bt_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  uploadBiltiAlert();

                userRatingPopup();


                donationDialog.dismiss();
            }
        });

        donationDialog.show();

    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }

    @Override
    public void onDeclineRequestSubmitButtonClick(String id, String comment) {

        emittCancelBooking(id, comment);

    }

//    private class DownloadTask extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected String doInBackground(String... url) {
//
//            String data = "";
//
//            try {
//                data = downloadUrl(url[0]);
//            } catch (Exception e) {
//                Log.d("Background Task", e.toString());
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            ParserTask parserTask = new ParserTask();
//
//
//            parserTask.execute(result);
//
//        }
//    }
//
//    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
//
//        // Parsing the data in non-ui thread
//        @Override
//        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
//
//            JSONObject jObject;
//            List<List<HashMap<String, String>>> routes = null;
//
//            try {
//                jObject = new JSONObject(jsonData[0]);
//                DirectionsJSONParser parser = new DirectionsJSONParser();
//
//                routes = parser.parse(jObject);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return routes;
//        }
//
//        @Override
//        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//            ArrayList points = null;
//            PolylineOptions lineOptions = null;
//            MarkerOptions markerOptions = new MarkerOptions();
//
//            for (int i = 0; i < result.size(); i++) {
//                points = new ArrayList();
//                lineOptions = new PolylineOptions();
//
//                List<HashMap<String, String>> path = result.get(i);
//
//                for (int j = 0; j < path.size(); j++) {
//                    HashMap point = path.get(j);
//
//                    double lat = Double.parseDouble(String.valueOf(point.get("lat")));
//                    double lng = Double.parseDouble(String.valueOf(point.get("lng")));
//                    LatLng position = new LatLng(lat, lng);
//
//                    points.add(position);
//                }
//
//                lineOptions.addAll(points);
//                lineOptions.width(12);
//                lineOptions.color(Color.RED);
//                lineOptions.geodesic(true);
//
//            }
//
//// Drawing polyline in the Google Map for the i-th route
//
//            if (lineOptions != null)
//                mMap.addPolyline(lineOptions);
//        }
//    }


    private void cancelBooking() {

        getDeclineRequest();

    }

    private void getDeclineRequest() {

        try {
            NetworkConn networkConn = NetworkConn.getInstance(getActivity());
            JSONObject jdata = new JSONObject();
            jdata.put(AppConstants.KEY_TYPES, "decline_request");

            Log.e("Send Response", "Send Response: " + AppConstants.API_GET_TYPE + " :: " + jdata);

            networkConn.makeRequest(getActivity(), networkConn.createRawDataRequest(AppConstants.API_GET_TYPE, jdata.toString()), this, AppConstants.EVENT_API_GET_TYPE);

        } catch (Exception e) {

            Log.e("Exception", "getDeclineRequest: " + e.getMessage());
        }

    }

    @Override
    public void onUserRatingSubmitButtonClick(String rating, String comment) {

        emmitUserRationg(rating, comment);

    }


    private void pickPhoto() {

        image_picker.imageOpenCamera();
    }


    private void uploadBiltiAlert() {

        if (myAlertDialog != null && myAlertDialog.isShowing()) {

            myAlertDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.you_want_to_upload_bilty_slip);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                HomeMenuActivity.isUploadBitly = false;
                pickPhoto();

            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                userRatingPopup();

            }
        });

        builder.setCancelable(false);
        myAlertDialog = builder.create();
        myAlertDialog.show();
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (Settings.canDrawOverlays(getActivity())) {
                if (isStarted) {
                    goToMap(mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_lat(), mIhaveArrivedAtPickupLocationModel.getResult().getBook_from_long(),
                            mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_lat(), mIhaveArrivedAtPickupLocationModel.getResult().getBook_to_long(), getResources().getString(R.string.pickup), getResources().getString(R.string.destination));

                } else {

                    goToMap("" + curentLocation.latitude, "" + curentLocation.longitude,
                            mAcceptedBookingModel.getResult().getBooking_info().getBook_from_lat(), mAcceptedBookingModel.getResult().getBooking_info().getBook_from_long(), getResources().getString(R.string.current), getResources().getString(R.string.pickup));

                }


            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void uploadBiltySlip(String biltySlipPath) {
        try {
            NetworkConn networkConn = NetworkConn.getInstance(getActivity());
            JSONObject jdata = new JSONObject();

            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put(AppConstants.KEY_TRIPID, mEndTripModel.getResult().getId());
            hashMap.put(AppConstants.KEY_IMG_BILTI, biltySlipPath);

           /* tripid:1,
                    img_bilti:abc.png*/
            Log.e("Send Registration", "Registration: " + AppConstants.API_UPLOAD_BILTI + "  :: " + hashMap.toString());

            networkConn.makeRequest(getActivity(), networkConn.createFormDataRequest(AppConstants.API_UPLOAD_BILTI, hashMap, 1), this, AppConstants.EVENT_API_UPLOAD_BILTI);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void userRatingPopup() {


        ((Activity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {


                Log.d("DialogState", "" + dialogForUserRating);


                if (dialogForUserRating != null) {
                    dialogForUserRating.dismiss();
                }

                //  Log.d("DialogState",""+dialogForUserRating +"bool"+dialogForUserRating.isShowing());


                final RatingBar ratingBar;
                final EditText et_Ratingcomment;

                Button btn_rating;

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.user_rating_popup, null);

                ratingBar = view.findViewById(R.id.ratingBar);
                et_Ratingcomment = view.findViewById(R.id.et_Ratingcomment);
                btn_rating = view.findViewById(R.id.btn_rating);
                CircleImageView circleImageView = view.findViewById(R.id.userImage);
                TextView user_name = view.findViewById(R.id.user_name);

                Glide.with(getActivity())
                        .load(mEndTripModel.getResult().getUser_details().getProfile_img())
                        .into(circleImageView);
                Log.e("imageurl",mEndTripModel.getResult().getUser_details().getProfile_img());
                user_name.setText(mEndTripModel.getResult().getUser_details().getFirst_name()+" "+mEndTripModel.getResult().getUser_details().getLast_name());


                dialogForUserRating = new Dialog(getActivity(), R.style.full_screen_dialog);
                dialogForUserRating.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogForUserRating.setContentView(view);
                dialogForUserRating.setCancelable(false);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialogForUserRating.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;

                dialogForUserRating.getWindow().setAttributes(lp);

                btn_rating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (ratingBar.getRating() != 0) {

                            dialogForUserRating.dismiss();


                            onUserRatingSubmitButtonClick("" + ratingBar.getRating(), et_Ratingcomment.getText().toString());

                        } else {

                            AppDialogs.showToast(getActivity(), getActivity().getResources().getString(R.string.error_please_rate_user));
                        }

                    }
                });

                dialogForUserRating.show();

            }
        });


    }

    @Override
    public void onContinue() {

        emittIHaveArrive();

    }

    @Override
    public void onCancel() {

    }


//    private void getProfileDetail() throws JSONException {
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put(AppConstants.DRIVE_USER_ID, AppPreference.loadStringPref(getActivity(), KEY_USER_ID));
//
//        NetworkConn networkConn = NetworkConn.getInstance(getActivity());
//
//        Log.e("Send Response", "Send Response: " + AppConstants.API_MOBLINE_NUMBER_CHECK + " :: " + jsonObject);
//
//        networkConn.makeRequest(getActivity(), networkConn.createRawDataRequest(AppConstants.PROFILE_DETAIl, jsonObject.toString()), this, AppConstants.EVENT_PROFILE_DETAIL);
//
//    }


    private void emittUpdateDriverLatlng(double latitude, double longitude) {

       /* try {

            //Call for update lat lng of driver
           *//* "{
            ""userid"" : ""20"",
                    ""token"" : ""1435565"",
                    ""language"" : ""hindi""
            ""lat"" : ""22.75"",
                    ""lang"" : ""75.52"",
                    ""tripid"" : ""3""
        }"*//*


            JSONObject jProfileDate = new JSONObject();
            jProfileDate.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_USER_ID));
            jProfileDate.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
            jProfileDate.put(AppConstants.KEY_LANGUAGE, AppPreference.loadStringPref(getActivity(), AppPreference.KEY_LANGUAGE));
            jProfileDate.put(AppConstants.KEY_LAT, latitude);
            jProfileDate.put(AppConstants.KEY_LANG, longitude);
            jProfileDate.put(AppConstants.KEY_TRIP_ID, AppConstants.KEY_VALUE_TRIPID);
            jProfileDate.put(AppConstants.ANGLE, AppConstants.ANGLE_VALUE);


            Log.e("sdfdsfd", "emittUpdateDriverLatlng: " + jProfileDate);
            SocketConnection.emitToServer(AppConstants.EMIT_GET_DRIVER_CURRENT_LOCATION_UPDATE, jProfileDate);


        } catch (Exception e) {

            Log.e(TAG, "onConnect: " + e.getMessage());
        }
*/
    }


    private void getPerdayRide() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstants.GET_PERDAY_AMOUNT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("PerdayEarning", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("status");
                            String message = jsonResponse.getString("message");
                            if (status == 1) {
                                JSONObject jsonData = jsonResponse.getJSONObject("result");

                                int total_ride = jsonData.optInt("total_ride");
                                int total_amount = jsonData.optInt("total_amount");
                                if (getActivity() != null) {
                                    tvTotalRide.setText(String.format("%s%d", getActivity().getResources().getString(R.string.ride_string), total_ride));
                                    tvTotalAmount.setText(String.format("%s%d", getActivity().getResources().getString(R.string.amount_String), +total_amount));
                                }

                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("vollyerror", String.valueOf(error));
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("userid", AppPreference.loadStringPref(getContext(), AppPreference.KEY_USER_ID));
                headers.put("token", AppPreference.loadStringPref(getActivity(), AppPreference.KEY_TOKEN));
                Log.e("headerput", headers + "");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    static void
    findDifference(String start_date,
                   String end_date)
    {

        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss");

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            // Calucalte time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            // Calucalte time difference in
            // seconds, minutes, hours, years,
            // and days
            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;

            // Print the date difference in
            // years, in days, in hours, in
            // minutes, and in seconds

            System.out.print(
                    "Difference "
                            + "between two dates is: ");

            System.out.println(
                    difference_In_Years
                            + " years, "
                            + difference_In_Days
                            + " days, "
                            + difference_In_Hours
                            + " hours, "
                            + difference_In_Minutes
                            + " minutes, "
                            + difference_In_Seconds
                            + " seconds");
        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int position) {
        String bookingId = resultBeans.get(position).booking_id;

        Fragment fragment = new ReportIssueFragmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BOOKING_ID, bookingId);
        bundle.putString(AppConstants.USER_EMAIL, resultBeans.get(position).user_details.email);
        bundle.putString(AppConstants.MOBILE_NUMBER, resultBeans.get(position).user_details.mobile);
        bundle.putString(AppConstants.TRIP_ID, resultBeans.get(position).id);
        fragment.setArguments(bundle);

        if (getActivity() != null) {
            HomeMenuActivity.mOnFragmentChange.onFragmentChange(fragment, getString(R.string.nav_report_issue));
        }
    }


}
