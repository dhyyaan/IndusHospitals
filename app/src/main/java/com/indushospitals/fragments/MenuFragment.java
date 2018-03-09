package com.indushospitals.fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.indushospitals.utils.AppController;
import com.indushospitals.utils.ConnectivityReceiver;
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




    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

       switch (view.getId()) {
           case R.id.rlResDoctor:
               if(ConnectivityReceiver.isConnected()){
                   if(SharePreferenceData.getString(getActivity(), Constents.LOGIN,"false").equals("true"))
                       // Store the Fragment in stack
                       MoreActivity.self.replaceFragment(DashBoardFragment.newInstance());
                   else
                       MoreActivity.self.replaceFragment(DoctorRegistrationFragment.newInstance());
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

                break;
           case R.id.rlAboutIndus:
               if(ConnectivityReceiver.isConnected()){
                   MoreActivity.self.replaceFragment(AboutIndusHealthcareFragment.newInstance());
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

               break;
           case R.id.rlFeedbak:

               if(ConnectivityReceiver.isConnected()){
                   MoreActivity.self.replaceFragment(FeedbackQueryFragment.newInstance());
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

               break;
           case R.id.rlContactus:
               if(ConnectivityReceiver.isConnected()){
                   MoreActivity.self.replaceFragment(ContactUsFragment.newInstance());
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

               break;
           case R.id. rlPrivacyPolicy:
               if(ConnectivityReceiver.isConnected()){
                   MoreActivity.self.replaceFragment(PrivacyPolicyFragment.newInstance());
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

               break;
           case R.id.rlShare:

               if(ConnectivityReceiver.isConnected()){
                   Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                   sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.think360.hospitalIndus.indus");
                   sharingIntent.setType("text/plain");
                   startActivity(Intent.createChooser(sharingIntent,"Share using"));
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

               break;
            case R.id.rlRateApp:
                if(ConnectivityReceiver.isConnected()){
                    rateApp();
                }else{
                    TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
                }

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
               if(ConnectivityReceiver.isConnected()){
                   MoreActivity.self.moreActivityBinding.title.setText("Facebook");
                   MoreActivity.self.replaceFragment(SocialMediaFragment.newInstance("https://www.facebook.com/Indushospitals/"));
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

               break;
           case R.id.ivTwitter:

               if(ConnectivityReceiver.isConnected()){
                   MoreActivity.self.moreActivityBinding.title.setText("Twitter");
                   MoreActivity.self.replaceFragment(SocialMediaFragment.newInstance("https://twitter.com/IndusCareforYOU"));
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

               break;
           case R.id.insta:
               if(ConnectivityReceiver.isConnected()){
                   MoreActivity.self.moreActivityBinding.title.setText("Instagram");
                   MoreActivity.self.replaceFragment(SocialMediaFragment.newInstance("https://www.instagram.com/indushospital/"));
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);
               }

               break;
           case R.id.youtube:
               if(ConnectivityReceiver.isConnected()){
                   MoreActivity.self.moreActivityBinding.title.setText("YouTube");
                   MoreActivity.self.replaceFragment(SocialMediaFragment.newInstance("https://www.youtube.com/channel/UCWCX1mCMpU2uaD-wXqAB9Rg"));
               }else{
                   TastyToast.makeText(MoreActivity.self, "NO Internet Connection!" , TastyToast.LENGTH_LONG, TastyToast.ERROR);

               }

               break;

        }

    }
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_menu));
        super.onResume();
    }
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }
    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url,MoreActivity.self.getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }
}
