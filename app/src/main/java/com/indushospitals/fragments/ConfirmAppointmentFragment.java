package com.indushospitals.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.utils.Constents;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmAppointmentFragment extends Fragment {
    private static final String ARG_PARAM2 = "param2";
    private String mFormSubmitId;
    public ConfirmAppointmentFragment() {
        // Required empty public constructor
    }

    public static ConfirmAppointmentFragment newInstance(String param2) {
        ConfirmAppointmentFragment fragment = new ConfirmAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            try {
                mFormSubmitId =  new JSONObject(getArguments().getString(ARG_PARAM2)).getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        MoreActivity.self.selApt = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.fragment_confirm_appointment, container, false);

        RelativeLayout button = (RelativeLayout)view.findViewById(R.id.rl_pay_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MoreActivity.self.replaceFragment(PaymentFragment.newInstance(mFormSubmitId));

            }
        });
        RelativeLayout buttonReqAppointment = (RelativeLayout)view.findViewById(R.id.rl_req_appointment);
        buttonReqAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!(mFormSubmitId.equals("null"))) {
                    Map<String, String> params = new HashMap<>();
                    params.put(Constents.ID, mFormSubmitId);
                    new ApiGetPostData(getActivity(), Request.Method.POST, BaseUrl.BASE_URL + BaseUrl.REQUEST_APPOINTMENT, params, new ServerCallBackObj() {
                        @Override
                        public void onSuccess(JSONObject jsonObj) {

                            try {
                                if (jsonObj.getString("status").equals("true")) {
                                    MoreActivity.self.replaceFragment(BookingStatusFragment.newInstance());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).addQueue();
                }

            }
        });

     MoreActivity.self.moreActivityBinding.   back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.   back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.self.onBackPressed();
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_confirm_appointment));
        super.onResume();
    }

}
