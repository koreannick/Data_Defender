package com.data_defender.Activity;

import android.content.SharedPreferences;
import android.util.Log;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import android.util.Log;
import android.util.SparseLongArray;

import com.data_defender.Activity.Main;
import com.data_defender.Activity.Splash;
import com.data_defender.data_defender.R;
import com.google.firebase.messaging.RemoteMessage;

import me.leolin.shortcutbadger.ShortcutBadger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by young on 2017-03-17.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";
    public static int bagde_count = 0;
    private String type, noti, time = null;


    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        noti = remoteMessage.getData().get("message");
        type = remoteMessage.getData().get("type");
        time = remoteMessage.getData().get("time");


    }

    private void sendNotification(String messageBody, String type, String img) {


        Intent intent = new Intent(this, Splash.class);
        intent.putExtra("from", "push");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.push_icon)
                .setContentTitle("Data_Defender")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        bagde_count++;
        ShortcutBadger.applyCount(this, bagde_count);

    }


    public String getData(String url) {

        class GetDataJSON extends AsyncTask<String, Void, String> {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            @Override
            protected String doInBackground(String... params) {
                DataOutputStream os;
                String uri = params[0];
                BufferedReader bufferedReader = null;
                StringBuilder sb = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    os = new DataOutputStream(con.getOutputStream());


                    os.flush();
                    os.close();

                    BufferedReader rd = null;
                    rd = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    sb = new StringBuilder();
                    String json;
                    while ((json = rd.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    Log.e("fire", sb.toString().trim());
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String result) {
            }
        }
        String result2 = null;
        GetDataJSON g = new GetDataJSON();
        try {
            result2 = g.execute(url).get();
        } catch (Exception e) {
        }
        return result2;
    }
}
