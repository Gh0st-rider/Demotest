package com.sudrives.sudrivespartner.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.EarningActivity;
import com.sudrives.sudrivespartner.models.CollectionModel;
import com.sudrives.sudrivespartner.models.EarningData;
import com.sudrives.sudrivespartner.utils.AppConstants;

import java.util.List;

/**
 * Created by pankaj on 10/30/18.
 */

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.ViewHolder> {


    private List<CollectionModel.ResultBean> values;
    private Context mContext;

    public CollectionsAdapter(List<CollectionModel.ResultBean> values, Context mContext) {
        this.values = values;
        this.mContext = mContext;
    }


    @Override
    public CollectionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_collection_list_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CollectionsAdapter.ViewHolder holder, int position) {

        CollectionModel.ResultBean collectionModel = values.get(position);

        holder.tv_list_collection_booking_id.setText(mContext.getString(R.string.trip_ID).concat(" : " + collectionModel.getBooking_id()));

        String createdTime = collectionModel.getBooking_date_time();

        holder.tv_list_collection_time.setText(createdTime);
        holder.tv_list_collection_total_distance.setText(collectionModel.getTotal_distance());
        holder.tv_list_collection_cash_collection.setText(mContext.getString(R.string.rupee).concat(" ").concat("" + collectionModel.getTotal_fare()));
        holder.tv_list_collection_haulage_payout.setText(mContext.getString(R.string.rupee).concat(" ").concat("0"));

        String donation = collectionModel.getTrip_donations();

        if (!TextUtils.isEmpty(donation)) {
            holder.tv_donation_amount.setText(mContext.getString(R.string.rupee).concat(" ").concat(donation));
        }

    }

    @Override
    public int getItemCount() {

        return values.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv_list_collection;
        TextView tv_list_collection_booking_id, tv_list_collection_time, tv_list_collection_total_distance, tv_list_collection_cash_collection, tv_list_collection_haulage_payout;
        TextView tv_donation_amount;


        public ViewHolder(View view) {
            super(view);

            cv_list_collection = view.findViewById(R.id.cv_list_collection);
            tv_list_collection_booking_id = view.findViewById(R.id.tv_list_collection_booking_id);
            tv_list_collection_time = view.findViewById(R.id.tv_list_collection_time);
            tv_list_collection_total_distance = view.findViewById(R.id.tv_list_collection_total_distance);
            tv_list_collection_cash_collection = view.findViewById(R.id.tv_list_collection_cash_collection);
            tv_list_collection_haulage_payout = view.findViewById(R.id.tv_list_collection_haulage_payout);

            tv_donation_amount = view.findViewById(R.id.tv_donation_amount);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(mContext, EarningActivity.class);
            EarningData earningData = new EarningData();


            earningData.bookingId = values.get(getAdapterPosition()).getBooking_id();
            earningData.cashCollected = values.get(getAdapterPosition()).getTotal_fare();
            earningData.date = values.get(getAdapterPosition()).getCreate_dt();
            earningData.dropOffLocation = values.get(getAdapterPosition()).getBook_to_address();
            earningData.haulagePayout = "0";
            earningData.pickUpLocation = values.get(getAdapterPosition()).getBook_from_address();
            earningData.type_of_booking = values.get(getAdapterPosition()).getType_of_booking();
            earningData.time = values.get(getAdapterPosition()).getCreate_dt();
            earningData.totalDistance = values.get(getAdapterPosition()).getTotal_distance();
            earningData.tripTime = values.get(getAdapterPosition()).getTotal_time();
            earningData.donationAmount = values.get(getAdapterPosition()).getTrip_donations();

            double lat = Double.parseDouble(values.get(getAdapterPosition()).getBook_from_lat());
            double lng = Double.parseDouble(values.get(getAdapterPosition()).getBook_from_long());

            double lat1 = 0;
            double lng1 = 0;
            if (!values.get(getAdapterPosition()).getType_of_booking().trim().equalsIgnoreCase("1")) {

                lat1 = Double.parseDouble(values.get(getAdapterPosition()).getBook_to_lat());
                lng1 = Double.parseDouble(values.get(getAdapterPosition()).getBook_to_long());
            }


            earningData.originlat = lat;
            earningData.originlng = lng;
            earningData.destlat = lat1;
            earningData.destlng = lng1;


            intent.putExtra(AppConstants.EARNING_DATA, earningData);

            mContext.startActivity(intent);

        }
    }

//    private String createdTime(String createdTime) {
//
//        String displayValue = "";
//
//        try {
//
//            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = dateFormatter.parse(createdTime);
//
//
//            date = dateFormatter.parse(createdTime);
//
//            SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
//            displayValue = timeFormatter.format(date);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return displayValue;
//    }

}
