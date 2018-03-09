package com.indushospitals.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.activities.WelcomeActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentBookAppointmentBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.utils.ConnectivityReceiver;
import com.indushospitals.utils.Constents;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by think360 on 12/04/17.
 */

public class BookAppointmentFragment extends Fragment {
    private List<String> mCityIds, mHospitalIds, mSpecialityIds, mDoctorIds;
    public static JSONObject obj;
    private FragmentBookAppointmentBinding binding;
    private Boolean selCity =false,selHospital=false,selSpeciality=false,selDoctor=false;
    private String selectedCity ="",selectedHospital="",selectedSpeciality="",selectedDoctor="";

    public BookAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsAndEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookAppointmentFragment newInstance() {


        return new BookAppointmentFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_appointment, container, false);
        MoreActivity.self.moreActivityBinding. back.setVisibility(View.INVISIBLE);

        Map<String, String> params = new HashMap<>();
        params.put(Constents.CITY_ID,selectedCity);
        params.put(Constents.HOSPITAL_ID,selectedHospital);
        params.put(Constents.SPECIALITY_ID,selectedSpeciality);
        params.put(Constents.DOCTOR_ID,selectedDoctor);
        new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.SEARCH_DROP, params, new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {


                try {
                    if (jsonObj.getString("status").equals("true")) {
                        mCityIds = new ArrayList<>();
                        mHospitalIds = new ArrayList<>();
                        mSpecialityIds = new ArrayList<>();
                        mDoctorIds = new ArrayList<>();
                        List<String> mCityList = new ArrayList<>();
                        List<String> mHospitalList = new ArrayList<>();
                        List<String> mSpecialityList = new ArrayList<>();
                        List<String> mDoctorsList = new ArrayList<>();

                        //For City Spinner Data
                        for (int i = 0; i < jsonObj.getJSONArray("city").length(); i++) {
                            mCityIds.add(jsonObj.getJSONArray("city").getJSONObject(i).getString("id"));
                            mCityList.add(jsonObj.getJSONArray("city").getJSONObject(i).getString("name"));

                        }
                        ArrayAdapter<String> adapter_city = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mCityList);
                        binding.spinnerCity.setAdapter(adapter_city);
                        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter_city.notifyDataSetChanged();

                        // For Hospital
                        for (int i = 0; i < jsonObj.getJSONArray("hospital").length(); i++) {
                            mHospitalIds.add(jsonObj.getJSONArray("hospital").getJSONObject(i).getString("id"));
                            mHospitalList.add(jsonObj.getJSONArray("hospital").getJSONObject(i).getString("name"));
                        }

                        //For Hospital Spinner Data
                        ArrayAdapter<String> adapter_hospital = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mHospitalList);
                        binding.spinnerHospital.setAdapter(adapter_hospital);
                        adapter_hospital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter_hospital.notifyDataSetChanged();
                        // For Speciality
                        for (int i = 0; i < jsonObj.getJSONArray("speciality").length(); i++) {
                            mSpecialityIds.add(jsonObj.getJSONArray("speciality").getJSONObject(i).getString("id"));
                            mSpecialityList.add(jsonObj.getJSONArray("speciality").getJSONObject(i).getString("name"));
                        }
                        //For Speciality Spinner Data
                        ArrayAdapter<String> adapter_speciality = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mSpecialityList);
                        binding.spinnerSpeciality.setAdapter(adapter_speciality);
                        adapter_speciality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter_speciality.notifyDataSetChanged();
                        // For Doctor
                        for (int i = 0; i < jsonObj.getJSONArray("doctor").length(); i++) {
                            mDoctorIds.add(jsonObj.getJSONArray("doctor").getJSONObject(i).getString("id"));
                            mDoctorsList.add(jsonObj.getJSONArray("doctor").getJSONObject(i).getString("name"));
                        }
                        //For Doctor Spinner Data

