package com.sudrives.sudrivespartner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import static com.sudrives.sudrivespartner.utils.AppPreference.KEY_USER_ID;

public class Request_cashout_Activity extends AppCompatActivity implements View.OnClickListener, NetworkConn.OnRequestResponse {

    LinearLayout ll_account,ll_paytm, ll_upi;
    TextView tv_account, tv_paytm, tv_upi;
    Dialog dialog;


    String id="", holdername="", bankname = "", ifsc= "", accountnumber="", image="", accounttype="", up_number="", paytmnumber="";
    int accountType = 0;
    CheckBox checkBox1,checkBox2,checkBox3;
    Button reqCashout;
    AlertDialog alertDialog;
    private String rupeesymbol = "\u20b9";

    TextView totalOnlinePickup, totalonlinepayment, tv_cashpayment, totalcashpayment, tv_totalearned, commisionadmin, driver_earned, admindue;

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

        checkBox1 = findViewById(R.id.check1);
        checkBox2 = findViewById(R.id.check2);
        checkBox3 = findViewById(R.id.check3);

        reqCashout = findViewById(R.id.btn_cashout);
        reqCashout.setOnClickListener(this);

        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fetchAccountDetails();

        getCashoutDeatils();


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

                if (tv_account.getText().toString().equalsIgnoreCase("Provide account details")){

                    onAccountDetailsDialog("account");

                }else {
                    onAccountDetailsDialog("accountDetails");
                    accountType=1;
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);

