package com.indushospitals.adapters;

import com.indushospitals.R;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundAdapter;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundViewHolder;

import com.indushospitals.databinding.ContactUsListItemBinding;
import com.indushospitals.interfaces.listener.ActionCallback;
import com.indushospitals.model.PackagesItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by think360 on 27/07/17.
 */

public class ContactUsBindingAdapter extends DataBoundAdapter<ContactUsListItemBinding> {
    List<PackagesItem> mPackagesList = new ArrayList<>();
    private ActionCallback mActionCallback;
    public ContactUsBindingAdapter(ActionCallback actionCallback, List<PackagesItem> mPackagesList) {
        super(R.layout.contact_us_list_item);
        mActionCallback = actionCallback;
        this.mPackagesList = mPackagesList;

    }

    @Override
    protected void bindItem(DataBoundViewHolder<ContactUsListItemBinding> holder, int position,
                            List<Object> payloads) {
        holder.binding.setData(mPackagesList.get(position));
        holder.binding.setCallback(mActionCallback);
    }

    @Override
    public int getItemCount() {
        return mPackagesList.size();
    }
}