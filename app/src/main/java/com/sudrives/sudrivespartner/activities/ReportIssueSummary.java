
package com.sudrives.sudrivespartner.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.ReportIssueModel;
import com.sudrives.sudrivespartner.utils.AppConstants;

import java.util.Locale;

public class ReportIssueSummary extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout headerPanel;
    private CardView card;
    private LinearLayout rlOrigin;
    private LinearLayout llBookingId;
    private TextView tvBookingId;
    private ImageView ivTruckType;
    private TextView tvTruckType;
    private LinearLayout lnrBookingDetailsLocation;
    private TextView tvOriginMyBooking;
    private TextView tvDestinationMyBooking;
    private LinearLayout llStatus;
    private ImageView ivPickup;
    private TextView tvDeliveryDateVal;
    private ImageView ivStatus;
    private TextView tvStatus;
    private ImageView ivAmount;
    private TextView tvAmount;
    private TextView tvDescription;
    private TextView descriptionHeading;

    ReportIssueModel.ResultBean resultBean;
    private TextView bageCount;

    boolean isGoHome;
    public void setLocale(String lang) {


        try {

            Locale myLocale = new Locale(lang);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            onConfigurationChanged(conf);

        } catch (Exception e) {

        }


        //  restartActivity();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // setLocale(AppPreference.loadStringPref(this, AppPreference.KEY_LANGUAGE).trim());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue_summary);

        if (getIntent() != null) {
            resultBean = (ReportIssueModel.ResultBean) getIntent().getSerializableExtra("Reportsummary");
            isGoHome = getIntent().getBooleanExtra("isGoToHome",false);
        }

        headerPanel = findViewById(R.id.header_panel);
        findViewById(R.id.ib_back_button).setOnClickListener(this);
        findViewById(R.id.iv_notification).setOnClickListener(this);
        card = findViewById(R.id.card);
        rlOrigin = findViewById(R.id.rl_origin);
        llBookingId = findViewById(R.id.ll_booking_id);
        tvBookingId = findViewById(R.id.tv_booking_id);
        ivTruckType = findViewById(R.id.iv_truck_type);
        tvTruckType = findViewById(R.id.tv_truck_type);
        lnrBookingDetailsLocation = findViewById(R.id.lnr_booking_details_location);
        tvOriginMyBooking = findViewById(R.id.tv_origin_my_booking);
        tvDestinationMyBooking = findViewById(R.id.tv_destination_my_booking);
        llStatus = findViewById(R.id.ll_status);
        ivPickup = findViewById(R.id.iv_pickup);
        tvDeliveryDateVal = findViewById(R.id.tv_delivery_date_val);
        ivStatus = findViewById(R.id.iv_status);
        tvStatus = findViewById(R.id.tv_status);
        tvAmount = findViewById(R.id.tv_amount);
        tvDescription = findViewById(R.id.tv_description);
        descriptionHeading = findViewById(R.id.description);

        bageCount = findViewById(R.id.tv_notification_Count);

        setData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.ib_back_button:

                if(isGoHome){
                    startActivity(new Intent(this,HomeMenuActivity.class));
                    finishAffinity();
                }else {
                    finish();
                }

                break;
        }
    }

    private void setData() {

        String bookingId = resultBean.getBooking_id();
        // String vehicleType = resultBean.ve;
        String entry = resultBean.getBook_from_address();
        String destination = resultBean.getBook_to_address();
        String bookingDate = resultBean.getCreate_dt();

        // bookingDate = Methods.getTimeStampToDate(bookingDate);

        String status = resultBean.getStatus();
        String price = resultBean.getTrip_price();
        String vehicleName = resultBean.getVehicle_name();

        switch (vehicleName) {
            case "Sedan":
                ivTruckType.setImageResource(R.drawable.sedan);
                break;

            case "Mini":
                ivTruckType.setImageResource(R.drawable.mini);
                break;

            case "Micro":

                ivTruckType.setImageResource(R.drawable.micro);
                break;

            case "SUV":

                ivTruckType.setImageResource(R.drawable.suv);

                break;

            default:
                ivTruckType.setImageResource(R.drawable.mini);
        }

        tvBookingId.setText(getText(R.string.text_booking_id) + " " + bookingId);
        tvTruckType.setText(vehicleName);
        tvOriginMyBooking.setText(entry);
        tvDestinationMyBooking.setText(destination);
        tvDeliveryDateVal.setText(bookingDate);


        tvAmount.setText(getResources().getString(R.string.rupee) + " " + price);
        tvDescription.setText(resultBean.getComments());
        if (!TextUtils.isEmpty("")) {
            descriptionHeading.setVisibility(View.GONE);
            tvDescription.setVisibility(View.GONE);
        } else {
            descriptionHeading.setVisibility(View.VISIBLE);
            tvDescription.setVisibility(View.VISIBLE);
        }

        switch (status) {
            case "Pending":
                status = getString(R.string.pending);
                tvStatus.setTextColor(getResources().getColor(R.color.pending_color));
                ivStatus.setImageResource(R.drawable.pending_icon);
                break;

            case "Processed":
                status = getString(R.string.processed);
                tvStatus.setTextColor(getResources().getColor(R.color.rating_clr));
                ivStatus.setImageResource(R.drawable.process);
                break;
            case "Completed":
                status = getString(R.string.completed);
                tvStatus.setTextColor(getResources().getColor(R.color.green));
                ivStatus.setImageResource(R.drawable.done_process);
                break;


        }

        tvStatus.setText(status);


    }

    @Override
    protected void onResume() {
        super.onResume();

        AppConstants.KEY_CONTEXT = ReportIssueSummary.this;

        if (TextUtils.isEmpty(AppConstants.notificationCount)) {

            bageCount.setVisibility(View.GONE);

        } else {

            bageCount.setVisibility(View.VISIBLE);

            bageCount.setText(AppConstants.notificationCount);

        }
    }




}
