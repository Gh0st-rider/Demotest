package com.sudrives.sudrivespartner.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.HomeMenuActivity;
import com.sudrives.sudrivespartner.adapters.BookingHistoryAdapter;
import com.sudrives.sudrivespartner.models.BookingDetailsBeans;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.interfaces.ReportIssueClickListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements NetworkConn.OnRequestResponse, ReportIssueClickListner {

    RecyclerView rvHistoryList;
    Context mContext;
    List<BookingDetailsBeans.ResultBean> resultBeans;
    BookingHistoryAdapter bookingHistoryAdapter;
    TextView tv_norecordfound;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHistoryList = view.findViewById(R.id.rv_history_list);
        tv_norecordfound = view.findViewById(R.id.tv_norecordfound);
        resultBeans = new ArrayList<>();
        bookingHistoryAdapter = new BookingHistoryAdapter(resultBeans, this, mContext);

        rvHistoryList.setAdapter(bookingHistoryAdapter);


        try {
            getHistoryList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getHistoryList() throws JSONException {


        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID));
        jsonObject.put(AppConstants.KEY_TRIP_TYPE, "history");
        jsonObject.put(AppConstants.KEY_LAT, "");
        jsonObject.put(AppConstants.KEY_LANG, "");


        jsonObject.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_TOKEN));
        jsonObject.put(AppConstants.KEY_TYPE_FOR, "driver");

        Log.e("fddfgfdg", "Hisotory: "+AppConstants.USER_BOOKINGS +"  "+jsonObject);


        NetworkConn networkConn = NetworkConn.getInstance(mContext);

        networkConn.makeRequest(mContext, networkConn.createRawDataRequest(AppConstants.USER_BOOKINGS, jsonObject.toString()), this, AppConstants.EVENT_USER_BOOKINGS);

    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        try {


            if (response != null) {

                Log.e("HistoryRecords", response.toString());


                if (response.getInt(AppConstants.KEY_STATUS) == 0) {

                    Log.d("HistoryRecord","NoHistory");
                    setNoRecordFound();


                } else {
                    BookingDetailsBeans bookingDetailsBeans = new Gson().fromJson(response.toString(), BookingDetailsBeans.class);

                    tv_norecordfound.setVisibility(View.GONE);
                    rvHistoryList.setVisibility(View.VISIBLE);
                    resultBeans.addAll(bookingDetailsBeans.result);
                    bookingHistoryAdapter.notifyDataSetChanged();
                }



            } else {

                setNoRecordFound();

              //  Toast.makeText(mContext, R.string.no_booking_found, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    private void setNoRecordFound() {
        rvHistoryList.setVisibility(View.GONE);
        tv_norecordfound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }

    @Override
    public void onClick(int position) {
        String bookingId = resultBeans.get(position).booking_id;

        Fragment fragment = new ReportIssueFragmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BOOKING_ID, bookingId);
        bundle.putString(AppConstants.USER_EMAIL, resultBeans.get(position).user_details.email);
        bundle.putString(AppConstants.MOBILE_NUMBER, resultBeans.get(position).user_details.mobile);
        bundle.putString(AppConstants.TRIP_ID, resultBeans.get(position).id);
        fragment.setArguments(bundle);

        if (getActivity() != null) {
       HomeMenuActivity.mOnFragmentChange.onFragmentChange(fragment, getString(R.string.nav_report_issue));
        }
    }
}
