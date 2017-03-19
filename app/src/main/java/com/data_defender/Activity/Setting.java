package com.data_defender.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.data_defender.data_defender.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.locks.ReadWriteLock;

import static com.data_defender.data_defender.R.id.aa;

/**
 * Created by young on 2017-03-17.
 */

public class Setting extends AppCompatActivity {
    private RelativeLayout main;
    private RelativeLayout limit_montly, limit_montly_90, limit_weekly, limit_daily;
    private Boolean b_limit_montly = true, b_limit_montly_90 = true, b_limit_weekly = true, b_limit_daily = true;
    private ImageView iv_limit_montly, iv_limit_montly_90, iv_limit_weekly, iv_limit_daily;
    private TextView tv_limit_montly, tv_limit_montly_90, tv_limit_weekly, tv_limit_daily;
    private EditText et_mbgb;
    private String mbgb = "Mb";
    private TextView tv_mb, tv_gb;
    private SeekBar seek;
    private TextView tv_seek;
    private TextView confirm;
    private Util_dialog dialog;
    private RelativeLayout back_btn;
    private Double pro = 0.0;
    private int last_day = 0;
    private String intent_token = null;
    private String intent_setting = null;
    private String json_rendering, json_set = null;
    private JSONArray jsonArray, jsonArray_set;

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        intent_token = intent.getStringExtra("token");
        intent_setting = intent.getStringExtra("setting");
        if (intent_setting.equals("not_yet")) {

        } else {
            try {
                Log.e("intetn_token",""+intent_token);
                json_rendering = new Util_php(Setting.this).execute(intent_token, "setting_rendering", "http://58.121.7.102/data_defender/php/fcm/setting_rendering.php").get();
                if (json_rendering.contains("error")) {
                } else {
                    parse(json_rendering);

                }
            } catch (Exception e) {

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_setting);

        Calendar cal = Calendar.getInstance();
        last_day = cal.getMaximum(Calendar.DAY_OF_MONTH);
//        find_id
        main = (RelativeLayout) findViewById(R.id.l_main);
        main.setOnClickListener(clicklis);
        back_btn = (RelativeLayout) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(clicklis);

        limit_montly = (RelativeLayout) findViewById(R.id.limit_montly);
        limit_montly_90 = (RelativeLayout) findViewById(R.id.limit_montly_90);
        limit_weekly = (RelativeLayout) findViewById(R.id.limit_weekly);
        limit_daily = (RelativeLayout) findViewById(R.id.limit_daily);
        limit_montly.setOnClickListener(clicklis);
        limit_montly_90.setOnClickListener(clicklis);
        limit_weekly.setOnClickListener(clicklis);
        limit_daily.setOnClickListener(clicklis);

        iv_limit_montly = (ImageView) findViewById(R.id.ab);
        iv_limit_montly_90 = (ImageView) findViewById(R.id.bb);
        iv_limit_weekly = (ImageView) findViewById(R.id.cb);
        iv_limit_daily = (ImageView) findViewById(R.id.db);

        tv_limit_montly = (TextView) findViewById(aa);
        tv_limit_montly_90 = (TextView) findViewById(R.id.ba);
        tv_limit_weekly = (TextView) findViewById(R.id.ca);
        tv_limit_daily = (TextView) findViewById(R.id.da);


        et_mbgb = (EditText) findViewById(R.id.et_mbgb);
        et_mbgb.setOnClickListener(clicklis);
        tv_mb = (TextView) findViewById(R.id.tv_mb);
        tv_mb.setOnClickListener(clicklis);
        tv_gb = (TextView) findViewById(R.id.tv_gb);
        tv_gb.setOnClickListener(clicklis);

        seek = (SeekBar) findViewById(R.id.seek);
        tv_seek = (TextView) findViewById(R.id.tv_seek);
        tv_seek.setText("0Mb");

        confirm = (TextView) findViewById(R.id.confirm);
        et_mbgb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    pro = (Double.parseDouble(et_mbgb.getText().toString()) / last_day);
                    String aa = Double.toString(pro);
                    if (mbgb.equals("Mb")) {

                    } else {

                    }
                    seek.setProgress(Integer.valueOf(aa));
                    tv_seek.setText(pro + mbgb);
                    Log.e("pririr", "" + pro);
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw));
                    String exceptionAsStrting = sw.toString();
                    Log.e("GODK", exceptionAsStrting);
                    e.printStackTrace();
                }
            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("pro", "" + progress);
                if (et_mbgb.getText().toString() == "0" || et_mbgb.getText().toString() == null || et_mbgb.getText().toString() == "" || Integer.parseInt(et_mbgb.getText().toString()) == 0) {

                } else {
                    pro = Double.parseDouble(et_mbgb.getText().toString()) / (progress / 100);
                    tv_seek.setText(pro + "Mb");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }

    View.OnClickListener clicklis = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.l_main:
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_mbgb.getWindowToken(), 0);
                    break;
                case R.id.tv_gb:
                    tv_gb.setBackgroundColor(getResources().getColor(R.color.app_default_1));
                    tv_gb.setTextColor(getResources().getColor(R.color.white));
                    tv_mb.setTextColor(getResources().getColor(R.color.app_default_1));
                    tv_mb.setBackgroundResource(R.drawable.box_setting);
                    mbgb = "Gb";
                    tv_seek.setText(pro + "Mb");
                    break;
                case R.id.tv_mb:
                    tv_mb.setBackgroundColor(getResources().getColor(R.color.app_default_1));
                    tv_gb.setBackgroundResource(R.drawable.box_setting);
                    tv_mb.setTextColor(getResources().getColor(R.color.white));
                    tv_gb.setTextColor(getResources().getColor(R.color.app_default_1));
                    mbgb = "Mb";
                    tv_seek.setText(pro + "Mb");
                    break;
                case R.id.limit_montly:
                    if (!b_limit_montly) {
                        iv_limit_montly.setBackgroundResource(R.drawable.checked);
                        b_limit_montly = true;
                    } else {
                        iv_limit_montly.setBackgroundResource(R.drawable.box_gray_2);
                        b_limit_montly = false;
                    }
                    break;
                case R.id.limit_montly_90:
                    if (!b_limit_montly_90) {
                        iv_limit_montly_90.setBackgroundResource(R.drawable.checked);
                        b_limit_montly_90 = true;
                    } else {
                        iv_limit_montly_90.setBackgroundResource(R.drawable.box_gray_2);
                        b_limit_montly_90 = false;
                    }
                    break;
                case R.id.limit_weekly:
                    if (!b_limit_weekly) {
                        iv_limit_weekly.setBackgroundResource(R.drawable.checked);
                        b_limit_weekly = true;
                    } else {
                        iv_limit_weekly.setBackgroundResource(R.drawable.box_gray_2);
                        b_limit_weekly = false;
                    }
                    break;
                case R.id.limit_daily:
                    if (!b_limit_daily) {
                        iv_limit_daily.setBackgroundResource(R.drawable.checked);
                        b_limit_daily = true;
                    } else {
                        iv_limit_daily.setBackgroundResource(R.drawable.box_gray_2);
                        b_limit_daily = false;
                    }
                    break;
                case R.id.confirm:
                    if (Integer.parseInt(et_mbgb.getText().toString()) == 0 || et_mbgb.getText().toString() == null || et_mbgb.getText().toString() == "") {
                        dialog = new Util_dialog(com.data_defender.Activity.Setting.this, getResources().getDrawable(R.drawable.logo), "한 달 총 데이터를 입력해주세요", "확인", "dismiss");
                        dialog.show();
                    } else {
                        try {
                            json_set= new Util_php(Setting.this).execute(intent_token, "setting", "http://58.121.7.102/data_defender/php/fcm/setting.php").get();
                            if (json_set.contains("error")) {
                            } else {
                                dialog = new Util_dialog(com.data_defender.Activity.Setting.this, getResources().getDrawable(R.drawable.logo), "설정이 완료 되었습니다.", "확인", "back");
                                dialog.show();
                                //
                            }
                        } catch (Exception e) {

                        }
                    }
                    break;
                case R.id.back_btn:
                    finish();
                    break;
            }
        }
    };


    private void parse(String data) {
        try {
            JSONObject jsonObj = new JSONObject(data);
            jsonArray = jsonObj.getJSONArray("result");

            JSONObject c = jsonArray.getJSONObject(0);
            et_mbgb.setText(Integer.parseInt(c.getString("total_data")));
            if (!(c.getString("push").contains("1"))) {
                iv_limit_montly.setBackgroundResource(R.drawable.box_gray_2);
                b_limit_montly = false;
            } else if (!(c.getString("push").contains("2"))) {
                iv_limit_montly_90.setBackgroundResource(R.drawable.box_gray_2);
                b_limit_montly_90 = false;
            } else if (!(c.getString("push").contains("3"))) {
                iv_limit_weekly.setBackgroundResource(R.drawable.box_gray_2);
                b_limit_weekly = false;
            } else if (!(c.getString("push").contains("4"))) {
                iv_limit_daily.setBackgroundResource(R.drawable.box_gray_2);
                b_limit_daily = false;
            } else {

            }
            seek.setProgress(Integer.parseInt( c.getString("daily_data"))/Integer.parseInt( c.getString("total_data"))*100);
            tv_seek.setText(Integer.parseInt( c.getString("daily_data"))+"Mb");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
//
//= (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(et_mbgb.getWindowToken(), 0);