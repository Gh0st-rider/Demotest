package com.sudrives.sudrivespartner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.adapters.CashoutRequestListAdapter;
import com.sudrives.sudrivespartner.adapters.SubscriptionPlanAdapter;
import com.sudrives.sudrivespartner.models.cashourRequest.CashoutrequestModel;
import com.sudrives.sudrivespartner.models.cashourRequest.Result;
import com.sudrives.sudrivespartner.models.subscriptionPlans.SubscriptionPlansModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sudrives.sudrivespartner.utils.AppPreference.KEY_USER_ID;

public class Request_cashout_Activity extends AppCompatActivity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    LinearLayout ll_account,ll_paytm, ll_upi;
    TextView tv_account, tv_paytm, tv_upi;
    Dialog dialog;


    String id="", holdername="", bankname = "", ifsc= "", accountnumber="", image="", accounttype="", up_number="", paytmnumber="";
    int accountType = 0;
    Button reqCashout;
    AlertDialog alertDialog;
    private String rupeesymbol = "\u20b9";

    TextView totalOnlinePickup, totalonlinepayment, tv_cashpayment, totalcashpayment, tv_totalearned, commisionadmin, driver_earned, admindue;

    RecyclerView rec_subs;
    List<Result> cashoutrequestModelList = new ArrayList<>();
    CashoutRequestListAdapter cashoutRequestListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save__payment__account__details_);



        ll_account = findViewById(R.id.ll_account);
        ll_paytm = findViewById(R.id.ll_paytm);
        ll_upi = findViewById(R.id.ll_upi);

        tv_account = findViewById(R.id.tv_account);
        tv_paytm = findViewById(R.id.tv_paytm);
        tv_upi = findViewById(R.id.tv_upi);

        totalonlinepayment = findViewById(R.id.totalonlinepayment);
        totalOnlinePickup = findViewById(R.id.totalOnlinePickup);
        tv_cashpayment = findViewById(R.id.tv_cashpaickups);
        totalcashpayment = findViewById(R.id.totalcashpayment);
        tv_totalearned = findViewById(R.id.tv_totalearned);
        commisionadmin = findViewById(R.id.commisionadmin);
        driver_earned = findViewById(R.id.driver_earned);
        admindue = findViewById(R.id.admin_due);

        ll_account.setOnClickListener(this);
        ll_paytm.setOnClickListener(this);
        ll_upi.setOnClickListener(this);



        reqCashout = findViewById(R.id.btn_cashout);
        reqCashout.setOnClickListener(this);


        rec_subs = findViewById(R.id.rec_cashout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fetchAccountDetails();

        getCashoutDeatils();
        fetchCashoutRequests();

    }

    private void fetchCashoutRequests(){

        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("any","");
            //  hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(Request_cashout_Activity.this, KEY_USER_ID));

            // Log.e("RequestCashout", "RequestCashout: " + AppConstants. + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.API_FETCH_CASHOUT_REQ, "" ), this, AppConstants.EVENT_API_FETCH_CASHOUT_REQ);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

            default:

                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.ll_account:

                ll_account.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.colorAccent));
                ll_paytm.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.white));
                ll_upi.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.white));
                reqCashout.setEnabled(true);
                accountType = 1;
                reqCashout.setBackgroundResource(R.drawable.rounded_corner_shape_primary);
                break;

            case R.id.ll_paytm:

                ll_account.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.white));
                ll_paytm.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.colorAccent));
                ll_upi.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.white));
                reqCashout.setEnabled(true);
                accountType = 2;

                reqCashout.setBackgroundResource(R.drawable.rounded_corner_shape_primary);

                break;

            case R.id.ll_upi:

                ll_account.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.white));
                ll_paytm.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.white));
                ll_upi.setBackgroundColor(ContextCompat.getColor(Request_cashout_Activity.this,R.color.colorAccent));
                reqCashout.setEnabled(true);
                accountType = 3;
                reqCashout.setBackgroundResource(R.drawable.rounded_corner_shape_primary);

                break;



            case R.id.btn_cashout:

                requestCashout();
                break;
        }



    }


    private void fetchAccountDetails(){

        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("any","");
          //  hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(Request_cashout_Activity.this, KEY_USER_ID));

           // Log.e("RequestCashout", "RequestCashout: " + AppConstants. + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.GET_DRIVER_ACCOUNT_DETAILS, ""), this, AppConstants.EVENT_GET_DRIVER_ACCOUNT_DETAILS);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        Log.e("responseAccountdetails",response.toString());
        if (strEventType.equalsIgnoreCase(AppConstants.EVENT_GET_DRIVER_ACCOUNT_DETAILS)){

            Log.e("responseAccountdetails",response.toString());
            try{

                if (response.getString("status").equalsIgnoreCase("0")){

                    tv_paytm.setText("Add paytm number");
                    tv_upi.setText("Add upi id");
                    tv_account.setText("Provide account details");

                }else {

                    JSONArray jsonArray = response.getJSONArray("result");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    id = jsonObject.optString("id");
                    holdername = jsonObject.optString("holdername");
                    bankname= jsonObject.optString("bankname");
                    ifsc = jsonObject.optString("ifsc");
                    accountnumber = jsonObject.optString("accountnumber");
                    accounttype = jsonObject.optString("account_type");
                    up_number = jsonObject.optString("up_number");
                    paytmnumber = jsonObject.optString("paytm_number");
                    Log.e("textpay",paytmnumber);
                    if (up_number.isEmpty() || up_number.equalsIgnoreCase("null")){
                        ll_upi.setVisibility(View.GONE);

                    }else {
                        tv_upi.setText(up_number);
                    }
                    if (paytmnumber.isEmpty() || paytmnumber.equalsIgnoreCase("null")){
                        ll_paytm.setVisibility(View.GONE);
                    }else {
                        tv_paytm.setText(paytmnumber);
                    }
                    if(holdername.isEmpty() || holdername.equalsIgnoreCase("null")){
                        ll_account.setVisibility(View.GONE);
                    }else {
                        tv_account.setText(accountnumber);
                    }

                }

            }catch (JSONException e){

            }

        }

        if (strEventType.equalsIgnoreCase(AppConstants.EVENT_SAVE_DRIVER_ACCOUNT_DETAILS)){

            Log.e("account",response.toString());


                if (response.optString("message").equalsIgnoreCase("Successfully")){
                    if (alertDialog!=null){
                        alertDialog.dismiss();
                        fetchAccountDetails();
                    }
                }



        }

        if (strEventType.equalsIgnoreCase(AppConstants.EVENT_REQUEST_CASHOUT_DETAILS)){

            Log.e("accountDetails",response.toString());

            try {
                JSONObject jsonObject = response.getJSONObject("result");
                String total_cash_booking = jsonObject.getString("total_cash_booking");
                String total_online_booking = jsonObject.getString("total_online_booking");
                String cash_booking_payment = jsonObject.getString("cash_booking_payment");
                String online_booking_payment = jsonObject.getString("online_booking_payment");
                String total_booking_payment = jsonObject.getString("total_booking_payment");
                String driver_booking_amount = jsonObject.getString("driver_booking_amount");
                String commision_fee_due_amount = jsonObject.getString("commision_fee_due_amount");
                String commision_fee_pay_amount =jsonObject.getString("commision_fee_pay_amount");

                totalonlinepayment.setText("online payment received: "+rupeesymbol+online_booking_payment);
                totalOnlinePickup.setText("online rides: "+total_online_booking);
                tv_cashpayment.setText("cash rides: "+total_cash_booking);
                totalcashpayment.setText("Cash received: "+rupeesymbol+cash_booking_payment);
                tv_totalearned.setText("Total booking (online + cash): "+rupeesymbol+total_booking_payment);
                commisionadmin.setText("Driver to admin due: "+rupeesymbol+commision_fee_due_amount);
                driver_earned.setText("Driver amount: "+rupeesymbol+driver_booking_amount);
                //admindue.setText("Admin to driver due: "+rupeesymbol+commision_fee_pay_amount);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        if (strEventType.equalsIgnoreCase(AppConstants.EVENT_REQUEST_CASHOUT)){
            try {
                Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            reqCashout.setEnabled(false);
            reqCashout.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_corner_shape_grey));
        }

        if (strEventType.equalsIgnoreCase(AppConstants.EVENT_API_FETCH_CASHOUT_REQ)){

            CashoutrequestModel cashoutrequestModel = new Gson().fromJson(response.toString(), CashoutrequestModel.class);
            cashoutrequestModelList.addAll(cashoutrequestModel.getResult());
            cashoutRequestListAdapter = new CashoutRequestListAdapter(cashoutrequestModelList,this);
            rec_subs.setLayoutManager(new LinearLayoutManager(this));
            rec_subs.setAdapter(cashoutRequestListAdapter);

        }


    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }


    private void saveAccountPayUpiDetails(String accountHoldername, String bankName, String ifsc, String accountNumber, String accounttype,String up_number, String paytmnumber){

        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_HOLDER_NAME, accountHoldername);
            hashMap.put(AppConstants.KEY_BANK_NAME, bankName);
            hashMap.put(AppConstants.KEY_IFSC, ifsc);
            hashMap.put(AppConstants.KEY_ACCOUNT_NUMBER, accountNumber);
            hashMap.put(AppConstants.KEY_ACCOUNTTYPE, accounttype);
            hashMap.put(AppConstants.KEY_UP_NUMBER, up_number);
            hashMap.put(AppConstants.KEY_PAYTM_NUMBER, paytmnumber);
            //  hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(Request_cashout_Activity.this, KEY_USER_ID));

            // Log.e("RequestCashout", "RequestCashout: " + AppConstants. + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.SAVE_DRIVER_ACCOUNT_DETAILS, hashMap,1), this, AppConstants.EVENT_SAVE_DRIVER_ACCOUNT_DETAILS);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void requestCashout(){
        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(AppConstants.KEY_PAYMENT_TYPE, accountType+"");
            //  hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(Request_cashout_Activity.this, KEY_USER_ID));

            // Log.e("RequestCashout", "RequestCashout: " + AppConstants. + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.REQUEST_CASHOUT, hashMap,1), this, AppConstants.EVENT_REQUEST_CASHOUT);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCashoutDeatils(){

        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);


            //HashMap<String, String> hashMap = new HashMap<>();
            //hashMap.put(AppConstants.KEY_PAYMENT_TYPE, accountType+"");
            //  hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(Request_cashout_Activity.this, KEY_USER_ID));

            // Log.e("RequestCashout", "RequestCashout: " + AppConstants. + "  :: " + hashMap.toString());

            networkConn.makeRequest(this, networkConn.createRawDataRequest(AppConstants.REQUEST_CASHOUT_DETAILS, ""), this, AppConstants.EVENT_REQUEST_CASHOUT_DETAILS);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}