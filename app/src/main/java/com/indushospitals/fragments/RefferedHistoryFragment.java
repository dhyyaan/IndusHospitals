package com.indushospitals.fragments;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.adapters.DashBoardBindingAdapter;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.ApiGetPostNoProgressBar;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentRefferedHistoryBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.interfaces.listener.ActionCallback2;
import com.indushospitals.model.DashboardItem;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.EndlessRecyclerOnScrollListener;
import com.indushospitals.utils.SharePreferenceData;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefferedHistoryFragment extends   Fragment{

    private Map<String, String> params = new HashMap<>();
    private List<DashboardItem> mPackagesList ;
    private FragmentRefferedHistoryBinding binding;
    private int nextPageNo ;
    private LinearLayoutManager linearLayoutManager;
    private DashBoardBindingAdapter adapter;
    public RefferedHistoryFragment() {
        // Required empty public constructor
    }

    public static RefferedHistoryFragment newInstance() {

        MoreActivity.self.selMore = false;
        return new RefferedHistoryFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reffered_history, container, false);

         linearLayoutManager = new LinearLayoutManager(MoreActivity.self);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
         binding.rwDashboard.setLayoutManager(linearLayoutManager);
       // binding.rwDashboard.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        Calendar mcurrentDate=Calendar.getInstance();
        final int mYear = mcurrentDate.get(Calendar.YEAR);
        final int mMonth=mcurrentDate.get(Calendar.MONTH);
        final int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
        binding.editTextfromDate.setText(mDay+"-"+(mMonth+1)+"-"+mYear);
        binding.editTexttoDate.setText(mDay+"-"+(mMonth+1)+"-"+mYear);
        params.put(Constents.START_DATE,mYear+"-"+(mMonth+1)+"-"+mDay);//format must "2017-04-25"
        params.put(Constents.END_DATE,mYear+"-"+(mMonth+1)+"-"+mDay);// format must "2017-05-04"
        search();
        binding.editTextfromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker


                DatePickerDialog mDatePicker=new DatePickerDialog(MoreActivity.self, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        binding.editTextfromDate.setText(selectedday+"-"+(selectedmonth+1)+"-"+selectedyear);
                        params.put(Constents.START_DATE,selectedyear+"-"+(selectedmonth+1)+"-"+selectedday);//format must "2017-04-25"
                        search();
                    }
                },mYear, mMonth, mDay);
                // mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        binding.editTexttoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker


                DatePickerDialog mDatePicker=new DatePickerDialog(MoreActivity.self, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        binding.editTexttoDate.setText(selectedday+"-"+(selectedmonth+1)+"-"+selectedyear);
                        params.put(Constents.END_DATE,selectedyear+"-"+(selectedmonth+1)+"-"+selectedday);// format must "2017-05-04"
                        search();
                    }
                },mYear, mMonth, mDay);
                // mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        MoreActivity.self.moreActivityBinding.back.setVisibility(View.VISIBLE);
     MoreActivity.self.moreActivityBinding.   back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MoreActivity.self.   replaceFragment1(DashBoardFragment.newInstance());
                //MoreActivity.self.onBackPressed();
            }
        });

        return binding.getRoot();
    }

private void search(){
        nextPageNo = 1;
        params.put(Constents.LOGIN_ID, SharePreferenceData.getString(MoreActivity.self, Constents.DOCTOR_ID, "null"));
        params.put(Constents.PAGE, String.valueOf(nextPageNo));
        new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.SEARCH_REFERRAL_HISTORY, params, new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {

                try {

                    if (jsonObj.getString("status").equals("true")) {
                        mPackagesList = new ArrayList<>();
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DashboardItem>>() {
                        }.getType();
                        List<DashboardItem> items = gson.fromJson("" + jsonObj.getJSONArray("data"), type);
                        for (int i = 0; i < items.size(); i++) {
                            mPackagesList.add(items.get(i));

                        }
                        adapter = new DashBoardBindingAdapter(MoreActivity.self, new ActionCallback2() {
                            @Override
                            public void onClick(DashboardItem dashboardItem) {
                                FragmentManager fm = MoreActivity.self.getSupportFragmentManager();
                                ReferPatientDailogFragment dialogFragment = new ReferPatientDailogFragment();

                                Bundle args = new Bundle();
                                args.putSerializable("item", dashboardItem);
                                dialogFragment.setArguments(args);
                                dialogFragment.show(fm, "");
                            }
                        }, mPackagesList);
                        if (binding.rwDashboard.getVisibility() == View.INVISIBLE)
                            binding.rwDashboard.setVisibility(View.VISIBLE);
                        binding.rwDashboard.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        binding.textViewResultText.setText(mPackagesList.size() + " " + "Patients");
                        nextPageNo = jsonObj.getInt("next");
                        binding.rwDashboard.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                            @Override
                            public void onLoadMore() {
                                search2();

                            }
                        });
                    } else {
                        binding.textViewResultText.setText("0 " + "Patients");
                        binding.rwDashboard.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).addQueue();
    }
    private void search2(){
        if( nextPageNo >1) {
            binding.itemProgressBar.setVisibility(View.VISIBLE);
            params.put(Constents.LOGIN_ID, SharePreferenceData.getString(MoreActivity.self, Constents.DOCTOR_ID, "null"));
            params.put(Constents.PAGE, String.valueOf(nextPageNo));
            new ApiGetPostNoProgressBar(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.SEARCH_REFERRAL_HISTORY, params, new ServerCallBackObj() {
                @Override
                public void onSuccess(JSONObject jsonObj) {

                    try {

                        if (jsonObj.getString("status").equals("true")) {

                            Gson gson = new Gson();
                            Type type = new TypeToken<List<DashboardItem>>() {
                            }.getType();
                            List<DashboardItem> items = gson.fromJson("" + jsonObj.getJSONArray("data"), type);
                            for (int i = 0; i < items.size(); i++) {
                                mPackagesList.add(items.get(i));

                            }

                            if (binding.rwDashboard.getVisibility() == View.INVISIBLE)
                                binding.rwDashboard.setVisibility(View.VISIBLE);

                            adapter.notifyDataSetChanged();
                            binding.textViewResultText.setText(mPackagesList.size() + " " + "Patients");
                            nextPageNo = jsonObj.getInt("next");
                            binding.itemProgressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).addQueue();
        }

}  @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_referred_history));
        super.onResume();
    }

}
