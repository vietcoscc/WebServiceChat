package com.example.vaio.webservicechat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {
    private static final int WHAT_CHAT = 1;
    private static final int WHAT_DATA = 2;
    private ListView lvMessage;
    private EditText edtEnter;
    private ImageView ivSend;
    private ArrayList<ItemMessage> arrItemMessage = new ArrayList<>();
    private ListViewAdapter adapter;
    private String phoneNumber;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.obj.toString();
            if (msg.what == WHAT_CHAT) {
                if (result.isEmpty()) {
                    Toast.makeText(MainActivity.this, "send message fail", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "send message seccess", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == WHAT_DATA) {
                try {
                    arrItemMessage.clear();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String content = jsonObject.getString("content");
                        String date = jsonObject.getString("date");
                        ItemMessage itemMessage = new ItemMessage(name, date, content);
                        arrItemMessage.add(itemMessage);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        phoneNumber = telephonyManager.getLine1Number();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void initViews() {
        lvMessage = (ListView) findViewById(R.id.lvMessage);
        edtEnter = (EditText) findViewById(R.id.edtEnter);
        ivSend = (ImageView) findViewById(R.id.ivSend);
        adapter = new ListViewAdapter(this, arrItemMessage);
        lvMessage.setAdapter(adapter);
        ivSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String content = edtEnter.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "content must not empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String link = Config.chat(phoneNumber, content);
        MyAsyncTask myAsyncTask = new MyAsyncTask(WHAT_CHAT, handler);
        myAsyncTask.execute(link);

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String link = Config.getData();
            MyAsyncTask myAsyncTask = new MyAsyncTask(WHAT_DATA, handler);
            myAsyncTask.execute(link);
        }
    }
}
