package com.sudrives.sudrivespartner.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.ContactUsModel;
import com.sudrives.sudrivespartner.networks.NetworkConn;
import com.sudrives.sudrivespartner.utils.AppConstants;

import org.json.JSONObject;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;

public class ContactUsFragment extends Fragment implements View.OnClickListener, NetworkConn.OnRequestResponse {
    private Context mContext;
    private View mLayouView;
    private static final String ARG_LAYOUT = "layout";
    private LinearLayout ll_contactus_callus, ll_contactus_emailus, ll_contactus_website;
    private TextView tv_contactus_callus, tv_contactus_emailus, tv_contactus_website;
    private ContactUsModel contactUsModel;


    public ContactUsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ContactUsFragment newInstance(int layout) {
        ContactUsFragment fragment = new ContactUsFragment();
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
        mLayouView = inflater.inflate(R.layout.fragment_contact_us, null);

        return mLayouView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        ll_contactus_callus = mLayouView.findViewById(R.id.ll_contactus_callus);
        ll_contactus_emailus = mLayouView.findViewById(R.id.ll_contactus_emailus);
        ll_contactus_website = mLayouView.findViewById(R.id.ll_contactus_website);

        tv_contactus_callus = mLayouView.findViewById(R.id.tv_contactus_callus);
        tv_contactus_emailus = mLayouView.findViewById(R.id.tv_contactus_emailus);
        tv_contactus_website = mLayouView.findViewById(R.id.tv_contactus_website);

        setValue();

    }

    private void setValue() {
        mContext = getActivity();

        ll_contactus_callus.setOnClickListener(this);
        ll_contactus_emailus.setOnClickListener(this);
        ll_contactus_website.setOnClickListener(this);

        tv_contactus_website.setMovementMethod(BetterLinkMovementMethod.newInstance());
        Linkify.addLinks(tv_contactus_website, Linkify.WEB_URLS);

        callContactUs();





    }

    private void callContactUs() {

        try {
            NetworkConn networkConn = NetworkConn.getInstance(getActivity());

            // JSONObject jdata = new JSONObject();

//            jdata.put(AppConstants.KEY_PAGE_NAME, AppConstants.KEY_CONTACT_US);

            networkConn.makeRequest(getActivity(), networkConn.createGetRequest(AppConstants.CONTACT_US), this, AppConstants.EVENT_CONTACT_US);

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

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_contactus_callus:

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + tv_contactus_callus.getText().toString().trim()));
                startActivity(callIntent);

                break;

            case R.id.ll_contactus_emailus:
                String emailAddress = tv_contactus_emailus.getText().toString().trim();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",emailAddress, null));
               // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Us");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;

        }


    }


    @Override
    public void onResponse(JSONObject response, String strEventType) {

        Log.e("Contact Us->>", response.toString());

        try {

            if (strEventType.trim().equalsIgnoreCase(AppConstants.EVENT_CONTACT_US)) {


                if (response.getInt(AppConstants.KEY_STATUS) == 1) {

                    Log.e("ContactUs", response.toString());

                    String contactUsResponce = response.toString();

                    contactUsModel = new Gson().fromJson(contactUsResponce, ContactUsModel.class);

                    setConatctInfo(contactUsModel);


                } else if (response.getInt(AppConstants.KEY_STATUS) == 0) {

                    Toast.makeText(mContext, response.toString(), Toast.LENGTH_SHORT).show();

                }

            }

        } catch (Exception e) {

            e.printStackTrace();
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

    private void setConatctInfo(ContactUsModel conatctInfo) {

        tv_contactus_emailus.setText("sudrivesapp@gmail.com");

       // String text = "<a href='" + contactUsModel.result.site_url + "'>" + contactUsModel.result.site_url + "</a>";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_contactus_website.setText(Html.fromHtml("https://sudrives.com/", Html.FROM_HTML_MODE_COMPACT));
        } else {

            tv_contactus_website.setText(Html.fromHtml("https://sudrives.com/"));
        }
        tv_contactus_callus.setText("918586024074");

    }
}
