package com.sudrives.sudrivespartner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.adapters.SubscriptionPlanAdapter;
import com.sudrives.sudrivespartner.databinding.ActivitySubscriptionPlanBinding;
import com.sudrives.sudrivespartner.models.BookingDetailsBeans;
import com.sudrives.sudrivespartner.models.subscriptionPlans.Result;
import com.sudrives.sudrivespartner.models.subscriptionPlans.SubscriptionPlansModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sudrives.sudrivespartner.utils.AppPreference.KEY_USER_ID;

public class SubscriptionPlanActivity extends AppCompatActivity implements NetworkConn.OnRequestResponse {

    ActivitySubscriptionPlanBinding binding;
    List<Result> subscriptionPlansModels = new ArrayList<>();
    SubscriptionPlanAdapter subscriptionPlanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_subscription_plan);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSubscriptionPlans();
    }

    private void getSubscriptionPlans(){
        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(SubscriptionPlanActivity.this, KEY_USER_ID));

            Log.e("Subscription", "Subscription: " + AppConstants.API_SUBSCRIPTION_PLANS_LIST + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.API_SUBSCRIPTION_PLANS_LIST, hashMap, 1), this, AppConstants.API_SUBSCRIPTION_PLANS_LIST);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {


        try {


            if (response != null) {

                Log.e("responsesubs",response.toString());

                if (response.getInt(AppConstants.KEY_STATUS) == 0) {

                    Log.e("responsesubs",response.toString());
                    //setNoRecordFound();


                } else {
                    SubscriptionPlansModel subscriptionPlansModel = new Gson().fromJson(response.toString(), SubscriptionPlansModel.class);
                    subscriptionPlansModels.addAll(subscriptionPlansModel.getResult());
                    subscriptionPlanAdapter = new SubscriptionPlanAdapter(subscriptionPlansModels,this);
                    binding.recSubs.setLayoutManager(new LinearLayoutManager(this));
                    binding.recSubs.setAdapter(subscriptionPlanAdapter);

                }



            } else {

                //setNoRecordFound();

                //  Toast.makeText(mContext, R.string.no_booking_found, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }
}