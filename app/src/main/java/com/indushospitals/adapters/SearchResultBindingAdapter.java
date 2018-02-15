package com.indushospitals.adapters;

import android.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundAdapter;
import com.indushospitals.adapters.recyclerbindingadapter.DataBoundViewHolder;
import com.indushospitals.databinding.SearchResultListRowBinding;
import com.indushospitals.interfaces.listener.ActionCallback3;
import com.indushospitals.model.SearchResultItem;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by think360 on 20/05/17.
 */

public class SearchResultBindingAdapter extends DataBoundAdapter<SearchResultListRowBinding> {
    List<SearchResultItem> mSearchResultList = new ArrayList<>();
    private ActionCallback3 mActionCallback3;
    public SearchResultBindingAdapter(ActionCallback3 actionCallback3, List<SearchResultItem> mSearchResultList) {
        super(R.layout.search_result_list_row);
        mActionCallback3 = actionCallback3;
        this.mSearchResultList = mSearchResultList;

    }

    @Override
    protected void bindItem(DataBoundViewHolder<SearchResultListRowBinding> holder, int position,
                            List<Object> payloads) {
        holder.binding.setData(mSearchResultList.get(position));
        holder.binding.setCallback(mActionCallback3);
    }

    @Override
    public int getItemCount() {
        return mSearchResultList.size();
    }
    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(CircleImageView view, String url) {
if(url.equals(""))
    view.setImageResource(R.mipmap.dr_gaurav);
        else
    Glide.with(MoreActivity.self).load(url).into(view);
    }
}