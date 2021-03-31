package com.sudrives.sudrivespartner.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.BuySubscriptionActivity;
import com.sudrives.sudrivespartner.models.cashourRequest.Result;

import java.util.List;

/**
 * Created by Shashank on 10/30/18.
 */

public class CashoutRequestListAdapter extends RecyclerView.Adapter<CashoutRequestListAdapter.ViewHolder> {


    private List<com.sudrives.sudrivespartner.models.cashourRequest.Result> values;
    private Context mContext;
    private String rupeesymbol = "\u20b9";

    public CashoutRequestListAdapter(List<com.sudrives.sudrivespartner.models.cashourRequest.Result> values, Context mContext) {
        this.values = values;
        this.mContext = mContext;
    }


    @Override
    public CashoutRequestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_request_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CashoutRequestListAdapter.ViewHolder holder, int position) {

        com.sudrives.sudrivespartner.models.cashourRequest.Result ResultResult = values.get(position);
        if (ResultResult.getStatus().equalsIgnoreCase("Approved")){
            holder.status.setText(ResultResult.getStatus().trim());
            holder.raised_amount.setText(rupeesymbol+ResultResult.getAmount().trim());
            holder.start_end_date.setText("Req Raised"+ResultResult.getRequestDate().trim()+"\nReq Apporved: "+ResultResult.getPaymentDate().trim());
        }else if (ResultResult.getStatus().equalsIgnoreCase("Pending")){
            holder.const1.setBackground(ContextCompat.getDrawable(mContext, R.color.yellow));
            holder.requestStatus.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            holder.status.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            holder.raised_amount.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            holder.start_end_date.setTextColor(ContextCompat.getColor(mContext,R.color.black));

            holder.status.setText(ResultResult.getStatus().trim());
            holder.raised_amount.setText(rupeesymbol+ResultResult.getAmount().trim());
            holder.start_end_date.setText("Req Raised: "+ResultResult.getRequestDate().trim());

        }


    }

    @Override
    public int getItemCount() {

        return values.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView status, requestStatus, raised_amount, start_end_date;
        ConstraintLayout const1;

        public ViewHolder(View view) {
            super(view);

            status = view.findViewById(R.id.status);
            requestStatus = view.findViewById(R.id.requestStatus);
            raised_amount = view.findViewById(R.id.raised_amount);
            start_end_date = view.findViewById(R.id.start_end_date);
            const1 = view.findViewById(R.id.const1);


        }

        @Override
        public void onClick(View view) {

            final Result ResultResult = values.get(getAdapterPosition());



        }
    }
}
