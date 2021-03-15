package com.sudrives.sudrivespartner.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class AboutUsFragment extends Fragment implements View.OnClickListener, NetworkConn.OnRequestResponse {
    private Context mContext;
    private View mLayouView;
    private static final String ARG_LAYOUT = "layout";
    TextView tv_terms_and_condition, tv_drive_with_us, tv_state;
    WebView wv_about_us;

    public AboutUsFragment() {
        // Required empty public constructor
    }


    public static AboutUsFragment newInstance(int layout) {
        AboutUsFragment fragment = new AboutUsFragment();
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
        mLayouView = inflater.inflate(R.layout.fragment_about_us, null);
        return mLayouView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        tv_terms_and_condition = mLayouView.findViewById(R.id.tv_terms_and_condition);
        tv_drive_with_us = mLayouView.findViewById(R.id.tv_drive_with_us);

        wv_about_us = mLayouView.findViewById(R.id.wv_about_us);

        wv_about_us.getSettings().setJavaScriptEnabled(true);

        tv_state = new TextView(getActivity());


        String language = AppPreference.loadStringPref(mContext,AppPreference.KEY_LANGUAGE);

        if(language.equalsIgnoreCase("hindi"))
            setLocale("hi");
        else
            setLocale("en");



        setValue();

        tv_terms_and_condition.callOnClick();

    }

    private void setValue() {

        tv_terms_and_condition.setOnClickListener(this);
        tv_drive_with_us.setOnClickListener(this);

        tv_state = tv_terms_and_condition;
        tv_terms_and_condition.setActivated(true);


        wv_about_us.getSettings().setJavaScriptEnabled(true);

        // wv_about_us.loadUrl("");

        if (mContext != null)

            callAboutUs();


    }

    private void callAboutUs() {

        try {
            NetworkConn networkConn = NetworkConn.getInstance(getActivity());
            JSONObject jdata = new JSONObject();

            jdata.put(AppConstants.KEY_TYPES, AppConstants.KEY_ABOUT_US);

            networkConn.makeRequest(getActivity(), networkConn.createRawDataRequest(AppConstants.API_GET_TYPE, jdata.toString()), this, AppConstants.EVENT_API_GET_TYPE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_terms_and_condition:

                tv_state.setActivated(false);
                tv_state = tv_terms_and_condition;
                tv_terms_and_condition.setActivated(true);

                tv_terms_and_condition.setTypeface(null, Typeface.BOLD);
                tv_drive_with_us.setTypeface(null, Typeface.NORMAL);

                try {
                    getTermsAndDriveWithUs("term-and-condition-driver");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.tv_drive_with_us:

                tv_state.setActivated(false);
                tv_state = tv_drive_with_us;
                tv_drive_with_us.setActivated(true);

                tv_drive_with_us.setTypeface(null, Typeface.BOLD);
                tv_terms_and_condition.setTypeface(null, Typeface.NORMAL);

                try {
                    getTermsAndDriveWithUs("drive-with-us-user");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onResponse(JSONObject response, String strEventType) {

        if (mContext != null) {

            AppDialogs.hideLoader();

            if (response.optInt("status") == 1) {
                String data = response.optJSONObject("result").optString("discription");
                loadWebPage(data);
            } else {
                //  if(mContext!=null)
                //  Toast.makeText(mContext, response.optString(getString(R.string.message)), Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onNoNetworkConnectivity() {

    }

    @Override
    public void onRequestFailed() {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadWebPage(String data) {


        wv_about_us.loadData(data, "text/html", "UTF-8");


    }

    private void getTermsAndDriveWithUs(String pageName) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AppConstants.KEY_PAGE_NAME, pageName);

        NetworkConn network = NetworkConn.getInstance(mContext);

        network.makeRequest(mContext, network.createRawDataRequest(AppConstants.ABOUT_US, jsonObject.toString()), this, AppConstants.EVENT_ABOUT_US);

    }
    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);


        //  restartActivity();

    }
}
