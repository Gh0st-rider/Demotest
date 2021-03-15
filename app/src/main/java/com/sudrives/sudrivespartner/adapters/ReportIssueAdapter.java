package com.sudrives.sudrivespartner.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.ReportIssueSummary;
import com.sudrives.sudrivespartner.models.ReportIssueModel;

import java.util.List;

public class ReportIssueAdapter extends RecyclerView.Adapter<ReportIssueAdapter.ViewHolder> {

    List<ReportIssueModel.ResultBean> resultBeans;
    Context context ;

    public ReportIssueAdapter(List<ReportIssueModel.ResultBean> resultBeans, Context context) {
        this.resultBeans = resultBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_issue_list_view,null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


//        1 : Accepted
//        2: Trip Start
//        3: Completed
//        4: Cancelled (by user)
//        5: Cancelled (by driver)
//        6: Pending

        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cardViewParams.setMargins(0,20,0,0);

        holder.cardView.setLayoutParams(cardViewParams);

        ReportIssueModel.ResultBean resultBean = resultBeans.get(position);

        String bookingId = resultBean.getBooking_id();
        // String vehicleType = resultBean.ve;
        String entry = resultBean.getBook_from_address();
        String destination = resultBean.getBook_to_address();
        String bookingDate = resultBean.getCreate_dt();

       // bookingDate = Methods.getTimeStampToDate(bookingDate);

        String status = resultBean.getStatus();
        String price = resultBean.getTrip_price();
        String vehicleName = resultBean.getVehicle_name();

        holder.tvBookingId.setText(context.getText(R.string.text_booking_id)+bookingId);
        holder.tvTruckType.setText(vehicleName);
        holder.tvOriginMyBooking.setText(entry);
        holder.tvDestinationMyBooking.setText(destination);
        holder.tvDeliveryDateVal.setText(bookingDate);


        switch (vehicleName) {
            case "Sedan":
                holder.ivTruckType.setImageResource(R.drawable.sedan);
                break;

            case "Mini":
                holder.ivTruckType.setImageResource(R.drawable.mini);
                break;

            case "Micro":
                holder.ivTruckType.setImageResource(R.drawable.micro);
                break;

            case "SUV":
                holder.ivTruckType.setImageResource(R.drawable.suv);

                break;

            default:
                holder.ivTruckType.setImageResource(R.drawable.mini);
        }

        switch (status) {
            case "Pending":
                status = context.getString(R.string.pending);
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.pending_color));
                holder.ivStatus.setImageResource(R.drawable.pending_icon);
                break;

            case "Processed":
                status = context.getString(R.string.processed);
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.rating_clr));
                holder.ivStatus.setImageResource(R.drawable.process);
                break;
            case "Completed":
                status = context.getString(R.string.completed);
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
                holder.ivStatus.setImageResource(R.drawable.done_process);
                break;



        }

//        Pending
//        Processed
//        Completed

        holder.tvStatus.setText(status);
        holder.tvAmount.setText(context.getResources().getString(R.string.rupee)+" "+price);

    }

    @Override
    public int getItemCount() {
        return resultBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
        private View viewStatus;
        private ImageView ivStatus;
        private TextView tvStatus;
        private View viewAmount;
        private ImageView ivAmount;
        private TextView tvAmount;
        private View view;
        private CardView cardView;

        public ViewHolder(View view) {
            super(view);

            rlOrigin = view.findViewById(R.id.rl_origin);
            llBookingId = view.findViewById(R.id.ll_booking_id);
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
            cardView = view.findViewById(R.id.card);

            cardView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(context, ReportIssueSummary.class);

            intent.putExtra("Reportsummary",resultBeans.get(getAdapterPosition()));
            intent.putExtra("isGoToHome",false);

            context.startActivity(intent);

        }
    }
}
