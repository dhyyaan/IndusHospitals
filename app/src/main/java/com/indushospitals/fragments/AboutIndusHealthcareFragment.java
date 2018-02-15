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
import com.indushospitals.databinding.AboutIndusHealthcarFragmentBinding;
import com.indushospitals.interfaces.ServerCallBackObj;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by think360 on 13/04/17.
 */

public class AboutIndusHealthcareFragment  extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    //  private OnFragmentInteractionListener mListener;

    public AboutIndusHealthcareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
    // * @param param1 Parameter 1.

     * @return A new instance of fragment NewsAndEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutIndusHealthcareFragment newInstance() {
        AboutIndusHealthcareFragment fragment = new AboutIndusHealthcareFragment();

        MoreActivity.self.selMore = false;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final AboutIndusHealthcarFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.about_indus_healthcar_fragment, container, false);

        Map<String, String> params = new HashMap<>();
        new ApiGetPostData(getActivity(), Request.Method.GET, BaseUrl.BASE_URL+BaseUrl.ABOUT_HEALTHCARE,params ,new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {


                    try {
                        if(jsonObj.getString("status").equals("true")) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                binding.tvAboutHealthcare.setText(Html.fromHtml(jsonObj.getJSONObject("data").getString("healthcare"), Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                binding.tvAboutHealthcare.setText(Html.fromHtml(jsonObj.getJSONObject("data").getString("healthcare")));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }).addQueue();
        MoreActivity.self.moreActivityBinding.  back.setVisibility(View.VISIBLE);
     MoreActivity.self.moreActivityBinding.   back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.self.onBackPressed();

            }
        });
        return binding.getRoot();

    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_about_healthcare));
        super.onResume();
    }


}
