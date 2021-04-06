package com.sudrives.sudrivespartner.networks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.net.Uri;
import android.util.Log;
import android.widget.ProgressBar;


import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.utils.AppConstants;
import com.sudrives.sudrivespartner.utils.AppDialogs;
import com.sudrives.sudrivespartner.utils.AppPreference;
import com.sudrives.sudrivespartner.utils.ErrorLayout;


import org.json.JSONObject;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.sudrives.sudrivespartner.utils.AppConstants.EVENT_GET_DRIVER_ACCOUNT_DETAILS;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_ACCOUNT_ID;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_ACCOUNT_NUMBER;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_AMOUNT;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_BANK_NAME;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_HOLDER_NAME;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_IFSC;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_PAYMENT_TYPE;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_PAYTM_NUMBER;
import static com.sudrives.sudrivespartner.utils.AppConstants.KEY_UP_NUMBER;

/**
 * Created on 9/28/17.
 */

public class NetworkConn {
    private static final NetworkConn ourInstance = new NetworkConn();
    private ProgressBar progressBar;
    static Context mContext;
    private AppDialogs appDialogs = new AppDialogs();

    public static NetworkConn getInstance(Context context) {
        mContext = context;


        return ourInstance;

    }


    private NetworkConn() {


    }


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");



    public void makeRequest(final Context context, final Request request, final OnRequestResponse requestResponse, final String strEventType) {


        if (checkNetworkConnectivity(context) == true) {
            // SSLContext sslContext = SslUtils.getSslContextForCertificateFile(context, "Shika365.crt");

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            final Dialog dialog = appDialogs.showLoader(context);

            new OkHttpClient.Builder()

                    .connectTimeout(40000, TimeUnit.SECONDS)
                    .writeTimeout(30000, TimeUnit.SECONDS)
                    .readTimeout(30000, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    // .sslSocketFactory(sslContext.getSocketFactory())
                    .retryOnConnectionFailure(false)
                    .build().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //  mProgressBarHandler.hide();
                            //    pd.dismiss();
                            appDialogs.hideLoader();

                            appDialogs.showToast(context, e.getMessage());

                        }
                    });