                        ArrayAdapter<String> adapter_doctors = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mDoctorsList);
                        binding.spinnerDoctors.setAdapter(adapter_doctors);
                        adapter_doctors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapter_doctors.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).addQueue();


        //get Hospital from server on city item click


        binding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                Map<String, String> params = new HashMap<>();

                selHospital = false;
                selSpeciality = false;

                if (position > -1) {
                    selectedCity = mCityIds.get(position);
                    selectedHospital="";
                    selectedSpeciality="";
                    selectedDoctor ="";
                    params.put(Constents.CITY_ID, selectedCity);
                    params.put(Constents.HOSPITAL_ID, selectedHospital);
                    params.put(Constents.SPECILITY_ID, selectedSpeciality);
                    params.put(Constents.DOCTOR_ID, selectedDoctor);
                } else {
                    selectedCity = "";
                    selectedHospital="";
                    selectedSpeciality="";
                    selectedDoctor ="";
                    params.put(Constents.CITY_ID, selectedCity);
                    params.put(Constents.HOSPITAL_ID, selectedHospital);
                    params.put(Constents.SPECILITY_ID, selectedSpeciality);
                    params.put(Constents.DOCTOR_ID, selectedDoctor);
                }
                if (selCity) {
                    new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.SEARCH_DROP, params, new ServerCallBackObj() {
                        @Override
                        public void onSuccess(JSONObject jsonObj) {

                            try {
                                if (jsonObj.getString("status").equals("true")) {

                                    mHospitalIds = new ArrayList<>();
                                    mSpecialityIds = new ArrayList<>();
                                    mDoctorIds = new ArrayList<>();

                                    List<String> mHospitalList = new ArrayList<>();
                                    List<String> mSpecialityList = new ArrayList<>();
                                    List<String> mDoctorsList = new ArrayList<>();

                                    for (int i = 0; i < jsonObj.getJSONArray("hospital").length(); i++) {
                                        mHospitalIds.add(jsonObj.getJSONArray("hospital").getJSONObject(i).getString("id"));
                                        mHospitalList.add(jsonObj.getJSONArray("hospital").getJSONObject(i).getString("name"));
                                    }
                                    ArrayAdapter<String> adapter_hospital = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mHospitalList);
                                    binding.spinnerHospital.setAdapter(adapter_hospital);
                                    adapter_hospital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    adapter_hospital.notifyDataSetChanged();

                                    // For Speciality
                                    for (int i = 0; i < jsonObj.getJSONArray("speciality").length(); i++) {
                                        mSpecialityIds.add(jsonObj.getJSONArray("speciality").getJSONObject(i).getString("id"));
                                        mSpecialityList.add(jsonObj.getJSONArray("speciality").getJSONObject(i).getString("name"));
                                    }
                                    //For Speciality Spinner Data
                                    ArrayAdapter<String> adapter_speciality = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mSpecialityList);
                                    binding.spinnerSpeciality.setAdapter(adapter_speciality);
                                    adapter_speciality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    adapter_speciality.notifyDataSetChanged();

                                    // For Doctor
                                    for (int i = 0; i < jsonObj.getJSONArray("doctor").length(); i++) {
                                        mDoctorIds.add(jsonObj.getJSONArray("doctor").getJSONObject(i).getString("id"));
                                        mDoctorsList.add(jsonObj.getJSONArray("doctor").getJSONObject(i).getString("name"));
                                    }
                                    //For Doctor Spinner Data

                                    ArrayAdapter<String> adapter_doctors = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mDoctorsList);
                                    binding.spinnerDoctors.setAdapter(adapter_doctors);
                                    adapter_doctors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    adapter_doctors.notifyDataSetChanged();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }).addQueue();
                }
                selCity = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        binding.spinnerHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {


                selSpeciality = false;
                Map<String, String> params = new HashMap<>();
                params.put(Constents.CITY_ID, selectedCity);

                if (position > -1) {
                    selectedHospital = mHospitalIds.get(position);
                    params.put(Constents.HOSPITAL_ID, selectedHospital);
                    selectedSpeciality="";
                    selectedDoctor ="";
                    params.put(Constents.SPECILITY_ID, selectedSpeciality);
                    params.put(Constents.DOCTOR_ID, selectedDoctor);
                } else {
                    selectedHospital = "";
                    selectedSpeciality="";
                    selectedDoctor ="";

                    params.put(Constents.HOSPITAL_ID, selectedHospital);
                    params.put(Constents.SPECILITY_ID, selectedSpeciality);
                    params.put(Constents.DOCTOR_ID, selectedDoctor);
                }
                if (selHospital) {
                    new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.SEARCH_DROP, params, new ServerCallBackObj() {
                        @Override
                        public void onSuccess(JSONObject jsonObj) {

                            try {
                                if (jsonObj.getString("status").equals("true")) {
                                    mSpecialityIds = new ArrayList<>();
                                    mDoctorIds = new ArrayList<>();

                                    List<String> mSpecialityList = new ArrayList<>();
                                    List<String> mDoctorsList = new ArrayList<>();

                                    for (int i = 0; i < jsonObj.getJSONArray("speciality").length(); i++) {
                                        mSpecialityIds.add(jsonObj.getJSONArray("speciality").getJSONObject(i).getString("id"));
                                        mSpecialityList.add(jsonObj.getJSONArray("speciality").getJSONObject(i).getString("name"));
                                    }

                                    ArrayAdapter<String> adapter_spinnerSpe = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mSpecialityList);
                                    binding.spinnerSpeciality.setAdapter(adapter_spinnerSpe);
                                    adapter_spinnerSpe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    adapter_spinnerSpe.notifyDataSetChanged();
                                    // For Doctor
                                    for (int i = 0; i < jsonObj.getJSONArray("doctor").length(); i++) {
                                        mDoctorIds.add(jsonObj.getJSONArray("doctor").getJSONObject(i).getString("id"));
                                        mDoctorsList.add(jsonObj.getJSONArray("doctor").getJSONObject(i).getString("name"));
                                    }

                                    ////
                                    ArrayAdapter<String> adapter_doctors = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mDoctorsList);
                                    binding.spinnerDoctors.setAdapter(adapter_doctors);
                                    adapter_doctors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    adapter_doctors.notifyDataSetChanged();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }).addQueue();
                }
                selHospital = true;
            }




            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerSpeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                selDoctor = false;
                Map<String, String> params = new HashMap<>();
                params.put(Constents.CITY_ID, selectedCity);
                params.put(Constents.HOSPITAL_ID, selectedHospital);

                if (position > -1) {
                    selectedSpeciality = mSpecialityIds.get(position);
                    params.put(Constents.SPECIALITY_ID, selectedSpeciality);
                    selectedDoctor = "";
                    params.put(Constents.DOCTOR_ID, selectedDoctor);
                } else {
                    selectedSpeciality = "";
                    selectedDoctor = "";
                    params.put(Constents.SPECIALITY_ID, selectedSpeciality);
                    params.put(Constents.DOCTOR_ID, selectedDoctor);
                }
                if (selSpeciality) {
                    new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.SEARCH_DROP, params, new ServerCallBackObj() {
                        @Override
                        public void onSuccess(JSONObject jsonObj) {

                            try {
                                if (jsonObj.getString("status").equals("true")) {

                                    mDoctorIds = new ArrayList<>();
                                     List<String> mDoctorsList = new ArrayList<>();

                                    for (int i = 0; i < jsonObj.getJSONArray("doctor").length(); i++) {
                                        mDoctorIds.add(jsonObj.getJSONArray("doctor").getJSONObject(i).getString("id"));
                                        mDoctorsList.add(jsonObj.getJSONArray("doctor").getJSONObject(i).getString("name"));
                                    }
                                    ArrayAdapter<String> adapter_doctors = new ArrayAdapter<>(MoreActivity.self, android.R.layout.simple_spinner_item, mDoctorsList);
                                    binding.spinnerDoctors.setAdapter(adapter_doctors);
                                    adapter_doctors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    adapter_doctors.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }).addQueue();
                }
                selSpeciality = true;
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ///
        binding.spinnerDoctors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                if (selDoctor) {
                    if (position > -1) {
                        selectedDoctor = mDoctorIds.get(position);


                    } else {

                        selectedDoctor = "";

                    }

                }
                selDoctor = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//Submit all fields data to server
        binding.btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Map<String, String> paramsSubmitAppointment = new HashMap<>();
                paramsSubmitAppointment.put(Constents.CITY_ID, selectedCity);
                paramsSubmitAppointment.put(Constents.HOSPITAL_ID, selectedHospital);
                paramsSubmitAppointment.put(Constents.SPECILITY_ID, selectedSpeciality);
                paramsSubmitAppointment.put(Constents.DOCTOR_ID, selectedDoctor );


                switch(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
                    case Calendar.SUNDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY, "6");

                        break;
                    case Calendar.MONDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY, "0");
                        break;
                    case Calendar.TUESDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY, "1");
                        break;
                    case Calendar.WEDNESDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY, "2");
                        break;
                    case Calendar.THURSDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY, "3");
                        break;
                    case Calendar.FRIDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY, "4");
                        break;
                    case Calendar.SATURDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY, "5");
                        break;
                    default:

                }
              if(ConnectivityReceiver.isConnected()){

                  new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.EVENING_MORNING, paramsSubmitAppointment, new ServerCallBackObj() {
                      @Override
                      public void onSuccess(JSONObject jsonObj) {

                          try {
                              if (jsonObj.getString("status").equals("true")) {
                                  obj = jsonObj;
                                  MoreActivity.self.replaceFragment(SearchResultFragment.newInstance(paramsSubmitAppointment));
                              }
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                  }).addQueue();
                   }else{
                  TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
              }




            }
        });

        return binding.getRoot();
    }



    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_book_your_appointment));
        super.onResume();
    }


}
