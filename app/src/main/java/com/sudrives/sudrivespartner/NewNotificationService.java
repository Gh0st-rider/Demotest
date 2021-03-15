package com.sudrives.sudrivespartner;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sudrives.sudrivespartner.activities.HomeMenuActivity;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NewNotificationService extends FirebaseMessagingService {

    private LocalBroadcastManager broadcaster;
    private NotificationCompat.Builder notificationBuilder;
    private Uri notificationuri;

    public static int notificationId = 0;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if (!AppPreference.loadStringPref(getApplicationContext(), AppPreference.KEY_USER_ID).trim().equalsIgnoreCase("")) {
            Log.e("NotificationData", "getData: " + remoteMessage.getData().toString());

            // String type = remoteMessage.getData();
            Map<String, String> object = remoteMessage.getData();

            // createNotification("","","");


            try {
                JSONObject jsonObject = new JSONObject(object.get("data"));
                String type = jsonObject.optString("type");
                String tripId = jsonObject.optString("tripid");

                String text = object.get("text");
                String title = object.get("title");

                Log.e("type : tripid", "" + type + " " + tripId);
                Log.e("NotificationData", "getData: 111111 " + isAppIsInBackground(this));

                if (type.trim().equalsIgnoreCase("new")) {

                    if (isAppIsInBackground(this)) {

                        if (AppConstants.ONPAUSE.equals("yes")) {

                        } else {

                            Log.d("NotificationTripId", "" + tripId + type);

                            createNotificationForNewBooking(type, title, text, tripId);
                        }

                    }


                } else {

                    createNotification(type, title, text, jsonObject);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("Map", "getData: " + object);
        }

    }

    public static NotificationManager notifManager;

    @SuppressLint("WrongConstant")
    public void createNotificationForNewBooking(String type, String title, String message, String tripId) {

        Random ran = new Random();
        int x = ran.nextInt(6) + 5;

        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.new_booking_notification_layout);


        //  Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent = new Intent(this, HomeMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("tripid", tripId);
        intent.putExtra("type", type);


        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        builder = new NotificationCompat.Builder(this, id);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {

                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            if (type.equals("new")) {
                //   builder.addAction(R.drawable.arrow_left, "Accept", pendingIntent);

                Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.timmer_mussic);

                MediaPlayer mediaPlayer = AppConstants.mediaPlayer;

                try {

                    mediaPlayer.setDataSource(this, defaultSoundUri);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mediaPlayer.prepare();
                    mediaPlayer.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                notificationuri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            }


            notificationLayout.setOnClickPendingIntent(R.id.bt_accept, pendingIntent);


            builder.setSmallIcon(R.mipmap.app_icon)
                    .setCustomContentView(notificationLayout)
                    .setCustomBigContentView(notificationLayout)
                    .setAutoCancel(true)
                    .setSound(notificationuri)
                    .addAction(R.drawable.tv_true_state_right, getResources().getString(R.string.accept), pendingIntent)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.blue))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setContentIntent(pendingIntent);

//            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//            bigText.bigText(message);
//            bigText.setBigContentTitle(title);


            // builder.build().flags |= Notification.FLAG_AUTO_CANCEL;


           // builder.setStyle(bigText);

        } else {

            builder = new NotificationCompat.Builder(this);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


            if (type.equals("new")) {
                //  builder.addAction(R.drawable.tv_true_state_right, getResources().getString(R.string.accept), pendingIntent);

                Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.timmer_mussic);

                MediaPlayer mediaPlayer = AppConstants.mediaPlayer;

                try {

                    mediaPlayer.setDataSource(this, defaultSoundUri);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mediaPlayer.prepare();
                    mediaPlayer.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                notificationuri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

            notificationLayout.setOnClickPendingIntent(R.id.bt_accept, pendingIntent);


            builder.setSmallIcon(R.mipmap.app_icon)
                    .setCustomContentView(notificationLayout)
                    .setCustomBigContentView(notificationLayout)
                    .setAutoCancel(true)
                    .setSound(notificationuri)
                    .addAction(R.drawable.tv_true_state_right, getResources().getString(R.string.accept), pendingIntent)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.blue))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_HIGH);

            // builder.build().flags |= Notification.FLAG_AUTO_CANCEL;


//            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//            bigText.bigText(message);
//            bigText.setBigContentTitle(title);
//
//            builder.setStyle(bigText);


        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        notificationId = x;

        Notification notification = builder.build();
        notifManager.notify(x, notification);

        //  notifManager.cancel(x);

//        if(type.equals("new")){
//
//            Notification notification = builder.
//                    addAction(R.drawable.arrow_left, "Accept", pendingIntent).build();
//            notifManager.notify(x, notification);
//
//        }else {
//            Notification notification = builder.build();
//            notifManager.notify(x, notification);
//
//        }

    }

    @SuppressLint("WrongConstant")
    public void createNotification(String type, String title, String message, JSONObject jsonObject) {

        Random ran = new Random();
        int x = ran.nextInt(6) + 5;

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent = new Intent(this, HomeMenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        builder = new NotificationCompat.Builder(this, id);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {

                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }


            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("Type", type);

            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            /*if(type.equals("new")){
                builder.addAction(R.drawable.tv_true_state_right, "Accept", pendingIntent);
                intent.putExtra(AppConstants.KEY_TRIPID, jsonObject.optString("tripid") );

            }*/


            builder.setSmallIcon(R.mipmap.app_icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.blue))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setContentIntent(pendingIntent);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(message);
            bigText.setBigContentTitle(title);

            builder.setStyle(bigText);

        } else {

            builder = new NotificationCompat.Builder(this);

            intent = new Intent(this, HomeMenuActivity.class);


            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("Type", type);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


           /* if(type.equals("new")){
                builder.addAction(R.drawable.tv_true_state_right, "Accept", pendingIntent);
                intent.putExtra(AppConstants.KEY_TRIPID, jsonObject.optString("tripid") );

            }*/

            builder.setSmallIcon(R.mipmap.app_icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.blue))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})

                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_HIGH);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.bigText(message);
            bigText.setBigContentTitle(title);

            builder.setStyle(bigText);


        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(x, notification);



        /*if(type.equals("new")){

            Notification notification = builder.
                    addAction(R.drawable.arrow_left, "Accept", pendingIntent).build();
            notifManager.notify(x, notification);

        }else {
            Notification notification = builder.build();
            notifManager.notify(x, notification);

        }*/

    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}