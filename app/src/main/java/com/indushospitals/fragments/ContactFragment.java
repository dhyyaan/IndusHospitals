package com.indushospitals.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.adapters.ContactUsBindingAdapter;
import com.indushospitals.databinding.ContactFragmentLayoutBinding;
import com.indushospitals.interfaces.listener.ActionCallback;
import com.indushospitals.model.PackagesItem;


import java.util.List;

/**
 * Created by think360 on 21/04/17.
 */

public class ContactFragment extends Fragment {



    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final ContactFragmentLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.contact_fragment_layout, container, false);


        final ActionCallback actionCallback = new ActionCallback() {
            @Override
            public void onClick(final PackagesItem item) {

                new AlertDialog.Builder(MoreActivity.self)
                       // .setTitle("Your Alert")
                        .setMessage(item.getContact_no())
                       // .setView()
                        .setCancelable(false)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+item.getContact_no()));
                                startActivity(intent);
                            }
                        }).show();
            }
        };


                        DividerItemDecoration horizontalDecoration = new DividerItemDecoration( binding.rvRearchResult.getContext(),
                                DividerItemDecoration.VERTICAL);

                        ContactUsBindingAdapter adapter = new ContactUsBindingAdapter(actionCallback, ContactUsFragment. mPackagesList);
                        binding.rvRearchResult.addItemDecoration(horizontalDecoration);
                        binding.rvRearchResult.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

        return binding.getRoot();
    }

}



