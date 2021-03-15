package com.sudrives.sudrivespartner.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.models.DeclineRequestModel;
import com.sudrives.sudrivespartner.utils.interfaces.ReportIssueClickListner;

import java.util.ArrayList;
import java.util.List;

public class DeclineRequestAdapter extends RecyclerView.Adapter<DeclineRequestAdapter.ViewHolder> {

    List<DeclineRequestModel.ResultBean.DeclineRequestBean> mDeclineRequestList = new ArrayList<>();

    ReportIssueClickListner reportIssueClickListner;
    Context context;

    public DeclineRequestAdapter(List<DeclineRequestModel.ResultBean.DeclineRequestBean> resultBeans, Context context) {
        this.mDeclineRequestList = resultBeans;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.decline_request_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        /*LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cardViewParams.setMargins(0,20,0,0);

        holder.cardView.setLayoutParams(cardViewParams);

        BookingDetailsBeans.ResultBean resultBean = resultBeans.get(position);*/

        final DeclineRequestModel.ResultBean.DeclineRequestBean been = mDeclineRequestList.get(position);

        holder.radioButton.setText(been.getName());

        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <mDeclineRequestList.size() ; i++) {

                    DeclineRequestModel.ResultBean.DeclineRequestBean model = mDeclineRequestList.get(i);
                    mDeclineRequestList.set(i, new DeclineRequestModel.ResultBean.DeclineRequestBean(model.getId(), model.getName(), model.getTypes(), false));
                }


                mDeclineRequestList.set(position, new DeclineRequestModel.ResultBean.DeclineRequestBean(been.getId(), been.getName(), been.getTypes(), true));

                notifyDataSetChanged();

            }
        });


        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                for (int i = 0; i <mDeclineRequestList.size() ; i++) {

                    DeclineRequestModel.ResultBean.DeclineRequestBean model = mDeclineRequestList.get(i);
                    mDeclineRequestList.set(i, new DeclineRequestModel.ResultBean.DeclineRequestBean(model.getId(), model.getName(), model.getTypes(), false));
                }


                mDeclineRequestList.set(position, new DeclineRequestModel.ResultBean.DeclineRequestBean(been.getId(), been.getName(), been.getTypes(), true));

                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDeclineRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RadioButton radioButton;
        private LinearLayout ll_top;


        public ViewHolder(View view) {
            super(view);


            radioButton = view.findViewById(R.id.radioButton);
            ll_top = view.findViewById(R.id.ll_top);


          //  tvReportIssue.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            reportIssueClickListner.onClick(getAdapterPosition());
        }
    }
}
