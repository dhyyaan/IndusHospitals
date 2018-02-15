package com.indushospitals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.api.ApiGetPostNoProgressBar;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SplashScreenMainActivity extends AppCompatActivity {
    private Handler handle = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen_activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        final Thread t = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(3000);
                    if(SharePreferenceData.getString(SplashScreenMainActivity.this, Constents.LOGIN,"false").equals("true")){
                       // runOnUiThread (new Thread(new Runnable() {
                           // public void run() {
                                Map<String, String> params = new HashMap<>();
                                new ApiGetPostNoProgressBar(SplashScreenMainActivity.this, Request.Method.GET, BaseUrl.BASE_URL + BaseUrl.HEALTH_TIPS, params, new ServerCallBackObj() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObj) {


                                        try {
                                            if (jsonObj.getString("status").equals("true")) {
                                                JSONObject jsonObject=jsonObj.getJSONObject("data");
                                               String titletext=jsonObject.getString("title");
                                               String descripiontext=jsonObject.getString("description");
                                                Intent intent = new Intent(SplashScreenMainActivity.this, WelcomeActivity.class);
                                                intent.putExtra("titletext",titletext);
                                                intent.putExtra("destext",descripiontext);
                                                // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                // intent.putExtra("getfragment",5);
                                                startActivity(intent);
                                                finish();


                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).addQueue();
                      //      }
                    //    }));


                    }else{

                        Intent loginRegister = new Intent(SplashScreenMainActivity.this, WelcomeActivity.class);
                        loginRegister.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(loginRegister);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };
        t.start();
    }
    @Override
    protected void onStop() {        super.onStop();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        handle.removeCallbacksAndMessages(null);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        handle.removeCallbacksAndMessages(null);
    }




}
