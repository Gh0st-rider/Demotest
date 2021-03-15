package com.sudrives.sudrivespartner.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.adapters.NotificationListAdapter;
import com.sudrives.sudrivespartner.models.NotificationModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.interfaces.NotificationClickListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, NetworkConn.OnRequestResponse, NotificationClickListner {

    ImageButton backButton;
    RecyclerView notificationList;
    NotificationListAdapter notificationListAdapter;
    List<NotificationModel.ResultBean> mList = new ArrayList<>();
    private int pageNo = 1;
    private RelativeLayout rl_notification;
    private TextView tv_norecordfound;

    // ProgressBar progressBar;
    private int notificationId;
    private int pos;

    TextView tvClearAll;


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
        setContentView(R.layout.activity_notification);

        rl_notification = findViewById(R.id.rl_notification);
        tv_norecordfound = findViewById(R.id.tv_norecordfound);
        backButton = findViewById(R.id.ib_back_button);
        notificationList = findViewById(R.id.rv_notification_list);

        tvClearAll = findViewById(R.id.tv_clear_all);

        tvClearAll.setOnClickListener(this);

//        progressBar = findViewById(R.id.progress_bar);
//        progressBar.setVisibility(View.GONE);

        notificationListAdapter = new NotificationListAdapter(mList, this, pageNo, this);
        notificationList.setAdapter(notificationListAdapter);

        backButton.setOnClickListener(this);

        try {
            getNotificationList(pageNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back_button:
                finish();
                break;

            case R.id.tv_clear_all:

                clearNotification();

                break;
        }
    }

    public void getNotificationList(int pageNo) throws JSONException {

//        {
//            "userid":"15",
//                "token":"4592576",
//                "language":"english",
//                "page":0
//        }

        Log.d("PageCount", "" + pageNo);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(this, AppPreference.KEY_USER_ID));
        jsonObject.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(this, AppPreference.KEY_TOKEN));
        jsonObject.put(AppConstants.KEY_LANG, "english");
        jsonObject.put(AppConstants.KEY_PAGE_NO, pageNo);

        NetworkConn networkConn = NetworkConn.getInstance(this);
//
//        if (pageNo == 0) {
//            progressBar.setVisibility(View.GONE);
//        } else {
//            progressBar.setVisibility(View.VISIBLE);
//        }

        networkConn.makeRequest(this, NetworkConn.getInstance(this).createRawDataRequest(AppConstants.NOTIFICATION_LIST, jsonObject.toString()), this, AppConstants.EVENT_NOTIFICATION_LIST);

    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {


        AppDialogs.hideLoader();

        switch (strEventType) {

            case AppConstants.EVENT_NOTIFICATION_LIST:

                try {

                    if (response.getInt(AppConstants.KEY_STATUS) == 1) {
                        tv_norecordfound.setVisibility(View.GONE);
                        rl_notification.setVisibility(View.VISIBLE);

                        NotificationModel notificationModel = new Gson().fromJson(response.toString(), NotificationModel.class);
                        setNotificationList(notificationModel);


                    } else {

                        if (mList.size() == 0) {

                            tv_norecordfound.setVisibility(View.VISIBLE);
                            rl_notification.setVisibility(View.GONE);
                            tv_norecordfound.setText(response.getString(AppConstants.KEY_MESSAGE));
                        }
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
                break;

            case AppConstants.EVENT_NOTIFICATION_READ:

                if (response.optInt("status") == 1) {

                    mList.get(pos).setRead_status("1");
                    notificationListAdapter.notifyItemChanged(pos);
                    int count = 0;

                    if (AppConstants.notificationCount.trim().isEmpty()) {
                        count = 0;
                    } else {
                        count = Integer.parseInt(AppConstants.notificationCount);
                    }


                    if (count > 0) {
                        count = count - 1;

                        AppConstants.notificationCount = String.valueOf(count);
                    } else {
                        AppConstants.notificationCount = "0";
                    }


                }
                break;

            case AppConstants.EVENT_CLEAR_NOTIFICATION:

                if (response.optInt("status") == 1) {
                    mList.clear();
                    notificationListAdapter.notifyDataSetChanged();

                    AppConstants.notificationCount = "0";

                    pageNo = 0;

                    try {
                        getNotificationList(pageNo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {

                }


                break;

        }

    }

    @Override
    public void onNoNetworkConnectivity() {

        AppDialogs.hideLoader();

    }

    @Override
    public void onRequestFailed() {

        AppDialogs.hideLoader();

        //  progressBar.setVisibility(View.GONE);

    }

    private void setNotificationList(NotificationModel notificationList) {

        mList.addAll(notificationList.getResult());
        notificationListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onRead(int position) {

        try {
            readUnreadNotification(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRecordEnd() {

        pageNo++;

        try {
            getNotificationList(pageNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void readUnreadNotification(int position) throws JSONException {

        //   AppDialogs.showLoader(this);

        pos = position;
        notificationId = Integer.parseInt(mList.get(position).getId());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(this, AppPreference.KEY_USER_ID));
        jsonObject.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(this, AppPreference.KEY_TOKEN));
        jsonObject.put(AppConstants.NOTIFICATION_ID, notificationId);

        NetworkConn.getInstance(this).makeRequest(this, NetworkConn.getInstance(this).createRawDataRequest(AppConstants.NOTIFICATION_READ, jsonObject.toString()), this, AppConstants.EVENT_NOTIFICATION_READ);

    }

    private void clearNotification() {

        NetworkConn networkConn = NetworkConn.getInstance(this);

        networkConn.makeRequest(this, NetworkConn.getInstance(this).createGetRequest(AppConstants.CLEAR_NOTIFICATION), this, AppConstants.EVENT_CLEAR_NOTIFICATION);

    }

}
