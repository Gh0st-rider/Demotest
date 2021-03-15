package com.sudrives.sudrivespartner.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.adapters.CollectionsAdapter;
import com.sudrives.sudrivespartner.models.CollectionModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CollectionsFragment extends Fragment implements View.OnClickListener, NetworkConn.OnRequestResponse, AppDialogs.SubmitButoonCallback {

    private Context mContext;
    private View mLayouView;
    private static final String ARG_LAYOUT = "layout";
    private TextView tv_norecordfound;
    private TextView tv_donation_Amount;

    private LinearLayout ll_top;

    public static AppDialogs.SubmitButoonCallback mSubmitButoonCallback;

    //Daily Collection
    TextView tv_collection_date, tv_collection_amount;

    TextView tv_collection_cash_amount, tv_collection_paytm_amount,
            tv_collection_cdcard_amount, tv_collection_net_banking_amount;

    RecyclerView rv_collection;

    private CollectionsAdapter mAdapter;
    private List<CollectionModel.ResultBean> mList = new ArrayList<>();
    private CollectionModel collectionModel;
    private String mode = "all";

    LinearLayout cash_layout;

    private TextView tv_type_collection;

    public CollectionsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CollectionsFragment newInstance(int layout) {
        CollectionsFragment fragment = new CollectionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT, layout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mLayouView = inflater.inflate(R.layout.fragment_collections, null);
        mSubmitButoonCallback = this;
        return mLayouView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init();

        try {
            callCollectionAPI();

            tv_type_collection.setText(getString(R.string.all).concat(" ").concat(getString(R.string.nav_collection)));

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDate = df.format(c.getTime());

            tv_collection_date.setText(formattedDate);

            tv_collection_amount.setText(getString(R.string.rupee).concat(" 0"));

        } catch (JSONException e) {
            e.printStackTrace();


        }
    }

    private void init() {

        ll_top = mLayouView.findViewById(R.id.ll_top);
        tv_norecordfound = mLayouView.findViewById(R.id.tv_norecordfound);
        tv_collection_date = mLayouView.findViewById(R.id.tv_collection_date);
        tv_collection_amount = mLayouView.findViewById(R.id.tv_collection_amount);
        tv_collection_cash_amount = mLayouView.findViewById(R.id.tv_collection_cash_amount);
        tv_donation_Amount = mLayouView.findViewById(R.id.tv_donation_Amount);
//        tv_collection_paytm_amount = mLayouView.findViewById(R.id.tv_collection_paytm_amount);
//        tv_collection_cdcard_amount = mLayouView.findViewById(R.id.tv_collection_cdcard_amount);
        tv_collection_net_banking_amount = mLayouView.findViewById(R.id.tv_collection_net_banking_amount);

        rv_collection = mLayouView.findViewById(R.id.rv_collection);
        tv_type_collection = mLayouView.findViewById(R.id.tv_type_collection);

        cash_layout = mLayouView.findViewById(R.id.cash_layout);


        setValue();


    }

    private void setValue() {
        mContext = getActivity();
        mAdapter = new CollectionsAdapter(mList, mContext);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv_collection.setLayoutManager(mLayoutManager);
        rv_collection.setItemAnimator(new DefaultItemAnimator());
        rv_collection.setAdapter(mAdapter);

    }

    private void callCollectionAPI() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID));
        jsonObject.put(AppConstants.KEY_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_TOKEN));
        jsonObject.put(AppConstants.KEY_FILTER, mode);


        Log.e("fgfg", "callCollectionAPI: " + AppConstants.DAILY_COLLECTION + "  :: " + jsonObject);
        NetworkConn networkConn = NetworkConn.getInstance(mContext);
        networkConn.makeRequest(mContext, NetworkConn.getInstance(mContext).createRawDataRequest(AppConstants.DAILY_COLLECTION, jsonObject.toString()), this, AppConstants.EVENT_DAILY_COLLECTION);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        try {


            if (response.getInt(AppConstants.KEY_STATUS) == 1) {

                tv_norecordfound.setVisibility(View.GONE);
                ll_top.setVisibility(View.VISIBLE);

                switch (strEventType) {
                    case AppConstants.EVENT_DAILY_COLLECTION:
                        if (response.optInt("status") == 1) {

                            ll_top.setVisibility(View.VISIBLE);

                            Log.d("Collection", response.toString());

                            mList.clear();

                            collectionModel = new Gson().fromJson(response.toString(), CollectionModel.class);


                            if (collectionModel != null) {
                                tv_norecordfound.setVisibility(View.GONE);
                                setCollectionDetails();
                            }

                        } else {
                        }
                        break;
                }


            } else {

                tv_norecordfound.setVisibility(View.VISIBLE);
                tv_norecordfound.setText(response.getString(AppConstants.KEY_MESSAGE));

                ll_top.setVisibility(View.GONE);
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

    @Override
    public void onSubmitButtonSuccess(String text) {

        switch (text) {

            case "All":
                tv_type_collection.setText(getString(R.string.all).concat(" ").concat(getString(R.string.nav_collection)));

                break;

            case "Weekly":
                tv_type_collection.setText(getString(R.string.weekly).concat(" ").concat(getString(R.string.nav_collection)));

                break;

            case "Monthly":
                tv_type_collection.setText(getString(R.string.month).concat(" ").concat(getString(R.string.nav_collection)));

                break;

            case "Daily":
                tv_type_collection.setText(getString(R.string.daily).concat(" ").concat(getString(R.string.nav_collection)));

                break;

        }


        try {

            mode = text.toLowerCase();

            callCollectionAPI();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setCollectionDetails() {

        String todayDate = getTime("");
        tv_collection_date.setText(todayDate);
        tv_collection_amount.setText(getString(R.string.rupee).concat(" ").concat("" + collectionModel.getAmount_earned_today()));

        String donation = collectionModel.getTotal_donations();

        if (!TextUtils.isEmpty(donation)) {
            tv_donation_Amount.setText(getString(R.string.donation_Amount).concat(" : " + getString(R.string.rupee).concat(" ").concat(donation)));
        }

        tv_collection_cash_amount.setText(getString(R.string.rupee).concat(" ").concat("" + collectionModel.getTotal_cash_payment()));

        tv_collection_net_banking_amount.setText(getString(R.string.rupee).concat(" ").concat("" + collectionModel.getTotal_online_payment()));


        setCollectionTripHistoryList(collectionModel);

    }

    private void setCollectionTripHistoryList(CollectionModel collectionModel) {


        mList.addAll(collectionModel.getResult());
        mAdapter.notifyDataSetChanged();

    }


    private String getTime(String time) {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String date = df.format(c);

//        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//        cal.setTimeInMillis(Long.parseLong(time));
//        //  String date = DateFormat.format("dd/MM/yyyy hh:mm a", cal).toString();
//        String date = DateFormat.format("dd-MMM-yyyy", cal).toString();

        date = getString(R.string.today).concat(" " + date);
        return date;


    }
}
