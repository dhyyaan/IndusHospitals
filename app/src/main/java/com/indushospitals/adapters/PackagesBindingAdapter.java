package com.indushospitals.adapters;


import com.indushospitals.R;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundAdapter;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundViewHolder;
import com.indushospitals.databinding.PackagesItemBinding;
import com.indushospitals.interfaces.listener.ActionCallback;
import com.indushospitals.model.PackagesItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think360 on 03/05/17.
 */

public class PackagesBindingAdapter extends DataBoundAdapter<PackagesItemBinding> {
    List<PackagesItem> mPackagesList = new ArrayList<>();
    private ActionCallback mActionCallback;
    public PackagesBindingAdapter(ActionCallback actionCallback, List<PackagesItem> mPackagesList) {
        super(R.layout.packages_item);
        mActionCallback = actionCallback;
        this.mPackagesList = mPackagesList;
        //Collections.addAll(mPackagesList, items);
    }

    @Override
    protected void bindItem(DataBoundViewHolder<PackagesItemBinding> holder, int position,
                            List<Object> payloads) {
        holder.binding.setData(mPackagesList.get(position));
        holder.binding.setCallback(mActionCallback);
    }

    @Override
    public int getItemCount() {
        return mPackagesList.size();
    }
}