package com.indushospitals.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.api.ApiGetPostData;
import com.indushospitals.api.baseurl.BaseUrl;
import com.indushospitals.interfaces.ServerCallBackObj;
import com.indushospitals.model.LocationIndus;
import com.indushospitals.model.PackagesItem;
import com.indushospitals.utils.font.CenturyGothicRegular;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactUsFragment extends Fragment {
     static List<PackagesItem> mPackagesList;
    static List<LocationIndus> mPackagesListl;
    private  ViewPager viewPager;
    private View view;
    private LinearLayout loc,contacts;
    private ImageView iv_location,iv_contacts;
    private CenturyGothicRegular tv_location,tv_contacts;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    public static ContactUsFragment newInstance() {

        MoreActivity.self.selMore = false;
        return new ContactUsFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_contact_us, container, false);


                Map<String, String> params = new HashMap<>();
                new ApiGetPostData(MoreActivity.self, Request.Method.GET, BaseUrl.BASE_URL + BaseUrl.GET_LOCATION,params ,new ServerCallBackObj() {
                    @Override
                    public void onSuccess(JSONObject jsonObj) {

                        try {
                            if(jsonObj.getString("status").equals("true")){
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<PackagesItem>>(){}.getType();
                                mPackagesList  = gson.fromJson(""+jsonObj.getJSONArray("data"), type);

                                //For Location
                                Gson gsonl = new Gson();
                                Type typel = new TypeToken<List<LocationIndus>>() {
                                }.getType();
                                mPackagesListl = gsonl.fromJson("" + jsonObj.getJSONArray("data"), typel);

                                viewPager = (ViewPager)view. findViewById(R.id.view_pager);
                                final PagerAdapter adapter = new PagerAdapter
                                        (getChildFragmentManager(), 2);
                                viewPager.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).addQueue();



        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_locations));


        MoreActivity.self.moreActivityBinding.  back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.   back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreActivity.self.onBackPressed();
                }
            });


         loc = (LinearLayout)view.findViewById(R.id.ll_location);
         iv_location = (ImageView)view.findViewById(R.id.iv_location);
          tv_location = (CenturyGothicRegular)view.findViewById(R.id.tv_location);

         contacts = (LinearLayout)view.findViewById(R.id.ll_contacts);

         iv_contacts = (ImageView)view.findViewById(R.id.iv_contacts);
         tv_contacts = (CenturyGothicRegular)view.findViewById(R.id.tv_contacts);


        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contacts.setBackground(  ResourcesCompat.getDrawable(getResources(), R.drawable.contactus_rectangle, null));
                loc.setBackgroundColor(Color.TRANSPARENT);
                iv_contacts.setImageResource(R.drawable.contact_us_white);
                iv_location.setImageResource(R.mipmap.location);
                tv_location.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tv_contacts.setTextColor(Color.WHITE);
                MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_contactus));
                viewPager.setCurrentItem(0);

            }
        });

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc.setBackground(  ResourcesCompat.getDrawable(getResources(), R.drawable.contactus_rectangle_right, null));

                contacts.setBackgroundColor(Color.TRANSPARENT);

                iv_location.setImageResource(R.drawable.location_white);
                iv_contacts.setImageResource(R.mipmap.contact2);
                tv_contacts.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tv_location.setTextColor(Color.WHITE);
                MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_locations));
                viewPager.setCurrentItem(1);

            }
        });
        return view;
    }


    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_contactus));
        super.onResume();
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return ContactFragment.newInstance();
                case 1:
                    return LocationFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
