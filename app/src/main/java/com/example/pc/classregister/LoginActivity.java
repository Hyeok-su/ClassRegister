package com.example.pc.classregister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        FirebaseMessaging.getInstance().subscribeToTopic("notice");
//        FirebaseInstanceId.getInstance().getToken();

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final CheckBox autoLogin = (CheckBox) findViewById(R.id.autoLogin);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor  = pref.edit();

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editor.putString("id", idText.getText().toString());
                    editor.putString("pw", passwordText.getText().toString());
                    editor.putBoolean("autoLogin", true);
                    editor.commit();
                } else {
                    // if unChecked, removeAll
                    editor.clear();
                    editor.commit();
                }
            }
        });

        if(pref.getBoolean("autoLogin", false)) {
            final String userID = pref.getString("id", "");
            final String userPassword = pref.getString("pw", "");
            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("로그인에 성공하였습니다.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userID", userID);
                            LoginActivity.this.startActivity(intent);
                            finish();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("계정을 다시 확인하세요.")
                                    .setNegativeButton("다시시도", null)
                                    .create();
                            dialog.show();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 성공하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                LoginActivity.this.startActivity(intent);
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("계정을 다시 확인하세요.")
                                        .setNegativeButton("다시시도", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        TextView information = (TextView) findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Pop.class));
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
