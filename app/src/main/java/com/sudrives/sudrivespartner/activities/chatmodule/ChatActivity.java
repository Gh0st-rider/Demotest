package com.sudrives.sudrivespartner.activities.chatmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.utils.AppPreference;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            driver_id = extras.getString("driver_id");
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
                    etMag.setText("");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}