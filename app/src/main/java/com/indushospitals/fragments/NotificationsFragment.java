package com.indushospitals.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.adapters.NotificationBindingAdapter;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.ApiGetPostNoProgressBar;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentNotificationsBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.interfaces.listener.ActionCallback4;
import com.indushospitals.model.NotificationItem;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;



import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NotificationsFragment extends Fragment {
     FragmentNotificationsBinding binding;
     ActionCallback4 actionCallback4;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance() {
        return  new NotificationsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      MoreActivity.self.moreActivityBinding.  back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.    back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.self.onBackPressed();
            }
        });
      //  MoreActivity.self.selMore = true;
        MoreActivity.self.  moreActivityBinding.badge.setVisibility(View.GONE);
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
           actionCallback4 = new ActionCallback4() {
            @Override
            public void onClick(NotificationItem item) {


                FragmentManager fm =  MoreActivity.self.getSupportFragmentManager();
                NotificationDailogFragment dialogFragment = new NotificationDailogFragment();

                Bundle args = new Bundle();
                args.putSerializable("item", item);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "Sample Fragment");

                Map<String, String> params = new HashMap<>();
                params.put("id", item.getId());
                new ApiGetPostData(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.READ_MESSAGE,params ,new ServerCallBackObj() {
                    @Override
                    public void onSuccess(JSONObject jsonObj) {

                        try {
                            if(jsonObj.getString("status").equals("true")){
                                getNotification();
                                MoreActivity.self. getNotificationCount();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).addQueue();
            }
        };
        getNotification();
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_notification));
        super.onResume();
    }
private void getNotification(){
    Map<String, String> params = new HashMap<>();
    params.put(Constents.DOCTOR_ID, SharePreferenceData.getString(getActivity(),Constents.DOCTOR_ID,"null"));
    new ApiGetPostNoProgressBar(MoreActivity.self, Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.NOTIFICATION,params ,new ServerCallBackObj() {
        @Override
        public void onSuccess(JSONObject jsonObj) {

            try {
                if(jsonObj.getString("status").equals("true")){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<NotificationItem>>(){}.getType();
                    List<NotificationItem> mPackagesList  = gson.fromJson(""+jsonObj.getJSONArray("data"), type);
                    NotificationBindingAdapter adapter = new NotificationBindingAdapter(getActivity(),actionCallback4, mPackagesList);
                    binding.rwDashboard.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }).addQueue();
}
}
