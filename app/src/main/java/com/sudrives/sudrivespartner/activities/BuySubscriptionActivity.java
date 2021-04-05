package com.sudrives.sudrivespartner.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.databinding.ActivityBuySubscriptionBinding;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.sudrives.sudrivespartner.utils.AppPreference.KEY_USER_ID;

public class BuySubscriptionActivity extends AppCompatActivity implements NetworkConn.OnRequestResponse, PaymentResultWithDataListener {

    String name, price, id, dis, val;
    ActivityBuySubscriptionBinding binding;
    private String rupeesymbol = "\u20b9";
    Checkout checkout;

    //upi
    private String TAG = "MainActivity";
    String payeeAddress = "8953909636@upi";
    String payeeName = "Shashank verma";
    String transactionNote = "Test for Sudrives deeplink";
    String amount = "1";
    String currencyUnit = "INR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_buy_subscription);

        checkout = new Checkout();

        Checkout.preload(getApplicationContext());

        if (getIntent().getExtras()!= null){
            name = getIntent().getExtras().getString("name");
            id = getIntent().getExtras().getString("id");
            price = getIntent().getExtras().getString("price");
            dis = getIntent().getExtras().getString("dis");
            val  = getIntent().getExtras().getString("val");
        }

        binding.title.setText(name);
        binding.planValidity.setText("val: "+val);
        binding.discount.setText("dis: "+dis+"%");
        binding.planPrice.setText("price: "+rupeesymbol+price);
        //getBuySubscriptionplan();
        binding.btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getBuySubscriptionplan();
                getOrderIDSubscriptionplan();
            }
        });

        binding.llRazor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpi();
            }
        });


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void startUpi(){
        Uri uri = Uri.parse("upi://pay?pa="+payeeAddress+"&pn="+payeeName+"&tn="+transactionNote+
                "&am="+amount+"&cu="+currencyUnit);
        Log.d(TAG, "onClick: uri: "+uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivityForResult(intent,1);
    }


    public void startPayment(final String amount, final String orderId, final String receipt) {

        checkout.setKeyID("rzp_live_Qo2n35b2sGbJKK");
        /**
         * Instantiate Checkout
         */

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "SuDrives");
            options.put("description", "Subscription"+receipt);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderId);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            options.put("prefill.email", "sudrivesapp@gmail.com");
            options.put("prefill.contact",AppPreference.loadStringPref(BuySubscriptionActivity.this, AppConstants.KEY_MOBILE));
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    private void getOrderIDSubscriptionplan(){
        try {
            NetworkConn networkConn = NetworkConn.getInstance(this);

            Map<String,String> params = new HashMap<String, String>();
            params.put(AppConstants.KEY_PAYMENT_TYPE,"Razorpay");
            params.put(AppConstants.KEY_AMOUNT,price);
            params.put(AppConstants.KEY_USING_TYPE, "Subscription");

             networkConn.makeRequest(this, networkConn.createFormDataRequest(AppConstants.API_GENERATE_ORDERID,params,1),this,AppConstants.EVENT_API_GENERATE_ORDERID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {
        Log.e("resBuySuns",response.toString());

        if (strEventType.equalsIgnoreCase(AppConstants.EVENT_API_GENERATE_ORDERID)){
            try {
            if (response.getString("status").equalsIgnoreCase("1")) {


                JSONObject jsonObject = response.getJSONObject("result");
                String amount = jsonObject.getString("amount");
                String orderId = jsonObject.getString("order_id");
                String reciept_no = jsonObject.getString("receipt");

                startPayment(amount, orderId, reciept_no);
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }

    private void sendRazorpayDetailsServer(final String payment_id, final String onlinePaymentStatus, final String onlinePaymentAmount, final String razorpayOrderId, final String razorpaySignature, final String email, final String mobile){
        RequestQueue queue = Volley.newRequestQueue(BuySubscriptionActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST,AppConstants.BASE_URL+"driver/buy_subscription_plan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);

                Toast.makeText(BuySubscriptionActivity.this,"Successfully buy subscription",Toast.LENGTH_LONG).show();

                if (onlinePaymentStatus.equalsIgnoreCase("Success")){

                    binding.llSuccess.setVisibility(View.VISIBLE);
                    binding.llFail.setVisibility(View.GONE);

                }

                if (onlinePaymentStatus.equalsIgnoreCase("Failure")){

                    binding.llSuccess.setVisibility(View.GONE);
                    binding.llFail.setVisibility(View.VISIBLE);

                }

               // mPostCommentResponse.requestCompleted();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("res",error.toString());
                //mPostCommentResponse.requestEndedWithError(error);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("userid",AppPreference.loadStringPref(BuySubscriptionActivity.this, AppPreference.KEY_USER_ID));
                params.put("subscription_plan_id",id);
                params.put("online_payment_id", payment_id);
                params.put("online_payment_status",onlinePaymentStatus);
                params.put("online_payment_amount",onlinePaymentAmount);
                params.put("razorpay_order_id",razorpayOrderId);
                params.put("razorpay_signature",razorpaySignature);
                params.put("email",email);
                params.put("mobile",mobile);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("userid",AppPreference.loadStringPref(BuySubscriptionActivity.this, AppPreference.KEY_USER_ID));
                params.put("token",AppPreference.loadStringPref(BuySubscriptionActivity.this, AppPreference.KEY_TOKEN));
                return params;
            }
        };
        queue.add(sr);
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Log.e("paymentData",s+"paymentdata"+ paymentData.getOrderId()+"payment_id"+paymentData.getPaymentId()+"signature"+paymentData.getSignature()+""+paymentData.getUserContact());
        sendRazorpayDetailsServer(paymentData.getPaymentId(),"Success",price,paymentData.getOrderId(),paymentData.getSignature(),paymentData.getUserEmail(),paymentData.getUserContact());
        binding.btnOnline.setVisibility(View.GONE);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        //Log.e("paymentData",s+"paymentdata"+ paymentData.getOrderId()+"payment_id"+paymentData.getPaymentId()+"signature"+paymentData.getSignature());
        binding.btnOnline.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject jsonObject1 = jsonObject.getJSONObject("error");
            String error = jsonObject1.getString("description");
            binding.llSuccess.setVisibility(View.GONE);
            binding.llFail.setVisibility(View.VISIBLE);
            binding.tvTextFailed.setText(error);
        }catch (JSONException e){
            e.printStackTrace();
        }

        binding.btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.llFail.setVisibility(View.GONE);
                binding.llSuccess.setVisibility(View.GONE);
                binding.btnOnline.setVisibility(View.VISIBLE);
            }
        });

        if (paymentData != null){
            sendRazorpayDetailsServer(paymentData.getPaymentId(),"Failure",price,paymentData.getOrderId(),paymentData.getSignature(),paymentData.getUserEmail(),paymentData.getUserContact());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode);
        Log.d(TAG, "onActivityResult: resultCode: " + resultCode);
        //txnId=UPI20b6226edaef4c139ed7cc38710095a3&responseCode=00&ApprovalRefNo=null&Status=SUCCESS&txnRef=undefined
        //txnId=UPI608f070ee644467aa78d1ccf5c9ce39b&responseCode=ZM&ApprovalRefNo=null&Status=FAILURE&txnRef=undefined

        if (data != null) {
            Log.d(TAG, "onActivityResult: data: " + data.getStringExtra("response"));
            String res = data.getStringExtra("response");
            String search = "SUCCESS";
            if (res.toLowerCase().contains(search.toLowerCase())) {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }
}