package com.indushospitals.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentOtpVerificationBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;
import com.sdsmdg.tastytoast.TastyToast;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by think360 on 12/04/17.
 */

public class OtpVerificationFragment extends Fragment {

    public OtpVerificationFragment() {
        // Required empty public constructor
    }

    public static OtpVerificationFragment newInstance() {
        MoreActivity.self.selMore = false;
        return new OtpVerificationFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MoreActivity.self.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        // Inflate the layout for this fragment
        final FragmentOtpVerificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_otp_verification, container, false);

        binding.btnOtpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();

                params.put(Constents.DOCTOR_ID, SharePreferenceData.getString(MoreActivity.self, Constents.DOCTOR_ID, null));
                params.put(Constents.OTP, binding.otpView.getOTP().toString());

                new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.VERIFY_OTP, params, new ServerCallBackObj() {
                    @Override
                    public void onSuccess(JSONObject jsonObj) {


                        try {
                            if (jsonObj.getString("status").equals("true")) {
                                SharePreferenceData.saveString(MoreActivity.self, Constents.LOGIN, "true");

                                 MoreActivity.self.moreActivityBinding.logo.setClickable(true);
                                 MoreActivity.self.moreActivityBinding.badge.setVisibility(View.VISIBLE);
                                 MoreActivity.self.getNotificationCount();
                                Intent loginRegister4 = new Intent(MoreActivity.self, MoreActivity.class);
                                loginRegister4.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                loginRegister4.putExtra("getfragment", 5);
                                loginRegister4.putExtra("getTip", false);
                                startActivity(loginRegister4);
                                MoreActivity.self. finish();
                            } else if (jsonObj.getString("status").equals("false")) {
                                SharePreferenceData.saveString(MoreActivity.self, Constents.LOGIN, "false");
                                TastyToast.makeText(MoreActivity.self, "" + jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).addQueue();


            }
        });
        SpannableString s_text_login = new SpannableString(getResources().getString(R.string.text_otp_again));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                Map<String, String> params = new HashMap<>();
                params.put(Constents.DOCTOR_ID, SharePreferenceData.getString(MoreActivity.self, Constents.DOCTOR_ID, null));
                new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.RESEND_OTP, params, new ServerCallBackObj() {
                    @Override
                    public void onSuccess(JSONObject jsonObj) {
                        try {
                            if (jsonObj.getString("status").equals("true")) {

                                TastyToast.makeText(MoreActivity.self, "" + jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.INFO);
                            } else {
                                TastyToast.makeText(MoreActivity.self, "" + jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).addQueue();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        s_text_login.setSpan(clickableSpan, 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(251, 101, 64));
        s_text_login.setSpan(fcs, 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //For UnderLine
        s_text_login.setSpan(new UnderlineSpan(), 0, 14, 0);
        //TextView textView = (TextView) v.findViewById(R.id.textViewGoWeb);
        binding.otpResend.setText(s_text_login);
        binding.otpResend.setMovementMethod(LinkMovementMethod.getInstance());
        binding.otpResend.setHighlightColor(Color.TRANSPARENT);

        MoreActivity.self.moreActivityBinding.back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  MoreActivity.self.onBackPressed();
            }
        });
        return binding.getRoot();

    }


    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_verification));
        super.onResume();
    }



}
