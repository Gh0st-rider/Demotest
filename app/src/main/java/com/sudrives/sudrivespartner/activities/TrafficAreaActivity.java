package com.sudrives.sudrivespartner.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.TrafficAreaModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;

import org.json.JSONObject;

import java.util.Locale;

public class TrafficAreaActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, NetworkConn.OnRequestResponse {

    private GoogleMap mMap;
    TextView bagecount;
    TrafficAreaModel mTrafficAreaModel;

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
        //setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_area);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        init();


    }

    private void init() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bagecount = findViewById(R.id.tv_notification_Count);


        setValue();


    }

    private void setValue() {

        if (TextUtils.isEmpty(AppConstants.notificationCount)) {

            bagecount.setVisibility(View.GONE);

        } else {

            bagecount.setVisibility(View.VISIBLE);

            bagecount.setText(AppConstants.notificationCount);

        }


        findViewById(R.id.ib_back_button).setOnClickListener(this);
        findViewById(R.id.iv_notification).setOnClickListener(this);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.5937, 78.9629), 5.0f));
        trafficArea();
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
        AppConstants.KEY_CONTEXT = TrafficAreaActivity.this;
        if (TextUtils.isEmpty(AppConstants.notificationCount)) {

            bagecount.setVisibility(View.GONE);

        } else {

            bagecount.setVisibility(View.VISIBLE);

            bagecount.setText(AppConstants.notificationCount);

        }
    }

    private void trafficArea() {

        try {

            NetworkConn networkConn = NetworkConn.getInstance(TrafficAreaActivity.this);
            JSONObject jdata = new JSONObject();

            jdata.put("latitude", AppConstants.VALUE_LATITUDE);
            jdata.put("longitude", AppConstants.VALUE_LONGITUDE);

            networkConn.makeRequest(TrafficAreaActivity.this, networkConn.createRawDataRequest(AppConstants.API_GET_MAXBOOKINGADDRESS, jdata.toString()), this, AppConstants.EVENT_API_GET_MAXBOOKINGADDRESS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        AppDialogs.hideLoader();

        Log.e("gggggggg", "OIOPOIOP: " + response);

        if (response.optInt("status") == 1) {


            if (strEventType.trim().equals(AppConstants.EVENT_API_GET_MAXBOOKINGADDRESS)) {
                mTrafficAreaModel = new Gson().fromJson(response.toString(), TrafficAreaModel.class);
                setDateForMarker(mTrafficAreaModel);
            }

        }

    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }


    private void setDateForMarker(TrafficAreaModel mTrafficAreaModel) {

        mMap.clear();

        Log.e("ffff", "Size: " + mTrafficAreaModel.result.size());

        if (mTrafficAreaModel.result.size() > 0) {
            new AssyncTaskForMarker(0).execute();
        }

        /*for (int i = 0; i < mTrafficAreaModel.result.size(); i++) {
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(Double.parseDouble(mTrafficAreaModel.result.get(i).latitude), Double.parseDouble(mTrafficAreaModel.result.get(i).longitude));
            mMap.addMarker(new MarkerOptions().position(sydney).title(mTrafficAreaModel.result.get(i).address));
            if (i == (mTrafficAreaModel.result.size() - 1))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));
        }*/

    }

    class AssyncTaskForMarker extends AsyncTask<Integer, Integer, Integer> {
        int position;

        public AssyncTaskForMarker(int position) {

            this.position = position;

        }


        @Override
        protected Integer doInBackground(Integer... strings) {




            return position;
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);

            LatLng sydney = new LatLng(Double.parseDouble(mTrafficAreaModel.result.get(s).latitude), Double.parseDouble(mTrafficAreaModel.result.get(s).longitude));
            mMap.addMarker(new MarkerOptions().position(sydney).title(mTrafficAreaModel.result.get(s).address));
            if (position == (mTrafficAreaModel.result.size() - 1))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));


            s = s + 1;

            if (s < mTrafficAreaModel.result.size()){

                new AssyncTaskForMarker(s).execute();
            }


        }
    }
}
