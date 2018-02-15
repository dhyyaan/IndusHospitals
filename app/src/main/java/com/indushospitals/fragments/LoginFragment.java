package com.indushospitals.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentLoginBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.model.ValidationViewModel;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        MoreActivity.self.selMore = false;
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(binding.etMobile.getText())){

                    if(checkMobileNo(binding.etMobile.getText().toString())){
                        Map<String, String> params = new HashMap<>();
                        params.put(Constents.MOBILE,binding.etMobile.getText().toString());
                        params.put("device_type","android");
                        params.put("deviceId",MoreActivity.self.displayFirebaseRegId());

                        new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.USER_LOGIN,params ,new ServerCallBackObj() {
                            @Override
                            public void onSuccess(JSONObject jsonObj) {

                                try {
                                    if(jsonObj.getString("status").equals("true")){

                                        SharePreferenceData.saveString(MoreActivity.self,Constents.DOCTOR_ID,jsonObj.getJSONObject("data").getString(Constents.DOCTOR_ID));

                                        MoreActivity.self.replaceFragment(OtpVerificationFragment.newInstance());

                                    }else {

                                        TastyToast.makeText(MoreActivity.self, jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).addQueue();
                    }else{
                        TastyToast.makeText(MoreActivity.self, "Please enter valid mobile number", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }


                }else{
                    TastyToast.makeText(MoreActivity.self, "Please enter mobile number", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }

            }
        });


        SpannableString s_text_login = new SpannableString(getResources().getString(R.string.link_dont_have_account));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

                MoreActivity.self.replaceFragment(DoctorRegistrationFragment.newInstance());
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        s_text_login.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(84,180,84));
        s_text_login.setSpan(fcs, 23, 30, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //For UnderLine
        s_text_login.setSpan(new UnderlineSpan(),23,30,0);
        binding.register.setText(s_text_login);
        binding.register.setMovementMethod(LinkMovementMethod.getInstance());
        binding.register.setHighlightColor(Color.TRANSPARENT);
        binding. setViewModel(new ValidationViewModel());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_login));
        super.onResume();
    }
    public boolean checkMobileNo(String mobile) {
        return Pattern.matches("^[7-9][0-9]{9}$", mobile);
    }

}
