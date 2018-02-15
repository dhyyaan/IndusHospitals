package com.indushospitals.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentFeedbackQueryBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.model.ValidationViewModel;
import com.indushospitals.utils.Constents;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by think360 on 13/04/17.
 */

public class FeedbackQueryFragment extends Fragment implements View.OnClickListener {

    private FragmentFeedbackQueryBinding binding;
    public FeedbackQueryFragment() {
        // Required empty public constructor
    }


    public static FeedbackQueryFragment newInstance() {

          MoreActivity.self.selMore = false;
        return new FeedbackQueryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback_query, container, false);
        binding.inputLayoutTypeHere.setHint("");
        binding.etTypeHere.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(binding.etTypeHere.hasFocus()){
                    binding.inputLayoutTypeHere.setHint(getResources().getString(R.string.text_description));

                }else{
                    if(binding.etTypeHere.getText().length()<1)
                        binding.inputLayoutTypeHere.setHint("");
                }
            }
        });

        FrameLayout back = (FrameLayout)getActivity().findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        binding. setViewModel(new ValidationViewModel());
        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {

      switch (view.getId()) {

          case R.id.back:
              getActivity().onBackPressed();
              break;
          case R.id.btnSubmit:
              MoreActivity.self.hideSoftKeyboard();
              if(!TextUtils.isEmpty(binding.etName.getText())){
                  if(!TextUtils.isEmpty(binding.etEmail.getText())){

                      if(!TextUtils.isEmpty(binding.etEmail.getText())){

                          if(isValidEmail(binding.etEmail.getText().toString())){

                              if(!TextUtils.isEmpty(binding.etContactNo.getText())){

                                  if(checkMobileNo(binding.etContactNo.getText().toString())){
                                      Map<String, String> params = new HashMap<>();

                                      if (binding.rBFeedback.isChecked()) {
                                          params.put(Constents.QUERY_TYPE, "0");
                                      } else if (binding.rBEnquery.isChecked()) {
                                          params.put(Constents.QUERY_TYPE, "1");
                                      } else if (binding.rBComplaint.isChecked()) {
                                          params.put(Constents.QUERY_TYPE, "2");
                                      }

                                      params.put(Constents.NAME, binding.etName.getText().toString());
                                      params.put(Constents.EMAIL, binding.etEmail.getText().toString());
                                      params.put(Constents.PHONE, binding.etContactNo.getText().toString());
                                      params.put(Constents.COMMENTS, binding.etTypeHere.getText().toString());
                                      new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.FEEDBACK, params, new ServerCallBackObj() {
                                          @Override
                                          public void onSuccess(JSONObject jsonObj) {

                                              try {
                                                  if (jsonObj.getString("status").equals("true")) {
                                                      binding.etName.setText("");
                                                      binding.etEmail.setText("");
                                                      binding.etContactNo.setText("");
                                                      binding.etTypeHere.setText("");

                                                      TastyToast.makeText(getActivity(), jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.INFO);
                                                      getActivity().onBackPressed();

                                                  } else {
                                                      TastyToast.makeText(getActivity(), jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                  }
                                              } catch (JSONException e) {
                                                  e.printStackTrace();
                                              }
                                          }
                                      }).addQueue();

                                  }else{
                                      TastyToast.makeText(getActivity(), "Mobile no is not valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                  }

                              }else{
                                  TastyToast.makeText(getActivity(), "Please enter mobile no", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                              }
                          }else{
                              TastyToast.makeText(getActivity(), "Please enter valid email id", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                          }



                      }else{
                          TastyToast.makeText(getActivity(), "Please enter email id", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                      }



                  }else{
                      TastyToast.makeText(getActivity(), "Please enter valid email id", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                  }
              }else{
                  TastyToast.makeText(getActivity(), "Please enter name", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
              }
          /*    if(binding.etName.getText().toString().equals("")){
                  binding.etName.setError("Please enter name");
              }else if(binding.etEmail.getText().toString().equals("")){
                  binding.etEmail.setError("Please enter valid email id");

              }
              else if(binding.etEmail.getText().toString().equals("") || !isValidEmail(binding.etEmail.getText().toString())){
                  binding.etEmail.setError("Please enter valid email id");

              }else if(binding.etContactNo.getText().toString().equals("") &&  !checkMobileNo(binding.etContactNo.getText().toString())){
                  binding.etContactNo.setError("Mobile No is not valid");

              }else if(binding.etTypeHere.getText().toString().equals("")){
                  binding.etTypeHere.setError("Please enter comments");

              }else {
                  Map<String, String> params = new HashMap<>();

                  if (binding.rBFeedback.isChecked()) {
                      params.put(Constents.QUERY_TYPE, "0");
                  } else if (binding.rBEnquery.isChecked()) {
                      params.put(Constents.QUERY_TYPE, "1");
                  } else if (binding.rBComplaint.isChecked()) {
                      params.put(Constents.QUERY_TYPE, "2");
                  }

                  params.put(Constents.NAME, binding.etName.getText().toString());
                  params.put(Constents.EMAIL, binding.etEmail.getText().toString());
                  params.put(Constents.PHONE, binding.etContactNo.getText().toString());
                  params.put(Constents.COMMENTS, binding.etTypeHere.getText().toString());
                  new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.FEEDBACK, params, new ServerCallBackObj() {
                      @Override
                      public void onSuccess(JSONObject jsonObj) {

                          try {
                              if (jsonObj.getString("status").equals("true")) {
                                  binding.etName.setText("");
                                  binding.etEmail.setText("");
                                  binding.etContactNo.setText("");
                                  binding.etTypeHere.setText("");

                                  TastyToast.makeText(getActivity(), jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.INFO);
                                  getActivity().onBackPressed();

                              } else {
                                  TastyToast.makeText(getActivity(), jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                  }).addQueue();
              }*/

              break;

        }

    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_feedback_query));
        super.onResume();
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean checkMobileNo(String mobile) {
        return Pattern.matches("^[7-9][0-9]{9}$", mobile);
    }
}
