package com.data_defender.Activity;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by young on 2017-03-18.
 */

public class Init_Font extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addCustom4(Typekit.createFromAsset(this, "NotoSans-Bold.otf"))
                .addCustom2(Typekit.createFromAsset(this, "NotoSans-Medium.otf"))
                .addCustom1(Typekit.createFromAsset(this, "NotoSans-Regular.otf"))
                .addCustom3(Typekit.createFromAsset(this, "NotoSans-Light.otf"));

    }
}