                    e.printStackTrace();
                    requestResponse.onRequestFailed();

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    try {

                        dialog.dismiss();

                        String str = response.body().string();
                        Log.e("onResponse", "onResponse: " + str);
                        final JSONObject jsonObject = new JSONObject(str);
                        response.body().close();

                        Log.e("onResponse", "onResponse: " + str);


                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //   mProgressBarHandler.hide();
                                //     pd.dismiss();
                                appDialogs.hideLoader();
                            }
                        });


                        final int status = jsonObject.getInt(AppConstants.KEY_STATUS);
                        final String message = jsonObject.getString(AppConstants.KEY_MESSAGE);

                        if (status == 0) {

                            requestResponse.onRequestFailed();

                            // AppUtil.showToast(mContext, message);


                            int errorCode = jsonObject.optInt(AppConstants.KEY_ERROR_CODE, 0);
                            int no_popup = jsonObject.optInt(AppConstants.KEY_NO_POPUP, 0);
                            Log.e("onResponse", "onResponse: " + errorCode);
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    requestResponse.onResponse(jsonObject, strEventType);
                                }
                            });
                            if (no_popup == 1) {

                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestResponse.onResponse(jsonObject, strEventType);
                                    }
                                });
                            } else if (errorCode == 461) {

                                AppDialogs.sessionPopPup(mContext);


                            } else if ((errorCode == 0 || errorCode == 1) && !strEventType.equalsIgnoreCase(EVENT_GET_DRIVER_ACCOUNT_DETAILS)) {
                                AppDialogs.errorPopup(mContext, message, null);

                            } else if (errorCode == 225 || errorCode == 236) {

                                appDialogs.showToast(mContext, message);

                            } else if (errorCode == 461) {

                               /* Intent intent = new Intent(mContext, LoginActivity.class);
                                mContext.startActivity(intent);
                                ActivityCompat.finishAffinity((Activity) mContext);
                                SessionStore.clear(mContext, AppConstants.KEY_PREF_NAME);
                                appDialogs.showToast(mContext, message);*/
                            } else if (errorCode == 466) {


                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new androidx.appcompat.app.AlertDialog.Builder(mContext).setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            @SuppressLint("NewApi")
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                AppPreference.clearAll(mContext);

                                                String url = "";
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse(url));
                                                ((Activity) context).startActivity(intent);
                                                dialog.dismiss();

                                            }
                                        }).setNegativeButton(mContext.getString(R.string.no), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        }).setTitle(mContext.getString(R.string.title_update))
                                                .setMessage(message)
                                                .setCancelable(false)
                                                .show();
                                    }
                                });


                            } else if (errorCode == 467) {

                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new androidx.appcompat.app.AlertDialog.Builder(mContext).setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            @SuppressLint("NewApi")
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                String url = "";
                                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(Uri.parse(url));
                                                ((Activity) context).startActivity(intent);
                                                dialog.dismiss();

                                            }
                                        }).setTitle(mContext.getString(R.string.title_update))
                                                .setMessage(message)
                                                .setCancelable(false)
                                                .show();
                                    }
                                });


                            } else {

                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestResponse.onResponse(jsonObject, strEventType);
                                    }
                                });
                            }


                        } else if (status == 3) {

                            AppDialogs.failedPopup(mContext, message, null);


                        } else {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    requestResponse.onResponse(jsonObject, strEventType);
                                }
                            });

                        }


                    } catch (Exception e) {
                        appDialogs.hideLoader();
                        e.printStackTrace();

                    }


                }
            });
        } else {
            appDialogs.showToast(context, "No Internet Connection");
            requestResponse.onNoNetworkConnectivity();
        }
    }


    public void makeRequestForCity(final Context context, final Request request, final OnRequestResponse requestResponse, final String strEventType) {


        if (checkNetworkConnectivity(context) == true) {
            // SSLContext sslContext = SslUtils.getSslContextForCertificateFile(context, "Shika365.crt");

            final Dialog dialog = appDialogs.showLoader(context);

            new OkHttpClient.Builder()

                    .connectTimeout(40000, TimeUnit.SECONDS)
                    .writeTimeout(30000, TimeUnit.SECONDS)
                    .readTimeout(30000, TimeUnit.SECONDS)
                    // .sslSocketFactory(sslContext.getSocketFactory())
                    .retryOnConnectionFailure(false)
                    .build().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //  mProgressBarHandler.hide();
                            //    pd.dismiss();
                            appDialogs.hideLoader();

                            appDialogs.showToast(context, e.getMessage());

                        }
                    });


                    e.printStackTrace();
                    requestResponse.onRequestFailed();

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {

                    try {

                        dialog.dismiss();

                        String str = response.body().string();
                        Log.e("onResponse", "onResponse: " + str);
                        final JSONObject jsonObject = new JSONObject(str);
                        response.body().close();

                        Log.e("onResponse", "onResponse: " + str);


                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //   mProgressBarHandler.hide();
                                //     pd.dismiss();
                                appDialogs.hideLoader();
                            }
                        });


                        final int status = jsonObject.getInt(AppConstants.KEY_STATUS);
                        final String message = jsonObject.getString(AppConstants.KEY_MESSAGE);

                        if (status == 0) {

                            requestResponse.onRequestFailed();

                            // AppUtil.showToast(mContext, message);


                            int errorCode = jsonObject.optInt(AppConstants.KEY_ERROR_CODE, 0);
                            int no_popup = jsonObject.optInt(AppConstants.KEY_NO_POPUP, 0);
                            Log.e("onResponse", "onResponse: " + errorCode);

                            if (no_popup == 1) {

                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestResponse.onResponse(jsonObject, strEventType);
                                    }
                                });
                            } else if (errorCode == 461) {

                                AppDialogs.sessionPopPup(mContext);


                            } else if (errorCode == 0 || errorCode == 1) {
//                                AppDialogs.errorPopup(mContext, message, null);

                            } else if (errorCode == 225 || errorCode == 236) {

                                appDialogs.showToast(mContext, message);

                            } else if (errorCode == 461) {

                               /* Intent intent = new Intent(mContext, LoginActivity.class);
                                mContext.startActivity(intent);
                                ActivityCompat.finishAffinity((Activity) mContext);
                                SessionStore.clear(mContext, AppConstants.KEY_PREF_NAME);
                                appDialogs.showToast(mContext, message);*/
                            } else if (errorCode == 466) {

                                //Update with cancel
                               /* AppDialogs.doubleButtonWithCallBackVersionDialog(mContext, R.layout.confirmation_dialog, message, "Application Update!!!",
                                        mContext.getResources().getString(R.string.update), mContext.getResources().getString(R.string.action_cancel), new DoubleButoonCallback() {

                                            @Override
                                            public void doubleButoonSuccess(String text) {

                                                requestResponse.onResponse(jsonObject, AppConstants.KEY_OK);

                                            }
                                        }, new DoubleButoonCallback() {
                                            @Override
                                            public void doubleButoonSuccess(String text) {


                                                requestResponse.onResponse(jsonObject, AppConstants.KEY_CANCEL);

                                            }
                                        });*/


                            } else if (errorCode == 467) {

                                //update with ok button
                               /* AppDialogs.singleButtonVersionDialog(mContext, R.layout.success_dialog1, "Application Update!!!", mContext.getResources().getString(R.string.icon_circul_check), message, mContext.getResources().getString(R.string.update),
                                        new SingleButoonCallback() {
                                            @Override
                                            public void singleButtonSuccess(String from) {
                                                requestResponse.onResponse(jsonObject, "Ok");
                                            }
                                        }, true);*/


                            } else {

                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestResponse.onResponse(jsonObject, strEventType);
                                    }
                                });
                            }


                        } else if (status == 3) {

                            AppDialogs.failedPopup(mContext, message, null);


                        } else {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    requestResponse.onResponse(jsonObject, strEventType);
                                }
                            });

                        }


                    } catch (Exception e) {
                        appDialogs.hideLoader();
                        e.printStackTrace();

                    }


                }
            });
        } else {
            appDialogs.showToast(context, "No Internet Connection");
            requestResponse.onNoNetworkConnectivity();
        }
    }


    public Request createRawDataRequest(String url, String json) {

        String str_UserId = "", strToken = "";

      /*  if (SessionStore.getUserDetails(this.mContext, AppConstants.KEY_PREF_NAME).get(SessionStore.USER_ID) != null) {
            str_UserId = SessionStore.getUserDetails(this.mContext, AppConstants.KEY_PREF_NAME).get(SessionStore.USER_ID);
            strToken = SessionStore.getUserDetails(this.mContext, AppConstants.KEY_PREF_NAME).get(SessionStore.USER_TOKEN);
        }
*/


        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
//                .addHeader(AppConstants.KEY_APP_VERSION, String.valueOf(BuildConfig.VERSION_CODE))
                .addHeader(AppConstants.KEY_APP_VERSION, AppConstants.VERSION_CODE)
                .addHeader(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID))
                .addHeader(AppConstants.KEY_LANGU, AppPreference.loadStringPref(mContext, AppPreference.KEY_LANGUAGE))
                .addHeader(AppPreference.KEY_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_TOKEN))
                .addHeader(AppConstants.KEY_TIMEZONE, "IST")
                .addHeader(AppConstants.KEY_DEVICE_TYPE, "Android")
                .addHeader(AppConstants.KEY_DEVICE_FCM_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_FCM_TOKEN))
                .addHeader(AppConstants.KEY_CONTENT_TYPE, "application/json")
                .build();

        Log.e("dfgdfgdfg", "dfgdfgdfgdfg: " + AppPreference.loadStringPref(mContext, AppPreference.KEY_LANGUAGE));
        Log.e("dfgdfgdfg", "dfgdfgdfgdfg: " + request.header(AppConstants.KEY_LANGU));

        return request;
    }

    public Request createGetRequest(String url) {

        String str_UserId = "", strToken = "";

      /*  if (SessionStore.getUserDetails(this.mContext, AppConstants.KEY_PREF_NAME).get(SessionStore.USER_ID) != null) {
            str_UserId = SessionStore.getUserDetails(this.mContext, AppConstants.KEY_PREF_NAME).get(SessionStore.USER_ID);
            strToken = SessionStore.getUserDetails(this.mContext, AppConstants.KEY_PREF_NAME).get(SessionStore.USER_TOKEN);
        }
*/

        Log.e("dfgdfgdfg", "dfgdfgdfgdfg: " + str_UserId);
        Log.e("dfgdfgdfg", "dfgdfgdfgdfg: " + strToken);
        //  Log.e("dfgdfgdfg", "dfgdfgdfgdfg: " + url + "  JSON: " + json);


        // RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .get()
//                .addHeader(AppConstants.KEY_APP_VERSION, String.valueOf(BuildConfig.VERSION_CODE))
                .addHeader(AppConstants.KEY_APP_VERSION, AppConstants.VERSION_CODE)
                .addHeader(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID))
                .addHeader(AppConstants.KEY_LANGU, AppPreference.loadStringPref(mContext, AppPreference.KEY_LANGUAGE))
                .addHeader(AppPreference.KEY_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_TOKEN))
                .addHeader(AppConstants.KEY_TIMEZONE, "IST")
                .addHeader(AppConstants.KEY_DEVICE_TYPE, "Android")
                .addHeader(AppConstants.KEY_DEVICE_FCM_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_FCM_TOKEN))
                .addHeader(AppConstants.KEY_CONTENT_TYPE, "application/json")
                .build();


        return request;
    }


    //form data

    public Request createFormDataRequest(String url, Map<String, String> params, int fileType) {


        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if(params.containsKey(KEY_AMOUNT))
            body.addFormDataPart(KEY_AMOUNT,params.get(KEY_AMOUNT));

        if (params.containsKey(KEY_HOLDER_NAME))
            body.addFormDataPart(KEY_HOLDER_NAME, params.get(KEY_HOLDER_NAME));

        if (params.containsKey(KEY_IFSC))
            body.addFormDataPart(KEY_IFSC, params.get(KEY_IFSC));

        if (params.containsKey(KEY_BANK_NAME))
            body.addFormDataPart(KEY_BANK_NAME, params.get(KEY_BANK_NAME));

        if (params.containsKey(KEY_ACCOUNT_NUMBER))
            body.addFormDataPart(KEY_ACCOUNT_NUMBER, params.get(KEY_ACCOUNT_NUMBER));

        if (params.containsKey(KEY_ACCOUNT_ID))
            body.addFormDataPart(KEY_ACCOUNT_ID, params.get(KEY_ACCOUNT_ID));

        if (params.containsKey(AppConstants.KEY_latitude))
            body.addFormDataPart(AppConstants.KEY_latitude, params.get(AppConstants.KEY_latitude));

        if (params.containsKey(AppConstants.KEY_VALUE_longitude))
            body.addFormDataPart(AppConstants.KEY_VALUE_longitude, params.get(AppConstants.KEY_VALUE_longitude));

        if (params.containsKey(AppConstants.KEY_VALUE_home_address))
            body.addFormDataPart(AppConstants.KEY_VALUE_home_address, params.get(AppConstants.KEY_VALUE_home_address));

        if (params.containsKey(AppConstants.KEY_VALUE_city))
            body.addFormDataPart(AppConstants.KEY_VALUE_city, params.get(AppConstants.KEY_VALUE_city));

        if (params.containsKey(AppConstants.KEY_VALUE_state))
            body.addFormDataPart(AppConstants.KEY_VALUE_state, params.get(AppConstants.KEY_VALUE_state));

        if (params.containsKey(AppConstants.KEY_TYPE)) {
            body.addFormDataPart(AppConstants.KEY_TYPE, params.get(AppConstants.KEY_TYPE));
        }
        if (params.containsKey(AppConstants.KEY_TOKEN)) {
            body.addFormDataPart(AppConstants.KEY_TOKEN, params.get(AppConstants.KEY_TOKEN));
        }
        if (params.containsKey(AppConstants.KEY_OTP)) {
            body.addFormDataPart(AppConstants.KEY_OTP, params.get(AppConstants.KEY_OTP));
        }
        if (params.containsKey(AppConstants.KEY_USER_ID)) {
            body.addFormDataPart(AppConstants.KEY_USER_ID, params.get(AppConstants.KEY_USER_ID));
        }
        if (params.containsKey(AppConstants.KEY_VALUE_FIRST_NAME)) {

            body.addFormDataPart(AppConstants.KEY_VALUE_FIRST_NAME, params.get(AppConstants.KEY_VALUE_FIRST_NAME));
        }
        if (params.containsKey(AppConstants.KEY_VALUE_LAST_NAME)) {

            body.addFormDataPart(AppConstants.KEY_VALUE_LAST_NAME, params.get(AppConstants.KEY_VALUE_LAST_NAME));
        }
        if (params.containsKey(AppConstants.KEY_VALUE_MOBILE)) {

            body.addFormDataPart(AppConstants.KEY_VALUE_MOBILE, params.get(AppConstants.KEY_VALUE_MOBILE));
        }
        if (params.containsKey(AppConstants.KEY_VALUE_VEHICLE_NO)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_VEHICLE_NO, params.get(AppConstants.KEY_VALUE_VEHICLE_NO));
        }
        if (params.containsKey(AppConstants.KEY_VALUE_VEHICLE_TYPE)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_VEHICLE_TYPE, params.get(AppConstants.KEY_VALUE_VEHICLE_TYPE));
        }
        if (params.containsKey(AppConstants.KEY_VALUE_LOCATION)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_LOCATION, params.get(AppConstants.KEY_VALUE_LOCATION));
        }

        if (params.containsKey(AppConstants.KEY_VALUE_ABOUT_US)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_ABOUT_US, params.get(AppConstants.KEY_VALUE_ABOUT_US));
        }

        if (params.containsKey(AppConstants.KEY_VEHICLE_NAME)) {
            body.addFormDataPart(AppConstants.KEY_VEHICLE_NAME, params.get(AppConstants.KEY_VEHICLE_NAME));
        }

        if (params.containsKey(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE, params.get(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE));
        }
        if (params.containsKey(AppConstants.KEY_VALUE_INSURANCE)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_INSURANCE, params.get(AppConstants.KEY_VALUE_INSURANCE));
        }
        if (params.containsKey(AppConstants.KEY_VALUE_PERMIT)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_PERMIT, params.get(AppConstants.KEY_VALUE_PERMIT));
        }


        if (params.containsKey(AppConstants.KEY_VALUE_DAILY)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_DAILY, params.get(AppConstants.KEY_VALUE_DAILY));
        }

        if (params.containsKey(AppConstants.KEY_VALUE_RENTAL)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_RENTAL, params.get(AppConstants.KEY_VALUE_RENTAL));
        }

        if (params.containsKey(AppConstants.KEY_VALUE_OUTSTATION)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_OUTSTATION, params.get(AppConstants.KEY_VALUE_OUTSTATION));
        }

        if (params.containsKey(AppConstants.KEY_ACCOUNT_NUMBER)){
            body.addFormDataPart(AppConstants.KEY_ACCOUNT_NUMBER, params.get(AppConstants.KEY_ACCOUNT_NUMBER));
        }

        if (params.containsKey(AppConstants.KEY_ACCOUNTTYPE)){
            body.addFormDataPart(AppConstants.KEY_ACCOUNTTYPE, params.get(AppConstants.KEY_ACCOUNTTYPE));
        }

        if (params.containsKey(AppConstants.KEY_HOLDER_NAME)){
            body.addFormDataPart(AppConstants.KEY_HOLDER_NAME, params.get(AppConstants.KEY_HOLDER_NAME));
        }
        if (params.containsKey(KEY_BANK_NAME)){
            body.addFormDataPart(KEY_BANK_NAME, params.get(KEY_BANK_NAME));
        }
        if (params.containsKey(KEY_IFSC)){
            body.addFormDataPart(KEY_IFSC, params.get(KEY_IFSC));
        }
        if (params.containsKey(KEY_UP_NUMBER)){
            body.addFormDataPart(KEY_UP_NUMBER, params.get(KEY_UP_NUMBER));
        }
        if (params.containsKey(KEY_PAYTM_NUMBER)){
            body.addFormDataPart(KEY_PAYTM_NUMBER,params.get(KEY_PAYTM_NUMBER));
        }

        if (params.containsKey(KEY_PAYMENT_TYPE)){
            body.addFormDataPart(KEY_PAYMENT_TYPE,params.get(KEY_PAYMENT_TYPE));
        }

        if (params.containsKey(AppConstants.KEY_VALUE_FIRST_NAME)) {
            body.addFormDataPart(AppConstants.KEY_VALUE_FIRST_NAME, params.get(AppConstants.KEY_VALUE_FIRST_NAME));
        }

        if (params.containsKey(AppConstants.EMAIL)) {
            body.addFormDataPart(AppConstants.EMAIL, params.get(AppConstants.EMAIL));
        }


        if (params.containsKey(AppConstants.COMMENT)) {
            body.addFormDataPart(AppConstants.COMMENT, params.get(AppConstants.COMMENT));
        }


        if (params.containsKey(AppConstants.KEY_TRIP_ID)) {
            body.addFormDataPart(AppConstants.KEY_TRIP_ID, params.get(AppConstants.KEY_TRIP_ID));
        }


        if (params.containsKey(AppConstants.TOPIC_ID)) {
            body.addFormDataPart(AppConstants.TOPIC_ID, params.get(AppConstants.TOPIC_ID));
        }


        if (params.containsKey(AppConstants.KEY_MOBILE)) {
            body.addFormDataPart(AppConstants.KEY_MOBILE, params.get(AppConstants.KEY_MOBILE));
        }

        if (params.containsKey(AppConstants.KEY_TRIPID)) {
            body.addFormDataPart(AppConstants.KEY_TRIPID, params.get(AppConstants.KEY_TRIPID));
        }

        if (params.containsKey(AppConstants.KEY_USING_TYPE)){
            body.addFormDataPart(AppConstants.KEY_USING_TYPE, params.get(AppConstants.KEY_USING_TYPE));

        }



      /*  hashMap.put(AppConstants.KEY_TRIPID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID));
        hashMap.put(AppConstants.KEY_IMG_BILTI, biltySlipPath);*/

       /* hashMap.put(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID));
        hashMap.put(AppConstants.KEY_TRIP_ID, trip_id);
        hashMap.put(AppConstants.TOPIC_ID, selectedID);
        hashMap.put(AppConstants.EMAIL, etWriteEmail.getText().toString().trim());
        hashMap.put(AppConstants.COMMENT, getEtComment().getText().toString().trim());
        hashMap.put(AppConstants.KEY_MOBILE, etWriteMobNum.getText().toString().trim());
*/


        if (params.containsKey(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH, params.get(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH));
            } else {
                body.addFormDataPart(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH, "profile.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_VALUE_DRIVING_LICENSE_PATH))));
            }
        }

        if (params.containsKey(AppConstants.PROFILE_IMAGE)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.PROFILE_IMAGE, params.get(AppConstants.PROFILE_IMAGE));
            } else {
                body.addFormDataPart(AppConstants.PROFILE_IMAGE, "profile.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.PROFILE_IMAGE))));
            }
        }

        if (params.containsKey(AppConstants.PICTURE)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.PICTURE, params.get(AppConstants.PICTURE));
            } else {
                body.addFormDataPart(AppConstants.PICTURE, "profile.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.PICTURE))));
            }
        }


        if (params.containsKey(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH, params.get(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH));
            } else {
                body.addFormDataPart(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH, "profile.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_VALUE_DRIVING_LICENSE_RC_PATH))));
            }
        }


        if (params.containsKey(AppConstants.KEY_IMG_BILTI)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_IMG_BILTI, params.get(AppConstants.KEY_IMG_BILTI));
            } else {
                body.addFormDataPart(AppConstants.KEY_IMG_BILTI, "profile.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_IMG_BILTI))));
            }
        }

        if (params.containsKey(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE, params.get(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE));
            } else {
                body.addFormDataPart(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE, "fitness_certificate.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_VALUE_FITNESS_CERTIFICATE))));
            }
        }

        if (params.containsKey(AppConstants.KEY_VALUE_INSURANCE)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_VALUE_INSURANCE, params.get(AppConstants.KEY_VALUE_INSURANCE));
            } else {
                body.addFormDataPart(AppConstants.KEY_VALUE_INSURANCE, "insurance.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_VALUE_INSURANCE))));
            }
        }

        if (params.containsKey(AppConstants.KEY_VALUE_PERMIT)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_VALUE_PERMIT, params.get(AppConstants.KEY_VALUE_PERMIT));
            } else {
                body.addFormDataPart(AppConstants.KEY_VALUE_PERMIT, "permit.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_VALUE_PERMIT))));
            }
        }

        if (params.containsKey(AppConstants.KEY_VALUE_ROAD_TAX)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_VALUE_ROAD_TAX, params.get(AppConstants.KEY_VALUE_ROAD_TAX));
            } else {
                body.addFormDataPart(AppConstants.KEY_VALUE_ROAD_TAX, "roadtax.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_VALUE_ROAD_TAX))));
            }
        }

        if (params.containsKey(AppConstants.KEY_VALUE_ADHAR_CARD)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_VALUE_ADHAR_CARD, params.get(AppConstants.KEY_VALUE_ADHAR_CARD));
            } else {
                body.addFormDataPart(AppConstants.KEY_VALUE_ADHAR_CARD, "roadtax.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_VALUE_ADHAR_CARD))));
            }
        }

        if (params.containsKey(AppConstants.KEY_VALUE_POLLUTION)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.KEY_VALUE_POLLUTION, params.get(AppConstants.KEY_VALUE_POLLUTION));
            } else {
                body.addFormDataPart(AppConstants.KEY_VALUE_POLLUTION, "pollution.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.KEY_VALUE_POLLUTION))));
            }
        }

        if (params.containsKey(AppConstants.PROFILE_PIC)) {
            if (fileType == 0) {

                body.addFormDataPart(AppConstants.PROFILE_PIC, params.get(AppConstants.PROFILE_PIC));
            } else {
                body.addFormDataPart(AppConstants.PROFILE_PIC, "profile_pic.png", RequestBody.create(MEDIA_TYPE_PNG, new File(params.get(AppConstants.PROFILE_PIC))));
            }
        }


        Log.e("createFormDataRequest", "body: " + body.toString());
        String str_UserId = "", strToken = "";


      /*  "version:1
        userid:5
        lang:english / hindi :- only one lang send
        timeZone:IST
        token:123546
        Content-Type:application/json"*/


        RequestBody requestBody = body.build();
        Log.e("createFormDataRequest", "requestBody: " + requestBody.toString());


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
//                .addHeader(AppConstants.KEY_APP_VERSION, String.valueOf(BuildConfig.VERSION_CODE))
                .addHeader(AppConstants.KEY_APP_VERSION, AppConstants.VERSION_CODE)
                .addHeader(AppConstants.KEY_USER_ID, AppPreference.loadStringPref(mContext, AppPreference.KEY_USER_ID))
                .addHeader(AppConstants.KEY_LANGU, AppPreference.loadStringPref(mContext, AppPreference.KEY_LANGUAGE))
                .addHeader(AppPreference.KEY_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_TOKEN))
                .addHeader(AppConstants.KEY_TIMEZONE, "IST")
                .addHeader(AppConstants.KEY_DEVICE_TYPE, "Android")
                .addHeader(AppConstants.KEY_DEVICE_FCM_TOKEN, AppPreference.loadStringPref(mContext, AppPreference.KEY_FCM_TOKEN))
                .addHeader(AppConstants.KEY_CONTENT_TYPE, "application/json")
                .build();

        return request;

        // return null;
    }


    public interface OnRequestResponse {
        void onResponse(JSONObject response, String strEventType);

        void onNoNetworkConnectivity();

        void onRequestFailed();
    }


    private boolean checkNetworkConnectivity(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}