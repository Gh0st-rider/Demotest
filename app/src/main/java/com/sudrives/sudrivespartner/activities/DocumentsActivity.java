package com.sudrives.sudrivespartner.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.helper.VolleySingleton;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DocumentsActivity extends AppCompatActivity implements View.OnClickListener {


    TextView bageCount;
    ImageView back, notification;
    LinearLayout llDl, llRC, llFitness, llInsurance, llPermit, llPollution, llRoad, llPan, llAdhar, llVerification, llClearance;
    ImageView ivDl, ivRC, ivFitness, ivInsurance, ivPermit, ivPollution, ivRoad, ivPan, ivAdhar, ivVerification, ivClearance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);


        llDl = findViewById(R.id.ll_dl);
        llRC = findViewById(R.id.ll_rc);
        llFitness = findViewById(R.id.ll_fitness);
        llInsurance = findViewById(R.id.ll_insurance);
        llPermit = findViewById(R.id.ll_permit);
        llPollution = findViewById(R.id.ll_pollution);
        llRoad = findViewById(R.id.ll_road_tax);
        llPan = findViewById(R.id.ll_pan);
        llAdhar = findViewById(R.id.ll_adhar);
        llVerification = findViewById(R.id.ll_pv);
        llClearance = findViewById(R.id.ll_pc);

        ivDl = findViewById(R.id.iv_dl);
        ivRC = findViewById(R.id.iv_rc);
        ivFitness = findViewById(R.id.iv_fitness);
        ivInsurance = findViewById(R.id.iv_insurance);
        ivPermit = findViewById(R.id.iv_permit);
        ivPollution = findViewById(R.id.iv_pollution);
        ivRoad = findViewById(R.id.iv_road_tax);
        ivPan = findViewById(R.id.iv_pan_card);
        ivAdhar = findViewById(R.id.iv_adhar_card);
        ivVerification = findViewById(R.id.iv_pv);
        ivClearance = findViewById(R.id.iv_pc);


        back = findViewById(R.id.ib_back_button);
        back.setOnClickListener(this);

        notification = findViewById(R.id.iv_notification);
        notification.setOnClickListener(this);

        bageCount = findViewById(R.id.tv_notification_Count);

        if (AppConstants.notificationCount.equals("0")) {
            bageCount.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(AppConstants.notificationCount)) {
            bageCount.setVisibility(View.GONE);
        } else {
            bageCount.setVisibility(View.VISIBLE);
            bageCount.setText(AppConstants.notificationCount);

        }

        getDocuments();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        AppConstants.KEY_CONTEXT = DocumentsActivity.this;
        if (AppConstants.notificationCount.equals("0")) {
            bageCount.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(AppConstants.notificationCount)) {

            bageCount.setVisibility(View.GONE);

        } else {

            bageCount.setVisibility(View.VISIBLE);

            bageCount.setText(AppConstants.notificationCount);

        }
    }


    public void getDocuments() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConstants.GET_DOCUMENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("getDocuments", response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.optInt("status");
                            String message = jsonResponse.optString("message");
                            if (status == 1) {
                                JSONObject jsonData = jsonResponse.getJSONObject("result");

                                int id = jsonData.optInt("id");

                                String driving_license = jsonData.optString("driving_license");


                                Log.e("dlllll", driving_license);

                                if (driving_license.equals("")) {
                                    ivDl.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivDl.setImageResource(R.drawable.check_green_24dp);
                                }

                                String rc_number = jsonData.optString("rc_number");
                                if (rc_number.equals("")) {
                                    ivRC.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivRC.setImageResource(R.drawable.check_green_24dp);
                                }


                                String fitness_certificate = jsonData.optString("fitness_certificate");
                                if (fitness_certificate.equals("")) {
                                    ivFitness.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivFitness.setImageResource(R.drawable.check_green_24dp);
                                }


                                String insurance = jsonData.optString("insurance");
                                if (insurance.equals("")) {
                                    ivInsurance.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivInsurance.setImageResource(R.drawable.check_green_24dp);
                                }


                                String permit = jsonData.optString("permit");
                                if (permit.equals("")) {
                                    ivPermit.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivPermit.setImageResource(R.drawable.check_green_24dp);
                                }


                                String pollution = jsonData.optString("pollution");
                                if (pollution.equals("")) {
                                    ivPollution.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivPollution.setImageResource(R.drawable.check_green_24dp);
                                }


                                String road_tax = jsonData.optString("road_tax");
                                if (road_tax.equals("")) {
                                    ivRoad.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivRoad.setImageResource(R.drawable.check_green_24dp);
                                }


                                String pan_card = jsonData.optString("pan_card");
                                if (pan_card.equals("")) {
                                    ivPan.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivPan.setImageResource(R.drawable.check_green_24dp);
                                }


                                String aadhar_card = jsonData.optString("aadhar_card");
                                if (aadhar_card.equals("")) {
                                    ivAdhar.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivAdhar.setImageResource(R.drawable.check_green_24dp);
                                }


                                String police_verification = jsonData.optString("police_verification");
                                if (police_verification.equals("")) {
                                    ivVerification.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivVerification.setImageResource(R.drawable.check_green_24dp);
                                }

                                String police_clearance = jsonData.optString("police_clearance");
                                if (police_clearance.equals("")) {
                                    ivClearance.setImageResource(R.drawable.upload_circle_black_24dp);
                                } else {
                                    ivClearance.setImageResource(R.drawable.check_green_24dp);
                                }


                            } else {
                                Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
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
                headers.put("userid", AppPreference.loadStringPref(getApplication(), AppPreference.KEY_USER_ID));
                headers.put("token", AppPreference.loadStringPref(getApplication(), AppPreference.KEY_TOKEN));
                Log.e("headerput", headers + "");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getApplication()).addToRequestQueue(stringRequest);
    }
}


