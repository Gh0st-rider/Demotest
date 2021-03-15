package com.sudrives.sudrivespartner.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.adapters.ReportIssueAdapter;
import com.sudrives.sudrivespartner.models.ReportIssueModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportIssueActivity extends AppCompatActivity implements NetworkConn.OnRequestResponse, View.OnClickListener {

    RecyclerView recyclerView;
    private ReportIssueModel reportIssueModel;
    List<ReportIssueModel.ResultBean> mList;

    ReportIssueAdapter reportIssueAdapter;
    private TextView tv_norecordfound;
    private TextView bageCount;
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
        setContentView(R.layout.activity_report_issue);

        recyclerView = findViewById(R.id.rv_report_issue);
        tv_norecordfound = findViewById(R.id.tv_norecordfound);

        mList = new ArrayList<>();

        reportIssueAdapter = new ReportIssueAdapter(mList, this);
        recyclerView.setAdapter(reportIssueAdapter);

        findViewById(R.id.iv_notification).setOnClickListener(this);
        findViewById(R.id.ib_back_button).setOnClickListener(this);
        bageCount = findViewById(R.id.tv_notification_Count);

        try {
            getReportIssueList();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (TextUtils.isEmpty(AppConstants.notificationCount)) {

            bageCount.setVisibility(View.GONE);
        } else {
            bageCount.setVisibility(View.VISIBLE);
            bageCount.setText(AppConstants.notificationCount);

        }
    }

    private void getReportIssueList() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(this, AppPreference.KEY_USER_ID));

        NetworkConn.getInstance(this).makeRequest(this, NetworkConn.getInstance(this).createRawDataRequest(AppConstants.REPORT_ISSUE_LIST, jsonObject.toString()), this, AppConstants.EVENT_REPORT_ISSUE_LIST);

    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        switch (strEventType) {
            case AppConstants.EVENT_REPORT_ISSUE_LIST:
                if (response.optInt("status") == 1) {

                    Log.d("ReportList", response.toString());


                    reportIssueModel = new Gson().fromJson(response.toString(), ReportIssueModel.class);

                    Log.d("ReportList", "ttt: " + reportIssueModel.getResult().size());

                    if (reportIssueModel.getResult().size() == 0) {

                        tv_norecordfound.setText(reportIssueModel.getMessage());
                        tv_norecordfound.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tv_norecordfound.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        setReportIssueList(reportIssueModel);
                    }


                }
                break;
        }

    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }

    private void setReportIssueList(ReportIssueModel reportIssueModel) {

        if (reportIssueModel != null) {
            if (reportIssueModel.getResult() != null) {
                mList.addAll(reportIssueModel.getResult());
                reportIssueAdapter.notifyDataSetChanged();
            }
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
        AppConstants.KEY_CONTEXT = ReportIssueActivity.this;
        if (TextUtils.isEmpty(AppConstants.notificationCount)) {

            bageCount.setVisibility(View.GONE);

        } else {

            bageCount.setVisibility(View.VISIBLE);

            bageCount.setText(AppConstants.notificationCount);

        }
    }

}
