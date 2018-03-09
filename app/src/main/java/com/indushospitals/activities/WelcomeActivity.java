package com.indushospitals.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.api.ApiGetPostNoProgressBar;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.customview.circulartextview.TextDrawable;
import com.indushospitals.databinding.WelcomeActivityMainBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.utils.ConnectivityReceiver;
import com.indushospitals.utils.font.CenturyGothicBold;
import com.indushospitals.utils.font.CenturyGothicRegular;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class WelcomeActivity extends RuntimePermissionsActivity implements View.OnClickListener  {

    private WelcomeActivityMainBinding welcomeActivityMainBinding;
    private TextDrawable drawable1,drawable2;
    private Handler handle = new Handler();
    private String titletext,descripiontext;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    private boolean tt = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 23)
            permissions();

        welcomeActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.welcome_activity_main);

        welcomeActivityMainBinding. bottomButtonBookAppt.setOnClickListener(this);
        welcomeActivityMainBinding. bottomButtonPackages.setOnClickListener(this);
        welcomeActivityMainBinding. bottomButtonLabReport.setOnClickListener(this);
        welcomeActivityMainBinding.  bottomButtonMore.setOnClickListener(this);

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2};
        titletext=getIntent().getStringExtra("titletext");
        descripiontext=getIntent().getStringExtra("destext");
        myViewPagerAdapter = new MyViewPagerAdapter();
        welcomeActivityMainBinding.viewPager.setAdapter(myViewPagerAdapter);

        welcomeActivityMainBinding.  viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        drawable1 = TextDrawable.builder(WelcomeActivity.this)
                .buildRoundRect("", Color.WHITE,100); // radius in px
        drawable2 = TextDrawable.builder(WelcomeActivity.this)

                .buildRoundRect("", Color.parseColor("#54B554"),100); // radius in px
        welcomeActivityMainBinding.ivDot1.setImageDrawable(drawable2);
        welcomeActivityMainBinding.ivDot2.setImageDrawable(drawable1);



    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }



    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    switch (position) {
                        case 0:

                            welcomeActivityMainBinding.ivDot1.setImageDrawable(drawable2);
                            welcomeActivityMainBinding.ivDot2.setImageDrawable(drawable1);


                            break;
                        case 1:
                            welcomeActivityMainBinding.ivDot1.setImageDrawable(drawable1);
                            welcomeActivityMainBinding.ivDot2.setImageDrawable(drawable2);


                            break;
                        default:
                    }


                }
            });
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };





    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        private MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            CenturyGothicBold title=   (CenturyGothicBold)view.findViewById(R.id.title);
            CenturyGothicRegular des=   (CenturyGothicRegular)view.findViewById(R.id.welcome_text);
            title.setText(titletext);
            des.setText(descripiontext);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void permissions() {
        int REQUEST_PERMISSIONS = 20;
        WelcomeActivity.super.requestAppPermissions(new
                String[]{Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, R.string.app_name, REQUEST_PERMISSIONS);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Thread   t = new Thread() {
            @Override
            public void run() {
                while (tt) {
                    try {
                        // checking for last page
                        // if last page home screen will be launched
                        if ( welcomeActivityMainBinding.viewPager.getCurrentItem() == 1) {
                            // move to next screen
                            sleep(3000);
                            welcomeActivityMainBinding.viewPager.setCurrentItem(0);

                        } else {
                            sleep(3000);
                            welcomeActivityMainBinding.viewPager.setCurrentItem(1);
                            // launchHomeScreen();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        t.start();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        if(ConnectivityReceiver.isConnected()){
            Map<String, String> params = new HashMap<>();
             new ApiGetPostNoProgressBar(WelcomeActivity.this, Request.Method.GET, BaseUrl.BASE_URL + BaseUrl.HEALTH_TIPS, params, new ServerCallBackObj
            () {
        @Override
        public void onSuccess(JSONObject jsonObj) {


                  try {
                        if (jsonObj.getString("status").equals("true")) {
                        JSONObject jsonObject=jsonObj.getJSONObject("data");
                        titletext=jsonObject.getString("title");
                        descripiontext=jsonObject.getString("description");
                        myViewPagerAdapter = new MyViewPagerAdapter();
                        welcomeActivityMainBinding.  viewPager.setAdapter(myViewPagerAdapter);
                        myViewPagerAdapter.notifyDataSetChanged();
                        welcomeActivityMainBinding.  viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

                        drawable1 = TextDrawable.builder(WelcomeActivity.this)
                            .buildRoundRect("", Color.WHITE,100); // radius in px
                         drawable2 = TextDrawable.builder(WelcomeActivity.this)

                            .buildRoundRect("", Color.parseColor("#54B554"),100); // radius in px
                         welcomeActivityMainBinding.ivDot1.setImageDrawable(drawable2);
                         welcomeActivityMainBinding.ivDot2.setImageDrawable(drawable1);

                        }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }).addQueue();
}


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handle.removeCallbacksAndMessages(null);
        tt = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handle.removeCallbacksAndMessages(null);
        tt = false;

    }






    @Override
    public void onClick(View v) {
        Intent loginRegister = new Intent(WelcomeActivity.this, MoreActivity.class);
        loginRegister.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        switch (v.getId()) {
            case R.id.bottomButtonBookAppt:
                if(ConnectivityReceiver.isConnected()){
                    tt = false;
                    loginRegister.putExtra("getfragment",0);
                    loginRegister.putExtra("getTip",true);
                    startActivity(loginRegister);
                    finish();
                }else{
                    TastyToast.makeText(WelcomeActivity.this, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

                break;
            case R.id.bottomButtonPackages:
                if(ConnectivityReceiver.isConnected()){
                    tt = false;
                    loginRegister.putExtra("getfragment",1);
                    loginRegister.putExtra("getTip",true);
                    startActivity(loginRegister);
                    finish();
                }else{
                    TastyToast.makeText(WelcomeActivity.this, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

                break;
            case R.id.bottomButtonLabReport:
                if(ConnectivityReceiver.isConnected()){
                    tt = false;
                    loginRegister.putExtra("getfragment",2);
                    loginRegister.putExtra("getTip",true);
                    startActivity(loginRegister);
                    finish();
                }else{
                    TastyToast.makeText(WelcomeActivity.this, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

                break;
            case R.id.bottomButtonMore:
                if(ConnectivityReceiver.isConnected()){
                    tt = false;
                    loginRegister.putExtra("getfragment",3);
                    loginRegister.putExtra("getTip",true);
                    startActivity(loginRegister);
                    finish();
                }else{
                    TastyToast.makeText(WelcomeActivity.this, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

                break;

        }


    }
}
