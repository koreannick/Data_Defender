package com.data_defender.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.data_defender.data_defender.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

/**
 * Created by young on 2017-03-17.
 */

public class Splash extends AppCompatActivity {
    private ImageView main;

    private Intent getintent;

    private String store_version;
    private String device_version;
    private Util_dialog dialog;
    private String token_ch = null;

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FirebaseApp.initializeApp(Splash.this);
//        String token =  FirebaseInstanceId.getInstance().getToken();
//        Log.e("토큰",""+token);


        /***************************************
         퍼미션 체크 구간
         ***************************************/
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(android.Manifest.permission.INTERNET,
                        android.Manifest.permission.ACCESS_NETWORK_STATE,
                        android.Manifest.permission.WAKE_LOCK)
                .check();
        /***************************************
         퍼미션 체크 구간여기까지
         ***************************************/

        getintent = getIntent();

        try {
            getVersion asyncTask = new getVersion();
            store_version = asyncTask.execute().get();
            store_version = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            device_version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            device_version = "1";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (store_version.compareTo(device_version) > 0) {
//                dialog = new Util_dialog(Splash.this, getResources().getDrawable(R.drawable.icon_warning), "최신버전으로 업데이트 해주세요", "확인", "update");
//                dialog.show();
        } else {
            setContentView(R.layout.l_splash);
            main = (ImageView) findViewById(R.id.loding_logo);
            Handler hd = new Handler();
            hd.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this,Main.class);
                    startActivity(intent);
                    finish();
                    }

            }, 3000); // 3초 후에 hd Handler 실행

        }

    }

    public class getVersion extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                store_version = MarketVersionChecker.getMarketVersion(getPackageName());

            } catch (Exception e) {

            }
            return store_version;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }


}

