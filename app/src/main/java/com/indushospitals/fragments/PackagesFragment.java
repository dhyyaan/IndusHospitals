package com.indushospitals.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.adapters.PackagesBindingAdapter;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.databinding.FragmentPackagesBinding;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.interfaces.listener.ActionCallback;
import com.indushospitals.model.PackagesItem;



import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackagesFragment extends Fragment {
    public PackagesFragment() {
        // Required empty public constructor
    }

    public static PackagesFragment newInstance() {

        return new PackagesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FragmentPackagesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_packages, container, false);
       MoreActivity.self.moreActivityBinding. back.setVisibility(View.INVISIBLE);

        final ActionCallback actionCallback = new ActionCallback() {
            @Override
            public void onClick(PackagesItem item) {

            MoreActivity.self.replaceFragment(PackageDetailFragment.newInstance(item));
            }
        };

        Map<String, String> params = new HashMap<>();
        new ApiGetPostData(getActivity(), Request.Method.GET, BaseUrl.BASE_URL + BaseUrl.ALL_PACKAGE,params ,new ServerCallBackObj() {
            @Override
            public void onSuccess(JSONObject jsonObj) {

                try {
                    if(jsonObj.getString("status").equals("true")){
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<PackagesItem>>(){}.getType();
                        List<PackagesItem> mPackagesList  = gson.fromJson(""+jsonObj.getJSONArray("data"), type);
                        PackagesBindingAdapter adapter = new PackagesBindingAdapter(actionCallback, mPackagesList);
                        binding.packagesList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        if(mPackagesList.size()>0){
                            binding.tvNoPackages.setVisibility(View.GONE);
                            binding.packagesList.setVisibility(View.VISIBLE);
                        }else{
                            binding.tvNoPackages.setVisibility(View.VISIBLE);
                            binding.packagesList.setVisibility(View.GONE);
                        }


                    }else{
                        binding.tvNoPackages.setVisibility(View.VISIBLE);
                        binding.packagesList.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).addQueue();

        return binding.getRoot();
    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_packages));
        super.onResume();
    }

}
