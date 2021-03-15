package com.sudrives.sudrivespartner.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.BuySubscriptionActivity;
import com.sudrives.sudrivespartner.activities.EarningActivity;
import com.sudrives.sudrivespartner.models.CollectionModel;
import com.sudrives.sudrivespartner.models.EarningData;
import com.sudrives.sudrivespartner.models.subscriptionPlans.Result;
import com.sudrives.sudrivespartner.models.subscriptionPlans.Result;
import com.sudrives.sudrivespartner.utils.AppConstants;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by pankaj on 10/30/18.
 */

public class SubscriptionPlanAdapter extends RecyclerView.Adapter<SubscriptionPlanAdapter.ViewHolder> {


    private List<Result> values;
    private Context mContext;
    private String rupeesymbol = "\u20b9";

    public SubscriptionPlanAdapter(List<Result> values, Context mContext) {
        this.values = values;
        this.mContext = mContext;
    }


    @Override
    public SubscriptionPlanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription_plans, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SubscriptionPlanAdapter.ViewHolder holder, int position) {

        Result ResultResult = values.get(position);
        holder.title.setText(ResultResult.getPlanName());
        holder.discount.setText("Dis-: "+ResultResult.getDiscountPersent()+"%");
        holder.plan_price.setText("Amt-: "+rupeesymbol+ResultResult.getPrice().toString());
        holder.plan_validity.setText("Val-:"+ResultResult.getValidity());

    }

    @Override
    public int getItemCount() {

        return values.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,plan_validity,plan_price,discount;
        ConstraintLayout const1;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            plan_validity = view.findViewById(R.id.plan_validity);
            plan_price = view.findViewById(R.id.plan_price);
            discount = view.findViewById(R.id.discount);
            const1 = view.findViewById(R.id.const1);
            const1.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            final Result ResultResult = values.get(getAdapterPosition());


            const1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, BuySubscriptionActivity.class);
                    i.putExtra("id",ResultResult.getId().toString());
                    i.putExtra("name",ResultResult.getPlanName());
                    i.putExtra("dis", ResultResult.getDiscountPersent());
                    i.putExtra("val", ResultResult.getValidity());
                    i.putExtra("price",ResultResult.getPrice().toString());
                    mContext.startActivity(i);
                }
            });
        }
    }
}
