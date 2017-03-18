package com.data_defender.Activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.data_defender.Util.WaveHelper;
import com.data_defender.data_defender.R;
import com.gelitenight.waveview.library.WaveView;

/**
 * Created by young on 2017-03-17.
 */

public class Main_daily extends Fragment {

    protected View view;
    //웨이브뷰 헬퍼
    private WaveHelper mWaveHelper;
    //border는 안보이게 해놨음.
    private int mBorderColor = Color.parseColor("#CECECE");
    private int mBorderWidth = 3;
    private int daily_max_data= 1000;
    private int weekly_max_data= 1000;
    private int monthly_max_data= 1000;


    ////
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.l_main_sub, container, false);

        /*********************************************************
         웨이브뷰
         *********************************************************/
        final WaveView waveView = (WaveView) view.findViewById(R.id.wave);
        waveView.setBorder(mBorderWidth, mBorderColor);

        mWaveHelper = new WaveHelper(waveView);
//        웨이브뷰 모양지정 circle = 원 square = 네모
        waveView.setShapeType(WaveView.ShapeType.CIRCLE);
//        웨이브뷰 색상지정
        waveView.setWaveColor(
                Color.parseColor("#97E6D2"),
                Color.parseColor("#C7F3E8"));
        /*********************************************************
         웨이브뷰 여기까지
         *********************************************************/
        /********************************************************
         *
         */

//        데이터 체크 시작
        Data_check_start();


        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveHelper.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    private void Data_check_start(){
        Data_check data_check = new Data_check();
        data_check.start();
    }



    class Data_check extends Thread{
        Handler handler = new Handler();

        @Override
        public void run() {

            final TextView txt_datacheck = (TextView)view.findViewById(R.id.txt_datacheck);
            while(true){
                try {
                    final long receiveByteByMobile = TrafficStats.getMobileRxBytes();
                    final long transmitByteByMobile = TrafficStats.getMobileTxBytes();
                    final long checked_data = receiveByteByMobile+transmitByteByMobile;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txt_datacheck.setText(String.valueOf(checked_data));

                        }
                    });
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void data_limit_check(long checked_data,int gubun){
        //gubun
        // 1 : 일일 사용량
        // 2 : 주 사용량
        // 3 : 월 사용량
        if(gubun == 1){
            if(checked_data>=daily_max_data){
                Log.e("daily_data end :: ",new String("일 데이터 다 썻엉~_~"));
            }
        }else if(gubun == 2){
            if(checked_data>=weekly_max_data){
                Log.e("weekly_data end :: ",new String("주 데이터 다 썻엉~_~"));
            }
        }else if(gubun == 3){
            if(checked_data>=monthly_max_data){
                Log.e("monthly_data end :: ",new String("월 데이터 다 썻엉~_~"));
            }else if(checked_data>(monthly_max_data*0.9)){
                Log.e("monthly_data 90% :: ",new String("월 데이터 90퍼 이상 썻엉~_~"));
            }
        }
    }

}




