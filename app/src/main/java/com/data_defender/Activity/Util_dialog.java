package com.data_defender.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.data_defender.data_defender.R;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by young on 2017-03-17.
 */

public class Util_dialog extends Dialog {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    private ImageView mIcon;
    private TextView mMsg;
    private TextView mBtn1, mBtn2;
    private ImageView mBtnMargin;

    private Drawable mIcon_bg = null;
    private String mType;
    private String msg1, btn1, btn2;


    Context mContext;

    @Override
    public void onBackPressed() {
    }



    //버튼두개일때
    public Util_dialog(Context context, Drawable icon, String msg, String btn_1, String btn_2, String type) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.mType = type;
        this.msg1 = msg;
        this.btn1 = btn_1;
        this.btn2 = btn_2;
        this.mIcon_bg = icon;
    }


    //버튼하나일때
    public Util_dialog(Context context, Drawable icon, String msg, String btn_1, String type) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.mType = type;
        this.msg1 = msg;
        this.btn1 = btn_1;
        this.btn2 = null;
        this.mIcon_bg = icon;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.l_custom_dialog);

        mIcon = (ImageView) findViewById(R.id.icon);
        mMsg = (TextView) findViewById(R.id.msg);
        mBtn1 = (TextView) findViewById(R.id.btn_1);
        mBtnMargin = (ImageView) findViewById(R.id.two_btn_margin);
        mBtn2 = (TextView) findViewById(R.id.btn_2);


//        버튼 하나일때
        if (btn2 == null) {
            mMsg.setText(msg1);
            mBtn1.setText(btn1);
            mBtn1.setOnClickListener(btnlistener);
            mIcon.setBackground(mIcon_bg);
        }
        //버튼두개일때
        else {
            mMsg.setText(msg1);
            mBtn1.setText(btn1);
            mBtnMargin.setVisibility(View.VISIBLE);
            mBtn2.setVisibility(View.VISIBLE);
            mBtn2.setText(btn2);
            mBtn1.setOnClickListener(btnlistener2);
            mBtn2.setOnClickListener(btnlistener3);
            mIcon.setBackground(mIcon_bg);
        }


    }

    View.OnClickListener btnlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (mType) {
                case"kill":
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                case"dismiss":
                    Util_dialog.this.dismiss();
                    break;
                case"back":
                    Activity activity = (Activity) mContext;
                    activity.finish();
                    break;
            }
        }
    };

    // 왼쪽 버튼
    View.OnClickListener btnlistener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (mType) {

            }
        }
    };
    // 오른쪽 버튼
    View.OnClickListener btnlistener3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (mType) {


            }
        }
    };


}

