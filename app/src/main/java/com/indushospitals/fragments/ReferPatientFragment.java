package com.indushospitals.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentReferPatientBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.model.ValidationViewModel;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ReferPatientFragment extends Fragment {

    private List<String> mHospitalIds;
    private List<String> mDoctorIds;
    private Map<String, String> paramFormSubmit = new HashMap<>();
    private FragmentReferPatientBinding binding;
    private Boolean isSeleted = false;
    public ReferPatientFragment() {
        // Required empty public constructor
    }

    public static ReferPatientFragment newInstance() {

        MoreActivity.self.  selMore = false;
        return new ReferPatientFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MoreActivity.self.value="1";
     MoreActivity.self.moreActivityBinding.   back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             MoreActivity.self.   replaceFragment1(DashBoardFragment.newInstance());
            //    MoreActivity.self.onBackPressed();
            }
        });
      binding = DataBindingUtil.inflate(inflater, R.layout.fragment_refer_patient, container, false);

        Map<String, String> params = new HashMap<>();
        new ApiGetPostData(getActivity(), Request.Method.GET, BaseUrl.BASE_URL + BaseUrl.ALL_HOSPITAL,params ,new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {
                mHospitalIds = new ArrayList<>();
                List<String> mHospitalList = new ArrayList<>();
                try {
                    if(jsonObj.getString("status").equals("true")){

                        for(int i=0;i<jsonObj.getJSONArray("data").length();i++){
                            mHospitalIds.add(jsonObj.getJSONArray("data").getJSONObject(i).getString("id"));
                            mHospitalList.add(jsonObj.getJSONArray("data").getJSONObject(i).getString("name"));
                        }
                        ArrayAdapter<String> adapter_hospital = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, mHospitalList);
                        binding.spinnerHospital.setAdapter(adapter_hospital);
                        adapter_hospital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter_hospital.notifyDataSetChanged();

                        getDoctors();

                    }else{
                        TastyToast.makeText(getActivity(),jsonObj.getString("message"),TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).addQueue();


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.self.hideSoftKeyboard();
                if(binding.spinnerHospital.getSelectedItemPosition()>0){

                    if(binding.spinnerDoctor.getSelectedItemId()>0 ){

                        if(!TextUtils.isEmpty(binding.etPatientName.getText()) ){

                            if(!TextUtils.isEmpty(binding.etAge.getText()) && Integer.parseInt(binding.etAge.getText().toString())<100 ) {

                                if (binding.rbMale.isChecked() || binding.rbFemale.isChecked()) {

                                    if (!TextUtils.isEmpty(binding.etContactNo.getText())) {

                                        if (checkMobileNo(binding.etContactNo.getText().toString())) {

                                            if (!TextUtils.isEmpty(binding.etEmail.getText())) {

                                                if (isValidEmail(binding.etEmail.getText().toString())) {

                                                    //code
                                                    int h = binding.spinnerHospital.getSelectedItemPosition() - 1;
                                                    int d = binding.spinnerDoctor.getSelectedItemPosition() - 1;
                                                    paramFormSubmit.put(Constents.LOGIN_ID, SharePreferenceData.getString(MoreActivity.self, Constents.DOCTOR_ID, "null"));
                                                    paramFormSubmit.put(Constents.HOSPITAL, binding.spinnerHospital.getAdapter().getItem(h).toString());
                                                    paramFormSubmit.put(Constents.DOCTOR_NAME, binding.spinnerDoctor.getAdapter().getItem(d).toString());

                                                    paramFormSubmit.put(Constents.PATIENT_NAME, binding.etPatientName.getText().toString());
                                                    paramFormSubmit.put(Constents.AGE, binding.etAge.getText().toString());

                                                    if (binding.rbMale.isChecked()) {
                                                        paramFormSubmit.put(Constents.GENDER, "Male");
                                                    } else {
                                                        paramFormSubmit.put(Constents.GENDER, "Female");
                                                    }

                                                    paramFormSubmit.put(Constents.EMAIL, binding.etEmail.getText().toString());
                                                    paramFormSubmit.put(Constents.CONTACT, binding.etContactNo.getText().toString());
                                                    paramFormSubmit.put(Constents.ADDRESS, binding.etAddress.getText().toString());
                                                    paramFormSubmit.put(Constents.COMMENTS, binding.etComments.getText().toString());

                                                    new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.REFERRAL, paramFormSubmit, new ServerCallBackObj() {
                                                        @Override
                                                        public void onSuccess(JSONObject jsonObj) {

                                                            try {
                                                                if (jsonObj.getString("status").equals("true")) {

                                                                    binding.etPatientName.setText(null);
                                                                    binding.etAge.setText(null);
                                                                    binding.rbMale.setChecked(false);
                                                                    binding.rbFemale.setChecked(false);
                                                                    binding.etEmail.setText(null);
                                                                    binding.etContactNo.setText(null);
                                                                    binding.etAddress.setText(null);
                                                                    binding.etComments.setText(null);

                                                                    MoreActivity.self.replaceFragment1(RefferedHistoryFragment.newInstance());
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }).addQueue();
                                                } else {
                                                    TastyToast.makeText(getActivity(), "Email is Invalid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                                                }

                                            } else {
                                                //code
                                                int h = binding.spinnerHospital.getSelectedItemPosition() - 1;
                                                int d = binding.spinnerDoctor.getSelectedItemPosition() - 1;
                                                paramFormSubmit.put(Constents.LOGIN_ID, SharePreferenceData.getString(getActivity(), Constents.DOCTOR_ID, "null"));
                                                paramFormSubmit.put(Constents.HOSPITAL, binding.spinnerHospital.getAdapter().getItem(h).toString());
                                                paramFormSubmit.put(Constents.DOCTOR_NAME, binding.spinnerDoctor.getAdapter().getItem(d).toString());

                                                paramFormSubmit.put(Constents.PATIENT_NAME, binding.etPatientName.getText().toString());
                                                paramFormSubmit.put(Constents.AGE, binding.etAge.getText().toString());

                                                if (binding.rbMale.isChecked()) {
                                                    paramFormSubmit.put(Constents.GENDER, "Male");
                                                } else {
                                                    paramFormSubmit.put(Constents.GENDER, "Female");
                                                }

                                                paramFormSubmit.put(Constents.EMAIL, binding.etEmail.getText().toString());
                                                paramFormSubmit.put(Constents.CONTACT, binding.etContactNo.getText().toString());
                                                paramFormSubmit.put(Constents.ADDRESS, binding.etAddress.getText().toString());
                                                paramFormSubmit.put(Constents.COMMENTS, binding.etComments.getText().toString());

                                                new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.REFERRAL, paramFormSubmit, new ServerCallBackObj() {
                                                    @Override
                                                    public void onSuccess(JSONObject jsonObj) {

                                                        try {
                                                            if (jsonObj.getString("status").equals("true")) {
                                                                binding.inputLayoutPatient.setError("");
                                                                binding.etPatientName.setText(null);
                                                                binding.etAge.setText(null);
                                                                binding.rbMale.setChecked(false);
                                                                binding.rbFemale.setChecked(false);
                                                                binding.etEmail.setText(null);
                                                                binding.etContactNo.setText(null);
                                                                binding.etAddress.setText(null);
                                                                binding.etComments.setText(null);

                                                                MoreActivity.self.replaceFragment1(RefferedHistoryFragment.newInstance());
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }).addQueue();
                                            }


                                        } else {
                                            TastyToast.makeText(getActivity(), "Mobile no is Invalid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                        }


                                    } else {
                                        TastyToast.makeText(getActivity(), "Please enter mobile no", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                    }

                                } else {
                                    TastyToast.makeText(getActivity(),"Please select gender",TastyToast.LENGTH_SHORT,TastyToast.ERROR);

                                }
                            }else{
                                TastyToast.makeText(getActivity(),"Please check age",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                            }






                        }else{
                            TastyToast.makeText(getActivity(),"Please enter name",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                        }








                    }else{
                        TastyToast.makeText(getActivity(),"Please select doctor",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                    }


                }else{
                    TastyToast.makeText(getActivity(),"Please select hospital",TastyToast.LENGTH_SHORT,TastyToast.ERROR);
                }


            }
        });
       binding.etComments.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.scrollview.post(new Runnable() {
                        @Override
                        public void run() {
                            // This method works but animates the scrolling
                            // which looks weird on first load
                            // scroll_view.fullScroll(View.FOCUS_DOWN);

                            // This method works even better because there are no animations.
                            //binding.scrollview.scrollTo(0, binding.scrollview.getBottom());
                            binding.scrollview.scrollTo(0, binding.scrollview.getBottom());
                           // binding.scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
            }
        });
       binding. setViewModel(new ValidationViewModel());

        return binding.getRoot();
    }

    //get doctors from server on hospital spinner click by id
    private void getDoctors(){

    binding.spinnerHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

            Map<String, String> params = new HashMap<>();
            mDoctorIds = new ArrayList<>();
            final List<String> mDoctorList = new ArrayList<>();
            if (position > -1) {

                params.put(Constents.HOSPITAL_ID, mHospitalIds.get(position));
            }
            if(isSeleted){
            new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.ALL_DOCTOR, params, new ServerCallBackObj() {
                @Override
                public void onSuccess(JSONObject jsonObj) {

                    try {
                        if (jsonObj.getString("status").equals("true")) {

                            for (int i = 0; i < jsonObj.getJSONArray("data").length(); i++) {
                                mDoctorIds.add(jsonObj.getJSONArray("data").getJSONObject(i).getString("id"));
                                mDoctorList.add(jsonObj.getJSONArray("data").getJSONObject(i).getString("name"));
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (position > -1) {
                        selectDoctor(mHospitalIds.get(position));

                    }

                }
            }).addQueue();

            }
            isSeleted= true;
            ArrayAdapter<String> adapter_doctor = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mDoctorList);
            binding.spinnerDoctor.setAdapter(adapter_doctor);
            adapter_doctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter_doctor.notifyDataSetChanged();
        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    }

    //send doctor_id,hospital_id to server  on hospital,doctors selected from spinner
    private void selectDoctor(final String hospital_id){

        binding.spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                if(position > -1) {
                    paramFormSubmit.put(Constents.HOSPITAL_ID, hospital_id);
                    paramFormSubmit.put(Constents.DOCTOR_ID, mDoctorIds.get(position));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_refer_patient));
        super.onResume();
    }
            private boolean isValidEmail(String email) {
                return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }

    public boolean checkMobileNo(String mobile) {
        return Pattern.matches("^[7-9][0-9]{9}$", mobile);
    }

}
