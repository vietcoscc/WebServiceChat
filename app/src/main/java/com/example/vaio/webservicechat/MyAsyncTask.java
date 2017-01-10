package com.example.vaio.webservicechat;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by vaio on 11/30/2016.
 */

public class MyAsyncTask extends AsyncTask<String, Void, String> {
    private int what;
    private Handler handler;

    public MyAsyncTask(int what, Handler handler) {
        this.what = what;
        this.handler = handler;
    }

    @Override
    protected String doInBackground(String... strings) {
        String link = strings[0];
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            int count = inputStream.read();
            StringBuilder builder = new StringBuilder();
            while (count != -1) {
                builder.append((char) count);
                count = inputStream.read();
            }
            inputStream.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            Message message = new Message();
            message.what = what;
            message.obj = s;
            handler.sendMessage(message);
        }
    }
}
