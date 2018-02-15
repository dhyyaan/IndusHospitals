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
import com.indushospitals.databinding.NotificationDailogFragmentBinding;
import com.indushospitals.model.NotificationItem;

/**
 * Created by think360 on 02/08/17.
 */

public class NotificationDailogFragment  extends DialogFragment {
    private NotificationItem item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        NotificationDailogFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.notification_dailog_fragment, container, false);

        if (getArguments() != null) {
            item = (NotificationItem)getArguments().getSerializable("item");
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