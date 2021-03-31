package com.sudrives.sudrivespartner.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.sudrives.sudrivespartner.R;
import com.sudrives.sudrivespartner.activities.ActivityLoginActivity;
import com.sudrives.sudrivespartner.adapters.DeclineRequestAdapter;
import com.sudrives.sudrivespartner.interfaces.iBeginTripListner;
import com.sudrives.sudrivespartner.interfaces.iDriveArrivedClickListner;
import com.sudrives.sudrivespartner.interfaces.iOnDonateListner;
import com.sudrives.sudrivespartner.models.AcceptTrip;
import com.sudrives.sudrivespartner.models.DeclineRequestModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AppDialogs {


    public static Dialog dialog, networkDialogLoader;

    private Context mContext;
    DonutProgress mDonutProgress;
    CountDownTimer mCustomCountdownTimer;
    private static Toast toast;
    public static String declineRequestId = "";
    LinearLayout ll_accept_popup_cross, ll_acceptPopup;

    TextView tv_accept_popup_timeCount, tv_accept_popup_pickupLocation, tv_accept_popup_dropLocation, tv_accept_popup_userName, tv_accept_popup_time, tv_accept_popup_collect;


    public AppDialogs(Context context) {

        this.mContext = context;

    }

    public AppDialogs() {
    }

    public static void noNetworkConnectionDialog(final Activity context) {

        AlertDialog.Builder alertadd = new AlertDialog.Builder(context);
        alertadd.setTitle(context.getResources().getString(R.string.error_no_connection));
        alertadd.setMessage(context.getResources().getString(R.string.error_no_con_msg));
        alertadd.create();
        alertadd.setPositiveButton(context.getResources().getString(R.string.text_settings), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int sumthin) {

                context.startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                dialogInterface.dismiss();
            }
        });

        alertadd.setNegativeButton(context.getResources().getString(R.string.text_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                context.finish();
            }
        });

        alertadd.show();
    }


    public static void hardUpdateDialog(final Activity context) {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(context);
        alertadd.setTitle(context.getResources().getString(R.string.error_app_update_required));
        alertadd.setMessage(context.getResources().getString(R.string.error_hard_update));
        alertadd.setCancelable(false);
        alertadd.create();
        alertadd.show();
    }

    public static void singleButtonDialog(final Context mcontext, final int id, final String title, final String icon, final String message, final String buttonName,
                                          final SingleButoonCallback singleButoonCallback, boolean value) {


        ((Activity) mcontext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                TextView tv_Done, tv_text, tv_title, tv_icon;

                final Dialog dialog = new Dialog(mcontext, R.style.DialogTheme);

                LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(id, null);
                tv_text = view.findViewById(R.id.SingleButton_message);
                tv_Done = view.findViewById(R.id.SingleButton_done);
                tv_title = view.findViewById(R.id.SingleButton_title);
                tv_icon = view.findViewById(R.id.SingleButton_icon);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(true);

                tv_icon.setText(icon);
                tv_text.setText(Html.fromHtml(message));
                tv_title.setText(title);
                tv_Done.setText(buttonName);


                tv_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        singleButoonCallback.singleButtonSuccess("text");
                    }
                });

                tv_Done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        singleButoonCallback.singleButtonSuccess(buttonName);
                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

            }
        });
    }


    public static void singleButtonVersionDialog(final Context mcontext, final int id, final String title, final int icon, final String message, final String buttonName,
                                                 final SingleButoonCallback singleButoonCallback, boolean value) {


        ((Activity) mcontext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                TextView tv_Done, tv_text, tv_title;
                CircleImageView tv_icon;

                final Dialog dialog = new Dialog(mcontext, R.style.DialogTheme);

                LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.success_dialog1, null);
                tv_text = view.findViewById(R.id.SingleButton_message);
                tv_Done = view.findViewById(R.id.SingleButton_done);
                tv_title = view.findViewById(R.id.SingleButton_title);
                tv_icon = view.findViewById(R.id.SingleButton_icon);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(true);

                tv_icon.setImageResource(icon);

                tv_text.setText(Html.fromHtml(message));
                tv_title.setText(title);
                tv_Done.setText(buttonName);

//                tv_icon.setVisibility(View.GONE);
                tv_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        singleButoonCallback.singleButtonSuccess("text");
                        dialog.dismiss();
                    }
                });

                tv_Done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        singleButoonCallback.singleButtonSuccess(buttonName);
                        dialog.dismiss();
                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();


            }
        });
    }


    public interface selectOkCancelListener {
        void okListener();

        void cancelListener();
    }


    public static Dialog showLoader(Context context) {
        networkDialogLoader = new Dialog(context, R.style.newDialogTheme);

        try {


            String fromTime = "";
            // Include success_dialogs_dialog.xml file
            networkDialogLoader.requestWindowFeature(Window.FEATURE_NO_TITLE);
            networkDialogLoader.setContentView(R.layout.progress_loader);
            networkDialogLoader.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            networkDialogLoader.setCancelable(false);
            networkDialogLoader.setCanceledOnTouchOutside(false);
            networkDialogLoader.show();

        } catch (Exception e) {
            Log.e("Exception", "Exception showLoader: " + e.getMessage());
        }

        return networkDialogLoader;
        // return networkDialogLoader;

    }

    public static void hideLoader() {

        if (networkDialogLoader != null && networkDialogLoader.isShowing()) {
            networkDialogLoader.dismiss();
        }


    }


    public static void versionSingleButtonDialog(Context mcontext, int id, String title, String icon, String message, final String buttonName, final SingleButoonCallback singleButoonCallback, boolean value) {

        /*TextView tv_Done, tv_text, tv_title, tv_icon;

        final Dialog dialog = new Dialog(mcontext, R.style.DialogTheme);

        LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(id, null);
        tv_text = view.findViewById(R.id.SingleButton_message);
        tv_Done = view.findViewById(R.id.SingleButton_done);
        tv_title = view.findViewById(R.id.SingleButton_title);
        tv_icon = view.findViewById(R.id.SingleButton_icon);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        tv_icon.setText(icon);
        tv_text.setText(Html.fromHtml(message));
        tv_title.setText(title);
        tv_Done.setText(buttonName);


        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleButoonCallback.singleButtonSuccess("text");
            }
        });

        tv_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleButoonCallback.singleButtonSuccess(buttonName);
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();*/
    }

    public static void endTripOtpPopup(final Context mContext, final String message, final SubmitButoonCallback listener) {


        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final EditText et_ET_enter_OTP;
                Button btn_ET_submit;

                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.end_trip_otp, null);

                et_ET_enter_OTP = view.findViewById(R.id.et_ET_enter_OTP);
                btn_ET_submit = view.findViewById(R.id.btn_ET_submit);

                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(false);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                btn_ET_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (!et_ET_enter_OTP.getText().toString().isEmpty()) {

                            if (listener != null) {
                                listener.onSubmitButtonSuccess(et_ET_enter_OTP.getText().toString());
                                dialog.dismiss();
                            }
                        } else {

                            showToast(mContext, mContext.getResources().getString(R.string.error_please_enter_otp));
                        }

                    }
                });

                dialog.show();

            }
        });


    }//End...

    public static void beginTripOtpPopup(final Context mContext, final String message, final iBeginTripListner listener) {


        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final EditText et_ET_enter_OTP;
                Button btn_ET_submit;

                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.begin_trip_otp, null);

                et_ET_enter_OTP = view.findViewById(R.id.et_ET_enter_OTP);
                btn_ET_submit = view.findViewById(R.id.btn_ET_submit);

                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(false);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                btn_ET_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (!et_ET_enter_OTP.getText().toString().isEmpty()) {

                            if (listener != null) {
                                listener.onSubmitOTP(et_ET_enter_OTP.getText().toString());
                                dialog.dismiss();
                            }
                        } else {

                            showToast(mContext, mContext.getResources().getString(R.string.error_please_enter_otp));
                        }

                    }
                });

                dialog.show();

            }
        });


    }//End...

    public static void fareSummeryPopup(final Context mContext, final String price, final String distance, final String tripId,final String paymentMode, final OnOKButtonListener listener, final iOnDonateListner iOnDonateListner) {


        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                TextView tv_fare_summary_total_distance, tv_fare_summary_total_cost;
                Button btn_fare_summary_done;

                final EditText donateAmout;
                Button donateButton;
                LinearLayout llFareBreakup;

                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.fare_summery, null);

                tv_fare_summary_total_distance = view.findViewById(R.id.tv_fare_summary_total_distance);
                tv_fare_summary_total_cost = view.findViewById(R.id.tv_fare_summary_total_cost);
                btn_fare_summary_done = view.findViewById(R.id.btn_fare_summary_done);
                llFareBreakup = view.findViewById(R.id.ll_fare_breakup);
                donateAmout = view.findViewById(R.id.et_donate_amount);
                donateButton = view.findViewById(R.id.bt_donate);

                final Dialog dialog = new Dialog(mContext, R.style.full_screen_dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(false);

                if (paymentMode.equals("0")){

                    tv_fare_summary_total_cost.setText(price);

                }else {

                    tv_fare_summary_total_cost.setText(mContext.getResources().getString(R.string.rupee)+" 0.00");

                }

                tv_fare_summary_total_distance.setText(mContext.getResources().getString(R.string.total_distance) + " : " + distance);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                btn_fare_summary_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (listener != null) {
                            String amount = donateAmout.getText().toString().trim();


                            if (TextUtils.isEmpty(amount)) {

                                iOnDonateListner.onDonateListner(tripId, "0", dialog);


                                //Toast.makeText(mContext, mContext.getString(R.string.error_donate_amount), Toast.LENGTH_SHORT).show();
                            } else {

                                double donationAmount = Double.parseDouble(amount);
                                String priceWithoutRupeeSign = price.replace(mContext.getString(R.string.rupee), "");
                                priceWithoutRupeeSign = priceWithoutRupeeSign.trim();
                                double totalPrice = Double.parseDouble(priceWithoutRupeeSign);

                                if (donationAmount > totalPrice) {
                                    Toast.makeText(mContext, mContext.getString(R.string.donation_amount_cannot_more_than_paid_amount), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                iOnDonateListner.onDonateListner(tripId, amount, dialog);

                            }
                        }

                    }
                });


                llFareBreakup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext,R.style.myFullscreenAlertDialogStyle);
                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View d = inflater.inflate(R.layout.popup_fare_details, null);
                        alertDialog.setView(d);
                        final androidx.appcompat.app.AlertDialog show = alertDialog.show();

                        ImageView imgClose = d.findViewById(R.id.close_popup_fare_detail);
                        imgClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                show.dismiss();
                            }
                        });
                    }
                });


