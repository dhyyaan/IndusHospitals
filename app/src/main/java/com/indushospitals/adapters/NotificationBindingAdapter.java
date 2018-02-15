package com.indushospitals.adapters;

import android.app.Activity;

import com.indushospitals.R;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundAdapter;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundViewHolder;
import com.indushospitals.customview.circulartextview.ColorGenerator;
import com.indushospitals.customview.circulartextview.TextDrawable;
import com.indushospitals.databinding.NotificationItemBinding;
import com.indushospitals.interfaces.listener.ActionCallback4;
import com.indushospitals.model.NotificationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think360 on 06/05/17.
 */

public class NotificationBindingAdapter  extends DataBoundAdapter<NotificationItemBinding> {
    List<NotificationItem> mPackagesList = new ArrayList<>();
    private ActionCallback4 mActionCallback;
    private Activity activity;
    public NotificationBindingAdapter(Activity activity, ActionCallback4 actionCallback, List<NotificationItem> mPackagesList) {
        super(R.layout.notification_item);
        mActionCallback = actionCallback;
        this.mPackagesList = mPackagesList;
        this.activity = activity;
    }

    @Override
    protected void bindItem(DataBoundViewHolder<NotificationItemBinding> holder, int position,
                            List<Object> payloads) {
        holder.binding.setData(mPackagesList.get(position));
        holder.binding.setCallback(mActionCallback);
      /*  if(mPackagesList.get(position).getRead_unread().equals("0")){
            holder.binding.cardView.setCardBackgroundColor(Color.GRAY);
        }else{
            holder.binding.cardView.setCardBackgroundColor(Color.WHITE);
        }*/

        TextDrawable drawable = TextDrawable.builder(activity)
                .buildRoundRect(mPackagesList.get(position).getSn(), ColorGenerator.MATERIAL.getRandomColor(),100); // radius in px
        holder.binding.imageView.setImageDrawable(drawable);


    }

    @Override
    public int getItemCount() {
        return mPackagesList.size();
    }
}