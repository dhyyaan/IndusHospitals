package com.indushospitals.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.api.ApiGetPostNoProgressBar;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.MoreActivityBinding;
import com.indushospitals.fragments.BookAppointmentFragment;
import com.indushospitals.fragments.DashBoardFragment;
import com.indushospitals.fragments.LabReportFragment;
import com.indushospitals.fragments.MenuFragment;
import com.indushospitals.fragments.NotificationsFragment;
import com.indushospitals.fragments.PackagesFragment;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.utils.Config;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;
import com.indushospitals.utils.font.CenturyGothicBold;
import com.indushospitals.utils.font.CenturyGothicRegular;
import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.sdsmdg.tastytoast.TastyToast;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by think360 on 07/04/17.
 */

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {
    public static MoreActivity self;
    public MoreActivityBinding moreActivityBinding;
    public String datavalue;
    public Boolean selApt=false,selPack =false,selMore=false;
    private Boolean selLab=false;
   // private String kk="";
    public String value="";
    private static final String TAG = WelcomeActivity.class.getSimpleName();
   // private BroadcastReceiver mRegistrationBroadcastReceiver;
    // private TextView txtRegId, txtMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        moreActivityBinding = DataBindingUtil.setContentView(this, R.layout.more_activity);
        self = this;
        if(getIntent().getBooleanExtra("getTip", false)){
        showTip();
         }


    /*   if( moreActivityBinding.back .getVisibility() != View.VISIBLE){
           moreActivityBinding.title.setPadding(20,0,0,0);
       }*/
        moreActivityBinding.bottomButtonBookAppt.setOnClickListener(this);
        moreActivityBinding.bottomButtonPackages.setOnClickListener(this);
        moreActivityBinding.bottomButtonLabReport.setOnClickListener(this);
        moreActivityBinding.bottomButtonMore.setOnClickListener(this);

        moreActivityBinding.logo.setOnClickListener(this);

        initFragments(getIntent().getIntExtra("getfragment", 100));
        setBackgroundColor(getIntent().getIntExtra("getfragment", 100));


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(SharePreferenceData.getString(MoreActivity.self, Constents.LOGIN,"false").equals("true")) {
                    getNotificationCount();

                }

            }
        });

        displayFirebaseRegId();

    }



    public void getNotificationCount() {
        Map<String, String> params = new HashMap<>();
        params.put(Constents.DOCTOR_ID, SharePreferenceData.getString(this, Constents.DOCTOR_ID, "null"));
        new ApiGetPostNoProgressBar(this, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.COUNT_NOTIFICATION, params, new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {

                try {
                    if (jsonObj.getString("status").equals("true")) {
                        moreActivityBinding.badge.setNumber(jsonObj.getInt("count"));

                       // moreActivityBinding.badge.setNumber(999);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).addQueue();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bottomButtonBookAppt:
                initFragments(0);
                setBackgroundColor(0);
                break;
            case R.id.bottomButtonPackages:
                initFragments(1);
                setBackgroundColor(1);


                break;

            case R.id.bottomButtonLabReport:
                initFragments(2);
                setBackgroundColor(2);
                break;
            case R.id.bottomButtonMore:
                initFragments(3);
                setBackgroundColor(3);

                break;
            case R.id.logo:
               if (SharePreferenceData.getString(this, Constents.LOGIN, "false").equals("true")) {
                  datavalue =  MoreActivity.self.moreActivityBinding.title.getText().toString();
                    MoreActivity.self.replaceFragment(NotificationsFragment.newInstance());

                } else {

                    TastyToast.makeText(this, "Please 'Sign in' to see notification!", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }


                break;
        }

    }
  public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void initFragments(int i) {

        switch (i) {

            case 0:
                if(!selApt) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    MoreActivity.self.replaceFragment(BookAppointmentFragment.newInstance());
                    //MoreActivity.self.replaceFragment(BookAppointmentDemo.newInstance());
                }
                     selApt= true;
                     selPack= false;
                    selMore =false;
                    selLab =false;
                break;

            case 1:
if(!selPack) {
    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    MoreActivity.self.replaceFragment1(PackagesFragment.newInstance());
}                selPack= true;
                selMore =false;
                selApt= false;
                selLab =false;
                break;
            case 2:
                if(!selLab) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    MoreActivity.self.replaceFragment1(LabReportFragment.newInstance());
                }
                selLab = true;
                selMore =false;
                selApt= false;
                selPack = false;

                break;

            case 3:
                if(!selMore) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    MoreActivity.self.replaceFragment(MenuFragment.newInstance());
                }
                selMore =true;
                selApt= false;
                selPack = false;
                selLab =false;
                break;


            case 5:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                MoreActivity.self.replaceFragment1(DashBoardFragment.newInstance());
                selMore =false;
                selApt= false;
                selPack = false;
                selLab =false;
                break;
          case 6:
                if (SharePreferenceData.getString(this, Constents.LOGIN, "false").equals("true")) {
                    datavalue =  MoreActivity.self.moreActivityBinding.title.getText().toString();
                    MoreActivity.self.replaceFragment(NotificationsFragment.newInstance());

                } else {

                    TastyToast.makeText(this, "Please 'Sign in' to see notification!", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }
                break;
   default:
        }
    }

    private void setBackgroundColor(int index) {
        switch (index) {
            case 0:

                moreActivityBinding.bottomButtonPackages.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));

                moreActivityBinding.bottomButtonLabReport.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));

                moreActivityBinding.bottomButtonMore.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                animateOverShoot(moreActivityBinding.bottomButtonLabReport);

                moreActivityBinding.bottomButtonBookAppt.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .fruit_salad_green));

                break;
            case 1:

                moreActivityBinding.bottomButtonLabReport.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                moreActivityBinding.bottomButtonBookAppt.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                moreActivityBinding.bottomButtonMore.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                animateOverShoot(moreActivityBinding.bottomButtonPackages);
                moreActivityBinding.bottomButtonPackages.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .fruit_salad_green));


                break;
            case 2:

                moreActivityBinding.bottomButtonBookAppt.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                moreActivityBinding.bottomButtonPackages.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                moreActivityBinding.bottomButtonMore.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));

                animateOverShoot(moreActivityBinding.bottomButtonBookAppt);
                moreActivityBinding.bottomButtonLabReport.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .fruit_salad_green));

                break;
            case 3:

                moreActivityBinding.bottomButtonLabReport.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                moreActivityBinding.bottomButtonPackages.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                moreActivityBinding.bottomButtonBookAppt.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));

                animateOverShoot(moreActivityBinding.bottomButtonMore);
                moreActivityBinding.bottomButtonMore.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .fruit_salad_green));

                break;
            case 5:

                moreActivityBinding.bottomButtonLabReport.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                moreActivityBinding.bottomButtonPackages.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));
                moreActivityBinding.bottomButtonBookAppt.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .colorPrimary));

                animateOverShoot(moreActivityBinding.bottomButtonMore);
                moreActivityBinding.bottomButtonMore.setBackgroundColor(ContextCompat.getColor(this, R.color
                        .fruit_salad_green));

                break;
            default:
                break;

        }
    }

    private void animateOverShoot(LinearLayout view) {
        view.getChildAt(0).setScaleX(0);
        view.getChildAt(0).setScaleY(0);
        view.getChildAt(0).animate()
                .scaleX(1)
                .scaleY(1)
                .alpha(1)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(100)
                .start();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {
            //Toast.makeText(this,"",Toast.LENGTH_SHORT).show();

            /*if(data != null && data.hasExtra("result")){
              String responsePayUmoney = data.getStringExtra("result");
                if(SdkHelper.checkForValidString(responsePayUmoney))
                    showDialogMessage(responsePayUmoney);
            } else {
                showDialogMessage("Unable to get Status of Payment");
            }*/
            // PaymentFragment.payUMoneyStatus(requestCode,  resultCode,  data);


            //  payUMoneyStatus.payUMoneyStatus( requestCode, resultCode,data);

        }
        //GPS enable
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
               // LocationFragment.onActivityResults();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                TastyToast.makeText(this, "Please on GPS from settings", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        }
    }


    @Override
    public void onBackPressed() {

        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

       //    String f = getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount-2).getName();
            switch(getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount-2).getName()){

                case  "BookAppointmentFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_book_your_appointment));
                    setBackgroundColor(0);
                    moreActivityBinding.back.setVisibility(View.GONE);
                    break;
                case  "SearchResultFragment":
                    SpannableStringBuilder sb = new SpannableStringBuilder(getResources().getString(R.string.text_search_result)+"\n"+"Today");
                    ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0, 0, 0));
                    sb.setSpan(fcs, 14, 19, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    setBackgroundColor(0);
                    moreActivityBinding.title.setText(sb);
                    break;

                case  "BookAppointmentPatientForm":moreActivityBinding.title.setText(getResources().getString(R.string.text_patient_form));

                    setBackgroundColor(0);
                    break;
                case  "ConfirmAppointmentFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_confirm_appointment));
                    setBackgroundColor(0);
                    break;
                case  "PaymentFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_payment));
                    setBackgroundColor(0);
                    break;
                case  "DashBoardFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_referred_patient));
                    setBackgroundColor(3);
                    break;
                case  "MenuFragment":
                    if(value.equals("")){
                        moreActivityBinding.title.setText(getResources().getString(R.string.text_menu));
                        moreActivityBinding.back.setVisibility(View.GONE);
                        setBackgroundColor(3);
                    }else{
                        MoreActivity.self.   replaceFragment1(DashBoardFragment.newInstance());
                    }

                    break;
                case  "DoctorRegistrationFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_doctor_reg));
                    setBackgroundColor(3);
                    break;
                case  "LoginFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_login));
                    setBackgroundColor(3);
                    break;
                case  "OtpVerificationFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_verification));
                    setBackgroundColor(3);
                    break;

                case  "RefferedHistoryFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_referred_history));
                    setBackgroundColor(3);
                    break;
                case  "ReferPatientFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_refer_patient));
                    MoreActivity.self.   replaceFragment1(DashBoardFragment.newInstance());
                    setBackgroundColor(3);
                    break;

                case  "LabReportFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_lab_report));
                    setBackgroundColor(2);


                    break;
                case  "PackagesFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_packages));
                    setBackgroundColor(1);
                    break;
                case  "ContactUsFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_locations));
                    setBackgroundColor(3);
                    break;

                case  "AboutIndusHealthcareFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_about_healthcare));
                    setBackgroundColor(3);
                    break;
                case  "FeedbackQueryFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_feedback_query));
                    setBackgroundColor(3);
                    break;

                case  "PrivacyPolicyFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_privacy_policy));
                    setBackgroundColor(3);
                    break;

                case  "PackageDetailFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_package_detail));
                    setBackgroundColor(1);
                    break;
                case  "NotificationsFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_notification));
                    setBackgroundColor(3);
                    break;

                case  "BookingStatusFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_booking_status));
                    setBackgroundColor(0);
                    break;
                case  "PaymentStatusFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_payment_status));
                    setBackgroundColor(0);
                    break;
                case  "SocialMediaFragment":moreActivityBinding.title.setText(getResources().getString(R.string.text_social_media));
                    setBackgroundColor(3);
                     break;

            }
            getSupportFragmentManager().popBackStack();

        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                String cd = getSupportFragmentManager().getBackStackEntryAt(backStackEntryCount - 1).getName();
              if (cd .equals("BookAppointmentFragment")) {
                  MoreActivity.self.  finish();
                }
              else  if (cd .equals("MenuFragment")) {
                  MoreActivity.self.  finish();
              }
                else if  (cd .equals("PackageDetailFragment")) {
                    Intent loginRegister2 = new Intent(MoreActivity.this, MoreActivity.class);
                    loginRegister2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister2.putExtra("getfragment", 1);
                    loginRegister2.putExtra("getTip",false);
                    startActivity(loginRegister2);
                    MoreActivity.self.  finish();
                }
                else if  (cd .equals ("DashBoardFragment")) {
                  MoreActivity.self.  finish();
                }

                else if  (cd .equals ("DoctorRegistrationFragment")) {
                    Intent loginRegister3 = new Intent(MoreActivity.this, MoreActivity.class);
                    loginRegister3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister3.putExtra("getfragment", 3);
                    loginRegister3.putExtra("getTip",false);
                    startActivity(loginRegister3);
                    MoreActivity.self.  finish();
                }
                else if  (cd .equals ("AboutIndusHealthcareFragment") ){
                    Intent loginRegister4 = new Intent(MoreActivity.this, MoreActivity.class);
                    loginRegister4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister4.putExtra("getfragment", 3);
                    loginRegister4.putExtra("getTip",false);
                    startActivity(loginRegister4);
                    finish();
                }
                else if  (cd .equals ("FeedbackQueryFragment")) {
                    Intent loginRegister5 = new Intent(MoreActivity.this, MoreActivity.class);
                    loginRegister5.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister5.putExtra("getfragment", 3);
                    loginRegister5.putExtra("getTip",false);
                    startActivity(loginRegister5);
                    MoreActivity.self.  finish();
                }
                else if  (cd .equals (  "PrivacyPolicyFragment")){
                    Intent loginRegister6 = new Intent(MoreActivity.this, MoreActivity.class);
                    loginRegister6.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister6.putExtra("getfragment", 3);
                    loginRegister6.putExtra("getTip",false);
                    startActivity(loginRegister6);
                    MoreActivity.self.  finish();

                }
                else if  (cd .equals (  "ReferPatientFragment")){

                    Intent loginRegister6 = new Intent(MoreActivity.this, MoreActivity.class);
                    loginRegister6.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister6.putExtra("getfragment", 5);
                    loginRegister6.putExtra("getTip",false);
                    startActivity(loginRegister6);
                    MoreActivity.self.  finish();
              }
              else if(datavalue .equals ("Referred Patients")) {
                    Intent loginRegister7 = new Intent(MoreActivity.this, MoreActivity.class);
                    loginRegister7.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister7.putExtra("getfragment", 5);
                    loginRegister7.putExtra("getTip",false);
                    startActivity(loginRegister7);
                    MoreActivity.self.  finish();
                }

              else if(datavalue .equals ("Packages") ){
                  Intent loginRegister9 = new Intent(MoreActivity.this, MoreActivity.class);
                  loginRegister9.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                  loginRegister9.putExtra("getfragment", 1);
                  loginRegister9.putExtra("getTip",false);
                  startActivity(loginRegister9);
                  MoreActivity.self.  finish();
              }
                else if(datavalue .equals("Lab Report") ){
                    Intent loginRegister8 = new Intent(MoreActivity.this, MoreActivity.class);
                    loginRegister8.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    loginRegister8.putExtra("getfragment", 2);
                    loginRegister8.putExtra("getTip",false);
                    startActivity(loginRegister8);
                    MoreActivity.self.finish();
                }

              else if(datavalue .equals ( "Menu") ){
                  Intent loginRegister11 = new Intent(MoreActivity.this, MoreActivity.class);
                  loginRegister11.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                  loginRegister11.putExtra("getfragment", 3);
                  loginRegister11.putExtra("getTip",false);
                  startActivity(loginRegister11);
                  MoreActivity.self.finish();
              }else {}
            }
            else{
                MoreActivity.self.  finish();
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(SharePreferenceData.getString(MoreActivity.self, Constents.LOGIN,"false").equals("true")) {
                    moreActivityBinding.badge.setVisibility(View.VISIBLE);
                    getNotificationCount();

                }

            }
        });


    }

    public void replaceFragment1(Fragment fragment) {
        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        transactions.replace(R.id.fragContainer, fragment).commitAllowingStateLoss();

    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.add(R.id.fragContainer, fragment).commitAllowingStateLoss();
    }

    private void showTip(){
        Map<String, String> params = new HashMap<>();
        new ApiGetPostNoProgressBar(MoreActivity.self, Request.Method.GET, BaseUrl.BASE_URL + BaseUrl.HEALTH_TIPS, params, new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {


                try {
                    if (jsonObj.getString("status").equals("true")) {
                        JSONObject jsonObject=jsonObj.getJSONObject("data");
                        final Dialog dialog = new Dialog(MoreActivity.self){
                            @Override
                            public boolean onTouchEvent(MotionEvent event) {
                                // Tap anywhere to close dialog.
                               this.dismiss();
                                return true;
                            }
                        };
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_IMMERSIVE |
                                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_FULLSCREEN);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(MoreActivity.self,R.color.translucent_black)));

                        }else{
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.translucent_black)));
                        }


                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.tip_dailog_fragment);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();

                        wlp.gravity = Gravity.CENTER;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                        window.setAttributes(wlp);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        CenturyGothicBold title=(CenturyGothicBold)dialog.findViewById(R.id.title);
                        CenturyGothicRegular welcome_text=(CenturyGothicRegular)dialog.findViewById(R.id.welcome_text);
                        title.setText(jsonObject.optString("title"));
                        welcome_text.setText(jsonObject.optString("description"));

                        dialog.show();

              /*        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(dialog!=null)
                                    dialog.dismiss();
                            }
                        }, 2500);*/


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).addQueue();
       // kk="";
    }

    // Fetches reg id from shared preferences
    // and displays on the screen


    public String displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);
         return regId;
     /*   if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");*/
    }

}