                    reqCashout.setEnabled(true);
                    reqCashout.setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_corner_shape_primary));


                }

                break;

            case R.id.ll_paytm:

                if (tv_paytm.getText().toString().equalsIgnoreCase("Add paytm number")){


                    onAccountDetailsDialog("paytm");

                }else {
                    onAccountDetailsDialog("paytmDetails");
                    accountType=2;
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(false);

                    reqCashout.setEnabled(true);
                    reqCashout.setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_corner_shape_primary));
                }

                break;

            case R.id.ll_upi:

                if (tv_upi.getText().toString().equalsIgnoreCase("Add upi id")){

                    onAccountDetailsDialog("upi");

                }else {
                    accountType=3;
                    onAccountDetailsDialog("upiDetails");
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(true);

                    reqCashout.setEnabled(true);
                    reqCashout.setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_corner_shape_primary));
                }

                break;

            case R.id.check1:
                if (tv_account.getText().toString().equalsIgnoreCase("Provide account details")){

                    onAccountDetailsDialog("upi");

                }else {

                    accountType=1;
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);

                    reqCashout.setEnabled(true);
                    reqCashout.setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_corner_shape_primary));
                }
                break;

            case R.id.check2:

                if (tv_paytm.getText().toString().equalsIgnoreCase("Add paytm number")){


                    onAccountDetailsDialog("paytm");

                }else {
                    accountType=2;
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(false);

                    reqCashout.setEnabled(true);
                    reqCashout.setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_corner_shape_primary));
                }

                break;

            case R.id.check3:
                if (tv_upi.getText().toString().equalsIgnoreCase("Add upi id")){

                    onAccountDetailsDialog("upi");

                }else {

                    accountType=3;
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(true);

                    reqCashout.setEnabled(true);
                    reqCashout.setBackground(ContextCompat.getDrawable(this,R.drawable.rounded_corner_shape_primary));
                }
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
                        tv_upi.setText("Add upi id");

                    }else {
                        tv_upi.setText(up_number);
                    }
                    if (paytmnumber.isEmpty() || paytmnumber.equalsIgnoreCase("null")){
                        tv_paytm.setText("Add paytm number");
                    }else {
                        tv_paytm.setText(paytmnumber);
                    }
                    if(holdername.isEmpty() || holdername.equalsIgnoreCase("null")){
                        tv_account.setText("Provide account details");
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

                totalonlinepayment.setText("online: "+rupeesymbol+online_booking_payment);
                totalOnlinePickup.setText("online user: "+total_online_booking);
                tv_cashpayment.setText("cash user: "+total_cash_booking);
                totalcashpayment.setText("Cash: "+rupeesymbol+cash_booking_payment);
                tv_totalearned.setText("Earned: "+rupeesymbol+total_booking_payment);
                commisionadmin.setText("Due: "+rupeesymbol+commision_fee_due_amount);
                driver_earned.setText("Total: "+rupeesymbol+driver_booking_amount);
                admindue.setText("Admin due: "+rupeesymbol+commision_fee_pay_amount);

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



    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }


    private void onAccountDetailsDialog(String cashoutoption) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.DialogTheme);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_account_details, null);



        LinearLayout account_details_lv = view.findViewById(R.id.account_details_l);
        LinearLayout account  = view.findViewById(R.id.account_details_view);
        TextView tv_account_title = view.findViewById(R.id.tv_account_title);
        ImageView edit_account_details = view.findViewById(R.id.edit_account_details);
        final TextInputEditText account_holder_name_ev = view.findViewById(R.id.account_holder_name_ev);
        final TextInputEditText bank_name_ev = view.findViewById(R.id.bank_name_ev);
        final TextInputEditText bank_ifsc_ev = view.findViewById(R.id.bank_ifsc_ev);
        final TextInputEditText account_number_ev = view.findViewById(R.id.account_number_ev);


        LinearLayout ll_paytm = view.findViewById(R.id.ll_paytm);
        LinearLayout ll_upi = view.findViewById(R.id.ll_upi);

        final Button save_account_details = view.findViewById(R.id.save_account_details);

        final TextInputEditText tie_upi = view.findViewById(R.id.tie_upi);
        final TextInputEditText tie_paytm = view.findViewById(R.id.tie_paytm);


        if (cashoutoption.equalsIgnoreCase("account")) {

            account_details_lv.setVisibility(View.VISIBLE);
            ll_paytm.setVisibility(View.GONE);
            ll_upi.setVisibility(View.GONE);
            account.setVisibility(View.VISIBLE);
            tv_account_title.setText("Account Details");
            edit_account_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    account_holder_name_ev.setEnabled(true);
                    bank_name_ev.setEnabled(true);
                    bank_ifsc_ev.setEnabled(true);
                    account_number_ev.setEnabled(true);

                    save_account_details.setBackground(ContextCompat.getDrawable(Request_cashout_Activity.this, R.drawable.rounded_corner_shape_primary));

                    save_account_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            saveAccountPayUpiDetails(account_holder_name_ev.getText().toString(), bank_name_ev.getText().toString(), bank_ifsc_ev.getText().toString(),account_number_ev.getText().toString(),"1","","");


                        }
                    });

                }
            });

        }


        if (cashoutoption.equalsIgnoreCase("accountDetails")) {

            account_details_lv.setVisibility(View.VISIBLE);
            ll_paytm.setVisibility(View.GONE);
            ll_upi.setVisibility(View.GONE);
            account.setVisibility(View.VISIBLE);
            tv_account_title.setText("Account Details");

            account_holder_name_ev.setText(holdername);
            bank_name_ev.setText(bankname);
            bank_ifsc_ev.setText(ifsc);
            account_number_ev.setText(accountnumber);
            save_account_details.setText("Update");
            edit_account_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    account_holder_name_ev.setEnabled(true);
                    bank_name_ev.setEnabled(true);
                    bank_ifsc_ev.setEnabled(true);
                    account_number_ev.setEnabled(true);

                    save_account_details.setBackground(ContextCompat.getDrawable(Request_cashout_Activity.this, R.drawable.rounded_corner_shape_primary));

                    save_account_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            saveAccountPayUpiDetails(account_holder_name_ev.getText().toString(), bank_name_ev.getText().toString(), bank_ifsc_ev.getText().toString(),account_number_ev.getText().toString(),"1","","");

                        }
                    });

                }
            });

        }


        if (cashoutoption.equalsIgnoreCase("paytm")) {

            account_details_lv.setVisibility(View.VISIBLE);
            ll_paytm.setVisibility(View.VISIBLE);
            ll_upi.setVisibility(View.GONE);
            account.setVisibility(View.GONE);

            tv_account_title.setText("Paytm Details");
            edit_account_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tie_paytm.setEnabled(true);


                    save_account_details.setBackground(ContextCompat.getDrawable(Request_cashout_Activity.this, R.drawable.rounded_corner_shape_primary));

                    save_account_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveAccountPayUpiDetails("", "", "","","2","",tie_paytm.getText().toString());


                        }
                    });

                }
            });

        }



        if (cashoutoption.equalsIgnoreCase("paytmDetails")) {

            account_details_lv.setVisibility(View.VISIBLE);
            ll_paytm.setVisibility(View.VISIBLE);
            ll_upi.setVisibility(View.GONE);
            account.setVisibility(View.GONE);
            tie_paytm.setText(paytmnumber);
            tv_account_title.setText("Paytm Details");
            save_account_details.setText("Update");
            edit_account_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tie_paytm.setEnabled(true);


                    save_account_details.setBackground(ContextCompat.getDrawable(Request_cashout_Activity.this, R.drawable.rounded_corner_shape_primary));

                    save_account_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveAccountPayUpiDetails("", "", "","","2","",tie_paytm.getText().toString());


                        }
                    });

                }
            });

        }


        if (cashoutoption.equalsIgnoreCase("upi")) {

            account_details_lv.setVisibility(View.VISIBLE);
            ll_paytm.setVisibility(View.GONE);
            ll_upi.setVisibility(View.VISIBLE);
            account.setVisibility(View.GONE);

            tv_account_title.setText("UPI Details");
            edit_account_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tie_upi.setEnabled(true);


                    save_account_details.setBackground(ContextCompat.getDrawable(Request_cashout_Activity.this, R.drawable.rounded_corner_shape_primary));

                    save_account_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveAccountPayUpiDetails("", "", "","","3",tie_upi.getText().toString(),"");


                        }
                    });

                }
            });

        }


        if (cashoutoption.equalsIgnoreCase("upiDetails")) {

            account_details_lv.setVisibility(View.VISIBLE);
            ll_paytm.setVisibility(View.GONE);
            ll_upi.setVisibility(View.VISIBLE);
            account.setVisibility(View.GONE);
            tie_upi.setText(up_number);
            tv_account_title.setText("UPI Details");
            save_account_details.setText("Update");

            edit_account_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tie_upi.setEnabled(true);


                    save_account_details.setBackground(ContextCompat.getDrawable(Request_cashout_Activity.this, R.drawable.rounded_corner_shape_primary));

                    save_account_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveAccountPayUpiDetails("", "", "","","3",tie_upi.getText().toString(),"");


                        }
                    });

                }
            });

        }



        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();

        alertDialog.show();


        /*final Dialog dialog = new Dialog(AppConstants.KEY_CONTEXT, R.style.MyDialogTheme);

        LayoutInflater layoutInflater = (LayoutInflater) AppConstants.KEY_CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.dialog_account_details, null);



//            CircleImageView imageView = findViewById(R.id.user_image);
        //  Glide.with(this)
        //        .load(mAcceptTrip.getResult().getUser_details().getProfile_img())
        //      .into(imageView);




        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(false);






        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
        //lp.gravity = Gravity.BOTTOM;

        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
    }*/
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