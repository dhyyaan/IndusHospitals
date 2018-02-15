package com.indushospitals.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.indushospitals.R;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundAdapter;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundViewHolder;
import com.indushospitals.customview.circulartextview.ColorGenerator;
import com.indushospitals.customview.circulartextview.TextDrawable;
import com.indushospitals.databinding.DashBoardListRowBinding;
import com.indushospitals.fragments.ReferPatientDailogFragment;
import com.indushospitals.interfaces.listener.ActionCallback2;
import com.indushospitals.model.DashboardItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think360 on 06/05/17.
 */

public class DashBoardBindingAdapter  extends DataBoundAdapter<DashBoardListRowBinding> {
    List<DashboardItem> mPackagesList = new ArrayList<>();
    private ActionCallback2 mActionCallback;
    private AppCompatActivity activity;
    public DashBoardBindingAdapter(AppCompatActivity activity, ActionCallback2 actionCallback, List<DashboardItem> mPackagesList) {
        super(R.layout.dash_board_list_row);
        mActionCallback = actionCallback;
        this.mPackagesList = mPackagesList;
        this.activity = activity;
    }

    @Override
    protected void bindItem(DataBoundViewHolder<DashBoardListRowBinding> holder, final int position,
                            List<Object> payloads) {
        holder.binding.setData(mPackagesList.get(position));
        holder.binding.setCallback(mActionCallback);
        holder.binding.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentManager fm = activity.getSupportFragmentManager();
                ReferPatientDailogFragment dialogFragment = new ReferPatientDailogFragment();

                Bundle args = new Bundle();
                args.putSerializable("item", mPackagesList.get(position));
                dialogFragment.setArguments(args);

                dialogFragment.show(fm,"");
            }
        });
        TextDrawable drawable = TextDrawable.builder(activity)
                .buildRoundRect(mPackagesList.get(position).getSn(), ColorGenerator.MATERIAL.getRandomColor(),100); // radius in px
        holder.binding.imageView.setImageDrawable(drawable);


    }

    @Override
    public int getItemCount() {
        return mPackagesList.size();
    }

    /**
     * Created by think360 on 27/07/17.
     */

    public static class ContactUsBindingAdapter {
    }
}