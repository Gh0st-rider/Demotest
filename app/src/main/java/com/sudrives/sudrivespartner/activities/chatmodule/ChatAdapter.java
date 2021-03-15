package com.sudrives.sudrivespartner.activities.chatmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudrives.sudrivespartner.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    Context mContext;
    List<FetchMessagesModel> mChat;

    public ChatAdapter(Context mContext, List<FetchMessagesModel> mChat) {
        this.mChat = mChat;
        this.mContext = mContext;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FetchMessagesModel chat = mChat.get(position);

        holder.tvChatMsg.setText(chat.getMsg());
        holder.tvChatTime.setText(chat.getChatTime());


    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvChatMsg;
        public TextView tvChatTime;

        public ViewHolder(View itemView) {
            super(itemView);

            tvChatMsg = itemView.findViewById(R.id.show_message);
            tvChatTime = itemView.findViewById(R.id.time_tv);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mChat.get(position).getIsUser().equals("user")) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}