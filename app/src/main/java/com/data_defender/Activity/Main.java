package com.data_defender.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.data_defender.data_defender.R;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by young on 2017-03-17.
 */

public class Main extends AppCompatActivity {

    private RelativeLayout tab_1, tab_2, tab_3;
    private boolean mIsEnabled;
    private ViewPager viewpager;
    ImageView[] iv = new ImageView[3];
    private String token_ch = null;
    private String token = null;
    private String setting =null;
    private Util_dialog dialog;
    private ImageView btn_setting;

    private InputMethodManager imm;


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onRestart() {
        Log.e("스타트","");

        super.onRestart();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e("리줌","");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_main);
        Log.e("onCreate","");
        token =   FirebaseInstanceId.getInstance().getToken();
        Log.e("토큰",""+FirebaseInstanceId.getInstance().getToken());

        /***********************************
         구글애드몹 추가
         **********************************/
        //mobileads initialize 모바일 앱ID 등록
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-1158590181545654~2133940922");
        //애드몹 뷰
        AdView mAdView = (AdView) findViewById(R.id.ad_view);
        //광고 요청 testdevice 정식등록때 바꿔야됨.
        //이게 정식출시때 해야할꺼
//        AdRequest adRequest = new AdRequest.Builder().build();
        //이게 테스트 기기 등록
        TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String mu_phone_deviceid = telephony.getDeviceId();    //device id
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(mu_phone_deviceid).build();
        mAdView.loadAd(adRequest);
        /***********************************
         구글애드몹 추가 여기까지
         **********************************/

        Log.e("chat_test_batcn_main", "" + FirebaseMessagingService.bagde_count);

         /* 프리퍼런스  */


        // 기본 padding 0

        Findid();
        try {
            token_ch = new Util_php(Main.this).execute(token, "insert_token", "http://58.121.7.102/data_defender/php/fcm/register.php").get();
            Log.e("php 리턴",""+token_ch);
            if (token_ch.contains("error")) {
                dialog = new Util_dialog(Main.this, getResources().getDrawable(R.drawable.round), "알 수 없는 오류가 발생했습니다. \n 잠시후 다시 시작해주세요.", "확인", "kill");
            } else if (token_ch.contains("alraedy")) {
                if(token_ch.contains("0")) {
                    setting="not_yet";
                }else if(token_ch.contains("1")) {
                    setting="already";
                }
            } else if (token_ch.contains("insert")) {
                setting="not_yet";
            } else {
                dialog = new Util_dialog(Main.this, getResources().getDrawable(R.drawable.round), "알 수 없는 오류가 발생했습니다. \n 잠시후 다시 시작해주세요.", "확인", "kill");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Findid() {
//        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        tab_1 = (RelativeLayout) findViewById(R.id.daily_tab);
        tab_1.setOnClickListener(btnListener);
        tab_2 = (RelativeLayout) findViewById(R.id.weekly_tab);
        tab_2.setOnClickListener(btnListener);
        tab_3 = (RelativeLayout) findViewById(R.id.montly_tab);
        tab_3.setOnClickListener(btnListener);
        viewpager = (ViewPager) findViewById(R.id.view_pager);
        btn_setting = (ImageView)findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Main.this,Setting.class);
                intent.putExtra("setting",setting);
                intent.putExtra("token",token);
                Log.e("token",""+token);
                startActivity(intent);
            }
        });
        final Main_Adapter viewpagerAdapter = new Main_Adapter(getSupportFragmentManager());

        viewpager.setAdapter(viewpagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    iv[0] = (ImageView) findViewById(R.id.daily_tab_iv);
                    iv[0].setBackgroundResource(R.drawable.round);
                    iv[1] = (ImageView) findViewById(R.id.weekly_tab_iv);
                    iv[1].setBackgroundResource(R.drawable.round_uncheck);
                    iv[2] = (ImageView) findViewById(R.id.montly_tab_iv);
                    iv[2].setBackgroundResource(R.drawable.round_uncheck);
                } else if (position == 1) {
                    iv[1] = (ImageView) findViewById(R.id.daily_tab_iv);
                    iv[1].setBackgroundResource(R.drawable.round);
                    iv[0] = (ImageView) findViewById(R.id.weekly_tab_iv);
                    iv[0].setBackgroundResource(R.drawable.round_uncheck);
                    iv[2] = (ImageView) findViewById(R.id.montly_tab_iv);
                    iv[2].setBackgroundResource(R.drawable.round_uncheck);
                } else {
                    iv[2] = (ImageView) findViewById(R.id.daily_tab_iv);
                    iv[2].setBackgroundResource(R.drawable.round);
                    iv[1] = (ImageView) findViewById(R.id.weekly_tab_iv);
                    iv[1].setBackgroundResource(R.drawable.round_uncheck);
                    iv[0] = (ImageView) findViewById(R.id.montly_tab_iv);
                    iv[0].setBackgroundResource(R.drawable.round_uncheck);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setCurrentInflateItem(int type) {
        if (type == 0) {
            viewpager.setCurrentItem(0);
        } else if (type == 1) {
            viewpager.setCurrentItem(1);
        } else {
            viewpager.setCurrentItem(2);

        }
    }

    View.OnClickListener btnListener = new View.OnClickListener() {


        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.daily_tab:
                    iv[0] = (ImageView) findViewById(R.id.daily_tab_iv);
                    iv[0].setBackgroundResource(R.drawable.round);
                    iv[1] = (ImageView) findViewById(R.id.weekly_tab_iv);
                    iv[1].setBackgroundResource(R.drawable.round_uncheck);
                    iv[2] = (ImageView) findViewById(R.id.montly_tab_iv);
                    iv[2].setBackgroundResource(R.drawable.round_uncheck);
                    setCurrentInflateItem(0);
                    break;

                case R.id.weekly_tab:
                    iv[1] = (ImageView) findViewById(R.id.daily_tab_iv);
                    iv[1].setBackgroundResource(R.drawable.round);
                    iv[0] = (ImageView) findViewById(R.id.weekly_tab_iv);
                    iv[0].setBackgroundResource(R.drawable.round_uncheck);
                    iv[2] = (ImageView) findViewById(R.id.montly_tab_iv);
                    iv[2].setBackgroundResource(R.drawable.round_uncheck);
                    setCurrentInflateItem(1);
                    break;
                case R.id.montly_tab:
                    iv[2] = (ImageView) findViewById(R.id.daily_tab_iv);
                    iv[2].setBackgroundResource(R.drawable.round);
                    iv[1] = (ImageView) findViewById(R.id.weekly_tab_iv);
                    iv[1].setBackgroundResource(R.drawable.round_uncheck);
                    iv[0] = (ImageView) findViewById(R.id.montly_tab_iv);
                    iv[0].setBackgroundResource(R.drawable.round_uncheck);
                    setCurrentInflateItem(2);
                    break;

            }
        }
    };


}

