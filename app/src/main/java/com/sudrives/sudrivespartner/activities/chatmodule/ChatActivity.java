package com.sudrives.sudrivespartner.activities.chatmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.BuySubscriptionActivity;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext = this;
    ImageView back;
    EditText etMag;
    ImageView btnSend;
    DatabaseReference databaseReference;
    List<FetchMessagesModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    public static final String Database_Path = "sudrives_chat";
    String driver_id;
    String strDate;

    AppPreference sessionPref;
    String endTripID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            driver_id = extras.getString("driver_id");
            endTripID = extras.getString("tripID");
        }


        back = findViewById(R.id.back_chat);
        back.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        strDate = mdformat.format(calendar.getTime());

        recyclerView = findViewById(R.id.recycler_view_chat);
        recyclerView.setNestedScrollingEnabled(false);
        databaseReference = FirebaseDatabase.getInstance("https://sudrives-9d1c0-default-rtdb.firebaseio.com/").getReference(Database_Path).child("userChat");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        databaseReference.child("driver" + AppPreference.loadStringPref(ChatActivity.this,AppPreference.KEY_USER_ID)).child("driver" + AppPreference.loadStringPref(ChatActivity.this,AppPreference.KEY_USER_ID)  +"+user"+ driver_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FetchMessagesModel studentDetails = dataSnapshot.getValue(FetchMessagesModel.class);
                    list.add(studentDetails);

                }

                adapter = new ChatAdapter(ChatActivity.this, list);
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        btnSend = findViewById(R.id.btn_send_msg);
        etMag = findViewById(R.id.et_msg);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etMag.getText().toString())) {

                    etMag.setError("Please write your message");

                } else {



                    InsertMessagesModel insertMessagesModel = new InsertMessagesModel();
                    //GetDataFromEditText();
                    String id1 = databaseReference.push().getKey();
                    String id = "driver" + AppPreference.loadStringPref(ChatActivity.this,AppPreference.KEY_USER_ID) + "+user" + driver_id;
                    // Adding name into class function object.
                    insertMessagesModel.setId(id1);
                    // Adding phone number into class function object.
                    insertMessagesModel.setMsg(etMag.getText().toString());
                    insertMessagesModel.setIsUser("driver");
                    insertMessagesModel.setChatTime(strDate);
                    sendChatNotification(driver_id, etMag.getText().toString(), endTripID);

                    etMag.setText("");
                    //sendChatNotification(driver_id, etMag.getText().toString(), endTripID);
                    // Adding the both name and number values using student details class object using ID.
                    databaseReference.child("driver" +  AppPreference.loadStringPref(ChatActivity.this,AppPreference.KEY_USER_ID)).child(id).child(id1).setValue(insertMessagesModel);
                   //sendNotification();
                }

            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_chat:

                finish();

                break;


        }
    }

    private void sendChatNotification(final String senderID, final String message, final String tripId){
        RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST, AppConstants.BASE_URL+"socketApi/sendmessagenotification", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);

              /*  Toast.makeText(BuySubscriptionActivity.this,"Successfully buy subscription",Toast.LENGTH_LONG).show();

                if (onlinePaymentStatus.equalsIgnoreCase("Success")){

                    binding.llSuccess.setVisibility(View.VISIBLE);
                    binding.llFail.setVisibility(View.GONE);

                }

                if (onlinePaymentStatus.equalsIgnoreCase("Failure")){

                    binding.llSuccess.setVisibility(View.GONE);
                    binding.llFail.setVisibility(View.VISIBLE);

                }*/

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
                params.put("send_user_id",senderID);
                params.put("trip_id",tripId);
                params.put("message", message);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("userid",AppPreference.loadStringPref(ChatActivity.this, AppPreference.KEY_USER_ID));
                params.put("token",AppPreference.loadStringPref(ChatActivity.this, AppPreference.KEY_TOKEN));
                return params;
            }
        };
        queue.add(sr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }



}