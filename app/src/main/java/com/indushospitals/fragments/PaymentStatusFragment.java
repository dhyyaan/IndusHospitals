package com.indushospitals.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indushospitals.R;


/**
 * Created by think360 on 14/06/17.
 */

public class PaymentStatusFragment extends Fragment {

    public PaymentStatusFragment() {
        // Required empty public constructor
    }

    public static PaymentStatusFragment newInstance() {


        return new PaymentStatusFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_status, container, false);
    }

}
