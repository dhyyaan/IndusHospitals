package com.indushospitals.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.ApiGetPostNoProgressBar;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentBookAppointmentPatientFormBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.model.ValidationViewModel;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class BookAppointmentPatientForm extends Fragment {

    private ProgressDialog pDialog ;

    private FragmentBookAppointmentPatientFormBinding binding;

    public BookAppointmentPatientForm() {
        // Required empty public constructor
    }

    public static BookAppointmentPatientForm newInstance() {
        MoreActivity.self.selApt = false;
        return new BookAppointmentPatientForm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_appointment_patient_form, container, false);
        pDialog = new ProgressDialog(MoreActivity.self);
        binding.tvDoctor.setText(SearchResultFragment.sri.getDoctor_name());
        binding.tvLocation.setText(SearchResultFragment.sri.getHospital());
        binding.etAppointmentDate.setText(SearchResultFragment.selectedDate);
        if(SearchResultFragment.sri.getImage().equals(""))
            binding.profileImage.setImageResource(R.mipmap.dr_gaurav);
        else
        Glide.with(MoreActivity.self).load(SearchResultFragment.sri.getImage()).into(binding.profileImage);

        if(SearchResultFragment.sri.getMoning().equals("")){
            binding.rbMorning.setVisibility(View.GONE);
            binding.tvMonning.setVisibility(View.GONE);
            binding.rbEvening.setChecked(true);
            binding.tvEvenning.setText(SearchResultFragment.sri.getEvening());
        }else{
            binding.tvMonning.setText(SearchResultFragment.sri.getMoning());
            binding.rbMorning.setChecked(true);
            if(SearchResultFragment.sri.getEvening().equals("")){
                binding.rbEvening.setVisibility(View.GONE);
                binding.tvEvenning.setVisibility(View.GONE);
            }else{
                binding.tvEvenning.setText(SearchResultFragment.sri.getEvening());
            }
        }

        if(!SearchResultFragment.sri.getEvening().equals("")){
            binding.tvEvenning.setText(SearchResultFragment.sri.getEvening());
        }else{
            binding.tvEvenning.setVisibility(View.GONE);
        }


        binding.etAppointmentDate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //To show current date in the datepicker
        Calendar mcurrentDate=Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth=mcurrentDate.get(Calendar.MONTH);
        int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker=new DatePickerDialog(MoreActivity.self, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {


                binding.etAppointmentDate.setText(selectedday+"-"+(selectedmonth+1)+"-"+selectedyear);
            }
        },mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
        mDatePicker.show();
    }
});


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.self.hideSoftKeyboard();

                if(!TextUtils.isEmpty(binding.etPatientName.getText())){

                    if(!TextUtils.isEmpty(binding.etAge.getText()) && Integer.parseInt(binding.etAge.getText().toString())<100 ){

                        if (binding.rbMale.isChecked() || binding.rbFemale.isChecked()){


                            if (!TextUtils.isEmpty(binding.etContactNo.getText())){

if(checkMobileNo(binding.etContactNo.getText().toString())){

    if(!TextUtils.isEmpty(binding.etEmail.getText())){
if(isValidEmail(binding.etEmail.getText().toString())){
    pDialog.setMessage("Loading...");
    pDialog.setCancelable(false);
    pDialog.setCanceledOnTouchOutside(false);
    pDialog.show();

    Map<String, String> params = new HashMap<>();
    params.put(Constents.LOGIN_ID, SharePreferenceData.getString(MoreActivity.self, Constents.DOCTOR_ID, "null"));
    params.put(Constents.DOCTOR_ID, SearchResultFragment.sri.getId());
    params.put(Constents.HOSPITAL_ID, SearchResultFragment.sri.getHospitalId());
    params.put(Constents.APPOINTMENT_DATE, binding.etAppointmentDate.getText().toString());

    if (binding.rbMorning.isChecked()) {
        params.put(Constents.TIME_SLOT, "Morning");
        params.put(Constents.CHOOSE_SLOT, binding.tvMonning.getText().toString());
    } else if (binding.rbEvening.isChecked()) {
        params.put(Constents.TIME_SLOT, "Evening");
        params.put(Constents.CHOOSE_SLOT, binding.tvEvenning.getText().toString());
    }


    params.put(Constents.PATIENT_NAME, binding.etPatientName.getText().toString());
    params.put(Constents.AGE, binding.etAge.getText().toString());

    if (binding.rbMale.isChecked()) {
        params.put(Constents.GENDER, "Male");
    } else {
        params.put(Constents.GENDER, "Female");
    }

    params.put(Constents.CONTACT, binding.etContactNo.getText().toString());
    params.put(Constents.EMAIL, binding.etEmail.getText().toString());
    params.put(Constents.ADDRESS, binding.etAddress.getText().toString());
    params.put(Constents.COMMENTS, binding.etComments.getText().toString());


    new ApiGetPostNoProgressBar(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.PATIENT_DETAIL, params, new ServerCallBackObj() {
        @Override
        public void onSuccess(JSONObject jsonObj) {
            try {
                if (jsonObj.getString("status").equals("true")) {

                    Map<String, String> params = new HashMap<>();
                    params.put(Constents.ID, jsonObj.getJSONObject("data").getString("id"));
                    new ApiGetPostNoProgressBar(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.REQUEST_APPOINTMENT, params, new ServerCallBackObj() {
                        @Override
                        public void onSuccess(JSONObject jsonObj) {

                            try {
                                if (jsonObj.getString("status").equals("true")) {
                                    binding.etPatientName.setText("");
                                    binding.etAge.setText("");
                                    binding.rbMale.setChecked(false);
                                    binding.rbFemale.setChecked(false);
                                    binding.etEmail.setText("");
                                    binding.etContactNo.setText("");
                                    binding.etAddress.setText("");
                                    binding.etComments.setText("");

                                    pDialog.hide();
                                    MoreActivity.self.replaceFragment(BookingStatusFragment.newInstance());

                                }else{
                                    pDialog.hide();
                                    TastyToast.makeText(MoreActivity.self, jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                pDialog.hide();
                            }
                        }
                    }).addQueue();

                  // MoreActivity.self.replaceFragment(ConfirmAppointmentFragment.newInstance(jsonObj.getJSONObject("data") + ""));this is only disable for some time as client needed all code i working previously if we comment this line

                } else {
                    pDialog.hide();
                    TastyToast.makeText(getActivity(), jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                pDialog.hide();
            }

        }
    }).addQueue();

}else {
    TastyToast.makeText(getActivity(), "Email is Invalid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
}

    }else{
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put(Constents.LOGIN_ID, SharePreferenceData.getString(MoreActivity.self, Constents.DOCTOR_ID, "null"));
        params.put(Constents.DOCTOR_ID, SearchResultFragment.sri.getId());
        params.put(Constents.HOSPITAL_ID, SearchResultFragment.sri.getHospitalId());
        params.put(Constents.APPOINTMENT_DATE, binding.etAppointmentDate.getText().toString());

        if (binding.rbMorning.isChecked()) {
            params.put(Constents.TIME_SLOT, "Morning");
            params.put(Constents.CHOOSE_SLOT, binding.tvMonning.getText().toString());
        } else if (binding.rbEvening.isChecked()) {
            params.put(Constents.TIME_SLOT, "Evening");
            params.put(Constents.CHOOSE_SLOT, binding.tvEvenning.getText().toString());
        }


        params.put(Constents.PATIENT_NAME, binding.etPatientName.getText().toString());
        params.put(Constents.AGE, binding.etAge.getText().toString());

        if (binding.rbMale.isChecked()) {
            params.put(Constents.GENDER, "Male");
        } else {
            params.put(Constents.GENDER, "Female");
        }

        params.put(Constents.CONTACT, binding.etContactNo.getText().toString());
        params.put(Constents.EMAIL, binding.etEmail.getText().toString());
        params.put(Constents.ADDRESS, binding.etAddress.getText().toString());
        params.put(Constents.COMMENTS, binding.etComments.getText().toString());


        new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.PATIENT_DETAIL, params, new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {
                try {
                    if (jsonObj.getString("status").equals("true")) {

                        Map<String, String> params = new HashMap<>();
                        params.put(Constents.ID, jsonObj.getJSONObject("data").getString("id"));
                        new ApiGetPostNoProgressBar(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.REQUEST_APPOINTMENT, params, new ServerCallBackObj() {
                            @Override
                            public void onSuccess(JSONObject jsonObj) {

                                try {
                                    if (jsonObj.getString("status").equals("true")) {
                                        binding.etPatientName.setText("");
                                        binding.etAge.setText("");
                                        binding.rbMale.setChecked(false);
                                        binding.rbFemale.setChecked(false);
                                        binding.etEmail.setText("");
                                        binding.etContactNo.setText("");
                                        binding.etAddress.setText("");
                                        binding.etComments.setText("");
                                        pDialog.hide();

                                        MoreActivity.self.replaceFragment(BookingStatusFragment.newInstance());
                                    }  else{
                                            pDialog.hide();
                                           TastyToast.makeText(MoreActivity.self, jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    pDialog.hide();
                                }
                            }
                        }).addQueue();


                    } else {
                        pDialog.hide();
                        TastyToast.makeText(getActivity(), jsonObj.getString("message"), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pDialog.hide();
                }

            }
        }).addQueue();

    }

}else{
    TastyToast.makeText(getActivity(), "Mobile No is not valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
 
}



                            }else{
                                TastyToast.makeText(getActivity(), "Mobile No is empty", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                            }


                        }else{
                            TastyToast.makeText(getActivity(), "Please select gender", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        }




                    }else{
                        TastyToast.makeText(getActivity(), "Please check age", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                    }


                }else{
                    TastyToast.makeText(getActivity(), "Please enter name", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                }


            }
        });

        MoreActivity.self.moreActivityBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MoreActivity.self.onBackPressed();

            }
        });
        binding. etComments.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.scroolview.post(new Runnable() {
                        @Override
                        public void run() {

                            binding.scroolview.scrollTo(0, binding.scroolview.getBottom());

                        }
                    });
                }
            }
        });

        binding.setViewModel(new ValidationViewModel());

        return binding.getRoot();
    }


    @Override
    public void onResume() {

        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_patient_form));
        super.onResume();
    }
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean checkMobileNo(String mobile) {

    return Pattern.matches("^[7-9][0-9]{9}$", mobile);


    }
}
