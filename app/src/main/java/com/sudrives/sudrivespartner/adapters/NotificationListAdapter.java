package com.sudrives.sudrivespartner.adapters;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.NotificationActivity;
import com.sudrives.sudrivespartner.models.NotificationModel;
import com.sudrives.sudrivespartner.utils.interfaces.NotificationClickListner;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    List<NotificationModel.ResultBean> resultBeans;
    NotificationActivity mContext;
    int pageNo;
    NotificationClickListner notificationClickListner;

    public NotificationListAdapter(List<NotificationModel.ResultBean> resultBeans, NotificationActivity mContext, int pageNo, NotificationClickListner notificationClickListner) {
        this.resultBeans = resultBeans;
        this.mContext = mContext;
        this.pageNo = pageNo;
        this.notificationClickListner = notificationClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int totalSize = resultBeans.size();
        totalSize = totalSize-1;

        if(position == totalSize){
            notificationClickListner.onRecordEnd();
        }

        NotificationModel.ResultBean resultBean = resultBeans.get(position);

        holder.tvNotificationTitle.setText(resultBean.getTitle());
        holder.tvNotificationMsg.setText(Html.fromHtml(resultBean.getMessage()));

        // String time = getTime(resultBean.getCreate_dt());
        holder.tvNotificationTime.setText(resultBean.getCreate_dt());

        if (resultBean.getRead_status().equals("0")) {
            holder.itemView.setAlpha(1.0f);
        } else {
            holder.itemView.setAlpha(.60f);
        }
    }

    @Override
    public int getItemCount() {
        return resultBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardview;
        private RelativeLayout rlNotificationlist;
        private RelativeLayout rlNotification;
        private LinearLayout llNotification;
        private ImageView ivNotification;
        private TextView tvNotificationTitle;
        private TextView tvNotificationMsg;
        private TextView tvNotificationTime;

        public ViewHolder(View view) {
            super(view);

            cardview = view.findViewById(R.id.cardview);
            rlNotificationlist = view.findViewById(R.id.rl_notificationlist);
            rlNotification = view.findViewById(R.id.rl_notification);
            llNotification = view.findViewById(R.id.ll_notification);
            ivNotification = view.findViewById(R.id.iv_notification);
            tvNotificationTitle = view.findViewById(R.id.tv_notification_title);
            tvNotificationMsg = view.findViewById(R.id.tv_notification_msg);
            tvNotificationTime = view.findViewById(R.id.tv_notification_time);

            tvNotificationTitle.setOnClickListener(this);
            tvNotificationMsg.setOnClickListener(this);
            tvNotificationTime.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (resultBeans.get(getAdapterPosition()).getRead_status().equals("0"))
                notificationClickListner.onRead(getAdapterPosition());
        }
    }

    private String getTime(String time) {

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(time));
        String date = DateFormat.format("dd-MMM-yy,hh:mm a", cal).toString();
        return date;


    }
}
