package com.sudrives.sudrivespartner.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.BookingDetailsBeans;
import com.sudrives.sudrivespartner.utils.Methods;
import com.sudrives.sudrivespartner.utils.interfaces.ReportIssueClickListner;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

    List<BookingDetailsBeans.ResultBean> resultBeans;
    ReportIssueClickListner reportIssueClickListner;
    Context context;

    public BookingHistoryAdapter(List<BookingDetailsBeans.ResultBean> resultBeans, ReportIssueClickListner reportIssueClickListner, Context context) {
        this.resultBeans = resultBeans;
        this.reportIssueClickListner = reportIssueClickListner;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_history_view, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


//        Sedan
//                Mini
//        Micro


//        1 : Accepted
//        2: Trip Start
//        3: Completed
//        4: Cancelled (by user)
//        5: Cancelled (by driver)
//        6: Pending

        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cardViewParams.setMargins(0, 20, 0, 0);

        holder.cardView.setLayoutParams(cardViewParams);

        BookingDetailsBeans.ResultBean resultBean = resultBeans.get(position);

        String bookingId = resultBean.booking_id;
        // String vehicleType = resultBean.ve;
        String entry = resultBean.book_from_address;
        String destination = resultBean.book_to_address;
        String bookingDate = resultBean.booking_date;

        bookingDate = Methods.getTimeStampToDate(bookingDate);

        String status = resultBean.booking_status;
        String price = resultBean.total_fare;


        String vehicleName = resultBean.vehicle_name;

        String paymantStatus = resultBean.payment_id;

        String paymentMode = resultBean.is_online_payment_accept;

        if (paymentMode.equalsIgnoreCase("0")) {
            holder.tv_payment_status.setText(resultBean.payment_mode);

        } else {

            if (!TextUtils.isEmpty(paymantStatus)) {
                holder.tv_payment_status.setText(resultBean.payment_mode);
            } else {
                holder.tv_payment_status.setText(resultBean.payment_mode);

            }
        }


        holder.tvBookingId.setText(context.getText(R.string.text_booking_id) + bookingId);

        switch (vehicleName) {
            case "Sedan":
            case "SUV":
                holder.ivTruckType.setImageResource(R.drawable.luxary_car_side);
                break;

            case "Mini":
            case "Micro":
                holder.ivTruckType.setImageResource(R.drawable.mini_car_side);
                break;



            case "Auto":
                break;

            case "Bike":
                holder.ivTruckType.setImageResource(R.drawable.bike_hdpi);

                break;

            default:
                holder.ivTruckType.setImageResource(R.drawable.mini_car_side);
        }

        holder.tvTruckType.setText("Mini");
        holder.tvOriginMyBooking.setText(entry);
        holder.tvDestinationMyBooking.setText(destination);
        holder.tvDeliveryDateVal.setText(bookingDate);
        holder.tvAmount.setText(context.getResources().getString(R.string.rupee) + " " + price);

        switch (status) {
            case "1":
                status = context.getString(R.string.accepted);

                break;
            case "2":
                status = context.getString(R.string.trip_start);
                break;
            case "3":
                status = context.getString(R.string.completed);
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
                holder.ivStatus.setImageResource(R.drawable.check_mark_black_outline);
                break;
            case "4":
            case "5":
                status = context.getString(R.string.cancelled);
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red_error_color));
                holder.ivStatus.setImageResource(R.drawable.shape_red);
                holder.tvAmount.setText(context.getResources().getString(R.string.rupee) + " 0 " );
                holder.iv_cancel.setVisibility(View.VISIBLE);
                holder.tvStatus.setVisibility(View.GONE);
                holder.ivStatus.setVisibility(View.GONE);

                break;
            case "6":
                status = context.getString(R.string.pending);
                break;


        }
        holder.tvStatus.setText(status);

        if (resultBean.type_of_booking.trim().equalsIgnoreCase("1")){

            //holder.iv_hisory_pickup_location.setVisibility(View.VISIBLE);
            //holder.iv_hisory_location_icon.setVisibility(View.GONE);
            holder.tvDestinationMyBooking.setVisibility(View.GONE);
        }else {

           // holder.iv_hisory_pickup_location.setVisibility(View.GONE);
           // holder.iv_hisory_location_icon.setVisibility(View.VISIBLE);
            holder.tvDestinationMyBooking.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return resultBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout rlOrigin;
        private ConstraintLayout llBookingId;
        private TextView tvBookingId;
        private ImageView ivTruckType;
        private TextView tvTruckType;
        private LinearLayout lnrBookingDetailsLocation;
        private TextView tvOriginMyBooking;
        private TextView tvDestinationMyBooking;
        private LinearLayout llStatus;
        private ImageView ivPickup;
        //private ImageView iv_hisory_location_icon;
        private ImageView iv_hisory_pickup_location;
        private TextView tvDeliveryDateVal;
        private View viewStatus;
        private ImageView ivStatus, iv_cancel;
        private TextView tvStatus;
        private View viewAmount;
        private ImageView ivAmount;
        private TextView tvAmount;
        private View view;
        private TextView tvReportIssue;
        private CardView cardView;
        private TextView tv_payment_status;

        public ViewHolder(View view) {
            super(view);

            //iv_hisory_location_icon = view.findViewById(R.id.iv_hisory_location_icon);
            iv_hisory_pickup_location = view.findViewById(R.id.iv_hisory_pickup_location);
            cardView = view.findViewById(R.id.card);
            rlOrigin = view.findViewById(R.id.rl_origin);
            llBookingId = view.findViewById(R.id.ll_booking_id);
            iv_cancel = view.findViewById(R.id.iv_cancel);
            tvBookingId = view.findViewById(R.id.tv_booking_id);
            ivTruckType = view.findViewById(R.id.iv_truck_type);
            tvTruckType = view.findViewById(R.id.tv_truck_type);
            lnrBookingDetailsLocation = view.findViewById(R.id.lnr_booking_details_location);
            tvOriginMyBooking = view.findViewById(R.id.tv_origin_my_booking);
            tvDestinationMyBooking = view.findViewById(R.id.tv_destination_my_booking);
            llStatus = view.findViewById(R.id.ll_status);
            ivPickup = view.findViewById(R.id.iv_pickup);
            tvDeliveryDateVal = view.findViewById(R.id.tv_delivery_date_val);
            ivStatus = view.findViewById(R.id.iv_status);
            tvStatus = view.findViewById(R.id.tv_status);
            tvAmount = view.findViewById(R.id.tv_amount);
            tv_payment_status = view.findViewById(R.id.tv_payment_status);
//            view = view.findViewById(R.id.view);
            tvReportIssue = view.findViewById(R.id.tv_report_issue);

            tvReportIssue.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            reportIssueClickListner.onClick(getAdapterPosition());
        }
    }


}
