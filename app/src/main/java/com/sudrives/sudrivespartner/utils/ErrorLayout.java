package com.sudrives.sudrivespartner.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sudrives.sudrivespartner.R;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Nikhat on 16/10/2018.
 */
public class ErrorLayout {
    public static final long LENGTH_SHORT = 2000L;
    public static final long LENGTH_LONG = 5000L;
    public RelativeLayout mErrorRl;
    public TextView mErrorTv;

    private Animation mMoveUpAnim, mMoveDownAnim;
    private final Handler handler = new Handler();
    private AtomicBoolean mErrorShown;
    Vibrator vVibrator;
    private Context mContext;

    public enum MsgType {
        Error, Success, Info, Warning
    }

    public ErrorLayout(Context context, View view) {
        mErrorTv = (TextView) view.findViewById(R.id.error_tv);
        mErrorRl = (RelativeLayout) view.findViewById(R.id.error_rl);
        vVibrator = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        mErrorTv.setVisibility(View.GONE);
        mMoveUpAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.error_move_up);
        mMoveDownAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.error_move_down);
        mErrorShown = new AtomicBoolean(false);
        mContext = context;
        //FontLoader.setOpensansRegular(mErrorTv);
    }


    /*
    * @param internetConnection = false means show error layout till internet connection not on
    * */
    @SuppressLint("MissingPermission")
    public void showAlert(String error, MsgType msgType, boolean internetConnection) {

        mErrorTv.setVisibility(View.VISIBLE);
        mErrorTv.setText(error);
        int color = 0;
        switch (msgType) {
            case Error:
//                color = mErrorRl.getResources().getColor(R.color.red_live_tutorial);
                mErrorTv.setBackgroundColor(mContext.getResources().getColor(R.color.red_error_color));
                break;
            case Success:
                // color = mErrorTv.getResources().getColor(R.color.msg_success_color);
                break;
            case Info:
                mErrorTv.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                break;
            case Warning:
                //   color = mErrorTv.getResources().getColor(R.color.msg_warning_color);
                break;

            default:
                mErrorTv.setBackgroundColor(mContext.getResources().getColor(R.color.red_error_color));
                break;
        }
        if (!internetConnection) {
            mErrorTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.app_icon, 0);
            mErrorTv.setCompoundDrawablePadding(10);
        }

        if (!mErrorShown.get()) {
            mErrorShown.set(true);
            vVibrator.vibrate(100);
            mErrorTv.startAnimation(mMoveDownAnim);
            if (internetConnection)
            {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mErrorTv.startAnimation(mMoveUpAnim);
                        mErrorShown.set(false);

                        if (null != mErrorLayoutListener) {
                            mErrorLayoutListener.onErrorHidden();
                        }
                    }
              //  }, 100 <= error.length() ? LENGTH_LONG : LENGTH_SHORT);
                }, 1 <= error.length() ? LENGTH_LONG : LENGTH_SHORT);
            }else{

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mErrorTv.startAnimation(mMoveUpAnim);
                        mErrorShown.set(false);

                        if (null != mErrorLayoutListener) {
                            mErrorLayoutListener.onErrorHidden();
                        }
                    }
                    //  }, 100 <= error.length() ? LENGTH_LONG : LENGTH_SHORT);
                }, 1 <= error.length() ? LENGTH_LONG : LENGTH_SHORT);

            }
        }
    }

    /*
    * @param error is simple message string
    * */
    public void hideErrorLayout() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mErrorTv.startAnimation(mMoveUpAnim);
                mErrorShown.set(false);

                if (null != mErrorLayoutListener) {
                    mErrorLayoutListener.onErrorHidden();
                }
            }
        }, 0);
    }

    private ErrorLayoutListener mErrorLayoutListener;

    public void setErrorLayoutListener(ErrorLayoutListener listener) {
        this.mErrorLayoutListener = listener;
    }

    public interface ErrorLayoutListener {
        void onErrorShown();

        void onErrorHidden();
    }
}
