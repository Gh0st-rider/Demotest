package com.sudrives.sudrivespartner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.sudrives.sudrivespartner.utils.Methods;

public class NetworkChangeReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.d("NETWORKSTATE","NOT AVAIALBE");

        if(checkInternet(context))
        {
            Toast.makeText(context, "Network Available Do operations",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Network not avaialable",Toast.LENGTH_LONG).show();

        }

    }

    boolean checkInternet(Context context) {

        if (Methods.isNetworkConnected(context)) {
            return true;
        } else {
            return false;
        }
    }

}
