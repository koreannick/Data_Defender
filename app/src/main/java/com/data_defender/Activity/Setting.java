package com.data_defender.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by young on 2017-03-17.
 */

public class Setting extends AppCompatActivity {
    /**
     * Created by young on 2017-03-17.
     */

    public static class Main_Adapter extends FragmentStatePagerAdapter {

        private boolean enabled;

        Fragment[] fragments = new Fragment[3];


        public Main_Adapter(FragmentManager fm) {
            super(fm);

            fragments[0] = new Main_daily();
            fragments[1] = new Main_weekly();
            fragments[2] = new Main_montly();
        }



        public Fragment getItem(int arg0) {
            return fragments[arg0];

        }

        public int getCount() {
            return fragments.length;
        }

    }
}
