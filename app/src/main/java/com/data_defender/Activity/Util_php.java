package com.data_defender.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by young on 2017-03-17.
 */

public class Util_php extends AsyncTask<String, Void, String> {

    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    Context context;
    FileInputStream fileInputStream1, fileInputStream2, fileInputStream3;
    int maxBufferSize = 1 * 1024 * 1024;

    Util_php(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private void writeFormField(String fieldName, String fieldValue) {
        try {
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.write(fieldValue.getBytes());
            dos.writeBytes(lineEnd);
        } catch (Exception e) {
            //System.out.println("AndroidUploader.writeFormField: got: " + e.getMessage());
            Log.e("ASDf", "AndroidUploader.writeFormField: " + e.getMessage());
        }
    }

    private void writeFileField(
            String fieldName,
            String fieldValue,
            FileInputStream fis) {
        try {
            // opening boundary line
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\""
                    + fieldName + "\";filename=\""
                    + fieldValue + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int bytesAvailable = fis.available();

            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];

            // read file and write it into form...
            int bytesRead = fis.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fis.read(buffer, 0, bufferSize);

            }

            dos.writeBytes(lineEnd);
        } catch (Exception e) {
            Log.e("file", "AndroidUploader.writeFormField: got: " + e.getMessage());
        }
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            URL url = new URL(params[params.length - 1]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());



            if (params[params.length - 2].equals("insert_token")) {
                writeFormField("token", params[0]);

                dos.flush();
                dos.close();

                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String json;

                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");

                }
                Log.e("insert_token", sb.toString().trim());
                return sb.toString().trim();

            }


            if (params[params.length - 2].equals("setting_rendering")) {
                writeFormField("token", params[0]);

                dos.flush();
                dos.close();

                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String json;

                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");

                }
                Log.e("setting_rendering", sb.toString().trim());
                return sb.toString().trim();

            }

            return "fail";

        } catch (Exception e) {
            Log.e("php", new String("Exception: " + e.getMessage()));
            return new String("Exception: " + e.getMessage());
        }

    }


}

