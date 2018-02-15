package com.indushospitals.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.indushospitals.R;
import com.indushospitals.customview.circulartextview.ColorGenerator;
import com.indushospitals.customview.circulartextview.TextDrawable;
import com.indushospitals.databinding.FragmentReferPatientDailogBinding;
import com.indushospitals.model.DashboardItem;


/**
 * Created by think360 on 25/04/17.
 */

public class ReferPatientDailogFragment extends DialogFragment {
      private DashboardItem item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        FragmentReferPatientDailogBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_refer_patient_dailog, container, false);

 if (getArguments() != null) {
     item = (DashboardItem)getArguments().getSerializable("item");
 }
        TextDrawable drawable = TextDrawable.builder(getActivity())
                .buildRoundRect(item.getSn(), ColorGenerator.MATERIAL.getRandomColor(),100); // radius in px
        binding.imageView.setImageDrawable(drawable);
        binding.setData(item);
       binding.ivCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getDialog().dismiss();
           }
       });
        return binding.getRoot();
    }
}