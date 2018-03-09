package com.indushospitals.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.activities.WelcomeActivity;
import com.indushospitals.adapters.SearchResultBindingAdapter;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.customview.weekview.WeekDatePicker;
import com.indushospitals.databinding.FragmentSearchResultBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.interfaces.listener.ActionCallback3;
import com.indushospitals.model.SearchResultItem;
import com.indushospitals.utils.ConnectivityReceiver;
import com.indushospitals.utils.Constents;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchResultFragment extends Fragment {

    private static Map<String , String>paramsSubmitAppointment;
    private List<SearchResultItem> mSearchResultItemList;
    public static String selectedDate;
    public static SearchResultItem sri;
    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment newInstance(Map<String , String>params) {

        paramsSubmitAppointment = new HashMap<>();
        paramsSubmitAppointment = params;
        MoreActivity.self.selApt = true;
        return new SearchResultFragment();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final FragmentSearchResultBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false);

        final Calendar c = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        selectedDate = df.format(c.getTime());

        final ActionCallback3 actionCallback3 = new ActionCallback3() {
            @Override
            public void onClick(SearchResultItem item) {
                if((selectedDate.equals("null"))) {
                    TastyToast.makeText(MoreActivity.self, "For Sunday booking is not available", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }else {

                    //if(ConnectivityReceiver.isConnected()){
                        sri = item;
                        MoreActivity.self.replaceFragment( BookAppointmentPatientForm.newInstance());
                     //  }else{
                    //  TastyToast.makeText(MoreActivity.self, "No internet connection!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    //}
                }
            }
        };
        try {
              mSearchResultItemList = new ArrayList<>();
            for(int i = 0; i< BookAppointmentFragment.obj.getJSONArray("data").length(); i++){

                for(int j = 0; j< BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").length(); j++) {
                 JSONArray availableDaysJSon = BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getJSONArray("available");
                    List<String> availableDaysList = new ArrayList<>(7);
                    for(int p=0 ;p< availableDaysJSon.length();p++)
                        availableDaysList.add(availableDaysJSon.getString(p));

                    mSearchResultItemList.add(new SearchResultItem(
                            BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getString("id"),
                            BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getString("name"),
                            BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getString("city"),
                            BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getString("specility"),
                            BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getString("hospital_id"),
                            BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getString("hospital"),
                            BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getString("evening_time"),
                            BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getString("mrning_time"),
                            availableDaysList, BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getString("pic")));
                }
                            }


            SearchResultBindingAdapter adapter = new SearchResultBindingAdapter(actionCallback3, mSearchResultItemList);
            binding.rvRearchResult.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            binding.tvResultNo.setText(mSearchResultItemList.size()+" "+getResources().getString(R.string.text_result_no));

        } catch (JSONException e) {
            e.printStackTrace();
        }




        binding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(ConnectivityReceiver.isConnected()){
                    MoreActivity.self.onBackPressed();
                  }else{
                      TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                       }
            }
        });

        MoreActivity.self.moreActivityBinding.back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ConnectivityReceiver.isConnected()){
                    MoreActivity.self.onBackPressed();
                }else{
                    TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

            }
        });

        binding.datePicker.setDateIndicator(LocalDate.now().plusDays(0), true);
        binding.datePicker.setLimits(LocalDate.now().minusWeeks(0),LocalDate.now().plusWeeks(2) );

        binding.datePicker.setOnDateSelectedListener(new WeekDatePicker.OnDateSelected() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSelected(LocalDate date) {

                if(ConnectivityReceiver.isConnected()){
                    switch(date.getDayOfWeek()){
                        case SUNDAY:paramsSubmitAppointment.put(Constents.WEEK_DAY,"6");
                            break;
                        case MONDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY,"0");
                            break;
                        case TUESDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY,"1");
                            break;
                        case WEDNESDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY,"2");
                            break;
                        case THURSDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY,"3");
                            break;
                        case FRIDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY,"4");
                            break;
                        case SATURDAY: paramsSubmitAppointment.put(Constents.WEEK_DAY,"5");
                            break;
                        default:

                    }
               new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.EVENING_MORNING, paramsSubmitAppointment, new ServerCallBackObj() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onSuccess(JSONObject jsonObj) {

                 try {
                    mSearchResultItemList = new ArrayList<>();
                      for(int i=0;i<jsonObj.getJSONArray("data").length();i++){

                        for(int j=0;j<jsonObj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").length();j++) {
                        JSONArray availableDaysJSon =jsonObj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getJSONArray("available");
                        List<String> availableDaysList = new ArrayList<>(7);
                        for(int p=0 ;p< availableDaysJSon.length();p++)
                            availableDaysList.add(availableDaysJSon.getString(p));

                        mSearchResultItemList.add(new SearchResultItem(
                                jsonObj.getJSONArray("data").getJSONObject(i).getString("id"),
                                jsonObj.getJSONArray("data").getJSONObject(i).getString("name"),
                                jsonObj.getJSONArray("data").getJSONObject(i).getString("city"),
                                jsonObj.getJSONArray("data").getJSONObject(i).getString("specility"),
                                jsonObj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getString("hospital_id"),
                                jsonObj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getString("hospital"),
                                jsonObj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getString("evening_time"),
                                jsonObj.getJSONArray("data").getJSONObject(i).getJSONArray("hospital").getJSONObject(j).getString("mrning_time"),
                                availableDaysList, BookAppointmentFragment.obj.getJSONArray("data").getJSONObject(i).getString("pic")));
                    }
                }
                SearchResultBindingAdapter adapter = new SearchResultBindingAdapter(actionCallback3, mSearchResultItemList);
                binding.rvRearchResult.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.tvResultNo.setText(mSearchResultItemList.size()+" "+getResources().getString(R.string.text_result_no));

                } catch (JSONException e) {
                 e.printStackTrace();
                  }
                  }
                }).addQueue();
              }else{
                 TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                 }
                String dayOfWeek =  ""+date.getDayOfWeek();

                SpannableStringBuilder sb1 = new SpannableStringBuilder(dayOfWeek.substring(0,3)+"|"+  date.format(DateTimeFormatter.ofPattern("dd-LL-yyyy")).substring(0,2)+ " "+date.getMonth());
                ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0, 0, 0));
                sb1.setSpan(fcs, 0, sb1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_search_result)+"\n"+sb1);
                selectedDate = date.format(DateTimeFormatter.ofPattern("dd-LL-yyyy"));
            }
        });
         MoreActivity.self.moreActivityBinding.titleDate.setVisibility(View.GONE);
        return binding.getRoot();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
        SpannableStringBuilder sb = new SpannableStringBuilder(getResources().getString(R.string.text_search_result));
        SpannableStringBuilder sb1 = new SpannableStringBuilder("Today");
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0, 0, 0));
        sb.setSpan(fcs, 0, sb1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        MoreActivity.self.moreActivityBinding.title.setText(sb+"\n"+sb1);

    }
}
