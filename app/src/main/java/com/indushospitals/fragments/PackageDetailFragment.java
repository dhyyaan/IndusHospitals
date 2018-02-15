package com.indushospitals.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.databinding.FragmentPackageDetailBinding;
import com.indushospitals.model.PackagesItem;


public class PackageDetailFragment extends Fragment {
    private PackagesItem item;

    public PackageDetailFragment() {
        // Required empty public constructor
    }

    public static PackageDetailFragment newInstance(PackagesItem param1) {
        PackageDetailFragment fragment = new PackageDetailFragment();
        fragment.item = param1;
        MoreActivity.self.selPack = false;
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPackageDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_package_detail, container, false);
         binding.setData(item);

        Glide.with(MoreActivity.self).load(R.raw.call).into(binding.ivGifCall);
        binding.bottomButtonContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+item.getMobile()));
                startActivity(intent);
            }
        });

      MoreActivity.self.moreActivityBinding.  back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.  back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             MoreActivity.self.onBackPressed();

            }
        });
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_package_detail));
        super.onResume();
    }
}