//                donateButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        String amount = donateAmout.getText().toString().trim();
//
//
//                        if (TextUtils.isEmpty(amount)) {
//                            Toast.makeText(mContext, mContext.getString(R.string.error_donate_amount), Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            double donationAmount = Double.parseDouble(amount);
//                            String priceWithoutRupeeSign = price.replace(mContext.getString(R.string.rupee), "");
//                            priceWithoutRupeeSign = priceWithoutRupeeSign.trim();
//                            double totalPrice = Double.parseDouble(priceWithoutRupeeSign);
//
//                            if (donationAmount > totalPrice) {
//                                Toast.makeText(mContext, mContext.getString(R.string.donation_amount_cannot_more_than_paid_amount), Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
//                            iOnDonateListner.onDonateListner(tripId, amount, dialog);
//
//                        }
//                    }
//                });

                dialog.show();

            }
        });


    }//End...


    public static void declineRequestPopup(final Context mContext, final List<DeclineRequestModel.ResultBean.DeclineRequestBean> mDeclineRequestList1, final DeclineRequestSubmitButton listener) {


        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {


                declineRequestId = "";
                List<DeclineRequestModel.ResultBean.DeclineRequestBean> mDeclineRequestList = new ArrayList<>();

                mDeclineRequestList = mDeclineRequestList1;
                DeclineRequestAdapter mDeclineRequestAdapter;

                mDeclineRequestAdapter = new DeclineRequestAdapter(mDeclineRequestList, mContext);

                final EditText et_comment;
                RadioGroup rg_cancel_order;
                Button btn_submit;
                LinearLayout ll_back;

                RecyclerView rv_cancel_order;


                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.decline_request_popup, null);

                rv_cancel_order = view.findViewById(R.id.rv_cancel_order);
                btn_submit = view.findViewById(R.id.btn_submit);
                et_comment = view.findViewById(R.id.et_comment);
                ll_back = view.findViewById(R.id.ll_back);
                rg_cancel_order = view.findViewById(R.id.rg_cancel_order);

                rv_cancel_order.setAdapter(mDeclineRequestAdapter);


                for (int i = 0; i < mDeclineRequestList.size(); i++) {

                    RadioButton rdbtn = new RadioButton(mContext);
                    rdbtn.setId((i * 2) + i);
                    rdbtn.setText(mDeclineRequestList.get(i).getName());
                    rg_cancel_order.addView(rdbtn);


                }


                final Dialog dialog = new Dialog(mContext, R.style.full_screen_dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(false);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);


                final List<DeclineRequestModel.ResultBean.DeclineRequestBean> finalMDeclineRequestList = mDeclineRequestList;
                rg_cancel_order.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        View radioButton = radioGroup.findViewById(i);
                        int index = radioGroup.indexOfChild(radioButton);

                        declineRequestId = finalMDeclineRequestList.get(index).getId();
                        Log.e("TTT", "TTTTTTTT: " + index);
                    }
                });


                ll_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!declineRequestId.trim().equalsIgnoreCase("")) {
                            dialog.dismiss();

                            if (listener != null) {
                                listener.onDeclineRequestSubmitButtonClick(declineRequestId, et_comment.getText().toString());

                            }
                        } else {

                            showToast(mContext, mContext.getResources().getString(R.string.decline_request_reson));
                        }


                    }
                });

                dialog.show();

            }
        });


    }//End...


    //***************************** showPictureModePopup      ***********************************************************************************************************************
    public static void errorPopup(final Context mContext, final String message, final OnOKButtonListener listener) {


        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv_message, tv_okay, tv_Cancel;

                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.error_popup, null);

                tv_message = view.findViewById(R.id.tv_success_added);
                tv_okay = view.findViewById(R.id.tv_success_okay);

                final Dialog dialog = new Dialog(mContext, R.style.DialogTheme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(false);

                tv_message.setText(message);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                tv_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        if (listener != null)
                            listener.onClickOK();

                    }
                });

                dialog.show();

            }
        });


    }//End...


    public static void cancelBookingPopup(final Context mContext, final String message, final SingleButoonCallback listener) {


        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv_message, tv_okay, tv_Cancel;

                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.error_popup, null);

                tv_message = view.findViewById(R.id.tv_success_added);
                tv_okay = view.findViewById(R.id.tv_success_okay);

                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(false);

                tv_message.setText(message);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                tv_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        if (listener != null)
                            listener.singleButtonSuccess("Cancel");

                    }
                });

                dialog.show();

            }
        });


    }//End...


    public static void sessionPopPup(final Context mContext) {

        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new androidx.appcompat.app.AlertDialog.Builder(mContext).setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AppPreference.clear(mContext);

                        mContext.startActivity(new Intent(mContext, ActivityLoginActivity.class));
                        if (mContext instanceof Activity) {
                            ((Activity) mContext).finishAffinity();
                        }

                    }
                }).setTitle(mContext.getString(R.string.session_expire))
                        .setMessage(mContext.getString(R.string.please_log_in_again))
                        .setCancelable(false)
                        .show();

            }
        });


    }

    //***************************** showPictureModePopup      ***********************************************************************************************************************
    public static void failedPopup(final Context mContext, final String message, final OnOKButtonListener listener) {


        /*((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv_message, tv_okay, tv_Cancel, SingleButtonAsActivity_icon;

                Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/shika_new.ttf");


                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.success_popup, null);

                tv_message = view.findViewById(R.id.tv_success_added);
                tv_okay = view.findViewById(R.id.tv_success_okay);
                SingleButtonAsActivity_icon = view.findViewById(R.id.SingleButtonAsActivity_icon);


                SingleButtonAsActivity_icon.setTypeface(font);
                SingleButtonAsActivity_icon.setText(mContext.getResources().getString(R.string.icon_failed));

                SingleButtonAsActivity_icon.setTextColor(mContext.getResources().getColor(R.color.dark_red));

                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(false);

                tv_message.setText(message);


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);

                tv_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        if (listener != null)
                            listener.onClickOK();

                    }
                });

                dialog.show();

            }
        });
*/

    }//End...


    //******************* show Accept Popup ****************************************
    public void showAcceptPopup(final Context mContext, final AcceptButoonCallback listener, final CountDownTimer customCountdownTimer, final DonutProgress donutProgress, final AcceptTrip mAcceptTrip) {


        ((Activity) mContext).runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {


                mCustomCountdownTimer = customCountdownTimer;
                mDonutProgress = donutProgress;


                final Dialog dialog = new Dialog(mContext, R.style.DialogTheme);

                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.accept_popup, null);


                ll_acceptPopup = view.findViewById(R.id.ll_acceptPopup);
                ll_accept_popup_cross = view.findViewById(R.id.ll_accept_popup_cross);
                mDonutProgress = view.findViewById(R.id.donut_accept_popup_progress);
                tv_accept_popup_timeCount = view.findViewById(R.id.tv_accept_popup_timeCount);
                tv_accept_popup_pickupLocation = view.findViewById(R.id.tv_accept_popup_pickupLocation);
                tv_accept_popup_dropLocation = view.findViewById(R.id.tv_accept_popup_dropLocation);
                tv_accept_popup_userName = view.findViewById(R.id.tv_accept_popup_userName);
                tv_accept_popup_time = view.findViewById(R.id.tv_accept_popup_time);
                tv_accept_popup_collect = view.findViewById(R.id.tv_accept_popup_collect);


                tv_accept_popup_pickupLocation.setText(mAcceptTrip.getResult().getBook_from_address());
                tv_accept_popup_dropLocation.setText(mAcceptTrip.getResult().getBook_to_address());
                tv_accept_popup_userName.setText(mContext.getResources().getString(R.string.to) + " " + mAcceptTrip.getResult().getUser_details().getFirst_name()
                        + " " + mAcceptTrip.getResult().getUser_details().getLast_name());


                if (mAcceptTrip.getResult().getIs_online_payment_accept().trim().equalsIgnoreCase("0")) {
                    tv_accept_popup_collect.setText(mContext.getResources().getString(R.string.collect_payment_at_pickup_location));

                } else {
                    tv_accept_popup_collect.setText("");
                }


                mCustomCountdownTimer = new CountDownTimer(60000, 1000) {

                    @SuppressLint("NewApi")
                    public void onTick(long millisUntilFinished) {


                        mDonutProgress.setProgress((int) (millisUntilFinished / 1000));
                        //minutes_value.setText((int) (millisUntilFinished/1000)+" "+activity.getResources().getString(R.string.secound));

                        if ((millisUntilFinished / 1000) > 10) {
                            tv_accept_popup_timeCount.setText(mContext.getResources().getString(R.string.within) + " " + (int) (millisUntilFinished / 1000) + " " + mContext.getResources().getString(R.string.seconds));

                        } else {
                            tv_accept_popup_timeCount.setText(mContext.getResources().getString(R.string.within) + " " + (int) (millisUntilFinished / 1000) + " " + mContext.getResources().getString(R.string.second));

                        }


                    }

                    public void onFinish() {
                        mDonutProgress.setProgress(0);
                        dialog.dismiss();

                    }
                };

                mCustomCountdownTimer.start();


                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(view);
                dialog.setCancelable(true);

                ll_accept_popup_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mCustomCountdownTimer.cancel();
                        dialog.dismiss();


                    }
                });


                mDonutProgress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        dialog.dismiss();
                        listener.onAcceptButtonSuccess(mAcceptTrip.getResult().getId(), AppConstants.KEY_VALUE_ACCEPT);
                    }
                });


                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;

                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

            }
        });


    }

    public static void arriveDialog(Context mContext, final iDriveArrivedClickListner iDriveArrivedClickListner) {

        final Dialog dialog = new Dialog(mContext);

        dialog.setContentView(R.layout.new_arrive_popup_layout);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button cancel = dialog.findViewById(R.id.btn_ET_cancel);
        Button continues = dialog.findViewById(R.id.btn_ET_submit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDriveArrivedClickListner.onCancel();

                dialog.dismiss();
            }
        });

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDriveArrivedClickListner.onContinue();

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public static void showToast(final Context context, final String message) {
        /*if (toast != null && toast.getView().isShown()) {
            toast.cancel();
        }*/

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast = new Toast(context); // Toast.makeText(context, message, Toast.LENGTH_SHORT);

                toast.setGravity(Gravity.CENTER, 0, 0);


//Creating the LayoutInflater instance
                LayoutInflater li = ((Activity) context).getLayoutInflater();
                //Getting the View object as defined in the customtoast.xml file
                View layout = li.inflate(R.layout.custom_toast, null, false);

                toast.setView(layout);//setting the view of custom toast layout
                TextView tv = layout.findViewById(R.id.tv_custom_toast_message);
                tv.setText(message);
                toast.show();
            }
        });

       /* toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();*/
    }


    public interface OnOKButtonListener {
        void onClickOK();
    }

    public interface SingleButoonCallback {
        public void singleButtonSuccess(String from);
    }

    public interface SubmitButoonCallback {
        public void onSubmitButtonSuccess(String text);
    }

    public interface DeclineRequestSubmitButton {
        public void onDeclineRequestSubmitButtonClick(String id, String comment);
    }

    public interface UserRatingSubmitButton {
        public void onUserRatingSubmitButtonClick(String rating, String comment);
    }

    public interface AcceptButoonCallback {
        public void onAcceptButtonSuccess(String text, String from);
    }


}