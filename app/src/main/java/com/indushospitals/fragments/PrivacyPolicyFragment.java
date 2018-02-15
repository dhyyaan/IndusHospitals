package com.indushospitals.fragments;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentPrivacyPolicyBinding;
import com.indushospitals.interfaces.ServerCallBackObj;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by think360 on 14/04/17.
 */

public class PrivacyPolicyFragment extends Fragment {
    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }
    public static PrivacyPolicyFragment newInstance() {
        MoreActivity.self.selMore = false;
        return new PrivacyPolicyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentPrivacyPolicyBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_policy, container, false);

        Map<String, String> params = new HashMap<>();
        new ApiGetPostData(getActivity(), Request.Method.GET, BaseUrl.BASE_URL+BaseUrl.PRIVACY_POLICY,params ,new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {

                try {
                    if(jsonObj.getString("status").equals("true")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.tvPrivacy.setText(Html.fromHtml(jsonObj.getJSONObject("data").getString("privacy_policy"), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            binding.tvPrivacy.setText(Html.fromHtml(jsonObj.getJSONObject("data").getString("privacy_policy")));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).addQueue();
        MoreActivity.self.moreActivityBinding.  back.setVisibility(View.VISIBLE);
      MoreActivity.self.moreActivityBinding.  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          getActivity().onBackPressed();

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_privacy_policy));
        super.onResume();
    }
}
