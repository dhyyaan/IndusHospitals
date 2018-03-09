package com.indushospitals.fragments;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.adapters.DashBoardBindingAdapter;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.ApiGetPostNoProgressBar;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentDashBoardBinding;
import com.indushospitals.interfaces.FragmentLifecycle;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.interfaces.listener.ActionCallback2;
import com.indushospitals.model.DashboardItem;
import com.indushospitals.utils.ConnectivityReceiver;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.EndlessRecyclerOnScrollListener;
import com.indushospitals.utils.SharePreferenceData;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashBoardFragment extends Fragment implements FragmentLifecycle {
  ;
    private List<DashboardItem>  mPackagesList ;

     private DashBoardBindingAdapter adapter;
    private ActionCallback2 actionCallback;
    private FragmentDashBoardBinding binding;
    private int nextPageNo=1;
    public DashBoardFragment() {
        // Required empty public constructor
    }

    public static DashBoardFragment newInstance() {
        MoreActivity.  self.selMore = false;
        return new DashBoardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dash_board, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( MoreActivity.self);
        binding.rwDashboard.setLayoutManager(linearLayoutManager);
        actionCallback = new ActionCallback2() {
            @Override
            public void onClick(DashboardItem item) {

                FragmentManager fm =  MoreActivity.self.getSupportFragmentManager();
                ReferPatientDailogFragment dialogFragment = new ReferPatientDailogFragment();

                Bundle args = new Bundle();
                args.putSerializable("item", item);
                dialogFragment.setArguments(args);

                dialogFragment.show(fm, "Sample Fragment");

            }
        };

       Map<String, String> params = new HashMap<>();
        params.put(Constents.DOCTOR_ID, SharePreferenceData.getString( MoreActivity.self,Constents.DOCTOR_ID,"null"));
        params.put(Constents.PAGE, String.valueOf(nextPageNo));
        new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.REFERRAL_HISTORY,params ,new ServerCallBackObj() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(JSONObject jsonObj) {

                try {
                    if(jsonObj.getString("status").equals("true")){
                        mPackagesList = new ArrayList<>();
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DashboardItem>>(){}.getType();
                        List<DashboardItem>  items = gson.fromJson(""+jsonObj.getJSONArray("data"), type);

                        mPackagesList.addAll(items);


                        adapter = new DashBoardBindingAdapter( MoreActivity.self,actionCallback, mPackagesList);
                        binding.rwDashboard.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        binding.tvrefered.setText(mPackagesList.size()+" "+getResources().getString(R.string.text_refered_patient));

                        nextPageNo = jsonObj.getInt("next");

                    }else {
                        binding.tvrefered.setText("0 "+getResources().getString(R.string.text_refered_patient));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).addQueue();

        binding.btnReferPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ConnectivityReceiver.isConnected()){
                     MoreActivity.self.replaceFragment1(ReferPatientFragment.newInstance());
                 }else{
                     TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                 }
            }
        });

        MoreActivity.self.moreActivityBinding. back.setVisibility(View.INVISIBLE);
        MoreActivity.self.moreActivityBinding.  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(ConnectivityReceiver.isConnected()){
                       MoreActivity.self.onBackPressed();
                   }else{
                       TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                   }

            }
        });



        binding.rwDashboard.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                addDataToList();
            }
        });
        binding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()){
                    MoreActivity.self.replaceFragment( RefferedHistoryFragment.newInstance());
                }else{
                    TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

            }
        });
        return binding.getRoot();
    }

    private void addDataToList() {
        if( nextPageNo >1 ) {
            binding.itemProgressBar.setVisibility(View.VISIBLE);
            Map<String, String> params = new HashMap<>();
            params.put(Constents.DOCTOR_ID, SharePreferenceData.getString(MoreActivity.self,Constents.DOCTOR_ID,"null"));
            params.put(Constents.PAGE, String.valueOf(nextPageNo));

            new ApiGetPostNoProgressBar( MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.REFERRAL_HISTORY, params, new ServerCallBackObj() {
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

                            adapter.notifyDataSetChanged();
                            binding.tvrefered.setText(mPackagesList.size() + " Patients" );
                            binding.itemProgressBar.setVisibility(View.GONE);
                            nextPageNo = jsonObj.getInt("next");
                        } else {
                            binding.tvrefered.setText("0 " + getResources().getString(R.string.text_refered_patient));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).addQueue();
        }


    }

    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_referred_patient));
        super.onResume();
    }

    @Override
    public void onPauseFragment() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_referred_patient));
    }

    @Override
    public void onResumeFragment() {
        Map<String, String> params = new HashMap<>();
        params.put(Constents.DOCTOR_ID, SharePreferenceData.getString( MoreActivity.self,Constents.DOCTOR_ID,"null"));
        params.put(Constents.PAGE, String.valueOf(nextPageNo));
        new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.REFERRAL_HISTORY,params ,new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {

                try {
                    if(jsonObj.getString("status").equals("true")){
                        mPackagesList = new ArrayList<>();
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DashboardItem>>(){}.getType();
                        List<DashboardItem>  items = gson.fromJson(""+jsonObj.getJSONArray("data"), type);

                        for(int i =0; i<items.size();i++){
                            mPackagesList.add(items.get(i));
                        }


                        adapter = new DashBoardBindingAdapter( MoreActivity.self,actionCallback, mPackagesList);
                        binding.rwDashboard.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        binding.tvrefered.setText(mPackagesList.size()+" "+getResources().getString(R.string.text_refered_patient));

                        nextPageNo = jsonObj.getInt("next");

                    }else {
                        binding.tvrefered.setText("0 "+getResources().getString(R.string.text_refered_patient));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).addQueue();
    }
}
