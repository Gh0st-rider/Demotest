package com.sudrives.sudrivespartner.networks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.sudrives.sudrivespartner.utils.AppConstants;

import java.util.regex.Pattern;

public class MySMSBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get Bundle object contained in the SMS intent passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str = "";
        Log.e("registerReceiver", "registerReceiver: "+bundle);

        Status smsRetrieverStatus = (Status) bundle.get(SmsRetriever.EXTRA_STATUS);
      if (bundle != null) {


          switch (smsRetrieverStatus.getStatusCode()) {

              case CommonStatusCodes.SUCCESS:
                  String otp = (String) bundle.get(SmsRetriever.EXTRA_SMS_MESSAGE);

                  otp = otp.replace(AppConstants.SMS_KEY, "");
                  Pattern pattern = Pattern.compile("[^\\d+]");
                  sms_str += otp;
                  Log.e("registerReceiver", "sms_str 2222: "+sms_str);

                  sms_str = sms_str.toLowerCase();
                  Log.e("registerReceiver", "sms_str 3333: "+sms_str);
                  sms_str = pattern.matcher(sms_str).replaceAll("");
                  Log.e("registerReceiver", "sms_str 444: "+sms_str);
                  Log.e("MySMSBroadCastReceiver", "OTP: " + sms_str);

//Check here sender is yours
                  Intent smsIntent = new Intent("otp");
                  smsIntent.putExtra("message", sms_str);

                  LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);

                  break;


              case CommonStatusCodes.TIMEOUT:
                  // Waiting for SMS timed out (5 minutes)
                  // Handle the error ...
                  break;

          }
      }


          /*  // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i = 0; i < smsm.length; i++) {
                smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                Log.e("registerReceiver", "smsm[i]: "+smsm[i]);
                Log.e("registerReceiver", "sms_str 1111: "+sms_str);


                Pattern pattern = Pattern.compile("[^\\d+]");
                sms_str += smsm[i].getMessageBody().toString();
                Log.e("registerReceiver", "sms_str 2222: "+sms_str);

                sms_str = sms_str.toLowerCase();
                Log.e("registerReceiver", "sms_str 3333: "+sms_str);
                sms_str = pattern.matcher(sms_str).replaceAll("");
                Log.e("registerReceiver", "sms_str 444: "+sms_str);

                String Sender = smsm[i].getOriginatingAddress();
                //Check here sender is yours
                Intent smsIntent = new Intent("otp");
                smsIntent.putExtra("message", sms_str);

                LocalBroadcastManager.getInstance(context).sendBroadcast(smsIntent);


        }*/
    }
}
