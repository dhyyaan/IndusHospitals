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
import com.indushospitals.databinding.FragmentDoctorRegistrationBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.model.ValidationViewModel;
import com.indushospitals.utils.ConnectivityReceiver;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by think360 on 12/04/17.
 */

public class DoctorRegistrationFragment extends Fragment implements View.OnClickListener{
    private FragmentDoctorRegistrationBinding binding;

    public DoctorRegistrationFragment() {
        // Required empty public constructor
    }

    public static DoctorRegistrationFragment newInstance() {
        MoreActivity.self.selMore = false;
        return new DoctorRegistrationFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doctor_registration, container, false);

        binding.btnDocRegister.setOnClickListener(this);

        SpannableString s_text_login = new SpannableString(getResources().getString(R.string.text_have_account));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if(ConnectivityReceiver.isConnected()){
                    MoreActivity.self. replaceFragment(LoginFragment.newInstance());
                }else{
                    TastyToast.makeText(MoreActivity.self, "No internet connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        s_text_login.setSpan(clickableSpan, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(84,180,84));
        s_text_login.setSpan(fcs, 25, 32, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //For UnderLine
        s_text_login.setSpan(new UnderlineSpan(),25,32  ,0);
        binding.login.setText(s_text_login);
        binding.login.setMovementMethod(LinkMovementMethod.getInstance());
        binding.login.setHighlightColor(Color.TRANSPARENT);

      MoreActivity.self.moreActivityBinding.  back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.   back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MoreActivity.self. onBackPressed();

            }
        });
        binding. setViewModel(new ValidationViewModel());
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_doctor_reg));
        super.onResume();
    }

    @Override
    public void onClick(View view) {

        if (view ==  binding.btnDocRegister) {
            if(!TextUtils.isEmpty(binding.etName.getText())){

                if(!TextUtils.isEmpty(binding.etMobile.getText())){

                    if(checkMobileNo(binding.etMobile.getText().toString())){

                        if(!TextUtils.isEmpty(binding.etEmail.getText())){

                            if(isValidEmail(binding.etEmail.getText().toString())){
                             //code

                                Map<String, String> params = new HashMap<>();

                                params.put(Constents.NAME, binding.etName.getText().toString());
                                params.put(Constents.EMAIL, binding.etEmail.getText().toString());
                                params.put(Constents.MOBILE, binding.etMobile.getText().toString());

                                if(ConnectivityReceiver.isConnected()) {
                                 new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.USER_REG, params, new ServerCallBackObj() {
                               @Override
                               public void onSuccess(JSONObject jsonObj) {

                                try {
                                    if (jsonObj.getString("status").equals("true")) {

                                     TastyToast.makeText(MoreActivity.self, "" + jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    SharePreferenceData.saveString(MoreActivity.self, "doctor_id", jsonObj.getJSONObject("data").getString("doctor_id"));
                                    MoreActivity.self.replaceFragment(OtpVerificationFragment.newInstance());
                                    binding.etName.setText("");
                                    binding.etEmail.setText("");
                                    binding.etMobile.setText("");
                                    } else if (jsonObj.getString("status").equals("false")) {
                                      TastyToast.makeText(MoreActivity.self, "" + jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                      }
                                      } catch (JSONException e) {
                                         e.printStackTrace();
                                      }
                                      }

                                   }).addQueue();

                                  }else{
                                       TastyToast.makeText(getActivity(), "No internet connection!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                    }
                                }
                                 else{
                                    TastyToast.makeText(getActivity(), "Please enter valid email id", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                   }
                              }
                               else {
                            //code
                            Map<String, String> params = new HashMap<>();

                            params.put(Constents.NAME, binding.etName.getText().toString());
                            params.put(Constents.EMAIL, binding.etEmail.getText().toString());
                            params.put(Constents.MOBILE, binding.etMobile.getText().toString());

                            if (ConnectivityReceiver.isConnected()) {
                                new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.USER_REG, params, new ServerCallBackObj() {
                                @Override
                                public void onSuccess(JSONObject jsonObj) {

                                    try {
                                        if (jsonObj.getString("status").equals("true")) {

                                            TastyToast.makeText(MoreActivity.self, "" + jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.INFO);

                                            SharePreferenceData.saveString(MoreActivity.self, "doctor_id", jsonObj.getJSONObject("data").getString("doctor_id"));
                                            MoreActivity.self.replaceFragment(OtpVerificationFragment.newInstance());
                                            binding.etName.setText("");
                                            binding.etEmail.setText("");
                                            binding.etMobile.setText("");

                                            binding.etName.setError(null);
                                            binding.etEmail.setError(null);
                                            binding.etMobile.setError(null);
                                        } else if (jsonObj.getString("status").equals("false")) {
                                            TastyToast.makeText(MoreActivity.self, "" + jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).addQueue();
                        }else{
                                TastyToast.makeText(MoreActivity.self, "No internet connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        }


                    }
                    else{
                        TastyToast.makeText(getActivity(), "Please enter valid mobile number", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    }


                }
                else{
                    TastyToast.makeText(getActivity(), "Please enter mobile number", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }
            else{
                TastyToast.makeText(getActivity(), "Please enter name", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }

      }

    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean checkMobileNo(String mobile) {
        return Pattern.matches("^[7-9][0-9]{9}$", mobile);
    }

}
