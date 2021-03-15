package com.sudrives.sudrivespartner.utils;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import io.fabric.sdk.android.Fabric;

public class MyApplication extends Application {

    private static Context context;

/*

    public Socket mSocket;
    private static MyApplication TheApp;



    {
        try {
            mSocket = IO.socket(AppConstants.BASE_SOCKET_URL);
        } catch (URISyntaxException e) {
            Log.e("dsfdsfdsfdsf","TEST: "+e.getMessage());
            throw new RuntimeException(e);

        }
    }
*/




    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

//        TheApp = this;
        context = getApplicationContext();
        installServiceProviderIfNeeded(context);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


  /*  public static Context getAppContext() {
        return MyApplication.context;
    }
    public Socket getSocket() {
        return mSocket;
    }


    public static MyApplication getApp() {
        return TheApp;
    }
*/

    public static void installServiceProviderIfNeeded(Context context) {
        try {
            ProviderInstaller.installIfNeeded(context);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();

            // Prompt the user to install/update/enable Google Play services.
            GooglePlayServicesUtil.showErrorNotification(e.getConnectionStatusCode(), context);

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
