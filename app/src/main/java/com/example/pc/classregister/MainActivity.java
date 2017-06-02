package com.example.pc.classregister;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    // 수강정정기간 때 내가 선택한 강의의 공석이 생길 경우 푸시 알림
    // 장바구니 확인 -> DB를 하나 더 만들어야 하나??
    
    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    public static String userID;
    public static String token;
    LinearLayout notice;
    RelativeLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userID = getIntent().getStringExtra("userID");
        token = getPreferences();
//        Log.d("MainActivity", "Token : " + token);

        FirebaseMessaging.getInstance().subscribeToTopic("notice");
        FirebaseInstanceId.getInstance().getToken();

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);

        final Button courseButton = (Button) findViewById(R.id.courseButton);
        final Button statisticsButton = (Button) findViewById(R.id.statisticsButton);
        final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        notice = (LinearLayout) findViewById(R.id.notice);
        fragment = (RelativeLayout) findViewById(R.id.fragment);

        courseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                fragment.setVisibility(View.VISIBLE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());
                fragmentTransaction.commit();
            }
        });

        statisticsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                fragment.setVisibility(View.VISIBLE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticsFragment());
                fragmentTransaction.commit();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                fragment.setVisibility(View.VISIBLE);
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });

        new BackgroundTask().execute();

        sendTokenToServer();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://okpo0356.dothome.co.kr/NoticeList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                // Log.i("MyMessage", result.toString());
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                // Log.i("MyMessage2", String.valueOf(jsonArray.length()));
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    count++;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        notice.setVisibility(View.VISIBLE);
        fragment.setVisibility(View.GONE);
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        Toast.makeText(getApplicationContext(), "'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();

        lastTimeBackPressed = System.currentTimeMillis();
    }

    // 값 불러오기
    private String getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("Token", "");
    }

    private void sendTokenToServer() {
        new MakeNetworkRequestAsyncTask().execute();
    }

    private void makeNetworkRequest() {
        // Add custom implementation, as needed.
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("userID", userID)
                .add("Token", token)
                .build();

        //request
        Request request = new Request.Builder()
                .url("http://okpo0356.dothome.co.kr/TokenRegister.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class MakeNetworkRequestAsyncTask extends AsyncTask<Void, Void, Void> {
        // The system calls this to perform work in a worker thread and
        // delivers it the parameters given to AsyncTask.execute()
        protected Void doInBackground(Void[] params) {
            makeNetworkRequest();
            return null;
        }

        // The system calls this to perform work in the UI thread and
        // delivers the result from doInBackground() method defined above
        @Override
        protected void onPostExecute(Void result) {}
    }
}