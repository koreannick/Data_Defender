package com.data_defender.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.data_defender.data_defender.R;

public class stats extends AppCompatActivity {
    IconRoundCornerProgressBar progressOne,progressTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        progressOne = (IconRoundCornerProgressBar) findViewById(R.id.progress_one);
        progressOne.setProgressColor(getResources().getColor(R.color.app_default_3));
        progressOne.setIconBackgroundColor(getResources().getColor(R.color.app_default_3));
        progressOne.setProgressBackgroundColor(getResources().getColor(R.color.text_default_2));
        progressOne.setIconImageResource(R.drawable.percent_11);

        progressTwo = (IconRoundCornerProgressBar) findViewById(R.id.progress_two);
        progressTwo.setProgressColor(getResources().getColor(R.color.app_default_3));
        progressTwo.setIconBackgroundColor(getResources().getColor(R.color.app_default_3));
        progressTwo.setProgressBackgroundColor(getResources().getColor(R.color.text_default_2));
        progressTwo.setIconImageResource(R.drawable.percent_08);

    }
}
