package com.indushospitals.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;
import com.indushospitals.utils.Constents;
import com.indushospitals.utils.SharePreferenceData;
import com.indushospitals.utils.font.CenturyGothicRegular;
import com.sdsmdg.tastytoast.TastyToast;


/**
 * Created by think360 on 07/04/17.
 */

public class MenuFragment extends Fragment implements View.OnClickListener {
    private CenturyGothicRegular textView;
    private RelativeLayout  rlLogOut;
    private View llogout;
    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_menu, container, false);
          llogout = view.findViewById(R.id.llogout);
        if(SharePreferenceData.getString(getActivity(), Constents.LOGIN,"false").equals("true")) {
            MoreActivity.self.moreActivityBinding.back.setVisibility(View.INVISIBLE);
              llogout.setVisibility(View.VISIBLE);
        }else{
            MoreActivity.self.moreActivityBinding.back.setVisibility(View.VISIBLE);
              llogout.setVisibility(View.GONE);
            MoreActivity.self.moreActivityBinding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreActivity.self.onBackPressed();

                }
            });

        }

        RelativeLayout rlResDoctor = (RelativeLayout)view.findViewById(R.id.rlResDoctor);
        RelativeLayout rlAboutIndus = (RelativeLayout)view.findViewById(R.id.rlAboutIndus);
        RelativeLayout rlFeedbak = (RelativeLayout)view.findViewById(R.id.rlFeedbak);
        RelativeLayout rlContactus = (RelativeLayout)view.findViewById(R.id.rlContactus);
        RelativeLayout rlPrivacyPolicy = (RelativeLayout)view.findViewById(R.id.rlPrivacyPolicy);
        RelativeLayout rlShare = (RelativeLayout)view.findViewById(R.id.rlShare);
        RelativeLayout rlRateApp = (RelativeLayout)view.findViewById(R.id.rlRateApp);
        RelativeLayout llSocialMedia = (RelativeLayout)view.findViewById(R.id.llSocialMedia);

        ImageView ivFacebook = (ImageView)llSocialMedia.findViewById(R.id.ivFacebook);
        ImageView ivTwitter = (ImageView)llSocialMedia.findViewById(R.id.ivTwitter);
        ImageView insta = (ImageView)llSocialMedia.findViewById(R.id.insta);
        ImageView youtube = (ImageView)llSocialMedia.findViewById(R.id.youtube);

        textView = (CenturyGothicRegular)view.findViewById(R.id.ivReferRes);
        rlLogOut = (RelativeLayout)view.findViewById(R.id.rlLogOut);
        if(SharePreferenceData.getString(getActivity(), Constents.LOGIN,"false").equals("true")) {

            textView.setText(getResources().getString(R.string.text_reffered_patient));
            rlLogOut.setVisibility(View.VISIBLE);

        }else{

            textView.setText(getResources().getString(R.string.text_doctor_res));
            rlLogOut.setVisibility(View.GONE);
            llogout.setVisibility(View.GONE);

        }

        rlResDoctor.setOnClickListener(this);
        rlAboutIndus.setOnClickListener(this);
        rlContactus.setOnClickListener(this);
        rlFeedbak.setOnClickListener(this);
        rlRateApp.setOnClickListener(this);
        rlShare.setOnClickListener(this);
        rlPrivacyPolicy.setOnClickListener(this);
        rlLogOut.setOnClickListener(this);

        ivFacebook.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        insta.setOnClickListener(this);
        youtube.setOnClickListener(this);



        return view;
    }




    @Override
    public void onClick(View view) {

       switch (view.getId()) {
           case R.id.rlResDoctor:
                if(SharePreferenceData.getString(getActivity(), Constents.LOGIN,"false").equals("true"))
                    // Store the Fragment in stack
                    MoreActivity.self.replaceFragment(DashBoardFragment.newInstance());
                else
                    MoreActivity.self.replaceFragment(DoctorRegistrationFragment.newInstance());
                break;
           case R.id.rlAboutIndus:
               MoreActivity.self.replaceFragment(AboutIndusHealthcareFragment.newInstance());
               break;
           case R.id.rlFeedbak:
               MoreActivity.self.replaceFragment(FeedbackQueryFragment.newInstance());
               break;
           case R.id.rlContactus:
               MoreActivity.self.replaceFragment(ContactUsFragment.newInstance());
               break;
           case R.id. rlPrivacyPolicy:
               MoreActivity.self.replaceFragment(PrivacyPolicyFragment.newInstance());
               break;
           case R.id.rlShare:
               Intent sharingIntent = new Intent(Intent.ACTION_SEND);
               sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.think360.hospitalIndus.indus");
               sharingIntent.setType("text/plain");
               startActivity(Intent.createChooser(sharingIntent,"Share using"));
               break;
            case R.id.rlRateApp:
               Toast.makeText(MoreActivity.self,"In progress",Toast.LENGTH_SHORT).show();
                break;
           case R.id. rlLogOut:
               AlertDialog.Builder alertDialog = new AlertDialog.Builder(MoreActivity.self);

               alertDialog.setCancelable(false);
               // Setting Dialog Message
               alertDialog.setMessage("Are you sure want to Logout?");


               // Setting Positive "Yes" Button
               alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog,int which) {
                       rlLogOut.setVisibility(View.GONE);
                       MoreActivity.self. moreActivityBinding.logo.setClickable(false);
                       MoreActivity.self. moreActivityBinding.badge.setVisibility(View.INVISIBLE);
                       textView.setText(getResources().getString(R.string.text_doctor_res));
                       SharePreferenceData.saveString(getActivity(),Constents.LOGIN,"false");
                       SharePreferenceData.saveString(getActivity(),Constents.DOCTOR_ID,"null");
                       TastyToast.makeText(MoreActivity.self, "Indus logout successfully" , TastyToast.LENGTH_LONG, TastyToast.INFO);
                   }
               });

               // Setting Negative "NO" Button
               alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       // Write your code here to invoke NO event
                     //  Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                       dialog.cancel();
                   }
               });

               // Showing Alert Message
               alertDialog.show();

               break;
           case R.id.ivFacebook:
               MoreActivity.self.moreActivityBinding.title.setText("Facebook");
               MoreActivity.self.replaceFragment(SocialMediaFragment.newInstance("https://www.facebook.com/Indushospitals/"));
               break;
           case R.id.ivTwitter:
               MoreActivity.self.moreActivityBinding.title.setText("Twitter");
               MoreActivity.self.replaceFragment(SocialMediaFragment.newInstance("https://twitter.com/IndusCareforYOU"));
               break;
           case R.id.insta:
               MoreActivity.self.moreActivityBinding.title.setText("Instagram");
               MoreActivity.self.replaceFragment(SocialMediaFragment.newInstance("https://www.instagram.com/indushospital/"));
               break;
           case R.id.youtube:
               MoreActivity.self.moreActivityBinding.title.setText("YouTube");
               MoreActivity.self.replaceFragment(SocialMediaFragment.newInstance("https://www.youtube.com/channel/UCWCX1mCMpU2uaD-wXqAB9Rg"));
               break;

        }

    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_menu));
        super.onResume();
    }

}
