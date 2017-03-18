package com.data_defender.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.data_defender.data_defender.R;

/**
 * Created by young on 2017-03-17.
 */

public class Main_montly extends Fragment {

    protected View view;


    @Override
    public void onStart() {
        super.onStart();
    }

    ////
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.l_main_sub, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        return view;

    }

}
